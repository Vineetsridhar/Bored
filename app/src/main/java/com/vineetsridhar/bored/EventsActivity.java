package com.vineetsridhar.bored;

import android.Manifest;
import android.animation.Animator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.karan.churi.PermissionManager.PermissionManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class EventsActivity extends AppCompatActivity {
    boolean isOpen = false, returnState;
    RelativeLayout layout;
    FloatingActionButton button;
    LinearLayout menuLayout;
    TextView submit, address;
    EditText title, location, desc;
    RecyclerView view;
    ArrayList<Event> eventList = new ArrayList<>();
    EventAdapter adapter;
    FirebaseAuth auth;
    String name, number;
    String locationString;

    LocationManager locationManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);

        layout = findViewById(R.id.layout);
        button = findViewById(R.id.fab);
        address = findViewById(R.id.address);
        button.setImageResource(R.drawable.ic_add_white_24dp);
        menuLayout = findViewById(R.id.createCard);
        submit = findViewById(R.id.submit);
        view = findViewById(R.id.listView);

        title = findViewById(R.id.title);
        location = findViewById(R.id.location);
        desc = findViewById(R.id.description);
        auth = FirebaseAuth.getInstance();

        getNameandNumber();


        adapter = new EventAdapter(getBaseContext(), R.layout.cards, eventList);
        loadItems();
        view.setAdapter(adapter);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);

        view.setLayoutManager(llm);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewMenu();
            }
        });
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getLocation();
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(title.getText().toString().equals("") || desc.getText().toString().equals("") || location.getText().toString().equals("")){
                    Toast.makeText(getBaseContext(), "Make sure to fill out all fields", Toast.LENGTH_SHORT).show();
                } else{
                    addToDatabase();
                    viewMenu();
                }
            }
        });

        view.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(@NonNull RecyclerView recyclerView, @NonNull MotionEvent motionEvent) {
                return false;
            }

            @Override
            public void onTouchEvent(@NonNull RecyclerView recyclerView, @NonNull MotionEvent motionEvent) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean b) {

            }
        });
        adapter.setOnItemClickListener(new EventAdapter.ClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                     makeToast("df");
                    Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + eventList.get(position).getLocation());
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    i.setData(gmmIntentUri);
                    i.setPackage("com.google.android.apps.maps");
                    startActivity(i);
            }

            @Override
            public void onItemLongClick(int position, View v) {

            }
        });

    }

    private void addToDatabase(){
        if(checkEvents(auth.getUid()))
            makeToast("You cannot have two events up at once. Please remove the existing Event");
        else {
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Events").push();
            ref.child("title").setValue(title.getText().toString());
            ref.child("description").setValue(desc.getText().toString());
            ref.child("name").setValue(name);
            ref.child("phone").setValue(number);
            ref.child("timestamp").setValue(ServerValue.TIMESTAMP);
            ref.child("id").setValue(auth.getUid());
            ref.child("location").setValue(location.getText().toString())
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful())
                                Log.e("", "");
                            else
                                Toast.makeText(getBaseContext(), "Error Occured", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    public boolean checkEvents(final String id){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Events");
        returnState = false;
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds:dataSnapshot.getChildren()){
                    makeToast("In Loop");
                    if(id.equals(ds.child("id").getValue())){
                        returnState = true;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return returnState;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        auth.signOut();
        startActivity(new Intent(EventsActivity.this, LoginActivity.class));
        return true;
    }

    private void viewMenu() {
        if (!isOpen) {
            //Open the menu
            //Get animation parameters
            int x = layout.getRight();
            int y = layout.getBottom();
            int startRadius = 0;
            int endRadius = (int) Math.hypot(layout.getWidth(), layout.getHeight());

            //Create animation and change values of the Floating Action Button
            button.setBackgroundTintList(ColorStateList.valueOf(ResourcesCompat.getColor(getResources(), android.R.color.white, null)));
            button.setImageResource(R.drawable.ic_close_black_24dp);
            Animator anim = ViewAnimationUtils.createCircularReveal(menuLayout, x, y, startRadius, endRadius);
            menuLayout.setVisibility(View.VISIBLE);
            anim.start();
            view.setVisibility(View.GONE);
            //Set isOpen as true so that I know if the menu is open or not.
            isOpen = true;
        } else {
            //If menu is already open, and I need to get back to homescreen
            //Animation Parameters
            int x = layout.getRight();
            int y = layout.getBottom();
            int startRadius = (int) Math.hypot(layout.getWidth(), layout.getHeight());
            int endRadius = 0;

            //Create animation and change values of the Floating Action Button
            button.setBackgroundTintList(ColorStateList.valueOf(ResourcesCompat.getColor(getResources(), R.color.colorAccent, null)));
            button.setImageResource(R.drawable.ic_add_white_24dp);
            Animator anim = ViewAnimationUtils.createCircularReveal(menuLayout, x, y, startRadius, endRadius);
            anim.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {

                }

                @Override
                public void onAnimationEnd(Animator animator) {
                    //listView.setVisibility(View.VISIBLE);
                    view.setVisibility(View.VISIBLE);
                    menuLayout.setVisibility(View.GONE);

                }

                @Override
                public void onAnimationCancel(Animator animator) {

                }

                @Override
                public void onAnimationRepeat(Animator animator) {

                }
            });
            //Start animation, and change isOpen to false if user clicks button again
            anim.start();
            isOpen = false;
        }
    }
    public void loadItems(){
       DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
       ref.child("Events").addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               eventList.clear();
               for(DataSnapshot ds:dataSnapshot.getChildren()) {
                   eventList.add(new Event((String)ds.child("name").getValue(),(String) ds.child("title").getValue(), (String)ds.child("location").getValue(), (String)ds.child("description").getValue(),(String) ds.child("phone").getValue()));
               }
               adapter.notifyDataSetChanged();
           }

           @Override
           public void onCancelled(@NonNull DatabaseError databaseError) {

           }
       });
    }

    public void getLocation(){
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        } else{
            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            if(location != null){
                double latti = location.getLatitude();
                double longi = location.getLongitude();
                getAddress(latti, longi);
            }
            else{
                this.location.setHint("Was not able to find Location");
            }
        }
    }

    public void getNameandNumber(){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("users");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds: snapshot.getChildren()){
                    if(ds.toString().contains(auth.getUid())) {
                        name = (String) ds.child("name").getValue();
                        number = (String) ds.child("phone").getValue();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void getAddress(double latitude, double longitude){

        Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
        try {
            StringBuilder address = new StringBuilder();
            List<Address> addressList = geocoder.getFromLocation(latitude, longitude, 1);
            if(addressList != null && addressList.size() > 0){
                if(addressList.get(0).getSubThoroughfare() != null)
                    address.append(addressList.get(0).getSubThoroughfare() + " ");
                if(addressList.get(0).getThoroughfare() != null)
                    address.append(addressList.get(0).getThoroughfare() + ", ");

                if(addressList.get(0).getLocality() != null)
                    address.append(addressList.get(0).getLocality() + ", ");
                if(addressList.get(0).getAdminArea() != null)
                    address.append(addressList.get(0).getAdminArea() + " ");
                if(addressList.get(0).getPostalCode() != null)
                    address.append(addressList.get(0).getPostalCode());

                this.location.setText(address.toString());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch(requestCode){
            case 1:
                getLocation();
                break;
        }
    }
    public void makeToast(String s){
        Toast.makeText(getBaseContext(), s, Toast.LENGTH_LONG).show();
    }
}
