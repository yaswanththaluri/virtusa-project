package com.maya.virtusa.virtusa;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class Content extends Fragment {

    private FirebaseDatabase database;
    private DatabaseReference reference;
    private ListView list;
    private DataAdapter adapter;
    private ChildEventListener mChildEventListener;
    private ProgressDialog load;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.content, container, false);


        database = FirebaseDatabase.getInstance();
        reference = database.getReference().child("content-free");



        List<Item> itemsAvailible = new ArrayList<>();
        list = view.findViewById(R.id.items);

        load = new ProgressDialog(view.getContext());
        load.setMessage("Fetching Items...Please Wait!");
        load.setCanceledOnTouchOutside(false);
        load.show();

        adapter = new DataAdapter(getContext(), R.layout.list_items, itemsAvailible);
        list.setAdapter(adapter);




        return view;
    }


    private void attachDataBaseReadListener() {
        if (mChildEventListener == null) {

            mChildEventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    Log.i("datastart", "onChildAdded: ");
                    Item items = dataSnapshot.getValue(Item.class);
                    Log.i("dataproduct", dataSnapshot.toString());
                    adapter.add(items);
                    load.dismiss();
                }


                @Override
                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {


                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

//                    progress.setVisibility(View.INVISIBLE);
                }
            };
            reference.addChildEventListener(mChildEventListener);

        }
    }


    @Override
    public void onStart() {
        super.onStart();
        attachDataBaseReadListener();
    }

    public void onPause() {
        super.onPause();
        detachDatabaseReadListener();
        adapter.clear();
    }


    private void detachDatabaseReadListener() {
        if (mChildEventListener != null) {
            reference.removeEventListener(mChildEventListener);
            mChildEventListener = null;
        }
    }
}
