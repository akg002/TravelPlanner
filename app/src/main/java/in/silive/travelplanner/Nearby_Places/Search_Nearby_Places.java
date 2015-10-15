package in.silive.travelplanner.Nearby_Places;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


import in.silive.travelplanner.R;


public class Search_Nearby_Places extends AppCompatActivity implements View.OnClickListener {
    String Api_key = "AIzaSyDT7HTfZS8fCk9HGqSRrtebfACIchDSOa4";
    URL url;
    HttpURLConnection connection;
    BufferedReader bufferedReader;
    StringBuilder response = new StringBuilder();
    ArrayList<String> local_attractions = new ArrayList<>();
    ListView local_display;
    Nearby_places_Custom_Adapter nearbyplacesCustom_adapter_for_places;
    Bundle info,checkList;
    String dest_city;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ((Button)findViewById(R.id.buttn_next)).setOnClickListener(this);
        ((Button)findViewById(R.id.buttn_back)).setOnClickListener(this);
        local_display = (ListView) findViewById(R.id.local_attractions_disp);

        try {
            Intent intent = getIntent();
            info = intent.getBundleExtra("info");
            checkList = intent.getBundleExtra("checkList");
            dest_city = info.getString("dest_station");
            String[] array = dest_city.split(" ");
            dest_city = array[0];
            for(int i=1;i<(array.length -1);++i){
                dest_city +=("%20"+array[i]);
            }
        } catch (Exception e) {
            Log.d("Search_Nearby_Places", "Error in getting intent");
        }

        new fetch_data(this).execute();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.buttn_next:
                Intent intent = new Intent(this, in.silive.travelplanner.Checklist.class);
                info.putStringArray("Fav", nearbyplacesCustom_adapter_for_places.get_fav_places());
                checkList.putBoolean("Fav", true);
                intent.putExtra("info", info);
                intent.putExtra("checkList",checkList);
                startActivity(intent);
                break;
            case R.id.buttn_back:finish();
                break;
        }

    }


    public class fetch_data extends AsyncTask<Void, Void, String> {
        ProgressDialog dialog;

        public fetch_data(Context c) {
            this.dialog = new ProgressDialog(c);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.setMessage("Searching Tourist Spots");
            dialog.show();
        }

        @Override
        protected void onPostExecute(String s)

        {
            super.onPostExecute(s);

            d_output(s);
            dialog.dismiss();

        }

        @Override
        protected String doInBackground(Void... params) {

            try {
                url = new URL("https://maps.googleapis.com/maps/api/place/textsearch/json?query=Tourist+spots+in+" + dest_city + "&key=" + Api_key);
                connection = (HttpURLConnection) url.openConnection();
                Log.d("Search_Nearby",""+url);
                Log.d("display", "connection");
                connection.setRequestMethod("GET");
                connection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                connection.connect();
                bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    response.append(line + "\n");

                }
            } catch (Exception e) {
                Log.d("display", "NOconnection");
                e.printStackTrace();
            }
            if (response.toString() != null)
                return response.toString();
            else
                return null;
        }
    }

    public void d_output(String s) {
        try {
            JSONObject output = new JSONObject(s);
            JSONArray places = output.getJSONArray("results");
            for (int i = 0; i < places.length(); i++) {
                JSONObject place = places.getJSONObject(i);
                local_attractions.add(place.getString("name"));
                Log.d("Dispaly", "Item added");

            }

        } catch (Exception e) {

            Log.d("Dispaly", "Adapter Called");

        }
  /*      nearbyplacesCustom_adapter_for_places.notifyDataSetChanged();
  */
        nearbyplacesCustom_adapter_for_places = new Nearby_places_Custom_Adapter(local_attractions, Search_Nearby_Places.this);
        local_display.setAdapter(nearbyplacesCustom_adapter_for_places);

    }


}




