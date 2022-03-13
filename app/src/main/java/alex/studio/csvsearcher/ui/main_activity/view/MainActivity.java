package alex.studio.csvsearcher.ui.main_activity.view;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import alex.studio.csvsearcher.R;
import alex.studio.csvsearcher.dto.CardGroup;
import alex.studio.csvsearcher.dto.CardSet;
import alex.studio.csvsearcher.dto.ColorMatch;
import alex.studio.csvsearcher.ui.adapter.ResultAdapter;
import alex.studio.csvsearcher.ui.adapter.ResultAdapterTwo;
import alex.studio.csvsearcher.ui.main_activity.Main;
import alex.studio.csvsearcher.ui.main_activity.presenter.MainPresenterOne;
import alex.studio.csvsearcher.ui.main_activity.presenter.MainPresenterTwo;

import static alex.studio.csvsearcher.utils.StringUtils.generateDateString;
import static alex.studio.csvsearcher.utils.ViewUtils.changeVisible;
import static alex.studio.csvsearcher.utils.ViewUtils.getTextFrom;
import static alex.studio.csvsearcher.utils.ViewUtils.isAnyVisible;
import static alex.studio.csvsearcher.utils.ViewUtils.isEmpty;
import static alex.studio.csvsearcher.utils.ViewUtils.isVisible;
import static alex.studio.csvsearcher.utils.ViewUtils.toGone;
import static alex.studio.csvsearcher.utils.ViewUtils.toVisible;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener,
        Main.View {

    private Main.Presenter presenter;

    private View btnResetDateFrom;
    private View btnResetDateTo;
    private View btnReset;
    private View btnSearch;
    private View btnRight;
    private View btnLeft;
    private View btnTop;
    private View btnBottom;
    private View btnTopLeft;
    private View btnBottomLeft;
    private View btnTopRight;
    private View btnBottomRight;
    private View colorRight;
    private View colorLeft;
    private View colorTop;
    private View colorBottom;
    private View colorTopLeft;
    private View colorBottomLeft;
    private View colorTopRight;
    private View colorBottomRight;
    private View btnFrom;
    private View btnTo;
    private View btnYes;
    private View btnNo;
    private View blockAskExit;
    private View blockCountGames;
    private View blockByDate;
    private View blockWait;

    private TextView textDateFrom;
    private TextView textDateTo;
    private TextView btnCountGames;
    private TextView btnByDate;
    private TextView textAppNameSmall;

    private EditText editCount1;

    private Spinner spinnerOne;
    private Spinner spinnerTwo;
    private Spinner spinnerThree;
    private Spinner spinnerFour;

    private RecyclerView recyclerView;
    private ResultAdapter resultAdapter;
    private ResultAdapterTwo resultAdapterTwo;

    private int colorGray;
    private int colorYellow;
    private int colorWhite;
    private int colorLightGray;

    private String curDirection;
    private String[] cards;
    int yearCur;
    int monthCur;
    int dayCur;
    int yearCurTo;
    int monthCurTo;
    int dayCurTo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initConstant();
        initView();
    }

    private void initConstant() {
        switch (getIntent().getStringExtra("type")) {
            case "one":
                presenter = new MainPresenterOne();
                break;
            case "two":
                presenter = new MainPresenterTwo();
                break;
        }

        curDirection = "";
        colorLightGray = getResources().getColor(R.color.textLightGrayBlue);
        colorGray = getResources().getColor(R.color.gray);
        colorYellow = getResources().getColor(R.color.yellow);
        colorWhite = getResources().getColor(R.color.white);
        cards = getResources().getStringArray(R.array.cards);

        Calendar dateAndTime = Calendar.getInstance();
        yearCur = yearCurTo = dateAndTime.get(Calendar.YEAR);
        monthCur = monthCurTo = dateAndTime.get(Calendar.MONTH);
        dayCur = dayCurTo = dateAndTime.get(Calendar.DAY_OF_MONTH);
    }

    private void initView() {
        btnResetDateFrom = findViewById(R.id.btnResetDateFrom);
        btnResetDateTo = findViewById(R.id.btnResetDateTo);
        btnReset = findViewById(R.id.btnReset);
        btnSearch = findViewById(R.id.btnSearch);
        btnRight = findViewById(R.id.btnRight);
        btnLeft = findViewById(R.id.btnLeft);
        btnTop = findViewById(R.id.btnTop);
        btnBottom = findViewById(R.id.btnBottom);
        btnTopLeft = findViewById(R.id.btnTopLeft);
        btnBottomLeft = findViewById(R.id.btnBottomLeft);
        btnTopRight = findViewById(R.id.btnTopRight);
        btnBottomRight = findViewById(R.id.btnBottomRight);
        colorRight = findViewById(R.id.colorRight);
        colorLeft = findViewById(R.id.colorLeft);
        colorTop = findViewById(R.id.colorTop);
        colorBottom = findViewById(R.id.colorBottom);
        colorTopLeft = findViewById(R.id.colorTopLeft);
        colorBottomLeft = findViewById(R.id.colorBottomLeft);
        colorTopRight = findViewById(R.id.colorTopRight);
        colorBottomRight = findViewById(R.id.colorBottomRight);
        btnFrom = findViewById(R.id.btnFrom);
        btnTo = findViewById(R.id.btnTo);
        btnYes = findViewById(R.id.btnYes);
        btnNo = findViewById(R.id.btnNo);
        blockAskExit = findViewById(R.id.blockAskExit);
        blockCountGames = findViewById(R.id.blockCountGames);
        blockByDate = findViewById(R.id.blockByDate);
        blockWait = findViewById(R.id.blockWait);

        textAppNameSmall = findViewById(R.id.textAppNameSmall);
        textDateFrom = findViewById(R.id.textDateFrom);
        textDateTo = findViewById(R.id.textDateTo);
        btnCountGames = findViewById(R.id.btnCountGames);
        btnByDate = findViewById(R.id.btnByDate);

        editCount1 = findViewById(R.id.editCount1);

        spinnerOne = findViewById(R.id.spinnerOne);
        spinnerTwo = findViewById(R.id.spinnerTwo);
        spinnerThree = findViewById(R.id.spinnerThree);
        spinnerFour = findViewById(R.id.spinnerFour);

        recyclerView = findViewById(R.id.recyclerResult);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        switch (getIntent().getStringExtra("type")) {
            case "one":
                resultAdapter = new ResultAdapter(MainActivity.this);
                recyclerView.setAdapter(resultAdapter);
                break;
            case "two":
                resultAdapterTwo = new ResultAdapterTwo(MainActivity.this);
                recyclerView.setAdapter(resultAdapterTwo);
                break;

        }

        btnByDate.post(() -> changeButtonWidth(btnSearch.getWidth(), btnCountGames, btnByDate));

        initEquipment();
        initSpinner();
        initAction();
    }

    private void initAction() {

        btnYes.setOnClickListener(v -> {
            toGone(blockAskExit);
            finish();
        });

        btnNo.setOnClickListener(v -> toGone(blockAskExit));

        blockAskExit.setOnClickListener(v -> {});

        btnResetDateTo.setOnClickListener(v -> textDateTo.setText(""));

        btnResetDateFrom.setOnClickListener(v -> textDateFrom.setText(""));

        btnReset.setOnClickListener(v -> {
            clearData();
            unselectAllColor(colorTop, colorBottom, colorLeft, colorRight, colorBottomLeft,
                    colorBottomRight, colorTopLeft, colorTopRight);
        });

        btnSearch.setOnClickListener(v -> {
            curDirection = "";
            presenter.launchSearch();
            unselectAllColor(colorTop, colorBottom, colorLeft, colorRight, colorBottomLeft,
                    colorBottomRight, colorTopLeft, colorTopRight);
        });

        btnCountGames.setOnClickListener(v -> changeStateOption(btnCountGames, 1,
                blockCountGames, blockByDate));

        btnByDate.setOnClickListener(v -> changeStateOption(btnByDate, 3, blockByDate,
                blockCountGames));

        btnRight.setOnClickListener(v -> selectArrow(colorRight));

        btnLeft.setOnClickListener(v -> selectArrow(colorLeft));

        btnTop.setOnClickListener(v -> selectArrow(colorTop));

        btnBottom.setOnClickListener(v -> selectArrow(colorBottom));

        btnTopLeft.setOnClickListener(v -> selectArrow(colorTopLeft));

        btnBottomLeft.setOnClickListener(v -> selectArrow(colorBottomLeft));

        btnTopRight.setOnClickListener(v -> selectArrow(colorTopRight));

        btnBottomRight.setOnClickListener(v -> selectArrow(colorBottomRight));

        btnFrom.setOnClickListener(v -> initDatePickerDialog(true));

        btnTo.setOnClickListener(v -> initDatePickerDialog(false));

        spinnerOne.setOnItemSelectedListener(this);

        spinnerTwo.setOnItemSelectedListener(this);

        spinnerThree.setOnItemSelectedListener(this);

        spinnerFour.setOnItemSelectedListener(this);

        blockWait.setOnClickListener(v -> {
        });
    }

    private void initEquipment() {
        String type = getIntent().getStringExtra("type");

        switch (type) {
            case "one":
                initEquipmentOne();
                break;
            case "two":
                initEquipmentTwo();
                break;
        }
    }

    private void initEquipmentOne() {
        textAppNameSmall.setText(getResources().getString(R.string.app_name));
        toVisible(textAppNameSmall);
    }

    private void initEquipmentTwo() {
        textAppNameSmall.setText(getResources().getString(R.string.search_dropped_after_the_given_without_enter));
        toVisible(textAppNameSmall);
    }

    private void initSpinner() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(MainActivity.this,
                R.layout.item_spinner_style, cards);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerOne.setAdapter(adapter);
        spinnerTwo.setAdapter(adapter);
        spinnerThree.setAdapter(adapter);
        spinnerFour.setAdapter(adapter);
    }

    private void changeSpinnerColor(TextView currentText, int position) {
        if (currentText == null) {
            return;
        }
        if (position != 0) {
            currentText.setTextColor(colorYellow);
        } else {
            currentText.setTextColor(colorLightGray);
        }
    }

    private void changeStateOption(TextView btn, int btnType, View mainBlock, View... blocks) {

        if (isAnyVisible(blocks)) {
            closeOtherOptionMessage();
            return;
        }

        switch (btnType) {
            case 1:
                if (!isEmpty(textDateFrom, textDateTo)) {
                    closeOtherOptionMessage();
                    return;
                }
                break;
            case 3:
                if (!isEmpty(editCount1)) {
                    closeOtherOptionMessage();
                    return;
                }
                break;
        }

        if (changeVisible(mainBlock)) {
            btn.setTextColor(colorYellow);
            btn.setBackground(getResources().getDrawable(R.drawable.blue_dark_radius_border));
        } else {
            boolean isEmpty;

            switch (btnType) {
                case 1:
                    isEmpty = isEmpty(editCount1);
                    break;
                case 3:
                    isEmpty = isEmpty(textDateFrom, textDateTo);
                    break;
                default:
                    isEmpty = true;
            }

            btn.setTextColor(colorWhite);
            btn.setBackground(isEmpty ? getResources().getDrawable(R.drawable.blue_dark_radius_border) :
                    getResources().getDrawable(R.drawable.blue_radius_active_border));
        }
    }

    private void closeOtherOptionMessage() {
        Toast.makeText(MainActivity.this, getResources().getString(
                R.string.close_other_option), Toast.LENGTH_LONG).show();
    }

    private void initDatePickerDialog(boolean dateType) {

        DatePickerDialog datePickerDialog;
        if (dateType) {
            datePickerDialog = new DatePickerDialog(MainActivity.this,
                    (datePicker, year, month, day) -> {

                        dayCur = day;
                        monthCur = month;
                        yearCur = year;

                        textDateFrom.setText(generateDateString(day, month, year));
                    },
                    yearCur,
                    monthCur,
                    dayCur);
        } else {
            datePickerDialog = new DatePickerDialog(MainActivity.this,
                    (datePicker, year, month, day) -> {

                        dayCurTo = day;
                        monthCurTo = month;
                        yearCurTo = year;

                        textDateTo.setText(generateDateString(day, month, year));
                    },
                    yearCurTo,
                    monthCurTo,
                    dayCurTo);
        }

        datePickerDialog.show();
    }

    private void selectArrow(View view) {
        selectCurrentArrow(view);
        curDirection = (String) view.getTag();
        presenter.launchSearch();
    }

    private void selectCurrentArrow(View view) {
        unselectAllColor(colorTop, colorBottom, colorLeft, colorRight, colorBottomLeft,
                colorBottomRight, colorTopLeft, colorTopRight);

        view.setBackgroundColor(colorYellow);
    }

    private void unselectAllColor(View... views) {
        for (View v : views) {
            v.setBackgroundColor(colorGray);
        }
    }

    private void changeButtonWidth(int w, View... buttons) {
        for (View button : buttons) {
            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) button.getLayoutParams();
            params.width = w;
            button.setLayoutParams(params);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
        changeSpinnerColor((TextView) parent.getChildAt(0), i);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    protected void onResume() {
        super.onResume();

        presenter.setView(this);
    }

    @Override
    public Context getContext() {
        return MainActivity.this;
    }

    @Override
    public String getDirection() {
        return curDirection;
    }

    @Override
    public String[] getCards() {

        return new String[]{
                cards[spinnerOne.getSelectedItemPosition()],
                cards[spinnerTwo.getSelectedItemPosition()],
                cards[spinnerThree.getSelectedItemPosition()],
                cards[spinnerFour.getSelectedItemPosition()]
        };
    }

    @Override
    public List<String[]> getCardsList() {
        return new ArrayList<>();
    }

    @Override
    public String[] getActiveDate() {
        if (!(getTextFrom(textDateFrom) + getTextFrom(textDateTo)).isEmpty()) {
            return new String[]{
                    getTextFrom(textDateFrom),
                    getTextFrom(textDateTo)
            };
        } else {
            return null;
        }
    }

    @Override
    public Integer getActiveCountGames() {

        try {
            return Integer.valueOf(
                    getTextFrom(editCount1)
            );
        } catch (Exception ex) {
            return null;
        }
    }

    public void clearData() {
        if (resultAdapter != null) {
            resultAdapter.clear();
        }

        if (resultAdapterTwo != null) {
            resultAdapterTwo.clear();
        }
    }

    @Override
    public void setRecyclerData(List<CardGroup> data) {
        resultAdapter.setData(data);
    }

    @Override
    public void setRecyclerResultData(List<CardSet> data) {

    }

    @Override
    public void setRecyclerResultData(List<Map<String, ColorMatch>> data, List<List<CardSet>> list) {

    }

    @Override
    public void setRecyclerData(Map<String, Integer> data) {
        resultAdapterTwo.setData(data);
    }

    @Override
    public void changeVisibleBlockWait(boolean changeVisible) {
        if (changeVisible) {
            toVisible(blockWait);
        } else {
            toGone(blockWait);
        }
    }

    @Override
    public boolean isCardValid() {
        switch (getIntent().getStringExtra("type")) {
            case "one":
                if (spinnerOne.getSelectedItemPosition() == 0 ||
                        spinnerTwo.getSelectedItemPosition() == 0 ||
                        spinnerThree.getSelectedItemPosition() == 0 ||
                        spinnerFour.getSelectedItemPosition() == 0) {
                    Toast.makeText(MainActivity.this, getResources()
                            .getString(R.string.error_select_card_one), Toast.LENGTH_LONG).show();
                    return false;
                }
            case "two":
                if (spinnerOne.getSelectedItemPosition() == 0 ||
                        spinnerTwo.getSelectedItemPosition() == 0) {

                    Toast.makeText(MainActivity.this, getResources()
                            .getString(R.string.error_select_card), Toast.LENGTH_LONG).show();
                    return false;
                }
                return true;
            default:
                return true;
        }
    }

    @Override
    public boolean isOrderSet() {
        return false;
    }

    @Override
    public void onBackPressed() {
        changeVisible(blockAskExit);
    }
}
