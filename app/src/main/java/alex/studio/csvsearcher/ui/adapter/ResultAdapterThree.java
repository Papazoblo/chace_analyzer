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
import alex.studio.csvsearcher.dto.CardGroup;
import alex.studio.csvsearcher.dto.CardSet;

public class ResultAdapterThree extends RecyclerView.Adapter<ResultAdapterThree.ResultViewHolder> {

    private List<CardGroup> listGroup = new ArrayList<>();
    private Context context;

    public ResultAdapterThree(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ResultViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ResultViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.item_result_style_3, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ResultViewHolder h, int pos) {

        CardGroup group = listGroup.get(pos);

        CardSet top = group.getCardSetTop();
        CardSet bottom = group.getCardSetBottom();

        h.textCard1.setText(top == null ? "-" : top.getCard1());
        h.textCard2.setText(top == null ? "-" : top.getCard2());
        h.textCard3.setText(top == null ? "-" : top.getCard3());
        h.textCard4.setText(top == null ? "-" : top.getCard4());
        h.number.setText(top == null ? "-" : top.getNumber());
        h.year.setText(top == null ? "-" : String.valueOf(top.getYear()));

        h.textCard21.setText(bottom == null ? "-" : bottom.getCard1());
        h.textCard22.setText(bottom == null ? "-" : bottom.getCard2());
        h.textCard23.setText(bottom == null ? "-" : bottom.getCard3());
        h.textCard24.setText(bottom == null ? "-" : bottom.getCard4());
        h.number2.setText(bottom == null ? "-" : bottom.getNumber());
        h.year2.setText(bottom == null ? "-" : String.valueOf(bottom.getYear()));

        changeTextColor(pos % 2 == 0, h.textCard1, h.textCard2, h.textCard3, h.textCard4,
                h.textCard21, h.textCard22, h.textCard23, h.textCard24);
    }

    private void changeTextColor(boolean flag, TextView... views) {

        int color = context.getResources().getColor(flag ? R.color.yellow : R.color.white);

        for (TextView view : views) {
            view.setTextColor(color);
        }
    }

    @Override
    public int getItemCount() {
        return listGroup.size();
    }

    public void clear() {
        setData(new ArrayList<>());
    }

    public void setData(List<CardGroup> data) {
        this.listGroup = data;
        notifyDataSetChanged();
    }

    public class ResultViewHolder extends RecyclerView.ViewHolder {

        private TextView number;
        private TextView year;
        private TextView number2;
        private TextView year2;
        private TextView textCard1;
        private TextView textCard2;
        private TextView textCard3;
        private TextView textCard4;
        private TextView textCard21;
        private TextView textCard22;
        private TextView textCard23;
        private TextView textCard24;

        public ResultViewHolder(@NonNull View v) {
            super(v);

            number = v.findViewById(R.id.number);
            number2 = v.findViewById(R.id.number2);
            year = v.findViewById(R.id.year);
            year2 = v.findViewById(R.id.year2);
            textCard1 = v.findViewById(R.id.textCard1);
            textCard2 = v.findViewById(R.id.textCard2);
            textCard3 = v.findViewById(R.id.textCard3);
            textCard4 = v.findViewById(R.id.textCard4);
            textCard21 = v.findViewById(R.id.textCard21);
            textCard22 = v.findViewById(R.id.textCard22);
            textCard23 = v.findViewById(R.id.textCard23);
            textCard24 = v.findViewById(R.id.textCard24);
        }
    }
}
