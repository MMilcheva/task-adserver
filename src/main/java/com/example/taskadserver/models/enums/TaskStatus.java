package com.example.taskadserver.models.enums;

public enum TaskStatus {
    PENDING("Pending"),
    COMPLETED("Completed"),
    DISCARDED("Discarded");


    private final String displayStatus;

    TaskStatus(String displayStatus) {
        this.displayStatus = displayStatus;
    }

    public String getDisplayStatus() {
        return displayStatus;
    }
}

