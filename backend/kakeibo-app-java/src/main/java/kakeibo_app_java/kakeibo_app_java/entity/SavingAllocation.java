package kakeibo_app_java.kakeibo_app_java.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@Table(name = "saving_allocations")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SavingAllocation {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    @Column(name = "allocation_id")
    private Long id;
    @Column(name = "allocation_amount",precision = 10,scale = 2)
    private BigDecimal amount;
    @Column(name = "allocation_date")
    private LocalDate allocationDate;
    @Column(name = "created_at")
    private LocalDate createdAt;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "saving_id")
    private Saving saving;

}
