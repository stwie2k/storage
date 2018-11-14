package com.example.alias.storage;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Comment extends AppCompatActivity {

    public List<Map<String, String>> data = new ArrayList<Map<String, String>>();


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.comment);


        Map<String,String>temp = new LinkedHashMap<>();
        temp.put("name","Bob");
        temp.put("date","11.9");
        temp.put("comment","Haha");
        data.add(temp);

        ListView listView=findViewById(R.id.lw);
        SimpleAdapter simpleAdapter = new SimpleAdapter(this,data,R.layout.commentlist,new String[] {"name","date","comment"},new int[]{R.id.username,R.id.date,R.id.com});
        listView.setAdapter(simpleAdapter);



    }
}
