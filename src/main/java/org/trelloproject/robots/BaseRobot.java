package org.trelloproject.robots;

import org.trelloproject.pages.BoardPage;
import org.trelloproject.pages.MainPage;

public class BaseRobot {

    public void createBoardByUI(String boardName) {
        new MainPage()
                .createNewBoard(boardName);
    }

    public void createTemplateBoardByUI(String boardName) {
        new MainPage()
                .createNewBoardFromTemplate(boardName);
    }

    public boolean showClosedBoardByUI() {
        return new MainPage().showClosedBoards();
    }

    public String getNameFirstBoardByUI(){
        return new MainPage().getNameFirstBoard();
    }

    public void logInByUI() {
        new MainPage()
                ;
    }

    public void logOutByUI() {
        new MainPage()
                .logOut();
    }

    //checking
    public Boolean checkingExistenceCardByUI(String cardName) {
        return new BoardPage().cardByNameIsExist(cardName);
    }

    public String currentUserNameByUI() {
        return new MainPage().getCurrentUserName();
    }

    public Boolean checkingOpenedMainPageByUI() {
        return new MainPage().mainPageIsOpen();
    }


}
