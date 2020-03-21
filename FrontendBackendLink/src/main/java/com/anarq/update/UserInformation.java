package com.anarq.update;

import java.util.Objects;

/**
 * A user's information for the AnarQ application.
 *
 * @author Logan Kulinski, lbk@purdue.edu
 * @version March 8, 2020
 */
public final class UserInformation {
    /**
     * The user type of this user information.
     */
    private UserType userType;

    /**
     * The username of this user information.
     */
    private String username;

    /**
     * The password of this user information.
     */
    private String password;

    /**
     * The new value of this user information.
     */
    private String newValue;

    /**
     * Constructs a newly allocated {@code UserInformation} object with a default user type, username, password, and
     * new value of {@code null}.
     */
    public UserInformation() {
        this.userType = null;
        this.username = null;
        this.password = null;
        this.newValue = null;
    } //UserInformation

    /**
     * Returns the user type of this user information.
     *
     * @return the user type of this user information
     */
    public UserType getUserType() {
        return this.userType;
    } //getUserType

    /**
     * Returns the username of this user information.
     *
     * @return the username of this user information
     */
    public String getUsername() {
        return this.username;
    } //getUsername

    /**
     * Returns the password of this user information.
     *
     * @return the password of this user information
     */
    public String getPassword() {
        return this.password;
    } //getPassword

    /**
     * Returns the new value of this user information.
     *
     * @return the new value of this user information
     */
    public String getNewValue() {
        return this.newValue;
    } //getNewUsername

    /**
     * Updates the user type of this user information with the specified user type.
     *
     * @param userType the user type to be used in the update
     */
    public void setUserType(UserType userType) {
        this.userType = userType;
    } //setUserType

    /**
     * Updates the username of this user information with the specified username.
     *
     * @param username the username to be used in the update
     */
    public void setUsername(String username) {
        this.username = username;
    } //setUsername

    /**
     * Updates the password of this user information with the specified password.
     *
     * @param password the password to be used in the update
     */
    public void setPassword(String password) {
        this.password = password;
    } //setPassword

    /**
     * Updates the new value of this user information with the specified new value.
     *
     * @param newValue the new value to be used in the update
     */
    public void setNewValue(String newValue) {
        this.newValue = newValue;
    } //setNewUsername

    /**
     * Returns the hash code of this user information.
     *
     * @return the hash code of this user information
     */
    @Override
    public int hashCode() {
        int result = 23;
        int prime = 31;

        result = prime * result + Objects.hashCode(this.userType);

        result = prime * result + Objects.hashCode(this.username);

        result = prime * result + Objects.hashCode(this.password);

        result = prime * result + Objects.hashCode(this.newValue);

        return result;
    } //hashCode

    /**
     * Determines whether or not the specified object is equal to this user information. {@code true} is returned if
     * and only if the specified object is an instance of {@code UserInformation} and its user type, username,
     * password, and new value are equal to this user information's. Username, password, and new value comparisons are
     * case-sensitive.
     *
     * @param object the object to be used in the comparisons
     * @return {@code true}, if the specified object is equal to this user information and {@code false} otherwise
     */
    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        } else if (object instanceof UserInformation) {
            boolean equal;

            equal = Objects.equals(this.userType, ((UserInformation) object).userType);

            equal &= Objects.equals(this.username, ((UserInformation) object).username);

            equal &= Objects.equals(this.password, ((UserInformation) object).password);

            equal &= Objects.equals(this.newValue, ((UserInformation) object).newValue);

            return equal;
        } else {
            return false;
        } //end if
    } //equals

    /**
     * Returns the {@code String} representation of this user information. The returned {@code String} consists of a
     * comma separated list of this user information's user type, username, password, and new value surrounded by this
     * class' name and square brackets ("[]").
     *
     * @return the {@code String} representation of this user information
     */
    @Override
    public String toString() {
        String format = "UserInformation[%s, %s, %s, %s]";

        return String.format(format, this.userType, this.username, this.password, this.newValue);
    } //toString
}