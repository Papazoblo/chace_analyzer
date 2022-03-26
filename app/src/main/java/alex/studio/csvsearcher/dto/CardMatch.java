package alex.studio.csvsearcher.dto;

import alex.studio.csvsearcher.enums.TypeMatch;

public class CardMatch {

    private int count;
    private CardSet set;
    private CardSet prevSet;
    private TypeMatch type;
    private int year;
    private MatcherPosition matcherPosition;

    public CardMatch(int count, CardSet set, CardSet prevSet, TypeMatch type,
                     MatcherPosition matcherPosition) {
        this.count = count;
        this.set = set;
        this.prevSet = prevSet;
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

    public TypeMatch getType() {
        return type;
    }

    public int getYear() {
        return year;
    }

    public MatcherPosition getMatcherPosition() {
        return this.matcherPosition;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
