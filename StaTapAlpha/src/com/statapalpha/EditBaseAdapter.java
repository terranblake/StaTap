package com.statapalpha;

import java.util.ArrayList;

import com.example.statapalpha.R;
import com.example.statapalpha.R.id;
import com.example.statapalpha.R.layout;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


public class EditBaseAdapter extends BaseAdapter {
    
    ArrayList<EditListData> myList = new ArrayList<EditListData>();
    LayoutInflater inflater;
    Context context;
    
    //test
    public EditBaseAdapter(Context context, ArrayList<EditListData> myList) {
            this.myList = myList;
            this.context = context;
            inflater = LayoutInflater.from(this.context);        // only context can also be used
    }

    @Override
    public int getCount() {
            return myList.size();
    }

    @Override
    public EditListData getItem(int position) {
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
                    convertView = inflater.inflate(R.layout.namelistview, null);
                    mViewHolder = new MyViewHolder();
                    convertView.setTag(mViewHolder);
            } else {
                    mViewHolder = (MyViewHolder) convertView.getTag();
            }
            
            mViewHolder.tvJ = detail(convertView, R.id.tvJ, myList.get(position).getJ());
            mViewHolder.tvF  = detail(convertView, R.id.tvfname,  myList.get(position).getFName());
            mViewHolder.tvL  = detail(convertView, R.id.tvlname,  myList.get(position).getLName());
            return convertView;
    }
    
    // or you can try better way
    private TextView detail(View v, int resId, String rawr) {
            TextView tv = (TextView) v.findViewById(resId);
            tv.setText(rawr);
            return tv;
    }
    
    public class MyViewHolder {
            public TextView tvJ, tvF, tvL;
    }

}