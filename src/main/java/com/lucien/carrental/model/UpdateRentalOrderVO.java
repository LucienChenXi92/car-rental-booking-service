package com.lucien.carrental.model;

import com.fasterxml.jackson.annotation.JsonProperty;
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
@ApiModel(description = "View model for updating rental order")
public class UpdateRentalOrderVO {

    @ApiModelProperty(required = true)
    private Long rentalOrderId;

    @ApiModelProperty(required = true)
    private Date rentalActualEndTime;

    public static UpdateRentalOrderVO convertToVO(RentalOrderDO roDO) {
        if (roDO == null) {
            return null;
        }
        return UpdateRentalOrderVO.builder()
                .rentalOrderId(roDO.getRentalOrderId())
                .rentalActualEndTime(roDO.getRentalActualEndTime())
                .build();
    }
}
