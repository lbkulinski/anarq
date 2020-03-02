package com.anarq.update;

import java.util.Objects;

public final class UserInformation {
    private String username;
    private String password;
    private String newValue;

    public UserInformation() {
        this.username = null;
        this.password = null;
        this.newValue = null;
    } //UserInformation

    public String getUsername() {
        return this.username;
    } //getUsername

    public String getPassword() {
        return this.password;
    } //getPassword

    public String getNewValue() {
        return this.newValue;
    } //getNewUsername

    public void setUsername(String username) {
        this.username = username;
    } //setUsername

    public void setPassword(String password) {
        this.password = password;
    } //setPassword

    public void setNewValue(String newValue) {
        this.newValue = newValue;
    } //setNewUsername

    @Override
    public int hashCode() {
        int result = 23;
        int prime = 31;

        result = prime * result + Objects.hashCode(this.username);

        result = prime * result + Objects.hashCode(this.password);

        result = prime * result + Objects.hashCode(this.newValue);

        return result;
    } //hashCode

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        } else if (object instanceof UserInformation) {
            boolean equal;

            equal = Objects.equals(this.username, ((UserInformation) object).username);

            equal &= Objects.equals(this.password, ((UserInformation) object).password);

            equal &= Objects.equals(this.newValue, ((UserInformation) object).newValue);

            return equal;
        } else {
            return false;
        } //end if
    } //equals

    @Override
    public String toString() {
        String format = "UserInformation[%s, %s, %s]";

        return String.format(format, this.username, this.password, this.newValue);
    } //toString
}