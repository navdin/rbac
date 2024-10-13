package com.example.rbac.models;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class User {
    public User(String username, String password, String email, List<StructureRole> roles, boolean isGlobalAdmin) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.roles = roles;
        for(StructureRole role : roles){
            setRoleIds.add(role.name);
        }
        this.isGlobalAdmin = isGlobalAdmin;
    }

    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    String username;
    String password;
    String email;

    boolean isGlobalAdmin;
    List<StructureRole> roles = new ArrayList<>();

    Set<String> setRoleIds = new HashSet<>();

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<StructureRole> getRoles() {
        return roles;
    }


    public void setRoles(List<StructureRole> roles) {
        this.roles = roles;
        for(StructureRole role : roles){
            setRoleIds.add(role.name);
        }
    }

    public void addRole(StructureRole role){

        this.roles.add(role);
        this.setRoleIds.add(role.name);
    }

    public void removeRole(StructureRole role){
        this.roles.remove(role);
        this.setRoleIds.remove(role.name);
    }

    public Set<String> getSetRoleIds() {
        return setRoleIds;
    }

    public boolean isGlobalAdmin() {
        return isGlobalAdmin;
    }

    public void setGlobalAdmin(boolean globalAdmin) {
        isGlobalAdmin = globalAdmin;
    }
}
