package com.dheia.finalyear;

/**
 * Created by Dheia on 26/02/2019.
 */

public class RecyclerViewItem {

    public String getQRname() {
        return name;
    }

    public void getQRname(String name) {
        this.name = name;
    }

    public String getQRUrl() {
        return url;
    }

    public void setQRUrl(String url) {
        this.url = url;
    }

    public String getQRSymbol() {
        return symbol;
    }

    public void setQRSymbol(String symbol) {
        this.symbol = symbol;
    }


    private String name, url, symbol ;

    public RecyclerViewItem(String name, String url, String symbol) {
        this.symbol = symbol;
        this.name = name;
        this.url = url;


    }

    @Override
    public String toString() {
        return name;
    }


}