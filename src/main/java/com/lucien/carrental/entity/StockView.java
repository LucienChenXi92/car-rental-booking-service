package com.lucien.carrental.entity;

import java.math.BigDecimal;

public interface StockView {

    Long getStockId();
    Long getCarId();
    Long getCurrentRentalOrderId();
    String getBrand();
    String getModel();
    BigDecimal getCostPerDay();
}
