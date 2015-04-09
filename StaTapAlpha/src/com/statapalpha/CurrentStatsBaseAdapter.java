package com.statapalpha;

import java.util.ArrayList;

import com.example.statapalpha.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


public class CurrentStatsBaseAdapter extends BaseAdapter {
    
    ArrayList<CurrentStatsListData> myList = new ArrayList<CurrentStatsListData>();
    LayoutInflater inflater;
    Context context;
    
    //test
    public CurrentStatsBaseAdapter(Context context, ArrayList<CurrentStatsListData> myList) {
            this.myList = myList;
            this.context = context;
            inflater = LayoutInflater.from(this.context);        // only context can also be used
    }

    @Override
    public int getCount() {
            return myList.size();
    }

    @Override
    public CurrentStatsListData getItem(int position) {
            return myList.get(position);
    }

    @Override
    public long getItemId(int position) {
            return position;
    }

    @SuppressLint("InflateParams") 
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
            MyViewHolder mViewHolder;
            
            if(convertView == null) {
                    convertView = inflater.inflate(R.layout.stat_list_listview, null);
                    mViewHolder = new MyViewHolder();
                    convertView.setTag(mViewHolder);
            } else {
                    mViewHolder = (MyViewHolder) convertView.getTag();
            }
            
            mViewHolder.tvPNum = detail(convertView, R.id.tvPNum, myList.get(position).getpNum(), position);
            mViewHolder.tvJNum  = detail(convertView, R.id.tvJNum,  myList.get(position).getjNum(), position);
            mViewHolder.tvPlay  = detail(convertView, R.id.tvPlay,  myList.get(position).getPlay(), position);
            mViewHolder.tvTime =  detail(convertView, R.id.tvTime, myList.get(position).getTime(), position);
            return convertView;
    }
    
    // or you can try better way
    private TextView detail(View v, int resId, String rawr, int position) {
            TextView tv = (TextView) v.findViewById(resId);
            tv.setText(rawr);
            if (!(myList.get(position).getHome())) {
            	tv.setTextColor(Color.parseColor("#FF0000"));
            	TextView blah = (TextView) v.findViewById(R.id.tvHashtag);
            	blah.setTextColor(Color.parseColor("#FF0000"));
            }
            return tv;
    }
    
    public class MyViewHolder {
            public TextView tvPNum, tvJNum, tvPlay, tvTime;
    }

}