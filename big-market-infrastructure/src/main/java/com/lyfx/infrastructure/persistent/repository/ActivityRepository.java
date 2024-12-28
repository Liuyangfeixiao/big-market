package com.lyfx.infrastructure.persistent.repository;

import com.lyfx.domain.activity.event.ActivitySkuStockZeroMessageEvent;
import com.lyfx.domain.activity.model.aggregate.CreatePartakeOrderAggregate;
import com.lyfx.domain.activity.model.aggregate.CreateQuotaOrderAggregate;
import com.lyfx.domain.activity.model.entity.*;
import com.lyfx.domain.activity.model.vo.ActivitySkuStockKeyVO;
import com.lyfx.domain.activity.model.vo.ActivityStateVO;
import com.lyfx.domain.activity.model.vo.UserRaffleOrderStateVO;
import com.lyfx.domain.activity.repository.IActivityRepository;
import com.lyfx.infrastructure.event.EventPublisher;
import com.lyfx.infrastructure.persistent.dao.*;
import com.lyfx.infrastructure.persistent.po.*;
import com.lyfx.infrastructure.persistent.redis.IRedisService;
import com.lyfx.types.common.Constants;
import com.lyfx.types.enums.ResponseCode;
import com.lyfx.types.exception.AppException;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBlockingQueue;
import org.redisson.api.RDelayedQueue;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.Resource;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author Yangfeixaio Liu
 * @time 2024/12/20 下午7:24
 * @description 活动仓储服务
 */
@Slf4j
@Repository
public class ActivityRepository implements IActivityRepository {
    @Resource
    private IRedisService redisService;
    @Resource
    private IRaffleActivityDao raffleActivityDao;
    @Resource
    private IRaffleActivitySkuDao raffleActivitySkuDao;
    @Resource
    private IRaffleActivityCountDao raffleActivityCountDao;
    @Resource
    private IRaffleActivityAccountDao raffleActivityAccountDao;
    @Resource
    private IRaffleActivityOrderDao raffleActivityOrderDao;
    @Resource
    private IRaffleActivityAccountDayDao raffleActivityAccountDayDao;
    @Resource
    private IRaffleActivityAccountMonthDao raffleActivityAccountMonthDao;
    @Resource
    private IUserRaffleOrderDao userRaffleOrderDao;
    @Resource
    private TransactionTemplate transactionTemplate;
    @Resource
    private EventPublisher eventPublisher;
    @Resource
    private ActivitySkuStockZeroMessageEvent activitySkuStockZeroMessageEvent;
    
    @Override
    public ActivitySkuEntity queryActivitySku(Long sku) {
        RaffleActivitySku raffleActivitySku = raffleActivitySkuDao.queryActivitySku(sku);
        String cacheKey = Constants.RedisKey.ACTIVITY_SKU_STOCK_COUNT_KEY + sku;
        Long cacheSkuStock = redisService.getAtomicLong(cacheKey);
        // 从缓存中得到剩余库存，如果缓存中没有剩余库存，那就从数据库中取得
        if (cacheSkuStock == null) {
            cacheSkuStock = raffleActivitySku.getStockCountSurplus().longValue();
        }
        return ActivitySkuEntity.builder()
                .sku(raffleActivitySku.getSku())
                .activityId(raffleActivitySku.getActivityId())
                .activityCountId(raffleActivitySku.getActivityCountId())
                .stockCount(raffleActivitySku.getStockCount())
                .stockCountSurplus(cacheSkuStock.intValue())
                .build();
    }
    
    @Override
    public ActivityEntity queryRaffleActivityByActivityId(Long activityId) {
        // 优先从缓存中获取
        String cacheKey = Constants.RedisKey.ACTIVITY_KEY + activityId;
        ActivityEntity activityEntity = redisService.getValue(cacheKey);
        if (activityEntity != null) return activityEntity;
        
        // 从数据库中获取数据
        RaffleActivity raffleActivity = raffleActivityDao.queryRaffleActivityByActivityId(activityId);
        activityEntity = ActivityEntity.builder()
                .activityId(raffleActivity.getActivityId())
                .activityName(raffleActivity.getActivityName())
                .activityDesc(raffleActivity.getActivityDesc())
                .beginDateTime(raffleActivity.getBeginDateTime())
                .endDateTime(raffleActivity.getEndDateTime())
                .strategyId(raffleActivity.getStrategyId())
                .state(ActivityStateVO.valueOf(raffleActivity.getState()))
                .build();
        redisService.setValue(cacheKey, activityEntity);
        return activityEntity;
    }
    
    @Override
    public ActivityCountEntity queryRaffleActivityCountByActivityCountId(Long activityCountId) {
        // 优先从缓存获取
        String cacheKey = Constants.RedisKey.ACTIVITY_COUNT_KEY + activityCountId;
        ActivityCountEntity activityCountEntity = redisService.getValue(cacheKey);
        if (null != activityCountEntity) return activityCountEntity;
        // 从库中获取数据
        RaffleActivityCount raffleActivityCount = raffleActivityCountDao.queryRaffleActivityCountByActivityCountId(activityCountId);
        activityCountEntity = ActivityCountEntity.builder()
                .activityCountId(raffleActivityCount.getActivityCountId())
                .totalCount(raffleActivityCount.getTotalCount())
                .dayCount(raffleActivityCount.getDayCount())
                .monthCount(raffleActivityCount.getMonthCount())
                .build();
        redisService.setValue(cacheKey, activityCountEntity);
        return activityCountEntity;
        
    }
    
    @Override
    public void doSaveOrder(CreateQuotaOrderAggregate createOrderAggregate) {
        // 订单对象
        ActivityOrderEntity activityOrderEntity = createOrderAggregate.getActivityOrderEntity();
        RaffleActivityOrder raffleActivityOrder = new RaffleActivityOrder();
        raffleActivityOrder.setUserId(activityOrderEntity.getUserId());
        raffleActivityOrder.setSku(activityOrderEntity.getSku());
        raffleActivityOrder.setActivityId(activityOrderEntity.getActivityId());
        raffleActivityOrder.setActivityName(activityOrderEntity.getActivityName());
        raffleActivityOrder.setStrategyId(activityOrderEntity.getStrategyId());
        raffleActivityOrder.setOrderId(activityOrderEntity.getOrderId());
        raffleActivityOrder.setOrderTime(activityOrderEntity.getOrderTime());
        raffleActivityOrder.setTotalCount(activityOrderEntity.getTotalCount());
        raffleActivityOrder.setDayCount(activityOrderEntity.getDayCount());
        raffleActivityOrder.setMonthCount(activityOrderEntity.getMonthCount());
        raffleActivityOrder.setTotalCount(createOrderAggregate.getTotalCount());
        raffleActivityOrder.setDayCount(createOrderAggregate.getDayCount());
        raffleActivityOrder.setMonthCount(createOrderAggregate.getMonthCount());
        raffleActivityOrder.setState(activityOrderEntity.getState().getCode());
        raffleActivityOrder.setOutBusinessNo(activityOrderEntity.getOutBusinessNo());
        
        // 账户对象
        RaffleActivityAccount raffleActivityAccount = new RaffleActivityAccount();
        raffleActivityAccount.setUserId(createOrderAggregate.getUserId());
        raffleActivityAccount.setActivityId(createOrderAggregate.getActivityId());
        raffleActivityAccount.setTotalCount(createOrderAggregate.getTotalCount());
        raffleActivityAccount.setTotalCountSurplus(createOrderAggregate.getTotalCount());
        raffleActivityAccount.setDayCount(createOrderAggregate.getDayCount());
        raffleActivityAccount.setDayCountSurplus(createOrderAggregate.getDayCount());
        raffleActivityAccount.setMonthCount(createOrderAggregate.getMonthCount());
        raffleActivityAccount.setMonthCountSurplus(createOrderAggregate.getMonthCount());
        
        // 编程式事务
        transactionTemplate.execute(status -> {
            try {
                // 1. 写入订单
                raffleActivityOrderDao.insert(raffleActivityOrder);
                // 2. 更新账户
                int count = raffleActivityAccountDao.updateAccountQuota(raffleActivityAccount);
                // 3. 创建账户-更新为0则账户不存在
                if (0 == count) {
                    raffleActivityAccountDao.insert(raffleActivityAccount);
                }
                return 1;
            } catch (DuplicateKeyException e) {
                status.setRollbackOnly();
                log.error("写入订单记录，唯一索引冲突 userId: {} activityId: {} sku: {}", activityOrderEntity.getUserId(), activityOrderEntity.getActivityId(), activityOrderEntity.getSku(), e);
                throw new AppException(ResponseCode.INDEX_DUP.getCode());
                
            }
            
        });
        
    }
    
    @Override
    public void cacheActivitySkuStockCount(String cacheKey, Integer stockCount) {
        if (redisService.isExists(cacheKey)) return;
        redisService.setAtomicLong(cacheKey, stockCount);
    }
    
    @Override
    public boolean subtractionActivitySkuStock(Long sku, String cacheKey, Date endDateTime) {
        // 先扣减redis中的数据
        long surplus = redisService.decr(cacheKey);
        if (surplus < 0) {
            // 库存小于0，恢复为0个
            redisService.setAtomicLong(cacheKey, 0);
            return false;
        }
        // 1. 按照cacheKey decr 后的值，如 99、98、97 和 key 组成为库存锁的key进行使用。
        // 2. 加锁为了兜底，如果后续有恢复库存，手动处理等【运营是人来操作，会有这种情况发放，系统要做防护】，也不会超卖。因为所有的可用库存key，都被加锁了。
        // 3. 设置加锁时间为活动到期 + 延迟1天
        String lockKey = cacheKey + Constants.UNDERLINE + surplus;
        long expireMills = endDateTime.getTime() - System.currentTimeMillis() + TimeUnit.DAYS.toMillis(1);
        Duration duration = Duration.ofMillis(expireMills);
        Boolean lock = redisService.setNx(lockKey, duration);
        if (!lock) {
            log.info("活动sku库存加锁失败 {}", lockKey);
        }
        
        // 库存消耗殆尽，发送MQ消息，更新数据库库存
        if (surplus == 0) {
            eventPublisher.publish(activitySkuStockZeroMessageEvent.topic(), activitySkuStockZeroMessageEvent.buildEventMessage(sku));
        }
        
        // 加锁成功则扣减成功
        return lock;
    }
    
    @Override
    public void activitySkuStockConsumeSendQueue(ActivitySkuStockKeyVO activitySkuStockKeyVO) {
        String cacheKey = Constants.RedisKey.ACTIVITY_SKU_COUNT_QUEUE_KEY + activitySkuStockKeyVO.getSku();
        RBlockingQueue<ActivitySkuStockKeyVO> blockingQueue = redisService.getBlockingQueue(cacheKey);
        RDelayedQueue<ActivitySkuStockKeyVO> delayedQueue = redisService.getDelayedQueue(blockingQueue);
        delayedQueue.offer(activitySkuStockKeyVO, 3, TimeUnit.SECONDS);
    }
    
    @Override
    public ActivitySkuStockKeyVO takeQueueValue(Long sku) {
        String cacheKey = Constants.RedisKey.ACTIVITY_SKU_COUNT_QUEUE_KEY + sku;
        RBlockingQueue<ActivitySkuStockKeyVO> blockingQueue = redisService.getBlockingQueue(cacheKey);
        RDelayedQueue<ActivitySkuStockKeyVO> delayedQueue = redisService.getDelayedQueue(blockingQueue);
        return blockingQueue.poll();
    }
    
    @Override
    public void clearQueueValue(Long sku) {
        String cacheKey = Constants.RedisKey.ACTIVITY_SKU_COUNT_QUEUE_KEY + sku;
        RBlockingQueue<ActivitySkuStockKeyVO> blockingQueue = redisService.getBlockingQueue(cacheKey);
        blockingQueue.clear();
        RDelayedQueue<ActivitySkuStockKeyVO> delayedQueue = redisService.getDelayedQueue(blockingQueue);
        delayedQueue.clear();
    }
    
    @Override
    public void updateActivitySkuStock(Long sku) {
        raffleActivitySkuDao.updateActivitySkuStock(sku);
    }
    
    @Override
    public void clearActivitySkuStock(Long sku) {
        raffleActivitySkuDao.clearActivitySkuStock(sku);
    }
    
    @Override
    public List<Long> querySkuList() {
        return raffleActivitySkuDao.querySkuList();
    }
    
    @Override
    public void saveCreatePartakeOrderAggregate(CreatePartakeOrderAggregate createPartakeOrderAggregate) {
        String userId = createPartakeOrderAggregate.getUserId();
        Long activityId = createPartakeOrderAggregate.getActivityId();
        ActivityAccountEntity activityAccountEntity = createPartakeOrderAggregate.getActivityAccountEntity();
        ActivityAccountMonthEntity activityAccountMonthEntity = createPartakeOrderAggregate.getActivityAccountMonthEntity();
        ActivityAccountDayEntity activityAccountDayEntity = createPartakeOrderAggregate.getActivityAccountDayEntity();
        UserRaffleOrderEntity userRaffleOrderEntity = createPartakeOrderAggregate.getUserRaffleOrderEntity();
        
        transactionTemplate.execute(status -> {
            try {
                // 1. 更新总账户
                int totalCount = raffleActivityAccountDao.updateActivityAccountSubtractionQuota(
                        RaffleActivityAccount.builder()
                                .userId(userId)
                                .activityId(activityId)
                                .build()
                );
                if (totalCount != 1) {
                    status.setRollbackOnly();
                    log.warn("写入创建参与活动记录，更新总账户额度不足，异常 userId: {} activityId: {}", userId, activityId);
                    throw new AppException(ResponseCode.ACCOUNT_QUOTA_ERROR.getCode(), ResponseCode.ACCOUNT_QUOTA_ERROR.getInfo());
                }
                
                // 2. 创建或者更新月账户
                if (createPartakeOrderAggregate.isExistAccountMonth()) {
                    // 存在月账户，更新月账户
                    int updateMonthCount = raffleActivityAccountMonthDao.updateActivityAccountMonthSubtractionQuota(
                            RaffleActivityAccountMonth.builder()
                                    .userId(userId)
                                    .activityId(activityId)
                                    .month(activityAccountMonthEntity.getMonth())
                                    .build()
                    );
                    if (updateMonthCount != 1) {
                        status.setRollbackOnly();
                        log.warn("写入创建参与活动记录，更新月账户额度不足，异常 userId: {} activityId: {} month: {}", userId, activityId, activityAccountMonthEntity.getMonth());
                        throw new AppException(ResponseCode.ACCOUNT_MONTH_QUOTA_ERROR.getCode(), ResponseCode.ACCOUNT_MONTH_QUOTA_ERROR.getInfo());
                    }
                    // 更新总账户中的月库存剩余
                    raffleActivityAccountDao.updateActivityAccountMonthSubtractionQuota(
                            RaffleActivityAccount.builder()
                                    .userId(userId)
                                    .activityId(activityId)
                                    .build()
                    );
                } else {
                    // 不存在月账户，新创建月账户
                    raffleActivityAccountMonthDao.insertActivityAccountMonth(RaffleActivityAccountMonth.builder()
                            .userId(activityAccountMonthEntity.getUserId())
                            .activityId(activityAccountMonthEntity.getActivityId())
                            .month(activityAccountMonthEntity.getMonth())
                            .monthCount(activityAccountMonthEntity.getMonthCount())
                            .monthCountSurplus(activityAccountMonthEntity.getMonthCountSurplus() - 1)
                            .build());
                    // 更新总表中的镜像月额度
                    raffleActivityAccountDao.updateActivityAccountMonthSurplusImageQuota(RaffleActivityAccount.builder()
                            .userId(userId)
                            .activityId(activityId)
                            .monthCountSurplus(activityAccountEntity.getMonthCountSurplus())
                            .build());
                }
                
                // 3. 创建或更新日账户，true - 存在则更新，false - 不存在则插入
                if (createPartakeOrderAggregate.isExistAccountDay()) {
                    int updateDayCount = raffleActivityAccountDayDao.updateActivityAccountDaySubtractionQuota(RaffleActivityAccountDay.builder()
                            .userId(userId)
                            .activityId(activityId)
                            .day(activityAccountDayEntity.getDay())
                            .build());
                    if (1 != updateDayCount) {
                        // 未更新成功则回滚
                        status.setRollbackOnly();
                        log.warn("写入创建参与活动记录，更新日账户额度不足，异常 userId: {} activityId: {} day: {}", userId, activityId, activityAccountDayEntity.getDay());
                        throw new AppException(ResponseCode.ACCOUNT_DAY_QUOTA_ERROR.getCode(), ResponseCode.ACCOUNT_DAY_QUOTA_ERROR.getInfo());
                    }
                    // 更新总账户中日镜像库存
                    raffleActivityAccountDao.updateActivityAccountDaySubtractionQuota(
                            RaffleActivityAccount.builder()
                                    .userId(userId)
                                    .activityId(activityId)
                                    .build());
                } else {
                    raffleActivityAccountDayDao.insertActivityAccountDay(RaffleActivityAccountDay.builder()
                            .userId(activityAccountDayEntity.getUserId())
                            .activityId(activityAccountDayEntity.getActivityId())
                            .day(activityAccountDayEntity.getDay())
                            .dayCount(activityAccountDayEntity.getDayCount())
                            .dayCountSurplus(activityAccountDayEntity.getDayCountSurplus() - 1)
                            .build());
                    // 新创建日账户，则更新总账表中日镜像额度
                    raffleActivityAccountDao.updateActivityAccountDaySurplusImageQuota(RaffleActivityAccount.builder()
                            .userId(userId)
                            .activityId(activityId)
                            .dayCountSurplus(activityAccountEntity.getDayCountSurplus())
                            .build());
                    
                }
                
                // 4. 写入参与活动订单
                userRaffleOrderDao.insert(UserRaffleOrder.builder()
                        .userId(userRaffleOrderEntity.getUserId())
                        .activityId(userRaffleOrderEntity.getActivityId())
                        .activityName(userRaffleOrderEntity.getActivityName())
                        .strategyId(userRaffleOrderEntity.getStrategyId())
                        .orderId(userRaffleOrderEntity.getOrderId())
                        .orderTime(userRaffleOrderEntity.getOrderTime())
                        .orderState(userRaffleOrderEntity.getOrderState().getCode())
                        .build());
                
                return 1;
            } catch (DuplicateKeyException e) {
                status.setRollbackOnly();
                log.error("写入创建参与活动记录，唯一索引冲突 userId: {} activityId: {}", userId, activityId, e);
                throw new AppException(ResponseCode.INDEX_DUP.getCode(), e);
            }
        });
    }
    
    @Override
    public ActivityAccountEntity queryActivityAccountByUserId(String userId, Long activityId) {
        // 查询账户
        RaffleActivityAccount raffleActivityAccount = new RaffleActivityAccount();
        raffleActivityAccount.setUserId(userId);
        raffleActivityAccount.setActivityId(activityId);
        raffleActivityAccountDao.queryActivityAccountByUserId(raffleActivityAccount);
        RaffleActivityAccount accountRes = raffleActivityAccountDao.queryActivityAccountByUserId(raffleActivityAccount);
        if (accountRes == null) {
            return null;
        }
        // 转换对象
        return ActivityAccountEntity.builder()
                .userId(accountRes.getUserId())
                .activityId(accountRes.getActivityId())
                .totalCount(accountRes.getTotalCount())
                .totalCountSurplus(accountRes.getTotalCountSurplus())
                .dayCount(accountRes.getDayCount())
                .dayCountSurplus(accountRes.getDayCountSurplus())
                .monthCount(accountRes.getMonthCount())
                .monthCountSurplus(accountRes.getMonthCountSurplus())
                .build();
    }
    
    @Override
    public UserRaffleOrderEntity queryNoUsedRaffleOrder(PartakeRaffleActivityEntity partakeRaffleActivityEntity) {
        // 1. 查询数据
        UserRaffleOrder userRaffleOrderRes = userRaffleOrderDao.queryNoUsedRaffleOrder(
                UserRaffleOrder.builder()
                .userId(partakeRaffleActivityEntity.getUserId())
                .activityId(partakeRaffleActivityEntity.getActivityId())
                .build());
        if (null == userRaffleOrderRes) {return null;}
        
        // 封装结果
        UserRaffleOrderEntity userRaffleOrderEntity = new UserRaffleOrderEntity();
        userRaffleOrderEntity.setUserId(userRaffleOrderRes.getUserId());
        userRaffleOrderEntity.setActivityId(userRaffleOrderRes.getActivityId());
        userRaffleOrderEntity.setActivityName(userRaffleOrderRes.getActivityName());
        userRaffleOrderEntity.setStrategyId(userRaffleOrderRes.getStrategyId());
        userRaffleOrderEntity.setOrderId(userRaffleOrderRes.getOrderId());
        userRaffleOrderEntity.setOrderTime(userRaffleOrderRes.getOrderTime());
        userRaffleOrderEntity.setOrderState(UserRaffleOrderStateVO.valueOf(userRaffleOrderRes.getOrderState()));
        return userRaffleOrderEntity;
    }
    
    @Override
    public ActivityAccountMonthEntity queryActivityAccountMonthByUserId(String userId, Long activityId, String month) {
        // 1. 查询账户
        RaffleActivityAccountMonth raffleActivityAccountMonthRes = raffleActivityAccountMonthDao.queryActivityAccountMonthByUserId(
                RaffleActivityAccountMonth.builder()
                .userId(userId)
                .activityId(activityId)
                .month(month)
                .build());
        if (raffleActivityAccountMonthRes == null) {return null;}
        // 转换对象
        return ActivityAccountMonthEntity.builder()
                .userId(raffleActivityAccountMonthRes.getUserId())
                .activityId(raffleActivityAccountMonthRes.getActivityId())
                .month(raffleActivityAccountMonthRes.getMonth())
                .monthCount(raffleActivityAccountMonthRes.getMonthCount())
                .monthCountSurplus(raffleActivityAccountMonthRes.getMonthCountSurplus())
                .build();
    }
    
    @Override
    public ActivityAccountDayEntity queryActivityAccountDayByUserId(String userId, Long activityId, String day) {
        // 1. 查询数据
        RaffleActivityAccountDay raffleActivityAccountDayRes = raffleActivityAccountDayDao.queryActivityAccountDayByUserId(
                RaffleActivityAccountDay.builder()
                        .userId(userId)
                        .activityId(activityId)
                        .day(day)
                        .build());
        if (raffleActivityAccountDayRes == null) return null;
        // 2. 转换对象
        return ActivityAccountDayEntity.builder()
                .userId(raffleActivityAccountDayRes.getUserId())
                .activityId(raffleActivityAccountDayRes.getActivityId())
                .day(raffleActivityAccountDayRes.getDay())
                .dayCount(raffleActivityAccountDayRes.getDayCount())
                .dayCountSurplus(raffleActivityAccountDayRes.getDayCountSurplus())
                .build();
    }
    
    @Override
    public List<ActivitySkuEntity> queryActivitySkuListByActivityId(Long activityId) {
        List<RaffleActivitySku> raffleActivitySkus = raffleActivitySkuDao.queryActivitySkuListByActivityId(activityId);
        // 查询为空返回一个空列表
        if (null == raffleActivitySkus) {return new ArrayList<>();}
        List<ActivitySkuEntity> activitySkuEntities = new ArrayList<>(raffleActivitySkus.size());
        for (RaffleActivitySku raffleActivitySku : raffleActivitySkus) {
            ActivitySkuEntity activitySkuEntity = new ActivitySkuEntity();
            activitySkuEntity.setActivityId(activityId);
            activitySkuEntity.setSku(raffleActivitySku.getSku());
            activitySkuEntity.setActivityCountId(raffleActivitySku.getActivityCountId());
            activitySkuEntity.setStockCount(raffleActivitySku.getStockCount());
            activitySkuEntity.setStockCountSurplus(raffleActivitySku.getStockCountSurplus());
            activitySkuEntities.add(activitySkuEntity);
        }
        return activitySkuEntities;
    }
    
    @Override
    public Integer queryRaffleActivityAccountDayPartakeCount(Long activityId, String userId) {
        RaffleActivityAccountDay raffleActivityAccountDay = new RaffleActivityAccountDay();
        raffleActivityAccountDay.setActivityId(activityId);
        raffleActivityAccountDay.setUserId(userId);
        raffleActivityAccountDay.setDay(raffleActivityAccountDay.currentDay());
        Integer dayPartakeCount = raffleActivityAccountDayDao.queryRaffleActivityAccountDayPartakeCount(raffleActivityAccountDay);
        
        return null == dayPartakeCount ? 0 : dayPartakeCount;
    }
}
