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
import alex.studio.csvsearcher.dto.CardSet;

public class ResultAdapterTwo extends RecyclerView.Adapter<ResultAdapterTwo.ResultViewHolder> {

    private List<CardSet> cardSetList = new ArrayList<>();
    private Context context;
    private int colorWhite;
    private int colorGray;

    public ResultAdapterTwo(Context context) {
        this.context = context;
        colorWhite = context.getResources().getColor(R.color.white);
        colorGray = context.getResources().getColor(R.color.lightGrayBlue);
    }

    @NonNull
    @Override
    public ResultViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ResultViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.item_result_style_2, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ResultViewHolder h, int pos) {

        CardSet set = cardSetList.get(pos);
        if (set == null) {
            h.textCard1.setText("");
            h.textCard2.setText("");
            h.textCard3.setText("");
            h.textCard4.setText("");
            h.textName.setText("");
            return;
        }
        h.textCard4.setText(set.getCard4());
        h.textCard4.setTextColor(set.getCard4().equals("-") ? colorGray : colorWhite);

        h.textCard3.setText(set.getCard3());
        h.textCard3.setTextColor(set.getCard3().equals("-") ? colorGray : colorWhite);

        h.textCard2.setText(set.getCard2());
        h.textCard2.setTextColor(set.getCard2().equals("-") ? colorGray : colorWhite);

        h.textCard1.setText(set.getCard1());
        h.textCard1.setTextColor(set.getCard1().equals("-") ? colorGray : colorWhite);

        h.textName.setText(set.getNumber());
    }

    @Override
    public int getItemCount() {
        return cardSetList.size();
    }

    public void setData(List<CardSet> cardSets) {
        if (cardSets.size() == 4) {
            cardSets.add(2, null);
        }
        this.cardSetList = cardSets;
        notifyDataSetChanged();
    }

    public static class ResultViewHolder extends RecyclerView.ViewHolder {

        private TextView textCard1;
        private TextView textCard2;
        private TextView textCard3;
        private TextView textCard4;
        private TextView textName;

        public ResultViewHolder(@NonNull View v) {
            super(v);

            textCard1 = v.findViewById(R.id.textCard1);
            textCard2 = v.findViewById(R.id.textCard2);
            textCard3 = v.findViewById(R.id.textCard3);
            textCard4 = v.findViewById(R.id.textCard4);
            textName = v.findViewById(R.id.name);
        }
    }
}
