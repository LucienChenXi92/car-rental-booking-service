package com.lucien.carrental.model;

import com.lucien.carrental.entity.RentalOrderDO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "View model for creating rental order")
public class CreateRentalOrderVO {

    @ApiModelProperty(required = true)
    private String userId;

    @ApiModelProperty(required = true)
    private Long carId;

    @ApiModelProperty(required = true)
    private Date rentalStartTime;

    @ApiModelProperty(required = true)
    private Date rentalTargetEndTime;

    public static CreateRentalOrderVO convertToVO(RentalOrderDO roDO) {
        if (roDO == null) {
            return null;
        }
        return CreateRentalOrderVO.builder()
                .userId(roDO.getUserId())
                .rentalStartTime(roDO.getRentalStartTime())
                .rentalTargetEndTime(roDO.getRentalTargetEndTime())
                .build();
    }
}
