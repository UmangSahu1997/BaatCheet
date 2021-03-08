package com.example.baatcheet;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SendBaat extends AppCompatActivity {
    EditText edtTweet;
    ListView seeTweets;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_baat);
        edtTweet=findViewById(R.id.edtTweet);
        seeTweets=findViewById(R.id.seeTweets);
    }
    public void btnSend(View bs){
        ParseObject parseObject=new ParseObject("MyTweet");
        parseObject.put("tweets",edtTweet.getText().toString());
        parseObject.put("user", ParseUser.getCurrentUser().getUsername());
        parseObject.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if(e==null){
                    Toast.makeText(SendBaat.this,"Done", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    public void seeOther(View so){
        final ArrayList<HashMap<String,String>> tweetList=new ArrayList<>();
        final SimpleAdapter adapter=new SimpleAdapter(SendBaat.this,tweetList,android.R.layout.simple_list_item_2,
                new String[]{"tweetUsername","tweetValue"},new int[]{android.R.id.text1,android.R.id.text2});
        try{
            ParseQuery<ParseObject> parseQuery=ParseQuery.getQuery("MyTweet");
            parseQuery.whereContainedIn("user",ParseUser.getCurrentUser().getList("fanOf"));
            parseQuery.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> objects, ParseException e) {
                    if(objects.size()>0 && e==null){
                        for(ParseObject tweetObject:objects){
                            HashMap<String,String> userTweet=new HashMap<>();
                            userTweet.put("tweetUsername",tweetObject.getString("user"));
                            userTweet.put("tweetValue",tweetObject.getString("tweets"));
                            tweetList.add(userTweet);

                        }
                        seeTweets.setAdapter(adapter);
                    }
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}