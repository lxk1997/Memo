package com.cling.mou;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;

/*音频文件类
 */

public class MusicService extends Service {
    public final IBinder binder = new MyBinder();
    public class MyBinder extends Binder {
        MusicService getService() {
            return MusicService.this;
        }
    }
    public String file_path;
    public static MediaPlayer mediaPlayer = new MediaPlayer();
    public MusicService(String path) {
    	file_path = path;
        initMediaPlayer();

    }

    public void initMediaPlayer() {
        try {
            mediaPlayer.setDataSource(file_path);
            mediaPlayer.prepare();
            mediaPlayer.setLooping(true);  // 设置循环播放
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private int flag = 0;
	public void playOrPause() {
        flag++;
        if (flag >= 1000) flag = 2;

        if(mediaPlayer.isPlaying()){
            mediaPlayer.pause();
        } else {
            mediaPlayer.start();
        }
    }
	public void Pause() {
		if(mediaPlayer.isPlaying()){
            mediaPlayer.pause();
        }
		mediaPlayer.stop();
	}

    @Override
    public void onDestroy() {
        mediaPlayer.stop();
        mediaPlayer.release();
        mediaPlayer=null;
        super.onDestroy();
    }
    /**
     * onBind 是 Service 的虚方法，因此我们不得不实现它。
     * 返回 null，表示客服端不能建立到此服务的连接。
     */
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

}