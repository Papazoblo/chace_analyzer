package alex.studio.csvsearcher.ui.main_activity;

import android.content.Context;

import java.util.List;
import java.util.Map;

import alex.studio.csvsearcher.dto.CardGroup;
import alex.studio.csvsearcher.dto.CardSet;
import alex.studio.csvsearcher.dto.ColorMatch;

public interface Main {

    interface View {

        Context getContext();

        void clearData();

        String getDirection();
        String[] getCards();
        List<String[]> getCardsList();
        String[] getActiveDate();
        Integer getActiveCountGames();

        void setRecyclerData(List<CardGroup> data);
        void setRecyclerData(Map<String, Integer> data);
        void setRecyclerResultData(List<CardSet> data);
        void setRecyclerResultData(List<Map<String, ColorMatch>> data, List<List<CardSet>> list);
        void changeVisibleBlockWait(boolean changeVisible);
        boolean isCardValid();
        boolean isOrderSet();
    }

    interface Presenter {

        void setView(Main.View view);
        void launchSearch();
    }
}
