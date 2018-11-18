package com.example.alias.storage;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyAdapter extends BaseAdapter {
    private Context mContext;
    private List<Map<String,String>> mList = new ArrayList<>();

    public MyAdapter(Context context, List<Map<String,String>> list) {
        mContext = context;
        mList = list;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int i) {
        return mList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (view == null) {
            viewHolder = new ViewHolder();
            view = LayoutInflater.from(mContext).inflate(R.layout.commentlist, null);
            viewHolder.mComment = view.findViewById(R.id.com);
            viewHolder.mUsername =  view.findViewById(R.id.username);
            viewHolder.mDate = view.findViewById(R.id.date);
            viewHolder.mStar= view.findViewById(R.id.star);
            viewHolder.mPicture= view.findViewById(R.id.imageView);
            viewHolder.mCount=view.findViewById(R.id.count);

            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.mComment.setText(mList.get(i).get("comment"));
        viewHolder.mUsername.setText(mList.get(i).get("name"));

        viewHolder.mDate.setText(mList.get(i).get("date"));
       String status=mList.get(i).get("status");
       if(status.equals("1"))
       {
           viewHolder.mStar.setImageResource(R.drawable.red);
       }

        String n=mList.get(i).get("starnum");
        viewHolder.mCount.setText(n);

        String username=mList.get(i).get("name");

        myDB db = new myDB(mContext);

        SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from "
                +"User"+ " where name = ?", new String[]{username});

        if (cursor == null) {
        } else {
            while (cursor.moveToNext()) {


                String uri=cursor.getString(3);
                Uri u=Uri.parse(uri);

//                viewHolder.mPicture.setImageURI(u);

            }

        }



        viewHolder.mStar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




              mOnItemChangeListener.onChangeClick(v,i);
            }
        });
        return view;
    }

    /**
     * 删除按钮的监听接口
     */
    public interface onItemChangeListener {
        void onChangeClick(View v,int i);
    }

    private onItemChangeListener mOnItemChangeListener;

    public void setOnItemChangeListener(onItemChangeListener mOnItemChangeListener) {
        this.mOnItemChangeListener = mOnItemChangeListener;
    }

    class ViewHolder {
        TextView mComment;
        TextView mUsername;
        TextView mDate;
      ImageView mPicture;
         ImageView mStar;
        TextView mCount;
    }

}
