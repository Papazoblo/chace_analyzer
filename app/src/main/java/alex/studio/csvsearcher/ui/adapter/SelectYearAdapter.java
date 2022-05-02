package alex.studio.csvsearcher.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import alex.studio.csvsearcher.R;
import alex.studio.csvsearcher.dto.YearDto;

public class SelectYearAdapter extends RecyclerView.Adapter<SelectYearAdapter.ResultViewHolder> {

    private List<List<YearDto>> listYear = new ArrayList<>();
    private Context context;

    private boolean multiselect;
    private int colorGray;
    private int colorWhite;
    private int bgCircle;

    public SelectYearAdapter(Context context) {
        this(context, true);
    }

    public SelectYearAdapter(Context context, boolean multiselect) {
        this.context = context;
        this.colorGray = context.getResources().getColor(R.color.gray);
        this.colorWhite = context.getResources().getColor(R.color.white);
        this.bgCircle = R.drawable.circle_select;
        this.multiselect = multiselect;
    }

    @NonNull
    @Override
    public ResultViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ResultViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.item_recycler_year_new, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ResultViewHolder h, int pos) {

        List<YearDto> yearRow = listYear.get(pos);

        for (int i = 0; i < yearRow.size(); i++) {
            YearDto year = yearRow.get(i);
            TextView textView = h.main.findViewWithTag(String.valueOf(i + 1));
            textView.setText(year.getYear());
            changeColor(textView, year.isMark());
            textView.setOnClickListener(v -> {
                year.changeMark();
                changeColor(((TextView) v), year.isMark());
            });
        }
    }

    @Override
    public int getItemCount() {
        return listYear.size();
    }

    public int getAllItemCount() {
        int count = 0;
        for (List<YearDto> list : listYear) {
            count += list.size();
        }
        return count;
    }

    public void initData() {
        listYear.clear();

        Calendar dateAndTime = Calendar.getInstance();
        int year = dateAndTime.get(Calendar.YEAR);

        for (int i = 1994; i <= year; i += 7) {
            List<YearDto> yearRow = new ArrayList<>();
            for (int j = 0; j < 7; j++) {
                if (i + j > year) {
                    break;
                }
                yearRow.add(new YearDto(i + j));
            }
            listYear.add(yearRow);
        }
        notifyDataSetChanged();
    }

    private void changeColor(TextView textView, boolean flag) {
        if (flag) {
            textView.setTextColor(colorWhite);
            textView.setBackgroundResource(bgCircle);
        } else {
            textView.setTextColor(colorGray);
            textView.setBackground(null);
        }
    }

    public int selectYear(int yearNum) {
        clearAll();
        int i = 0;
        for (List<YearDto> yearRow : listYear) {
            for (YearDto year : yearRow) {
                if (year.getYear().equals(String.valueOf(yearNum))) {
                    year.setMark(true);
                    break;
                }
                i++;
            }
        }
        notifyDataSetChanged();
        return i;
    }

    public void clearAll() {
        changeStateAll(false);
    }

    public void selectAll() {
        changeStateAll(true);
    }

    private void changeStateAll(boolean state) {
        for (List<YearDto> yearRow : listYear) {
            for (YearDto year : yearRow) {
                year.setMark(state);
            }
        }
        notifyDataSetChanged();
    }

    public List<Integer> getArraySelectedYears() {
        List<Integer> years = new ArrayList<>();
        for (List<YearDto> yearRow : listYear) {
            for (YearDto year : yearRow) {
                if (year.isMark()) {
                    years.add(Integer.valueOf(year.getYear()));
                }
            }
        }
        return years;
    }

    public static class ResultViewHolder extends RecyclerView.ViewHolder {

        private TextView yearOne;
        private TextView yearTwo;
        private TextView yearThree;
        private TextView yearFour;
        private TextView yearFive;
        private TextView yearSix;
        private TextView yearSeven;
        private View main;

        public ResultViewHolder(@NonNull View v) {
            super(v);

            main = v.findViewById(R.id.main);
            yearOne = v.findViewById(R.id.yearOne);
            yearTwo = v.findViewById(R.id.yearTwo);
            yearThree = v.findViewById(R.id.yearThree);
            yearFour = v.findViewById(R.id.yearFour);
            yearFive = v.findViewById(R.id.yearFive);
            yearSix = v.findViewById(R.id.yearSix);
            yearSeven = v.findViewById(R.id.yearSeven);
        }
    }
}
