package alex.studio.csvsearcher.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import alex.studio.csvsearcher.R;
import alex.studio.csvsearcher.dto.CardGroup;
import alex.studio.csvsearcher.dto.CardSet;
import alex.studio.csvsearcher.dto.CountCard;

public class ResultAdapterTwo extends RecyclerView.Adapter<ResultAdapterTwo.ResultViewHolder> {

    private List<CountCard> listGroup = new ArrayList<>();
    private Context context;
    private int maxVal = 0;
    private int colorYellow;
    private int colorWhite;

    public ResultAdapterTwo(Context context) {
        this.context = context;
        this.colorYellow = context.getResources().getColor(R.color.yellow);
        this.colorWhite = context.getResources().getColor(R.color.white);
    }

    @NonNull
    @Override
    public ResultViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ResultViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.item_result_style_2, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ResultViewHolder h, int pos) {

        CountCard card = listGroup.get(pos);

        h.textName.setText(card.getName());
        h.textCount.setText(card.getCount() + "");

        if (card.getCount() == maxVal) {
            changeTextColor(colorYellow, h.textCount, h.textRovno, h.textName);
        } else {
            changeTextColor(colorWhite, h.textCount, h.textRovno, h.textName);
        }
    }

    private void changeTextColor(int color, TextView... views) {

        for (TextView view : views) {
            view.setTextColor(color);
        }
    }

    @Override
    public int getItemCount() {
        return listGroup.size();
    }

    public void clear() {
        setData(new HashMap<>());
    }

    public void setData(Map<String, Integer> data) {
        this.listGroup.clear();

        if(!data.isEmpty()) {
            List<Integer> list = new ArrayList<>();
            for (int i = 7; i < 15; i++) {
                String key = i + "";
                switch (key) {
                    case "11":
                        key = "J";
                        break;
                    case "12":
                        key = "Q";
                        break;
                    case "13":
                        key = "K";
                        break;
                    case "14":
                        key = "A";
                        break;
                }

                list.add(data.get(key));
                listGroup.add(new CountCard(key, data.get(key)));
            }

            maxVal = Collections.max(list);
        }
        notifyDataSetChanged();
    }

    public class ResultViewHolder extends RecyclerView.ViewHolder {

        private TextView textName;
        private TextView textCount;
        private TextView textRovno;

        public ResultViewHolder(@NonNull View v) {
            super(v);

            textRovno = v.findViewById(R.id.textRovno);
            textName = v.findViewById(R.id.textName);
            textCount = v.findViewById(R.id.textCount);
        }
    }
}
