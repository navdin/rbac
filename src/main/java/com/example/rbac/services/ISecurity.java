package com.example.rbac.services;

import com.example.rbac.models.AssignRole;
import com.example.rbac.models.StructureRole;
import com.example.rbac.models.User;

import java.util.List;

public interface ISecurity {

    boolean signup(User user);
    boolean signin(User user);
    boolean assignRoles(AssignRole assignRole);

    List<User> getAllUsers();

    boolean createRole(StructureRole role);

    List<StructureRole> getAllRoles();
}
