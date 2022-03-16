package alex.studio.csvsearcher.enums;

public enum CardPosition {
    ONE("one"),
    TWO("two"),
    THREE("three"),
    FOUR("four");

    CardPosition(String pos) {
        this.position = pos;
    }

    private final String position;

    public String getPosition() {
        return position;
    }

    public static CardPosition of(String pos) {
        for(CardPosition dir : CardPosition.values()) {
            if (dir.getPosition().equals(pos)) {
                return dir;
            }
        }
        return null;
    }
}
