package alex.studio.csvsearcher.ui.main_activity.view;

import static alex.studio.csvsearcher.utils.DateUtils.toDate;
import static alex.studio.csvsearcher.utils.ViewUtils.changeVisible;
import static alex.studio.csvsearcher.utils.ViewUtils.getTextFrom;
import static alex.studio.csvsearcher.utils.ViewUtils.isVisible;
import static alex.studio.csvsearcher.utils.ViewUtils.toGone;
import static alex.studio.csvsearcher.utils.ViewUtils.toVisible;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import alex.studio.csvsearcher.R;
import alex.studio.csvsearcher.dto.CardGroup;
import alex.studio.csvsearcher.dto.CardMatch;
import alex.studio.csvsearcher.dto.CardSet;
import alex.studio.csvsearcher.dto.ColorMatch;
import alex.studio.csvsearcher.enums.CardPosition;
import alex.studio.csvsearcher.enums.Direction;
import alex.studio.csvsearcher.ui.adapter.ResultAdapterOne;
import alex.studio.csvsearcher.ui.adapter.YearAdapter;
import alex.studio.csvsearcher.ui.main_activity.Main;
import alex.studio.csvsearcher.ui.main_activity.presenter.MainPresenterOne;

public class MainActivityOne extends AppCompatActivity implements AdapterView.OnItemSelectedListener,
        Main.View {

    private Main.Presenter presenter;

    private View btnAllYear;
    private View btnCancel;
    private View btnSave;
    private View btnResetField;
    private View btnReset;
    private View btnSearch;
    private View btnYes;
    private View btnNo;
    private View blockAskExit;
    private View blockWait;
    private View blockSelectDate;
    private View blockOptions;
    private View btnClose;
    private View btnSetting;
    private View secondSelectCardBlock;

    private TextView switchFourRandom;
    private TextView switchFourOriginal;
    private TextView switchThreeRandom;
    private TextView switchThreeOriginal;

    private LinearLayout cardSelectorBlock;

    private TextView textOne;
    private TextView textTwo;
    private TextView textThree;
    private TextView textFour;
    private Spinner spinnerMonths;
    private Spinner spinnerDays;

    private TextView textDate;

    private RecyclerView recyclerYear;
    private RecyclerView recyclerView;
    private ResultAdapterOne resultAdapter;
    private YearAdapter yearAdapter;

    private CardPosition curPosition;
    private int colorYellow;
    private int colorLightGray;
    private Drawable activeBg;
    private Drawable whiteBorderBg;
    private Drawable bottomBorderBg;

    private boolean[] optionsState = new boolean[]{true, false, false, false};
    private boolean stateAllYear = true;

    private String[] cards;
    private String[] months;
    private String firstDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_one);

        initConstant();
        initView();
    }

    private void initConstant() {
        presenter = new MainPresenterOne();
        curPosition = null;

        colorLightGray = getResources().getColor(R.color.textLightGrayBlue);
        colorYellow = getResources().getColor(R.color.yellow);
        cards = getResources().getStringArray(R.array.cards);
        months = getResources().getStringArray(R.array.months);
        activeBg = getResources().getDrawable(R.drawable.blue_radius_active_border);
        whiteBorderBg = getResources().getDrawable(R.drawable.white_active_border);
        bottomBorderBg = getResources().getDrawable(R.drawable.bottom_border);
    }

    private void initView() {
        btnAllYear = findViewById(R.id.btnAllYear);
        btnCancel = findViewById(R.id.btnCancel);
        btnSave = findViewById(R.id.btnSave);
        btnResetField = findViewById(R.id.btnResetField);
        btnReset = findViewById(R.id.btnReset);
        btnSetting = findViewById(R.id.btnSettings);
        btnSearch = findViewById(R.id.btnSearch);
        btnYes = findViewById(R.id.btnYes);
        btnNo = findViewById(R.id.btnNo);
        btnClose = findViewById(R.id.btnClose);
        blockOptions = findViewById(R.id.blockOptions);
        blockAskExit = findViewById(R.id.blockAskExit);
        blockWait = findViewById(R.id.blockWait);
        blockSelectDate = findViewById(R.id.blockSelectDate);
        cardSelectorBlock = findViewById(R.id.cardSelectorBlock);
        secondSelectCardBlock = findViewById(R.id.secondSelectCardBlock);

        switchFourRandom = findViewById(R.id.switchFourRandom);
        switchFourOriginal = findViewById(R.id.switchFourOriginal);
        switchThreeOriginal = findViewById(R.id.switchThreeOriginal);
        switchThreeRandom = findViewById(R.id.switchThreeRandom);

        textDate = findViewById(R.id.textDate);

        textOne = findViewById(R.id.textOne);
        textTwo = findViewById(R.id.textTwo);
        textThree = findViewById(R.id.textThree);
        textFour = findViewById(R.id.textFour);
        spinnerMonths = findViewById(R.id.spinnerMonth);
        spinnerDays = findViewById(R.id.spinnerDay);

        recyclerView = findViewById(R.id.recyclerResult);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        resultAdapter = new ResultAdapterOne(MainActivityOne.this);
        recyclerView.setAdapter(resultAdapter);

        recyclerYear = findViewById(R.id.recyclerYear);
        recyclerYear.setLayoutManager(new LinearLayoutManager(this));
        yearAdapter = new YearAdapter(MainActivityOne.this, true);
        recyclerYear.setAdapter(yearAdapter);
        yearAdapter.initData();

        toGone(secondSelectCardBlock);
        spinnerDays.post(this::initializationData);

        for (int i = 0; i < cards.length; i++) {
            TextView textView = (TextView) LayoutInflater.from(this)
                    .inflate(R.layout.card_selecter, null);
            textView.setText(cards[i]);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT
            );
            params.weight = 1;
            if (i != cards.length - 1) {
                params.rightMargin = 1;
            }
            textView.setLayoutParams(params);
            textView.setOnClickListener(v -> {
                TextView selectTextView = null;
                switch (curPosition) {
                    case ONE:
                        selectTextView = textOne;
                        break;
                    case TWO:
                        selectTextView = textTwo;
                        break;
                    case THREE:
                        selectTextView = textThree;
                        break;
                    case FOUR:
                        selectTextView = textFour;
                        break;
                }
                String curText = getTextFrom(v);
                selectTextView.setText(curText);
                if (curText.equals(cards[0])) {
                    selectTextView.setTextColor(colorLightGray);
                } else {
                    selectTextView.setTextColor(colorYellow);
                }
                selectTextView.setBackground(bottomBorderBg);
                toGone(cardSelectorBlock);
                curPosition = null;
            });
            cardSelectorBlock.addView(textView);
        }

        initSpinner();
        initAction();
    }

    private void initializationData() {
        presenter.initializationData((CardSet val) -> {
            textOne.setText(val.getCard1());
            textTwo.setText(val.getCard2());
            textThree.setText(val.getCard3());
            textFour.setText(val.getCard4());
            textDate.setText(val.getDateString());
            Calendar c = Calendar.getInstance();
            c.setTime(val.getDate());
            spinnerMonths.setSelection(c.get(Calendar.MONTH));
            selectDay(c.get(Calendar.MONTH));
            spinnerDays.setSelection(c.get(Calendar.DAY_OF_MONTH) - 1);
            firstDate = val.getDateString();
        });
    }

    private void changeEnableState(int pos, View view) {
        if (optionsState[pos]) {
            view.setBackground(null);
            optionsState[pos] = false;
        } else {
            view.setBackground(activeBg);
            optionsState[pos] = true;
        }
    }

    private void resetCards() {
        textOne.setText(cards[0]);
        textTwo.setText(cards[0]);
        textThree.setText(cards[0]);
        textFour.setText(cards[0]);
    }

    private void initAction() {

        btnYes.setOnClickListener(v -> {
            toGone(blockAskExit);
            finish();
        });

        btnNo.setOnClickListener(v -> toGone(blockAskExit));

        blockAskExit.setOnClickListener(v -> {
        });

        btnSave.setOnClickListener(v -> saveDateAction());

        btnCancel.setOnClickListener(v -> toGone(blockSelectDate));

        btnResetField.setOnClickListener(v -> resetCards());

        btnAllYear.setOnClickListener(v -> changeAllYearState());

        btnReset.setOnClickListener(v -> clearData());

        btnSearch.setOnClickListener(v -> presenter.launchSearch());

        btnSetting.setOnClickListener(v -> toVisible(blockOptions));

        btnClose.setOnClickListener(v -> toGone(blockOptions));

        textOne.setOnClickListener(this::selectCardClick);

        textTwo.setOnClickListener(this::selectCardClick);

        textThree.setOnClickListener(this::selectCardClick);

        textFour.setOnClickListener(this::selectCardClick);

        spinnerMonths.setOnItemSelectedListener(this);

        spinnerDays.setOnItemSelectedListener(this);

        switchFourOriginal.setOnClickListener(v -> changeEnableState(0, v));

        switchFourRandom.setOnClickListener(v -> changeEnableState(1, v));

        switchThreeOriginal.setOnClickListener(v -> changeEnableState(2, v));

        switchThreeRandom.setOnClickListener(v -> changeEnableState(3, v));

        blockWait.setOnClickListener(v -> {
        });

        blockSelectDate.setOnClickListener(v -> {
        });

        textDate.setOnClickListener(v -> toVisible(blockSelectDate));
    }

    private void selectCardClick(View view) {
        textOne.setBackground(bottomBorderBg);
        textTwo.setBackground(bottomBorderBg);
        textThree.setBackground(bottomBorderBg);
        textFour.setBackground(bottomBorderBg);
        view.setBackground(whiteBorderBg);
        curPosition = CardPosition.of((String) view.getTag());
        toVisible(cardSelectorBlock);
    }

    private void changeAllYearState() {
        if (!stateAllYear) {
            stateAllYear = true;
            yearAdapter.clearAll();
            btnAllYear.setBackground(getResources().getDrawable(R.drawable.blue_dark_radius_border));
        } else {
            stateAllYear = false;
            yearAdapter.selectAll();
            btnAllYear.setBackground(getResources().getDrawable(R.drawable.blue_radius_active_border));
        }
    }

    private void initSpinner() {
        ArrayAdapter<String> adapterMonth = new ArrayAdapter<>(MainActivityOne.this,
                R.layout.item_spinner_style_month_drop_down, months);
        adapterMonth.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMonths.setAdapter(adapterMonth);
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
        return MainActivityOne.this;
    }

    @Override
    public Direction getDirection() {
        return null;
    }

    public List<Integer> getSelectedYears() {
        return yearAdapter.getArraySelectedYears();
    }

    @Override
    public String[] getCards() {
        return new String[]{
                getTextFrom(textFour),
                getTextFrom(textThree),
                getTextFrom(textTwo),
                getTextFrom(textOne)
        };
    }

    @Override
    public List<String[]> getCardsList() {
        return new ArrayList<>();
    }

    @Override
    public String[] getActiveDate() {
        String[] dMY = textDate.getText().toString().split("\\.");
        String result = dMY[0] + "." + dMY[1] + "." + firstDate.substring(6);
        return new String[]{result};
    }

    @Override
    public Integer getActiveCountGames() {
        return 0;
    }

    public void clearData() {
        resultAdapter.setData(new ArrayList<>());
        textDate.setText(firstDate);
        Date date = toDate(firstDate);
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        spinnerMonths.setSelection(c.get(Calendar.MONTH));
        selectDay(c.get(Calendar.MONTH));
        spinnerDays.setSelection(c.get(Calendar.DAY_OF_MONTH) - 1);
        yearAdapter.clearAll();
    }

    @Override
    public void setCardGroupListToRecycler(List<CardGroup> data) {

    }

    @Override
    public void setCardMatchListToRecycler(List<CardMatch> data) {
        resultAdapter.setData(data);
    }

    @Override
    public void setCardSetListToRecycler(List<CardSet> data) {

    }

    @Override
    public void setRecyclerResultData(List<Map<String, ColorMatch>> data, List<List<CardSet>> list) {

    }

    @Override
    public void setMapToRecycler(Map<String, Integer> data) {

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

        if (getTextFrom(textOne).equals(cards[0]) ||
                getTextFrom(textTwo).equals(cards[0]) ||
                getTextFrom(textThree).equals(cards[0]) ||
                getTextFrom(textFour).equals(cards[0])) {

            Toast.makeText(MainActivityOne.this, getResources()
                    .getString(R.string.error_select_card_one), Toast.LENGTH_LONG).show();
            return false;
        }
        if (textDate.getText().toString().isEmpty()) {
            Toast.makeText(MainActivityOne.this, getResources()
                    .getString(R.string.error_select_date), Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    @Override
    public boolean isOrderSet() {
        return false;
    }

    public boolean[] getOptionsState() {
        return optionsState;
    }

    @Override
    public void onBackPressed() {
        if (curPosition != null) {
            curPosition = null;
            toGone(cardSelectorBlock);
            return;
        }
        if (isVisible(blockSelectDate)) {
            toGone(blockSelectDate);
        } else {
            changeVisible(blockAskExit);
        }
    }

    private void saveDateAction() {
        String result = "";
        List<Integer> years = yearAdapter.getArraySelectedYears();

        result += addZero(spinnerDays.getSelectedItemPosition() + 1) + ".";
        result += addZero(spinnerMonths.getSelectedItemPosition() + 1) +
                (years.size() == 1 ? "." + years.get(0) :
                        years.isEmpty() ? "." + firstDate.substring(6) : "");

        textDate.setText(result);
        toGone(blockSelectDate);
    }

    private String addZero(int num) {
        if (num < 10) {
            return "0" + num;
        }
        return num + "";
    }

    private void selectDay(int pos) {

        int selection = spinnerDays.getSelectedItemPosition();

        ArrayAdapter<String> adapterDays = new ArrayAdapter<>(MainActivityOne.this,
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
