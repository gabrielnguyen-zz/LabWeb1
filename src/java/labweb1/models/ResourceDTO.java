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
public class ResourceDTO {
    private String name, color, category, from , end, status, description, requestDate, updatedDate;
    private int id, quantity;

    public ResourceDTO() {
    }

    public ResourceDTO(String name, String color, String category, int id, int quantity) {
        this.name = name;
        this.color = color;
        this.category = category;
        this.id = id;
        this.quantity = quantity;
    }
    
    public ResourceDTO(String name, String color, int id, int quantity, String from , String end) {
        this.name = name;
        this.color = color;
        this.quantity = quantity;
        this.id = id;
        this.from= from;
        this.end = end;
    }

     public ResourceDTO(String name, String color, int id, int quantity) {
        this.name = name;
        this.color = color;
        this.quantity = quantity;
        this.id = id;
    }

    public ResourceDTO(String name, String color, String category, String from, String end, String status, String description, String requestDate, String updatedDate, int id, int quantity) {
        this.name = name;
        this.color = color;
        this.category = category;
        this.from = from;
        this.end = end;
        this.status = status;
        this.description = description;
        this.requestDate = requestDate;
        this.updatedDate = updatedDate;
        this.id = id;
        this.quantity = quantity;
        
    }

    public String getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(String requestDate) {
        this.requestDate = requestDate;
    }

    public String getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(String updatedDate) {
        this.updatedDate = updatedDate;
    }

    


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
    
}
