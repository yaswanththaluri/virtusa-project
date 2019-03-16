package com.maya.virtusa.virtusa;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DataAdapter extends ArrayAdapter<Item>
{

    private View vview;



    public DataAdapter(Context context, int resource, List<Item> objects) {
        super(context, resource, objects);
    }


    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.list_items, parent, false);

        }

        vview = convertView;

        TextView title = convertView.findViewById(R.id.displaycontentname);
        TextView author = convertView.findViewById(R.id.displayauthorname);
        TextView preview = convertView.findViewById(R.id.displaypreview);
        TextView read = convertView.findViewById(R.id.readmore);

        LinearLayout lay = convertView.findViewById(R.id.colorlay);



        TextView lock = convertView.findViewById(R.id.locking);


        Item item = getItem(position);

        title.setText(item.getContentName());
        author.setText(item.getAuthor());
        preview.setText(item.getPreview());

        final String na = item.getContentName();
        final String au = item.getAuthor();
        final String tot = item.getFullContent();


        if(item.getIsFree().equals("true"))
        {
            read.setVisibility(View.VISIBLE);
            lock.setTextColor(Color.parseColor("#33cc33"));
            lock.setText("Unlocked");
            lock.setVisibility(View.VISIBLE);
            lay.setBackgroundColor(Color.parseColor("#33cc33"));
        }

        else
        {
            read.setVisibility(View.INVISIBLE);
            lock.setTextColor(Color.parseColor("#ff0000"));
            lock.setVisibility(View.VISIBLE);
            lock.setText("Locked");
            lay.setBackgroundColor(Color.parseColor("#ff0000"));
        }




        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        DatabaseReference reference = database.getReference().child("users").child(user.getUid());


        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userEntry u = dataSnapshot.getValue(userEntry.class);
                if(u.getIsPremium().equals("true"))
                {

                    TextView read = vview.findViewById(R.id.readmore);
                    LinearLayout lay = vview.findViewById(R.id.colorlay);
                    TextView lock = vview.findViewById(R.id.locking);


                    read.setVisibility(View.VISIBLE);
                    lock.setTextColor(Color.parseColor("#33cc33"));
                    lock.setText("Unlocked");
                    lock.setVisibility(View.VISIBLE);
                    lay.setBackgroundColor(Color.parseColor("#33cc33"));
                }
                else
                {

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        read.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), ItemView.class);
                Bundle extras = new Bundle();
                extras.putString("contentname", na);
                extras.putString("contentauthor", au);
                extras.putString("contentpreview", tot);
                i.putExtras(extras);
                view.getContext().startActivity(i);
            }
        });






        return convertView;
    }





}
