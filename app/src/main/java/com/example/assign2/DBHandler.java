package com.example.assign2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBHandler extends SQLiteOpenHelper {

    // creating a constant variables for our database.
    // below variable is for our database name.
    private static final String DB_NAME = "bookingdb";

    // below int is our database version
    private static final int DB_VERSION = 1;

    // below variable is for our table name.
    private static final String TABLE_NAME = "mybooking";


    private static final String ID_COL = "id";


    private static final String NAME_COL = "Name";


    private static final String ADDRESS_COL = "Address";

    private static final String PHONE_COL = "Phone";

    private static final String NOTE_COL = "Note";

    private static final String DATE_COL = "Date";

    private static final String TIME_COL = "Time";


    private static final String DELIVERY_COL = "Delivery";

    private static final String RandomString_COL = "String2";

    // creating a constructor for our database handler.
    public DBHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    // below method is for creating a database by running a sqlite query
    @Override
    public void onCreate(SQLiteDatabase db) {
        // on below line we are creating
        // an sqlite query and we are
        // setting our column names
        // along with their data types.
        String query = "CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + NAME_COL + " TEXT,"
                + ADDRESS_COL + " TEXT,"
                + PHONE_COL  + " TEXT,"
                + DATE_COL  + " TEXT,"
                + TIME_COL  + " TEXT,"
                + DELIVERY_COL  + " TEXT,"
                + NOTE_COL  + " TEXT,"
                + RandomString_COL + " TEXT)";


        // at last we are calling a exec sql
        // method to execute above sql query
        db.execSQL(query);
    }

    // this method is use to add new course to our sqlite database.
    public void addNewBookings(String Name, String Address, String Date, String Time, String Phone,
                             String Note, String Delivery, String String2) {

        // on below line we are creating a variable for
        // our sqlite database and calling writable method
        // as we are writing data in our database.
        SQLiteDatabase db = this.getWritableDatabase();

        // on below line we are creating a
        // variable for content values.
        ContentValues values = new ContentValues();

        // on below line we are passing all values
        // along with its key and value pair.
        values.put(NAME_COL, Name);
        values.put(ADDRESS_COL, Address);
        values.put(PHONE_COL, Phone);
        values.put(DATE_COL, Date);
        values.put(TIME_COL, Time);
        values.put(DELIVERY_COL, Delivery);
        values.put(NOTE_COL, Note);
        values.put(RandomString_COL, String2);





        // after adding all values we are passing
        // content values to our table.
        db.insert(TABLE_NAME, null, values);

        // at last we are closing our
        // database after adding database.
        db.close();
    }

    // we have created a new method for reading all the courses.
    public ArrayList<BookingModal> readBookings() {
        // on below line we are creating a
        // database for reading our database.
        SQLiteDatabase db = this.getReadableDatabase();

        // on below line we are creating a cursor with query to read data from database.
        Cursor cursorBookings = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);

        // on below line we are creating a new array list.
        ArrayList<BookingModal> BookingModalArrayList = new ArrayList<>();

        // moving our cursor to first position.
        if (cursorBookings.moveToFirst()) {
            do {
                // on below line we are adding the data from cursor to our array list.
             BookingModalArrayList.add(new BookingModal
                        (cursorBookings.getString(0),
                                cursorBookings.getString(1),
                                cursorBookings.getString(2),
                                cursorBookings.getString(3),
                                cursorBookings.getString(4),
                                cursorBookings.getString(5),
                                cursorBookings.getString(6),
                                cursorBookings.getString(7)));
            } while (cursorBookings.moveToNext());
            // moving our cursor to next.
        }
        // at last closing our cursor
        // and returning our array list.
        cursorBookings.close();
        return BookingModalArrayList;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // this method is called to check if the table exists already.
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}

