package com.zephyr.myoteecopy;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.shizhefei.view.indicator.IndicatorViewPager;
import com.shizhefei.view.indicator.ScrollIndicatorView;
import com.shizhefei.view.indicator.slidebar.ColorBar;

import adapter.MyTabAdapter;
import helper.DataHelper;
import util.DisplayUtil;
import helper.SoundHelper;

/**
 * Created by Zephyr on 2016/7/12.
 */
public class CosplayActivity extends Activity implements View.OnClickListener{

    private final String TAG = "CosplayActivity";

    private RelativeLayout transitionView;
    private Button back, save, share;
    private WebView mWebView;
    private WebSettings mWebSettings;

    private IndicatorViewPager mIndicatorViewPager;
    private MyTabAdapter mMyTabAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cosplay);

        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        SoundHelper.getInstance().play(SoundHelper.BOY);
        mWebView.loadUrl("file:///android_asset/cosplay.html");
    }

    private void initView() {
        transitionView =(RelativeLayout) findViewById(R.id.transition);

        back = (Button) findViewById(R.id.cosplay_back);
        back.setOnClickListener(this);

        save = (Button) findViewById(R.id.cosplay_save);
        save.setOnClickListener(this);

        share = (Button) findViewById(R.id.cosplay_share);
        share.setOnClickListener(this);

        initWebView();

        initIndicatorView();
    }

    private void initWebView() {
        mWebView = (WebView) findViewById(R.id.cosplay_webview);
        mWebSettings = mWebView.getSettings();
        mWebSettings.setJavaScriptEnabled(true);
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                mWebView.loadUrl("javascript:initHeadEdit()");
                transitionView.setVisibility(View.GONE);
            }
        });
    }

    private void initIndicatorView() {
        ScrollIndicatorView mScrollIndicatorView = (ScrollIndicatorView) findViewById(R.id.cosplay_indicator);
        mScrollIndicatorView.setScrollBar(new ColorBar(this, 0xFF2196F3, 4));

        ViewPager mViewPager = (ViewPager) findViewById(R.id.cosplay_viewpager);
        mViewPager.setOffscreenPageLimit(5);

        mIndicatorViewPager = new IndicatorViewPager(mScrollIndicatorView, mViewPager);
        mMyTabAdapter = new MyTabAdapter(this);
        mMyTabAdapter.setOnItemClicked(mOnItemClicked);
        mIndicatorViewPager.setAdapter(mMyTabAdapter);
    }

    private MyTabAdapter.OnItemClicked mOnItemClicked = new MyTabAdapter.OnItemClicked() {
        @Override
        public void onClicked(int type, int position) {
            int id = DataHelper.calculateId(type, position);
            switch (type) {
                case DataHelper.FACE:
                    mWebView.loadUrl("javascript:faceChange("+String.valueOf(id)+")");
                    break;
                case DataHelper.HAIR:
                    mWebView.loadUrl("javascript:hairChange("+String.valueOf(id)+")");
                    break;
                case DataHelper.EYEBROW:
                    mWebView.loadUrl("javascript:eyebrowChange("+String.valueOf(id)+")");
                    break;
                case DataHelper.EYE:
                    mWebView.loadUrl("javascript:eyeChange("+String.valueOf(id)+")");
                    break;
                case DataHelper.MOUTH:
                    mWebView.loadUrl("javascript:mouthChange("+String.valueOf(id)+")");
                    break;
            }
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cosplay_back:
                finish();
                break;
            case R.id.cosplay_save:

                if (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    } else {
                        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
                    }
                } else {
                    saveWebViewPicture();
                }

                DisplayUtil.showToast(this, "save");
                break;
            case R.id.cosplay_share:
                DisplayUtil.showToast(this, "share");
                break;

        }
    }

    private void saveWebViewPicture() {
        mWebView.setDrawingCacheEnabled(true);
        mWebView.buildDrawingCache();
        DisplayUtil.SavePicture(Bitmap.createBitmap(mWebView.getDrawingCache()));
        mWebView.setDrawingCacheEnabled(false);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    saveWebViewPicture();
                }
        }
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        Log.i(TAG, "finalize");
    }
}
