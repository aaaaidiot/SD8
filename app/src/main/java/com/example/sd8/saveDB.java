package com.example.sd8;
import android.provider.BaseColumns;

public class saveDB {
    public static abstract class FeedEntry implements BaseColumns {
        public static final String TABLE_NAME = "save";
        public static final String COLUMN_NAME_WORD = "word";
        public static final String COLUMN_NAME_TENSU = "tensu";
    }
}
