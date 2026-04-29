package com.example.ternaksmart;

public class Vaksin {
    private String nama;
    private String targetUmur;
    private boolean isDone;

    public Vaksin(String nama, String targetUmur, boolean isDone) {
        this.nama = nama;
        this.targetUmur = targetUmur;
        this.isDone = isDone;
    }

    public String getNama() { return nama; }
    public void setNama(String nama) { this.nama = nama; }
    
    public String getTargetUmur() { return targetUmur; }
    public void setTargetUmur(String targetUmur) { this.targetUmur = targetUmur; }

    public boolean isDone() { return isDone; }
    public void setDone(boolean done) { isDone = done; }
}