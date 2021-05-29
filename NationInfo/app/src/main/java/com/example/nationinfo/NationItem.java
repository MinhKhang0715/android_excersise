package com.example.nationinfo;

public class NationItem {
    private final String _nationCode;
    private final String _nationName;
    private final int _population;
    private final int _areaInSqKm;

    public NationItem(String nationCode, String nationName, int population, int areaInSqKm) {
        _nationCode = nationCode;
        _nationName = nationName;
        _population = population;
        _areaInSqKm = areaInSqKm;
    }

    public String getNationCode() {
        return _nationCode;
    }
    public String getNationName() {
        return _nationName;
    }

    public int getPopulation() {
        return _population;
    }
    public int getAreaInSqKm() {
        return _areaInSqKm;
    }
}
