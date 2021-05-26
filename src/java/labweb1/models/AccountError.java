/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labweb1.models;

/**
 *
 * @author Gabriel Nguyen
 */
public class AccountError {
    private String usernameLengthErr, usernameIsExisted, passwordLengthErr, fullNameLengthErr, phoneErr, phoneFormatErr, addressErr;
    private String usernameFormatErr;
    public AccountError() {
    }

    public AccountError(String usernameFormatErr, String usernameLengthErr, String usernameIsExisted, String passwordLengthErr, String fullNameLengthErr, String phoneErr, String phoneFormatErr, String addressErr) {
        this.usernameLengthErr = usernameLengthErr;
        this.usernameFormatErr = usernameFormatErr;
        this.usernameIsExisted = usernameIsExisted;
        this.passwordLengthErr = passwordLengthErr;
        this.fullNameLengthErr = fullNameLengthErr;
        this.phoneErr = phoneErr;
        this.phoneFormatErr = phoneFormatErr;
        this.addressErr = addressErr;
    }

    public String getUsernameLengthErr() {
        return usernameLengthErr;
    }

    public void setUsernameLengthErr(String usernameLengthErr) {
        this.usernameLengthErr = usernameLengthErr;
    }

    public String getUsernameIsExisted() {
        return usernameIsExisted;
    }

    public void setUsernameIsExisted(String usernameIsExisted) {
        this.usernameIsExisted = usernameIsExisted;
    }

    public String getPasswordLengthErr() {
        return passwordLengthErr;
    }

    public void setPasswordLengthErr(String passwordLengthErr) {
        this.passwordLengthErr = passwordLengthErr;
    }

    public String getFullNameLengthErr() {
        return fullNameLengthErr;
    }

    public void setFullNameLengthErr(String fullNameLengthErr) {
        this.fullNameLengthErr = fullNameLengthErr;
    }

    public String getUsernameFormatErr() {
        return usernameFormatErr;
    }

    public void setUsernameFormatErr(String usernameFormatErr) {
        this.usernameFormatErr = usernameFormatErr;
    }

    

    public String getPhoneErr() {
        return phoneErr;
    }

    public void setPhoneErr(String phoneErr) {
        this.phoneErr = phoneErr;
    }

    public String getPhoneFormatErr() {
        return phoneFormatErr;
    }

    public void setPhoneFormatErr(String phoneFormatErr) {
        this.phoneFormatErr = phoneFormatErr;
    }

    public String getAddressErr() {
        return addressErr;
    }

    public void setAddressErr(String addressErr) {
        this.addressErr = addressErr;
    }
    
    
}
