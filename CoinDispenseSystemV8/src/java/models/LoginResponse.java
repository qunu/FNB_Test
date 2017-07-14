package models;


import models.UserModel;

/**
 *
 * @author ryan
 */
public class LoginResponse {
    private boolean success = false;
    private UserModel userModel;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public UserModel getUserModel() {
        return userModel;
    }
    
    public void setUserModel(UserModel userModel) {
        this.userModel = userModel;
    }
    
    
    
    
    
}
