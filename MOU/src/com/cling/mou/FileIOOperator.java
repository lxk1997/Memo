package com.cling.mou;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

import android.content.Context;
import android.widget.Toast;

/*文件IO类
 */

public class FileIOOperator {
	
	public FileIOOperator() {
		File destDir = new File("/sdcard/mou/text");//创建文本文件夹
		if (!destDir.exists()) {
		   destDir.mkdirs();
		}
		destDir = new File("/sdcard/mou/audio");//创建音频文件夹
		if (!destDir.exists()) {
		   destDir.mkdirs();
		}
		destDir = new File("/sdcard/mou/collect/text");//创建收藏文本文件夹
		if (!destDir.exists()) {
		   destDir.mkdirs();
		}
		destDir = new File("/sdcard/mou/collect/audio");//创建收藏音频文件夹
		if (!destDir.exists()) {
		   destDir.mkdirs();
		}
	}
	
	//将文件保存到sd:
	//1 文本文件
	//2 音频文件
	//3 收藏文本
	//4 收藏音频
	
	
	public void saveToSDCard(Context context, String fromPath, String toPath, int operatorId) throws Throwable {
		File file;
		InputStream inStream = null;
		switch (operatorId) {
		case 1: 
			inStream = context.getResources().openRawResource(R.raw.test_txt);
		break;
		case 2:
			inStream = context.getResources().openRawResource(R.raw.test_media);
		break;
		case 3:
		case 4:
			file = new File(fromPath);
			inStream = new FileInputStream(file);
		break;
		}
		file = new File(toPath);
	    if (!file.exists()) {
	    	File dir = new File(file.getParent());
			dir.mkdirs();
			file.createNewFile();
		}
	    FileOutputStream fileOutputStream = new FileOutputStream(file);//存入SDCard
	    byte[] buffer = new byte[10];
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        int len = 0;
	    while((len = inStream.read(buffer)) != -1) {
	        outStream.write(buffer, 0, len);
	    }
	    byte[] bs = outStream.toByteArray();
	    fileOutputStream.write(bs);
	    outStream.close();
	    inStream.close();
	    fileOutputStream.flush();
	    fileOutputStream.close();
	}
	public String loadTxtFromSDFile(Context context, String path) {
        String result=null;
        try {
            File f=new File(path);
            int length=(int)f.length();
            byte[] buff=new byte[length];
            FileInputStream fin=new FileInputStream(f);
            fin.read(buff);
            fin.close();
            result=new String(buff,"UTF-8");
        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(context,"没有找到指定文件",Toast.LENGTH_SHORT).show();
        }
        return result;
    }
	
	//收藏为本地文件
	public void writeIntoSD(String filePath, String content) {
		try {
			byte[] b_utf8 = content.getBytes("UTF-8");
			content = new String(b_utf8, "UTF-8");
			File file = new File(filePath);
			if (!file.exists()) {
				File dir = new File(file.getParent());
				dir.mkdirs();
				file.createNewFile();
			}
			FileOutputStream outStream = new FileOutputStream(file);
			outStream.write(content.getBytes());
			outStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
		
	//删除本地文件
	public void deleteFromSD(File file) {
		file.delete();
	}
}
