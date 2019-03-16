package com.maya.virtusa.virtusa;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class DataAdapter extends ArrayAdapter<Item>
{




    public DataAdapter(Context context, int resource, List<Item> objects) {
        super(context, resource, objects);
    }


    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.list_items, parent, false);

        }


        TextView title = convertView.findViewById(R.id.displaycontentname);
        TextView author = convertView.findViewById(R.id.displayauthorname);
        TextView preview = convertView.findViewById(R.id.displaypreview);
        TextView read = convertView.findViewById(R.id.readmore);



        TextView lock = convertView.findViewById(R.id.locking);


        Item item = getItem(position);

        title.setText(item.getContentName());
        author.setText(item.getAuthor());
        preview.setText(item.getPreview());

        final String na = item.getContentName();
        final String au = item.getAuthor();
        final String pr = item.getPreview();

        if (item.getIsFree().equals("false"))
        {
            read.setVisibility(View.INVISIBLE);
            lock.setTextColor(Color.parseColor("#ff0000"));
            lock.setVisibility(View.VISIBLE);
            lock.setText("Locked");
        }

        else
        {
            read.setVisibility(View.VISIBLE);
            lock.setTextColor(Color.parseColor("#33cc33"));
            lock.setText("Unlocked");
            lock.setVisibility(View.VISIBLE);
        }

        read.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), ItemView.class);
                Bundle extras = new Bundle();
                extras.putString("contentname", na);
                extras.putString("contentauthor", au);
                extras.putString("contentpreview", pr);
                i.putExtras(extras);
                view.getContext().startActivity(i);
            }
        });



        return convertView;
    }


}
