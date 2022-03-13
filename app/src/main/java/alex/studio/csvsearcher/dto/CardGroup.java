package alex.studio.csvsearcher.dto;

public class CardGroup {

    private CardSet cardSetTop;
    private CardSet cardSetBottom;

    public CardGroup(CardSet cardSet1, CardSet cardSet2) {
        cardSetTop = cardSet1;
        cardSetBottom = cardSet2;
    }

    public CardSet getCardSetTop() {
        return cardSetTop;
    }

    public CardSet getCardSetBottom() {
        return cardSetBottom;
    }
}
