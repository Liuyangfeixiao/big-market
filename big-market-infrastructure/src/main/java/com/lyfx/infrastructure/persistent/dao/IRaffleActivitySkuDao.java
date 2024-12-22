package com.lyfx.infrastructure.persistent.dao;

import com.lyfx.infrastructure.persistent.po.RaffleActivitySku;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author Yangfeixaio Liu
 * @time 2024/12/20 下午4:10
 * @description 商品SKu DAO
 */

@Mapper
public interface IRaffleActivitySkuDao {
    
    RaffleActivitySku queryActivitySku(Long sku);
    
    void updateActivitySkuStock(Long sku);
    
    void clearActivitySkuStock(Long sku);
    
    List<Long> querySkuList();
}
