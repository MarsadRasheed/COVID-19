package com.hamlet.COVID_19;

public class ExampleItem {
    private String countryName;
    private int confirmedCases;
    private int recoveredCases;
    private int deathes;
    private int newConfirmedCases;
    private int newRecoveredCases;
    private int newDeathes;



    public ExampleItem(String countryName, int confirmedCases, int recoveredCases, int deathes, int newConfirmedCases, int newRecoveredCases, int newDeathes) {
        this.countryName = countryName;
        this.confirmedCases = confirmedCases;
        this.recoveredCases = recoveredCases;
        this.deathes = deathes;
        this.newConfirmedCases = newConfirmedCases;
        this.newRecoveredCases = newRecoveredCases;
        this.newDeathes = newDeathes;
    }

    public String getCountryName() {
        return countryName;
    }

    public int getConfirmedCases() {
        return confirmedCases;
    }

    public int getRecoveredCases() {
        return recoveredCases;
    }

    public int getDeathes() {
        return deathes;
    }

    public int getNewConfirmedCases() {
        return newConfirmedCases;
    }

    public int getNewRecoveredCases() {
        return newRecoveredCases;
    }

    public int getNewDeathes() {
        return newDeathes;
    }
}
