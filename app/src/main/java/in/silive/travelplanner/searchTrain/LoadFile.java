package in.silive.travelplanner.searchTrain;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by AKG 002 on 02-10-2015.
 */
public class LoadFile{
    public static String[] getStationList(Context context){
        AssetManager assetManager = context.getAssets();
        ArrayList<String> list= new ArrayList<String>();
        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(assetManager.open("Station_List.txt")));
            String line;
            while ((line=bufferedReader.readLine())!=null)
            {
                list.add(line);
            }
        }catch (Exception e)
        {
            Log.e("Load File",""+e.getMessage());
        }
        String[] s= list.toArray(new String[list.size()]);
        return s;
    }
}
