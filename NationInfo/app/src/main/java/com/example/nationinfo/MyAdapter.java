package com.example.nationinfo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class MyAdapter extends BaseAdapter {

    Context _context;
    ArrayList<NationItem> _nationList;

    public MyAdapter(Context context, ArrayList<NationItem> nationList){
        _context = context;
        _nationList = nationList;
    }

    @Override
    public int getCount() {
        return _nationList.size();
    }

    @Override
    public Object getItem(int position) {
        return _nationList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View row = convertView;
        if(row == null) {
            row = LayoutInflater.from(_context).inflate(R.layout.row, parent, false);
        }

        TextView nationName = row.findViewById(R.id.nationName);
        nationName.setText(_nationList.get(position).getNationName());

        return row;
    }
}
