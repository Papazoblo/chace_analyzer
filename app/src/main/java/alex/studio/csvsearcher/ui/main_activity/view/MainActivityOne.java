package alex.studio.csvsearcher.ui.main_activity.view;

import static alex.studio.csvsearcher.enums.SelectDayType.FROM;
import static alex.studio.csvsearcher.enums.SelectDayType.GENERAL;
import static alex.studio.csvsearcher.enums.SelectDayType.TO;
import static alex.studio.csvsearcher.utils.DateUtils.toDate;
import static alex.studio.csvsearcher.utils.ViewUtils.changeVisible;
import static alex.studio.csvsearcher.utils.ViewUtils.getTextFrom;
import static alex.studio.csvsearcher.utils.ViewUtils.isVisible;
import static alex.studio.csvsearcher.utils.ViewUtils.toGone;
import static alex.studio.csvsearcher.utils.ViewUtils.toVisible;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import alex.studio.csvsearcher.R;
import alex.studio.csvsearcher.dto.CardGroup;
import alex.studio.csvsearcher.dto.CardMatch;
import alex.studio.csvsearcher.dto.CardSet;
import alex.studio.csvsearcher.dto.ColorMatch;
import alex.studio.csvsearcher.enums.CardPosition;
import alex.studio.csvsearcher.enums.Direction;
import alex.studio.csvsearcher.enums.SelectDayType;
import alex.studio.csvsearcher.ui.adapter.ResultAdapterOne;
import alex.studio.csvsearcher.ui.adapter.SelectYearAdapter;
import alex.studio.csvsearcher.ui.main_activity.Main;
import alex.studio.csvsearcher.ui.main_activity.presenter.MainPresenterOne;

public class MainActivityOne extends AppCompatActivity implements Main.View {

    private Main.Presenter presenter;

    private ImageView imageArrow;
    private View btnSave;
    private View btnEnableNext;
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
    private View cardSelectPanel;

    private View btnResetDate;
    private View btnSaveSelect;
    private View btnCancelSelectDay;
    private View blockDayPicker;
    private TextView textDay;
    private TextView textFromDay;
    private TextView textToDay;
    private TextView[] textDays = new TextView[31];
    private Integer selectedDay = null;
    private Integer[] selectedDaysRange = new Integer[2];
    private SelectDayType selectDayType;

    private View btnCancel;
    private View btnSelectAllMonth;
    private View btnAllMonth;
    private View blockMonthPicker;
    private View btnResetSelectMonth;
    private View btnSaveSelectMonth;
    private TextView btnMonth;
    private TextView[] textMonth = new TextView[12];
    private TreeSet<Integer> selectedMonth = new TreeSet<>();

    private TextView btnYear;
    private View btnSaveSelectYear;
    private View btnResetSelectYear;
    private View btnAllYear;
    private View btnResetYear;
    private View btnAllSelectYear;
    private View blockYearPicker;
    private RecyclerView recyclerYear;

    private TextView switchFourRandom;
    private TextView switchFourOriginal;
    private TextView switchThreeRandom;
    private TextView switchThreeOriginal;

    private LinearLayout cardSelectorBlock;

    private TextView textOne;
    private TextView textTwo;
    private TextView textThree;
    private TextView textFour;

    private TextView textDate;

    private RecyclerView recyclerView;
    private ResultAdapterOne resultAdapter;
    private SelectYearAdapter yearAdapter;

    private CardPosition curPosition;
    private int colorYellow;
    private int colorGray;
    private int colorWhite;
    private int colorLightGray;
    private int colorBlack;
    private int activeBg;
    private int circleSelectBg;
    private int whiteBorderBg;
    private int bottomBorderBg;

    private boolean[] optionsState = new boolean[]{true, false, false, false};
    private boolean enableNext = false;

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
        colorGray = getResources().getColor(R.color.gray);
        colorWhite = getResources().getColor(R.color.white);
        colorBlack = getResources().getColor(R.color.black);
        cards = getResources().getStringArray(R.array.cards);
        months = getResources().getStringArray(R.array.months);
        circleSelectBg = R.drawable.circle_select;
        activeBg = R.drawable.blue_radius_active_border;
        whiteBorderBg = R.drawable.white_active_border;
        bottomBorderBg = R.drawable.bottom_border;
    }

    private void initView() {
        imageArrow = findViewById(R.id.imageArrow);
        btnCancel = findViewById(R.id.btnCancel);
        btnResetDate = findViewById(R.id.btnResetDate);
        btnSave = findViewById(R.id.btnSave);
        btnEnableNext = findViewById(R.id.btnEnableNext);
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
        cardSelectPanel = findViewById(R.id.cardSelectPanel);

        switchFourRandom = findViewById(R.id.switchFourRandom);
        switchFourOriginal = findViewById(R.id.switchFourOriginal);
        switchThreeOriginal = findViewById(R.id.switchThreeOriginal);
        switchThreeRandom = findViewById(R.id.switchThreeRandom);

        textDate = findViewById(R.id.textDate);

        textOne = findViewById(R.id.textOne);
        textTwo = findViewById(R.id.textTwo);
        textThree = findViewById(R.id.textThree);
        textFour = findViewById(R.id.textFour);

        recyclerView = findViewById(R.id.recyclerResult);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        resultAdapter = new ResultAdapterOne(MainActivityOne.this);
        recyclerView.setAdapter(resultAdapter);

        toGone(secondSelectCardBlock);

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
                selectTextView.setBackgroundResource(bottomBorderBg);
                toGone(cardSelectorBlock);
                curPosition = null;
            });
            cardSelectorBlock.addView(textView);
        }

        imageArrow.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);

        initAction();
        initDayPicker();
        initMonthPicker();
        initYearPicker();
        textDay.post(this::initializationData);
    }

    private void initDayPicker() {
        btnCancelSelectDay = findViewById(R.id.btnCancelSelectDay);
        btnSaveSelect = findViewById(R.id.btnSaveSelect);
        blockDayPicker = findViewById(R.id.blockDayPicker);
        textDay = findViewById(R.id.textDay);
        textFromDay = findViewById(R.id.textFromDay);
        textToDay = findViewById(R.id.textToDay);

        blockDayPicker.post(() -> {
            for (int i = 0; i < textDays.length; i++) {
                TextView textView = blockDayPicker.findViewWithTag(String.valueOf(i + 1));
                textView.setOnClickListener(v -> {
                    resetAllSelect(textDays, new HashSet<>(Collections.singletonList(selectedDay)));
                    ((TextView) v).setTextColor(colorWhite);
                    v.setBackgroundResource(circleSelectBg);
                    switch (selectDayType) {
                        case GENERAL:
                            selectedDay = Integer.valueOf((String) v.getTag());
                            break;
                        case FROM:
                            selectedDaysRange[0] = Integer.valueOf((String) v.getTag());
                            break;
                        case TO:
                            selectedDaysRange[1] = Integer.valueOf((String) v.getTag());
                            break;
                    }
                });
                textDays[i] = textView;
            }
        });

        blockDayPicker.setOnClickListener(v -> {
        });

        textDay.setOnClickListener(v -> showSelectByType(GENERAL));

        textFromDay.setOnClickListener(v -> showSelectByType(FROM));

        textToDay.setOnClickListener(v -> showSelectByType(TO));

        btnSaveSelect.setOnClickListener(v -> {
            Calendar c = Calendar.getInstance();
            c.setTime(toDate(firstDate));

            switch (selectDayType) {
                case GENERAL:
                    if (selectedDay != null) {
                        selectedDaysRange = new Integer[2];
                        textDay.setText(String.valueOf(selectedDay));
                        textFromDay.setText(R.string.from);
                        textToDay.setText(R.string.to);
                    } else if (selectedDaysRange[0] != null || selectedDaysRange[1] != null) {
                        selectDayType = FROM;
                    }
                    break;
                case FROM:
                    if (selectedDaysRange[0] != null) {
                        if (selectedDaysRange[1] != null &&
                                selectedDaysRange[0] > selectedDaysRange[1]) {
                            Toast.makeText(MainActivityOne.this, String.format(getResources()
                                            .getString(R.string.error_from_less_then_to), selectedDaysRange[1]),
                                    Toast.LENGTH_LONG).show();
                            return;
                        }

                        selectedDay = null;
                        textDay.setText("интервал");
                        textFromDay.setText(String.valueOf(selectedDaysRange[0]));
                    } else if (selectedDay != null) {
                        selectDayType = GENERAL;
                    } else {
                        selectDayType = TO;
                    }
                case TO:
                    if (selectedDaysRange[1] != null) {
                        if (selectedDaysRange[0] != null &&
                                selectedDaysRange[1] < selectedDaysRange[0]) {
                            Toast.makeText(MainActivityOne.this, String.format(getResources()
                                            .getString(R.string.error_to_less_then_from), selectedDaysRange[0]),
                                    Toast.LENGTH_LONG).show();
                            return;
                        }
                        selectedDay = null;
                        textDay.setText("интервал");
                        textToDay.setText(String.valueOf(selectedDaysRange[1]));
                    } else if (selectedDay != null) {
                        selectDayType = GENERAL;
                    } else {
                        selectDayType = FROM;
                    }
                    break;
            }
            toGone(blockDayPicker);
        });

        btnCancelSelectDay.setOnClickListener(v -> btnSaveSelect.performClick());
    }

    private void initMonthPicker() {
        btnSelectAllMonth = findViewById(R.id.btnAllSelectMonth);
        btnAllMonth = findViewById(R.id.btnAllMonth);
        blockMonthPicker = findViewById(R.id.blockMonthPicker);
        btnResetSelectMonth = findViewById(R.id.btnResetSelectMonth);
        btnSaveSelectMonth = findViewById(R.id.btnSaveSelectMonth);
        btnMonth = findViewById(R.id.btnMonth);

        blockMonthPicker.post(() -> {
            for (int i = 0; i < textMonth.length; i++) {
                TextView textView = blockMonthPicker.findViewWithTag(String.valueOf(i + 1));
                textView.setOnClickListener(v -> {
                    Integer cur = Integer.valueOf((String) v.getTag());
                    if (selectedMonth.contains(cur)) {
                        ((TextView) v).setTextColor(colorGray);
                        v.setBackground(null);
                        selectedMonth.remove(cur);
                    } else {
                        ((TextView) v).setTextColor(colorWhite);
                        v.setBackgroundResource(circleSelectBg);
                        selectedMonth.add(cur);
                    }
                });
                textMonth[i] = textView;
            }
        });

        blockMonthPicker.setOnClickListener(v -> {
        });

        btnMonth.setOnClickListener(v -> toVisible(blockMonthPicker));

        btnSaveSelectMonth.setOnClickListener(v -> {
            Calendar c = Calendar.getInstance();
            c.setTime(toDate(firstDate));
            if (selectedMonth.isEmpty()) {
                int month = c.get(Calendar.MONTH);
                textMonth[month].setBackgroundResource(circleSelectBg);
                textMonth[month].setTextColor(colorWhite);
                btnMonth.setText(months[month]);
            } else if (selectedMonth.size() == 1) {
                int month = selectedMonth.first() - 1;
                textMonth[month].setBackgroundResource(circleSelectBg);
                textMonth[month].setTextColor(colorWhite);
                btnMonth.setText(months[month]);
            } else {
                if (selectedMonth.size() == 12) {
                    btnMonth.setText("все месяцы");
                } else {
                    btnMonth.setText("несколько");
                }
            }
            toGone(blockMonthPicker);
        });

        btnResetSelectMonth.setOnClickListener(v -> resetAllSelect(textMonth, selectedMonth));

        /*btnResetMonth.setOnClickListener(v -> {
            Calendar c = Calendar.getInstance();
            c.setTime(toDate(firstDate));
            int month = c.get(Calendar.MONTH);
            resetAllSelect(textMonth, selectedMonth);
            textMonth[month].setBackgroundResource(circleSelectBg);
            textMonth[month].setTextColor(colorWhite);
            btnMonth.setText(textMonth[month].getText());
        });*/

        btnSelectAllMonth.setOnClickListener(v -> selectAll(textMonth, selectedMonth, btnMonth));

        btnAllMonth.setOnClickListener(v -> selectAll(textMonth, selectedMonth, btnMonth));
    }

    private void initYearPicker() {
        btnAllSelectYear = findViewById(R.id.btnAllSelectYear);
        btnAllYear = findViewById(R.id.btnAllYear);
        btnYear = findViewById(R.id.btnYear);
        btnSaveSelectYear = findViewById(R.id.btnSaveSelectYear);
        btnResetSelectYear = findViewById(R.id.btnResetSelectYear);
        btnResetYear = findViewById(R.id.btnResetYear);
        blockYearPicker = findViewById(R.id.blockYearPicker);
        recyclerYear = findViewById(R.id.recyclerYear);

        yearAdapter = new SelectYearAdapter(MainActivityOne.this, true);
        recyclerYear.setLayoutManager(new LinearLayoutManager(this));
        recyclerYear.setAdapter(yearAdapter);
        yearAdapter.initData();

        blockYearPicker.setOnClickListener(v -> {
        });

        btnAllYear.setOnClickListener(v -> {
            yearAdapter.selectAll();
            btnYear.setText("все года");
        });

        btnAllSelectYear.setOnClickListener(v -> yearAdapter.selectAll());

        btnYear.setOnClickListener(v -> toVisible(blockYearPicker));

        btnSaveSelectYear.setOnClickListener(v -> {
            List<Integer> years = yearAdapter.getArraySelectedYears();
            if (years.isEmpty()) {
                Calendar c = Calendar.getInstance();
                c.setTime(toDate(firstDate));
                yearAdapter.selectYear(c.get(Calendar.YEAR));
                btnYear.setText(String.valueOf(c.get(Calendar.YEAR)));
            } else if (years.size() == 1) {
                btnYear.setText(String.valueOf(years.get(0)));
            } else {
                if (years.size() == yearAdapter.getAllItemCount()) {
                    btnYear.setText("все года");
                } else {
                    btnYear.setText("несколько");
                }
            }
            toGone(blockYearPicker);
        });

        btnResetSelectYear.setOnClickListener(v -> yearAdapter.clearAll());

        btnResetYear.setOnClickListener(v -> {
            yearAdapter.selectAll();
            btnYear.setText("все года");
        });
    }

    private void showSelectByType(SelectDayType selectDayType) {
        this.selectDayType = selectDayType;
        resetAllSelect(textDays, null);
        switch (selectDayType) {
            case GENERAL:
                if (selectedDay != null) {
                    selectTextView(selectedDay);
                }
                break;
            case FROM:
                if (selectedDaysRange[0] != null) {
                    selectTextView(selectedDaysRange[0]);
                }
                break;
            case TO:
                if (selectedDaysRange[1] != null) {
                    selectTextView(selectedDaysRange[1]);
                }
                break;
        }
        toVisible(blockDayPicker);
    }

    private void selectTextView(Integer value) {
        TextView textView = blockDayPicker.findViewWithTag(String.valueOf(value));
        textView.setTextColor(colorWhite);
        textView.setBackgroundResource(circleSelectBg);
    }

    private void selectAll(TextView[] array, Set<Integer> valueSet, TextView btn) {
        for (TextView item : array) {
            valueSet.add(Integer.valueOf((String) item.getTag()));
            item.setTextColor(colorWhite);
            item.setBackgroundResource(circleSelectBg);
            btn.setText("все месяцы");
        }
    }

    private void resetAllSelect(TextView[] array, Set<Integer> valueSet) {
        for (TextView day : array) {
            if (valueSet != null) {
                valueSet.clear();
            }
            day.setTextColor(colorGray);
            day.setBackground(null);
        }
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
        selectDayType = GENERAL;
        firstDate = date;
        textFromDay.setText(R.string.from);
        textToDay.setText(R.string.to);
        Calendar c = Calendar.getInstance();
        c.setTime(toDate(date));
        int curDay = c.get(Calendar.DAY_OF_MONTH);
        textDay.setText(String.valueOf(curDay));
        selectedDay = curDay;
        selectedDaysRange = new Integer[2];
        int month = c.get(Calendar.MONTH);
        resetAllSelect(textMonth, selectedMonth);
        textMonth[month].setBackgroundResource(circleSelectBg);
        textMonth[month].setTextColor(colorWhite);
        btnMonth.setText(months[month]);
        selectedMonth.add(month + 1);
        yearAdapter.selectAll();
        btnYear.setText("все года");
        saveDateAction();
    }

    private void changeEnableState(int pos, View view, int textColorActive, int textColorUnActive,
                                   int bgColor) {
        if (optionsState[pos]) {
            view.setBackground(null);
            ((TextView) view).setTextColor(textColorUnActive);
            optionsState[pos] = false;
        } else {
            view.setBackgroundResource(activeBg);
            ((TextView) view).setTextColor(textColorActive);
            view.setBackgroundResource(bgColor);
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

        btnSave.setOnClickListener(v -> {
            saveDateAction();
            toGone(blockSelectDate);
        });

        btnCancel.setOnClickListener(v -> btnSave.performClick());

        btnEnableNext.setOnClickListener(v -> {
            enableNext = enableNext ? false : true;
            imageArrow.setRotation(enableNext ? 180 : 0);
            imageArrow.setColorFilter(enableNext ? Color.YELLOW : Color.WHITE,
                    PorterDuff.Mode.SRC_ATOP);
        });

        btnReset.setOnClickListener(v -> clearData());

        btnSearch.setOnClickListener(v -> presenter.launchSearch());

        btnSetting.setOnClickListener(v -> toVisible(blockOptions));

        btnClose.setOnClickListener(v -> {
            if (!optionsState[0] && !optionsState[1] && !optionsState[2] && !optionsState[3]) {
                Toast.makeText(MainActivityOne.this, getResources()
                        .getString(R.string.error_select_option), Toast.LENGTH_LONG).show();
                return;
            }
            toGone(blockOptions);
        });

        textOne.setOnClickListener(this::selectCardClick);

        textTwo.setOnClickListener(this::selectCardClick);

        textThree.setOnClickListener(this::selectCardClick);

        textFour.setOnClickListener(this::selectCardClick);

        switchFourOriginal.setOnClickListener(v -> changeEnableState(0, v,
                getResources().getColor(R.color.black), colorWhite,
                R.drawable.four_original_active_border));

        switchFourRandom.setOnClickListener(v -> changeEnableState(1, v, colorWhite,
                colorWhite, R.drawable.four_random_active_border));

        switchThreeOriginal.setOnClickListener(v -> changeEnableState(2, v,
                getResources().getColor(R.color.black), colorWhite,
                R.drawable.three_original_active_border));

        switchThreeRandom.setOnClickListener(v -> changeEnableState(3, v, colorWhite,
                colorWhite, R.drawable.three_random_active_border));

        blockWait.setOnClickListener(v -> {
        });

        blockSelectDate.setOnClickListener(v -> {
        });

        textDate.setOnClickListener(v -> toVisible(blockSelectDate));

        btnResetDate.setOnClickListener(v -> initDate(firstDate));
    }

    private void selectCardClick(View view) {
        textOne.setBackgroundResource(bottomBorderBg);
        textTwo.setBackgroundResource(bottomBorderBg);
        textThree.setBackgroundResource(bottomBorderBg);
        textFour.setBackgroundResource(bottomBorderBg);
        view.setBackgroundResource(whiteBorderBg);
        curPosition = CardPosition.of((String) view.getTag());
        toVisible(cardSelectorBlock);
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
    public void clearData() {
        resultAdapter.setData(new ArrayList<>(), enableNext);
        recyclerView.setBackground(null);
    }

    @Override
    public Direction getDirection() {
        return null;
    }

    public List<Integer> getSelectedDays() {
        if (selectDayType == GENERAL) {
            return Collections.singletonList(selectedDay);
        } else {
            return Arrays.asList(selectedDaysRange);
        }
    }

    public Set<Integer> getSelectedMonths() {
        return selectedMonth;
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
        List<String> dates = new ArrayList<>();
        /*for (Integer day : selectedDay) {
            for (Integer month : selectedMonth) {
                for (Integer year : yearAdapter.getArraySelectedYears()) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(day < 10 ? "0" + day : String.valueOf(day));
                    sb.append(".");
                    sb.append(month < 10 ? "0" + month : String.valueOf(month));
                    sb.append(".");
                    sb.append(year);
                    dates.add(sb.toString());
                }
            }
        }*/
        return dates.toArray(new String[0]);
    }

    @Override
    public Integer getActiveCountGames() {
        return 0;
    }

    @Override
    public void setCardGroupListToRecycler(List<CardGroup> data) {

    }

    @Override
    public void setCardMatchListToRecycler(List<CardMatch> data) {
        resultAdapter.setData(data, enableNext);
        if (data.isEmpty()) {
            recyclerView.setBackground(null);
        } else {
            recyclerView.setBackgroundColor(colorBlack);
        }
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
        if (selectDayType != GENERAL &&
                (selectedDaysRange[0] == null || selectedDaysRange[1] == null)) {
            Toast.makeText(MainActivityOne.this, getResources()
                    .getString(R.string.error_select_day_range), Toast.LENGTH_LONG).show();
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
        StringBuilder sb = new StringBuilder();
        List<Integer> years = yearAdapter.getArraySelectedYears();

        if (selectDayType == GENERAL) {
            sb.append(selectedDay);
            if (selectedMonth.size() == 1) {
                sb.append("." + (selectedMonth.first() < 10 ? "0" + selectedMonth.first() :
                        selectedMonth.first()));

                if (yearAdapter.getAllItemCount() == years.size()) {
                    sb.append(".");
                    sb.append(years.get(years.size() - 1));

                    if (sb.toString().equals(firstDate)) {
                        textDate.setText(sb.toString());
                        return;
                    }
                }
            }
        }
        textDate.setText("Дата");
    }
}
