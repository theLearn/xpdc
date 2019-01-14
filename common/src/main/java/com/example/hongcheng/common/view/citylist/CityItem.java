package com.example.hongcheng.common.view.citylist;

/**
 * Created by next on 2016/3/24.
 */
public class CityItem {

    private String name;
    private String pinyin;

    public CityItem(String name, String pinyin) {
        this.name = name;
        this.pinyin = pinyin;
    }

    public CityItem() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }
}
