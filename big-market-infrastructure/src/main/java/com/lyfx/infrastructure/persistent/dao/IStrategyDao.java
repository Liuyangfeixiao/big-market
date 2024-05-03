package com.lyfx.infrastructure.persistent.dao;

import com.lyfx.infrastructure.persistent.po.Strategy;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author Yangfeixaio Liu
 * @time 2/5/2024 下午8:40
 * @description Strategy Dao
 */
@Mapper
public interface IStrategyDao {
    List<Strategy> queryStrategyList();
}
