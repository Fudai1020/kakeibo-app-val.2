package kakeibo_app_java.kakeibo_app_java.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "shared")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Shared {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "share_id")
    private Long shareId;
    @ManyToOne
    @JoinColumn(name = "owner_id",nullable = false)
    private User owner;
    @ManyToOne
    @JoinColumn(name = "partner_id",nullable = false)
    private User partner;
    @Column(name = "started_at")
    private LocalDateTime startDate;
    @Column(name = "is_active")
    private boolean isActive;
}
