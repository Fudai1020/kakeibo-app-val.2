package kakeibo_app_java.kakeibo_app_java.entity;

import java.time.LocalDate;
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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@Table(name = "savings")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Saving {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "saving_id")
    private Long id;
    @Column(name = "savings_name")
    private String name;
    @Column(name = "created_at")
    private LocalDate createdAt;
    @Column(name = "is_private")
    private boolean isPrivate;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "userId")
    private User user;
    @OneToMany(mappedBy = "saving",cascade = CascadeType.ALL,orphanRemoval = true)
    @Builder.Default
    private List<SavingAllocation> allocations =new ArrayList<>();
}
