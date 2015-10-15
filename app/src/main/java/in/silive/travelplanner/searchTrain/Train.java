package in.silive.travelplanner.searchTrain;

import android.util.Log;

import org.json.JSONObject;

import java.util.Calendar;

/**
 * Created by AKG 002 on 30-09-2015.
 */
public class Train {
    String name;
    int no ;
    String arrTime,depTime ;
    public void setArrTime(String arrTime) {
        this.arrTime = arrTime;
    }



    public String getArrTime() {
        return arrTime;
    }



    public void setName(String name) {
        this.name = name;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public void setDepTime(String depTime) {
        this.depTime = depTime;
    }
    public Train(){}
    public Train(JSONObject train_obj) {
        try {
            name = train_obj.getString("name");
            no=train_obj.getInt("number");
            String input_time=train_obj.getString("src_departure_time");
            String[] split = input_time.split(":");
            depTime = split[0]+split[1];
            input_time=train_obj.getString("dest_arrival_time");
            split = input_time.split(":");
            arrTime = split[0]+split[1];

        }catch (Exception e){
            Log.d("Error in train class :","Error in parsing");
        }
    }
    public String getName() {
        return this.name;
    }
    public int getNo(){
        return this.no;
    }
    public String getDepTime(){
        return this.depTime;
    }
}
