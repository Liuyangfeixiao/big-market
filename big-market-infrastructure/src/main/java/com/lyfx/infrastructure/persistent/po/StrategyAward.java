package com.lyfx.infrastructure.persistent.po;

import lombok.Data;

import java.math.BigDecimal;
import java.net.Inet4Address;
import java.util.Date;

/**
 * @author Yangfeixaio Liu
 * @time 2/5/2024 下午8:21
 * @description 抽奖策略奖品明细配置 - 概率、规则
 */
@Data
public class StrategyAward {
    /** 自增ID */
    private Long id;
    /** 抽奖策略ID */
    private Long strategyId;
    /** 抽奖奖品ID - 内部流转使用 */
    private Integer awardId;
    /** 抽奖奖品标题 */
    private String awardTitle;
    /** 抽奖奖品副标题 */
    private String awardSubtitle;
    /** 奖品库存总量 */
    private Integer awardCount;
    /** 奖品库存剩余 */
    private Integer awardCountSurplus;
    /** 奖品中奖概率 */
    private BigDecimal awardRate;
    /** 规则模型，奖品配置的规则 */
    private String ruleModels;
    /** 排序 */
    private Integer sort;
    /** 创建时间 */
    private Date createTime;
    /** 修改时间 */
    private Date updateTime;
}
