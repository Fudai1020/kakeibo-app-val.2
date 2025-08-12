package kakeibo_app_java.kakeibo_app_java.dto;

public class ChangePasswordRequest {
    private String password;
    private String newPassword;

    public String getPassword(){return password;}
    public void setPassword(String password){this.password = password;}

    public String getNewPassword(){return newPassword;}
    public void setNewPassword(String newPassword){this.newPassword = newPassword;}
}
