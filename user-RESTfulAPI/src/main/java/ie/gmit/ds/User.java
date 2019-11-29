package ie.gmit.ds;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.protobuf.ByteString;

import javax.validation.constraints.NotNull;

public class User {

    //User Account credentials
    @NotNull
    int userID;
    @NotNull
    String userName;
    @NotNull
    String email;
    @NotNull
    String password;

    byte[] hashedPassword;
    byte[] salt;

    public User(){}

    // Add New Account
    public User(int userID, String userName, String email, String password, byte[] hash, byte[] sa) {
        this.userID = userID;
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.hashedPassword = hash;
        this.salt = sa;
    }

    // login user
    public User(String email, String password) {
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

    @JsonProperty
    public byte[] getHashedPassword() {
        return hashedPassword;
    }

    @JsonProperty
    public byte[] getSalt() {
        return salt;
    }

    public void setHashedPassword(byte[] hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    public void setSalt(byte[] salt) {
        this.salt = salt;
    }
}