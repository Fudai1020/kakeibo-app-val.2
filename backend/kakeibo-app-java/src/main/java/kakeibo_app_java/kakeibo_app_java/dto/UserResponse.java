package kakeibo_app_java.kakeibo_app_java.dto;


import kakeibo_app_java.kakeibo_app_java.entity.User;

public class UserResponse {
    private Long id;
    private String email;
    private String memo;
    private java.util.Date createdAt;

    public UserResponse(User user){
        this.id = user.getId();
        this.email = user.getEmail();
        this.memo = user.getMemo();
        this.createdAt = user.getCreatedAt();
    }
    public Long getId(){
        return id;
    }
    public String getEmail(){
        return email;
    }
    public String getMemo(){
        return memo;
    }
    public java.util.Date getCreatedAt(){
        return createdAt;
    }
}
