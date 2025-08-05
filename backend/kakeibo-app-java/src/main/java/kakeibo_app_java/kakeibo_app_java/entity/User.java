package kakeibo_app_java.kakeibo_app_java.entity;


import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "user_name")
    private String name;

    @Column(name = "user_email")
    private String email;

    @Column(name = "password_hash")
    private String password;
    
    @Column(name = "user_memo")
    private String memo;

    @Column(name = "created_at")
    private Date createdAt;

    public Long getId(){ return id; }
    public void setId(Long id){ this.id = id; }

    public String getName(){return name;}
    public void setName(String name){ this.name = name;}

    public String getEmail(){return email;}
    public void setEmail(String email){this.email = email;}

    public String getPassword(){return password;}
    public void setPassword(String password){this.password = password;}

    public String getMemo(){return memo;}
    public void setMemo(String memo){this.memo = memo;}

    public Date getCreatedAt(){return createdAt;}
    public void setCreatedAt(Date createdAt){this.createdAt = createdAt;}
}
