package helper;

import android.support.annotation.IntDef;
import android.util.SparseIntArray;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;

import global.MyApplication;

/**
 * Created by Zephyr on 2016/7/21.
 */
public class DataHelper {
    private static String TAG = "DataHelper";

    public static final int HAIR = 0;
    public static final int FACE  = 1;
    public static final int EYEBROW = 2;
    public static final int EYE = 3;
    public static final int MOUTH = 4;
    @IntDef({HAIR, FACE, EYEBROW, EYE, MOUTH})
    @Retention(RetentionPolicy.SOURCE)
    public @interface IconType{}

    public static String[] iconType = {"hair", "face", "eyebrow", "eye", "mouth"};
    public static int[] iconNum = {75, 20, 31, 53, 48};
    public static final int iconTypeLength = iconType.length;
    private static int[] iconIdStart = new int[iconTypeLength];
    public static ArrayList<SparseIntArray> iconList = new ArrayList<>();

    private static volatile DataHelper instance;

    public static DataHelper getInstance() {
        if (instance == null) {
            synchronized (DataHelper.class) {
                if (instance == null) {
                    instance = new DataHelper();
                }
            }
        }
        return instance;
    }

    public void initIconData() {
        for (int i = 0; i < iconTypeLength; i++) {
            SparseIntArray sparseIntArray = new SparseIntArray();
            @IconType int type = i;
            switch (type) {
                case HAIR:
                    iconIdStart[i] = 0;
                    break;
                case FACE:
                    iconIdStart[i] = 20000;
                    break;
                default:
                    iconIdStart[i] = 1;
                    break;
            }
            for (int k = 0; k < iconNum[i]; k++) {
                StringBuilder stringBuilder = new StringBuilder(iconType[i]);
                stringBuilder.append("_");
                stringBuilder.append(calculateId(i, k));
                sparseIntArray.append(
                        k,
                        MyApplication.getAppContext().getResources().getIdentifier(stringBuilder.toString(), "mipmap", MyApplication.getAppContext().getPackageName()));
//                Log.i(TAG, MyApplication.getAppContext().getResources().getResourceName(sparseIntArray.get(k)));
            }
            iconList.add(sparseIntArray);
        }
    }

    /**
     * get the id e.g.. the id 20000 of face_20000(picture)
     * */
    public static int calculateId(int type, int offset) {
        return offset + iconIdStart[type];
    }
}
