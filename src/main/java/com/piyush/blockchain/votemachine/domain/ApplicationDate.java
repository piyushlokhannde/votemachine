package com.piyush.blockchain.votemachine.domain;

import java.time.LocalDateTime;

public enum ApplicationDate {

    INSTANCE;



    private LocalDateTime fixedTime;
    private boolean isFixedTime;

    public LocalDateTime getApplicationTime() {
        if (isFixedTime) {
            return fixedTime;
        } else  {
            return LocalDateTime.now();
        }

    }


    public void setFixedTime(LocalDateTime fixedTime) {

        this.fixedTime = fixedTime;
        this.isFixedTime = true;
    }
}
