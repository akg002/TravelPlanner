package in.silive.travelplanner.Hotels;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.Override;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import in.silive.travelplanner.R;


/**
 * Created by H.P on 10/10/2015.
 */
public class Hotels_Search extends AppCompatActivity implements AdapterView.OnItemClickListener, View.OnClickListener {
    String Api_key = "AIzaSyDT7HTfZS8fCk9HGqSRrtebfACIchDSOa4";
    URL H_url;
    HttpURLConnection H_connection;
    BufferedReader H_bufferedReader;
    StringBuilder H_response = new StringBuilder();
    ArrayList<String> Hotels = new ArrayList<>();
    ArrayAdapter<String> H_display;
    ListView hotel_list;
    Bundle info,checkList;
    String destination_city;
    String selected_hotel;

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        selected_hotel = ((TextView) view).getText().toString();

        Intent i=new Intent(this,in.silive.travelplanner.Checklist.class);
        info.putString("Hotel", selected_hotel);
        checkList.putBoolean("Hotels", true);
        i.putExtra("info", info);
        i.putExtra("checkList", checkList);
        startActivity(i);
        Toast.makeText(this, "Hotel Name saved.", Toast.LENGTH_LONG);
        finish();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotels_search);
        ((Button)findViewById(R.id.buttn_next)).setOnClickListener(this);
        ((Button)findViewById(R.id.buttn_back)).setOnClickListener(this);
        Intent intent = getIntent();
        info = intent.getBundleExtra("info");
        destination_city = info.getString("dest_station");
        String[] array = destination_city.split(" ");
        destination_city = array[0];
        for(int i=1;i<(array.length -1);++i){
            destination_city +=("%20"+array[i]);
        }
        checkList = intent.getBundleExtra("checkList");

        hotel_list = (ListView) findViewById(R.id.hotel_list);
        H_display = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, Hotels);
        hotel_list.setAdapter(H_display);

        new display_hotels(this).execute();
        hotel_list.setOnItemClickListener(this);
    }


    public class display_hotels extends AsyncTask<Void, Void, String> {
        ProgressDialog progressDialog;

        display_hotels(Context c) {
            this.progressDialog = new ProgressDialog(c);

        }


        @Override
        protected String doInBackground(Void[] params) {

            try {
                H_url = new URL("https://maps.googleapis.com/maps/api/place/textsearch/json?query=Hotels+in+"+destination_city+"&key="+ Api_key);
                H_connection = (HttpURLConnection) H_url.openConnection();
                Log.d("Hotels_Search","url : "+H_url);
                Log.d("display", "connection");
                H_connection.setRequestMethod("GET");
                H_connection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");

                H_connection.connect();
                H_bufferedReader = new BufferedReader(new InputStreamReader(H_connection.getInputStream()));

                String line = "";
                while ((line = H_bufferedReader.readLine()) != null) {
                    H_response.append(line + "\n");
                  //  Log.d("Hotels","Incoming "+line);

                }
            } catch (Exception e) {
                Log.d("display", "NOconnection");
                e.printStackTrace();
            }
            if (H_response.toString() != null)
                return H_response.toString();
            else
                return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setMessage("Fetching Hotels ");
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(String s)

        {
           // super.onPostExecute(s);

            parsing_Hotels(s);
            H_display.notifyDataSetChanged();
            progressDialog.dismiss();



        }
    }

    public void parsing_Hotels(String s) {
        try {
            JSONObject Hotel_object = new JSONObject(s);
            JSONArray List_of_hotels = Hotel_object.getJSONArray("results");
            for (int i = 0; i < List_of_hotels.length(); i++) {
                JSONObject place = List_of_hotels.getJSONObject(i);
                Hotels.add(place.getString("name"));

                Log.d("Hotels", "Item added");

            }
        } catch (Exception e) {
            Log.d("Hotels", "Parsing not working");
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttn_back:
                finish();
                break;
            case R.id.buttn_next:
                Toast.makeText(this, "Please select any Hotel", Toast.LENGTH_LONG).show();
                break;
        }

    }
}
