package com.example.contentprovider;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Calllog implements Serializable {
    private String number;
    private String type;
    private Date date; // Change type from String to Date
    private String duration;

    public Calllog(String number, String type, Date date, String duration) { // Change constructor parameter type
        this.number = number;
        this.type = type;
        this.date = date;
        this.duration = duration;
    }

    public Calllog() {
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getDate() { // Change return type to Date
        return date;
    }

    public void setDate(Date date) { // Change parameter type to Date
        this.date = date;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    private String getCallTypeLabel() {
        switch (type) {
            case "1":
                return "Cuộc gọi đến";
            case "2":
                return "Cuộc gọi đi";
            case "3":
                return "Cuộc gọi nhỡ";
            case "4":
                return "Thư thoại";
            case "5":
                return "Cuộc gọi bị từ chối";
            case "6":
                return "Cuộc gọi bị chặn";
            case "7":
                return "Cuộc gọi trả lời từ thiết bị khác";
            default:
                return "Không xác định";
        }
    }

    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy"); // Format to show only date
        return "Số điện thoại: " + number + "\n" +
                "Loại cuộc gọi: " + getCallTypeLabel() + "\n" +
                "Ngày gọi: " + sdf.format(date) + "\n" + // Use formatted date
                "Thời lượng: " + duration;
    }
}
