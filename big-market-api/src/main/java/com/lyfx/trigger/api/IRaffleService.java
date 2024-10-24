package com.lyfx.trigger.api;

import com.lyfx.trigger.api.dto.RaffleAwardListRequestDTO;
import com.lyfx.trigger.api.dto.RaffleAwardListResponseDTO;
import com.lyfx.trigger.api.dto.RaffleRequestDTO;
import com.lyfx.trigger.api.dto.RaffleResponseDTO;
import com.lyfx.types.model.Response;

import java.util.List;

/**
 * @author Yangfeixaio Liu
 * @time 2024/10/23 下午10:03
 * @description 抽奖服务接口
 */
public interface IRaffleService {
    
    /**
     * 策略装配接口
     * @param strategyId 策略Id
     * @return 装配结果
     */
    Response<Boolean> strategyArmory(Long strategyId);
    
    /**
     * 查询奖品配置列表
     * @param requestDTO 查询请求参数
     * @return 奖品列表数据
     */
    Response<List<RaffleAwardListResponseDTO>> queryRaffleAwardList(RaffleAwardListRequestDTO requestDTO);
    
    /**
     * 随机抽奖接口
     * @param requestDTO 请求参数
     * @return 抽奖结果
     */
    Response<RaffleResponseDTO> randomRaffle(RaffleRequestDTO requestDTO);
}
