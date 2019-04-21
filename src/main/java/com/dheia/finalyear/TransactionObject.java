package com.dheia.finalyear;

/**
 * Created by Dheia on 05/04/2019.
 */

public class TransactionObject {

    private String name, price, date, quantity, total, buyorsell;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getBuyorsell() {
        return buyorsell;
    }

    public void setBuyorsell(String buyorsell) {
        this.buyorsell = buyorsell;
    }

    public TransactionObject(String name, String price, String quantity, String total, String date, String buyorsell) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.total = total;
        this.buyorsell = buyorsell;
        this.date = date;

    }
}
