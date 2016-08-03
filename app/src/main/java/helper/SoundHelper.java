package helper;

import android.annotation.TargetApi;
import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.support.annotation.IntDef;
import android.util.Log;

import com.zephyr.myoteecopy.R;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.HashMap;

import global.MyApplication;

/**
 * Created by Zephyr on 2016/7/28.
 */
public class SoundHelper {

    public static final int BOY = 1;
    public static final int SAVE_SUCCEED = 5;
    @IntDef({BOY, SAVE_SUCCEED})
    @Retention(RetentionPolicy.SOURCE)
    public @interface SoundId{}

    /**
     * volume radio:[0,1]
     * */
    private float lrRadio;
    private static final String TAG = "SoundHelper";
    private static volatile SoundHelper sSoundHelper;
    private SoundPool mSoundPool;
    private HashMap<Integer, Integer> soundPoolMap;
    private boolean isQuiet;

    public static SoundHelper getInstance() {
        if (sSoundHelper == null) {
            synchronized (SoundHelper.class) {
                if (sSoundHelper == null) {
                    sSoundHelper = new SoundHelper();
                }
            }
        }
        return sSoundHelper;
    }

    private SoundHelper() {
        if (mSoundPool == null) {
            mSoundPool = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
            soundPoolMap = new HashMap<>();
            isQuiet = false;
            LoadSound();
        }
    }

    private void LoadSound() {
        soundPoolMap.put(1, mSoundPool.load(MyApplication.getAppContext(),
                R.raw.boy, 1));
        soundPoolMap.put(5, mSoundPool.load(MyApplication.getAppContext(),
                R.raw.save_succeed, 1));
        resumeVolume();
    }

    public void play(@SoundId int id) {
        if (isQuiet) {
            return;
        }
        resumeVolume();
        int result = mSoundPool.play(soundPoolMap.get(id), lrRadio, lrRadio, 1, 0, 1);
    }

    public void setLrRadio(float level) {
        if (level < 0) {
            lrRadio = 0;
        } else if (level > 1) {
            lrRadio = 1;
        } else {
            lrRadio = level;
        }
    }

    public void resumeVolume() {
        isQuiet = false;
        AudioManager audioManager = (AudioManager) MyApplication.getAppContext().getSystemService(Context.AUDIO_SERVICE);
        int current = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        int max = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        setLrRadio(current / (float) max);
    }

    public void keepQuit() {
        isQuiet = true;
    }

    public void release() {
        //有bug 释放后程序重启后无法再load
//        mSoundPool.release();
    }
}
