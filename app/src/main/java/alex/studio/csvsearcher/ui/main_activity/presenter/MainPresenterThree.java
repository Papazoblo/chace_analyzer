package alex.studio.csvsearcher.ui.main_activity.presenter;

import static alex.studio.csvsearcher.utils.DateUtils.toDate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import alex.studio.csvsearcher.R;
import alex.studio.csvsearcher.dto.CardSet;
import alex.studio.csvsearcher.dto.ColorMatch;
import alex.studio.csvsearcher.functions.Consumer;
import alex.studio.csvsearcher.ui.main_activity.view.MainActivityThree;

public class MainPresenterThree extends MainPresenter {

    private List<Map<String, ColorMatch>> result;
    private List<List<CardSet>> workList;

    @Override
    public void initializationData(Consumer<CardSet> action) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void launchAlgorithm() {

        String[] selectCards = view.getCards();
        String[] dates = view.getActiveDate();

        List<CardSet> workListCard = getWorkList(1);

        workList = createWorkList(workListCard, dates);
        result = new ArrayList<>();

        for (List<CardSet> cardSetList : workList) {
            Map<String, ColorMatch> mapResult = new HashMap<>();

            searchLeftDirection(cardSetList, selectCards, mapResult);
            searchTopDirection(cardSetList, selectCards, mapResult);
            searchTopRightDirection(cardSetList, selectCards, mapResult);
            searchTopLeftDirection(cardSetList, selectCards, mapResult);

            result.add(mapResult);
        }

        for(int i = workList.size() - 1 ; i >= 0 ; i--) {
            if(workList.get(i).isEmpty()) {
                workList.remove(i);
                result.remove(i);
                i--;
            }
        }

        outputData(workList);
    }

    private void searchLeftDirection(List<CardSet> list, String[] selectCards,
                                     Map<String, ColorMatch> mapResult) {

        int maxLength = selectCards.length;
        boolean order = view.isOrderSet();

        for (int i = 0; i < list.size(); i++) {
            CardSet cardSet = list.get(i);

            for(int j = 0 ; j + maxLength <= 4 ; j++) {

                String[] equalArray = new String[maxLength];
                for(int eq = 0 ; eq < maxLength ; eq++) {
                    equalArray[eq] = cardSet.getCardByPos(j + eq);
                }

                if(!equalsGames(equalArray, selectCards, order).isEmpty()) {

                    for (int m = 0; m < maxLength; m++) {
                        createColorMatch(i, m + j, mapResult);
                    }
                }
            }
        }
    }

    private void searchTopDirection(List<CardSet> list, String[] selectCards, Map<String,
            ColorMatch> mapResult) {

        int maxLength = selectCards.length;
        boolean order = view.isOrderSet();

        for (int i = 0; i <= list.size() - maxLength; i++) {

            if (i + maxLength > list.size()) {
                continue;
            }

            List<CardSet> cardFromFile = new ArrayList<>(list.subList(i, i + maxLength));

            for (int j = 0; j < 4; j++) {

                String[] equalArray = new String[maxLength];
                for(int eq = 0 ; eq < maxLength ; eq++) {
                    equalArray[eq] = cardFromFile.get(eq).getCardByPos(j);
                }

                if(!equalsGames(equalArray, selectCards, order).isEmpty()) {

                    for (int m = 0; m < maxLength; m++) {
                        createColorMatch(i + m, j, mapResult);
                    }
                }
            }
        }
    }

    private void searchTopLeftDirection(List<CardSet> list, String[] selectCards,
                                        Map<String, ColorMatch> mapResult) {
        int maxLength = selectCards.length;
        boolean order = view.isOrderSet();

        for (int i = 0; i <= list.size() - maxLength; i++) {
            if (i + maxLength > list.size()) {
                continue;
            }

            List<CardSet> cardFromFile = new ArrayList<>(list.subList(i, i + maxLength));

            for (int j = 0; j < 4 - maxLength + 1; j++) {

                String[] equalArray = new String[maxLength];
                for (int eq = 0; eq < maxLength; eq++) {
                    equalArray[eq] = cardFromFile.get(maxLength - eq - 1)
                            .getCardByPos(maxLength - eq - 1 + j);
                }

                if (!equalsGames(equalArray, selectCards, order).isEmpty()) {

                    for (int m = 0; m < maxLength; m++) {
                        createColorMatch(i + m, j + m, mapResult);
                    }
                }
            }
        }
    }

    private void searchTopRightDirection(List<CardSet> list, String[] selectCards, Map<String,
            ColorMatch> mapResult) {
        int maxLength = selectCards.length;
        boolean order = view.isOrderSet();

        for (int i = 0; i <= list.size() - maxLength; i++) {
            if (i + maxLength > list.size()) {
                continue;
            }

            List<CardSet> cardFromFile = new ArrayList<>(list.subList(i, i + maxLength));

            for (int j = 0; j < 4 - maxLength + 1; j++) {

                String[] equalArray = new String[maxLength];
                for(int eq = 0 ; eq < maxLength ; eq++) {
                    equalArray[eq] = cardFromFile.get(maxLength - eq - 1).getCardByPos(j + eq);
                }

                if(!equalsGames(equalArray, selectCards, order).isEmpty()) {

                    for (int m = 0; m < maxLength; m++) {
                        createColorMatch(i + m, j - m + maxLength - 1, mapResult);
                    }
                }
            }
        }
    }

    private static String equalsGames(String[] cardsFromFile,
                                      String[] cards, boolean order) {

        if(order) {
            return equalsCardWithOrder(cardsFromFile, cards);
        } else {
            return equalsCardWithoutOrder(cardsFromFile, cards);
        }
    }

    protected static boolean equalsDates(String[] dates, String[] cardDates) {
        for (String cardDateString : cardDates) {
            boolean dateInArray = false;
            Date cardDate = toDate(cardDateString);

            for (String selectDateString : dates) {
                Date selectDate = toDate(selectDateString);

                if (selectDate.getTime() == cardDate.getTime()) {
                    dateInArray = true;
                    break;
                }
            }

            if (!dateInArray) {
                return false;
            }
        }
        return true;
    }

    private static String equalsCardWithOrder(String[] cardFromFile, String[] cards) {

        String str1 = stringFromArray(cards);
        String str2 = inverseStringFromArray(cards);
        String res = stringFromArray(cardFromFile);

        if(res.equals(str1) || res.equals(str2)) {
            return str1;
        } else {
            return "";
        }
    }

    private static String stringFromArray(String[] array) {
        String res = "";
        for(String str : array) {
            res += str;
        }
        return res;
    }

    private static String inverseStringFromArray(String[] str) {
        String res = "";
        for(int i = str.length - 1 ; i >= 0 ; i--) {
            res += str[i];
        }
        return res;
    }

    private static String equalsCardWithoutOrder(String[] cardFromFile, String[] cards) {

        List<String> list = new ArrayList<>(Arrays.asList(cardFromFile));

        String str = "";
        int success = 0;

        for (int i = 0; i < cards.length; i++) {
            for (int j = 0; j < list.size(); j++) {
                if (cards[i].equals(list.get(j))) {
                    str += cards[i];
                    list.remove(j);
                    success++;
                    break;
                }
            }
            if (success != i + 1) {
                return "";
            }
        }
        return str;
    }

    private void outputData(List<List<CardSet>> workList) {
        ((MainActivityThree) view.getContext()).runOnUiThread(() -> {
            view.setRecyclerResultData(result, workList);
            view.changeVisibleBlockWait(false);
        });
    }

    private String createKey(int i, int j) {
        return i + "," + j;
    }

    private void createColorMatch(int i, int j, Map<String, ColorMatch> result) {

        int color;
        color = view.getContext().getResources().getColor(R.color.yellow);

        String key = createKey(i, j);
        if (result.get(key) == null) {
            result.put(key, new ColorMatch(color));
        } else {
            if (!result.get(key).isIntersection() && result.get(key).getColor() != color) {
                result.get(key).setIntersection(true);
                result.get(key).setColor(
                        view.getContext().getResources().getColor(R.color.yellow));
            }
        }
    }

    private static List<List<CardSet>> createWorkList(List<CardSet> setList, String[] dates) {
        List<List<CardSet>> allResult = new ArrayList<>();

        for (String date : dates) {
            List<CardSet> result = new ArrayList<>();

            for (CardSet cardSet : setList) {
                if (equalsDates(new String[]{date}, new String[]{cardSet.getDateString()})) {
                    result.add(cardSet);
                }
            }

            allResult.add(result);
        }
        return allResult;
    }
}
