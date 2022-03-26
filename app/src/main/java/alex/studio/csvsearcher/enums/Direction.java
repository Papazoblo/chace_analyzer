package alex.studio.csvsearcher.enums;

public enum Direction {
    TOP("top"),
    BOTTOM("bottom"),
    LEFT("left"),
    RIGHT("right"),
    TOP_LEFT("topLeft"),
    TOP_RIGHT("topRight"),
    BOTTOM_LEFT("bottomLeft"),
    BOTTOM_RIGHT("bottomRight"),
    FULL("full");

    Direction(String direction) {
        this.direction = direction;
    }

    private final String direction;

    public String getDirection() {
        return direction;
    }

    public static Direction of(String direction) {
        for(Direction dir : Direction.values()) {
            if (dir.getDirection().equals(direction)) {
                return dir;
            }
        }
        return null;
    }
}
