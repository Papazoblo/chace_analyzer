package alex.studio.csvsearcher.components;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.util.Log;

import androidx.documentfile.provider.DocumentFile;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import alex.studio.csvsearcher.enums.Properties;

public class DirectoryReader {

    private Context context;

    public DirectoryReader(Context context) {
        this.context = context;
    }

    public InputStream readDirectory(String dir) throws FileNotFoundException {
        Uri uri = Uri.parse(dir);
        File file = new File(dir);
        File[] files = file.listFiles();
        List<File> resultList = new ArrayList<>();

        if(files != null) {
            for (File f : files) {
                if (Objects.requireNonNull(f.getName()).endsWith(".csv")) {
                    resultList.add(f);
                }
            }
        }

        if(!resultList.isEmpty()) {
            return new FileInputStream(resultList.get(0));
        }
        return null;
    }
}
