package in.silive.travelplanner;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Detailed_view extends AppCompatActivity {
    Cursor cursor;
    Bundle info;
    Integer pos;
    TextView date_txtview;
    TextView source;
    TextView destination;
    TextView train;
    TextView hotel;
    TextView fav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_view);

        display_details();
    }

    public void display_details() {
        info = getIntent().getBundleExtra("position");
        pos = Integer.parseInt(info.getString("position"));
        cursor.moveToPosition(pos);
        date_txtview = (TextView) findViewById(R.id.date_txtview);
        source = (TextView) findViewById(R.id.source);
        destination = (TextView) findViewById(R.id.destination);
        train = (TextView) findViewById(R.id.train);
        hotel = (TextView) findViewById(R.id.hotel);
        fav = (TextView) findViewById(R.id.fav);
        date_txtview.setText(cursor.getString(0));
        source.setText(cursor.getString(1));
        destination.setText(cursor.getString(2));
        train.setText(cursor.getString(3));
        hotel.setText(cursor.getString(6));
        fav.setText(cursor.getString(7));


    }

}
