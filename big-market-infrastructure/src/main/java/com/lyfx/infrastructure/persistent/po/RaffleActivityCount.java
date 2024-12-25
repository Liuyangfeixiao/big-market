package com.lyfx.infrastructure.persistent.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author Yangfeixaio Liu
 * @time 2024/12/18 上午10:24
 * @description 抽奖活动次数配置表-持久化对象
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RaffleActivityCount {
    /**
     * 自增ID
     */
    private Long id;
    
    /**
     * 活动次数编号
     */
    private Long activityCountId;
    
    /**
     * 总次数
     */
    private Integer totalCount;
    
    /**
     * 日次数
     */
    private Integer dayCount;
    
    /**
     * 月次数
     */
    private Integer monthCount;
    
    /**
     * 创建时间
     */
    private Date createTime;
    
    /**
     * 更新时间
     */
    private Date updateTime;
}
