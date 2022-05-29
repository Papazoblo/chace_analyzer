package alex.studio.csvsearcher.ui.main_activity;

import android.content.Context;

import java.util.List;
import java.util.Map;

import alex.studio.csvsearcher.dto.CardGroup;
import alex.studio.csvsearcher.dto.CardMatch;
import alex.studio.csvsearcher.dto.CardSet;
import alex.studio.csvsearcher.dto.CardSetSix;
import alex.studio.csvsearcher.dto.ColorMatch;
import alex.studio.csvsearcher.enums.Direction;
import alex.studio.csvsearcher.functions.Consumer;

public interface Main {

    interface View {

        Context getContext();

        void clearData();

        Direction getDirection();

        String[] getCards();

        List<String[]> getCardsList();

        String[] getActiveDate();

        Integer getActiveCountGames();

        void setCardGroupListToRecycler(List<CardGroup> data);

        void setCardMatchListToRecycler(List<CardMatch> data);

        void setMapToRecycler(Map<String, Integer> data);

        void setCardSetListToRecycler(List<CardSet> data);

        void setCardSetSixListToRecycler(List<CardSetSix> data);

        void setRecyclerResultData(List<Map<String, ColorMatch>> data, List<List<CardSet>> list);

        void changeVisibleBlockWait(boolean changeVisible);

        boolean isCardValid();

        boolean isOrderSet();
    }

    interface Presenter {

        void setView(Main.View view);

        void launchSearch();

        void initReadFile(Consumer<List<CardSet>> afterEnd);

        void initializationData(Consumer<List<CardSet>> action);

        void launchAlgorithm();

        List<CardSet> getCards();
    }
}
