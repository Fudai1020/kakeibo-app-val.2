package kakeibo_app_java.kakeibo_app_java.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Entity
@Table(name = "income_categories")
@AllArgsConstructor
@Builder
public class IncomeCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "income_category_id")
    private Long id;

    @Column(name = "income_category_name")
    private String categoryName;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")   
    private User user;

    @OneToMany(mappedBy = "incomeCategory")
    private List<Income> incomes = new ArrayList<>();

    public IncomeCategory(){}

    public IncomeCategory(String categoryName,User user){
        this.categoryName = categoryName;   
        this.user = user;
    }

    public Long getId(){return id;}
    public void setId(Long id){this.id = id;}

    public String getCategoryName(){return categoryName;}
    public void setCategoryName(String categoryName){this.categoryName = categoryName;}

    public User getUser(){return user;}
    public void setUser(User user){this.user = user;}

    public List<Income> getIncomes(){return incomes;}
    public void setIncomes(List<Income> incomes){this.incomes = incomes;}
}
