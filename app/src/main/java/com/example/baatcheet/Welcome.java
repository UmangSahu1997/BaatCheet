package com.example.baatcheet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;

import static com.parse.ParseUser.logOutInBackground;

public class Welcome extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private ListView listView;
    private ArrayList<String> mArrayList;
    private ArrayAdapter mArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        Toast.makeText(Welcome.this," Welcome",Toast.LENGTH_LONG).show();
        listView=findViewById(R.id.listview);
        mArrayList=new ArrayList<>();
        mArrayAdapter=new ArrayAdapter(Welcome.this,android.R.layout.simple_list_item_checked,mArrayList);
        listView.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);
        listView.setOnItemClickListener(this);
        ParseQuery<ParseUser> parseQuery=new ParseUser().getQuery();
        parseQuery.whereNotEqualTo("username", ParseUser.getCurrentUser().getUsername());
        parseQuery.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> objects, ParseException e) {
                if(e==null){
                    if(objects.size()>0){
                        for(ParseUser user:objects){
                            mArrayList.add(user.getUsername());
                        }
                        listView.setAdapter(mArrayAdapter);
                        for(String tuser:mArrayList){
                            if(ParseUser.getCurrentUser().getList("fanOf")!=null){
                            if(ParseUser.getCurrentUser().getList("fanOf").contains(tuser)){
                                listView.setItemChecked(mArrayList.indexOf(tuser),true);
                            }
                        }}
                    }
                }
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.my_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.logOutUser){
            ParseUser.getCurrentUser().logOut();
            finish();
        }
        else if(item.getItemId()==R.id.sendBaat){
            Intent intent = new Intent(Welcome.this, SendBaat.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        CheckedTextView checkedTextView=(CheckedTextView) view;
        if(checkedTextView.isChecked()){
            Toast.makeText(Welcome.this,mArrayList.get(position)+" is followed",Toast.LENGTH_LONG).show();
            ParseUser.getCurrentUser().add("fanOf",mArrayList.get(position));
        }else {
            Toast.makeText(Welcome.this,mArrayList.get(position)+" is not followed",Toast.LENGTH_LONG).show();
            ParseUser.getCurrentUser().getList("fanOf").remove(mArrayList.get(position));
            List followerUplist=ParseUser.getCurrentUser().getList("fanOf");
            ParseUser.getCurrentUser().remove("fanOf");
            ParseUser.getCurrentUser().put("fanOf",followerUplist);
        }
        ParseUser.getCurrentUser().saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    Toast.makeText(Welcome.this,"Done",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

}