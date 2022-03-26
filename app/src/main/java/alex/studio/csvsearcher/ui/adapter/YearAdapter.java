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

public class YearAdapter extends RecyclerView.Adapter<YearAdapter.ResultViewHolder> {

    private List<YearDto> listYear = new ArrayList<>();
    private Context context;

    private boolean multiselect;
    private int colorYellow;
    private int colorWhite;

    public YearAdapter(Context context) {
        this(context, true);
    }

    public YearAdapter(Context context, boolean multiselect) {
        this.context = context;
        this.colorYellow = context.getResources().getColor(R.color.yellow);
        this.colorWhite = context.getResources().getColor(R.color.white);
        this.multiselect = multiselect;
    }

    @NonNull
    @Override
    public ResultViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ResultViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.item_recycler_year, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ResultViewHolder h, int pos) {

        YearDto yearDto = listYear.get(pos);

        h.textYear.setText(yearDto.getYear());
        changeColor(h.textYear, yearDto.isMark());

        h.textYear.setOnClickListener(v -> {
            if (!multiselect) {
                clearAll();
            }
            yearDto.changeMark();
            changeColor(h.textYear, yearDto.isMark());
        });
    }

    public int selectYear(int yearNum) {
        clearAll();
        int i = 0;
        for (YearDto year : listYear) {
            if (year.getYear().equals(String.valueOf(yearNum))) {
                year.setMark(true);
                break;
            }
            i++;
        }
        notifyDataSetChanged();
        return i;
    }

    private void changeColor(TextView textView, boolean flag) {
        if (flag) {
            textView.setTextColor(colorYellow);
        } else {
            textView.setTextColor(colorWhite);
        }
    }

    @Override
    public int getItemCount() {
        return listYear.size();
    }

    public void initData() {
        listYear.clear();

        Calendar dateAndTime = Calendar.getInstance();
        int year = dateAndTime.get(Calendar.YEAR);

        for (int i = 1994; i <= year; i++) {
            listYear.add(new YearDto(i));
        }
        notifyDataSetChanged();
    }

    public void clearAll() {
        changeStateAll(false);
    }

    public void selectAll() {
        changeStateAll(true);
    }

    private void changeStateAll(boolean state) {
        for (YearDto year : listYear) {
            year.setMark(state);
        }
        notifyDataSetChanged();
    }

    public int getSelectedCount() {
        int count = 0;
        for (YearDto year : listYear) {
            if (year.isMark()) {
                count++;
            }
        }
        return count;
    }

    public String getSelectYears() {
        String result = "";

        for (YearDto year : listYear) {
            if (year.isMark()) {
                if (!result.isEmpty()) {
                    result += ",";
                }
                result += year.getYear();
            }
        }

        return result.length() <= 4 ? result : "[" + result + "]";
    }

    public List<Integer> getArraySelectedYears() {
        List<Integer> years = new ArrayList<>();
        for (YearDto year : listYear) {
            if (year.isMark()) {
                years.add(Integer.valueOf(year.getYear()));
            }
        }
        return years;
    }

    public class ResultViewHolder extends RecyclerView.ViewHolder {

        private TextView textYear;

        public ResultViewHolder(@NonNull View v) {
            super(v);

            textYear = v.findViewById(R.id.textYear);
        }
    }
}
