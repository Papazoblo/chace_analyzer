package alex.studio.csvsearcher.ui.main_activity.presenter;

import static alex.studio.csvsearcher.utils.DateUtils.doDateOperation;
import static alex.studio.csvsearcher.utils.DateUtils.getDateField;
import static alex.studio.csvsearcher.utils.DateUtils.toDate;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import alex.studio.csvsearcher.dto.CardMatch;
import alex.studio.csvsearcher.dto.CardSet;
import alex.studio.csvsearcher.dto.MatcherPosition;
import alex.studio.csvsearcher.enums.TypeMatch;
import alex.studio.csvsearcher.functions.Consumer;
import alex.studio.csvsearcher.ui.main_activity.view.MainActivityOne;

public class MainPresenterOne extends MainPresenter {

    private final int FOUR_ORIGINAL = 0;
    private final int FOUR_RANDOM = 1;
    private final int THREE_ORIGINAL = 2;
    private final int THREE_RANDOM = 3;

    private boolean[] options;

    @Override
    public void initializationData(Consumer<CardSet> action) {
        if (listCard.isEmpty()) {
            initReadFile(action);
        } else {
            action.accept((CardSet) listCard.get(0));
        }
    }

    @Override
    public void launchAlgorithm() {

        options = ((MainActivityOne) view).getOptionsState();

        String[] selectCards = view.getCards();

        String[] selectedDate = view.getActiveDate();

        List<CardSet> workListCard = getWorkList(1);
        List<Date[]> dates = createDateArray(selectedDate[0]);

        Map<Integer, Map<Date, List<CardMatch>>> matchMap = createWorkList(workListCard, dates, selectCards);
        printOutputData(findResult(matchMap));
    }

    private List<Date[]> createDateArray(String curDateString) {
        List<Date[]> result = new ArrayList<>();
        Date curDate = toDate(curDateString);
        Date minDate = toDate(((CardSet) listCard.get(listCard.size() - 1)).getDateString());
        Date beforeDate = doDateOperation(curDate, Calendar.DAY_OF_MONTH, -7);
        Date afterDate = doDateOperation(curDate, Calendar.DAY_OF_MONTH, 7);
        List<Integer> years = ((MainActivityOne) view).getSelectedYears();
        do {
            beforeDate = doDateOperation(beforeDate, Calendar.YEAR, -1);
            afterDate = doDateOperation(afterDate, Calendar.YEAR, -1);
            if (beforeDate.before(minDate) || afterDate.before(minDate)) {
                break;
            }
            if (!isYearContains(beforeDate, years)) {
                continue;
            }
            result.add(new Date[]{beforeDate, afterDate});
        } while (true);
        return result;
    }

    private boolean isYearContains(Date date, List<Integer> years) {
        if (years.isEmpty()) {
            return true;
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return years.contains(c.get(Calendar.YEAR));
    }

    private void printOutputData(List<CardMatch> result) {
        ((MainActivityOne) view.getContext()).runOnUiThread(() -> {
            view.setCardMatchListToRecycler(result);
            view.changeVisibleBlockWait(false);
        });
    }

    private static List<CardMatch> findResult(Map<Integer, Map<Date, List<CardMatch>>> matchYearMap) {
        List<CardMatch> result = new ArrayList<>();
        for (Integer year : matchYearMap.keySet()) {
            if (!matchYearMap.containsKey(year)) {
                continue;
            }
            for (Map.Entry<Date, List<CardMatch>> entry : matchYearMap.get(year).entrySet()) {
                if (entry.getValue().isEmpty()) {
                    continue;
                }
                Collections.sort(entry.getValue(), MainPresenterOne::resultComparator);
                CardMatch match = entry.getValue().get(0);
                match.setYear(year);
                result.add(match);
            }
        }
        Collections.sort(result, (o1, o2) -> o2.getSet().getDate().compareTo(o1.getSet().getDate()));
        return result;
    }

    private Map<Integer, Map<Date, List<CardMatch>>> createWorkList(List<CardSet> cardSets,
                                                                    List<Date[]> dates,
                                                                    String[] selectCards) {
        boolean firstMatch = false;
        Map<Integer, Map<Date, List<CardMatch>>> mapYears = new HashMap<>();

        for (int i = 0, j = 0; i < dates.size(); i++) {
            Map<Date, List<CardMatch>> mapDate = new HashMap<>();
            for (; j < cardSets.size(); j++) {
                CardSet curSet = cardSets.get(j);
                if (isBetweenDate(curSet, dates.get(i))) {
                    if (!firstMatch && j != 0) {
                        firstMatch = true;
                    }

                    searchMatch(cardSets.get(j - 1), curSet, selectCards, mapDate);
                } else if (curSet.getDate().before(dates.get(i)[0])) {
                    break;
                }
                if (!mapDate.isEmpty()) {
                    mapYears.put(getDateField(dates.get(i)[0], Calendar.YEAR), mapDate);
                }
            }
            firstMatch = false;
        }
        return mapYears;
    }

    private static boolean isBetweenDate(CardSet curSet, Date[] dates) {
        return (curSet.getDate().equals(dates[0]) || curSet.getDate().after(dates[0])) &&
                (curSet.getDate().equals(dates[1]) || curSet.getDate().before(dates[1]));
    }

    private void searchMatch(CardSet prevSet, CardSet curSet, String[] selectCards,
                             Map<Date, List<CardMatch>> resultMap) {

        String[] curCards = curSet.getCards();
        int count = 0;
        String matchedOrder = "";

        MatcherPosition matcherPosition = new MatcherPosition();
        for (int i = 0; i < selectCards.length; i++) {
            for (int j = selectCards.length - 1; j >= 0; j--) {
                if (selectCards[i].equals(curCards[j])) {
                    matchedOrder += curCards[j];
                    matcherPosition.getMatched()[i] = true;
                    replacePositions(matcherPosition, i, j);
                    curCards[j] = "-";
                    count++;
                    break;
                }
            }
        }

        Date curDate = curSet.getDate();
        if (!resultMap.containsKey(curDate)) {
            resultMap.put(curDate, new ArrayList<>());
        }

        if (count == 3) {
            boolean isContain = curSet.toString(true, false).contains(matchedOrder);
            if (isContain && options[THREE_ORIGINAL]) {
                resultMap.get(curDate).add(new CardMatch(count, curSet, prevSet, TypeMatch.FULL,
                        matcherPosition));
            } else if (!isContain && options[THREE_RANDOM]) {
                resultMap.get(curDate).add(new CardMatch(count, curSet, prevSet, TypeMatch.ANY,
                        matcherPosition));
            }
        } else if (count == 4) {
            boolean isEqual = curSet.toString(true, false).equals(matchedOrder);
            if (isEqual && options[FOUR_ORIGINAL]) {
                resultMap.get(curDate).add(new CardMatch(count, curSet, prevSet, TypeMatch.FULL,
                        matcherPosition));
            } else if (!isEqual && options[FOUR_RANDOM]) {
                resultMap.get(curDate).add(new CardMatch(count, curSet, prevSet, TypeMatch.ANY,
                        matcherPosition));
            }
        }
    }

    private static int resultComparator(CardMatch o1, CardMatch o2) {
        int compResult = Integer.compare(o2.getType().name().length(), o1.getType().name().length());
        if (compResult == 0) {
            compResult = Integer.compare(o2.getCount(), o1.getCount());
        }
        return compResult;
    }

    private static void replacePositions(MatcherPosition mp, int pos, int val) {
        for (int i = 0; i < mp.getPositions().length; i++) {
            if (mp.getPositions()[i] == val && pos == i) {
                break;
            } else if (mp.getPositions()[i] == val) {
                int tmp = mp.getPositions()[pos];
                mp.getPositions()[pos] = val;
                mp.getPositions()[i] = tmp;
                break;
            }
        }
    }
}
