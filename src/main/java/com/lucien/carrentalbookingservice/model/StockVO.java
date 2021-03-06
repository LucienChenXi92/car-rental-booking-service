package com.lucien.carrentalbookingservice.model;


import com.lucien.carrentalbookingservice.entity.StockWithCarInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StockVO {

    private Long stockId;
    private Long carId;
    private Long currentRentalOrderId;
    private BigDecimal costPerDay;
    private String brand;
    private String model;

    public static StockVO convertToVO(StockWithCarInfo entry) {
        if (entry == null) {
            return null;
        }
        return StockVO.builder()
                .stockId(entry.getStockId())
                .carId(entry.getCarId())
                .currentRentalOrderId(entry.getCurrentRentalOrderId())
                .costPerDay(entry.getCostPerDay())
                .brand(entry.getBrand())
                .model(entry.getModel())
                .build();
    }

}
