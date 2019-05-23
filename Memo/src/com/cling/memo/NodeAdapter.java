﻿package com.cling.memo;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class NodeAdapter extends ArrayAdapter {
	private final int resourceId;
	
	public NodeAdapter(Context context, int textViewResourceId, List<Node> objects) {
		super(context, textViewResourceId, objects);
		resourceId = textViewResourceId;
	}
	
	public 	View getView(int position, View convertView, ViewGroup parent) {
		Node node = (Node) getItem(position); // 获取当前项的Node实例
        View view = LayoutInflater.from(getContext()).inflate(resourceId, null);//实例化一个对象
        
        TextView node_content = (TextView)view.findViewById(R.id.node_content);
        TextView node_date = (TextView) view.findViewById(R.id.node_date);//获取该布局内的文本视图
        node_content.setText(node.getTitle());
        node_date.setText(node.getDate());
        return view;

	}
}