package com.example.statapalpha;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
 
public class StatBaseAdapter extends BaseAdapter {
        
        ArrayList<StatListData> myList = new ArrayList<StatListData>();
        LayoutInflater inflater;
        Context context;
        
        
        public StatBaseAdapter(Context context, ArrayList<StatListData> myList) {
                this.myList = myList;
                this.context = context;
                inflater = LayoutInflater.from(this.context);        // only context can also be used
        }
 
        @Override
        public int getCount() {
                return myList.size();
        }
 
        @Override
        public StatListData getItem(int position) {
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
                        convertView = inflater.inflate(R.layout.stat_listview, null);
                        mViewHolder = new MyViewHolder();
                        convertView.setTag(mViewHolder);
                } else {
                        mViewHolder = (MyViewHolder) convertView.getTag();
                }
                
                mViewHolder.tvNum = detail(convertView, R.id.textViewNum, myList.get(position).getJersey());
                mViewHolder.tvP  = detail(convertView, R.id.textViewP,  myList.get(position).getPoints());
                mViewHolder.tvFTA  = detail(convertView, R.id.textViewFTA,  myList.get(position).getFTA());
                mViewHolder.tvFTM  = detail(convertView, R.id.textViewFTM,  myList.get(position).getFTM());
                mViewHolder.tvFGA  = detail(convertView, R.id.textViewFGA,  myList.get(position).getFGA());
                mViewHolder.tvFGM  = detail(convertView, R.id.textViewFGM,  myList.get(position).getFGM());
                mViewHolder.tvTPA  = detail(convertView, R.id.textViewTPA,  myList.get(position).getTPA());
                mViewHolder.tvTPM  = detail(convertView, R.id.textViewTPM,  myList.get(position).getTPM());
                mViewHolder.tvA  = detail(convertView, R.id.textViewA,  myList.get(position).getAssists());
                mViewHolder.tvBL  = detail(convertView, R.id.textViewBL,  myList.get(position).getBlocks());
                mViewHolder.tvRB  = detail(convertView, R.id.textViewRB,  myList.get(position).getRebounds());
                mViewHolder.tvS  = detail(convertView, R.id.textViewS,  myList.get(position).getSteals());
                mViewHolder.tvTO  = detail(convertView, R.id.textViewTO,  myList.get(position).getTO());
                mViewHolder.tvF  = detail(convertView, R.id.textViewF,  myList.get(position).getFouls());
                return convertView;
        }
        
        // or you can try better way
        private TextView detail(View v, int resId, int num) {
                TextView tv = (TextView) v.findViewById(resId);
                String rawr = Integer.toString(num);
                tv.setText(rawr);
                return tv;
        }
        
        private class MyViewHolder {
                public TextView tvNum, tvP, tvFTA, tvFTM, tvFGA, tvFGM, tvTPA, tvTPM, tvA, tvRB, tvS, tvTO, tvF, tvBL;
        }
 
}