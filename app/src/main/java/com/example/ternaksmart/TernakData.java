package com.example.ternaksmart;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "ternak_data")
public class TernakData {
    @PrimaryKey(autoGenerate = true)
    private int id;
    
    private String nama;
    private double berat;
    private String photoPath;
    private double latitude;
    private double longitude;
    private long timestamp;

    public TernakData(String nama, double berat, String photoPath, double latitude, double longitude, long timestamp) {
        this.nama = nama;
        this.berat = berat;
        this.photoPath = photoPath;
        this.latitude = latitude;
        this.longitude = longitude;
        this.timestamp = timestamp;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNama() { return nama; }
    public void setNama(String nama) { this.nama = nama; }

    public double getBerat() { return berat; }
    public void setBerat(double berat) { this.berat = berat; }

    public String getPhotoPath() { return photoPath; }
    public void setPhotoPath(String photoPath) { this.photoPath = photoPath; }

    public double getLatitude() { return latitude; }
    public void setLatitude(double latitude) { this.latitude = latitude; }

    public double getLongitude() { return longitude; }
    public void setLongitude(double longitude) { this.longitude = longitude; }

    public long getTimestamp() { return timestamp; }
    public void setTimestamp(long timestamp) { this.timestamp = timestamp; }
}