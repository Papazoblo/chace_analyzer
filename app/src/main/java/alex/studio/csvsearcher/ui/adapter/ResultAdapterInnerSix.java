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

public class ResultAdapterInnerSix extends RecyclerView.Adapter<ResultAdapterInnerSix.ResultViewHolder> {

    private List<Long> cardSetList = new ArrayList<>();
    private Context context;

    public ResultAdapterInnerSix(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ResultViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ResultViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.item_result_style_6_inner, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ResultViewHolder h, int pos) {
        h.number.setText(String.valueOf(cardSetList.get(pos)));
    }

    @Override
    public int getItemCount() {
        return cardSetList.size();
    }

    public void setData(List<Long> selectedNumbers) {
        this.cardSetList = selectedNumbers;
        notifyDataSetChanged();
    }

    public class ResultViewHolder extends RecyclerView.ViewHolder {

        private TextView number;

        public ResultViewHolder(@NonNull View v) {
            super(v);

            number = v.findViewById(R.id.number);
        }
    }
}
