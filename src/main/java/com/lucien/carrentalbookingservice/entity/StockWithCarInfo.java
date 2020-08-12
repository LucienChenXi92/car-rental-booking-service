package com.lucien.carrentalbookingservice.entity;

import java.math.BigDecimal;

public interface StockWithCarInfo {

    Long getStockId();
    Long getCarId();
    Long getCurrentRentalOrderId();
    String getBrand();
    String getModel();
    BigDecimal getCostPerDay();

}
