package com.zephyr.myoteecopy;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ToggleButton;
import helper.SoundHelper;
import util.DisplayUtil;

public class MainActivity extends Activity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener{

    private Button chooseMan, share;
    private ImageButton shareClose;
    private ToggleButton sound;
    private AlertDialog mAlertDialog;
    private final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {
        chooseMan = (Button) findViewById(R.id.choose_man);
        chooseMan.setOnClickListener(this);

        share = (Button) findViewById(R.id.home_share);
        share.setOnClickListener(this);

        sound = (ToggleButton) findViewById(R.id.home_sound);
        sound.setOnCheckedChangeListener(this);
        if (sound.isChecked()) {
            SoundHelper.getInstance().resumeVolume();
        } else {
            SoundHelper.getInstance().keepQuit();
        }

        findViewById(R.id.home_feedback).setOnClickListener(this);

        initDialog();
    }

    private void initDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_share, null);
        builder.setView(view);
        mAlertDialog = builder.create();
        view.findViewById(R.id.share_close).setOnClickListener(this);
        view.findViewById(R.id.share_qq).setOnClickListener(this);
        view.findViewById(R.id.share_qzone).setOnClickListener(this);
        view.findViewById(R.id.share_wechat).setOnClickListener(this);
        view.findViewById(R.id.share_pyq).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.choose_man:
                Intent it = new Intent();
                it.setClass(MainActivity.this, CosplayActivity.class);
                startActivity(it);
                break;
            case R.id.home_share:
                mAlertDialog.show();
                break;
            case R.id.home_feedback:
                DisplayUtil.showToast(this, "成功反馈~");
                break;
            case R.id.share_qq:
                DisplayUtil.showToast(this, "分享到QQ");
                mAlertDialog.cancel();
                break;
            case R.id.share_qzone:
                DisplayUtil.showToast(this, "分享到QQ空间");
                mAlertDialog.cancel();
                break;
            case R.id.share_wechat:
                DisplayUtil.showToast(this, "分享到微信");
                mAlertDialog.cancel();
                break;
            case R.id.share_pyq:
                DisplayUtil.showToast(this, "分享到朋友圈");
                mAlertDialog.cancel();
                break;
            case R.id.share_close:
                mAlertDialog.cancel();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        SoundHelper.getInstance().release();
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        Log.i(TAG, "finalize");
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (buttonView.getId() == R.id.home_sound) {
            if (isChecked) {
                SoundHelper.getInstance().resumeVolume();
            } else {
                SoundHelper.getInstance().keepQuit();
            }
        }
    }
}
