package org.trelloproject.entities;

import java.util.List;

public class User {
    private String id;
    private String fullName;
    private String username;
    private String email;
    private List<String> idBoards;
    private List<String> idOrganizations;

    public String getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public List<String> getIdBoards() {
        return idBoards;
    }

    public List<String> getIdOrganizations() {
        return idOrganizations;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setIdBoards(List<String> idBoards) {
        this.idBoards = idBoards;
    }

    public void setIdOrganizations(List<String> idOrganizations) {
        this.idOrganizations = idOrganizations;
    }
}
