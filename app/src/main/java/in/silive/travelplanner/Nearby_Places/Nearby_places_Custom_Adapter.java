package in.silive.travelplanner.Nearby_Places;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;

import in.silive.travelplanner.R;

/**
 * Created by H.P on 10/7/2015.
 */
public class Nearby_places_Custom_Adapter extends BaseAdapter {


    Context context;
    ArrayList<String> list_of_places = new ArrayList<String>();
    boolean checkedlist[];

    Nearby_places_Custom_Adapter(ArrayList list_of_places, Context c) {
        this.list_of_places = list_of_places;
        this.context = c;
        Toast.makeText(c, "Mark as Favourates", Toast.LENGTH_LONG).show();
        checkedlist = new boolean[list_of_places.size()];
    }

    @Override
    public int getCount() {
        return this.list_of_places.size();
    }

    @Override
    public Object getItem(int position) {
        return list_of_places.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.custom_adapter, parent, false);
            viewHolder.textView = (TextView) convertView.findViewById(R.id.custom_text);
            viewHolder.checkBox = (CheckBox) convertView.findViewById(R.id.custom_check);
            convertView.setTag(viewHolder);
            viewHolder.checkBox.setTag("UnChecked");
        } else {
            viewHolder = (ViewHolder) convertView.getTag();

        }
        viewHolder.textView.setText(list_of_places.get(position));
        viewHolder.checkBox.setChecked(false);
        if (checkedlist[position]) {
            viewHolder.checkBox.setChecked(true);
        }
        viewHolder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkedlist[position]) {
                    checkedlist[position] = false;
                } else
                    checkedlist[position] = true;
            }
        });
        return convertView;
    }

    public class ViewHolder {
        TextView textView;
        CheckBox checkBox;
    }
    public String[] get_fav_places()
    {

        ArrayList<String> fav_places=new ArrayList();



        for (int i=0;i<list_of_places.size();i++)
        {
            if (checkedlist[i]==true)
            {
                fav_places.add(list_of_places.get(i)); ;
                Log.d("fav", list_of_places.get(i)) ;        }

        }
        String[] fav_list = fav_places.toArray(new String[fav_places.size()]);
        return  fav_list;

    }
}
