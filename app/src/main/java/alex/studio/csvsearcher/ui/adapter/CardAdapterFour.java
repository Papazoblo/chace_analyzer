package alex.studio.csvsearcher.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import alex.studio.csvsearcher.R;

public class CardAdapterFour extends RecyclerView.Adapter<CardAdapterFour.ResultViewHolder> {

    private final int MAX_ROW_COUNT = 7;

    private List<String[]> listGroupCards = new ArrayList<>();
    private String[] cardArray;
    private Context context;
    private int colorYellow;
    private int colorLightGray = 0;

    public CardAdapterFour(Context context) {
        this.context = context;
        this.colorYellow = context.getResources().getColor(R.color.yellow);
        this.colorLightGray = context.getResources().getColor(R.color.textLightGrayBlue);
        this.cardArray = context.getResources().getStringArray(R.array.cards);
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

        h.spinnerOne.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
                cards[0] = cardArray[i];
                changeSpinnerColor((TextView) parent.getChildAt(0), i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        h.spinnerTwo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
                cards[1] = cardArray[i];
                changeSpinnerColor((TextView) parent.getChildAt(0), i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        h.spinnerThree.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
                cards[2] = cardArray[i];
                changeSpinnerColor((TextView) parent.getChildAt(0), i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        h.spinnerFour.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
                cards[3] = cardArray[i];
                changeSpinnerColor((TextView) parent.getChildAt(0), i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        h.spinnerOne.setSelection(getPosArrayByChar(cards[0]));
        h.spinnerTwo.setSelection(getPosArrayByChar(cards[1]));
        h.spinnerThree.setSelection(getPosArrayByChar(cards[2]));
        h.spinnerFour.setSelection(getPosArrayByChar(cards[3]));

        h.btnDelete.setOnClickListener(v -> deleteRow(pos));
        h.btnClear.setOnClickListener(v -> clearRow(pos));
    }

    private int getPosArrayByChar(String s) {
        for (int i = 0; i < cardArray.length; i++) {
            if (cardArray[i].equals(s)) {
                return i;
            }
        }
        return 0;
    }

    private void changeSpinnerColor(TextView currentText, int position) {
        if (currentText == null) {
            return;
        }
        if (position != 0) {
            currentText.setTextColor(colorYellow);
        } else {
            currentText.setTextColor(colorLightGray);
        }
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
            }
            listGroupCards.add(new String[]{"-", "-", "-", "-"});
            notifyDataSetChanged();
        } else {
            Toast.makeText(context, context.getResources().getString(R.string.error_max_limit_rows),
                    Toast.LENGTH_LONG).show();
        }
    }

    public boolean isHasCardInRow() {
        String[] pattern = listGroupCards.get(0);

        boolean flag = false;
        for(String card : pattern) {
            if(!card.equals("-")) {
                return true;
            }
        }
        return false;
    }

    public boolean isSelectedValid() {
        String[] pattern = listGroupCards.get(0);

        for (int i = 1; i < listGroupCards.size(); i++) {
            for(int j = 0 ; j < pattern.length ; j++) {
                if(pattern[j].equals("-") && !listGroupCards.get(i)[j].equals("-") ||
                        !pattern[j].equals("-") && listGroupCards.get(i)[j].equals("-")) {
                    return false;
                }
            }
        }
        return true;
    }

    private void deleteRow(int pos) {
        listGroupCards.remove(pos);
        notifyDataSetChanged();
    }

    private void clearRow(int pos) {
        for(int i = 0 ; i < 4 ; i++) {
            listGroupCards.get(pos)[i] = "-";
        }
        notifyDataSetChanged();
    }

    public class ResultViewHolder extends RecyclerView.ViewHolder {

        private Spinner spinnerOne;
        private Spinner spinnerTwo;
        private Spinner spinnerThree;
        private Spinner spinnerFour;

        private View btnDelete;
        private View btnClear;

        public ResultViewHolder(@NonNull View v) {
            super(v);

            spinnerOne = v.findViewById(R.id.spinnerOne);
            spinnerTwo = v.findViewById(R.id.spinnerTwo);
            spinnerThree = v.findViewById(R.id.spinnerThree);
            spinnerFour = v.findViewById(R.id.spinnerFour);

            btnDelete = v.findViewById(R.id.btnDelete);
            btnClear = v.findViewById(R.id.btnClear);

            ArrayAdapter<String> adapter = new ArrayAdapter<>(context,
                    R.layout.item_spinner_style, cardArray);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            spinnerOne.setAdapter(adapter);
            spinnerTwo.setAdapter(adapter);
            spinnerThree.setAdapter(adapter);
            spinnerFour.setAdapter(adapter);
        }
    }
}
