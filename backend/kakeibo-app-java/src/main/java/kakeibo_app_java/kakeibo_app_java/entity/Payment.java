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
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "payments")
@Builder
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
    private Long id;
    @Column(name = "payment_name")
    private String name;
    @Column(name = "payment_amount",precision = 10,scale = 2)
    private BigDecimal amount;
    @Column(name = "payment_memo")
    private String memo;
    @Column(name = "is_private")
    private boolean isPrivate;
    @Column(name = "payment_date")
    private LocalDate paymentDate;
    @Column(name = "created_at")
    private LocalDate createdAt;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_category_id")
    private PaymentCategory paymentCategory;
    
}
