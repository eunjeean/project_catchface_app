package com.example.fersonaapplication;

public class MyReportListVO {
    private String wantedCate; // 범죄유형
    private String reportDate; // 신고날짜

    public String getWantedCate() {
        return wantedCate;
    }

    public void setWantedCate(String wantedCate) {
        this.wantedCate = wantedCate;
    }

    public String getReportDate() {
        return reportDate;
    }

    public void setReportDate(String reportDate) {
        this.reportDate = reportDate;
    }

    public MyReportListVO(String wantedCate, String reportDate) {
        this.wantedCate = wantedCate;
        this.reportDate = reportDate;
    }
}
