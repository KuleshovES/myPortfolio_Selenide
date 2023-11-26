package org.trelloproject.robots;

import org.trelloproject.entities.Board;
import org.trelloproject.pages.BoardPage;
import org.trelloproject.robots.api.RestApiRobot;


public class BoardRobot {

    //preconditions with board
    public Board createFullBoardByRest(String boardName, String columnName, String firstTaskName, String secondTaskName) {
        return RestApiRobot.createFullBoard(boardName, columnName, firstTaskName, secondTaskName);
    }

    public Board createEmptyBoardByRest(String boardName) {
        return RestApiRobot.createEmptyBoard(boardName);
    }

    public void closedBoardByRest(String boardId) {
        RestApiRobot.closedBoard(boardId);
    }

    //cards actions
    public void createCardByUI(Board board, String cardName) throws InterruptedException {
        new BoardPage()
                .openBoard(board.getName())
                .createCard(cardName);
    }

    public void copyCardByUI(Board board, String newNameCopiedCard) {
        new BoardPage()
                .openBoard(board.getName())
                .copyCard(newNameCopiedCard);
    }

    public void updateCardByUI(Board board, String newCardName) {
        new BoardPage()
                .openBoard(board.getName())
                .updateCardName(newCardName);
    }

    public String getFirstCardUI() {
        return new BoardPage().getActualCardNameInActiveBoard();
    }

    public String getNameBoardUI() {
        return new BoardPage().getBoardNameUI();
    }

    public void deleteCardByUI(Board board, String deletedCardName) {
        new BoardPage()
                .openBoard(board.getName())
                .deleteCard(deletedCardName);
    }

    public void movedCardByUI(Board board, String cardName) {
        new BoardPage()
                .openBoard(board.getName())
                .moveCardByDrag(cardName);
    }

    public void addLabelToCardByUI (Board board, String cardName, String color) {
        new BoardPage()
                .openBoard(board.getName())
                .addLabelToCard(cardName, color);
    }

    public void filterCardByUI (Board board, String filterText) {
        new BoardPage()
                .openBoard(board.getName())
                .filteredCardOnBoard(filterText);
    }

    public void updateBoardNameByUI(Board board, String boardName) {
        new BoardPage()
                .openBoard(board.getName())
                .updateBoardName(boardName);
    }
    //

    public void createColumnByUI(Board board, String nameColumn) {
        new BoardPage()
                .openBoard(board.getName())
                .createColumn(nameColumn);
    }


    //checking
    public Boolean checkingExistenceCardByUI(String cardName) {
        return new BoardPage().cardByNameIsExist(cardName);
    }

    public Boolean checkingDisplayCardByUI(String cardName) {
        return new BoardPage().cardIsDisplayed(cardName);
    }

    public Boolean checkingExistenceLabelByUI(String color) {
        return new BoardPage().checkLabelOnCardExists(color);
    }

    public Boolean checkingExistenceColumnByUI(String columnName) {
        return new BoardPage().columnByNameIsDisplayed(columnName);
    }


}
