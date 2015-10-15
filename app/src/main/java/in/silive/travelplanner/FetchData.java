package in.silive.travelplanner;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import in.silive.travelplanner.Interface.canDisplayTrainAdapter;
import in.silive.travelplanner.Interface.canDisplayTrainSeats;
import in.silive.travelplanner.searchTrain.Train;

/**
 * Created by AKG 002 on 06-10-2015.
 */



public class FetchData {





    public static String getTrainsURL(String src,String dest,String day,String month){
        return "http://api.railwayapi.com/between/source/" + src + "/dest/" + dest + "/date/" + day + "-" + month + "/apikey/"+"dkkyr5216";
    }


    public static class fetchTrains extends AsyncTask<Void, Void, String> {

        String src_stn_code, dest_stn_code;
        URL url;
        HttpURLConnection connection;
        String day, month;
        BufferedReader bufferedReader;
        canDisplayTrainAdapter trainApiObj;
        StringBuilder response;
        ArrayList<Train> trainArrayList = new ArrayList<Train>();
        TextView disp;
        ProgressDialog dialog;

        public ArrayList<Train> getTrainList() {
            return this.trainArrayList;
        }

        public fetchTrains(canDisplayTrainAdapter c, String src_code, String dest_code, String d, String m) {
            this.src_stn_code = src_code;
            this.dest_stn_code = dest_code;
            this.day = d;
            this.month = m;
            this.trainApiObj = c;
            Context context = (Context) trainApiObj;
            this.dialog = new ProgressDialog(context);
        }

        @Override
        protected String doInBackground(Void... params) {

            try {
                url = new URL(FetchData.getTrainsURL(src_stn_code, dest_stn_code, day, month));
                System.out.print("" + url);
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                connection.connect();
                int responseCode = connection.getResponseCode();
                System.out.println("Code :" + responseCode);
                bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                response = new StringBuilder();
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    response.append(line + "\n");
                }

            } catch (Exception e) {
                Log.d("Train Search Api", "Error in connection \n");
                e.printStackTrace();
                return null;
            }


            try {
                JSONObject output = new JSONObject(response.toString());
                JSONArray train_array = output.getJSONArray("train");
                if (output.getInt("total") != 0) {
                    for (int i = 0; i < train_array.length(); ++i) {
                        Train train = new Train(train_array.getJSONObject(i));
                        trainArrayList.add(train);
                        // trainAdapter.notifyDataSetChanged();

                        Log.d("Train Search Api", "" + train.getName());
                    }
                } else {

                    Log.d("Train Search Api :", "No available trains");
                    return "0";
                }
            } catch (Exception e) {
                Log.d("Train Search Api :", "parsing error");
            }
            return null;
        }

        @Override
        protected void onPreExecute() {

            dialog.setMessage("Fetching Trains .....");
            dialog.show();

        }

        @Override
        protected void onPostExecute(String s) {
            //   super.onPostExecute(s);
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            if (s == null) {
                Toast.makeText((Context) trainApiObj, "No Internet Connection.", Toast.LENGTH_LONG);
            }
            if (s == "0") {
                Toast.makeText((Context) trainApiObj, "No trains found", Toast.LENGTH_LONG).show();

            }
            trainApiObj.displayTrainList(trainArrayList);

        }
    }
    public static class fetchTrainInfo extends AsyncTask<Void,Void,String>{
        URL url;
        HttpURLConnection connection;
        BufferedReader bufferedReader;
        StringBuilder response;
        Train train;
        String day, month,year,src_station, dest_station,train_class;
        String seat;
        canDisplayTrainSeats bookTicketObj ;
        ProgressDialog dialog;


        public fetchTrainInfo(canDisplayTrainSeats c,Train t,String d,String m,String y,String src,String dest,String cl){
            this.train=t;
            this.day = d;
            this.month = m;
            this.src_station = src;
            this.dest_station = dest;
            this.train_class = cl;
            this.bookTicketObj = c;
            this.dialog = new ProgressDialog((Context)c);
            this.year = y;
        }


        @Override
        protected String doInBackground(Void... params) {
            Log.d("Book Ticket Api","Api Called");
            String[] array = src_station.split(" ");
            src_station = array[array.length-1];
            array = dest_station.split(" ");
            dest_station = array[array.length-1];
            try {
                url = new URL("http://api.railwayapi.com/check_seat/train/"+train.getNo()+"/source/"+src_station+"/dest/"+dest_station+"/date/"+day+"-"+month+"-"+year+"/class/"+train_class+"/quota/GN/apikey/dkkyr5216/");
                connection = (HttpURLConnection)url.openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                connection.connect();
                Log.d("Book Ticket Api:", "url : " + url.toString());
                int responseCode = connection.getResponseCode();
                System.out.println("Code :" + responseCode);
                bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                response = new StringBuilder();
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    response.append(line + "\n");
                    Log.d("Incoming stream",line);
                }
            }catch (Exception e ){
                Toast.makeText((Context) bookTicketObj, "No Internet Connection.", Toast.LENGTH_LONG);
                Log.d("Book Ticket Api :", "Error in connection");
            }

            try {
                JSONObject output = new JSONObject(response.toString());
              ;
                JSONArray availability = output.getJSONArray("availability");
                if (availability.length()!=0) {
                    seat = availability.getJSONObject(0).getString("status");

                }
                else {
                    Log.d("Book Ticket Api :", "No available seats");

                }
            } catch (Exception e) {
                e.printStackTrace();
                Log.d("Book Ticket Api :", "parsing error");
                return null;
            }
            return "success";

        }

        @Override
        protected void onPreExecute() {
            //super.onPreExecute();
            dialog.setMessage("Fetching Seats Info .....");
            dialog.show();
        }

        @Override
        protected void onPostExecute(String s) {
            //  super.onPostExecute(s);
            if (dialog.isShowing()){
                dialog.dismiss();
            }
            if (s!=null) {
                bookTicketObj.setTrainSeats(seat, true);

            }
            else
            {    bookTicketObj.setTrainSeats("No avilable seats", false);
                Toast d = Toast.makeText((Context)bookTicketObj, "No available seats", Toast.LENGTH_LONG);
                d.show();
            }
        }
    }

}

