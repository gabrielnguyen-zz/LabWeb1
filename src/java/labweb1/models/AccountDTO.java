/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labweb1.models;

import java.io.Serializable;

/**
 *
 * @author Gabriel Nguyen
 */
public class AccountDTO implements Serializable{
    private String accountName, phone, address, name, status, role;

    public AccountDTO(String accountName, String phone, String address, String name, String status, String role) {
        this.accountName = accountName;
        this.phone = phone;
        this.address = address;
        this.name = name;
        this.status = status;
        this.role = role;
    }

    public AccountDTO() {
        //To change body of generated methods, choose Tools | Templates.
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
    
}
