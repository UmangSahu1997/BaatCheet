package com.example.baatcheet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class Login extends AppCompatActivity {
    EditText edtEmail2,edtPass2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle("Log in");
        edtEmail2=findViewById(R.id.edtEmail2);
        edtPass2=findViewById(R.id.edtPass2);
        if(ParseUser.getCurrentUser()!=null){
            ParseUser.getCurrentUser().logOut();
        }
    }
    public void signPage(View sp){
        Intent intent=new Intent(Login.this,MainActivity.class);
        startActivity(intent);
    }
    public void logIn(View li){
        ParseUser.logInInBackground(edtEmail2.getText().toString(), edtPass2.getText().toString(), new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if(user !=null && e== null){
                    Toast.makeText(Login.this,user.get("username")+" logged in sucessfully",Toast.LENGTH_SHORT).show();
                    goWelcome();
                }
                else Toast.makeText(Login.this," logged in unsucessfully",Toast.LENGTH_SHORT).show();
            }
        });

    }
    public void goBack1(View gb){
        try{
            InputMethodManager inputMethodManager=(InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    public void goWelcome(){
        Intent intent=new Intent(Login.this,Welcome.class);
        startActivity(intent);
    }
}
