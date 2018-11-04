package com.vineetsridhar.bored;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class RemoveActivity extends AppCompatActivity {

    ListView view;
    ArrayList<MiniEvent> events;
    ArrayAdapter<MiniEvent> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remove);

        view = findViewById(R.id.listView);
        events = new ArrayList<>();
        getEvents(new MyCallBack() {
            @Override
            public void onCallBack(String value) {
                view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {
                        final String id = events.get(i).getId();
                        FirebaseDatabase.getInstance().getReference().child("Events").child(id).removeValue();
                        events.remove(i);
                    }
                });
            }
        });

    }

    public void getEvents(final MyCallBack myCallBack){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Events");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                    events.add(new MiniEvent((String)snapshot.child("id").getValue(), (String)snapshot.child("title").getValue()));
                }
                adapter = new ArrayAdapter<>(getBaseContext(), android.R.layout.simple_list_item_1, events);
                view.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                myCallBack.onCallBack("");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public interface MyCallBack{
        void onCallBack(String value);
    }
}
