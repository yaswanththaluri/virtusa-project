package com.maya.virtusa.virtusa;

public class userEntry {

    private String isPremium;

    private userEntry()
    {

    }

    public userEntry(String isPremium)
    {
        this.isPremium = isPremium;
    }

    public String getIsPremium() {
        return isPremium;
    }

    public void setIsPremium(String isPremium) {
        this.isPremium = isPremium;
    }
}
