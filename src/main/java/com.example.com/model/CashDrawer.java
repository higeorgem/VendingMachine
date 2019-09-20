package com.example.com.model;

public class CashDrawer {
    private long id;
    private Denomination denomination;
    private int count;

    public CashDrawer(long id, Denomination denomination, int count) {
        this.id = id;
        this.denomination = denomination;
        this.count = count;
    }

    public CashDrawer() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Denomination getDenomination() {
        return denomination;
    }

    public void setDenomination(Denomination denomination) {
        this.denomination = denomination;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
