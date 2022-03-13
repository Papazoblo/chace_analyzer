package alex.studio.csvsearcher.ui.main_activity.view;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import alex.studio.csvsearcher.R;
import alex.studio.csvsearcher.dto.CardGroup;
import alex.studio.csvsearcher.dto.CardSet;
import alex.studio.csvsearcher.dto.ColorMatch;
import alex.studio.csvsearcher.ui.adapter.ResultAdapterThree;
import alex.studio.csvsearcher.ui.adapter.YearAdapter;
import alex.studio.csvsearcher.ui.main_activity.Main;
import alex.studio.csvsearcher.ui.main_activity.presenter.MainPresenterThree;

import static alex.studio.csvsearcher.utils.ViewUtils.changeVisible;
import static alex.studio.csvsearcher.utils.ViewUtils.isVisible;
import static alex.studio.csvsearcher.utils.ViewUtils.toGone;
import static alex.studio.csvsearcher.utils.ViewUtils.toVisible;

public class MainActivityThree extends AppCompatActivity implements AdapterView.OnItemSelectedListener,
        Main.View {

    private Main.Presenter presenter;

    private View btnAllYear;
    private View btnCancel;
    private View btnSave;
    private View btnReset;
    private View btnSearch;
    private View btnYes;
    private View btnNo;
    private View blockAskExit;
    private View blockWait;
    private View blockSelectDate;
    private View blockByDate;

    private Switch switchOrder;

    private Spinner spinnerOne;
    private Spinner spinnerTwo;
    private Spinner spinnerThree;
    private Spinner spinnerFour;
    private Spinner spinnerMonths;
    private Spinner spinnerDays;

    private TextView textDate;

    private RecyclerView recyclerYear;
    private RecyclerView recyclerView;
    private ResultAdapterThree resultAdapter;
    private YearAdapter yearAdapter;

    private int colorYellow;
    private int colorLightGray;

    private boolean stateAllYear = true;

    private String[] cards;
    private String[] months;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_three);

        initConstant();
        initView();
    }

    private void initConstant() {
        presenter = new MainPresenterThree();

        colorLightGray = getResources().getColor(R.color.textLightGrayBlue);
        colorYellow = getResources().getColor(R.color.yellow);
        cards = getResources().getStringArray(R.array.cards);
        months = getResources().getStringArray(R.array.months);
    }

    private void initView() {
        btnAllYear = findViewById(R.id.btnAllYear);
        btnCancel = findViewById(R.id.btnCancel);
        btnSave = findViewById(R.id.btnSave);
        btnReset = findViewById(R.id.btnReset);
        btnSearch = findViewById(R.id.btnSearch);
        btnYes = findViewById(R.id.btnYes);
        btnNo = findViewById(R.id.btnNo);
        blockAskExit = findViewById(R.id.blockAskExit);
        blockWait = findViewById(R.id.blockWait);
        blockSelectDate = findViewById(R.id.blockSelectDate);
        blockByDate = findViewById(R.id.blockByDate);

        switchOrder = findViewById(R.id.switchOrder);

        textDate = findViewById(R.id.textDate);

        spinnerOne = findViewById(R.id.spinnerOne);
        spinnerTwo = findViewById(R.id.spinnerTwo);
        spinnerThree = findViewById(R.id.spinnerThree);
        spinnerFour = findViewById(R.id.spinnerFour);
        spinnerMonths = findViewById(R.id.spinnerMonth);
        spinnerDays = findViewById(R.id.spinnerDay);

        recyclerView = findViewById(R.id.recyclerResult);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        resultAdapter = new ResultAdapterThree(MainActivityThree.this);
        recyclerView.setAdapter(resultAdapter);

        recyclerYear = findViewById(R.id.recyclerYear);
        recyclerYear.setLayoutManager(new LinearLayoutManager(this));
        yearAdapter = new YearAdapter(MainActivityThree.this);
        recyclerYear.setAdapter(yearAdapter);
        yearAdapter.initData();

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

        btnAllYear.setOnClickListener(v -> changeAllYearState());

        btnSave.setOnClickListener(v -> saveAction());

        btnCancel.setOnClickListener(v -> toGone(blockSelectDate));

        btnReset.setOnClickListener(v -> clearData());

        btnSearch.setOnClickListener(v -> presenter.launchSearch());

        spinnerOne.setOnItemSelectedListener(this);

        spinnerTwo.setOnItemSelectedListener(this);

        spinnerThree.setOnItemSelectedListener(this);

        spinnerFour.setOnItemSelectedListener(this);

        spinnerMonths.setOnItemSelectedListener(this);

        spinnerDays.setOnItemSelectedListener(this);

        blockWait.setOnClickListener(v -> {
        });

        blockSelectDate.setOnClickListener(v -> {
        });

        blockByDate.setOnClickListener(v -> toVisible(blockSelectDate));
    }

    private void initSpinner() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(MainActivityThree.this,
                R.layout.item_spinner_style, cards);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerOne.setAdapter(adapter);
        spinnerTwo.setAdapter(adapter);
        spinnerThree.setAdapter(adapter);
        spinnerFour.setAdapter(adapter);

        ArrayAdapter<String> adapterMonth = new ArrayAdapter<>(MainActivityThree.this,
                R.layout.item_spinner_style_month_drop_down, months);
        adapterMonth.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMonths.setAdapter(adapterMonth);
    }

    private void changeAllYearState() {
        if(!stateAllYear) {
            stateAllYear = true;
            yearAdapter.clearAll();
            btnAllYear.setBackground(getResources().getDrawable(R.drawable.blue_radius_border));
        } else {
            stateAllYear = false;
            yearAdapter.selectAll();
            btnAllYear.setBackground(getResources().getDrawable(R.drawable.blue_radius_active_border));
        }
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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
        if (parent.getId() != R.id.spinnerMonth && parent.getId() != R.id.spinnerDay) {
            changeSpinnerColor((TextView) parent.getChildAt(0), i);
        } else {
            changeSpinnerColor((TextView) parent.getChildAt(0), 1);
            if (parent.getId() == R.id.spinnerMonth) {
                selectDay(i);
            }
        }
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
        return MainActivityThree.this;
    }

    @Override
    public String getDirection() {
        return "";
    }

    @Override
    public String[] getCards() {

        if(spinnerThree.getSelectedItemPosition() == 0) {
            return new String[]{
                    cards[spinnerOne.getSelectedItemPosition()],
                    cards[spinnerTwo.getSelectedItemPosition()]
            };
        } else if(spinnerFour.getSelectedItemPosition() == 0) {
            return new String[]{
                    cards[spinnerOne.getSelectedItemPosition()],
                    cards[spinnerTwo.getSelectedItemPosition()],
                    cards[spinnerThree.getSelectedItemPosition()]
            };
        } else {
            return new String[]{
                    cards[spinnerOne.getSelectedItemPosition()],
                    cards[spinnerTwo.getSelectedItemPosition()],
                    cards[spinnerThree.getSelectedItemPosition()],
                    cards[spinnerFour.getSelectedItemPosition()]
            };
        }
    }

    @Override
    public List<String[]> getCardsList() {
        return new ArrayList<>();
    }

    @Override
    public String[] getActiveDate() {
        String[] dMY = textDate.getText().toString().split("\\.");
        dMY[2] = dMY[2].replace("[", "");
        dMY[2] = dMY[2].replace("]", "");
        String[] year = dMY[2].split(",");
        String[] dates = new String[year.length];
        for(int i = 0 ; i < year.length ; i++) {
            dates[i] = dMY[0] + "." + dMY[1] + "." + year[i];
        }
        return dates;
    }

    @Override
    public Integer getActiveCountGames() {
        return 0;
    }

    public void clearData() {
        resultAdapter.setData(new ArrayList<>(), new ArrayList<>());
    }

    @Override
    public void setRecyclerData(List<CardGroup> data) {

    }

    @Override
    public void setRecyclerResultData(List<CardSet> data) {

    }

    @Override
    public void setRecyclerResultData(List<Map<String, ColorMatch>> data, List<List<CardSet>> list) {
        resultAdapter.setData(data, list);
    }

    @Override
    public void setRecyclerData(Map<String, Integer> data) {

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
        if (spinnerOne.getSelectedItemPosition() == 0 ||
                spinnerTwo.getSelectedItemPosition() == 0) {

            Toast.makeText(MainActivityThree.this, getResources()
                    .getString(R.string.error_select_card), Toast.LENGTH_LONG).show();
            return false;
        } else if(spinnerThree.getSelectedItemPosition() == 0 &&
                spinnerFour.getSelectedItemPosition() != 0) {
            Toast.makeText(MainActivityThree.this, getResources()
                    .getString(R.string.error_card_order), Toast.LENGTH_LONG).show();
            return false;
        }
        if(textDate.getText().toString().isEmpty()) {
            Toast.makeText(MainActivityThree.this, getResources()
                    .getString(R.string.error_select_date), Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    @Override
    public boolean isOrderSet() {
        return switchOrder.isChecked();
    }

    @Override
    public void onBackPressed() {
        if(isVisible(blockSelectDate)) {
            toGone(blockSelectDate);
        } else {
            changeVisible(blockAskExit);
        }
    }

    private void saveAction() {
        String result = "";

        result += addZero(spinnerDays.getSelectedItemPosition() + 1) + ".";
        result += addZero(spinnerMonths.getSelectedItemPosition() + 1) + ".";
        if(yearAdapter.getSelectedCount() > 0) {
            result += yearAdapter.getSelectYears();
        } else {
            Toast.makeText(MainActivityThree.this,
                    getResources().getString(R.string.error_select_year), Toast.LENGTH_LONG).show();
            return;
        }

        textDate.setText(result);
        toGone(blockSelectDate);
    }

    private String addZero(int num) {
        if(num < 10) {
            return "0" + num;
        }
        return num + "";
    }

    private void selectDay(int pos) {

        int selection = spinnerDays.getSelectedItemPosition();

        ArrayAdapter<String> adapterDays = new ArrayAdapter<>(MainActivityThree.this,
                R.layout.item_spinner_style_month_drop_down, generateDaysArray(pos));
        adapterDays.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDays.setAdapter(adapterDays);
        spinnerDays.setSelection(selection >= adapterDays.getCount() ? adapterDays.getCount() - 1
                : selection);
    }

    private String[] generateDaysArray(int month) {
        int count = 0;
        switch (month) {
            case 0:
            case 2:
            case 4:
            case 6:
            case 7:
            case 9:
            case 11:
                count = 31;
                break;
            case 1:
                count = 29;
                break;
            case 3:
            case 5:
            case 8:
            case 10:
                count = 30;
                break;
        }

        String[] result = new String[count];
        for (int i = 0; i < count; i++) {
            result[i] = (i + 1) + "";
        }

        return result;
    }

}
