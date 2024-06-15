package com.lyfx.domain.strategy.service;

import com.lyfx.domain.strategy.model.entity.RaffleAwardEntity;
import com.lyfx.domain.strategy.model.entity.RaffleFactorEntity;

/**
 * @author Yangfeixiao Liu
 * @time 2024-05-06 11:58
 * @description
 */
public interface IRaffleStrategy {
    RaffleAwardEntity performRaffle(RaffleFactorEntity raffleFactorEntity);
}
