package org.trelloproject.entities;

import com.google.gson.annotations.SerializedName;

public class Card {

    public String id;
    public String name;
    public String idList;
    public String idBoard;
    @SerializedName("closed")
    public Boolean isClosed;

    public Card(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getIdList() {
        return idList;
    }

    public String getIdBoard() {
        return idBoard;
    }

    public Boolean getClosed() {
        return isClosed;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIdList(String idList) {
        this.idList = idList;
    }

    public void setIdBoard(String idBoard) {
        this.idBoard = idBoard;
    }

    public void setClosed(Boolean closed) {
        isClosed = closed;
    }
}


