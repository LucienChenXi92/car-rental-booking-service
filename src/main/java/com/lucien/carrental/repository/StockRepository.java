package com.lucien.carrental.repository;

import com.lucien.carrental.entity.StockDO;
import com.lucien.carrental.entity.StockView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StockRepository extends JpaRepository<StockDO, Long> {

    @Query(value = "SELECT s.stock_id as stockId, "
            + "s.car_id as carId, "
            + "s.current_rental_order_id as currentRentalOrderId,"
            + "c.brand as brand, "
            + "c.model as model, "
            + "c.cost_per_day as costPerDay "
            + "FROM stock s JOIN car c ON s.car_id = c.car_id "
            + "WHERE s.current_rental_order_id = -1;", nativeQuery = true)
    List<StockView> findAllAvailable();

    @Query(value = "SELECT * FROM stock s WHERE s.car_id = ?1 AND s.current_rental_order_id = -1 LIMIT 1 FOR UPDATE;",
            nativeQuery = true)
    StockDO findFirstAvailable(Long carId);

    @Query(value = "SELECT s.stock_id as stockId, "
            + "s.car_id as carId, "
            + "s.current_rental_order_id as currentRentalOrderId,"
            + "c.brand as brand, "
            + "c.model as model, "
            + "c.cost_per_day as costPerDay "
            + "FROM stock s JOIN car c ON s.car_id = c.car_id;", nativeQuery = true)
    List<StockView> findAllStocks();

    @Query(value = "SELECT s.stock_id as stockId, "
            + "s.car_id as carId, "
            + "s.current_rental_order_id as currentRentalOrderId,"
            + "c.brand as brand, "
            + "c.model as model, "
            + "c.cost_per_day as costPerDay "
            + "FROM stock s JOIN car c ON s.car_id = c.car_id "
            + "WHERE s.stock_id = ?1 ;", nativeQuery = true)
    StockView findById(long id);

}
