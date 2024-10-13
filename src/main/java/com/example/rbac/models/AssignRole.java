package com.example.rbac.models;

public class AssignRole {

    String adminUser;
    String user;
    String roles;  //"X-admin;Y-read"

    String actions;  // "add;delete"



    public AssignRole(String adminUser, String user, String roles, String actions) {
        this.adminUser = adminUser;
        this.user = user;
        this.roles = roles;
        this.actions = actions;
    }

    public String getAdminUser() {
        return adminUser;
    }

    public void setAdminUser(String adminUser) {
        this.adminUser = adminUser;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public String getActions() {
        return actions;
    }

    public void setActions(String actions) {
        this.actions = actions;
    }
}
