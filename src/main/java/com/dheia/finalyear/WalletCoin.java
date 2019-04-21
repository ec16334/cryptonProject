package com.dheia.finalyear;

/**
 * Created by Dheia on 27/03/2019.
 */

public class WalletCoin {

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

    public String getPublicKeyy() {
        return publickey1;
    }

    public void setPublicKeyy(String symbol) {
        publickey1 = symbol;
    }

    private String name, url, symbol, publickey1 ;

    public WalletCoin(String name, String url, String symbol, String publicKey) {
        this.symbol = symbol;
        this.name = name;
        this.url = url;
        publickey1=publicKey;


    }

    @Override
    public String toString() {
        return name;
    }



}
