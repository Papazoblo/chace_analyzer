package alex.studio.csvsearcher.components;

import android.content.Context;
import android.os.Environment;

import com.ironz.binaryprefs.BinaryPreferencesBuilder;
import com.ironz.binaryprefs.Preferences;

import alex.studio.csvsearcher.enums.Properties;

import static alex.studio.csvsearcher.enums.Properties.*;
import static alex.studio.csvsearcher.enums.Properties.FIRST_LAUNCH;
import static alex.studio.csvsearcher.enums.Properties.PDF_FOLDER;
import static alex.studio.csvsearcher.utils.Encripter.decript;
import static alex.studio.csvsearcher.utils.Encripter.encript;

public class StorageManager {

    private final String STORAGE_NAME = "csv_searcher";

    private Preferences preference;
    private Context context;

    public StorageManager(Context context) {
        this.context = context;
        preference = new BinaryPreferencesBuilder(context)
                .name(STORAGE_NAME)
                .build();
    }

    public void write(Properties key, String val) {
        preference.edit().putString(encript(key.name()),
                encript(val)).commit();
    }

    public String read(Properties key) {

        String result = decript(preference.getString(encript(key.name()), ""));

        if (key == CSV_LINK) {
            return result.isEmpty() ? "https://www.pais.co.il/chance/chance_resultsDownload.aspx"
                    : result;
        } else if(key == CSV_FOLDER){
            return result.isEmpty() ? Environment.getExternalStorageDirectory() + "/Chance"
                    : result;
        } else {
            return "";
        }
    }


    public void clearStorage() {
        write(CSV_LINK, "");
        write(CSV_FOLDER, "");
        write(PDF_FOLDER, "");
        write(FIRST_LAUNCH, "");
    }
}
