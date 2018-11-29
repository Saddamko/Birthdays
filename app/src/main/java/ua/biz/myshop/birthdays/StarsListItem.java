package ua.biz.myshop.birthdays;

public class StarsListItem {

    private String canonical;
    private String birthday;
    private String band;


    public String getBand() {
        return band;
    }

    public void setBand(String band) {
        this.band = band;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getCanonical() {
        return canonical;
    }

    public void setCanonical(String canonical) {
        this.canonical = canonical;
    }


    @Override
    public String toString() {
        return "[ canonical=" + canonical + ", band=" + band + " , birthday=" + birthday + "]";
    }
}