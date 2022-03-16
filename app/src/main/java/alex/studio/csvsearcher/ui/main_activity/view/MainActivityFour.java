package alex.studio.csvsearcher.ui.main_activity.view;

import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
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
import alex.studio.csvsearcher.enums.Direction;
import alex.studio.csvsearcher.ui.adapter.CardAdapterFour;
import alex.studio.csvsearcher.ui.adapter.ResultAdapterFour;
import alex.studio.csvsearcher.ui.main_activity.Main;
import alex.studio.csvsearcher.ui.main_activity.presenter.MainPresenterFour;

import static alex.studio.csvsearcher.enums.Direction.TOP;
import static alex.studio.csvsearcher.utils.StringUtils.generateDateString;
import static alex.studio.csvsearcher.utils.ViewUtils.changeVisible;
import static alex.studio.csvsearcher.utils.ViewUtils.getTextFrom;
import static alex.studio.csvsearcher.utils.ViewUtils.isAnyVisible;
import static alex.studio.csvsearcher.utils.ViewUtils.isEmpty;
import static alex.studio.csvsearcher.utils.ViewUtils.toGone;
import static alex.studio.csvsearcher.utils.ViewUtils.toVisible;

public class MainActivityFour extends AppCompatActivity implements Main.View {

    private Main.Presenter presenter;

    private View btnAddRow;
    private View btnResetDateFrom;
    private View btnResetDateTo;
    private View btnReset;
    private View btnSearch;
    private View btnTop;
    private View btnBottom;
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
    private TextView textNotMatch;

    private EditText editCount1;

    private RecyclerView recyclerRow;
    private RecyclerView recyclerView;
    private ResultAdapterFour resultAdapterFour;
    private CardAdapterFour cardAdapterFour;

    private int colorYellow;
    private int colorWhite;

    private Direction curDirection;
    int yearCur;
    int monthCur;
    int dayCur;
    int yearCurTo;
    int monthCurTo;
    int dayCurTo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_four);

        initConstant();
        initView();
    }

    private void initConstant() {
        presenter = new MainPresenterFour();

        curDirection = TOP;

        colorYellow = getResources().getColor(R.color.yellow);
        colorWhite = getResources().getColor(R.color.white);

        Calendar dateAndTime = Calendar.getInstance();
        yearCur = yearCurTo = dateAndTime.get(Calendar.YEAR);
        monthCur = monthCurTo = dateAndTime.get(Calendar.MONTH);
        dayCur = dayCurTo = dateAndTime.get(Calendar.DAY_OF_MONTH);
    }

    private void initView() {
        btnAddRow = findViewById(R.id.btnAddRow);
        btnResetDateFrom = findViewById(R.id.btnResetDateFrom);
        btnResetDateTo = findViewById(R.id.btnResetDateTo);
        btnReset = findViewById(R.id.btnReset);
        btnSearch = findViewById(R.id.btnSearch);
        btnTop = findViewById(R.id.btnTop);
        btnBottom = findViewById(R.id.btnBottom);
        btnFrom = findViewById(R.id.btnFrom);
        btnTo = findViewById(R.id.btnTo);
        btnYes = findViewById(R.id.btnYes);
        btnNo = findViewById(R.id.btnNo);
        blockAskExit = findViewById(R.id.blockAskExit);
        blockCountGames = findViewById(R.id.blockCountGames);
        blockByDate = findViewById(R.id.blockByDate);
        blockWait = findViewById(R.id.blockWait);

        textNotMatch = findViewById(R.id.textNotMatch);
        textDateFrom = findViewById(R.id.textDateFrom);
        textDateTo = findViewById(R.id.textDateTo);
        btnCountGames = findViewById(R.id.btnCountGames);
        btnByDate = findViewById(R.id.btnByDate);

        editCount1 = findViewById(R.id.editCount1);

        recyclerView = findViewById(R.id.recyclerResult);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        resultAdapterFour = new ResultAdapterFour(MainActivityFour.this);
        recyclerView.setAdapter(resultAdapterFour);
        recyclerRow = findViewById(R.id.recyclerRow);
        recyclerRow.setLayoutManager(new LinearLayoutManager(this));
        cardAdapterFour = new CardAdapterFour(MainActivityFour.this);
        recyclerRow.setAdapter(cardAdapterFour);

        changeColor((ImageView) btnTop, colorYellow);
        initAction();
    }

    private void initAction() {

        btnYes.setOnClickListener(v -> {
            toGone(blockAskExit);
            finish();
        });

        btnNo.setOnClickListener(v -> toGone(blockAskExit));

        blockAskExit.setOnClickListener(v -> {});

        btnAddRow.setOnClickListener(v -> cardAdapterFour.addRow());

        btnResetDateTo.setOnClickListener(v -> textDateTo.setText(""));

        btnResetDateFrom.setOnClickListener(v -> textDateFrom.setText(""));

        btnReset.setOnClickListener(v -> clearData());

        btnSearch.setOnClickListener(v -> {
            if(curDirection != null) {
                toGone(textNotMatch);
                presenter.launchSearch();
            }
        });

        btnCountGames.setOnClickListener(v -> changeStateOption(btnCountGames, 1,
                blockCountGames, blockByDate));

        btnByDate.setOnClickListener(v -> changeStateOption(btnByDate, 3, blockByDate,
                blockCountGames));

        btnFrom.setOnClickListener(v -> initDatePickerDialog(true));

        btnTo.setOnClickListener(v -> initDatePickerDialog(false));

        btnTop.setOnClickListener(v -> launchSearch(TOP));

        btnBottom.setOnClickListener(v -> launchSearch(Direction.BOTTOM));

        blockWait.setOnClickListener(v -> {
        });
    }

    private void launchSearch(Direction type) {
        curDirection = type;
        unselectArrows((ImageView) btnBottom, (ImageView) btnTop);

        if (type == TOP) {
            changeColor((ImageView) btnTop, colorYellow);
        } else {
            changeColor((ImageView) btnBottom, colorYellow);
        }
    }

    private void unselectArrows(ImageView... images) {
        for (ImageView image : images) {
            changeColor(image, colorWhite);
        }
    }

    private void changeColor(ImageView image, int color) {
        image.setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
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
        Toast.makeText(MainActivityFour.this, getResources().getString(
                R.string.close_other_option), Toast.LENGTH_LONG).show();
    }

    private void initDatePickerDialog(boolean dateType) {

        DatePickerDialog datePickerDialog;
        if (dateType) {
            datePickerDialog = new DatePickerDialog(MainActivityFour.this,
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
            datePickerDialog = new DatePickerDialog(MainActivityFour.this,
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

    @Override
    protected void onResume() {
        super.onResume();

        presenter.setView(this);
    }

    @Override
    public Context getContext() {
        return MainActivityFour.this;
    }

    @Override
    public Direction getDirection() {
        return curDirection;
    }

    @Override
    public String[] getCards() {
        return new String[0];
    }

    @Override
    public List<String[]> getCardsList() {
        return cardAdapterFour.getListGroupCards();
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
        toGone(textNotMatch);
        resultAdapterFour.setData(new ArrayList<>());
        launchSearch(TOP);
    }

    @Override
    public void setRecyclerData(List<CardGroup> data) {

    }

    @Override
    public void setRecyclerResultData(List<CardSet> data) {
        resultAdapterFour.setData(data);
        if (data.isEmpty()) {
            toVisible(textNotMatch);
        }
    }

    @Override
    public void setRecyclerResultData(List<Map<String, ColorMatch>> data, List<List<CardSet>> list) {

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
        if (cardAdapterFour.getItemCount() < 2) {
            Toast.makeText(MainActivityFour.this,
                    getResources().getString(R.string.error_min_limit_rows),
                    Toast.LENGTH_LONG).show();
            return false;
        } else {

            if (!cardAdapterFour.isSelectedValid()) {
                Toast.makeText(MainActivityFour.this,
                        getResources().getString(R.string.error_select_card_position),
                        Toast.LENGTH_LONG).show();
                return false;
            }

            if (!cardAdapterFour.isHasCardInRow()) {
                Toast.makeText(MainActivityFour.this,
                        getResources().getString(R.string.error_row_is_empty),
                        Toast.LENGTH_LONG).show();
                return false;
            }

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
