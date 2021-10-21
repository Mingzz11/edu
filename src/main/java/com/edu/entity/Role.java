package com.edu.entity;

import java.io.Serializable;
import java.util.List;

public class Role implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer roleId;
    private String roleName;
    private List<Permission> permissions;//权限集合

    public List<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}