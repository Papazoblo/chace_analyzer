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
import java.util.Map;

import alex.studio.csvsearcher.R;
import alex.studio.csvsearcher.dto.CardSet;
import alex.studio.csvsearcher.dto.ColorMatch;
import alex.studio.csvsearcher.ui.custom_view.CustomLinearLayoutManager;

public class ResultAdapterFive extends RecyclerView.Adapter<ResultAdapterFive.ResultViewHolder> {

    private List<List<CardSet>> cardSetList = new ArrayList<>();
    private List<Map<String, ColorMatch>> colorMatchMap = new ArrayList<>();
    private Context context;
    private int colorWhite;

    public ResultAdapterFive(Context context) {
        this.context = context;
        this.colorWhite = context.getResources().getColor(R.color.white);
    }

    @NonNull
    @Override
    public ResultViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ResultViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.item_result_style_5, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ResultViewHolder h, int pos) {

        int newPos = cardSetList.size() - pos - 1;

        ResultAdapterInnerThree adapter = new ResultAdapterInnerThree(context);
        h.recyclerView.setAdapter(adapter);
        adapter.setData(colorMatchMap.get(newPos), cardSetList.get(newPos));

        if (!cardSetList.get(newPos).isEmpty()) {
            h.textDate.setText(cardSetList.get(newPos).get(0).getDateString());
        }

    }

    @Override
    public int getItemCount() {
        return cardSetList.size();
    }

    public void setData(List<Map<String, ColorMatch>> data, List<List<CardSet>> cardSets) {
        this.colorMatchMap = data;
        this.cardSetList = cardSets;
        notifyDataSetChanged();
    }

    public class ResultViewHolder extends RecyclerView.ViewHolder {

        private TextView textDate;
        private RecyclerView recyclerView;

        public ResultViewHolder(@NonNull View v) {
            super(v);

            textDate = v.findViewById(R.id.textDate);
            recyclerView = v.findViewById(R.id.recyclerView);
            CustomLinearLayoutManager linearLayoutManager = new CustomLinearLayoutManager(context) {
                @Override
                public boolean canScrollVertically() {
                    return false;
                }
            };
            recyclerView.setLayoutManager(linearLayoutManager);
        }
    }
}
