package com.example.rodneytressler.todolist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by rodneytressler on 10/25/16.
 */

public class CustomAdapter extends ArrayAdapter<groceryListItem> {
    private SimpleDateFormat formatter;

    public CustomAdapter(Context context, ArrayList<groceryListItem> items) {
        super(context, 0, items);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        groceryListItem groceryListItem = getItem(position);
        formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm aaa");
        TimeZone tz = TimeZone.getTimeZone("America/Kentucky/Monticello");
        formatter.setTimeZone(tz);

        LayoutInflater inflater = LayoutInflater.from(getContext());
        View customView = inflater.inflate(R.layout.activity_grocery_list_item, parent, false);

        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_grocery_list_item, parent, false);
        }
        Date date2 = new Date();
        TextView title = (TextView) convertView.findViewById(R.id.grocery_list_item_title);
        TextView date = (TextView) convertView.findViewById(R.id.grocery_list_item_date);
        TextView text = (TextView) convertView.findViewById(R.id.grocery_list_item_text);
        TextView category = (TextView) convertView.findViewById(R.id.category_text);
        TextView currentDate = (TextView) convertView.findViewById(R.id.date_edited);
        title.setText(groceryListItem.getTitle());
        date.setText("Due-Date: " + groceryListItem.getDate());
        text.setText(groceryListItem.getText());
        category.setText("Category:  " + groceryListItem.getCategory().toLowerCase());
        currentDate.setText("Last Edited: " + formatter.format(date2));

        return convertView;

    }

}
