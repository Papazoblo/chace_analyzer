package alex.studio.csvsearcher.dto;

public class MatcherPosition {

    int[] positions = new int[]{0, 1, 2, 3};
    boolean[] matched = new boolean[]{false, false, false, false};

    public int[] getPositions() {
        return this.positions;
    }

    public boolean[] getMatched() {
        return this.matched;
    }
}
