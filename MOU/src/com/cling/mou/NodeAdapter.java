package com.cling.mou;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

public class NodeAdapter extends ArrayAdapter {
	private final int resourceId;
	public CheckBox cb;
	public boolean isShowCheckBox;
	public boolean isChecked;
	public int setCheckedId;
	public int is_txt;
	public int operatorId;
	public NodeAdapter(Context context, int textViewResourceId, List<Node> objects) {
		super(context, textViewResourceId, objects);
		resourceId = textViewResourceId;
	}
	
	public 	View getView(int position, View convertView, ViewGroup parent) {
		Node node = (Node) getItem(position);
		is_txt = node.isText();
		ViewHolder viewHolder = new ViewHolder();
		if(convertView == null){
			convertView = LayoutInflater.from(getContext()).inflate(R.layout.node_item, null);
			viewHolder.node_content = (TextView)convertView.findViewById(R.id.node_content);
			viewHolder.node_date = (TextView)convertView.findViewById(R.id.node_date);
			viewHolder.cb = (CheckBox)convertView.findViewById(R.id.cb_select);
			viewHolder.media = (ImageView)convertView.findViewById(R.id.is_media);
			convertView.setTag(viewHolder);
		}
		else{
			viewHolder = (ViewHolder)convertView.getTag();
		}
		viewHolder.node_content.setText(node.getTitle());
		viewHolder.node_date.setText(node.getDate());
		if (isShowCheckBox) {
            viewHolder.cb.setVisibility(cb.VISIBLE);
        } else {
            viewHolder.cb.setVisibility(cb.GONE);
        }
		if (isChecked) {
            viewHolder.cb.setChecked(true);
        } else {
            viewHolder.cb.setChecked(false);
        }
		if(position == setCheckedId) viewHolder.cb.setChecked(true);
		if(is_txt == 1) {
			viewHolder.media.setVisibility(8);
		} else {
			viewHolder.media.setVisibility(0);
		}
		return convertView;
	}
	public class ViewHolder{
    	TextView node_content;
    	TextView node_date;
    	CheckBox cb;
    	ImageView media;
    	public ViewHolder() {
    		super();
    	}
    }
}