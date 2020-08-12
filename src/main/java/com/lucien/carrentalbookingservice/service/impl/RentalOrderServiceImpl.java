package com.lucien.carrentalbookingservice.service.impl;

import com.lucien.carrentalbookingservice.entity.RentalOrderDO;
import com.lucien.carrentalbookingservice.entity.StockDO;
import com.lucien.carrentalbookingservice.exeception.SystemException;
import com.lucien.carrentalbookingservice.model.CreateRentalOrderVO;
import com.lucien.carrentalbookingservice.model.RentalOrderVO;
import com.lucien.carrentalbookingservice.model.UpdateRentalOrderVO;
import com.lucien.carrentalbookingservice.repository.RentalOrderRepository;
import com.lucien.carrentalbookingservice.repository.StockRepository;
import com.lucien.carrentalbookingservice.service.RentalOrderService;
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
            throw new SystemException("Rental order doesn't exist or already deleted.");
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
            throw new SystemException("No available stock.");
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

        // Transaction step3: sync stock info
        stockDO.setCurrentRentalOrderId(newCreatedRentalOrderDO.getRentalOrderId());
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
            throw new SystemException("Rental order id is invalid.");
        }

        // Can not updated a closed rental order
        if (rentalOrderDO.getRentalActualEndTime() != null) {
            throw new SystemException("Can not update a closed rental order.");
        }

        // Transaction step2: update order data
        rentalOrderDO.setRentalActualEndTime(rentalOrderVO.getRentalActualEndTime());
        RentalOrderDO updatedRentalOrderDO = roRepository.saveAndFlush(rentalOrderDO);

        // Transaction step3: release stock resource
        StockDO stockDO = stockRepository.findById(rentalOrderDO.getStockId()).orElse(null);
        stockDO.setCurrentRentalOrderId(-1L);
        stockRepository.save(stockDO);

        // Response updated
        return RentalOrderVO.convertToVO(updatedRentalOrderDO);
    }

    @Override
    @Transactional
    public void delete(long id) {

        RentalOrderDO rentalOrderDO = roRepository.findById(id).orElse(null);
        if (rentalOrderDO == null) {
            throw new SystemException("Rental order doesn't exist or already deleted.");
        }
        if (rentalOrderDO.getRentalActualEndTime() == null) {
            throw new SystemException("Rental order is not close yet, please close first.");
        }
        roRepository.deleteById(id);
    }

}
