package com.xp.dc.xpdc.bean;

public class CarInfo {
    private String carName;
    private int id;
    private int carIcon;
    private String price;
    private boolean isChecked;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public String getCarName() {
        return carName;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }

    public int getCarIcon() {
        return carIcon;
    }

    public void setCarIcon(int carIcon) {
        this.carIcon = carIcon;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
