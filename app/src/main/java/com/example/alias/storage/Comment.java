package com.example.alias.storage;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Comment extends AppCompatActivity {

    public List<Map<String, String>> data = new ArrayList<Map<String, String>>();

    SimpleAdapter simpleAdapter;
    String currentuser;
    MyAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.comment);



        Intent intent =getIntent();
        currentuser=intent.getStringExtra("name");


        UpdateInfo();

        Map<String,String>temp = new LinkedHashMap<>();
        temp.put("name","Bob");
        temp.put("date","11.9");
        temp.put("comment","Haha");
//        data.add(temp);

        final ListView listView=findViewById(R.id.lw);
          adapter = new MyAdapter(Comment.this, data);

       final  AlertDialog dialog = new AlertDialog.Builder(this).create();//创建对话框

//        simpleAdapter = new SimpleAdapter(this,data,R.layout.commentlist,new String[] {"name","date","comment"},new int[]{R.id.username,R.id.date,R.id.com});
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l){

                LinearLayout layout = (LinearLayout)view;
                TextView status = (TextView) layout.findViewById(R.id.username);
                String _name=status.getText().toString();



                String number="";
                Cursor cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,
                        ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " = \"" + _name + "\"", null, null);
                if(cursor.moveToFirst())
                {
                    number = "\nPhone: ";
                    do {
                        number += cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)) + "         ";
                    } while (cursor.moveToNext());
                }
                else
                {
                    number = "\nPhone number not exist ";
                }





                dialog.setIcon(R.mipmap.ic_launcher);//设置对话框icon
                dialog.setTitle("Info");//设置对话框标题

                dialog.setMessage("Username:"+_name+number);//设置文字显示内容

                dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();//关闭对话框
                    }
                });
                dialog.show();//显示对话框

            }
        });

        final  AlertDialog dialog1 = new AlertDialog.Builder(this).create();//创建对话框

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView,View view,final int position,long l) {
                LinearLayout layout = (LinearLayout)view;
                TextView status = (TextView) layout.findViewById(R.id.username);
                String _name=status.getText().toString();
                if(_name.equals(currentuser))
                {

                    dialog1.setIcon(R.mipmap.ic_launcher);//设置对话框icon


                    dialog1.setMessage("Delete or not");//设置文字显示内容

                    dialog1.setButton(DialogInterface.BUTTON_NEGATIVE, "No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();//关闭对话框
                        }
                    });

                    dialog1.setButton(DialogInterface.BUTTON_POSITIVE, "Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();//关闭对话框


                            String id=data.get(position).get("id");

                            myDB db = new myDB(getBaseContext());
                            SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();
                            sqLiteDatabase.execSQL("delete from " + "Comment"
                                    + " where CID = ?", new String[]{id});
                            sqLiteDatabase.close();

                            myDB db1 = new myDB(getBaseContext());
                            SQLiteDatabase sqLiteDatabase2 = db1.getWritableDatabase();
                            sqLiteDatabase2.execSQL("delete from " + "Star"
                                    + " where CID = ?", new String[]{id});
                            sqLiteDatabase2.close();



                            data.remove(position);

                            adapter.notifyDataSetChanged();

                        }
                    });
                    dialog1.show();//显示对话框


                }
                else{
                    dialog1.setIcon(R.mipmap.ic_launcher);//设置对话框icon


                    dialog1.setMessage("Report or not");//设置文字显示内容

                    dialog1.setButton(DialogInterface.BUTTON_NEGATIVE, "No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();//关闭对话框
                        }
                    });

                    dialog1.setButton(DialogInterface.BUTTON_POSITIVE, "Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();//关闭对话框
                        }
                    });
                    dialog1.show();//显示对话框

                }



                return true;
            }
        });
        adapter.setOnItemChangeListener(new MyAdapter.onItemChangeListener() {
            @Override
            public void onChangeClick(View v,int i) {



                ImageView imageView = (ImageView)v;
                String co=data.get(i).get("starnum");
               int num=Integer.parseInt(co);
//            UpdateInfo();

                String id=data.get(i).get("id");



                String name=data.get(i).get("name");

                Object e=imageView.getDrawable().getConstantState();
                Object a=getResources().getDrawable(R.drawable.white).getConstantState();
                Object r=getResources().getDrawable(R.drawable.red).getConstantState();
                 if( e.equals(a))
                 {
                     imageView.setImageResource(R.drawable.red);

                     num++;


                     myDB Db = new myDB(getBaseContext());
                     SQLiteDatabase db = Db.getWritableDatabase();


                     db.execSQL("INSERT INTO Star (CID, username) values (? ,?)",new String[]{id,currentuser});


                     db.close();

                     List<Map<String, String>> newdata = new ArrayList<Map<String, String>>();



                     data.get(i).put("starnum",String.valueOf(num));

                     data.get(i).put("status","1");






                     adapter.notifyDataSetChanged();


                 }
                 else{
                     imageView.setImageResource(R.drawable.white);

                     num--;

                     myDB db = new myDB(getBaseContext());
                     SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();
                     sqLiteDatabase.execSQL("delete from " + "Star"
                             + " where CID = ? AND username = ?", new String[]{id,name});
                     sqLiteDatabase.close();

                     data.get(i).put("status","0");
                     data.get(i).put("starnum",String.valueOf(num));






                     adapter.notifyDataSetChanged();

            }





                myDB db = new myDB(getBaseContext());
            SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();
                sqLiteDatabase.execSQL("update " + "Comment" +
                        " set starnum = ? where CID = ?", new Object[]{
                num, id});
                sqLiteDatabase.close();






            }
        });

        Button se=findViewById(R.id.send);
        se.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText ed=findViewById(R.id.co);
                String com=ed.getText().toString();
                if(com.equals(""))
                {
                    Toast.makeText(Comment.this, "Comment cannot be empty.", Toast.LENGTH_LONG).show();
                    return ;
                }
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// HH:mm:ss

                Date da = new Date(System.currentTimeMillis());

                String date=simpleDateFormat.format(da);






                myDB Db = new myDB(getBaseContext());
                SQLiteDatabase db = Db.getWritableDatabase();

                ContentValues cv = new ContentValues();
                cv.put("comment", com);
                cv.put("username", currentuser);
                cv.put("date", date);
                cv.put("starnum",0);

                long id= db.insert("Comment", null, cv);




                db.close();

                Map<String,String>temp = new LinkedHashMap<>();
                temp.put("name",currentuser);
                temp.put("date",date);
                temp.put("comment",com);
                temp.put("starnum","0");
                temp.put("status","0");
                temp.put("id",String.valueOf(id));
                data.add(temp);






                adapter.notifyDataSetChanged();

            }
        });




    }
    public void UpdateInfo()
    {
        myDB db = new myDB(getBaseContext());

        SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from "
                +"Comment", null);
        data  = new ArrayList<Map<String, String>>();
        if (cursor == null) {
        } else {
            while (cursor.moveToNext()) {
                int commentid=cursor.getInt(0);
                String comment = cursor.getString(1);
                String user=cursor.getString(2);
                String date=cursor.getString(3);
                int starnum=cursor.getInt(4);

                Map<String, String> map = new HashMap<String, String>();
                map.put("id",String.valueOf(commentid));
                map.put("comment", comment);
                map.put("name", user);
                map.put("date", date);
                map.put("starnum",String.valueOf(starnum));
                map.put("status","0");

                data.add(map);
            }

        }


        Cursor cursor1 = sqLiteDatabase.rawQuery("select * from "
                +"Star"+" where username = ?", new String[]{currentuser});

        if (cursor1 == null) {
        } else {
            while (cursor1.moveToNext()) {
                String comid2=cursor1.getString(0);
                String comid=cursor1.getString(1);
                String comid1=cursor1.getString(2);

            if(comid==null)comid="0";

                for( int i = 0 ; i < data.size() ; i++) {
                   String index=data.get(i).get("id");
                   if(comid.equals(index)){
                       data.get(i).put("status","1");
                       break;
                   }
                }



            }

        }

    }

}
