package com.lyfx.infrastructure.persistent.dao;

import com.lyfx.infrastructure.persistent.po.Award;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author Yangfeixaio Liu
 * @time 2/5/2024 下午8:39
 * @description Award Dao
 */
@Mapper
public interface IAwardDao {
    List<Award> queryAwardList();
    
    String queryAwardConfigByAwardId(Integer awardId);
    
    String queryAwardKeyByAwardId(Integer awardId);
}
