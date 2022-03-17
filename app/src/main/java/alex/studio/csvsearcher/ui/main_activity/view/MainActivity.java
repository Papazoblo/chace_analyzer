package alex.studio.csvsearcher.ui.main_activity.view;

import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import alex.studio.csvsearcher.R;
import alex.studio.csvsearcher.dto.CardGroup;
import alex.studio.csvsearcher.dto.CardSet;
import alex.studio.csvsearcher.dto.ColorMatch;
import alex.studio.csvsearcher.enums.CardPosition;
import alex.studio.csvsearcher.enums.Direction;
import alex.studio.csvsearcher.enums.Properties;
import alex.studio.csvsearcher.ui.adapter.ResultAdapter;
import alex.studio.csvsearcher.ui.adapter.ResultAdapterTwo;
import alex.studio.csvsearcher.ui.main_activity.Main;
import alex.studio.csvsearcher.ui.main_activity.presenter.MainPresenterOne;
import alex.studio.csvsearcher.ui.main_activity.presenter.MainPresenterTwo;
import alex.studio.csvsearcher.utils.ViewUtils;

import static alex.studio.csvsearcher.utils.StringUtils.generateDateString;
import static alex.studio.csvsearcher.utils.ViewUtils.changeVisible;
import static alex.studio.csvsearcher.utils.ViewUtils.getTextFrom;
import static alex.studio.csvsearcher.utils.ViewUtils.isAnyVisible;
import static alex.studio.csvsearcher.utils.ViewUtils.isEmpty;
import static alex.studio.csvsearcher.utils.ViewUtils.isVisible;
import static alex.studio.csvsearcher.utils.ViewUtils.toGone;
import static alex.studio.csvsearcher.utils.ViewUtils.toVisible;

public class MainActivity extends AppCompatActivity implements Main.View {

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
    private ImageView arrowRight;
    private ImageView arrowLeft;
    private ImageView arrowTop;
    private ImageView arrowBottom;
    private ImageView arrowTopLeft;
    private ImageView arrowBottomLeft;
    private ImageView arrowTopRight;
    private ImageView arrowBottomRight;
    private View btnFrom;
    private View btnTo;
    private View btnYes;
    private View btnNo;
    private View blockAskExit;
    private View blockCountGames;
    private View blockByDate;
    private View blockWait;

    private LinearLayout cardSelectorBlock;

    private TextView textDateFrom;
    private TextView textDateTo;
    private TextView btnCountGames;
    private TextView btnByDate;
    private TextView textAppNameSmall;

    private EditText editCount1;

    private TextView textOne;
    private TextView textTwo;
    private TextView textThree;
    private TextView textFour;

    private RecyclerView recyclerView;
    private ResultAdapter resultAdapter;
    private ResultAdapterTwo resultAdapterTwo;

    private int colorBlack;
    private int colorYellow;
    private int colorWhite;
    private int colorLightGray;

    private CardPosition curPosition;
    private Direction curDirection;
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
        switch (Properties.valueOf(getIntent().getStringExtra("type"))) {
            case ALGO_3:
                presenter = new MainPresenterOne();
                break;
            case ALGO_4:
                presenter = new MainPresenterTwo();
                break;
        }

        curDirection = null;
        curPosition = null;
        colorYellow = getResources().getColor(R.color.yellow);
        colorWhite = getResources().getColor(R.color.white);
        colorBlack = getResources().getColor(R.color.black);
        colorLightGray = getResources().getColor(R.color.textLightGrayBlue);
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
        arrowRight = findViewById(R.id.arrowRight);
        arrowLeft = findViewById(R.id.arrowLeft);
        arrowTop = findViewById(R.id.arrowTop);
        arrowBottom = findViewById(R.id.arrowBottom);
        arrowTopLeft = findViewById(R.id.arrowTopLeft);
        arrowBottomLeft = findViewById(R.id.arrowBottomLeft);
        arrowTopRight = findViewById(R.id.arrowTopRight);
        arrowBottomRight = findViewById(R.id.arrowBottomRight);
        btnFrom = findViewById(R.id.btnFrom);
        btnTo = findViewById(R.id.btnTo);
        btnYes = findViewById(R.id.btnYes);
        btnNo = findViewById(R.id.btnNo);
        blockAskExit = findViewById(R.id.blockAskExit);
        blockCountGames = findViewById(R.id.blockCountGames);
        blockByDate = findViewById(R.id.blockByDate);
        blockWait = findViewById(R.id.blockWait);
        cardSelectorBlock = findViewById(R.id.cardSelectorBlock);

        textAppNameSmall = findViewById(R.id.textAppNameSmall);
        textDateFrom = findViewById(R.id.textDateFrom);
        textDateTo = findViewById(R.id.textDateTo);
        btnCountGames = findViewById(R.id.btnCountGames);
        btnByDate = findViewById(R.id.btnByDate);

        editCount1 = findViewById(R.id.editCount1);

        textOne = findViewById(R.id.textOne);
        textTwo = findViewById(R.id.textTwo);
        textThree = findViewById(R.id.textThree);
        textFour = findViewById(R.id.textFour);

        recyclerView = findViewById(R.id.recyclerResult);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        switch (Properties.valueOf(getIntent().getStringExtra("type"))) {
            case ALGO_3:
                resultAdapter = new ResultAdapter(MainActivity.this);
                recyclerView.setAdapter(resultAdapter);
                break;
            case ALGO_4:
                resultAdapterTwo = new ResultAdapterTwo(MainActivity.this);
                recyclerView.setAdapter(resultAdapterTwo);
                break;

        }

        btnByDate.post(() -> changeButtonWidth(btnSearch.getWidth(), btnCountGames, btnByDate));

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
                toGone(cardSelectorBlock);
                curPosition = null;
            });
            cardSelectorBlock.addView(textView);
        }

        initEquipment();
        initAction();
    }

    private void initAction() {

        btnYes.setOnClickListener(v -> {
            toGone(blockAskExit);
            finish();
        });

        btnNo.setOnClickListener(v -> toGone(blockAskExit));

        blockAskExit.setOnClickListener(v -> {
        });

        btnResetDateTo.setOnClickListener(v -> textDateTo.setText(""));

        btnResetDateFrom.setOnClickListener(v -> textDateFrom.setText(""));

        btnReset.setOnClickListener(v -> {
            clearData();
            unselectAllColor(arrowTop, arrowBottom, arrowLeft, arrowRight, arrowBottomLeft,
                    arrowBottomRight, arrowTopLeft, arrowTopRight);
        });

        btnSearch.setOnClickListener(v -> {
            curDirection = null;
            presenter.launchSearch();
            unselectAllColor(arrowTop, arrowBottom, arrowLeft, arrowRight, arrowBottomLeft,
                    arrowBottomRight, arrowTopLeft, arrowTopRight);
        });

        btnCountGames.setOnClickListener(v -> changeStateOption(btnCountGames, 1,
                blockCountGames, blockByDate));

        btnByDate.setOnClickListener(v -> changeStateOption(btnByDate, 3, blockByDate,
                blockCountGames));

        btnRight.setOnClickListener(v -> selectArrow(arrowRight));

        btnLeft.setOnClickListener(v -> selectArrow(arrowLeft));

        btnTop.setOnClickListener(v -> selectArrow(arrowTop));

        btnBottom.setOnClickListener(v -> selectArrow(arrowBottom));

        btnTopLeft.setOnClickListener(v -> selectArrow(arrowTopLeft));

        btnBottomLeft.setOnClickListener(v -> selectArrow(arrowBottomLeft));

        btnTopRight.setOnClickListener(v -> selectArrow(arrowTopRight));

        btnBottomRight.setOnClickListener(v -> selectArrow(arrowBottomRight));

        btnFrom.setOnClickListener(v -> initDatePickerDialog(true));

        btnTo.setOnClickListener(v -> initDatePickerDialog(false));

        blockWait.setOnClickListener(v -> {
        });

        textOne.setOnClickListener(this::selectCardClick);

        textTwo.setOnClickListener(this::selectCardClick);

        textThree.setOnClickListener(this::selectCardClick);

        textFour.setOnClickListener(this::selectCardClick);
    }

    private void selectCardClick(View view) {
        curPosition = CardPosition.of((String) view.getTag());
        toVisible(cardSelectorBlock);
    }

    private void initEquipment() {

        switch (Properties.valueOf(getIntent().getStringExtra("type"))) {
            case ALGO_3:
                initEquipmentOne();
                break;
            case ALGO_4:
                initEquipmentTwo();
                break;
        }
    }

    private void initEquipmentOne() {
        textAppNameSmall.setText("");
        toVisible(textAppNameSmall);
    }

    private void initEquipmentTwo() {
        textAppNameSmall.setText(getResources().getString(R.string.search_dropped_after_the_given_without_enter));
        toVisible(textAppNameSmall);
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

    private void selectArrow(ImageView view) {
        selectCurrentArrow(view);
        curDirection = Direction.of((String) view.getTag());
        presenter.launchSearch();
    }

    private void selectCurrentArrow(ImageView view) {
        unselectAllColor(arrowTop, arrowBottom, arrowLeft, arrowRight, arrowBottomLeft,
                arrowBottomRight, arrowTopLeft, arrowTopRight);

        view.setColorFilter(colorYellow, PorterDuff.Mode.SRC_ATOP);
    }

    private void unselectAllColor(ImageView... views) {
        for (ImageView v : views) {
            v.setColorFilter(colorBlack, PorterDuff.Mode.SRC_ATOP);
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
    protected void onResume() {
        super.onResume();

        presenter.setView(this);
    }

    @Override
    public Context getContext() {
        return MainActivity.this;
    }

    @Override
    public Direction getDirection() {
        return curDirection;
    }

    @Override
    public String[] getCards() {

        return new String[]{
                getTextFrom(textOne),
                getTextFrom(textTwo),
                getTextFrom(textThree),
                getTextFrom(textFour)
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
        }
        return null;
    }

    @Override
    public Integer getActiveCountGames() {
        try {
            return Integer.valueOf(getTextFrom(editCount1));
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
        switch (Properties.valueOf(getIntent().getStringExtra("type"))) {
            case ALGO_3:
                if (getTextFrom(textOne).equals(cards[0]) ||
                        getTextFrom(textTwo).equals(cards[0]) ||
                        getTextFrom(textThree).equals(cards[0]) ||
                        getTextFrom(textFour).equals(cards[0])) {
                    Toast.makeText(MainActivity.this, getResources()
                            .getString(R.string.error_select_card_one), Toast.LENGTH_LONG).show();
                    return false;
                }
            case ALGO_4:
                if (getTextFrom(textOne).equals(cards[0]) ||
                        getTextFrom(textTwo).equals(cards[0])) {

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
        if (curPosition != null) {
            curPosition = null;
            toGone(cardSelectorBlock);
            return;
        }
        changeVisible(blockAskExit);
    }
}
