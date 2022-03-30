package alex.studio.csvsearcher.ui.adapter;

import static alex.studio.csvsearcher.utils.ViewUtils.getTextFrom;
import static alex.studio.csvsearcher.utils.ViewUtils.toGone;
import static alex.studio.csvsearcher.utils.ViewUtils.toVisible;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import alex.studio.csvsearcher.R;
import alex.studio.csvsearcher.enums.CardPosition;

public class CardAdapterSix extends RecyclerView.Adapter<CardAdapterSix.ResultViewHolder> {

    private final int MAX_ROW_COUNT = 7;

    private List<String[]> listGroupCards = new ArrayList<>();
    private List<CardPosition> curPositions = new ArrayList<>();
    private List<View> cardSelectPanels = new ArrayList<>();
    private List<List<TextView>> textViews = new ArrayList<>();
    private String[] cardArray;
    private Context context;
    private int colorYellow;
    private int colorLightGray;
    private Drawable whiteBorderBg;
    private Drawable bottomBorderBg;

    public CardAdapterSix(Context context) {
        this.context = context;
        this.colorYellow = context.getResources().getColor(R.color.yellow);
        this.colorLightGray = context.getResources().getColor(R.color.textLightGrayBlue);
        this.cardArray = context.getResources().getStringArray(R.array.cards);
        whiteBorderBg = context.getResources().getDrawable(R.drawable.white_active_border);
        bottomBorderBg = context.getResources().getDrawable(R.drawable.bottom_border);
    }

    @NonNull
    @Override
    public ResultViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ResultViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.item_recycler_4, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ResultViewHolder h, int pos) {

        String[] cards = listGroupCards.get(pos);

        cardSelectPanels.add(h.cardSelectorBlock);
        textViews.add(Arrays.asList(h.textOne, h.textTwo, h.textThree, h.textFour));

        h.textOne.setOnClickListener(v -> selectCardClick(h, v, pos));

        h.textTwo.setOnClickListener(v -> selectCardClick(h, v, pos));

        h.textThree.setOnClickListener(v -> selectCardClick(h, v, pos));

        h.textFour.setOnClickListener(v -> selectCardClick(h, v, pos));

        h.textOne.setText(cards[0]);
        changeColor(cards[0], h.textOne);
        h.textTwo.setText(cards[1]);
        changeColor(cards[1], h.textTwo);
        h.textThree.setText(cards[2]);
        changeColor(cards[2], h.textThree);
        h.textFour.setText(cards[3]);
        changeColor(cards[3], h.textFour);

        h.cardSelectorBlock.removeAllViews();
        for (int i = 0; i < cardArray.length; i++) {
            TextView textView = (TextView) LayoutInflater.from(context)
                    .inflate(R.layout.card_selecter, null);
            textView.setText(cardArray[i]);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT
            );
            params.weight = 1;
            if (i != cardArray.length - 1) {
                params.rightMargin = 1;
            }
            textView.setLayoutParams(params);
            textView.setOnClickListener(v -> {
                TextView selectTextView = null;
                switch (curPositions.get(pos)) {
                    case ONE:
                        selectTextView = h.textOne;
                        cards[0] = getTextFrom(v);
                        break;
                    case TWO:
                        selectTextView = h.textTwo;
                        cards[1] = getTextFrom(v);
                        break;
                    case THREE:
                        selectTextView = h.textThree;
                        cards[2] = getTextFrom(v);
                        break;
                    case FOUR:
                        selectTextView = h.textFour;
                        cards[3] = getTextFrom(v);
                        break;
                }
                String curText = getTextFrom(v);
                selectTextView.setText(curText);
                selectTextView.setBackground(bottomBorderBg);
                changeColor(curText, selectTextView);
                toGone(h.cardSelectorBlock);
                curPositions.set(pos, null);
            });
            h.cardSelectorBlock.addView(textView);
        }

        h.btnDelete.setOnClickListener(v -> deleteRow(pos));
        h.btnClear.setOnClickListener(v -> clearRow(pos));
    }

    private void changeColor(String curText, TextView textView) {
        if (curText.equals(cardArray[0])) {
            textView.setTextColor(colorLightGray);
        } else {
            textView.setTextColor(colorYellow);
        }
    }

    private void selectCardClick(ResultViewHolder h, View view, int pos) {
        for (View panel : cardSelectPanels) {
            toGone(panel);
        }
        for (List<TextView> group : textViews) {
            for (TextView tv : group) {
                tv.setBackground(bottomBorderBg);
            }
        }
        view.setBackground(whiteBorderBg);
        curPositions.set(pos, CardPosition.of((String) view.getTag()));
        toVisible(h.cardSelectorBlock);
    }

    @Override
    public int getItemCount() {
        return listGroupCards.size();
    }

    public List<String[]> getListGroupCards() {
        return listGroupCards;
    }

    public void addRow() {
        if (listGroupCards.size() < MAX_ROW_COUNT) {
            if(listGroupCards.isEmpty()) {
                listGroupCards.add(new String[]{"-", "-", "-", "-"});
                curPositions.add(null);
            }
            listGroupCards.add(new String[]{"-", "-", "-", "-"});
            curPositions.add(null);
            notifyDataSetChanged();
        } else {
            Toast.makeText(context, context.getResources().getString(R.string.error_max_limit_rows),
                    Toast.LENGTH_LONG).show();
        }
    }

    public boolean isHasCardInRow() {
        String[] pattern = listGroupCards.get(0);

        for(String card : pattern) {
            if(!card.equals(cardArray[0])) {
                return true;
            }
        }
        return false;
    }

    public boolean isSelectedValid() {
        String[] pattern = listGroupCards.get(0);

        for (int i = 1; i < listGroupCards.size(); i++) {
            for(int j = 0 ; j < pattern.length ; j++) {
                if(pattern[j].equals(cardArray[0]) && !listGroupCards.get(i)[j].equals(cardArray[0]) ||
                        !pattern[j].equals(cardArray[0]) && listGroupCards.get(i)[j].equals(cardArray[0])) {
                    return false;
                }
            }
        }
        return true;
    }

    private void deleteRow(int pos) {
        listGroupCards.remove(pos);
        curPositions.remove(pos);
        cardSelectPanels.remove(pos);
        notifyDataSetChanged();
    }

    private void clearRow(int pos) {
        for(int i = 0 ; i < 4 ; i++) {
            listGroupCards.get(pos)[i] = "-";
        }
        notifyDataSetChanged();
    }

    public static class ResultViewHolder extends RecyclerView.ViewHolder {

        private TextView textOne;
        private TextView textTwo;
        private TextView textThree;
        private TextView textFour;

        private LinearLayout cardSelectorBlock;

        private View btnDelete;
        private View btnClear;

        public ResultViewHolder(@NonNull View v) {
            super(v);

            textOne = v.findViewById(R.id.textOne);
            textTwo = v.findViewById(R.id.textTwo);
            textThree = v.findViewById(R.id.textThree);
            textFour = v.findViewById(R.id.textFour);

            cardSelectorBlock = v.findViewById(R.id.cardSelectorBlock);

            btnDelete = v.findViewById(R.id.btnDelete);
            btnClear = v.findViewById(R.id.btnClear);
        }
    }
}
