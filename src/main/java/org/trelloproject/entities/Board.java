package org.trelloproject.entities;

public class Board {
    public String id;
    String name;
    Boolean status;
    String idMemberCreator;

    public Board(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Boolean getStatus() {
        return status;
    }

    public String getIdMemberCreator() {
        return idMemberCreator;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public void setIdMemberCreator(String idMemberCreator) {
        this.idMemberCreator = idMemberCreator;
    }
}

