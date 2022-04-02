package alex.studio.csvsearcher.enums;

import android.content.res.Resources;
import android.os.Environment;

import alex.studio.csvsearcher.R;

public enum Properties {

    CSV_LINK("csv_link", "https://www.pais.co.il/chance/chance_resultsDownload.aspx", 0),
    CSV_FOLDER("csv_folder", Environment.getExternalStorageDirectory() + "/Chance", 0),
    PDF_FOLDER("pdf_folder", null, 0),
    FIRST_LAUNCH("first_launch", null, 0),
    ALGO_1("algorithm_1_name", null, R.string.algo_name_1_set_val),
    ALGO_2("algorithm_2_name", null, R.string.algo_name_2_set_val),
    ALGO_3("algorithm_3_name", null, R.string.algo_name_3_set_val),
    ALGO_4("algorithm_4_name", null, R.string.algo_name_4_set_val),
    ALGO_5("algorithm_5_name", null, R.string.algo_name_5_set_val),
    ALGO_6("algorithm_6_name", null, R.string.algo_name_6_set_val);

    Properties(String key, String defaultVal, int resId) {
        this.defaultValue = defaultVal;
        this.resId = resId;
        this.key = key;
    }

    private final String key;
    private final String defaultValue;
    private final int resId;

    public String getDefaultValue() {
        return this.defaultValue;
    }

    public int getResId() {
        return this.resId;
    }

    public String getKey() {
        return this.key;
    }

    public static Properties fromKey(String key) {
        for (Properties prop : Properties.values()) {
            if (prop.key.equals(key)) {
                return prop;
            }
        }
        throw new Resources.NotFoundException();
    }
}
