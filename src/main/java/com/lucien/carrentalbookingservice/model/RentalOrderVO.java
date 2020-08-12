package com.lucien.carrentalbookingservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lucien.carrentalbookingservice.entity.RentalOrderDO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RentalOrderVO {

    private Long rentalOrderId;

    private String userId;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long carId;

    private Long stockId;

    private Date rentalStartTime;

    private Date rentalTargetEndTime;

    private Date rentalActualEndTime;

    private Date lastUpdate;

    public static RentalOrderVO convertToVO(RentalOrderDO roDO) {
        if (roDO == null) {
            return null;
        }
        return RentalOrderVO.builder()
                .rentalOrderId(roDO.getRentalOrderId())
                .stockId(roDO.getStockId())
                .userId(roDO.getUserId())
                .rentalStartTime(roDO.getRentalStartTime())
                .rentalTargetEndTime(roDO.getRentalTargetEndTime())
                .rentalActualEndTime(roDO.getRentalActualEndTime())
                .lastUpdate(roDO.getLastUpdate())
                .build();
    }
}
