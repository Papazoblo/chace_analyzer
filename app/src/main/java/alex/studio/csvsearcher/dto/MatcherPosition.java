package alex.studio.csvsearcher.dto;

public class MatcherPosition {

    int[] positions = new int[]{3, 2, 1, 0};
    boolean[] matched = new boolean[]{false, false, false, false};

    public int[] getPositions() {
        return this.positions;
    }

    public boolean[] getMatched() {
        return this.matched;
    }

    public void resetPositions() {
        this.positions = new int[]{3, 2, 1, 0};
    }

    public void reverseMatch() {
        boolean tmp = matched[0];
        matched[0] = matched[3];
        matched[3] = tmp;
        tmp = matched[1];
        matched[1] = matched[2];
        matched[2] = tmp;
    }
}
