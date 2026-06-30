package com.example.ternaksmart;

public class Vaksin {
    private String nama;
    private int targetHari;
    private boolean isDone;

    public Vaksin(String nama, int targetHari, boolean isDone) {
        this.nama = nama;
        this.targetHari = targetHari;
        this.isDone = isDone;
    }

    public String getNama() { return nama; }
    public void setNama(String nama) { this.nama = nama; }
    
    public int getTargetHari() { return targetHari; }
    public void setTargetHari(int targetHari) { this.targetHari = targetHari; }
    
    public String getTargetUmurFormatted() { return "Hari ke-" + targetHari; }

    public boolean isDone() { return isDone; }
    public void setDone(boolean done) { isDone = done; }
}