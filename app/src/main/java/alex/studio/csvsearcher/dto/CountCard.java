package alex.studio.csvsearcher.dto;

public class CountCard {

    private String name;
    private int count;

    public CountCard(String name, int count) {
        this.name = name;
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public int getCount() {
        return count;
    }
}
