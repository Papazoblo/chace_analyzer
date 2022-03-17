package alex.studio.csvsearcher.components;

import android.content.Context;
import android.os.Environment;

import com.ironz.binaryprefs.BinaryPreferencesBuilder;
import com.ironz.binaryprefs.Preferences;

import alex.studio.csvsearcher.R;
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
        if(key.getDefaultValue() == null && key.getResId() != 0) {
            return read(key, context.getString(key.getResId()));
        } else {
            return read(key, key.getDefaultValue());
        }
    }

    public String read(Properties key, String defaultValue) {
        String val = preference.getString(encript(key.name()), null);
        return val == null ? defaultValue : decript(val);
    }

    public void clearStorage() {
        write(CSV_LINK, null);
        write(CSV_FOLDER, null);
        write(PDF_FOLDER, null);
        write(FIRST_LAUNCH, null);
    }
}
