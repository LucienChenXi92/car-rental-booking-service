package com.lucien.carrentalbookingservice.service.impl;

import com.lucien.carrentalbookingservice.entity.StockWithCarInfo;
import com.lucien.carrentalbookingservice.exception.SystemException;
import com.lucien.carrentalbookingservice.model.StockVO;
import com.lucien.carrentalbookingservice.repository.StockRepository;
import com.lucien.carrentalbookingservice.service.StockService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.Validate.notNull;

@Service
public class StockServiceImpl implements StockService {

    private final StockRepository stockRepository;

    public StockServiceImpl(StockRepository stockRepository) {
        this.stockRepository = notNull(stockRepository);
    }

    @Override
    public List<StockVO> findAllAvailable() {
        List<StockWithCarInfo> results = stockRepository.findAllAvailable();
        return results.stream().map(StockVO::convertToVO).collect(Collectors.toList());
    }

    @Override
    public List<StockVO> findAll() {
        List<StockWithCarInfo> results = stockRepository.findAllStocks();
        return results.stream().map(StockVO::convertToVO).collect(Collectors.toList());
    }

    @Override
    public StockVO findById(long id) {
        StockWithCarInfo result = stockRepository.findStockWithCarInfoById(id);
        if (result == null) {
            throw new SystemException("Stock doesn't exist or already deleted.");
        }
        return StockVO.convertToVO(result);
    }

}
