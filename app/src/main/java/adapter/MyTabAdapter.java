package adapter;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.shizhefei.view.indicator.IndicatorViewPager;
import com.zephyr.myoteecopy.R;

import java.util.ArrayList;

import helper.DataHelper;
import util.DisplayUtil;

/**
 * Created by Zephyr on 2016/7/21.
 */
public class MyTabAdapter extends IndicatorViewPager.IndicatorViewPagerAdapter {

    private final String TAG = "TabAdapter";
    private int[] tabsId;
    private Context mContext;
    private ArrayList<BaseAdapter> mAdapterArrayList;
    private OnItemClicked mOnItemClicked;

    //type * codeNum + offset = id in adapter, different from the id in DataHelper
    private final int codeNum = 100000;

    public MyTabAdapter(Context context) {
        mContext = context;
        TypedArray array = context.getResources().obtainTypedArray(R.array.tabs);
        tabsId = new int[array.length()];
        for (int i = 0; i < array.length(); i++) {
            tabsId[i] = array.getResourceId(i, R.drawable.xtab_hair);
        }
        array.recycle();

        initGridAdapter();
    }

    private void initGridAdapter() {
        mAdapterArrayList = new ArrayList<>();
        mAdapterArrayList.add(new MyBaseAdapter(DataHelper.HAIR));
        mAdapterArrayList.add(new MyBaseAdapter(DataHelper.FACE));
        mAdapterArrayList.add(new MyBaseAdapter(DataHelper.EYEBROW));
        mAdapterArrayList.add(new MyBaseAdapter(DataHelper.EYE));
        mAdapterArrayList.add(new MyBaseAdapter(DataHelper.MOUTH));
    }

    @Override
    public int getCount() {
        return tabsId.length;
    }

    @Override
    public View getViewForTab(int position, View convertView, ViewGroup container) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.tab_top, container, false);
        }
        ((ImageButton)convertView).setImageResource(tabsId[position]);
        return convertView;
    }

    public View getViewForPage(int position, View convertView, ViewGroup container) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.grid_bottom, container, false);
        }
        GridView gridView = (GridView)convertView;
        gridView.setOnItemClickListener(mOnItemClickListener);
        gridView.setAdapter(mAdapterArrayList.get(position));
        return convertView;
    }

    @Override
    public int getItemPosition(Object object) {
        //这是ViewPager适配器的特点,有两个值 POSITION_NONE，POSITION_UNCHANGED，默认就是POSITION_UNCHANGED,
        // 表示数据没变化不用更新.notifyDataChange的时候重新调用getViewForPage
        return PagerAdapter.POSITION_UNCHANGED;
    }

    public void setOnItemClicked(OnItemClicked onItemClicked) {
        this.mOnItemClicked = onItemClicked;
    }

    private AdapterView.OnItemClickListener mOnItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            try {
                int type = (int)id / codeNum;
                checkType(type);
                DisplayUtil.showToast(mContext, DataHelper.iconType[type] + position);
                mOnItemClicked.onClicked(type, position);
            } catch (Exception e) {
                Log.e("", "error: " + e);
            }
        }

        private void checkType(int type) {
            switch (type) {
                case DataHelper.FACE:
                case DataHelper.HAIR:
                case DataHelper.EYEBROW:
                case DataHelper.EYE:
                case DataHelper.MOUTH:
                    break;
                default:
                    new Exception("error type " + type);
            }
        }
    };

    public interface OnItemClicked {
        void onClicked(int type, int position);
    }

    private class MyBaseAdapter extends BaseAdapter {

        private @DataHelper.IconType int type;

        public MyBaseAdapter(@DataHelper.IconType int type) {
            this.type = type;
        }

        @Override
        public int getCount() {
            return DataHelper.iconNum[type];
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return type * codeNum + position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = new ImageView(mContext);
            }
            ((ImageView)convertView).setImageResource(DataHelper.iconList.get(type).get(position));
//            convertView.setBackgroundResource(DataHelper.iconList.get(type).get(position));
            return convertView;
        }
    }
}
