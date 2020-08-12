package com.lucien.carrentalbookingservice.service.impl;

import com.lucien.carrentalbookingservice.entity.StockWithCarInfo;
import com.lucien.carrentalbookingservice.exeception.SystemException;
import com.lucien.carrentalbookingservice.model.StockVO;
import com.lucien.carrentalbookingservice.repository.StockRepository;
import com.lucien.carrentalbookingservice.service.StockService;
import lombok.AllArgsConstructor;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class StockServiceTest {

    private StockService stockService;
    private StockRepository repository = mock(StockRepository.class);

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void init() {
        stockService = new StockServiceImpl(repository);
    }

    @Test
    public void testFindAllAvailable() {
        // given
        when(repository.findAllAvailable()).thenReturn(getAllAvailableStockData());

        // when
        List<StockVO> allStockVO = stockService.findAllAvailable();

        // then
        assert(allStockVO.size() == 3);
        StockVO stockVO = allStockVO.get(0);
        assert(2L == stockVO.getStockId());
        assert(1L == stockVO.getCarId());
        assert(-1L == stockVO.getCurrentRentalOrderId());
        assert("Toyota".equals(stockVO.getBrand()));
        assert("Camry".equals(stockVO.getModel()));
        assert(30 == stockVO.getCostPerDay().intValue());
    }

    @Test
    public void testFindAll() {
        // given
        when(repository.findAllStocks()).thenReturn(getAllStockData());

        // when
        List<StockVO> allStockVO = stockService.findAll();

        // then
        assert(allStockVO.size() == 4);

        StockVO stockVO = allStockVO.get(0);
        assert(1L == stockVO.getStockId());
        assert(1L == stockVO.getCarId());
        assert(1L == stockVO.getCurrentRentalOrderId());
        assert("Toyota".equals(stockVO.getBrand()));
        assert("Camry".equals(stockVO.getModel()));
        assert(30 == stockVO.getCostPerDay().intValue());
    }

    @Test
    public void testFindByIdWhenDataExists() {
        // given
        when(repository.findStockWithCarInfoById(2)).thenReturn(getStockData());

        // when
        StockVO stockVO = stockService.findById(2);

        // then
        assert(2L == stockVO.getStockId());
        assert(1L == stockVO.getCarId());
        assert(-1L == stockVO.getCurrentRentalOrderId());
        assert("Toyota".equals(stockVO.getBrand()));
        assert("Camry".equals(stockVO.getModel()));
        assert(30 == stockVO.getCostPerDay().intValue());
    }

    @Test
    public void testFindStockWithCarInfoByIdWhenDataNotExists() {
        // given
        when(repository.findStockWithCarInfoById(anyLong())).thenReturn(null);

        // desired result
        thrown.expect(SystemException.class);
        thrown.expectMessage("Stock doesn't exist or already deleted.");

        // test
        stockService.findById(10);
    }

    private List<StockWithCarInfo> getAllStockData() {
        List<StockWithCarInfo> mockedData = new ArrayList<>();
        mockedData.add(new StockWithCarData(1L, 1L, 1L,
                "Toyota", "Camry", new BigDecimal(30.0)));
        mockedData.add(new StockWithCarData(2L, 1L, -1L,
                "Toyota", "Camry", new BigDecimal(30.0)));
        mockedData.add(new StockWithCarData(3L, 2L, -1L,
                "BMW", "650", new BigDecimal(80.0)));
        mockedData.add(new StockWithCarData(4L, 2L, -1L,
                "BMW", "650", new BigDecimal(80.0)));
        return mockedData;
    }

    private List<StockWithCarInfo> getAllAvailableStockData() {
        List<StockWithCarInfo> mockedData = new ArrayList<>();
        mockedData.add(new StockWithCarData(2L, 1L, -1L,
                "Toyota", "Camry", new BigDecimal(30.0)));
        mockedData.add(new StockWithCarData(3L, 2L, -1L,
                "BMW", "650", new BigDecimal(80.0)));
        mockedData.add(new StockWithCarData(4L, 2L, -1L,
                "BMW", "650", new BigDecimal(80.0)));
        return mockedData;
    }

    private StockWithCarInfo getStockData() {
        return new StockWithCarData(2L, 1L, -1L,
                "Toyota", "Camry", new BigDecimal(30.0));
    }

    private

    @AllArgsConstructor
    class StockWithCarData implements StockWithCarInfo {

        private Long stockId;
        private Long carId;
        private Long currentRentalOrderId;
        private String brand;
        private String model;
        private BigDecimal costPerDay;

        @Override
        public Long getStockId() {
            return this.stockId;
        }

        @Override
        public Long getCarId() {
            return this.carId;
        }

        @Override
        public Long getCurrentRentalOrderId() {
            return this.currentRentalOrderId;
        }

        @Override
        public String getBrand() {
            return this.brand;
        }

        @Override
        public String getModel() {
            return this.model;
        }

        @Override
        public BigDecimal getCostPerDay() {
            return this.costPerDay;
        }
    }
}
