package com.vineetsridhar.bored;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {


    FirebaseAuth auth = FirebaseAuth.getInstance();

    TextView login, signup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login = findViewById(R.id.login);
        signup = findViewById(R.id.signup);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextActivity(1);
            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextActivity(3);
            }
        });

        if(auth.getCurrentUser() != null){
            nextActivity(2);
            //login.setText(auth.getUid());
        }
    }

    public void nextActivity(int num) {
        if(num == 1)
            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
        else if(num == 2)
            startActivity(new Intent(LoginActivity.this, EventsActivity.class));
        else
            startActivity(new Intent(LoginActivity.this, SignupActivity.class));

    }
}
