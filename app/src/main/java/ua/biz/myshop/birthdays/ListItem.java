package ua.biz.myshop.birthdays;

public class ListItem {

    private String name;
    private String mobile;
    private String email;
    private String birthday;
    private String photo;

    public String getUrl() {
        return photo;
    }

    public void setUrl(String photo) {
        this.photo = photo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @Override
    public String toString() {
        return "[ name=" + name + ", mobile=" + mobile + " , birthday=" + birthday + "]";
    }
}