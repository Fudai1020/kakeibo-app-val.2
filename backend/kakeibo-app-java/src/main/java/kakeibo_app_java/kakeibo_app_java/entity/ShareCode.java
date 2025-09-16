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

@Table(name = "share_code")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class ShareCode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "code_id")
    private Long codeId;
    @Column(name = "code",unique = true,nullable = false)
    private String code;
    @ManyToOne
    @JoinColumn(name = "owner_id",nullable = false)
    private User owner;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
