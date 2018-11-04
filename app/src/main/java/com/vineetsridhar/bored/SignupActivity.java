package com.vineetsridhar.bored;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignupActivity extends AppCompatActivity {
    EditText email;
    EditText password;
    EditText name;
    EditText location;
    EditText phonenum;
    TextView signup;
    FirebaseAuth auth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        Intent i = getIntent();


        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        name = findViewById(R.id.name);
        location = findViewById(R.id.location);
        signup = findViewById(R.id.signup);
        phonenum = findViewById(R.id.phonenum);


        email.setText(i.getStringExtra("email"));
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createUser();
            }
        });

    }

    public void createUser(){
        auth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            String uid;
                            DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("users");
                            ref.child(uid = task.getResult().getUser().getUid())
                                    .child("email").setValue(email.getText().toString());
                            ref.child(uid).child("name")
                                    .setValue(name.getText().toString());
                            ref.child(uid).child("zip")
                                    .setValue(location.getText().toString());
                            ref.child(uid).child("phone")
                                    .setValue(phonenum.getText().toString());
                            nextActivity();
                        }
                        else
                            Toast.makeText(getBaseContext(), "Didn't work", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void nextActivity() {
        startActivity(new Intent(SignupActivity.this, EventsActivity.class));
    }
}
