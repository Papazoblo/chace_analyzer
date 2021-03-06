package alex.studio.csvsearcher.ui.main_activity.presenter;


import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import alex.studio.csvsearcher.dto.CardSet;
import alex.studio.csvsearcher.dto.CardSetSix;
import alex.studio.csvsearcher.functions.Consumer;
import alex.studio.csvsearcher.ui.main_activity.view.MainActivitySix;

public class MainPresenterSix extends MainPresenter {

    private List<CardSet> result;

    @Override
    public void initializationData(Consumer<List<CardSet>> action) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void launchAlgorithm() {

        result = new ArrayList<>();
        List<String[]> selectCards = view.getCardsList();

        String[] dates = view.getActiveDate();

        List<CardSet> workListCard = getWorkList(selectCards.size());

        if (workListCard.isEmpty()) {
            view.changeVisibleBlockWait(false);
            view.clearData();
            return;
        }

        switch (view.getDirection()) {
            case TOP:
                searchTopDirection(workListCard, dates, selectCards);
                //searchTopLeftDirection(workListCard, dates, selectCards);
                //searchTopRightDirection(workListCard, dates, selectCards);
                break;
            case BOTTOM:
                searchBottomDirection(workListCard, dates, selectCards);
                //searchBottomLeftDirection(workListCard, dates, selectCards);
                //searchBottomRightDirection(workListCard, dates, selectCards);
                break;
        }

        outputData(sortedResult(result));
    }

    private void searchTopDirection(List<CardSet> list, String[] dates,
                                    List<String[]> selectCards) {
        int step = selectCards.size() + 1;

        for (int i = 0; i < list.size() - step + 1; i++) {

            if (i + step >= list.size()) {
                continue;
            }

            List<CardSet> selection = list.subList(i, i + step);

            boolean resultEquals = true;
            for (int j = 0; j < selectCards.size(); j++) {
                CardSet current = selection.get(j + 1);
                if (!equalsGames(
                        new String[]{
                                current.getCard1(),
                                current.getCard2(),
                                current.getCard3(),
                                current.getCard4()
                        },
                        selectCards.get(j),
                        dates, selection.get(j + 1).getDateString())) {
                    resultEquals = false;
                    break;
                }
            }

            if (resultEquals) {
                addToResults(selection.get(0), selectCards.get(0));
            }
        }
    }

    private void searchBottomDirection(List<CardSet> list, String[] dates,
                                       List<String[]> selectCards) {
        int step = selectCards.size() + 1;

        for (int i = 0; i < list.size() - step + 1; i++) {

            if (i + step >= list.size()) {
                continue;
            }

            List<CardSet> selection = list.subList(i, i + step);

            boolean resultEquals = true;
            for (int j = 0; j < selectCards.size(); j++) {
                CardSet current = selection.get(j);
                if (!equalsGames(
                        new String[]{
                                current.getCard1(),
                                current.getCard2(),
                                current.getCard3(),
                                current.getCard4()
                        },
                        selectCards.get(j),
                        dates, selection.get(j).getDateString())) {
                    resultEquals = false;
                    break;
                }
            }

            if (resultEquals) {
                addToResults(selection.get(selection.size() - 1), selectCards.get(0));
            }
        }
    }

    private void searchBottomLeftDirection(List<CardSet> list, String[] dates,
                                           List<String[]> selectCards) {
        int step = selectCards.size() + 1;
        int endStep = list.size() - step - 4 + 2;
        for (int i = 0; i <= endStep; i++) {

            if (i + 2 + step >= list.size()) {
                continue;
            }

            List<CardSet> selection;
            if (i == 0) {
                selection = list.subList(i, i + 3 + step);
            } else if (i == endStep) {
                selection = list.subList(i - 1, i + 2 + step);
            } else {
                selection = list.subList(i - 1, i + 3 + step);
            }

            boolean resultEquals = true;
            for (int j = 0; j < selectCards.size(); j++) {
                if (!equalsGames(
                        selection.subList(j + (i != 0 ? 1 : 0), j + (i != 0 ? 1 : 0) + 4),
                        selectCards.get(j),
                        dates, "BL")) {
                    resultEquals = false;
                    break;
                }
            }

            if (resultEquals) {
                List<CardSet> toResult;
                if (i == 0) {
                    toResult = selection.subList(step - 1, step - 1 + 4);
                } else {
                    toResult = new ArrayList<>(selection.subList(step, step + 4));
                }
                addToResults(toResult, selectCards.get(0), "TR");
            }
        }
    }

    private void searchBottomRightDirection(List<CardSet> list, String[] dates,
                                            List<String[]> selectCards) {
        int step = selectCards.size() + 1;
        int endStep = list.size() - step - 4 + 2;
        for (int i = 0; i <= endStep; i++) {

            if (i + 2 + step >= list.size()) {
                continue;
            }

            List<CardSet> selection;
            if (i == 0) {
                selection = list.subList(i, i + 3 + step);
            } else if (i == endStep) {
                selection = list.subList(i - 1, i + 2 + step);
            } else {
                selection = list.subList(i - 1, i + 3 + step);
            }

            boolean resultEquals = true;
            for (int j = 0; j < selectCards.size(); j++) {
                if (!equalsGames(
                        selection.subList(j + (i != 0 ? 1 : 0), j + (i != 0 ? 1 : 0) + 4),
                        selectCards.get(j),
                        dates, "BR")) {
                    resultEquals = false;
                    break;
                }
            }

            if (resultEquals) {
                List<CardSet> toResult;
                if (i == 0) {
                    toResult = selection.subList(step - 1, step - 1 + 4);
                } else {
                    toResult = new ArrayList<>(selection.subList(step, step + 4));
                }
                addToResults(toResult, selectCards.get(0), "BR");
            }
        }
    }

    private void searchTopLeftDirection(List<CardSet> list, String[] dates,
                                        List<String[]> selectCards) {

        int step = selectCards.size() + 1;
        int endStep = list.size() - step - 4 + 2;
        for (int i = 0; i <= endStep; i++) {
            List<CardSet> selection;

            if (i + 2 + step >= list.size()) {
                continue;
            }

            if (i == 0) {
                selection = list.subList(i, i + 3 + step);
            } else if (i == endStep) {
                selection = list.subList(i - 1, i + 2 + step);
            } else {
                selection = list.subList(i - 1, i + 3 + step);
            }

            boolean resultEquals = true;
            for (int j = 0; j < selectCards.size(); j++) {
                if (!equalsGames(
                        selection.subList(j + (i != 0 ? 1 : 0), j + (i != 0 ? 1 : 0) + 4),
                        selectCards.get(j),
                        dates, "BR")) {
                    resultEquals = false;
                    break;
                }
            }

            if (resultEquals) {
                List<CardSet> toResult;
                if (i != 0) {

                    if (i == endStep) {
                        toResult = selection.subList(0, 4);
                    } else {
                        toResult = new ArrayList<>(selection.subList(0, 4));
                    }
                    addToResults(toResult, selectCards.get(0), "BR");
                }
            }
        }
    }

    private void searchTopRightDirection(List<CardSet> list, String[] dates,
                                         List<String[]> selectCards) {
        int step = selectCards.size() + 1;
        int endStep = list.size() - step - 4 + 2;
        for (int i = 0; i <= endStep; i++) {

            if (i + 2 + step >= list.size()) {
                continue;
            }

            List<CardSet> selection;
            if (i == 0) {
                selection = list.subList(i, i + 3 + step);
            } else if (i == endStep) {
                selection = list.subList(i - 1, i + 2 + step);
            } else {
                selection = list.subList(i - 1, i + 3 + step);
            }

            boolean resultEquals = true;
            for (int j = 0; j < selectCards.size(); j++) {
                if (!equalsGames(
                        selection.subList(j + (i != 0 ? 1 : 0), j + (i != 0 ? 1 : 0) + 4),
                        selectCards.get(j),
                        dates, "BL")) {
                    resultEquals = false;
                    break;
                }
            }

            if (resultEquals) {
                List<CardSet> toResult;
                if (i == endStep) {
                    toResult = selection.subList(0, 4);
                } else {
                    toResult = new ArrayList<>(selection.subList(0, 4));
                }
                addToResults(toResult, selectCards.get(0), "TR");
            }
        }
    }

    private boolean equalsGames(String[] cardsFromFile,
                                String[] cards, String[] dates,
                                String... cardDates) {

        if (dates != null) {
            return equalsCard(cardsFromFile, cards) && equalsDates(dates, cardDates);
        } else {
            return equalsCard(cardsFromFile, cards);
        }
    }

    private boolean equalsGames(List<CardSet> cardsFromFile,
                                String[] cards, String[] dates, String type) {

        if (dates != null) {
            for (int i = 0; i < cardsFromFile.size(); i++) {
                if (!equalsDates(dates, new String[]{cardsFromFile.get(i).getDateString()})) {
                    return false;
                }
            }
        }

        String[] sortedArray;
        switch (type) {
            case "BL":
                sortedArray = new String[]{
                        cardsFromFile.get(3).getCardByPos(0),
                        cardsFromFile.get(2).getCardByPos(1),
                        cardsFromFile.get(1).getCardByPos(2),
                        cardsFromFile.get(0).getCardByPos(3)
                };
                break;
            case "BR":
                sortedArray = new String[]{
                        cardsFromFile.get(0).getCardByPos(0),
                        cardsFromFile.get(1).getCardByPos(1),
                        cardsFromFile.get(2).getCardByPos(2),
                        cardsFromFile.get(3).getCardByPos(3)
                };
                break;
            default:
                sortedArray = new String[0];
        }
        return equalsCard(sortedArray, cards);
    }

    private boolean equalsCard(String[] cardFromFile, String[] cards) {

        for (int i = 0; i < 4; i++) {
            if (cards[i].equals("-")) {
                continue;
            }
            if (!cardFromFile[i].equals(cards[i])) {
                return false;
            }
        }
        return true;
    }

    private void outputData(List<CardSetSix> result) {
        ((MainActivitySix) view.getContext()).runOnUiThread(() -> {
            view.setCardSetSixListToRecycler(result);
            view.changeVisibleBlockWait(false);
        });
    }

    private void addToResults(CardSet cardSet, String[] pattern) {
        CardSet resultCardSet = new CardSet();
        for (int i = 0; i < pattern.length; i++) {
            resultCardSet.setCardByPos(i, pattern[i].equals("-") ? "-" : cardSet.getCardByPos(i));
            resultCardSet.setNumber(cardSet.getNumber());
        }
        result.add(resultCardSet);
    }

    private void addToResults(List<CardSet> cardSet, String[] pattern, String typeDirection) {

        for (int j = 0; j < cardSet.size(); j += 4) {

            CardSet resultCardSet = new CardSet();
            for (int i = 0; i < pattern.length; i++) {

                switch (typeDirection) {
                    case "TR":
                        resultCardSet.setCardByPos(i, pattern[i].equals("-") ? "-" : cardSet.get(j + 3 - i)
                                .getCardByPos(i));
                        resultCardSet.setNumber(cardSet.get(j + 3 - i).getNumber());
                        break;
                    case "BR":
                        resultCardSet.setCardByPos(i, pattern[i].equals("-") ? "-" : cardSet.get(j + i)
                                .getCardByPos(i));
                        resultCardSet.setNumber(cardSet.get(j + i).getNumber());
                        break;

                }
            }
            result.add(resultCardSet);
        }
    }

    private List<CardSetSix> sortedResult(List<CardSet> result) {
        Map<String, CardSetSix> groupMap = new HashMap<>();
        for (CardSet cardSet : result) {
            CardSetSix val;
            if ((val = groupMap.get(cardSet.toString())) != null) {
                val.addGameNumber(Long.valueOf(cardSet.getNumber()));
                groupMap.put(cardSet.toString(), val);
            } else {
                CardSetSix set = new CardSetSix();
                set.stringToCardSet(cardSet.toString());
                set.addGameNumber(Long.valueOf(cardSet.getNumber()));
                groupMap.put(cardSet.toString(), set);
            }
        }

        List<CardSetSix> list = new ArrayList<>(groupMap.values());
        Collections.sort(list, (e1, e2) -> Integer.compare(e2.getCount(), e1.getCount()));

        /*for(int i = 0 ; i < list.size() ; i++) {
            if(i == 0) {
                continue;
            }
            list.get(0).getGamesNumber().addAll(list.get(i).getGamesNumber());
        }*/
        return list;
    }
}
