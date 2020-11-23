package ace.infosolutions.tournyapp.utils;

import android.content.Context;
import android.os.Environment;

import java.io.File;

public class AbsoluteFilePath {

    public static File getAbsolutePath(String relativePath, Context context) {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            return new File(context.getExternalFilesDir(null), relativePath);
        } else {
            return new File(context.getFilesDir(), relativePath);
        }
    }
}
