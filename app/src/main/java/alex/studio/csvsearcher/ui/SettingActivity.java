package alex.studio.csvsearcher.ui;

import static alex.studio.csvsearcher.enums.Properties.ALGO_1;
import static alex.studio.csvsearcher.enums.Properties.ALGO_2;
import static alex.studio.csvsearcher.enums.Properties.ALGO_3;
import static alex.studio.csvsearcher.enums.Properties.ALGO_4;
import static alex.studio.csvsearcher.enums.Properties.ALGO_5;
import static alex.studio.csvsearcher.enums.Properties.ALGO_6;
import static alex.studio.csvsearcher.enums.Properties.CSV_FOLDER;
import static alex.studio.csvsearcher.enums.Properties.CSV_LINK;
import static alex.studio.csvsearcher.utils.FileUtil.getFullPathFromTreeUri;
import static alex.studio.csvsearcher.utils.ViewUtils.getTextFrom;
import static alex.studio.csvsearcher.utils.ViewUtils.isNotEmpty;

import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

import alex.studio.csvsearcher.R;
import alex.studio.csvsearcher.components.StorageManager;
import alex.studio.csvsearcher.enums.Properties;

public class SettingActivity extends AppCompatActivity {

    private static final int READ_REQUEST_CODE_CSV = 1;
    private static final int READ_REQUEST_CODE_PDF = 2;
    private static final int READ_REQUEST_CODE_IMPORT = 3;
    private static final int READ_REQUEST_CODE_EXPORT = 4;
    private static final String IMPORT_FILE_NAME = "application.properties";

    private EditText editCsvLink;
    private EditText editFirstAlgoName;
    private EditText editTwoAlgoName;
    private EditText editThreeAlgoName;
    private EditText editFourAlgoName;
    private EditText editFiveAlgoName;
    private EditText editSixAlgoName;

    private View btnChangeCsvFolder;
    private View btnSave;
    private View btnCancel;
    //private View btnImport;

    private TextView textCsvFolder;
    private StorageManager storageManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        storageManager = new StorageManager(SettingActivity.this);
        initView();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case READ_REQUEST_CODE_CSV:
                try {
                    textCsvFolder.setText(getFullPathFromTreeUri(data.getData(),
                            SettingActivity.this));
                } catch (Exception ex) {
                    textCsvFolder.setText("");
                }
                break;
            case READ_REQUEST_CODE_EXPORT:
                try {
                    exportToFile();
                    Toast.makeText(SettingActivity.this,
                            "Настройки успешно экспортированы", Toast.LENGTH_SHORT).show();
                } catch (Exception ex) {
                    Toast.makeText(SettingActivity.this,
                            "Невозможно экспортировать настройки", Toast.LENGTH_SHORT).show();
                }
                break;
            case READ_REQUEST_CODE_IMPORT:
                try {
                    importFromFile(data.getData());
                    Toast.makeText(SettingActivity.this,
                            "Настройки успешно импортрованы", Toast.LENGTH_SHORT).show();
                } catch (Exception ex) {
                    Toast.makeText(SettingActivity.this,
                            "Невозможно импортировать настройки", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private boolean isDataValid() {
        return isNotEmpty(editCsvLink, textCsvFolder/*, textPdfFolder*/);
    }

    private void initView() {
        editCsvLink = findViewById(R.id.editCsvLink);

        //btnImport = findViewById(R.id.btnImport);
        btnChangeCsvFolder = findViewById(R.id.btnChangeCsvFolder);
        //btnChangePdfFolder = findViewById(R.id.btnChangePdfFolder);

        btnCancel = findViewById(R.id.btnCancel);
        btnSave = findViewById(R.id.btnSave);

        textCsvFolder = findViewById(R.id.textCsvFolder);
        //textPdfFolder = findViewById(R.id.textPdfFolder);
        editFirstAlgoName = findViewById(R.id.editFirstAlgoName);
        editTwoAlgoName = findViewById(R.id.editTwoAlgoName);
        editThreeAlgoName = findViewById(R.id.editThreeAlgoName);
        editFourAlgoName = findViewById(R.id.editForAlgoName);
        editFiveAlgoName = findViewById(R.id.editFiveAlgoName);
        editSixAlgoName = findViewById(R.id.editSixAlgoName);

        initAction();
        initValue();
    }

    private void initAction() {

        //btnChangePdfFolder.setOnClickListener(v -> performFileSearch(READ_REQUEST_CODE_PDF));

        btnChangeCsvFolder.setOnClickListener(v -> performFileSearch(READ_REQUEST_CODE_CSV, true));

        btnCancel.setOnClickListener(v -> closeActivity());

        btnSave.setOnClickListener(v -> {
            try {
                saveAction();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        //btnImport.setOnClickListener(v -> performFileSearch(READ_REQUEST_CODE_IMPORT, false));
    }

    private void saveAction() throws IOException {
        if (isDataValid()) {
            StorageManager storageManager = new StorageManager(SettingActivity.this);
            storageManager.write(Properties.CSV_FOLDER, getTextFrom(textCsvFolder));
            //storageManager.write(Properties.PDF_FOLDER, getTextFrom(textPdfFolder));
            storageManager.write(CSV_LINK, getTextFrom(editCsvLink));
            storageManager.write(ALGO_1, getTextFrom(editFirstAlgoName));
            storageManager.write(ALGO_2, getTextFrom(editTwoAlgoName));
            storageManager.write(ALGO_3, getTextFrom(editThreeAlgoName));
            storageManager.write(ALGO_4, getTextFrom(editFourAlgoName));
            storageManager.write(ALGO_5, getTextFrom(editFiveAlgoName));
            storageManager.write(ALGO_6, getTextFrom(editSixAlgoName));
            //exportToFile();
            closeActivity();
            return;
        }

        Toast.makeText(SettingActivity.this, getResources()
                .getString(R.string.enter_all_fields), Toast.LENGTH_SHORT).show();
    }

    private void initValue() {
        editCsvLink.setText(storageManager.read(CSV_LINK));
        textCsvFolder.setText(storageManager.read(CSV_FOLDER));
        editFirstAlgoName.setText(storageManager.read(ALGO_1));
        editTwoAlgoName.setText(storageManager.read(ALGO_2));
        editThreeAlgoName.setText(storageManager.read(ALGO_3));
        editFourAlgoName.setText(storageManager.read(ALGO_4));
        editFiveAlgoName.setText(storageManager.read(ALGO_5));
        editSixAlgoName.setText(storageManager.read(ALGO_6));
        //textPdfFolder.setText(storageManager.read(PDF_FOLDER));
    }

    private void exportToFile() throws IOException {
        File file = new File(getTextFrom(textCsvFolder) + "/" + IMPORT_FILE_NAME);
        StringBuilder sb = new StringBuilder();
        sb.append(createSettingsString(CSV_LINK, editCsvLink));
        sb.append(createSettingsString(CSV_FOLDER, textCsvFolder));
        sb.append(createSettingsString(ALGO_1, editFirstAlgoName));
        sb.append(createSettingsString(ALGO_2, editTwoAlgoName));
        sb.append(createSettingsString(ALGO_3, editThreeAlgoName));
        sb.append(createSettingsString(ALGO_4, editFourAlgoName));
        sb.append(createSettingsString(ALGO_5, editFiveAlgoName));
        sb.append(createSettingsString(ALGO_6, editSixAlgoName));
        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        writer.write(sb.toString());
        writer.close();
    }

    private void importFromFile(Uri uri) throws IOException {
        if (!uri.toString().endsWith(".properties")) {
            Toast.makeText(SettingActivity.this, "Неверный формат файла",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        InputStream is = getContentResolver().openInputStream(uri);
        BufferedInputStream reader = new BufferedInputStream(is);
        byte[] info = new byte[reader.available()];
        reader.read(info, 0, info.length);
        String data = new String(info);
        String[] lines = data.split("\n");
        for (int i = 0; i < lines.length; i++) {
            if (!lines[i].isEmpty()) {
                String[] values = lines[i].split(":", 2);
                if (values.length == 2) {
                    try {
                        Properties prop = Properties.fromKey(values[0]);
                        storageManager.write(prop, values[1]);
                        getTextViewByProp(prop).setText(values[1]);
                    } catch (Resources.NotFoundException ex) {
                        Log.d("IMPORT", "Resource not found exception");
                    }
                }
            }
        }
        is.close();
        reader.close();
    }

    private TextView getTextViewByProp(Properties prop) {
        TextView view;
        switch (prop) {
            case ALGO_1:
                view = editFirstAlgoName;
                break;
            case ALGO_2:
                view = editTwoAlgoName;
                break;
            case ALGO_3:
                view = editThreeAlgoName;
                break;
            case ALGO_4:
                view = editFourAlgoName;
                break;
            case ALGO_5:
                view = editFiveAlgoName;
                break;
            case ALGO_6:
                view = editSixAlgoName;
                break;
            case CSV_FOLDER:
                view = textCsvFolder;
                break;
            case CSV_LINK:
                view = editCsvLink;
                break;
            default:
                throw new Resources.NotFoundException();
        }
        return view;
    }

    private String createSettingsString(Properties prop, View editText) {
        return String.format("%s:%s\n", prop.getKey(), getTextFrom(editText));
    }

    private void performFileSearch(final int requestCode, boolean selectFolder) {
        Intent i = new Intent(selectFolder ? Intent.ACTION_OPEN_DOCUMENT_TREE :
                Intent.ACTION_GET_CONTENT);
        i.addCategory(Intent.CATEGORY_DEFAULT);
        if (!selectFolder) {
            i.setType("*/*");
            i.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
        }
        startActivityForResult(Intent.createChooser(i, selectFolder ? "Выберите папку" :
                "Выберите файл"), requestCode);
    }

    private void closeActivity() {
        startActivity(new Intent(SettingActivity.this, StartActivity.class));
        finish();
    }

    @Override
    public void onBackPressed() {
        closeActivity();
    }
}
