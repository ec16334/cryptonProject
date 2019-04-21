package com.dheia.finalyear;

/**
 * Created by Dheia on 16/01/2019.
 */

public class Coin {

    private String name, price, ranking, change24h, marketcap;


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

    public String getRanking() {
        return ranking;
    }

    public void setRanking(String logoUrl) {
        this.ranking = ranking;
    }

    public String getChange24h() {
        return change24h;
    }

    public void setChange24h(String change24h) {
        this.change24h = change24h;
    }

    public String getMarketcap() {
        return marketcap;
    }

    public void setMarketcap(String marketcap) {
        this.marketcap = marketcap;
    }

    public Coin(String name, String price, String ranking, String change24h, String marketcap) {
        this.name = name;
        this.price = price;
        this.ranking = ranking;
        this.change24h = change24h;
        this.marketcap = marketcap;
    }
}





