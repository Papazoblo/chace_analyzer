package alex.studio.csvsearcher.ui.main_activity.presenter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import alex.studio.csvsearcher.dto.CardSet;
import alex.studio.csvsearcher.ui.main_activity.view.MainActivity;

public class MainPresenterTwo extends MainPresenter {

    private Map<String, Integer> result;

    protected void launchAlgorithm() {

        result = initResultMap();
        String[] selectCards = view.getCards();

        String[] dates = view.getActiveDate();

        List<CardSet> workListCard = getWorkList(1);

        if (workListCard.isEmpty()) {
            view.changeVisibleBlockWait(false);
            view.clearData();
            return;
        }

        switch (view.getDirection()) {
            case "right":
                searchRightDirection(workListCard, dates, selectCards);
                break;
            case "left":
                searchLeftDirection(workListCard, dates, selectCards);
                break;
            case "top":
                searchTopDirection(workListCard, dates, selectCards);
                break;
            case "bottom":
                searchBottomDirection(workListCard, dates, selectCards);
                break;
            case "topRight":
                searchTopRightDirection(workListCard, dates, selectCards);
                break;
            case "topLeft":
                searchTopLeftDirection(workListCard, dates, selectCards);
                break;
            case "bottomRight":
                searchBottomRightDirection(workListCard, dates, selectCards);
                break;
            case "bottomLeft":
                searchBottomLeftDirection(workListCard, dates, selectCards);
                break;
            default:
                searchRightDirection(workListCard, dates, selectCards);
                searchLeftDirection(workListCard, dates, selectCards);
                searchTopDirection(workListCard, dates, selectCards);
                searchBottomDirection(workListCard, dates, selectCards);
                searchTopRightDirection(workListCard, dates, selectCards);
                searchTopLeftDirection(workListCard, dates, selectCards);
                searchBottomRightDirection(workListCard, dates, selectCards);
                searchBottomLeftDirection(workListCard, dates, selectCards);
        }

        outputData(result);
    }

    private void searchRightDirection(List<CardSet> list, String[] dates,
                                      String[] selectCards) {

        for (int i = 0; i < list.size(); i++) {
            CardSet cardSet = list.get(i);

            equalsGames(new String[]{cardSet.getCard1(), cardSet.getCard2(), cardSet.getCard3(),
                    cardSet.getCard4()}, selectCards, dates, cardSet.getDate());
        }
    }

    private void searchLeftDirection(List<CardSet> list, String[] dates,
                                     String[] selectCards) {

        for (int i = 0; i < list.size(); i++) {
            CardSet cardSet = list.get(i);

            equalsGames(new String[]{cardSet.getCard4(), cardSet.getCard3(), cardSet.getCard2(),
                    cardSet.getCard1()}, selectCards, dates, cardSet.getDate());
        }

    }

    private void searchTopDirection(List<CardSet> list, String[] dates,
                                    String[] selectCards) {

        int step = getStepSize(selectCards);
        CardSet cardSet0 = null;
        CardSet cardSet1 = null;
        CardSet cardSet2 = null;
        CardSet cardSet3 = null;
        CardSet cardSet4 = null;

        for (int i = 0; i < list.size() - step; i++) {

            switch (step) {
                case 4:
                    cardSet4 = list.get(i + 4);
                case 3:
                    cardSet3 = list.get(i + 3);
                case 2:
                    cardSet0 = list.get(i);
                    cardSet1 = list.get(i + 1);
                    cardSet2 = list.get(i + 2);
                    break;
            }

            for (int j = 0; j < 4; j++) {

                switch (step) {
                    case 2:
                        equalsGames(new String[]{
                                        cardSet2.getCardByPos(j),
                                        cardSet1.getCardByPos(j),
                                        cardSet0.getCardByPos(j)
                                }, selectCards, dates, cardSet2.getDate(),
                                cardSet1.getDate(), cardSet0.getDate());
                        break;
                    case 3:
                        equalsGames(new String[]{
                                        cardSet3.getCardByPos(j),
                                        cardSet2.getCardByPos(j),
                                        cardSet1.getCardByPos(j),
                                        cardSet0.getCardByPos(j)
                                }, selectCards,
                                dates, cardSet3.getDate(), cardSet2.getDate(), cardSet1.getDate(),
                                cardSet0.getDate());
                        break;
                    case 4:
                        equalsGames(new String[]{
                                        cardSet4.getCardByPos(j),
                                        cardSet3.getCardByPos(j),
                                        cardSet2.getCardByPos(j),
                                        cardSet1.getCardByPos(j),
                                        cardSet0.getCardByPos(j)
                                }, selectCards, dates, cardSet4.getDate(),
                                cardSet3.getDate(), cardSet2.getDate(),
                                cardSet1.getDate(), cardSet0.getDate());
                        break;
                }
            }
        }

    }

    private void searchBottomDirection(List<CardSet> list, String[] dates,
                                       String[] selectCards) {

        int step = getStepSize(selectCards);
        CardSet cardSet0 = null;
        CardSet cardSet1 = null;
        CardSet cardSet2 = null;
        CardSet cardSet3 = null;
        CardSet cardSet4 = null;

        for (int i = 0; i < list.size() - step; i++) {

            switch (step) {
                case 4:
                    cardSet4 = list.get(i + 4);
                case 3:
                    cardSet3 = list.get(i + 3);
                case 2:
                    cardSet0 = list.get(i);
                    cardSet1 = list.get(i + 1);
                    cardSet2 = list.get(i + 2);
                    break;
            }

            for (int j = 0; j < 4; j++) {

                switch (step) {
                    case 2:
                        equalsGames(new String[]{
                                        cardSet0.getCardByPos(j),
                                        cardSet1.getCardByPos(j),
                                        cardSet2.getCardByPos(j)
                                }, selectCards, dates, cardSet0.getDate(),
                                cardSet1.getDate(), cardSet2.getDate());
                        break;
                    case 3:
                        equalsGames(new String[]{
                                        cardSet0.getCardByPos(j),
                                        cardSet1.getCardByPos(j),
                                        cardSet2.getCardByPos(j),
                                        cardSet3.getCardByPos(j)
                                }, selectCards,
                                dates, cardSet0.getDate(), cardSet1.getDate(), cardSet2.getDate(),
                                cardSet3.getDate());
                        break;
                    case 4:
                        equalsGames(new String[]{
                                        cardSet0.getCardByPos(j),
                                        cardSet1.getCardByPos(j),
                                        cardSet2.getCardByPos(j),
                                        cardSet3.getCardByPos(j),
                                        cardSet4.getCardByPos(j)
                                }, selectCards, dates, cardSet0.getDate(),
                                cardSet1.getDate(), cardSet2.getDate(),
                                cardSet3.getDate(), cardSet4.getDate());
                        break;
                }
            }
        }

    }

    private void searchTopRightDirection(List<CardSet> list, String[] dates,
                                         String[] selectCards) {

        for (int i = 0; i < list.size() - 3; i++) {
            CardSet cardSet1 = list.get(i);
            CardSet cardSet2 = list.get(i + 1);
            CardSet cardSet3 = list.get(i + 2);
            CardSet cardSet4 = list.get(i + 3);

            equalsGames(new String[]{cardSet4.getCardByPos(0), cardSet3.getCardByPos(1),
                            cardSet2.getCardByPos(2), cardSet1.getCardByPos(3)}, selectCards,
                    dates, cardSet1.getDate(), cardSet2.getDate(), cardSet3.getDate(),
                    cardSet4.getDate());
        }

    }

    private void searchTopLeftDirection(List<CardSet> list, String[] dates,
                                        String[] selectCards) {

        for (int i = 0; i < list.size() - 3; i++) {
            CardSet cardSet1 = list.get(i);
            CardSet cardSet2 = list.get(i + 1);
            CardSet cardSet3 = list.get(i + 2);
            CardSet cardSet4 = list.get(i + 3);

            equalsGames(new String[]{cardSet4.getCardByPos(3), cardSet3.getCardByPos(2),
                            cardSet2.getCardByPos(1), cardSet1.getCardByPos(0)}, selectCards,
                    dates, cardSet1.getDate(), cardSet2.getDate(), cardSet3.getDate(),
                    cardSet4.getDate());
        }

    }

    private void searchBottomRightDirection(List<CardSet> list, String[] dates,
                                            String[] selectCards) {

        for (int i = 0; i < list.size() - 3; i++) {
            CardSet cardSet1 = list.get(i);
            CardSet cardSet2 = list.get(i + 1);
            CardSet cardSet3 = list.get(i + 2);
            CardSet cardSet4 = list.get(i + 3);

            equalsGames(new String[]{cardSet1.getCardByPos(0), cardSet2.getCardByPos(1),
                            cardSet3.getCardByPos(2), cardSet4.getCardByPos(3)}, selectCards,
                    dates, cardSet1.getDate(), cardSet2.getDate(), cardSet3.getDate(),
                    cardSet4.getDate());
        }

    }

    private void searchBottomLeftDirection(List<CardSet> list, String[] dates,
                                           String[] selectCards) {

        for (int i = 0; i < list.size() - 3; i++) {
            CardSet cardSet1 = list.get(i);
            CardSet cardSet2 = list.get(i + 1);
            CardSet cardSet3 = list.get(i + 2);
            CardSet cardSet4 = list.get(i + 3);

            equalsGames(new String[]{cardSet1.getCardByPos(3), cardSet2.getCardByPos(2),
                            cardSet3.getCardByPos(1), cardSet4.getCardByPos(0)}, selectCards,
                    dates, cardSet1.getDate(), cardSet2.getDate(), cardSet3.getDate(),
                    cardSet4.getDate());
        }

    }

    private void equalsGames(String[] cardsFromFile,
                             String[] cards, String[] dates,
                             String... cardDates) {


        if (dates != null) {
            if (!equalsDates(dates, cardDates)) {
                return;
            }
        }
        equalsCard(cardsFromFile, cards);
    }

    private boolean equalsCard(String[] cardFromFile, String[] cards) {

        String fromFile = toString(cardFromFile);
        String selected = toString(cards);

        int pos;
        if((pos = fromFile.indexOf(selected)) != -1) {
            if(pos + selected.length() < cardFromFile.length) {
                incrementResult(cardFromFile[pos + selected.length()]);
            }
        }

        if (cardFromFile[0].equals(cards[0]) && cardFromFile[1].equals(cards[1])) {
            if (cards[2].equals("-")) {
                incrementResult(cardFromFile[2]);
            } else if (cardFromFile.length > 3 && cardFromFile[2].equals(cards[2])) {
                if (cards[3].equals("-")) {
                    incrementResult(cardFromFile[3]);
                } else if (cardFromFile.length > 4 && cardFromFile[3] != null && cardFromFile[3].equals(cards[3])) {
                    incrementResult(cardFromFile[4]);
                }
            }
        }
        return true;
    }

    private String toString(String[] array) {

        String result = "";

        for(String str : array) {
            if(str.equals("-")) {
                break;
            }
            result += (str.equals("10") ? "1" : str);
        }
        return result;
    }

    private int getStepSize(String[] cards) {
        if (cards[2].equals("-")) {
            return 2;
        } else if (cards[3].equals("-")) {
            return 3;
        } else {
            return 4;
        }
    }

    private void incrementResult(String key) {
        result.put(key, result.get(key) + 1);
    }

    private Map<String, Integer> initResultMap() {
        Map<String, Integer> result = new HashMap<>();

        result.put("7", 0);
        result.put("8", 0);
        result.put("9", 0);
        result.put("10", 0);
        result.put("J", 0);
        result.put("Q", 0);
        result.put("K", 0);
        result.put("A", 0);

        return result;
    }

    private void outputData(Map<String, Integer> result) {
        ((MainActivity) view.getContext()).runOnUiThread(() -> {
            view.setRecyclerData(result);
            view.changeVisibleBlockWait(false);
        });
    }
}
