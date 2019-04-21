package com.dheia.finalyear;

import java.util.ArrayList;

/**
 * Created by Dheia on 06/04/2019.
 */

public class Tab1Coin {


     private String name,qty,price,ID,url,symbol, percent;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getPercent() {
        return percent;
    }

    public void setPercent(String percent) {
        this.percent = percent;
    }

    public Tab1Coin(String name, String qty, String price, String ID, String url, String symbol, String percent) {
        this.name = name;
        this.qty = qty;
        this.price = price;
        this.ID = ID;
        this.url = url;
        this.symbol = symbol;
        this.percent=percent;
    }


}
