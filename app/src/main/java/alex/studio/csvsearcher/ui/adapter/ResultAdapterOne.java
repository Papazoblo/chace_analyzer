package alex.studio.csvsearcher.ui.adapter;

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

public class ResultAdapterOne extends RecyclerView.Adapter<ResultAdapterOne.ResultViewHolder> {

    private List<CardMatch> cardSetList = new ArrayList<>();
    private Context context;
    private int colorWhite;
    private int colorGray;

    public ResultAdapterOne(Context context) {
        this.context = context;
        colorWhite = context.getResources().getColor(R.color.white);
        colorGray = context.getResources().getColor(R.color.lightGrayBlue);
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
        h.textYear.setText(String.valueOf(match.getYear()));
        h.textNumber.setText(match.getPrevSet().getNumber());
        h.textCard4.setText(match.getPrevSet().getCardByPos(match.getMatcherPosition().getPositions()[0]));
        h.textCard4.setTextColor(getColor(match.getMatcherPosition().getMatched()[0]));

        h.textCard3.setText(match.getPrevSet().getCardByPos(match.getMatcherPosition().getPositions()[1]));
        h.textCard3.setTextColor(getColor(match.getMatcherPosition().getMatched()[1]));

        h.textCard2.setText(match.getPrevSet().getCardByPos(match.getMatcherPosition().getPositions()[2]));
        h.textCard2.setTextColor(getColor(match.getMatcherPosition().getMatched()[2]));

        h.textCard1.setText(match.getPrevSet().getCardByPos(match.getMatcherPosition().getPositions()[3]));
        h.textCard1.setTextColor(getColor(match.getMatcherPosition().getMatched()[3]));
    }

    @Override
    public int getItemCount() {
        return cardSetList.size();
    }

    public void setData(List<CardMatch> cardSets) {
        this.cardSetList = cardSets;
        notifyDataSetChanged();
    }

    private int getColor(boolean flag) {
        return flag ? colorWhite : colorGray;
    }

    public static class ResultViewHolder extends RecyclerView.ViewHolder {

        private TextView textYear;
        private TextView textCard1;
        private TextView textCard2;
        private TextView textCard3;
        private TextView textCard4;
        private TextView textNumber;

        public ResultViewHolder(@NonNull View v) {
            super(v);

            textYear = v.findViewById(R.id.year);
            textCard1 = v.findViewById(R.id.textCard1);
            textCard2 = v.findViewById(R.id.textCard2);
            textCard3 = v.findViewById(R.id.textCard3);
            textCard4 = v.findViewById(R.id.textCard4);
            textNumber = v.findViewById(R.id.number);
        }
    }
}
