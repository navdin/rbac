package com.example.rbac.services.impl;

import com.example.rbac.db.DB;
import com.example.rbac.exception.custom.NotAuthorizedException;
import com.example.rbac.exception.custom.RoleAlreadyExistsException;
import com.example.rbac.exception.custom.RoleNotFoundException;
import com.example.rbac.exception.custom.UsernameAlreadyExistsException;
import com.example.rbac.models.AssignRole;
import com.example.rbac.models.StructureRole;
import com.example.rbac.models.User;
import com.example.rbac.services.ISecurity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SecurityImpl implements ISecurity {

    @Autowired
    DB db;

    @Override
    public boolean signup(User user) {
        if(db.usernameAlreadyExists(user.getUsername())){
            throw new UsernameAlreadyExistsException("Username already exists. Please use a different one.");
        }
        db.saveUser(user);
        return true;
    }

    public boolean createRole(StructureRole role) {
        if(db.roleAlreadyExists(role.getName())){
            throw new RoleAlreadyExistsException("Role already exists. Please create a different one.");
        }
        if(role.getName().contains("admin")){
            StructureRole newRole = new StructureRole(role.getName().split("-")[0]+"-read","","read access");
            db.saveRole(newRole);
            db.getUser("root").get().addRole(newRole);

        }
        db.saveRole(role);
        db.getUser("root").get().addRole(role);
        return true;

    }

    @Override
    public List<StructureRole> getAllRoles() {
        return db.getAllRoles();
    }

    @Override
    public boolean signin(User user) {
        return false;
    }

    @Override
    public boolean assignRoles(AssignRole assignRole) {

        if(db.getUser(assignRole.getUser()).isEmpty()){
            throw new UsernameNotFoundException("User "+assignRole.getUser() + " not found.");
        }
        String[] rolesReq = assignRole.getRoles().split(";");
        for(String role : rolesReq){
            if(db.getStructureRole(role) == null){
                throw new RoleNotFoundException("Role "+role+" not found. First create this role.");
            }
        }
        String[] actionsReq = assignRole.getActions().split(";");
        User adminUser = db.getUser(assignRole.getAdminUser()).get();
        User assignee = db.getUser(assignRole.getUser()).get();



        int counter = -1;
        for(String role : rolesReq){
            counter++;

            if(adminUser.isGlobalAdmin()) {
                assignRoleAndReset(true, assignee, role, actionsReq[counter]);
                continue;
            }

            String[] roleParts = role.split("-");
            if(roleParts[1].equals("admin")){
                if(actionsReq[counter].equals("delete")){
                    throw new NotAuthorizedException(adminUser.getUsername() + " cannot delete ADMIN access of "+assignee.getUsername()+" for structure "+roleParts[0]
                            +adminUser.getUsername()+" should be GLOBAL-ADMIN");
                }
                throw new NotAuthorizedException(adminUser.getUsername() + " cannot give ADMIN access to "+assignee.getUsername()+" for structure "+roleParts[0]
                        +adminUser.getUsername()+" should be GLOBAL-ADMIN");
            }
            else if(roleParts[1].equals("read")){
                if(adminUser.getSetRoleIds().contains(roleParts[0]+"-admin")){
                    if(assignee.getSetRoleIds().contains(roleParts[0]+"-admin")){
                        throw new NotAuthorizedException(adminUser.getUsername() + "cannot change access of "+assignee.getUsername()+" for structure "+roleParts[0]+
                                ". "+assignee.getUsername()+" has ADMIN access for structure "+roleParts[0]);
                    }
                    assignRoleAndReset(false, assignee, role, actionsReq[counter]);
                }
                else{
                    if(actionsReq[counter].equals("delete")){
                        throw new NotAuthorizedException(adminUser.getUsername() + "cannot delete READ access of "+assignee.getUsername()+" for structure "+roleParts[0]+
                                ". "+adminUser.getUsername()+" should have ADMIN access for structure "+roleParts[0]);
                    }
                    throw new NotAuthorizedException(adminUser.getUsername() + "cannot give READ access to "+assignee.getUsername()+" for structure "+roleParts[0]+
                            ". "+adminUser.getUsername()+" should have ADMIN access for structure "+roleParts[0]);
                }
            }
        }
        return true;
    }

    @Override
    public List<User> getAllUsers() {
        return db.getAllUsers();
    }



    private void assignRoleAndReset(boolean isGlobalAdmin, User assignee, String  roleName, String action){
        String[] roleParts = roleName.split("-");
        if(action.equals("delete")){
            assignee.removeRole(db.getStructureRole(roleName));

            if(isGlobalAdmin){
                if(roleName.contains("read")){
                    assignee.removeRole(db.getStructureRole(roleParts[0]+"-"+"admin"));
                }
            }
            return;
        }

        if(roleParts[1].equals("admin")){
            if(assignee.getSetRoleIds().contains(roleName)){
                return;
            }
            assignee.addRole(db.getStructureRole(roleName));
            if(roleName.contains("Global")){
                return;
            }
            if(!assignee.getSetRoleIds().contains(roleParts[0]+"-"+"read")){
                assignee.addRole(db.getStructureRole(roleParts[0]+"-"+"read"));
            }
        }
        else if(roleParts[1].equals("read")){
            if(db.getStructureRole(roleParts[0]+"-"+"admin")!=null){
                assignee.removeRole(db.getStructureRole(roleParts[0]+"-"+"admin"));
            }
            if(assignee.getSetRoleIds().contains(roleName)){
                return;
            }
            assignee.addRole(db.getStructureRole(roleName));
        }
    }
}
