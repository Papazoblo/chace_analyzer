package alex.studio.csvsearcher.enums;

import android.os.Environment;

import alex.studio.csvsearcher.R;

public enum Properties {

    CSV_LINK("https://www.pais.co.il/chance/chance_resultsDownload.aspx", 0),
    CSV_FOLDER(Environment.getExternalStorageDirectory() + "/Chance", 0),
    PDF_FOLDER(null, 0),
    FIRST_LAUNCH(null, 0),
    ALGO_1(null, R.string.algo_name_1_set_val),
    ALGO_2(null, R.string.algo_name_2_set_val),
    ALGO_3(null, R.string.algo_name_3_set_val),
    ALGO_4(null, R.string.algo_name_4_set_val),
    ALGO_5(null, R.string.algo_name_5_set_val),
    ALGO_6(null, R.string.algo_name_6_set_val);

    Properties(String defaultVal, int resId) {
        this.defaultValue = defaultVal;
        this.resId = resId;
    }

    private final String defaultValue;
    private final int resId;

    public String getDefaultValue() {
        return this.defaultValue;
    }

    public int getResId() {
        return this.resId;
    }
}
