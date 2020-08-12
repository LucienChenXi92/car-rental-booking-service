package com.lucien.carrentalbookingservice.repository;

import com.lucien.carrentalbookingservice.entity.StockDO;
import com.lucien.carrentalbookingservice.entity.StockWithCarInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StockRepository extends JpaRepository<StockDO, Long> {

    @Query(value = "SELECT * FROM stock s WHERE s.car_id = ?1 AND s.current_rental_order_id = -1 LIMIT 1 FOR UPDATE;",
            nativeQuery = true)
    StockDO findFirstAvailable(Long carId);

    @Query(value = "SELECT s.stock_id as stockId, "
            + "s.car_id as carId, "
            + "s.current_rental_order_id as currentRentalOrderId,"
            + "c.brand as brand, "
            + "c.model as model, "
            + "c.cost_per_day as costPerDay "
            + "FROM stock s JOIN car c ON s.car_id = c.car_id "
            + "WHERE s.current_rental_order_id = -1;", nativeQuery = true)
    List<StockWithCarInfo> findAllAvailable();

    @Query(value = "SELECT s.stock_id as stockId, "
            + "s.car_id as carId, "
            + "s.current_rental_order_id as currentRentalOrderId,"
            + "c.brand as brand, "
            + "c.model as model, "
            + "c.cost_per_day as costPerDay "
            + "FROM stock s JOIN car c ON s.car_id = c.car_id;", nativeQuery = true)
    List<StockWithCarInfo> findAllStocks();

    @Query(value = "SELECT s.stock_id as stockId, "
            + "s.car_id as carId, "
            + "s.current_rental_order_id as currentRentalOrderId,"
            + "c.brand as brand, "
            + "c.model as model, "
            + "c.cost_per_day as costPerDay "
            + "FROM stock s JOIN car c ON s.car_id = c.car_id "
            + "WHERE s.stock_id = ?1 ;", nativeQuery = true)
    StockWithCarInfo findStockWithCarInfoById(long id);

}
