package com.cling.mou;

/*单个条目节点类
 * 实现单个条目的构造、set、get功能，Parcelable接口方便Intent不同Activity之间传递Node对象
 * 1.全局变量id防止每次进入MainActivity之后都调用nodeInit()，连接添加功能后删除即可
 * 2.date显示在ListView中，time显示在编辑备忘录细节中
 */

public class Node {
	public static int id;
	private int is_txt;
	private String title;
	private String content;
	private String date;
	private String time;
	
	public Node(String _title, String _content, String _date, String _time, int _is_txt) {
		this.title = _title;
		this.content = _content;
		this.date = _date;
		this.time = _time;
		this.is_txt = _is_txt;
		id++;
	}
	
//	public Node(Node rhs) {
//		this.title = rhs.title;
//		this.content = rhs.content;
//		this.date = rhs.date;
//		this.time = rhs.time;
//		this.is_txt = rhs.is_txt;
//		id++;
//	}
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
	public int isText() {
		return is_txt;
	}
	public void setisText(int isTxt) {
		this.is_txt = isTxt;
	}
}
