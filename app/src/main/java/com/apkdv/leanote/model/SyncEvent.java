package com.apkdv.leanote.model;


public class SyncEvent {
    private boolean isSucceed;

    public SyncEvent(boolean isSucceed) {
        this.isSucceed = isSucceed;
    }

    public boolean isSucceed() {
        return isSucceed;
    }
}
