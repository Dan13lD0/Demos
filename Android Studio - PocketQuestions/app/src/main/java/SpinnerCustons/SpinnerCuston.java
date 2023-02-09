package SpinnerCustons;


import android.app.Activity;
import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.pocketquestions.R;


import java.util.List;

public class SpinnerCuston extends BaseAdapter {
    private List<String>lstData;
    private Activity activity;
    private LayoutInflater inflater;

    public SpinnerCuston(List<String> lstData, Activity activity) {
        this.lstData = lstData;
        this.activity = activity;
        this.inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return lstData.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view =convertView;
        if (convertView == null)
            view = inflater.inflate(R.layout.layout_spinner,null);
        TextView tv = (TextView)view.findViewById(R.id.txtDescricao);
        tv.setText(lstData.get(position));
        return  view;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        View view = super.getDropDownView(position,convertView,parent);
        LinearLayout li = (LinearLayout)view;
        TextView tv = (TextView)li.findViewById(R.id.txtDescricao);
        tv.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT));
        return view;
    }
}
