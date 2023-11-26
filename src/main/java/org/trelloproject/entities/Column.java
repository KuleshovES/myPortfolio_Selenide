package org.trelloproject.entities;

import com.google.gson.annotations.SerializedName;

public class Column {
    public String id;
    public String name;
    @SerializedName("closed")
    public Boolean closed;
    public String idBoard;

    public Column(String name, Board board) {
        this.name = name;
        this.idBoard = board.getId();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Boolean getClosed() {
        return closed;
    }

    public String getIdBoard() {
        return idBoard;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setClosed(Boolean closed) {
        this.closed = closed;
    }

    public void setIdBoard(String idBoard) {
        this.idBoard = idBoard;
    }
}
