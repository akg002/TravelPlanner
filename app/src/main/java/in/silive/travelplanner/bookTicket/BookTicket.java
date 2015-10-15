package in.silive.travelplanner.bookTicket;

import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import in.silive.travelplanner.Checklist;
import in.silive.travelplanner.FetchData;
import in.silive.travelplanner.Interface.canDisplayTrainSeats;

import in.silive.travelplanner.R;
import in.silive.travelplanner.searchTrain.Train;

public class BookTicket extends AppCompatActivity implements canDisplayTrainSeats,AdapterView.OnItemSelectedListener,View.OnClickListener{
    String src_station,dest_station,day,year,month,trainName;
    int trainNo;
    Train train;
    TextView TextView_src,TextView_dest,TextView_date,TextView_avail_seats_SL,TextView_avail_seats_3AC,TextView_avail_seats_2AC;
    String[] train_class = {"SL","3A","2A","CC"};
    Spinner spinner_train_class ;
    ArrayAdapter arrayAdapter_train_class;
    FetchData.fetchTrainInfo query ;
    Bundle info;
    boolean seats_available;
    Bundle loc;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_ticket);
        try {

            Intent i = getIntent();
            info = i.getBundleExtra("info");
            trainName = info.getString("Train_Name");
            trainNo = Integer.parseInt(info.getString("Train_No"));

            src_station = info.getString("src_station");
            dest_station = info.getString("dest_station");
            day = info.getString("day");
            month = info.getString("month");
            year = info.getString("year");
            train = new Train();
            train.setName(trainName);
            train.setNo(trainNo);
        }catch (Exception e){
            Log.d("App Log","Coudn't get intent");
            Toast.makeText(this,"Coudnt get seats info",Toast.LENGTH_LONG);
            finish();
        }
        ((TextView) findViewById(R.id.TextView_src)).setText(src_station);
        ((TextView) findViewById(R.id.TextView_dest)).setText(dest_station);
        ((TextView) findViewById(R.id.TextView_date)).setText(day+" - "+month+" - "+year);
        ((TextView) findViewById(R.id.TextView_train)).setText(trainNo+" - "+trainName);
        spinner_train_class = (Spinner)findViewById(R.id.spinner_train_class);
        arrayAdapter_train_class = new ArrayAdapter(this,android.R.layout.simple_spinner_item,train_class);
        arrayAdapter_train_class.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner_train_class.setAdapter(arrayAdapter_train_class);
        spinner_train_class.setOnItemSelectedListener(this);
        ((Button)findViewById(R.id.buttn_next)).setOnClickListener(this);
        ((Button)findViewById(R.id.buttn_back)).setOnClickListener(this);
    }

    @Override
    public void setTrainSeats(String seats,boolean available) {
        if(available) {
            ((TextView) findViewById(R.id.TextView_avail_seat)).setText("" + seats);
        }else {
            ((TextView) findViewById(R.id.TextView_avail_seat)).setText("No available seats");
        }
        seats_available=available;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttn_next : nextButtnClicked();
                break;
            case R.id.buttn_back : backButtnClicked();
                break;

        }
    }

    public void nextButtnClicked(){
        if (seats_available) {
            Toast.makeText(this, "Ticket Booked .", Toast.LENGTH_LONG).show();
            Intent i = new Intent(this, Checklist.class);
            Bundle checkList = new Bundle();
            checkList.putBoolean("Tickets",true);
            checkList.putBoolean("Hotels", false);
            checkList.putBoolean("Fav",false);
            i.putExtra("info", info);
            i.putExtra("checkList", checkList);
            startActivity(i);
            finish();
        }
        else
            Toast.makeText(this, "Ticket Booked .", Toast.LENGTH_LONG).show();

    }

    public void backButtnClicked(){
        finish();
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String train_class = ((TextView)view).getText().toString();
        query = new FetchData.fetchTrainInfo(this,train,day,month,year,src_station,dest_station,train_class);

        query.execute();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
