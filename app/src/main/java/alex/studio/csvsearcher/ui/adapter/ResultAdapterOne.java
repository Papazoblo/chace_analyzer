package alex.studio.csvsearcher.ui.adapter;

import static alex.studio.csvsearcher.utils.ViewUtils.toGone;
import static alex.studio.csvsearcher.utils.ViewUtils.toVisible;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import alex.studio.csvsearcher.R;
import alex.studio.csvsearcher.dto.CardMatch;
import alex.studio.csvsearcher.dto.CardSet;
import alex.studio.csvsearcher.enums.TypeMatch;

public class ResultAdapterOne extends RecyclerView.Adapter<ResultAdapterOne.ResultViewHolder> {

    private List<CardMatch> cardSetList = new ArrayList<>();
    private Context context;
    private int colorOriginal;
    private int colorRandom;
    private int colorGray;
    private String[] months;
    private boolean blockNext;

    public ResultAdapterOne(Context context) {
        this.context = context;
        colorGray = context.getResources().getColor(R.color.lightGrayBlue);
        colorOriginal = context.getResources().getColor(R.color.original);
        colorRandom = context.getResources().getColor(R.color.random);
        months = context.getResources().getStringArray(R.array.months);
    }

    @NonNull
    @Override
    public ResultViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ResultViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.item_result_style_1, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ResultViewHolder h, int pos) {

        CardMatch match = cardSetList.get(pos);
        if (pos == 0) {
            h.monthName.setText(months[match.getMonth()]);
            toVisible(h.monthName);
        } else {
            CardMatch prevMatch = cardSetList.get(pos - 1);
            if ((match.getYear() != prevMatch.getYear()) ||
                    (match.getMonth() != prevMatch.getMonth())) {
                h.monthName.setText(months[match.getMonth()]);
                toVisible(h.monthName);
            } else {
                toGone(h.monthName);
            }
        }

        CardSet prevSet = blockNext ? match.getNextSet() : match.getPrevSet();

        h.textYear.setText(String.valueOf(match.getYear()));
        h.textNumber.setText(prevSet.getNumber());
        h.textCard4.setText(prevSet.getCardByPos(match.getMatcherPosition().getPositions()[0]));
        h.textCard4.setTextColor(getColor(match.getMatcherPosition().getMatched()[0], match));

        h.textCard3.setText(prevSet.getCardByPos(match.getMatcherPosition().getPositions()[1]));
        h.textCard3.setTextColor(getColor(match.getMatcherPosition().getMatched()[1], match));

        h.textCard2.setText(prevSet.getCardByPos(match.getMatcherPosition().getPositions()[2]));
        h.textCard2.setTextColor(getColor(match.getMatcherPosition().getMatched()[2], match));

        h.textCard1.setText(prevSet.getCardByPos(match.getMatcherPosition().getPositions()[3]));
        h.textCard1.setTextColor(getColor(match.getMatcherPosition().getMatched()[3], match));
    }

    @Override
    public int getItemCount() {
        return cardSetList.size();
    }

    public void setData(List<CardMatch> cardSets, boolean blockNext) {
        List<CardMatch> res = new ArrayList<>();
        for (CardMatch item : cardSets) {
            if ((blockNext && item.getNextSet() != null) ||
                    (!blockNext && item.getPrevSet() != null)) {
                res.add(item);
            }
        }
        this.cardSetList = res;
        this.blockNext = blockNext;
        notifyDataSetChanged();
    }

    private int getColor(boolean flag, CardMatch match) {
        int color;
        if (match.getType() == TypeMatch.ANY) {
            color = colorRandom;
        } else {
            color = colorOriginal;
        }
        return flag ? color : colorGray;
    }

    public static class ResultViewHolder extends RecyclerView.ViewHolder {

        private TextView textYear;
        private TextView textCard1;
        private TextView textCard2;
        private TextView textCard3;
        private TextView textCard4;
        private TextView textNumber;
        private TextView monthName;

        public ResultViewHolder(@NonNull View v) {
            super(v);

            textYear = v.findViewById(R.id.year);
            textCard1 = v.findViewById(R.id.textCard1);
            textCard2 = v.findViewById(R.id.textCard2);
            textCard3 = v.findViewById(R.id.textCard3);
            textCard4 = v.findViewById(R.id.textCard4);
            textNumber = v.findViewById(R.id.number);
            monthName = v.findViewById(R.id.monthName);
        }
    }
}
