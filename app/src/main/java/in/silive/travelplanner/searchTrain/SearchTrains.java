package in.silive.travelplanner.searchTrain;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

import in.silive.travelplanner.FetchData;
import in.silive.travelplanner.R;
import in.silive.travelplanner.Interface.canDisplayTrainAdapter;


public class SearchTrains extends AppCompatActivity implements View.OnClickListener,AdapterView.OnItemClickListener,canDisplayTrainAdapter{

    ListView disp_list;
    Button buttn;
    TrainListCustomAdapter trainAdapter ;
    String src_station, dest_station,src_code,dest_code;
    String day, month, year;
    FetchData.fetchTrains query ;
    Bundle info;
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Train train = query.getTrainList().get(position);

        Intent i = new Intent(this,in.silive.travelplanner.bookTicket.BookTicket.class);
        info.putString("Train_Name", train.getName());
        info.putString("Train_No", "" + train.getNo());
        info.putString("Train_Arrival",train.getArrTime());
        info.putString("Train_Departure",train.getDepTime());
        i.putExtra("info",info);
        startActivity(i);

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_trains);
        disp_list = (ListView) findViewById(R.id.disp);

        disp_list.setOnItemClickListener(this);
        Intent i = getIntent();
        info = i.getBundleExtra("info");
        for (String key : info.keySet()){
            Log.d("SearchTrains:",key+"-"+info.getString(key));
        }

        src_code = info.getString("src_code",src_code);
        dest_code = info.getString("dest_code",dest_code);
        day = info.getString("day", day);
        month = info.getString("month", month);
        query = new FetchData.fetchTrains(this, src_code, dest_code, day, month);
        query.execute();
        ((Button)findViewById(R.id.buttn_next)).setOnClickListener(this);
        ((Button)findViewById(R.id.buttn_back)).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttn_next : nextButtnClicked();
                break;
            case R.id.buttn_back : backButtnClicked();

        }


    }

    public void nextButtnClicked(){

        Toast.makeText(this,"Select a train.",Toast.LENGTH_LONG).show();

    }

    public void backButtnClicked(){
        finish();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_train_api_test, menu);
        return true;
    }




    @Override
    public void displayTrainList(ArrayList<Train> trainArrayList) {
        trainAdapter = new TrainListCustomAdapter(this,trainArrayList);
        disp_list.setAdapter(trainAdapter);
        trainAdapter.notifyDataSetChanged();
    }
}
