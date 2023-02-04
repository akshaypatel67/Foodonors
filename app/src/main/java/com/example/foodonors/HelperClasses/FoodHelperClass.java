package com.example.foodonors.HelperClasses;

public class FoodHelperClass {
    String phoneNo, preparationDate, contents;
    int quantity;

    public FoodHelperClass(String phoneNo, String preparationDate, String contents, int quantity) {
        this.phoneNo = phoneNo;
        this.preparationDate = preparationDate;
        this.contents = contents;
        this.quantity = quantity;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getPreparationDate() {
        return preparationDate;
    }

    public void setPreparationDate(String preparationDate) {
        this.preparationDate = preparationDate;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
