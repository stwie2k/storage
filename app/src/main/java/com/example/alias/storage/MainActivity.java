package com.example.alias.storage;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MainActivity extends AppCompatActivity {

    EditText username;
    EditText password1;
    EditText password2;
    ImageView pic;
    public List<Map<String, String>> datas = new ArrayList<Map<String, String>>();
    Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        UpdateInfo();

        username=findViewById(R.id.name);
        password1=findViewById(R.id.p1);
        password2=findViewById(R.id.p2);
        pic=findViewById(R.id.picture);




        Button ok=findViewById(R.id.ok);
        Button clear=findViewById(R.id.clear);
        final RadioGroup radioGroup=(RadioGroup) findViewById(R.id.RB);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton)MainActivity.this.findViewById(group.getCheckedRadioButtonId());
                String status=rb.getText().toString();



                if(status.equals("Login"))
                {
                    password2.setVisibility(View.GONE);
                    pic.setVisibility(View.GONE);

                }
                else{
                    password2.setVisibility(View.VISIBLE);
                    pic.setVisibility(View.VISIBLE);
                }


            }

        });


        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String un=username.getText().toString();
                String p1 = password1.getText().toString();
                String p2 = password2.getText().toString();


                if(un.equals(""))
                {
                    Toast.makeText(MainActivity.this, "Username cannot be empty.", Toast.LENGTH_LONG).show();
                    return ;
                }
                if(p1.equals(""))
                {
                    Toast.makeText(MainActivity.this, "Password cannot be empty.", Toast.LENGTH_LONG).show();
                    return ;
                }

                RadioButton rb = (RadioButton)MainActivity.this.findViewById(radioGroup.getCheckedRadioButtonId());
                String status=rb.getText().toString();

                String pa="";

                if(status.equals("Login"))
                {
                    int flag=0;
                    for (Map<String, String> m : datas){
                       String user=m.get("name");
                       if(user.equals(un))
                       {
                           flag=1;
                          pa=m.get("password");
                           break;
                       }

                    }


                    if(flag==0)
                    {
                        Toast.makeText(MainActivity.this, "Username not existed", Toast.LENGTH_LONG).show();
                        return;
                    }

                    if(!pa.equals(p1))
                    {
                        Toast.makeText(MainActivity.this, "Invalid password", Toast.LENGTH_LONG).show();
                        return;
                    }

                    Intent intent = new Intent(MainActivity.this, Comment.class);
                    intent.putExtra("name",un);
                    MainActivity.this.startActivity(intent);


                }
                else{
                    if(!p1.equals(p2)){
                        Toast.makeText(MainActivity.this, "Password Mismatch", Toast.LENGTH_LONG).show();
                        return;
                    }

                    for (Map<String, String> m : datas){
                        String user=m.get("name");
                        if(user.equals(un))
                        {
                            Toast.makeText(MainActivity.this, "Username already existed", Toast.LENGTH_LONG).show();
                            return;
                        }

                    }

                    Map<String, String> map = new HashMap<String, String>();
                    map.put("name", un);
                    map.put("password", p1);

                    datas.add(map);


                    String ur;
                   if(uri==null)
                   {
                       ur="";
                   }
                   else{
                       ur=uri.toString();
                   }

                    myDB Db = new myDB(getBaseContext());
                    SQLiteDatabase db = Db.getWritableDatabase();

                    ContentValues cv = new ContentValues();
                    cv.put("name", un);
                    cv.put("password", p1);
                    cv.put("uri",ur);


                    db.insert("User", null, cv);
                    db.close();




                    Toast.makeText(MainActivity.this, "Register successfully", Toast.LENGTH_LONG).show();
                }


            }
        });


        pic.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent();

                intent.setAction(Intent.ACTION_PICK);

                intent.setType("image/*");

                startActivityForResult(intent, 0);
            }
        });


        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username.setText("");
                password1.setText("");
                password2.setText("");
            }
        });




    }

    public void UpdateInfo()
    {
        myDB db = new myDB(getBaseContext());

        SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from "
                +"User", null);
        datas  = new ArrayList<Map<String, String>>();
        if (cursor == null) {
        } else {
            while (cursor.moveToNext()) {

                String nam = cursor.getString(1);
                String pass=cursor.getString(2);
                Map<String, String> map = new HashMap<String, String>();
                map.put("name", nam);
                map.put("password", pass);

                datas.add(map);
            }

        }


    }
    @Override

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (data != null) {

            // 得到图片的全路径

            uri = data.getData();



            //图片缩放的实现，请看：https://blog.csdn.net/reality_jie_blog/article/details/16891095

          this.pic.setImageURI(uri);



//            获取图片的缩略图，可能为空！
//
//             Bitmap bitmap = data.getParcelableExtra("data");
//
//             this.pic.setImageBitmap(bitmap);



        }

        super.onActivityResult(requestCode, resultCode, data);

    }



}
