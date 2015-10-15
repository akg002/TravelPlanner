package in.silive.travelplanner;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class Central extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_central);
        ((TextView)findViewById(R.id.newJourney)).setOnClickListener(this);
        ((TextView)findViewById(R.id.oldJourneys)).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.newJourney : startActivity(new Intent(this,in.silive.travelplanner.travelInfo.TravelInfo.class));
                break;
            case R.id.oldJourneys : startActivity(new Intent(this,in.silive.travelplanner.OldJourneys.OldJourneys.class));
        }
    }
}
