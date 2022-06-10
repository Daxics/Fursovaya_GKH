package com.example.kursovaya;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "contactDb";
    public static final String TABLE_CALC = "calculate";
    public static final String TABLE_SCAN = "scan";

    //Общие поля
    public static final String KEY_ID = "_id";
    public static final String KEY_SUM = "sum";

    // Поля для БД калькулятора
    public static final String KEY_OTOPL = "otopl";
    public static final String KEY_WODOS = "wodos";
    public static final String KEY_GAZ = "gaz";
    public static final String KEY_ELECT = "elect";
    public static final String KEY_OTHER = "other";
    public static final String KEY_DISC = "disc";

    // Calc table create statement
    private static final String CREATE_TABLE_CALC = "CREATE TABLE "
            + TABLE_CALC + "(" + KEY_ID + " INTEGER PRIMARY KEY," +
            KEY_OTOPL + " TEXT," +
            KEY_WODOS + " TEXT," +
            KEY_GAZ + " TEXT," +
            KEY_ELECT + " TEXT," +
            KEY_OTHER + " TEXT," +
            KEY_DISC + " TEXT," +
            KEY_SUM + " TEXT" + ")";

    //Поля для БД сканера
    public static final String KEY_FIO = "FIO";
    public static final String KEY_ADRESS = "Adress";
    public static final String KEY_BIC = "BIC";
    public static final String KEY_INN = "payeeINN";
    public static final String KEY_PERSONAL_ACC = "PersonalAcc";
    public static final String KEY_PERS_ACC = "PersAcc";
    public static final String KEY_PURPOSE = "Purpose";
    public static final String KEY_COR_PER = "COR_PER";

    // Scan table create statement
    private static final String CREATE_TABLE_SCAN =  "CREATE TABLE "
            + TABLE_SCAN + "(" + KEY_ID + " INTEGER PRIMARY KEY," +
            KEY_FIO + " TEXT," +
            KEY_ADRESS + " TEXT," +
            KEY_BIC + " TEXT," +
            KEY_INN + " TEXT," +
            KEY_PERSONAL_ACC + " TEXT," +
            KEY_PERS_ACC + " TEXT," +
            KEY_PURPOSE + " TEXT," +
            KEY_COR_PER + " TEXT," +
            KEY_SUM + " TEXT" + ")";


    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_CALC);
        db.execSQL(CREATE_TABLE_SCAN);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CALC);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SCAN);

        // create new tables
        onCreate(db);
    }
}
