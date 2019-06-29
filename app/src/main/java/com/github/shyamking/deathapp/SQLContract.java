package com.github.shyamking.deathapp;

import android.provider.BaseColumns;

import java.io.OutputStream;

public class SQLContract {
    private SQLContract() {}

    public static class CrimeFavourites implements BaseColumns {
        public static final String Street = "street";
        public static final String Context = "context";
        public static final String Persistent_key = "persistent_key";
        public static final String Location_type = "location_type";
        public static final String Location_subtype = "location_subtype";
        public static final String Month = "month";
        public static final String Outcome_status = "outcome_status";
        public static final String Category = "category";
        public static final String Latitude = "latitude";
        public static final String Longitude = "longitude";
        public static final String Id = "id";

        public static final String TableName = "CrimeFavourites";

        public static final String CREATE_QUERY = "CREATE TABLE " + TableName + " (" +
                _ID + " INTEGER PRIMARY KEY, " +
                Id + " TEXT, " +
                Street + " TEXT, " +
                Context + " TEXT, " +
                Persistent_key + " TEXT, " +
                Location_subtype + " TEXT, " +
                Location_type + " TEXT, " +
                Month + " TEXT, " +
                Outcome_status + " TEXT, " +
                Category + " TEXT," +
                Latitude + " REAL," +
                Longitude + " REAL)";

        public static final String DELETE_QUERY = "DROP TABLE IF EXISTS " + TableName + ";";
    }
}
