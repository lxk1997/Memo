package com.cling.memo;

import android.os.Parcel;
import android.os.Parcelable;

public class Node implements Parcelable{
	public static int id;
	private String title;
	private String content;
	private String date;
	private String time;
	
	public Node(String _title, String _content, String _date, String _time) {
		this.title = _title;
		this.content = _content;
		this.date = _date;
		this.time = _time;
		id++;
	}
	
	public Node() {
		super();
	}

	public void setTitle(String _title) {
		// TODO Auto-generated method stub
		this.title = _title;
	}
	
	public void setContent(String _content) {
		// TODO Auto-generated method stub
		this.content = _content;
	}

	public void setDate(String _date) {
		// TODO Auto-generated method stub
		this.date = _date;
	}
	
	public void setTime(String _time) {
		// TODO Auto-generated method stub
		this.time = _time;
	}
	
	public String getTitle() {
		// TODO Auto-generated method stub
		return title;
	}
	
	public String getContent() {
		// TODO Auto-generated method stub
		return content;
	}

	public String getDate() {
		// TODO Auto-generated method stub
		return date;
	}
	
	public String getTime() {
		return time;
	}
	public static final Creator<Node> CREATOR = new Creator<Node>() {
        @Override
        public Node createFromParcel(Parcel in) {
            return new Node(in.readString(),in.readString(), in.readString(), in.readString());
        }

        @Override
        public Node[] newArray(int size) {
            return new Node[size];
        }
    };
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeString(getTitle());
	    dest.writeString(getContent());
	    dest.writeString(getDate());
	    dest.writeString(getTime());   
	}
	
}
