package alex.studio.csvsearcher.dto;

import static alex.studio.csvsearcher.utils.DateUtils.toDate;

import java.util.Calendar;
import java.util.Date;

public class CardSet {

    private final int DATE = 0;
    private final int NUMBER = 1;
    private final int CARD_1 = 5;
    private final int CARD_2 = 4;
    private final int CARD_3 = 3;
    private final int CARD_4 = 2;

    private String date;
    private String number;
    private String card1;
    private String card2;
    private String card3;
    private String card4;

    public CardSet(String date, String number, String card1, String card2, String card3,
                   String card4) {
        this.date = date;
        this.number = number;
        this.card1 = card1;
        this.card2 = card2;
        this.card3 = card3;
        this.card4 = card4;
    }

    public CardSet() {
        this("-", "");
    }

    public CardSet(String emptySymbol, String number) {
        date = "";
        this.number = number;
        card1 = emptySymbol;
        card2 = emptySymbol;
        card3 = emptySymbol;
        card4 = emptySymbol;
    }

    public CardSet(String[] infoArray) {
        date = infoArray[DATE].replace("/", ".");
        number = infoArray[NUMBER];
        card1 = infoArray[CARD_1];
        card2 = infoArray[CARD_2];
        card3 = infoArray[CARD_3];
        card4 = infoArray[CARD_4];
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getDateString() {
        return date;
    }

    public Date getDate() {
        return toDate(this.date);
    }

    public long getYear() {
        Calendar c = Calendar.getInstance();
        c.setTime(toDate(this.date));
        return c.get(Calendar.YEAR);
    }

    public String getNumber() {
        return number;
    }

    public String getCard1() {
        return card1;
    }

    public String getCard2() {
        return card2;
    }

    public String getCard3() {
        return card3;
    }

    public String getCard4() {
        return card4;
    }

    public String[] getCards() {
        return new String[]{card1, card2, card3, card4};
    }

    public String getCardByPos(int pos) {
        switch (pos) {
            case 0:
                return getCard1();
            case 1:
                return getCard2();
            case 2:
                return getCard3();
            case 3:
                return getCard4();
            default:
                return "";
        }
    }

    public void setCardByPos(int pos, String val) {
        switch (pos) {
            case 0:
                card1 = val;
                break;
            case 1:
                card2 = val;
                break;
            case 2:
                card3 = val;
                break;
            case 3:
                card4 = val;
                break;
        }
    }

    @Override
    public String toString() {
        return card1 + " " + card2 + " " + card3 + " " + card4;
    }

    public String toString(boolean isReverse, boolean withSpace) {
        String result = isReverse ? card4 + " " + card3 + " " + card2 + " " + card1 : toString();
        return withSpace ? result : result.replace(" ", "");
    }

    public void stringToCardSet(String str) {
        String[] array = str.split(" ");

        if (array.length == 4) {
            for (int i = 0; i < array.length; i++) {
                setCardByPos(i, array[i]);
            }
        }
    }
}
