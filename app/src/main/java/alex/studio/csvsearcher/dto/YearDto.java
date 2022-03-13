package alex.studio.csvsearcher.dto;

public class YearDto {

    private int year;
    private boolean mark;

    public YearDto(int year) {
        this.year = year;
        this.mark = false;
    }

    public String getYear() {
        return year + "";
    }

    public boolean isMark() {
        return mark;
    }

    public void changeMark() {
        mark = !mark;
    }

    public void setMark(boolean val) {
        mark = val;
    }
}
