package com.vineetsridhar.bored;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends AppCompatActivity {
    EditText email;
    EditText password;
    TextView login;
    FirebaseAuth auth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        email = findViewById(R.id.username);
        password = findViewById(R.id.password);
        login = findViewById(R.id.login);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(email.getText().toString().equals("")||password.getText().toString().equals(""))
                    Toast.makeText(getBaseContext(), "Login Failed!", Toast.LENGTH_SHORT).show();
                else
                    login();
            }
        });
    }

    public void login(){
        auth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            nextActivity();
                        } else{
                            Toast.makeText(getBaseContext(), "Login Failed!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    public void nextActivity() {
        startActivity(new Intent(HomeActivity.this, EventsActivity.class));
    }
}
