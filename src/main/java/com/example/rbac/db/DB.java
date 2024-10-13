package com.example.rbac.db;

import com.example.rbac.models.StructureRole;
import com.example.rbac.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

@Service
public class DB {

    Map<String, User> mapUsers = new HashMap<>();
    Map<String, StructureRole> mapRoles = new HashMap<>();

    @Autowired
    private PasswordEncoder passwordEncoder;

    public DB() {

        List<StructureRole> listRole = new ArrayList<>();
        listRole.add(new StructureRole("Global-admin", "", "all"));
            System.out.println("global admin added");
            User globalAdmin = new User("root", "$2a$10$v6gIDvrUIKlKXwZ5PrpZO.3wVF5n8NsCbeSYJWF2ETFHz8CdBElDK",
                    "root@email.com", new ArrayList<>(), true);
            globalAdmin.setRoles(listRole);

            mapUsers.put("root", globalAdmin);
            mapRoles.put("Global-admin", listRole.get(0));

    }

    public boolean usernameAlreadyExists(String username){
        if(mapUsers.get(username) == null){
            return false;
        }
        else{
            return true;
        }
    }



    public boolean saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        mapUsers.put(user.getUsername(), user);
        return true;
    }

    public boolean saveRole(StructureRole role) {
        mapRoles.put(role.getName(), role);
        return true;
    }

    public Optional<User> getUser(String username){
        return Optional.ofNullable(mapUsers.get(username));
    }

    public StructureRole getStructureRole(String roleName){
        return mapRoles.get(roleName);
    }


    public List<User> getAllUsers() {
        return mapUsers.values().stream().toList();
    }


    public boolean roleAlreadyExists(String roleName) {
        if(mapRoles.get(roleName) == null){
            return false;
        }
        else{
            return true;
        }
    }


    public List<StructureRole> getAllRoles() {
        return mapRoles.values().stream().toList();
    }
}
