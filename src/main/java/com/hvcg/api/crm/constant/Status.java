package com.hvcg.api.crm.constant;

public enum Status {
    ACTIVE(false), IN_ACTIVE(true);

    private boolean status;

    Status(boolean status) {
        this.status = status;
    }

    public boolean getStatus() {
        return status;
    }

}
