package in.silive.travelplanner.travelInfo;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;

import java.util.Calendar;

import in.silive.travelplanner.R;
import in.silive.travelplanner.searchTrain.LoadFile;
import in.silive.travelplanner.searchTrain.SearchTrains;

public class TravelInfo extends AppCompatActivity implements View.OnClickListener{
    AutoCompleteTextView src_station_txtView, dest_station_txtView;
    TextView dateTxtView;
    String src_station, dest_station,src_code,dest_code;
    Calendar cal;
    String day, month, year;
    Bundle info ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travel_info);
        info = new Bundle();
        
        src_station_txtView = (AutoCompleteTextView) findViewById(R.id.fromTxtView);
        dest_station_txtView = (AutoCompleteTextView) findViewById(R.id.toTxtView);
        ArrayAdapter<String> aa = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, LoadFile.getStationList(this));
        src_station_txtView.setAdapter(aa);
        dest_station_txtView.setAdapter(aa);
        dateTxtView = (TextView) findViewById(R.id.dateTxtView);

        cal = Calendar.getInstance();
        day = "" + cal.get(Calendar.DAY_OF_MONTH);
        month = "" + cal.get(Calendar.MONTH);
        if (Integer.parseInt(day) < 10)
            day = "0" + day;

        if (Integer.parseInt(month + 1) < 10)
            month = "0" +  (Integer.parseInt(month) + 1);
        else month = "" +  (Integer.parseInt(month) + 1);
        year = "" + cal.get(Calendar.YEAR);
        dateTxtView.setText(day + "-" + month + "-" + year);
        dateTxtView.setOnClickListener(this);

        ((Button)findViewById(R.id.buttn_next)).setOnClickListener(this);
        ((Button)findViewById(R.id.buttn_back)).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dateTxtView :  datePicker();
                break;
            case R.id.buttn_next : nextButtnClicked();
                break;
            case R.id.buttn_back : backButtnClicked();
        }
    }

    public void datePicker() {
        DatePicker date = new DatePicker();
        Calendar calender = Calendar.getInstance();


        Bundle args = new Bundle();
        args.putInt("year", calender.get(Calendar.YEAR));
        args.putInt("month", calender.get(Calendar.MONTH));
        args.putInt("day", calender.get(Calendar.DAY_OF_MONTH));
        date.setArguments(args);

        date.setCallBack(ondate);
        date.show(getFragmentManager(), "Date Picker");
    }

    public DatePickerDialog.OnDateSetListener ondate = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(android.widget.DatePicker view, int y, int monthOfYear, int dayOfMonth) {
            if (dayOfMonth < 10)
                day = "0" + dayOfMonth;
            else day = "" + dayOfMonth;
            if (monthOfYear + 1 < 10)
                month = "0" + (int) (monthOfYear + 1);
            else month = "" + (int) (monthOfYear + 1);
            year = ""+y;
            dateTxtView.setText(day + "-" + month + "-" + year);
        }

    };


    public void nextButtnClicked(){
        Log.d("TravelInfo","Going to SearchTrains");
        Intent next = new Intent(this, in.silive.travelplanner.searchTrain.SearchTrains.class);
        src_station = src_station_txtView.getText().toString();
        dest_station = dest_station_txtView.getText().toString();
        String[] array = src_station.split(" ");
        src_code = array[array.length - 1];
        array = dest_station.split(" ");
        dest_code = array[array.length - 1];
        info.putString("src_station",src_station);
        info.putString("src_code",src_code);
        info.putString("dest_station",dest_station);
        info.putString("dest_code",dest_code);
        info.putString("day",day);
        info.putString("month",month);
        info.putString("year",year);
        next.putExtra("info", info);
        startActivity(next);

    }

    public void backButtnClicked(){
        finish();
    }
}
