package alex.studio.csvsearcher.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import alex.studio.csvsearcher.R;
import alex.studio.csvsearcher.dto.CardSet;
import alex.studio.csvsearcher.dto.ColorMatch;

public class ResultAdapterInnerThree extends RecyclerView.Adapter<ResultAdapterInnerThree.ResultViewHolder> {

    private List<CardSet> cardSetList = new ArrayList<>();
    private Map<String, ColorMatch> colorMatchMap = new HashMap<>();
    private Context context;
    private int colorWhite;

    public ResultAdapterInnerThree(Context context) {
        this.context = context;
        this.colorWhite = context.getResources().getColor(R.color.white);
    }

    @NonNull
    @Override
    public ResultViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ResultViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.item_result_style_6, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ResultViewHolder h, int pos) {

        ColorMatch match1 = colorMatchMap.get(pos + "," + 0);
        ColorMatch match2 = colorMatchMap.get(pos + "," + 1);
        ColorMatch match3 = colorMatchMap.get(pos + "," + 2);
        ColorMatch match4 = colorMatchMap.get(pos + "," + 3);

        CardSet set = cardSetList.get(pos);

        h.textCard1.setText(set.getCard1());
        h.textCard2.setText(set.getCard2());
        h.textCard3.setText(set.getCard3());
        h.textCard4.setText(set.getCard4());

        h.textCard1.setTextColor(match1 == null ? colorWhite : match1.getColor());
        h.textCard2.setTextColor(match2 == null ? colorWhite : match2.getColor());
        h.textCard3.setTextColor(match3 == null ? colorWhite : match3.getColor());
        h.textCard4.setTextColor(match4 == null ? colorWhite : match4.getColor());
    }

    @Override
    public int getItemCount() {
        return cardSetList.size();
    }

    public void setData(Map<String, ColorMatch> data, List<CardSet> cardSets) {
        this.colorMatchMap = data;
        this.cardSetList = cardSets;
        notifyDataSetChanged();
    }

    public class ResultViewHolder extends RecyclerView.ViewHolder {

        private TextView textCard1;
        private TextView textCard2;
        private TextView textCard3;
        private TextView textCard4;

        public ResultViewHolder(@NonNull View v) {
            super(v);

            textCard1 = v.findViewById(R.id.textCard1);
            textCard2 = v.findViewById(R.id.textCard2);
            textCard3 = v.findViewById(R.id.textCard3);
            textCard4 = v.findViewById(R.id.textCard4);
        }
    }
}
