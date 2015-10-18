package in.silive.travelplanner.OldJourneys;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.zip.Inflater;

import in.silive.travelplanner.R;

/**
 * Created by AKG 002 on 14-10-2015.
 */
public class AdapterForShortJourney extends BaseAdapter {
    Context context;
    Cursor cursor;

    AdapterForShortJourney(Context c, Cursor cur) {
        this.context = c;
        this.cursor = cur;
    }

    @Override
    public int getCount() {
        return cursor.getCount();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.d("Position", "" + position);
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.adapter_for_short_journey, null);
        }


        Log.d("ADap", cursor.getCount() + "  " + cursor.getPosition());

        ((TextView) convertView.findViewById(R.id.dateTxtView)).setText(cursor.getString(0));
        ((TextView) convertView.findViewById(R.id.city)).setText(cursor.getString(1) +
                " -> " + cursor.getString(2));
        Log.d("ADap", "added");

        cursor.moveToNext();
        return convertView;
    }
}
