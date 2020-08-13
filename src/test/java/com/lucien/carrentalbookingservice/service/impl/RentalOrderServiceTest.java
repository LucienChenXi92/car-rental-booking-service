package com.lucien.carrentalbookingservice.service.impl;

import com.lucien.carrentalbookingservice.entity.RentalOrderDO;
import com.lucien.carrentalbookingservice.entity.StockDO;
import com.lucien.carrentalbookingservice.exception.SystemException;
import com.lucien.carrentalbookingservice.model.CreateRentalOrderVO;
import com.lucien.carrentalbookingservice.model.RentalOrderVO;
import com.lucien.carrentalbookingservice.model.UpdateRentalOrderVO;
import com.lucien.carrentalbookingservice.repository.RentalOrderRepository;
import com.lucien.carrentalbookingservice.repository.StockRepository;
import com.lucien.carrentalbookingservice.service.RentalOrderService;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class RentalOrderServiceTest {

    private RentalOrderService orderService;
    private RentalOrderRepository orderRepository = mock(RentalOrderRepository.class);
    private StockRepository stockRepository = mock(StockRepository.class);
    private Date startDate = new Date(1597210911000L);
    private Date targetEndDate = new Date(1597218111000L);

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void init() {
        orderService = new RentalOrderServiceImpl(orderRepository, stockRepository);
    }

    @Test
    public void testFindAll() {
        // given
        when(orderRepository.findAll()).thenReturn(getAllRentalOrderData());

        // when
        List<RentalOrderVO> allRentalOrderVO = orderService.findAll();

        // then
        assert(2 == allRentalOrderVO.size());
        RentalOrderVO rentalOrderVO = allRentalOrderVO.get(0);
        assert(1L == rentalOrderVO.getRentalOrderId());
        assert("Lucien".equals(rentalOrderVO.getUserId()));
        assert(1L == rentalOrderVO.getStockId());
        assert(startDate.getTime() == rentalOrderVO.getRentalStartTime().getTime());
        assert(targetEndDate.getTime() == rentalOrderVO.getRentalTargetEndTime().getTime());
        assert(targetEndDate.getTime() == rentalOrderVO.getRentalActualEndTime().getTime());
        assert(startDate.getTime() == rentalOrderVO.getLastUpdate().getTime());
    }

    @Test
    public void testFindByIdWhenDataExists() {
        // given
        when(orderRepository.findById(1L)).thenReturn(getRentalOrderData());

        // when
        RentalOrderVO rentalOrderVO = orderService.findById(1L);

        // then
        assert(1L == rentalOrderVO.getRentalOrderId());
        assert("Lucien".equals(rentalOrderVO.getUserId()));
        assert(1L == rentalOrderVO.getStockId());
        assert(startDate.getTime() == rentalOrderVO.getRentalStartTime().getTime());
        assert(targetEndDate.getTime() == rentalOrderVO.getRentalTargetEndTime().getTime());
        assert(targetEndDate.getTime() == rentalOrderVO.getRentalActualEndTime().getTime());
        assert(startDate.getTime() == rentalOrderVO.getLastUpdate().getTime());
    }

    @Test
    public void testFindByIdWhenDataNotExists() {
        // given
        when(orderRepository.findById(anyLong())).thenReturn(Optional.empty());

        // desired exception
        thrown.expect(SystemException.class);
        thrown.expectMessage("Rental order doesn't exist or already deleted.");

        // test
        orderService.findById(10L);
    }

    @Test
    public void testCreateWhenSuccess() {
        // given
        CreateRentalOrderVO request = new CreateRentalOrderVO("Lucien", 1L, startDate, targetEndDate);
        StockDO targetStockDO = new StockDO(2L, 1L, -1L);
        RentalOrderDO savedOrderDO = new RentalOrderDO(1L, "Lucien", 2L,
                startDate, targetEndDate, null, startDate, false);

        when(stockRepository.findFirstAvailable(anyLong())).thenReturn(targetStockDO);
        when(orderRepository.saveAndFlush(any(RentalOrderDO.class))).thenReturn(savedOrderDO);

        // when
        RentalOrderVO savedOrderVO = orderService.create(request);

        // then
        verify(stockRepository, Mockito.times(1)).findFirstAvailable(1L);
        verify(orderRepository, Mockito.times(1)).saveAndFlush(any(RentalOrderDO.class));
        verify(stockRepository, Mockito.times(1)).save(any(StockDO.class));

        assert(savedOrderVO != null);
        assert(1L == savedOrderVO.getRentalOrderId());
        assert("Lucien".equals(savedOrderVO.getUserId()));
        assert(2L == savedOrderVO.getStockId());
        assert(startDate.getTime() == savedOrderVO.getRentalStartTime().getTime());
        assert(targetEndDate.getTime() == savedOrderVO.getRentalTargetEndTime().getTime());
        assert(null == savedOrderVO.getRentalActualEndTime());
        assert(startDate.getTime() == savedOrderVO.getLastUpdate().getTime());
    }

    @Test
    public void testCreateWhenNoAvailableStock() {
        // given
        CreateRentalOrderVO request = new CreateRentalOrderVO("Lucien", 1L, startDate, targetEndDate);
        when(stockRepository.findFirstAvailable(anyLong())).thenReturn(null);

        // desired exception
        thrown.expect(SystemException.class);
        thrown.expectMessage("No available stock.");

        // test
        orderService.create(request);
    }

    @Test
    public void testUpdateWhenSuccess() {
        // given
        UpdateRentalOrderVO request = new UpdateRentalOrderVO(1L, targetEndDate);
        RentalOrderDO targetRentalOrderDO = new RentalOrderDO(1L, "Lucien",
                2L, startDate, targetEndDate, null, startDate, false);
        RentalOrderDO updatedRentalOrderDO = new RentalOrderDO(1L, "Lucien",
                2L, startDate, targetEndDate, targetEndDate, startDate, false);
        StockDO targetStockDO = new StockDO(1L, 1L, 1L);
        when(orderRepository.findById(anyLong())).thenReturn(Optional.of(targetRentalOrderDO));
        when(stockRepository.findById(anyLong())).thenReturn(Optional.of(targetStockDO));
        when(orderRepository.saveAndFlush(any(RentalOrderDO.class))).thenReturn(updatedRentalOrderDO);

        // when
        RentalOrderVO updatedOrderVO = orderService.update(request);

        // then
        verify(orderRepository, Mockito.times(1)).findById(1L);
        verify(orderRepository, Mockito.times(1)).saveAndFlush(any(RentalOrderDO.class));
        verify(stockRepository, Mockito.times(1)).findById(anyLong());
        verify(stockRepository, Mockito.times(1)).save(any(StockDO.class));

        assert(updatedOrderVO != null);
        assert(targetEndDate.getTime() == updatedOrderVO.getRentalActualEndTime().getTime());
    }

    @Test
    public void testUpdateWhenOrderNoExists() {
        // given
        UpdateRentalOrderVO request = new UpdateRentalOrderVO(1L, targetEndDate);
        RentalOrderDO targetRentalOrderDO = new RentalOrderDO(1L, "Lucien",
                2L, startDate, targetEndDate, targetEndDate, startDate, false);
        when(orderRepository.findById(anyLong())).thenReturn(Optional.of(targetRentalOrderDO));

        // desired exception
        thrown.expect(SystemException.class);
        thrown.expectMessage("Can not update a closed rental order.");

        // test
        orderService.update(request);
    }

    @Test
    public void testUpdateWhenOrderClosed() {
        // given
        UpdateRentalOrderVO request = new UpdateRentalOrderVO(1L, targetEndDate);
        when(orderRepository.findById(anyLong())).thenReturn(Optional.empty());

        // desired exception
        thrown.expect(SystemException.class);
        thrown.expectMessage("Rental order id is invalid.");

        // test
        orderService.update(request);
    }

    @Test
    public void testDeleteWhenSuccess() {
        // given
        Optional<RentalOrderDO> targetDO = Optional.of(new RentalOrderDO(1L, "Lucien", 1L,
                startDate, targetEndDate, targetEndDate, startDate, false));
        when(orderRepository.findById(anyLong())).thenReturn(targetDO);

        // when
        orderService.delete(1L);

        // then
        verify(orderRepository, Mockito.times(1)).deleteById(1L);
    }

    @Test
    public void testDeleteWhenWhenOrderNotExist() {
        // given
        Optional<RentalOrderDO> targetDO = Optional.empty();
        when(orderRepository.findById(anyLong())).thenReturn(targetDO);

        // desired exception
        thrown.expect(SystemException.class);
        thrown.expectMessage("Rental order doesn't exist or already deleted.");

        // test
        orderService.delete(1L);
    }

    @Test
    public void testDeleteWhenWhenOrderNotClosed() {
        // given
        Optional<RentalOrderDO> targetDO = Optional.of(new RentalOrderDO(1L, "Lucien", 1L,
                startDate, targetEndDate, null, startDate, false));
        when(orderRepository.findById(anyLong())).thenReturn(targetDO);

        // desired exception
        thrown.expect(SystemException.class);
        thrown.expectMessage("Rental order is not close yet, please close first.");

        // test
        orderService.delete(1L);
    }

    private List<RentalOrderDO> getAllRentalOrderData() {
        List<RentalOrderDO> data = new ArrayList<>();
        data.add(new RentalOrderDO(1L, "Lucien", 1L,
                startDate, targetEndDate, targetEndDate, startDate, false));
        data.add(new RentalOrderDO(2L, "Lucien", 2L,
                startDate, targetEndDate, null, startDate, false));
        return data;
    }

    private Optional getRentalOrderData() {
        return Optional.of(new RentalOrderDO(1L, "Lucien", 1L,
                startDate, targetEndDate, targetEndDate, startDate, false));
    }

    private StockDO getOneStockDOData() {
        return new StockDO(2L, 1L, -1L);
    }

    private RentalOrderDO getOneOrderDOData() {
        return new RentalOrderDO(1L, "Lucien", 2L,
                startDate, targetEndDate, null, startDate, false);
    }

}
