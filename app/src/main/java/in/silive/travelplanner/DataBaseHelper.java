package in.silive.travelplanner;

/**
 * Created by AKG 002 on 10-10-2015.
 */

import android.database.Cursor;



        import android.content.ContentValues;
        import android.content.Context;
        import android.database.Cursor;
        import android.database.sqlite.SQLiteDatabase;
        import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by H.P on 10/8/2015.
 */
public class DataBaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Travel";
    public static final String J_Id = "";
    public static final String Saved_places = "";
    public static final String Source = "";
    public static final String Destination = "";
    public static final String Date = "";
    public static final String Train_Name = "";
    public static final String Train_Number = "";
    public static final String T_Arrival = "";
    public static final String T_Departure = "";
    public static final String Hotel = "";
    public static final String Favorites = "";
    public static final Integer DB_Version = 1;

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DB_Version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("Create Table Journey" +
                "(J_Id Integer AUTO_INCREMENT," +
                "Source varchar," +
                "Destination varchar," +
                "Date varchar," +
                "Train_Name varchar," +
                "Train_Number INTEGER," +
                "Train_Arrival varchar," +
                "Train_Departure varchar," +
                "Hotel varchar," +
                "Favorites varchar ,Primary Key(J_Id));");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    static int count = 1;

    //required function to assign id's automatically;
    public void Insert_Data_Route(Integer id, String source, String destination, String date) {
        id = count++;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("J_Id", id);
        contentValues.put("Source", source);
        contentValues.put("Destination", destination);
        contentValues.put("Date", date);

    }

    public void Insert_Data_Places(Integer id, String place_name) {
        id = count;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Favorites", place_name);
        contentValues.put("J_Id", id);
        db.update("Journey", contentValues, "id = ? ", new String[]{Integer.toString(id)});
    }

    public void Insert_Data_Hotels(Integer id, String Hotel) {
        id = count;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Hotel", Hotel);
        contentValues.put("J_Id", id);
        db.update("Journey", contentValues, "id = ? ", new String[]{Integer.toString(id)});
    }

    public void Insert_Data_Trains(Integer id, String name, String t_number, String arrival, String departre) {
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

    public void Update_Record(Integer id, String source, String destination, String date)//Updation -done in the Insert functions except Route function
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
        Cursor res = db.rawQuery("select * from Journey where J_Id= " + id +"",null);
        return res;
    }

    public Cursor getShortJourney(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select Date,Source,Destination from Journey ;",null);
        return cursor;
    }

    public Cursor getDetailedJourney(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from Journey ;",null);
        return cursor;
    }
}


