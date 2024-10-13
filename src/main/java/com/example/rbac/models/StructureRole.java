package com.example.rbac.models;

import java.util.HashMap;
import java.util.Map;

public class StructureRole {
    String name; // "X-read" or "XY-admin"
    String desc;
    String permissions;    // "X-read"  or  "X-admin;Y-admin"

   // Map<String, String> mapPermissions = new HashMap<>();  // structure-permission  X:admin,Y:admin

    public StructureRole(String name, String desc, String permissions) {
        this.name = name;
        this.desc = desc;
        this.permissions = permissions;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPermissions() {
        return permissions;
    }

    public void setPermissions(String permissions) {
        this.permissions = permissions;
    }
}
