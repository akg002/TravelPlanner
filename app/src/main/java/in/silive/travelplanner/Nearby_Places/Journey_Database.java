package in.silive.travelplanner.Nearby_Places;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by H.P on 10/8/2015.
 */
public class Journey_Database extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Local_Attractions";
    public static final String J_Id = "";
    public static final String Saved_places = "";
    public static final String Source = "";
    public static final String Destination = "";
    public static final String Date = "";
    public static final String Train_Name = "";
    public static final String Train_Number = "";
    public static final String T_Arrival = "";
    public static final String T_Departure = "";
    public static final String Hotel_Name = "";
    public static final String Favorites = "";
    public static final Integer DB_Version = 1;

    Journey_Database(Context context) {
        super(context, DATABASE_NAME, null, DB_Version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("Create Table Journey" +
                "(J_Id Integer Primary Key," +
                "Source String," +
                "Destination String," +
                "Date String," +
                "Train_Name String," +
                "Train_Number String," +
                "Train_Arrival String," +
                "Train_Departure String," +
                "Hotel_Name String," +
                "Favorites String )");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    static int count = 1;
    int id;

    //required function to assign id's automatically;
    public void Insert_Data_Route( String source, String destination, String date) {
        id = count++;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("J_Id", id);
        contentValues.put("Source", source);
        contentValues.put("Destination", destination);
        contentValues.put("Date", date);

    }

    public void Insert_Data_Places( String place_name) {
        id = count;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Favorites", place_name);
        contentValues.put("J_Id", id);
        db.update("Journey", contentValues, "id = ? ", new String[]{Integer.toString(id)});
    }

    public void Insert_Data_Hotels( String Hotel) {
        id = count;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Hotel_Name", Hotel);
        contentValues.put("J_Id", id);
        db.update("Journey", contentValues, "id = ? ", new String[]{Integer.toString(id)});
    }

    public void Insert_Data_Trains( String name, String t_number, String arrival, String departre) {
        id = count;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("J_Id", id);
        contentValues.put("Train_Name", name);
        contentValues.put("Train_Number", t_number);
        contentValues.put("Train_Depature", departre);
        contentValues.put("Train_Arrival", arrival);
        db.update("Journey", contentValues, "id = ? ", new String[]{Integer.toString(id)});
    }

    public void Update_Record( String source, String destination, String date)//Updation -done in the Insert functions except Route function
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("J_Id", id);
        contentValues.put("Source", source);
        contentValues.put("Destination", destination);
        contentValues.put("Date", date);
        db.update("Journey", contentValues, "id = ? ", new String[]{Integer.toString(id)});
    }

    public Cursor getData(Integer id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("city_list * from Journey where J_Id=" + id + "", null);
        return res;
    }
}
