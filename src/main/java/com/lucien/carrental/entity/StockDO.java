package com.lucien.carrental.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@Entity
@Table(name = "stock")
@NoArgsConstructor
@AllArgsConstructor
public class StockDO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stock_id", unique = true, nullable = false)
    private Long stockId;

    @Column(name = "car_id", nullable = false)
    private Long carId;

    @Column(name = "current_rental_order_id")
    private Long currentRentalStockOrderId; // when stock is available, this value is -1

}
