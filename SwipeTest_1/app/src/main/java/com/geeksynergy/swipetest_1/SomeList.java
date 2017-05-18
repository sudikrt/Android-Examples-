package com.geeksynergy.swipetest_1;

/**
 * Created by Foolish_Guy on 5/18/2017.
 */

public class SomeList {
    private String data1;
    private String data2;

    public SomeList() {
        data1 = "";
        data2 = "";
    }
    public SomeList (String data1) {
        this.data1 = data1;
    }

    public String getData1() {
        return data1;
    }

    public void setData1(String data1) {
        this.data1 = data1;
    }

    public String getData2() {
        return data2;
    }

    public void setData2(String data2) {
        this.data2 = data2;
    }
}
