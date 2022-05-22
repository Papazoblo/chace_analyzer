package alex.studio.csvsearcher.ui.main_activity.view;

import static alex.studio.csvsearcher.utils.DateUtils.getDateField;
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
import android.widget.Switch;
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
import alex.studio.csvsearcher.dto.CardMatch;
import alex.studio.csvsearcher.dto.CardSet;
import alex.studio.csvsearcher.dto.ColorMatch;
import alex.studio.csvsearcher.enums.CardPosition;
import alex.studio.csvsearcher.enums.Direction;
import alex.studio.csvsearcher.ui.adapter.ResultAdapterFive;
import alex.studio.csvsearcher.ui.adapter.YearAdapter;
import alex.studio.csvsearcher.ui.main_activity.Main;
import alex.studio.csvsearcher.ui.main_activity.presenter.MainPresenterFive;

public class MainActivityFive extends AppCompatActivity implements AdapterView.OnItemSelectedListener,
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
    private View secondSelectCardBlock;
    private View cardSelectPanel;

    private LinearLayout cardSelectorBlock;

    private Switch switchOrder;

    private TextView textOne;
    private TextView textTwo;
    private TextView textThree;
    private TextView textFour;
    private Spinner spinnerMonths;
    private Spinner spinnerDays;

    private TextView textDate;

    private RecyclerView recyclerYear;
    private RecyclerView recyclerView;
    private ResultAdapterFive resultAdapter;
    private YearAdapter yearAdapter;

    private CardPosition curPosition;
    private int colorYellow;
    private int colorLightGray;
    private int colorBlack;
    private Drawable whiteBorderBg;
    private Drawable bottomBorderBg;

    private boolean stateAllYear = true;

    private String[] cards;
    private String[] months;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_five);

        initConstant();
        initView();
    }

    private void initConstant() {
        presenter = new MainPresenterFive();
        curPosition = null;

        colorLightGray = getResources().getColor(R.color.textLightGrayBlue);
        colorYellow = getResources().getColor(R.color.yellow);
        colorBlack = getResources().getColor(R.color.black);
        cards = getResources().getStringArray(R.array.cards);
        months = getResources().getStringArray(R.array.months);
        whiteBorderBg = getResources().getDrawable(R.drawable.white_active_border);
        bottomBorderBg = getResources().getDrawable(R.drawable.bottom_border);
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
        cardSelectorBlock = findViewById(R.id.cardSelectorBlock);
        secondSelectCardBlock = findViewById(R.id.secondSelectCardBlock);
        cardSelectPanel = findViewById(R.id.cardSelectPanel);

        switchOrder = findViewById(R.id.switchOrder);

        textDate = findViewById(R.id.textDate);

        textOne = findViewById(R.id.textOne);
        textTwo = findViewById(R.id.textTwo);
        textThree = findViewById(R.id.textThree);
        textFour = findViewById(R.id.textFour);
        spinnerMonths = findViewById(R.id.spinnerMonth);
        spinnerDays = findViewById(R.id.spinnerDay);

        recyclerView = findViewById(R.id.recyclerResult);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        resultAdapter = new ResultAdapterFive(MainActivityFive.this);
        recyclerView.setAdapter(resultAdapter);

        recyclerYear = findViewById(R.id.recyclerYear);
        recyclerYear.setLayoutManager(new LinearLayoutManager(this));
        yearAdapter = new YearAdapter(MainActivityFive.this);
        recyclerYear.setAdapter(yearAdapter);
        yearAdapter.initData();

        toGone(secondSelectCardBlock);
        textOne.setText(cards[0]);
        textTwo.setText(cards[0]);
        textThree.setText(cards[0]);
        textFour.setText(cards[0]);

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
                TextView selectTextView = cardSelectPanel.findViewWithTag(curPosition.getPosition());
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
        textDate.post(this::initializationData);
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

        textOne.setOnClickListener(this::selectCardClick);

        textTwo.setOnClickListener(this::selectCardClick);

        textThree.setOnClickListener(this::selectCardClick);

        textFour.setOnClickListener(this::selectCardClick);

        spinnerMonths.setOnItemSelectedListener(this);

        spinnerDays.setOnItemSelectedListener(this);

        blockWait.setOnClickListener(v -> {
        });

        blockSelectDate.setOnClickListener(v -> {
        });

        blockByDate.setOnClickListener(v -> toVisible(blockSelectDate));
    }

    private void initializationData() {
        presenter.initializationData((List<CardSet> list) -> {
            CardSet val = list.get(0);
            textOne.setText(val.getCard1());
            textTwo.setText(val.getCard2());
            textThree.setText(val.getCard3());
            textFour.setText(val.getCard4());
            initDate(val.getDateString());
        });
    }

    private void initDate(String date) {
        textDate.setText(date);
        Calendar c = Calendar.getInstance();
        c.setTime(toDate(date));
        spinnerMonths.setSelection(getDateField(c.getTime(), Calendar.MONTH));
        selectDay(getDateField(c.getTime(), Calendar.MONTH));
        spinnerDays.setSelection(getDateField(c.getTime(), Calendar.DAY_OF_MONTH) - 1);
        yearAdapter.selectYear(getDateField(c.getTime(), Calendar.YEAR));
        recyclerYear.scrollToPosition(yearAdapter.getPositionByYear(
                getDateField(c.getTime(), Calendar.YEAR)));
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

    private void initSpinner() {
        ArrayAdapter<String> adapterMonth = new ArrayAdapter<>(MainActivityFive.this,
                R.layout.item_spinner_style_month_drop_down, months);
        adapterMonth.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMonths.setAdapter(adapterMonth);
    }

    private void changeAllYearState() {
        if(!stateAllYear) {
            stateAllYear = true;
            yearAdapter.clearAll();
            btnAllYear.setBackground(getResources().getDrawable(R.drawable.blue_dark_radius_border));
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
        return MainActivityFive.this;
    }

    @Override
    public Direction getDirection() {
        return null;
    }

    @Override
    public String[] getCards() {

        if(getTextFrom(textThree).equals(cards[0])) {
            return new String[]{
                    getTextFrom(textOne),
                    getTextFrom(textTwo)
            };
        } else if(getTextFrom(textFour).equals(cards[0])) {
            return new String[]{
                    getTextFrom(textOne),
                    getTextFrom(textTwo),
                    getTextFrom(textThree)
            };
        } else {
            return new String[]{
                    getTextFrom(textOne),
                    getTextFrom(textTwo),
                    getTextFrom(textThree),
                    getTextFrom(textFour)
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

        List<Integer> years = yearAdapter.getArraySelectedYears();
        String[] dates = new String[years.size()];
        for (int i = 0; i < years.size(); i++) {
            dates[i] = dMY[0] + "." + dMY[1] + "." + years.get(i);
        }
        return dates;
    }

    @Override
    public Integer getActiveCountGames() {
        return 0;
    }

    public void clearData() {
        resultAdapter.setData(new ArrayList<>(), new ArrayList<>());
        recyclerView.setBackground(null);
    }

    @Override
    public void setCardGroupListToRecycler(List<CardGroup> data) {

    }

    @Override
    public void setCardMatchListToRecycler(List<CardMatch> data) {

    }

    @Override
    public void setCardSetListToRecycler(List<CardSet> data) {

    }

    @Override
    public void setRecyclerResultData(List<Map<String, ColorMatch>> data, List<List<CardSet>> list) {
        resultAdapter.setData(data, list);
        if (list.isEmpty()) {
            recyclerView.setBackground(null);
        } else {
            recyclerView.setBackgroundColor(colorBlack);
        }
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
                getTextFrom(textTwo).equals(cards[0])) {

            Toast.makeText(MainActivityFive.this, getResources()
                    .getString(R.string.error_select_card), Toast.LENGTH_LONG).show();
            return false;
        } else if(getTextFrom(textThree).equals(cards[0]) &&
                !getTextFrom(textFour).equals(cards[0])) {
            Toast.makeText(MainActivityFive.this, getResources()
                    .getString(R.string.error_card_order), Toast.LENGTH_LONG).show();
            return false;
        }
        if(textDate.getText().toString().isEmpty()) {
            Toast.makeText(MainActivityFive.this, getResources()
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
        if (curPosition != null) {
            curPosition = null;
            toGone(cardSelectorBlock);
            return;
        }
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
            Toast.makeText(MainActivityFive.this,
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

        ArrayAdapter<String> adapterDays = new ArrayAdapter<>(MainActivityFive.this,
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
