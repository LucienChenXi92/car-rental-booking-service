package com.lucien.carrental.service.impl;

import com.lucien.carrental.entity.RentalOrderDO;
import com.lucien.carrental.entity.StockDO;
import com.lucien.carrental.model.CreateRentalOrderVO;
import com.lucien.carrental.model.RentalOrderVO;
import com.lucien.carrental.model.UpdateRentalOrderVO;
import com.lucien.carrental.repository.RentalOrderRepository;
import com.lucien.carrental.repository.StockRepository;
import com.lucien.carrental.service.RentalOrderService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.Validate.notNull;


@Service
public class RentalOrderServiceImpl implements RentalOrderService {

    private final RentalOrderRepository roRepository;
    private final StockRepository stockRepository;

    public RentalOrderServiceImpl(RentalOrderRepository repository,
                                  StockRepository stockRepository) {
        this.roRepository = notNull(repository);
        this.stockRepository = notNull(stockRepository);
    }

    @Override
    public List<RentalOrderVO> findAll() {
        List<RentalOrderDO> results = roRepository.findAll();
        return results.stream().map(RentalOrderVO::convertToVO).collect(Collectors.toList());
    }

    @Override
    public RentalOrderVO findById(long id) {
        RentalOrderDO result = roRepository.findById(id).orElse(null);
        if (result == null) {
            throw new RuntimeException("This rental order doesn't exist or already deleted.");
        }
        return RentalOrderVO.convertToVO(result);
    }

    @Override
    @Transactional
    public RentalOrderVO create(CreateRentalOrderVO rentalOrderVO) {
        Long carId = rentalOrderVO.getCarId();
        // Transaction step1: confirm stock is enough and lock data
        StockDO stockDO = stockRepository.findFirstAvailable(carId);
        if (stockDO == null) {
            throw new RuntimeException("No available stock.");
        }

        // init rental order
        RentalOrderDO targetRentalOrderDo = RentalOrderDO.builder()
                .stockId(stockDO.getStockId())
                .userId(rentalOrderVO.getUserId())
                .rentalStartTime(rentalOrderVO.getRentalStartTime())
                .rentalTargetEndTime(rentalOrderVO.getRentalTargetEndTime())
                .deleted(false)
                .build();

        // Transaction step2: save rental order
        RentalOrderDO newCreatedRentalOrderDO = roRepository.saveAndFlush(targetRentalOrderDo);

        // Transaction step3: save stock
        stockDO.setCurrentRentalStockOrderId(newCreatedRentalOrderDO.getRentalOrderId());
        stockRepository.save(stockDO);

        // Response new created order
        return RentalOrderVO.convertToVO(newCreatedRentalOrderDO);
    }

    @Override
    @Transactional
    public RentalOrderVO update(UpdateRentalOrderVO rentalOrderVO) {

        Long rentalOrderId = rentalOrderVO.getRentalOrderId();

        // Transaction step1: find rental order in DB
        RentalOrderDO rentalOrderDO = roRepository.findById(rentalOrderId).orElse(null);

        // No available data to be updated
        if (rentalOrderDO == null) {
            throw new RuntimeException("Not a valid rental order.");
        }

        // To keep it sample, we only allow to update rentalActualEndTime to close rental order
        if (rentalOrderVO.getRentalActualEndTime() == null) {
            throw new RuntimeException("Rental order actual end time can not be null.");
        }

        // Transaction step2: update order data
        rentalOrderDO.setRentalActualEndTime(rentalOrderVO.getRentalActualEndTime());
        RentalOrderDO updatedRentalOrderDO = roRepository.saveAndFlush(rentalOrderDO);

        // Transaction step3: release stock resource
        StockDO stockDO = stockRepository.findById(rentalOrderDO.getStockId()).orElse(null);
        if (stockDO == null) {
            throw new RuntimeException("Can't find target stock.");
        }
        stockDO.setCurrentRentalStockOrderId(-1L);
        stockRepository.save(stockDO);

        // Response updated
        return RentalOrderVO.convertToVO(updatedRentalOrderDO);
    }

    @Override
    @Transactional
    public void delete(long id) {

        RentalOrderDO rentalOrderDO = roRepository.findById(id).orElse(null);
        if (rentalOrderDO == null) {
            throw new RuntimeException("This rental order doesn't exist or already deleted.");
        }
        if (rentalOrderDO.getRentalActualEndTime() == null) {
            throw new RuntimeException("This rental order is not close yet, please return cars to us first.");
        }
        roRepository.deleteById(id);
    }

}
