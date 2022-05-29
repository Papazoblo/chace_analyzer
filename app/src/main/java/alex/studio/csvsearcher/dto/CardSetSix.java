package alex.studio.csvsearcher.dto;

import java.util.Set;
import java.util.TreeSet;

public class CardSetSix extends CardSet {

    private boolean visible = false;

    private TreeSet<Long> gamesNumbers = new TreeSet<>((o1, o2) -> o2.compareTo(o1));

    public int getCount() {
        return gamesNumbers.size();
    }

    public void addGameNumber(Long number) {
        gamesNumbers.add(number);
    }

    public Set<Long> getGamesNumber() {
        return gamesNumbers;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean flag) {
        this.visible = flag;
    }
}
