package com.example.foodonors.HelperClasses;

public class FoodHelperClass {
    String phoneNo, preparationDate, availableTime, contents, foodTime;
    int quantity;

    public FoodHelperClass() {

    }

    public FoodHelperClass(String phoneNo, String preparationDate, String availableTime, String contents, int quantity, String foodTime) {
        this.phoneNo = phoneNo;
        this.preparationDate = preparationDate;
        this.availableTime = availableTime;
        this.contents = contents;
        this.quantity = quantity;
        this.foodTime = foodTime;
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

    public String getAvailableTime() {
        return availableTime;
    }

    public void setAvailableTime(String availableTime) {
        this.availableTime = availableTime;
    }

    public String getFoodTime() {
        return foodTime;
    }

    public void setFoodTime(String foodTime) {
        this.foodTime = foodTime;
    }
}
