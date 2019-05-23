//package com.example.sdustcamp;
//
//import java.util.List;
//
//
//import android.content.Context;
//import android.util.Log;
//import cn.bmob.v3.BmobQuery;
//import cn.bmob.v3.exception.BmobException;
//import cn.bmob.v3.listener.FindListener;
//import cn.bmob.v3.listener.SaveListener;
//import cn.bmob.v3.listener.UpdateListener;
//
//public class BmobOperator {
//	private static TableUser user;
//	private static Context context;
//	private static String ans="1234";
//	private static  String password;
//	private static  boolean is_registed;
//
//	public BmobOperator() {
//		this.is_registed = false;
//		this.password = null;
//	}
//	public void add(Context context, TableUser user) {
//		this.context = context;
//		this.user = user;
//		user.save(new SaveListener<String>() {
//
//			@Override
//			public void done(String arg0, BmobException arg1) {
//				// TODO Auto-generated method stub
//				if(arg1 == null) {
//					Log.i("数据插入成功", "1111");
//				} else {
//					Log.i("数据插入失败", "1111");
//				}
//			}
//		});
//	}
//
//	public void delete(Context context, TableUser user) {
//		this.context = context;
//		this.user = user;
//		user.delete(new UpdateListener() {
//
//			@Override
//			public void done(BmobException arg0) {
//				// TODO Auto-generated method stub
//				if(arg0 == null) {
//					Log.i("数据删除成功","22222");
//				} else {
//					Log.i("数据删除失败","22222");
//				}
//			}
//		});
//	}
//
//	public void update(Context context, TableUser user) {
//		this.context = context;
//		this.user = user;
//		user.update(new UpdateListener() {
//
//			@Override
//			public void done(BmobException arg0) {
//				// TODO Auto-generated method stub
//				if(arg0 == null) {
//					Log.i("数据更新成功","22222");
//				} else {
//					Log.i("数据更新失败","22222");
//				}
//			}
//		});
//	}
//
//	public void queryAll(Context context, final String phoneNum) {
//		this.context = context;
//
//		BmobQuery<TableUser> bmobQuery = new BmobQuery<TableUser>();
//		bmobQuery.findObjects(new FindListener<TableUser>() {
//
//			@Override
//			public void done(List<TableUser> object, BmobException arg1) {
//				// TODO Auto-generated method stub
//				if(arg1 == null) {
//					Log.i("查询成功：共" + object.size() + "条数据。","123");
//					is_registed = false;
//					for(TableUser t: object) {
//	                	Log.i("id",t.getPhoneNum());
//	                	Log.i("key",t.getPassword());
//	                	if(t.getPhoneNum().equals(phoneNum.toString().trim())) {
//	                		BmobOperator.password = t.getPassword();
//	                		BmobOperator.is_registed = true;
//	                		break;
//	                	}
//	                }
//				} else {
//					Log.i("查询失败","123");
//				}
//			}
//		});
//	}
//
//	public String getPassword() {
//		return BmobOperator.password;
//	}
//
//	public Boolean getIsRegisted() {
//		return BmobOperator.is_registed;
//	}
//}
