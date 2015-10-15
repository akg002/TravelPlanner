package in.silive.travelplanner;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Checklist extends AppCompatActivity implements View.OnClickListener{
    Bundle checkList,info;
    Intent i;
    boolean allItemChecked;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checklist);
        Log.d("CheckList","Reached here");
        Intent i = getIntent();
        checkList = i.getBundleExtra("checkList");
        info = i.getBundleExtra("info");
        allItemChecked = check(checkList);
        ((TextView)findViewById(R.id.ticket)).setOnClickListener(this);
        ((TextView)findViewById(R.id.hotel)).setOnClickListener(this);
        ((TextView)findViewById(R.id.fav)).setOnClickListener(this);
        ((Button)findViewById(R.id.saveBttn)).setOnClickListener(this);

    }

    public boolean check(Bundle checkList){
        if (checkList.getBoolean("Tickets")){
            ((ImageView)findViewById(R.id.ticket_check)).setImageResource(R.drawable.checked);
        }
        else {
            ((ImageView) findViewById(R.id.ticket_check)).setImageResource(R.drawable.unchecked);
            return false;
        }
        if (checkList.getBoolean("Hotels")){
            ((ImageView)findViewById(R.id.hotel_check)).setImageResource(R.drawable.checked);
        }
        else {
            ((ImageView) findViewById(R.id.hotel_check)).setImageResource(R.drawable.unchecked);
            return false;
        }
        if (checkList.getBoolean("Fav")){
            ((ImageView)findViewById(R.id.fav_check)).setImageResource(R.drawable.checked);
        }
        else {
            ((ImageView) findViewById(R.id.fav_check)).setImageResource(R.drawable.unchecked);
            return false;
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ticket:finish();
                break;
            case R.id.hotel :   i = new Intent(this,in.silive.travelplanner.Hotels.Hotels_Search.class);
                i.putExtra("info",info);
                i.putExtra("checkList",checkList);
                startActivity(i);
                Log.d("CheckList", "Calling Hotel_Search.class");
                break;
            case R.id.fav :   i = new Intent(this,in.silive.travelplanner.Nearby_Places.Search_Nearby_Places.class);
                i.putExtra("info",info);
                i.putExtra("checkList",checkList);
                startActivity(i);
                Log.d("CheckList", "Calling Search_Nearby.class");
                break;
            case R.id.saveBttn: save();
                break;
        }
    }

    public void save(){
        if (allItemChecked){
            DataBaseHelper dbHelper = new DataBaseHelper(this);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            for (String i : info.getStringArray("Fav"))
            {
                Log.d("CheckList",i+" ");
            }

            db.execSQL("INSERT INTO JOURNEY VALUES (null,\"" +
                            info.get("src_station") + "\",\"" +
                            info.get("dest_station") + "\",\"" +
                            info.get("day") + "-" + info.get("month") + "-" + info.get("year") + "\",\"" +
                            info.getString("Train_Name") + "\",\"" +
                            info.getString("Train_No") + "\",\"" +
                            info.getString("Train_Arrival") + "\",\"" +
                            info.getString("Train_Departure") + "\",\"" +
                            info.getString("Hotel") + "\",\"" +
                            getString(info.getStringArray("Fav")) + "\");"

            );


            Toast.makeText(this,"Journey Saved",Toast.LENGTH_LONG).show();
            Intent i = new Intent(this,Central.class);
            startActivity(i);
            finish();
        }
        else {
            Toast.makeText(this,"All items not checked",Toast.LENGTH_LONG).show();
        }
    }

    String getString(String[] list){
        String codedArray="";
        for (String k : list)
        {
            codedArray = k+"%";
        }
        return codedArray;
    }
}
