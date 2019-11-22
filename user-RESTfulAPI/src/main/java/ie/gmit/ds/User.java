package ie.gmit.ds;

import com.fasterxml.jackson.annotation.JsonProperty;

public class User {

    int userID;
    String userName;
    String email;
    String password;

    public User(){}
    public User(int userID, String userName, String email, String password) {
        this.userID = userID;
        this.userName = userName;
        this.email = email;
        this.password = password;
    }

    @JsonProperty
    public int getUserID() {
        return userID;
    }

    @JsonProperty
    public String getUserName() {
        return userName;
    }

    @JsonProperty
    public String getEmail() {
        return email;
    }

    @JsonProperty
    public String getPassword() {
        return password;
    }
}