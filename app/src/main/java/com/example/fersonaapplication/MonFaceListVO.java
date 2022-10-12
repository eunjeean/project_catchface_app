package com.example.fersonaapplication;

public class MonFaceListVO {
    private String monList;
    private int imgId;

    public String getMonList() {
        return monList;
    }

    public void setMonList(String monList) {
        this.monList = monList;
    }

    public int getImgId() {
        return imgId;
    }

    public void setImgId(int imgId) {
        this.imgId = imgId;
    }

    public MonFaceListVO(String num, int imgId){
        this.monList = num;
        this.imgId = imgId;
    }
}
