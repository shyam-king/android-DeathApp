package com.github.shyamking.deathapp.ui;

import java.io.Serializable;

public class Crime implements Serializable {
    String category;
    String location_type;
    String location_subtype;
    String month;
    String street;
    String persistent_id;
    String context;
    String outcome_status;

    public Crime() {

    }

    public Crime setCategory(String category) {
        this.category = category;
        return this;
    }

    public Crime setLocationType(String location_type) {
        this.location_type = location_type;
        return this;
    }

    public Crime setLocationSubtype(String location_subtype) {
        this.location_subtype = location_subtype;
        return this;
    }

    public Crime setMonth(String month) {
        this.month = month;
        return this;
    }

    public Crime setStreet(String street) {
        this.street = street;
        return this;
    }

    public Crime setPersistentId(String persistent_id) {
        this.persistent_id = persistent_id;
        return this;
    }

    public Crime setContext(String context) {
        this.context = context;
        return this;
    }

    public Crime setOutcomeStatus(String outcome_status) {
        this.outcome_status = outcome_status;
        return this;
    }

    public String getCategory() {
        if (category != null)
            return category;
        return "null";
    }

    public String getContext() {
        if (context != null)
            return context;
        return "null";
    }

    public String getLocation_subtype() {
        if (location_subtype != null)
            return location_subtype;
        return "null";
    }

    public String getMonth() {
        if (month != null)
            return month;
        return "null";
    }

    public String getLocation_type() {
        if (location_type != null)
            return location_type;
        return "null";
    }

    public String getOutcome_status() {
        if (outcome_status!= null) return outcome_status;
        return "null";
    }

    public String getPersistent_id() {
        if (persistent_id != null) return persistent_id;
        return "null";
    }

    public String getStreet() {
        if (street != null)
            return street;
        return "null";
    }
}