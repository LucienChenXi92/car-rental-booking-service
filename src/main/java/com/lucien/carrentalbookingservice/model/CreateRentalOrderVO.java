package com.lucien.carrentalbookingservice.model;

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

}
