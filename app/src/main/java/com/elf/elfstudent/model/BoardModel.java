package com.elf.elfstudent.model;

/**
 * Created by Nandha on 9/21/2016.
 *
 * A custom class which holds spinner object
 *
 *  because we need to send  Id{@param  boardId} and not text{@param name} to webservice
 */

public class BoardModel {
    public String name;

    //the ID to send to server during resgistration
    public String boardId;

    public BoardModel(String name, String boardId) {
        this.name = name;
        this.boardId = boardId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBoardId() {
        return boardId;
    }

    public void setBoardId(String boardId) {
        this.boardId = boardId;
    }

    @Override
    public String toString() {
        return name;
    }
}
