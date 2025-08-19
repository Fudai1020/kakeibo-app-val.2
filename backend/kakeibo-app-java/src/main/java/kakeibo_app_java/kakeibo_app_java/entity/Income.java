package kakeibo_app_java.kakeibo_app_java.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Builder;

@Entity
@Table(name = "incomes")
@Builder
public class Income {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "income_id")
    private Long id;

    @Column(name = "income_name")
    private String name;

    @Column(name = "income_amount",precision = 10,scale = 2)
    private BigDecimal amount;

    @Column(name = "income_memo")
    private String memo;

    @Column(name = "is_private")
    private boolean isPrivate;

    @Column(name = "income_date")
    private LocalDate incomeDate;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")   
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "income_category_id")
    private IncomeCategory incomeCategory;

    public Long getId(){return id;}
    public void setId(Long id){this.id = id;}

    public String getName(){return name;}
    public void setName(String name){this.name = name;}

    public BigDecimal getAmount(){return amount;}
    public void setAmount(BigDecimal amount){this.amount = amount;}

    public String getMemo(){return memo;}
    public void setMemo(String memo){this.memo = memo;}

    public boolean getIsPrivate(){return isPrivate;}
    public void setIsPrivate(boolean isPrivate){this.isPrivate = isPrivate;}

    public LocalDate getIncomeDate(){return incomeDate;}
    public void setIncomeDate(LocalDate incomeDate){this.incomeDate = incomeDate;}
    
    public LocalDateTime getCreatedAt(){return createdAt;}
    public void setCreatedAt(LocalDateTime createdAt){this.createdAt = createdAt;}

    public User getUser(){return user;}
    public void setUser(User user){this.user = user;}

    public IncomeCategory getIncomeCategory(){return incomeCategory;}
    public void setIncomeCategory(IncomeCategory incomeCategory){this.incomeCategory = incomeCategory;}
}
