package com.dheia.finalyear;

/**
 * Created by Dheia on 16/02/2019.
 */

public class CoinItemChangelly {

    private String coinSymbol,coinName,coinImage,coinId;



    public String getCoinSymbol() {
        return coinSymbol;
    }
    public String getCoinId() {
        return coinId;
    }

    public void setCoinSymbol(String coinSymbol) {
        this.coinSymbol = coinSymbol;
    }

    public void setcoinId(String coinId) {
        this.coinId = coinId;
    }


    public String getCoinName() {
        return coinName;
    }

    public void setCoinName(String coinName) {
        this.coinName = coinName;
    }

    public String getCoinImage() {
        return coinImage;
    }

    public void setCoinImage(String coinImage) {
        this.coinImage = coinImage;
    }

    public CoinItemChangelly(String coinSymbol, String coinImage, String coinName,String coinId) {
        this.coinName = coinName;
        this.coinImage = coinImage;
        this.coinSymbol = coinSymbol;
        this.coinId = coinId;
    }


    @Override
    public String toString() {
        return coinSymbol;
    }

}
