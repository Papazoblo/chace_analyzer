package alex.studio.csvsearcher.dto;

public class ColorMatch {

    private int color;
    private boolean intersection;

    public ColorMatch(int color) {
        this.color = color;
        this.intersection = false;
    }

    public int getColor() {
        return color;
    }

    public boolean isIntersection() {
        return intersection;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public void setIntersection(boolean intersection) {
        this.intersection = intersection;
    }
}
