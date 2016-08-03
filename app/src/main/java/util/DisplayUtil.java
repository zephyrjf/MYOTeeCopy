package util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Environment;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;

import global.MyApplication;

public class DisplayUtil {
    private static final String TAG = "DisplayUtil";
    /**
     * 根据dip值转化成px值
     *
     * @param context
     * @param dip
     * @return
     */
    public static int dipToPix(Context context, int dip) {
        int size = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, context.getResources().getDisplayMetrics());
        return size;
    }

    public static BitmapDrawable getDrawbleFromId(Context context, int id) {
        BitmapFactory.Options opt = new BitmapFactory.Options();

        opt.inPreferredConfig = Bitmap.Config.RGB_565;

        opt.inPurgeable = true;

        opt.inInputShareable = true;

        InputStream is = context.getResources().openRawResource(id);

        Bitmap bm = BitmapFactory.decodeStream(is, null, opt);

        BitmapDrawable bd = new BitmapDrawable(context.getResources(), bm);
        return bd;
    }

    private static Toast sToast;
    public static void showToast(Context context, String text) {
        if (sToast == null) {
            sToast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
        } else {
            sToast.setText(text);
        }
        sToast.show();
    }

    public static void SavePicture(Bitmap bitmap) {
        File sdCard = Environment.getExternalStorageDirectory();
        sdCard = new File(sdCard, "MYOTEECopy");
        sdCard.mkdirs();

        sdCard = new File(sdCard, System.currentTimeMillis() + ".png");
        try {
            FileOutputStream out = new FileOutputStream(sdCard);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean isExternalStorageWritable() {
        String externalStorageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(externalStorageState)) {
            return true;
        }
        return false;
    }

    public static boolean isExternalStorageReadable() {
        String externalStorageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(externalStorageState) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(externalStorageState)) {
            return true;
        }
        return false;
    }

    public static File saveExternalFile(String filename) {
//        File file = new File(MyApplication.getAppContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES),filename);
//        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), filename);
        File file = new File(Environment.getExternalStorageDirectory(), filename);
        Log.i(TAG, file.getAbsolutePath());
        if (!file.mkdir()) {
            Log.i(TAG, "file mk failed");
        }
        return file;
    }
}
