package alex.studio.csvsearcher.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import alex.studio.csvsearcher.R;
import alex.studio.csvsearcher.components.StorageManager;
import alex.studio.csvsearcher.enums.Properties;

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
import static alex.studio.csvsearcher.utils.ViewUtils.isEmpty;
import static alex.studio.csvsearcher.utils.ViewUtils.isNotEmpty;

import java.util.Optional;

public class SettingActivity extends AppCompatActivity {

    private final int READ_REQUEST_CODE_CSV = 1;
    private final int READ_REQUEST_CODE_PDF = 2;

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

    private TextView textCsvFolder;
    //private TextView textPdfFolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        initView();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == READ_REQUEST_CODE_CSV) {
            try {
                textCsvFolder.setText(getFullPathFromTreeUri(data.getData(),
                        SettingActivity.this));
            } catch (Exception ex) {
                textCsvFolder.setText("");
            }
        } /*else if (requestCode == READ_REQUEST_CODE_PDF) {
            try {
                textPdfFolder.setText(data.getData().toString());
            } catch (Exception ex) {
                textPdfFolder.setText("");
            }
        }*/
    }

    private boolean isDataValid() {
        return isNotEmpty(editCsvLink, textCsvFolder/*, textPdfFolder*/);
    }

    private void initView() {
        editCsvLink = findViewById(R.id.editCsvLink);

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

        btnChangeCsvFolder.setOnClickListener(v -> performFileSearch(READ_REQUEST_CODE_CSV));

        btnCancel.setOnClickListener(v -> closeActivity());

        btnSave.setOnClickListener(v -> saveAction());
    }

    private void saveAction() {
        if(isDataValid()) {
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
            closeActivity();
            return;
        }

        Toast.makeText(SettingActivity.this, getResources()
                .getString(R.string.enter_all_fields), Toast.LENGTH_SHORT).show();
    }

    private void initValue() {
        StorageManager storageManager = new StorageManager(SettingActivity.this);
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

    private void performFileSearch(final int requestCode) {
        Intent i = new Intent(Intent.ACTION_OPEN_DOCUMENT_TREE);
        i.addCategory(Intent.CATEGORY_DEFAULT);
        startActivityForResult(Intent.createChooser(i, "Choose directory"), requestCode);
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
