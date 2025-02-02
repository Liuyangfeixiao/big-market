package com.lyfx.trigger.api;

import com.lyfx.trigger.api.dto.*;
import com.lyfx.types.model.Response;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Yangfeixaio Liu
 * @time 2024/12/27 下午4:30
 * @description 抽奖活动服务
 */

public interface IRaffleActivityService {
    /**
     * 活动装配、数据预热缓存
     * @param activityId
     * @return 装配结果
     */
    Response<Boolean> armory(Long activityId);
    
    Response<ActivityDrawResponseDTO> draw(ActivityDrawRequestDTO request);
    
    /**
     * 日历签到返利接口
     * @param userId 用户ID
     * @return 签到状态
     */
    Response<Boolean> calendarSignRebate(String userId);
    
    /**
     * 判断是否完成日历签到返利
     * @param userId 用户ID
     * @return 签到状态
     */
    Response<Boolean> isCalendarSignRebate(String userId);
    
    /**
     * 查询用户活动账户
     * @param request 请求对象[活动ID, 用户ID]
     * @return 返回结果 [总额度，月额度，日额度]
     */
    Response<UserActivityAccountResponseDTO> queryUserActivityAccount(UserActivityAccountRequestDTO request);
    
    Response<List<SkuProductResponseDTO>> querySkuProductListByActivityId(Long activityId);
    
    Response<BigDecimal> queryUserCreditAccount(String userId);
    
    Response<Boolean> creditPayExchangeSku(SkuProductShopCarRequestDTO request);
}
