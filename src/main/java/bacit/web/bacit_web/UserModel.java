package bacit.web.bacit_web;

public class UserModel {
    private String fullName;
    private String phoneNumber;

    public UserModel(String fullName, String phoneNumber) {
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getUser() {
        return "User with fullname: "+getFullName()+", and phone number: "
                +getPhoneNumber()+".";
    }
}
