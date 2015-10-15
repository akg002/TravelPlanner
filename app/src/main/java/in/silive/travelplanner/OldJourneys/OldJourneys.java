package in.silive.travelplanner.OldJourneys;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import in.silive.travelplanner.DataBaseHelper;
import in.silive.travelplanner.R;

public class OldJourneys extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
ListView journeyList;
    AdapterForShortJourney listAdapter;
    DataBaseHelper dataBaseHelper;
    Cursor cursor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_old_journeys);
        dataBaseHelper = new DataBaseHelper(this);
        cursor = dataBaseHelper.getShortJourney();
        cursor.moveToFirst();
        journeyList = (ListView)findViewById(R.id.journey_list);
        listAdapter = new AdapterForShortJourney(this,cursor);
        journeyList.setAdapter(listAdapter);

        ((Button)findViewById(R.id.buttn_next)).setEnabled(false);
        ((Button)findViewById(R.id.buttn_back)).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttn_back:
                finish();
                break;

        }

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent=new Intent(this,in.silive.travelplanner.Detailed_view.class);

    }
}
