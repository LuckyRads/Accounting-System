package com.lucky.accountingsystem.model;

import java.io.Serializable;

public class Expense implements Serializable {

    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
