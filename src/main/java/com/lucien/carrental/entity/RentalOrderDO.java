package com.lucien.carrental.entity;

import com.lucien.carrental.model.RentalOrderVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.Date;

@Data
@Builder
@Entity
@Table(name = "rental_order")
@SQLDelete(sql = "update rental_order set deleted = 1 where rental_order_id = ?")
@Where(clause = "deleted = 0")
@NoArgsConstructor
@AllArgsConstructor
public class RentalOrderDO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rental_order_id", unique = true, nullable = false)
    private Long rentalOrderId;

    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(name = "stock_id", nullable = false)
    private Long stockId;

    @Column(name = "rental_start_time")
    private Date rentalStartTime;

    @Column(name = "rental_target_end_time")
    private Date rentalTargetEndTime;

    @Column(name = "rental_actual_end_time")
    private Date rentalActualEndTime;

    @Column(name = "last_update", nullable = false)
    @UpdateTimestamp
    private Date lastUpdate;

    @Column(name = "deleted", nullable = false)
    private boolean deleted;

    public static RentalOrderDO covertToDO(RentalOrderVO vo) {
        if (vo == null) {
            return null;
        }
        return RentalOrderDO.builder()
                .rentalOrderId(vo.getRentalOrderId())
                .userId(vo.getUserId())
                .stockId(vo.getStockId())
                .rentalStartTime(vo.getRentalStartTime())
                .rentalTargetEndTime(vo.getRentalTargetEndTime())
                .rentalActualEndTime(vo.getRentalActualEndTime())
                .lastUpdate(vo.getLastUpdate())
                .build();

    }

}
