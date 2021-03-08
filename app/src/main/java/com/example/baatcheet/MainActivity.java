package com.example.baatcheet;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    EditText edtName, edtEmail, edtPass;
    Button button, button2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edtName = findViewById(R.id.edtName);
        edtEmail = findViewById(R.id.edtEmail);
        edtPass = findViewById(R.id.edtPass);
        button = findViewById(R.id.button);
        button2 = findViewById(R.id.button2);
        button.setOnClickListener(this);
        button2.setOnClickListener(this);
        if (ParseUser.getCurrentUser() != null) {
            goWelcome();
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button:
                if (edtEmail.getText().toString().equals("") || edtPass.getText().toString().equals("") ||
                        edtName.getText().toString().equals("")) {
                    Toast.makeText(MainActivity.this, "Fill it properly", Toast.LENGTH_SHORT).show();
                } else {
                    ParseUser pu = new ParseUser();
                    pu.setUsername(edtName.getText().toString());
                    pu.setPassword(edtPass.getText().toString());
                    pu.setEmail(edtEmail.getText().toString());


                    pu.signUpInBackground(new SignUpCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null) {
                                Toast.makeText(MainActivity.this, "Signed in sucessfully", Toast.LENGTH_SHORT).show();
                                goWelcome();
                            } else {
                                Toast.makeText(MainActivity.this, "Error" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                }
                break;
            case R.id.button2:
                Intent intent = new Intent(MainActivity.this, Login.class);
                startActivity(intent);
                break;
        }
    }

    public void goBack(View gb) {
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void goWelcome() {
        Intent intent = new Intent(MainActivity.this, Welcome.class);
        startActivity(intent);
    }

}