package alex.studio.csvsearcher.ui.main_activity.view;

import static alex.studio.csvsearcher.utils.ViewUtils.changeVisible;
import static alex.studio.csvsearcher.utils.ViewUtils.getTextFrom;
import static alex.studio.csvsearcher.utils.ViewUtils.toGone;
import static alex.studio.csvsearcher.utils.ViewUtils.toVisible;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import alex.studio.csvsearcher.R;
import alex.studio.csvsearcher.dto.CardGroup;
import alex.studio.csvsearcher.dto.CardMatch;
import alex.studio.csvsearcher.dto.CardSet;
import alex.studio.csvsearcher.dto.CardSetSix;
import alex.studio.csvsearcher.dto.ColorMatch;
import alex.studio.csvsearcher.enums.CardPosition;
import alex.studio.csvsearcher.enums.Direction;
import alex.studio.csvsearcher.ui.adapter.ResultAdapterTwo;
import alex.studio.csvsearcher.ui.main_activity.Main;
import alex.studio.csvsearcher.ui.main_activity.presenter.MainPresenterTwo;

public class MainActivityTwo extends AppCompatActivity implements Main.View {

    private Main.Presenter presenter;

    private View blockOptions;
    private View btnResetField;
    private View btnSearch;
    private View btnYes;
    private View btnNo;
    private View blockAskExit;
    private View blockWait;
    private View cardSelectPanel;
    private View btnClose;
    private View btnPlusOne;
    private View btnFirstPare;
    private View btnSecondPare;
    private View btnThirdPare;
    private View btnFourthPare;
    private View btnSettings;
    private View btnResetResult;

    private LinearLayout cardSelectorBlock;

    private TextView textOne;
    private TextView textTwo;
    private TextView textThree;
    private TextView textFour;
    private TextView textFive;
    private TextView textSix;
    private TextView textSeven;
    private TextView textEight;

    private RecyclerView recyclerView;
    private ResultAdapterTwo resultAdapter;

    private CardPosition curPosition;
    private int colorYellow;
    private int colorLightGray;
    private int colorBlack;
    private int activeBg;
    private int blueBorderBg;
    private int whiteBorderBg;
    private int bottomBorderBg;

    private String[] cards;
    private boolean[] options = new boolean[]{true, true, true, true};
    private boolean enabledPlusOne = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_two);

        initConstant();
        initView();
    }

    private void initConstant() {
        presenter = new MainPresenterTwo();
        curPosition = null;

        colorBlack = getResources().getColor(R.color.black);
        colorLightGray = getResources().getColor(R.color.textLightGrayBlue);
        colorYellow = getResources().getColor(R.color.yellow);
        cards = getResources().getStringArray(R.array.cards);
        activeBg = R.drawable.blue_radius_active_border;
        whiteBorderBg = R.drawable.white_active_border;
        bottomBorderBg = R.drawable.bottom_border;
        blueBorderBg = R.drawable.blue_active_border;
    }

    private void initView() {
        btnResetField = findViewById(R.id.btnResetField);
        btnSearch = findViewById(R.id.btnSearch);
        btnYes = findViewById(R.id.btnYes);
        btnNo = findViewById(R.id.btnNo);
        blockAskExit = findViewById(R.id.blockAskExit);
        blockWait = findViewById(R.id.blockWait);
        cardSelectorBlock = findViewById(R.id.cardSelectorBlock);
        cardSelectPanel = findViewById(R.id.cardSelectPanel);
        btnClose = findViewById(R.id.btnClose);
        btnPlusOne = findViewById(R.id.btnPlusOne);
        btnFirstPare = findViewById(R.id.btnFirstPare);
        btnSecondPare = findViewById(R.id.btnSecondPare);
        btnThirdPare = findViewById(R.id.btnThirdPare);
        btnFourthPare = findViewById(R.id.btnFourthPare);
        btnSettings = findViewById(R.id.btnSettings);
        btnResetResult = findViewById(R.id.btnResetResult);
        blockOptions = findViewById(R.id.blockOptions);

        textOne = findViewById(R.id.textOne);
        textTwo = findViewById(R.id.textTwo);
        textThree = findViewById(R.id.textThree);
        textFour = findViewById(R.id.textFour);
        textFive = findViewById(R.id.textFive);
        textSix = findViewById(R.id.textSix);
        textSeven = findViewById(R.id.textSeven);
        textEight = findViewById(R.id.textEight);
        textEight.post(this::initializationData);

        recyclerView = findViewById(R.id.recyclerResult);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        resultAdapter = new ResultAdapterTwo(MainActivityTwo.this);
        recyclerView.setAdapter(resultAdapter);

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
                switch (curPosition) {
                    case ONE:
                    case FIVE:
                        changeOptionIndicatorState(new View[]{selectTextView}, options[0]);
                        break;
                    case TWO:
                    case SIX:
                        changeOptionIndicatorState(new View[]{selectTextView}, options[1]);
                        break;
                    case THREE:
                    case SEVEN:
                        changeOptionIndicatorState(new View[]{selectTextView}, options[2]);
                        break;
                    case FOUR:
                    case EIGHT:
                        changeOptionIndicatorState(new View[]{selectTextView}, options[3]);
                        break;
                }
                toGone(cardSelectorBlock);
                curPosition = null;
            });
            cardSelectorBlock.addView(textView);
        }

        initAction();
    }

    private void initializationData() {
        presenter.initializationData((List<CardSet> list) -> {
            CardSet val1 = list.get(0);
            CardSet val2 = list.get(1);

            textOne.setText(val1.getCard1());
            textTwo.setText(val1.getCard2());
            textThree.setText(val1.getCard3());
            textFour.setText(val1.getCard4());

            textFive.setText(val2.getCard1());
            textSix.setText(val2.getCard2());
            textSeven.setText(val2.getCard3());
            textEight.setText(val2.getCard4());

            textOne.setBackgroundResource(blueBorderBg);
            textTwo.setBackgroundResource(blueBorderBg);
            textThree.setBackgroundResource(blueBorderBg);
            textFour.setBackgroundResource(blueBorderBg);
            textFive.setBackgroundResource(blueBorderBg);
            textSix.setBackgroundResource(blueBorderBg);
            textSeven.setBackgroundResource(blueBorderBg);
            textEight.setBackgroundResource(blueBorderBg);

/*

            textOne.setText(cards[3]);
            textTwo.setText(cards[1]);
            textThree.setText(cards[1]);
            textFour.setText(cards[4]);

            textFive.setText(cards[2]);
            textSix.setText(cards[8]);
            textSeven.setText(cards[6]);
            textEight.setText(cards[7]);*/
        });
    }

    private void resetCards() {
        textOne.setText(cards[0]);
        textTwo.setText(cards[0]);
        textThree.setText(cards[0]);
        textFour.setText(cards[0]);
        textFive.setText(cards[0]);
        textSix.setText(cards[0]);
        textSeven.setText(cards[0]);
        textEight.setText(cards[0]);
    }

    private void initAction() {

        btnYes.setOnClickListener(v -> {
            toGone(blockAskExit);
            finish();
        });

        btnNo.setOnClickListener(v -> toGone(blockAskExit));

        blockAskExit.setOnClickListener(v -> {
        });

        btnResetField.setOnClickListener(v -> resetCards());

        btnResetResult.setOnClickListener(v -> resultAdapter.setData(Collections.emptyList()));

        btnSearch.setOnClickListener(v -> presenter.launchSearch());

        textOne.setOnClickListener(this::selectCardClick);

        textTwo.setOnClickListener(this::selectCardClick);

        textThree.setOnClickListener(this::selectCardClick);

        textFour.setOnClickListener(this::selectCardClick);

        textFive.setOnClickListener(this::selectCardClick);

        textSix.setOnClickListener(this::selectCardClick);

        textSeven.setOnClickListener(this::selectCardClick);

        textEight.setOnClickListener(this::selectCardClick);

        blockWait.setOnClickListener(v -> {
        });

        btnSettings.setOnClickListener(v -> toVisible(blockOptions));

        btnClose.setOnClickListener(v -> toGone(blockOptions));

        btnPlusOne.setOnClickListener(v -> enabledPlusOne = changeBtnState(v, enabledPlusOne));

        btnFirstPare.setOnClickListener(v -> {
            options[0] = changeBtnState(v, options[0]);
            changeOptionIndicatorState(new View[]{textOne, textFive}, options[0]);
        });

        btnSecondPare.setOnClickListener(v -> {
            options[1] = changeBtnState(v, options[1]);
            changeOptionIndicatorState(new View[]{textTwo, textSix}, options[1]);
        });

        btnThirdPare.setOnClickListener(v -> {
            options[2] = changeBtnState(v, options[2]);
            changeOptionIndicatorState(new View[]{textThree, textSeven}, options[2]);
        });

        btnFourthPare.setOnClickListener(v -> {
            options[3] = changeBtnState(v, options[3]);
            changeOptionIndicatorState(new View[]{textFour, textEight}, options[3]);
        });
    }

    private boolean changeBtnState(View view, boolean state) {
        if (state) {
            view.setBackground(null);
            return false;
        } else {
            view.setBackgroundResource(activeBg);
            return true;
        }
    }

    private void changeOptionIndicatorState(View[] views, boolean val) {
        if (val) {
            for (View view : views) {
                view.setBackgroundResource(blueBorderBg);
            }
        } else {
            for (View view : views) {
                view.setBackgroundResource(bottomBorderBg);
            }
        }
    }

    private void selectCardClick(View view) {

        changeOptionIndicatorState(new View[]{textOne, textFive}, options[0]);
        changeOptionIndicatorState(new View[]{textTwo, textSix}, options[1]);
        changeOptionIndicatorState(new View[]{textThree, textSeven}, options[2]);
        changeOptionIndicatorState(new View[]{textFour, textEight}, options[3]);
        view.setBackgroundResource(whiteBorderBg);
        curPosition = CardPosition.of((String) view.getTag());
        visibleViewWithPosition(view, cardSelectorBlock);
    }

    private static void visibleViewWithPosition(View pointView, View visibleBlock) {
        int[] location = new int[2];
        pointView.getLocationOnScreen(location);
        ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) visibleBlock.getLayoutParams();
        params.topMargin = location[1] + (location[1] / 4);
        visibleBlock.setLayoutParams(params);
        toVisible(visibleBlock);
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.setView(this);
    }

    @Override
    public Context getContext() {
        return MainActivityTwo.this;
    }

    @Override
    public void clearData() {

    }

    public boolean[] getOptions() {
        return options;
    }

    public boolean isEnabledPlusOne() {
        return enabledPlusOne;
    }

    @Override
    public Direction getDirection() {
        return null;
    }

    @Override
    public String[] getCards() {
        return new String[]{
                getTextFrom(textOne),
                getTextFrom(textTwo),
                getTextFrom(textThree),
                getTextFrom(textFour),
                getTextFrom(textFive),
                getTextFrom(textSix),
                getTextFrom(textSeven),
                getTextFrom(textEight)
        };
    }

    @Override
    public List<String[]> getCardsList() {
        return new ArrayList<>();
    }

    @Override
    public String[] getActiveDate() {
        return new String[]{};
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
    }

    @Override
    public void setCardSetListToRecycler(List<CardSet> data) {
        resultAdapter.setData(data);

        if (data.isEmpty()) {
            recyclerView.setBackground(null);
        } else {
            recyclerView.setBackgroundColor(colorBlack);
        }
    }

    @Override
    public void setCardSetSixListToRecycler(List<CardSetSix> data) {

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

        if ((options[0] && (getTextFrom(textOne).equals(cards[0]) || getTextFrom(textFive).equals(cards[0])))
                || (options[1] && (getTextFrom(textTwo).equals(cards[0]) || getTextFrom(textSix).equals(cards[0])))
                || (options[2] && (getTextFrom(textThree).equals(cards[0]) || getTextFrom(textSeven).equals(cards[0])))
                || (options[3] && (getTextFrom(textFour).equals(cards[0]) || getTextFrom(textEight).equals(cards[0])))) {

            Toast.makeText(MainActivityTwo.this, getResources()
                    .getString(R.string.error_select_card_with_options), Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
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
