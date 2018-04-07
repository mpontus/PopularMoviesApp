package com.mpontus.popularmoviesapp.domain;

public enum MovieSourceType {
    POPULAR(1), TOP_RATED(2);

    private int value;

    MovieSourceType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static MovieSourceType fromValue(int v) {
        switch (v) {
            case 1:
                return POPULAR;
            case 2:
                return TOP_RATED;
            default:
                return POPULAR;
        }
    }
}
