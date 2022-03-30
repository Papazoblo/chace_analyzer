package alex.studio.csvsearcher.ui.main_activity.presenter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import alex.studio.csvsearcher.dto.CardSet;
import alex.studio.csvsearcher.functions.Consumer;
import alex.studio.csvsearcher.ui.main_activity.view.MainActivityTwo;

public class MainPresenterTwo extends MainPresenter {

    private static final String[] cardLine = new String[]{"7", "8", "9", "10", "J", "Q", "K", "A"};
    private boolean[] options;
    private boolean plusOne;

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
        options = ((MainActivityTwo) view).getOptions();
        plusOne = ((MainActivityTwo) view).isEnabledPlusOne();
        List<String[]> pairs = generatePairs(view.getCards());
        List<List<Integer>> pairsResult = new ArrayList<>();
        for (int i = 0; i < options.length; i++) {
            if (options[i]) {
                pairsResult.add(processPair(pairs.get(i)));
                continue;
            }
            pairsResult.add(null);
        }
        printOutputData(preparationToPrint(pairsResult));
    }

    private List<Integer> processPair(String[] pair) {
        int start = getPosition(pair[0]);
        int end = getPosition(pair[1]);
        int resultIn = findIn(start, end);
        int resultOut = findOut(start, end);
        return Arrays.asList(resultIn, resultOut, plusOneResult(resultIn),
                plusOneResult(resultOut));

    }

    private static int findIn(int start, int end) {
        if (start == end) {
            return start;
        } else if (Math.abs(start - end) == 1) {
            return 1;
        } else if (start > end) {
            return start - end - 1;
        } else {
            return end - start - 1;
        }
    }

    private static int findOut(int start, int end) {
        if (start == end) {
            return 7;
        } else if (Math.abs(start - end) == 1) {
            return 6;
        } else if (Math.abs(start - end) == 7) {
            return 1;
        } else if (start < end) {
            return start + cardLine.length - end - 1;
        } else {
            return end + cardLine.length - start - 1;
        }
    }

    private List<CardSet> preparationToPrint(List<List<Integer>> resultPairs) {
        ArrayList<CardSet> cardSetList = new ArrayList<>(Arrays.asList(
                new CardSet("#", "IN"),
                new CardSet("#", "OUT"),
                new CardSet("#", "IN+1"),
                new CardSet("#", "OUT+1")));
        for (int i = 0; i < resultPairs.size(); i++) {
            List<Integer> pair = resultPairs.get(i);
            if (pair == null) {
                continue;
            }
            for (int j = 0; j < pair.size(); j++) {
                cardSetList.get(j).setCardByPos(i, cardLine[pair.get(j) - 1]);
            }
        }
        if (!plusOne) {
            cardSetList.remove(3);
            cardSetList.remove(2);
        }
        return cardSetList;
    }

    private static Integer plusOneResult(int val) {
        return val == cardLine.length ? 1 : (val + 1);
    }

    private static int getPosition(String card) {
        for (int i = 0; i < cardLine.length; i++) {
            if (card.equals(cardLine[i])) {
                return i + 1;
            }
        }
        return 1;
    }

    private static List<String[]> generatePairs(String[] cards) {
        return Arrays.asList(new String[]{cards[0], cards[4]},
                new String[]{cards[1], cards[5]},
                new String[]{cards[2], cards[6]},
                new String[]{cards[3], cards[7]});
    }

    private void printOutputData(List<CardSet> result) {
        ((MainActivityTwo) view.getContext()).runOnUiThread(() -> {
            view.setCardSetListToRecycler(result);
            view.changeVisibleBlockWait(false);
        });
    }
}
