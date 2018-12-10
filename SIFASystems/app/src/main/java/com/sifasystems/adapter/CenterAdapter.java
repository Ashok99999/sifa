package com.sifasystems.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.sifasystems.R;
import com.sifasystems.model.Center;
import com.sifasystems.model.Center;

import java.util.ArrayList;

public class CenterAdapter extends ArrayAdapter<Center>{

    private final Context mContext;
    private ArrayList<Center> dataSet;

    public CenterAdapter(ArrayList<Center> data, Context context) {
        super(context, R.layout.list_adapter_center_details, data);
        this.dataSet = data;
        this.mContext=context;
    }
    @Override
    public Center getItem(int position){
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
        CenterAdapter.AppInfoHolder holder = null;
        Center Center = getItem(position);
        if(itemView == null) {
            inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            itemView = (View)inflater.inflate(R.layout.list_adapter_center_details, parent, false);

            holder = new CenterAdapter.AppInfoHolder();

            holder.tvCenterNumber = (TextView) itemView.findViewById(R.id.tv_center_id);
            holder.tvSchoolName = (TextView) itemView.findViewById(R.id.tv_school_name);
            itemView.setTag(holder);
        }
        else {
            holder = (CenterAdapter.AppInfoHolder)itemView.getTag();
        }

        holder.tvCenterNumber.setText(Center.centerID);
        holder.tvSchoolName.setText(Center.schoolName);

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
        TextView tvCenterNumber;
        TextView tvSchoolName;
    }

}
