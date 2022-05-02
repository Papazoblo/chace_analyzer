package alex.studio.csvsearcher.ui.main_activity.presenter;

import static alex.studio.csvsearcher.utils.DateUtils.doDateOperation;
import static alex.studio.csvsearcher.utils.DateUtils.getDateField;
import static alex.studio.csvsearcher.utils.DateUtils.toDate;
import static alex.studio.csvsearcher.utils.DateUtils.toDateString;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
    public void initializationData(Consumer<List<CardSet>> action) {
        if (listCard.isEmpty()) {
            initReadFile(action);
        } else {
            action.accept(Collections.singletonList((CardSet) listCard.get(0)));
        }
    }

    @Override
    public void launchAlgorithm() {

        options = ((MainActivityOne) view).getOptionsState();

        String[] selectCards = view.getCards();

        List<Integer> days = ((MainActivityOne) view).getSelectedDays();
        Set<Integer> months = ((MainActivityOne) view).getSelectedMonths();
        List<Integer> years = ((MainActivityOne) view).getSelectedYears();
        List<Date[]> dateList = createDateArray(days, new ArrayList<>(months),
                years);
        List<CardSet> workListCard = getWorkList(1);

        Map<Integer, Map<Date, List<CardMatch>>> matchMap = createWorkList(workListCard, dateList,
                selectCards);
        printOutputData(findResult(matchMap));
    }

    private List<Date[]> createDateArray(List<Integer> days, List<Integer> months,
                                         List<Integer> years) {
        List<Date[]> dates = new ArrayList<>();
        for (Integer year : years) {
            for (Integer month : months) {
                Date date1;
                Date date2;
                if (days.size() == 2) {
                    date1 = toDate(toDateString(days.get(0), month, year));
                    date2 = toDate(toDateString(days.get(1), month, year));
                } else {
                    Date date = toDate(toDateString(days.get(0), month, year));
                    Calendar c = Calendar.getInstance();
                    c.setTime(date);
                    date1 = doDateOperation(date, Calendar.DAY_OF_MONTH, -7);
                    date2 = doDateOperation(date, Calendar.DAY_OF_MONTH, 7);
                }
                dates.add(new Date[]{date1, date2});
            }
        }
        Collections.sort(dates, (d1, d2) -> d2[0].compareTo(d1[0]));
        return dates;
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
        Collections.sort(result, (o1, o2) -> {
            int i = o2.getSet().getDate().compareTo(o1.getSet().getDate());
            if (i == 0) {
                return Long.valueOf(o2.getSet().getNumber())
                        .compareTo(Long.valueOf(o1.getSet().getNumber()));
            }
            return i;
        });
        return result;
    }

    private Map<Integer, Map<Date, List<CardMatch>>> createWorkList(List<CardSet> cardSets,
                                                                    List<Date[]> dates,
                                                                    String[] selectCards) {
        Map<Integer, Map<Date, List<CardMatch>>> mapYears = new HashMap<>();

        for (int i = 0, j = 1; i < dates.size(); i++) {
            Map<Date, List<CardMatch>> mapDate = new HashMap<>();
            for (; j < cardSets.size(); j++) {
                if (j <= 0) {
                    j = 1;
                }
                CardSet curSet = cardSets.get(j);
                if (isBetweenDate(curSet, dates.get(i))) {
                    searchMatch(cardSets.get(j - 1), curSet, selectCards, mapDate);
                } else if (curSet.getDate().before(dates.get(i)[0])) {
                    j--;
                    break;
                }
            }
            int year = getDateField(dates.get(i)[0], Calendar.YEAR);
            if (!mapYears.containsKey(year)) {
                mapYears.put(getDateField(dates.get(i)[0], Calendar.YEAR), new HashMap<>());
            }
            mapYears.get(year).putAll(mapDate);
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
            boolean[] contains = getThreeOriginal(selectCards, curSet);
            if (contains != null && options[THREE_ORIGINAL]) {
                matcherPosition.setMatched(contains);
                matcherPosition.resetPositions();
                resultMap.get(curDate).add(new CardMatch(count, curSet, prevSet, TypeMatch.FULL,
                        matcherPosition));
            } else if (contains == null && options[THREE_RANDOM]) {
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

    private boolean[] getThreeOriginal(String[] selectCards, CardSet curSet) {
        String setString = curSet.toString(true, false);
        if (setString.startsWith(selectCards[0] + selectCards[1] + selectCards[2])) {
            return new boolean[]{true, true, true, false};
        } else if (setString.endsWith(selectCards[1] + selectCards[2] + selectCards[3])) {
            return new boolean[]{false, true, true, true};
        } else if (setString.startsWith(selectCards[0] + selectCards[1]) &&
                setString.endsWith(selectCards[3])) {
            return new boolean[]{true, true, false, true};
        } else if (setString.startsWith(selectCards[0]) &&
                setString.endsWith(selectCards[2] + selectCards[3])) {
            return new boolean[]{true, false, true, true};
        } else {
            return null;
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
