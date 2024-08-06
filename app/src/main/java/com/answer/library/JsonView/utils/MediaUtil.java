package com.answer.library.JsonView.utils;

import android.media.*;
import com.answer.library.JsonView.*;
import com.answer.library.JsonView.debug.*;
import java.io.*;
import java.util.*;

/**
 * @Author AnswerDev
 * @Date 2023/03/17 23:17
 * @Describe Media工具
 */
public class MediaUtil {

    public static final String TAG = "MediaUtil";
    
    public static void playMusic(String fileName) {
        String localDir = JsonManager.MainContext.getFilesDir().getAbsolutePath();
        File file = new File(localDir + File.separator + fileName);

        MediaPlayer mMediaPlayer = new MediaPlayer();
        try {
			FileInputStream fis = new FileInputStream(file);
            mMediaPlayer.setDataSource(fis.getFD());
            mMediaPlayer.setLooping(false);
            mMediaPlayer.prepare();
            mMediaPlayer.start();

            final FileInputStream finalFis = fis;
			final File finalFile = file;
            mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
					@Override
					public void onCompletion(MediaPlayer mp) {
						mp.reset();
						mp.release();
						mp = null;
						try {
							finalFis.close(); // This is redundant due to try-with-resources
						} catch (IOException e) {
							JsonLog.e(TAG, e.getMessage());
						}
					
					}
				});

        } catch (IOException e) {
            if (mMediaPlayer != null) {
                mMediaPlayer.reset();
                mMediaPlayer.release();
            }
            JsonLog.e(TAG, e.getMessage());
        }
    }
}

