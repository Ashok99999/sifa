package com.sifasystems.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.sifasystems.R;
import com.sifasystems.model.User;
import java.io.File;
import java.util.ArrayList;

public class UserAdapter extends ArrayAdapter<User> {
    private final Context mContext;
    private ArrayList<User> dataSet;

    public UserAdapter(ArrayList<User> data, Context context) {
        super(context, R.layout.list_adapter_user_cell, data);
        this.dataSet = data;
        this.mContext=context;
    }
    @Override
    public User getItem(int position){
        return dataSet.get(position);
    }

    @Override
    public int getCount() {
        return dataSet.size();
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        View itemView = convertView;
        LayoutInflater inflater;
        AppInfoHolder holder = null;
        User user = getItem(position);
        if(itemView == null) {
            inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            itemView = (View)inflater.inflate(R.layout.list_adapter_user_cell, parent, false);

            holder = new AppInfoHolder();

            holder.txt_id = (TextView) itemView.findViewById(R.id.txt_id);
            holder.txt_name = (TextView) itemView.findViewById(R.id.txt_name);
            holder.txt_location = (TextView) itemView.findViewById(R.id.txt_location);
            holder.txt_updated_at = (TextView) itemView.findViewById(R.id.txt_updated_at);
            itemView.setTag(holder);
        }
        else {
            holder = (AppInfoHolder)itemView.getTag();
        }

        holder.txt_id.setText(user.key);
        holder.txt_name.setText(String.format("%s %s", user.fname, user.sname));
        holder.txt_location.setText(user.location);
        holder.txt_updated_at.setText(user.updated_at);

//        holder.btn_health_issue.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ((ListView) parent).performItemClick(v, position, 0); // Let the event be handled in onItemClick()
//            }
//        });

        return itemView;
    }

    static class AppInfoHolder
    {
        TextView txt_id;
        TextView txt_name;
        TextView txt_location;
        TextView txt_updated_at;
    }
}
