package com.example.kursovaya.History.Calc;

public class CalcItem {

    private String item_title;
    private String key_id;
    private String sum;

    public CalcItem() {
    }

    public CalcItem(String item_title, String key_id, String sum) {
        this.item_title = item_title;
        this.key_id = key_id;
        this.sum = sum;
    }

    public String getItem_title() {
        return item_title;
    }

    public void setItem_title(String item_title) {
        this.item_title = item_title;
    }

    public String getKey_id() {
        return key_id;
    }

    public void setKey_id(String key_id) {
        this.key_id = key_id;
    }

    public String getItem_sum() {
        return sum;
    }

    public void setItem_image(int item_image) {
        this.sum = sum;
    }
}
