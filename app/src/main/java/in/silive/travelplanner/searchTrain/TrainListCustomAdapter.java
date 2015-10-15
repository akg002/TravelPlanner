package in.silive.travelplanner.searchTrain;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import in.silive.travelplanner.R;

/**
 * This is a custom adapter for displaying list items in a train search .
 */
public class TrainListCustomAdapter extends BaseAdapter {

    ArrayList<Train> trainArrayList;
    Context context;
    TrainListCustomAdapter(Context c,ArrayList<Train> trainList){
        this.trainArrayList = trainList;
        this.context=c;

    }

    @Override
    public int getCount() {
        return trainArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return trainArrayList.toArray()[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        if (row==null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.list_adapter_trains,null);
        }
        TextView txt1 = (TextView)row.findViewById(R.id.TextView1);
        TextView txt2 = (TextView)row.findViewById(R.id.TextView2);
        txt1.setText(trainArrayList.get(position).getNo()+" - "+trainArrayList.get(position).getName());
        txt2.setText("Departure: "+trainArrayList.get(position).getDepTime()+" HRS  Arrival: "+trainArrayList.get(position).getArrTime()+" HRS  ");


        return row;
    }
}
