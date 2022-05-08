package alex.studio.csvsearcher.dto;

import java.util.Calendar;

import alex.studio.csvsearcher.enums.TypeMatch;

public class CardMatch {

    private int count;
    private CardSet set;
    private CardSet prevSet;
    private CardSet nextSet;
    private TypeMatch type;
    private int year;
    private MatcherPosition matcherPosition;
    private int month;

    public CardMatch(int count, CardSet set, CardSet prevSet, CardSet nextSet, TypeMatch type,
                     MatcherPosition matcherPosition) {
        this.count = count;
        this.set = set;
        this.prevSet = prevSet;
        this.nextSet = nextSet;
        this.type = type;
        this.matcherPosition = matcherPosition;
    }

    public int getCount() {
        return count;
    }

    public CardSet getSet() {
        return set;
    }

    public CardSet getPrevSet() {
        return prevSet;
    }

    public CardSet getNextSet() {
        return nextSet;
    }

    public TypeMatch getType() {
        return type;
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        Calendar c = Calendar.getInstance();
        c.setTime(set.getDate());
        return c.get(Calendar.MONTH);
    }

    public MatcherPosition getMatcherPosition() {
        return this.matcherPosition;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
