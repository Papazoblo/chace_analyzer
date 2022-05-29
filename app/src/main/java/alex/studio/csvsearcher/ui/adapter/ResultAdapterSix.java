package alex.studio.csvsearcher.ui.adapter;

import static alex.studio.csvsearcher.utils.ViewUtils.isVisible;
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
import alex.studio.csvsearcher.dto.CardSetSix;
import alex.studio.csvsearcher.ui.custom_view.CustomLinearLayoutManager;

public class ResultAdapterSix extends RecyclerView.Adapter<ResultAdapterSix.ResultViewHolder> {

    private List<CardSetSix> listGroup = new ArrayList<>();
    private Context context;

    public ResultAdapterSix(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ResultViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ResultViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.item_result_style_6, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ResultViewHolder h, int pos) {

        CardSetSix cardSet = listGroup.get(pos);

        h.textCard1.setText(cardSet.getCard1());
        h.textCard2.setText(cardSet.getCard2());
        h.textCard3.setText(cardSet.getCard3());
        h.textCard4.setText(cardSet.getCard4());

        h.btnOpenNumbers.setOnClickListener(view -> {
            if (isVisible(h.recyclerNumbers)) {
                toGone(h.recyclerNumbers);
                cardSet.setVisible(false);
            } else {
                toVisible(h.recyclerNumbers);
                cardSet.setVisible(true);
            }
        });

        //количество розыгрышей
        h.number.setText(String.valueOf(cardSet.getCount()));

        if (cardSet.isVisible()) {
            toVisible(h.recyclerNumbers);
        } else {
            toGone(h.recyclerNumbers);
        }

        ResultAdapterInnerSix adapter = new ResultAdapterInnerSix(context);
        h.recyclerNumbers.setAdapter(adapter);
        adapter.setData(new ArrayList<>(cardSet.getGamesNumber()));
        toGone(h.secondBlockSelectCard);
    }

    @Override
    public int getItemCount() {
        return listGroup.size();
    }

    public void clear() {
        setData(new ArrayList<>());
    }

    public void setData(List<CardSetSix> data) {
        this.listGroup = data;
        notifyDataSetChanged();
    }

    public class ResultViewHolder extends RecyclerView.ViewHolder {

        private TextView textCard1;
        private TextView textCard2;
        private TextView textCard3;
        private TextView textCard4;
        private TextView number;
        private View btnOpenNumbers;
        private RecyclerView recyclerNumbers;
        private View secondBlockSelectCard;

        public ResultViewHolder(@NonNull View v) {
            super(v);

            textCard1 = v.findViewById(R.id.textCard1);
            textCard2 = v.findViewById(R.id.textCard2);
            textCard3 = v.findViewById(R.id.textCard3);
            textCard4 = v.findViewById(R.id.textCard4);
            btnOpenNumbers = v.findViewById(R.id.btnOpenNumbers);
            number = v.findViewById(R.id.number);
            secondBlockSelectCard = v.findViewById(R.id.secondSelectCardBlock);
            recyclerNumbers = v.findViewById(R.id.recyclerNumbers);
            recyclerNumbers.setLayoutManager(new CustomLinearLayoutManager(context) {
                @Override
                public boolean canScrollVertically() {
                    return false;
                }
            });
        }
    }
}
