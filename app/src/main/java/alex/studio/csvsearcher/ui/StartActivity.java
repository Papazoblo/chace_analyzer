package alex.studio.csvsearcher.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import alex.studio.csvsearcher.R;
import alex.studio.csvsearcher.components.DownloadFileFromURL;
import alex.studio.csvsearcher.components.StorageManager;
import alex.studio.csvsearcher.enums.Properties;
import alex.studio.csvsearcher.ui.main_activity.view.MainActivity;
import alex.studio.csvsearcher.ui.main_activity.view.MainActivityFour;
import alex.studio.csvsearcher.ui.main_activity.view.MainActivityThree;

import static alex.studio.csvsearcher.utils.ViewUtils.isVisible;
import static alex.studio.csvsearcher.utils.ViewUtils.toGone;
import static alex.studio.csvsearcher.utils.ViewUtils.toVisible;

public class StartActivity extends AppCompatActivity {

    private StorageManager storageManager;

    private View btnStorage;
    private View btnDownload;
    private View btnSetting;
    private TextView btnAlgorithmOne;
    private TextView btnAlgorithmTwo;
    private TextView btnAlgorithmThree;
    private TextView btnAlgorithmFour;
    private TextView btnAlgorithmFive;
    private TextView btnAlgorithmSix;
    private View btnYes;
    private View btnNo;
    private View blockBtnTop;
    private View blockBtnBottom;
    private View blockBtnMedium;
    private View blockAsk;
    private View blockProgress;
    private View blockAskExit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        storageManager = new StorageManager(StartActivity.this);

        initView();
    }

    private void initView() {
        btnStorage = findViewById(R.id.btnStorage);
        btnDownload = findViewById(R.id.btnDowndload);
        btnSetting = findViewById(R.id.btnSettings);
        btnAlgorithmOne = findViewById(R.id.btnAlgorithmOne);
        btnAlgorithmTwo = findViewById(R.id.btnAlgorithmTwo);
        btnAlgorithmThree = findViewById(R.id.btnAlgorithmThree);
        btnAlgorithmFour = findViewById(R.id.btnAlgorithmFour);
        btnAlgorithmFive = findViewById(R.id.btnAlgorithmFive);
        btnAlgorithmSix = findViewById(R.id.btnAlgorithmSix);
        btnYes = findViewById(R.id.btnYes);
        btnNo = findViewById(R.id.btnNo);
        blockBtnTop = findViewById(R.id.blockBtnTop);
        blockBtnBottom = findViewById(R.id.blockBtnBottom);
        blockBtnMedium = findViewById(R.id.blockBtnMedium);
        blockAsk = findViewById(R.id.blockAsk);
        blockProgress = findViewById(R.id.blockProgress);
        blockAskExit = findViewById(R.id.blockAskExit);

        btnAlgorithmFour.post(() -> {
            int h = btnAlgorithmFour.getWidth();
            setBtnHeight(blockBtnBottom, h);
            setBtnHeight(blockBtnTop, h);
            setBtnHeight(blockBtnMedium, h);
        });

        btnAlgorithmOne.setText(storageManager.read(Properties.ALGO_1));
        btnAlgorithmTwo.setText(storageManager.read(Properties.ALGO_2));
        btnAlgorithmThree.setText(storageManager.read(Properties.ALGO_3));
        btnAlgorithmFour.setText(storageManager.read(Properties.ALGO_4));
        btnAlgorithmFive.setText(storageManager.read(Properties.ALGO_5));
        btnAlgorithmSix.setText(storageManager.read(Properties.ALGO_6));

        initAction();
    }

    private void setBtnHeight(View view, int height) {
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) view.getLayoutParams();
        params.height = height;
        view.setLayoutParams(params);
    }

    private void initAction() {

        btnYes.setOnClickListener(v -> {
            toGone(blockAskExit);
            finish();
        });

        btnNo.setOnClickListener(v -> toGone(blockAskExit));

        blockAskExit.setOnClickListener(v -> {
        });

        btnSetting.setOnClickListener(v -> {
            if (permissionGranted()) {
                startActivity(
                        new Intent(StartActivity.this, SettingActivity.class));
                finish();
            } else {
                requestPermission();
            }
        });

        btnAlgorithmOne.setOnClickListener(v -> startMainActivity(Properties.ALGO_1));

        btnAlgorithmTwo.setOnClickListener(v -> startMainActivity(Properties.ALGO_2));

        btnAlgorithmThree.setOnClickListener(v -> startMainActivity(Properties.ALGO_3));

        btnAlgorithmFour.setOnClickListener(v -> startMainActivity(Properties.ALGO_4));

        btnAlgorithmFive.setOnClickListener(v -> startMainActivity(Properties.ALGO_5));

        btnAlgorithmSix.setOnClickListener(v -> startMainActivity(Properties.ALGO_6));

        btnDownload.setOnClickListener(v -> downloadFileAction());

        btnStorage.setOnClickListener(v -> toGone(blockAsk));

        blockAsk.setOnClickListener(v -> {
        });

        blockProgress.setOnClickListener(v -> {
        });
    }

    private void downloadFileAction() {

        if (!checkPermission()) {
            return;
        }

        StorageManager storageManager = new StorageManager(StartActivity.this);

        toGone(blockAsk);
        new DownloadFileFromURL(StartActivity.this,
                storageManager.read(Properties.CSV_FOLDER)
        ).execute(storageManager.read(Properties.CSV_LINK));
    }

    private void startMainActivity(Properties algorithm) {

        if (isPathValid()) {
            switch (algorithm) {
                case ALGO_1:
                    break;
                case ALGO_2:
                    break;
                case ALGO_3:
                case ALGO_4:
                    startActivity(new Intent(StartActivity.this, MainActivity.class)
                            .putExtra("type", algorithm.name())
                    );
                    break;
                case ALGO_5:
                    startActivity(new Intent(StartActivity.this, MainActivityThree.class)
                            .putExtra("type", algorithm.name())
                    );
                    break;
                case ALGO_6:
                    startActivity(new Intent(StartActivity.this, MainActivityFour.class)
                            .putExtra("type", algorithm.name())
                    );
                    break;
            }
        }
    }

    private boolean isPathValid() {
        if (storageManager.read(Properties.CSV_FOLDER).isEmpty()) {
            Toast.makeText(StartActivity.this,
                    getResources().getString(R.string.enter_csv_path), Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    private boolean checkPermission() {
        if (!permissionGranted()) {
            requestPermission();
            return false;
        }
        return true;
    }

    private boolean permissionGranted() {
        return ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this,
                Manifest.permission.INTERNET) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.INTERNET}, 1);
    }

    public void showDialog() {
        runOnUiThread(() -> {
            toVisible(blockProgress);
        });
    }

    public void hideDialog() {
        runOnUiThread(() -> {
            toGone(blockProgress);
            Toast.makeText(StartActivity.this, getResources()
                    .getString(R.string.download_complete), Toast.LENGTH_LONG).show();
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkPermission();
    }

    @Override
    public void onBackPressed() {
        if (isVisible(blockAsk)) {
            toGone(blockAsk);
        } else if (isVisible(blockProgress)) {
        } else if (isVisible(blockAskExit)) {
            toGone(blockAskExit);
        } else {
            toVisible(blockAskExit);
        }
    }
}
