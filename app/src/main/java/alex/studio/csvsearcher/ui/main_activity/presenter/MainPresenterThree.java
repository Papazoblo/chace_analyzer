package alex.studio.csvsearcher.ui.main_activity.presenter;

import java.util.ArrayList;
import java.util.List;

import alex.studio.csvsearcher.dto.CardGroup;
import alex.studio.csvsearcher.dto.CardSet;
import alex.studio.csvsearcher.functions.Consumer;
import alex.studio.csvsearcher.ui.main_activity.view.MainActivityThreeFour;

public class MainPresenterThree extends MainPresenter {

    @Override
    public void initializationData(Consumer<CardSet> action) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void launchAlgorithm() {

        String[] selectCards = view.getCards();
        String[] dates = view.getActiveDate();

        List<CardSet> workListCard = getWorkList(1);

        List<CardGroup> result = new ArrayList<>();

        switch (view.getDirection()) {
            case RIGHT:
                result = searchRightDirection(workListCard, dates, selectCards);
                break;
            case LEFT:
                result = searchLeftDirection(workListCard, dates, selectCards);
                break;
            case TOP:
                result = searchTopDirection(workListCard, dates, selectCards);
                break;
            case BOTTOM:
                result = searchBottomDirection(workListCard, dates, selectCards);
                break;
            case TOP_RIGHT:
                result = searchTopRightDirection(workListCard, dates, selectCards);
                break;
            case TOP_LEFT:
                result = searchTopLeftDirection(workListCard, dates, selectCards);
                break;
            case BOTTOM_RIGHT:
                result = searchBottomRightDirection(workListCard, dates, selectCards);
                break;
            case BOTTOM_LEFT:
                result = searchBottomLeftDirection(workListCard, dates, selectCards);
                break;
            default:
                result.addAll(searchRightDirection(workListCard, dates, selectCards));
                result.addAll(searchLeftDirection(workListCard, dates, selectCards));
                result.addAll(searchTopDirection(workListCard, dates, selectCards));
                result.addAll(searchBottomDirection(workListCard, dates, selectCards));
                result.addAll(searchTopRightDirection(workListCard, dates, selectCards));
                result.addAll(searchTopLeftDirection(workListCard, dates, selectCards));
                result.addAll(searchBottomRightDirection(workListCard, dates, selectCards));
                result.addAll(searchBottomLeftDirection(workListCard, dates, selectCards));
        }

        outputData(result);
    }

    private List<CardGroup> searchRightDirection(List<CardSet> list, String[] dates,
                                                 String[] selectCards) {
        List<CardGroup> listGroup = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {
            CardSet cardSet = list.get(i);

            if (equalsGames(new String[]{cardSet.getCard1(), cardSet.getCard2(), cardSet.getCard3(),
                    cardSet.getCard4()}, selectCards, dates, cardSet.getDateString())) {
                if (i == 0) {
                    listGroup.add(new CardGroup(null, list.get(i + 1)));
                } else if (i == list.size() - 1) {
                    listGroup.add(new CardGroup(list.get(i - 1), null));
                } else {
                    listGroup.add(new CardGroup(list.get(i - 1), list.get(i + 1)));
                }
            }
        }
        return listGroup;
    }

    private List<CardGroup> searchLeftDirection(List<CardSet> list, String[] dates,
                                                String[] selectCards) {
        List<CardGroup> listGroup = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {
            CardSet cardSet = list.get(i);

            if (equalsGames(new String[]{cardSet.getCard4(), cardSet.getCard3(), cardSet.getCard2(),
                    cardSet.getCard1()}, selectCards, dates, cardSet.getDateString())) {
                if (i == 0) {
                    listGroup.add(new CardGroup(null, toLeft(list.get(i + 1))));
                } else if (i == list.size() - 1) {
                    listGroup.add(new CardGroup(toLeft(list.get(i - 1)), null));
                } else {
                    listGroup.add(new CardGroup(toLeft(list.get(i - 1)), toLeft(list.get(i + 1))));
                }
            }
        }

        return listGroup;
    }

    private List<CardGroup> searchTopDirection(List<CardSet> list, String[] dates,
                                               String[] selectCards) {
        List<CardGroup> listGroup = new ArrayList<>();

        for (int i = 0; i < list.size() - 3; i++) {

            if (i + 3 >= list.size()) {
                return listGroup;
            }

            CardSet cardSet1 = list.get(i);
            CardSet cardSet2 = list.get(i + 1);
            CardSet cardSet3 = list.get(i + 2);
            CardSet cardSet4 = list.get(i + 3);

            for (int j = 0; j < 4; j++) {

                if (equalsGames(new String[]{cardSet4.getCardByPos(j), cardSet3.getCardByPos(j),
                                cardSet2.getCardByPos(j), cardSet1.getCardByPos(j)}, selectCards,
                        dates, cardSet1.getDateString(), cardSet2.getDateString(),
                        cardSet3.getDateString(), cardSet4.getDateString())) {

                    if (j == 0) {
                        listGroup.add(new CardGroup(null, toTop(cardSet1, cardSet2, cardSet3,
                                cardSet4, j + 1)));
                    } else if (j == 3) {
                        listGroup.add(new CardGroup(toTop(cardSet1, cardSet2, cardSet3,
                                cardSet4, j - 1), null));
                    } else {
                        listGroup.add(new CardGroup(toTop(cardSet1, cardSet2, cardSet3,
                                cardSet4, j - 1), toTop(cardSet1, cardSet2, cardSet3,
                                cardSet4, j + 1)));
                    }
                }
            }
        }

        return listGroup;
    }

    private List<CardGroup> searchBottomDirection(List<CardSet> list, String[] dates,
                                                  String[] selectCards) {
        List<CardGroup> listGroup = new ArrayList<>();

        for (int i = 0; i < list.size() - 3; i++) {

            if (i + 3 >= list.size()) {
                return listGroup;
            }

            CardSet cardSet1 = list.get(i);
            CardSet cardSet2 = list.get(i + 1);
            CardSet cardSet3 = list.get(i + 2);
            CardSet cardSet4 = list.get(i + 3);

            for (int j = 0; j < 4; j++) {

                if (equalsGames(new String[]{cardSet1.getCardByPos(j), cardSet2.getCardByPos(j),
                                cardSet3.getCardByPos(j), cardSet4.getCardByPos(j)}, selectCards,
                        dates, cardSet1.getDateString(), cardSet2.getDateString(),
                        cardSet3.getDateString(), cardSet4.getDateString())) {

                    if (j == 0) {
                        listGroup.add(new CardGroup(null, toBottom(cardSet1, cardSet2, cardSet3,
                                cardSet4, j + 1)));
                    } else if (j == 3) {
                        listGroup.add(new CardGroup(toBottom(cardSet1, cardSet2, cardSet3,
                                cardSet4, j - 1), null));
                    } else {
                        listGroup.add(new CardGroup(toBottom(cardSet1, cardSet2, cardSet3,
                                cardSet4, j - 1), toBottom(cardSet1, cardSet2, cardSet3,
                                cardSet4, j + 1)));
                    }
                }
            }
        }

        return listGroup;
    }

    private List<CardGroup> searchTopRightDirection(List<CardSet> list, String[] dates,
                                                    String[] selectCards) {
        List<CardGroup> listGroup = new ArrayList<>();

        for (int i = 0; i < list.size() - 3; i++) {

            if (i + 3 >= list.size()) {
                return listGroup;
            }

            CardSet cardSet1 = list.get(i);
            CardSet cardSet2 = list.get(i + 1);
            CardSet cardSet3 = list.get(i + 2);
            CardSet cardSet4 = list.get(i + 3);

            if (equalsGames(new String[]{cardSet4.getCardByPos(0), cardSet3.getCardByPos(1),
                            cardSet2.getCardByPos(2), cardSet1.getCardByPos(3)}, selectCards,
                    dates, cardSet1.getDateString(), cardSet2.getDateString(),
                    cardSet3.getDateString(), cardSet4.getDateString())) {

                if (i == 0) {

                    CardSet cardSet5 = list.get(i + 4);

                    listGroup.add(new CardGroup(toRightDiagonal(cardSet3, cardSet2, cardSet1,
                            null), toRightDiagonal(cardSet5, cardSet4, cardSet3, cardSet2)));
                } else if (i == list.size() - 3) {
                    CardSet cardSet0 = list.get(i - 1);

                    listGroup.add(new CardGroup(toRightDiagonal(cardSet3, cardSet2, cardSet1,
                            cardSet0), toRightDiagonal(null, cardSet4, cardSet3,
                            cardSet2)));
                } else {
                    CardSet cardSet0 = list.get(i - 1);
                    CardSet cardSet5 = list.get(i + 4);

                    listGroup.add(new CardGroup(toRightDiagonal(cardSet3, cardSet2, cardSet1,
                            cardSet0), toRightDiagonal(cardSet5, cardSet4, cardSet3, cardSet2)));
                }
            }
        }

        return listGroup;
    }

    private List<CardGroup> searchTopLeftDirection(List<CardSet> list, String[] dates,
                                                   String[] selectCards) {
        List<CardGroup> listGroup = new ArrayList<>();

        for (int i = 0; i < list.size() - 3; i++) {

            if (i + 3 >= list.size()) {
                return listGroup;
            }

            CardSet cardSet1 = list.get(i);
            CardSet cardSet2 = list.get(i + 1);
            CardSet cardSet3 = list.get(i + 2);
            CardSet cardSet4 = list.get(i + 3);

            if (equalsGames(new String[]{cardSet4.getCardByPos(3), cardSet3.getCardByPos(2),
                            cardSet2.getCardByPos(1), cardSet1.getCardByPos(0)}, selectCards,
                    dates, cardSet1.getDateString(), cardSet2.getDateString(),
                    cardSet3.getDateString(), cardSet4.getDateString())) {

                if (i == 0) {

                    CardSet cardSet5 = list.get(i + 4);

                    listGroup.add(new CardGroup(toLeftDiagonal(cardSet3, cardSet2, cardSet1,
                            null), toLeftDiagonal(cardSet5, cardSet4, cardSet3, cardSet2)));
                } else if (i == list.size() - 3) {
                    CardSet cardSet0 = list.get(i - 1);

                    listGroup.add(new CardGroup(toLeftDiagonal(cardSet3, cardSet2, cardSet1,
                            cardSet0), toLeftDiagonal(null, cardSet4, cardSet3,
                            cardSet2)));
                } else {
                    CardSet cardSet0 = list.get(i - 1);
                    CardSet cardSet5 = list.get(i + 4);

                    listGroup.add(new CardGroup(toLeftDiagonal(cardSet3, cardSet2, cardSet1,
                            cardSet0), toLeftDiagonal(cardSet5, cardSet4, cardSet3, cardSet2)));
                }
            }
        }

        return listGroup;
    }

    private List<CardGroup> searchBottomRightDirection(List<CardSet> list, String[] dates,
                                                       String[] selectCards) {
        List<CardGroup> listGroup = new ArrayList<>();

        for (int i = 0; i < list.size() - 3; i++) {

            if (i + 3 >= list.size()) {
                return listGroup;
            }

            CardSet cardSet1 = list.get(i);
            CardSet cardSet2 = list.get(i + 1);
            CardSet cardSet3 = list.get(i + 2);
            CardSet cardSet4 = list.get(i + 3);

            if (equalsGames(new String[]{cardSet1.getCardByPos(0), cardSet2.getCardByPos(1),
                            cardSet3.getCardByPos(2), cardSet4.getCardByPos(3)}, selectCards,
                    dates, cardSet1.getDateString(), cardSet2.getDateString(),
                    cardSet3.getDateString(), cardSet4.getDateString())) {

                if (i == 0) {

                    CardSet cardSet5 = list.get(i + 4);

                    listGroup.add(new CardGroup(toRightDiagonal(null, cardSet1, cardSet2, cardSet3),
                            toRightDiagonal(cardSet2, cardSet3, cardSet4, cardSet5)));
                } else if (i == list.size() - 3) {
                    CardSet cardSet0 = list.get(i - 1);

                    listGroup.add(new CardGroup(toRightDiagonal(cardSet0, cardSet1, cardSet2,
                            cardSet3), toRightDiagonal(cardSet2, cardSet3, cardSet4, null)));
                } else {
                    CardSet cardSet0 = list.get(i - 1);
                    CardSet cardSet5 = list.get(i + 4);

                    listGroup.add(new CardGroup(toRightDiagonal(cardSet0, cardSet1, cardSet2,
                            cardSet3), toRightDiagonal(cardSet2, cardSet3, cardSet4, cardSet5)));
                }
            }
        }

        return listGroup;
    }

    private List<CardGroup> searchBottomLeftDirection(List<CardSet> list, String[] dates,
                                                      String[] selectCards) {
        List<CardGroup> listGroup = new ArrayList<>();

        for (int i = 0; i < list.size() - 3; i++) {
            CardSet cardSet1 = list.get(i);
            CardSet cardSet2 = list.get(i + 1);
            CardSet cardSet3 = list.get(i + 2);
            CardSet cardSet4 = list.get(i + 3);

            if (equalsGames(new String[]{cardSet1.getCardByPos(3), cardSet2.getCardByPos(2),
                            cardSet3.getCardByPos(1), cardSet4.getCardByPos(0)}, selectCards,
                    dates, cardSet1.getDateString(), cardSet2.getDateString(),
                    cardSet3.getDateString(), cardSet4.getDateString())) {

                if (i == 0) {

                    CardSet cardSet5 = list.get(i + 4);

                    listGroup.add(new CardGroup(toLeftDiagonal(null, cardSet1, cardSet2,
                            cardSet3), toLeftDiagonal(cardSet2, cardSet3, cardSet4, cardSet5)));
                } else if (i == list.size() - 3) {
                    CardSet cardSet0 = list.get(i - 1);

                    listGroup.add(new CardGroup(toLeftDiagonal(cardSet0, cardSet1, cardSet2,
                            cardSet3), toLeftDiagonal(cardSet2, cardSet3, cardSet4,
                            null)));
                } else {
                    CardSet cardSet0 = list.get(i - 1);
                    CardSet cardSet5 = list.get(i + 4);

                    listGroup.add(new CardGroup(toLeftDiagonal(cardSet0, cardSet1, cardSet2,
                            cardSet3), toLeftDiagonal(cardSet2, cardSet3, cardSet4, cardSet5)));
                }
            }
        }

        return listGroup;
    }

    private static boolean equalsGames(String[] cardsFromFile,
                                       String[] cards, String[] dates,
                                       String... cardDates) {

        if (dates != null) {
            if (!equalsDates(dates, cardDates)) {
                return false;
            }
        }
        return equalsCard(cardsFromFile, cards);
    }

    private static boolean equalsCard(String[] cardFromFile,
                                      String[] cards) {

        for (int i = 0; i < 4; i++) {
            if (!cardFromFile[i].equals(cards[i])) {
                return false;
            }
        }
        return true;
    }

    private static CardSet toLeft(CardSet set) {
        return new CardSet(set.getDateString(), set.getNumber(), set.getCard4(),
                set.getCard3(), set.getCard2(), set.getCard1());
    }

    private static CardSet toTop(CardSet set1, CardSet set2, CardSet set3, CardSet set4, int pos) {
        return new CardSet(set1.getDateString(), set1.getNumber(), set4.getCardByPos(pos),
                set3.getCardByPos(pos), set2.getCardByPos(pos), set1.getCardByPos(pos));
    }

    private static CardSet toBottom(CardSet set1, CardSet set2, CardSet set3, CardSet set4,
                                    int pos) {
        return new CardSet(set1.getDateString(), set1.getNumber(), set1.getCardByPos(pos),
                set2.getCardByPos(pos), set3.getCardByPos(pos), set4.getCardByPos(pos));
    }

    private static CardSet toRightDiagonal(CardSet set1, CardSet set2, CardSet set3, CardSet set4) {
        return new CardSet(set2.getDateString(), set2.getNumber(),
                set1 == null ? "-" : set1.getCardByPos(0),
                set2.getCardByPos(1),
                set3.getCardByPos(2),
                set4 == null ? "-" : set4.getCardByPos(3)
        );
    }

    private static CardSet toLeftDiagonal(CardSet set1, CardSet set2, CardSet set3, CardSet set4) {
        return new CardSet(set2.getDateString(), set2.getNumber(),
                set1 == null ? "-" : set1.getCardByPos(3),
                set2.getCardByPos(2),
                set3.getCardByPos(1),
                set4 == null ? "-" : set4.getCardByPos(0)
        );
    }

    private void outputData(List<CardGroup> cardGroups) {
        ((MainActivityThreeFour) view.getContext()).runOnUiThread(() -> {
            view.setCardGroupListToRecycler(cardGroups != null ? cardGroups : new ArrayList<>());
            view.changeVisibleBlockWait(false);
        });
    }
}
