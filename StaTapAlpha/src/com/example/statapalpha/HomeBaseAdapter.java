package com.example.statapalpha;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


public class HomeBaseAdapter extends BaseAdapter {
    
    ArrayList<HomeListData> myList = new ArrayList<HomeListData>();
    LayoutInflater inflater;
    Context context;
    
    
    public HomeBaseAdapter(Context context, ArrayList<HomeListData> myList) {
            this.myList = myList;
            this.context = context;
            inflater = LayoutInflater.from(this.context);        // only context can also be used
    }

    @Override
    public int getCount() {
            return myList.size();
    }

    @Override
    public HomeListData getItem(int position) {
            return myList.get(position);
    }

    @Override
    public long getItemId(int position) {
            return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
            MyViewHolder mViewHolder;
            
            if(convertView == null) {
                    convertView = inflater.inflate(R.layout.homelistview, null);
                    mViewHolder = new MyViewHolder();
                    convertView.setTag(mViewHolder);
            } else {
                    mViewHolder = (MyViewHolder) convertView.getTag();
            }
            
            mViewHolder.tvG = detail(convertView, R.id.tvG, myList.get(position).getGame());
            mViewHolder.tvT1  = detail(convertView, R.id.tvT1,  myList.get(position).getTeam1());
            mViewHolder.tvT2  = detail(convertView, R.id.tvT2,  myList.get(position).getTeam2());
            return convertView;
    }
    
    // or you can try better way
    private TextView detail(View v, int resId, String rawr) {
            TextView tv = (TextView) v.findViewById(resId);
            tv.setText(rawr);
            return tv;
    }
    
    public class MyViewHolder {
            public TextView tvG, tvT1, tvT2;
    }

}