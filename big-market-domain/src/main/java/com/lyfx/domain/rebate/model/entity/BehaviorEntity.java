package com.lyfx.domain.rebate.model.entity;

import com.lyfx.domain.rebate.model.vo.BehaviorTypeVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Yangfeixaio Liu
 * @time 2025/1/2 17:42
 * @description
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BehaviorEntity {
    private String userId;
    private BehaviorTypeVO behaviorType;
    // 唯一值，确保幂等
    private String outBusinessNo;
}
