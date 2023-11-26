package org.trelloproject.tests.UI;

import org.trelloproject.robots.BoardRobot;
import org.trelloproject.entities.Board;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.testng.Assert;

import org.testng.annotations.*;
import org.trelloproject.ConfProperties;
import org.trelloproject.robots.api.RestApiRobot;

import static org.trelloproject.ConfProperties.driver;


public class TestsUIWithCards {
    private BoardRobot boardRobot = new BoardRobot();
    private final String newCardName = "newCardName";
    private final String defaultBoardName = "MyTestBoard";
    private final String defaultColumnName = "Backlog";
    private final String defaultFirstCardName = "FirstTask";
    private final String defaultSecondCardName = "SecondTask";


    @BeforeMethod
    public void openDriver() throws InterruptedException {
        driver = ConfProperties.preconditionWithLogin();
    }

    @Epic(value = "UI")
    @Feature(value = "Tests with Cards")
    @Description(value = "Test check create card By UI")
    @Test
    public void createCardUI() throws InterruptedException {
        Board newBoard = boardRobot.createEmptyBoardByRest("MyTestBoard");
        boardRobot.createCardByUI(newBoard, newCardName);
        String actualNameFirstCard = boardRobot.getFirstCardUI();
        Assert.assertEquals(actualNameFirstCard, newCardName);

    }

    @Epic(value = "UI")
    @Feature(value = "Tests with Cards")
    @Description(value = "Test check update card By UI")
    @Test
    public void updateCardUI() {
        Board newBoard = boardRobot.createFullBoardByRest(defaultBoardName, defaultColumnName, defaultFirstCardName, defaultSecondCardName);
        boardRobot.updateCardByUI(newBoard, newCardName);
        String actualNameFirstCard = boardRobot.getFirstCardUI();
        Assert.assertEquals(actualNameFirstCard, newCardName);

    }

    @Epic(value = "UI")
    @Feature(value = "Tests with Cards")
    @Description(value = "Test check copy card By UI")
    @Test
    public void copyCardUI() {
        final String newNameCopiedCard = "Copied card";
        Board newBoard = boardRobot.createFullBoardByRest(defaultBoardName, defaultColumnName, defaultFirstCardName, defaultSecondCardName);
        boardRobot.copyCardByUI(newBoard, newNameCopiedCard);
        Assert.assertNotEquals(boardRobot.getFirstCardUI(), newNameCopiedCard);

    }

    @Epic(value = "UI")
    @Feature(value = "Tests with Cards")
    @Description(value = "Test check delete card By UI")
    @Test
    public void deleteCardUI() {
        Board newBoard = boardRobot.createFullBoardByRest(defaultBoardName, defaultColumnName, defaultFirstCardName, defaultSecondCardName);
        boardRobot.deleteCardByUI(newBoard, defaultFirstCardName);
        Assert.assertFalse(boardRobot.checkingExistenceCardByUI(defaultFirstCardName));

    }

    @Epic(value = "UI")
    @Feature(value = "Tests with Cards")
    @Description(value = "Test check drag card By UI")
    @Test
    public void dragCardUI() {
        Board newBoard = boardRobot.createFullBoardByRest(defaultBoardName, defaultColumnName, defaultFirstCardName, defaultSecondCardName);
        boardRobot.movedCardByUI(newBoard, defaultFirstCardName);
        //TODO assert doesn't work =( need check card in list2

    }

    @Epic(value = "UI")
    @Feature(value = "Tests with Cards")
    @Description(value = "Test check added label to card By UI")
    @Test
    public void addLabelToCardUI() {
        Board newBoard = boardRobot.createFullBoardByRest(defaultBoardName, defaultColumnName, defaultFirstCardName, defaultSecondCardName);
        boardRobot.addLabelToCardByUI(newBoard, defaultFirstCardName, "Green");
        Assert.assertFalse(boardRobot.checkingExistenceLabelByUI("Green"));
    }

    @Epic(value = "UI")
    @Feature(value = "Tests with Cards")
    @Description(value = "Test check filtered card on board By UI")
    @Test
    public void filteredCardOnBoardUI() {
        Board newBoard = boardRobot.createFullBoardByRest(defaultBoardName, defaultColumnName, defaultFirstCardName, defaultSecondCardName);
        boardRobot.filterCardByUI(newBoard, "First");
        Assert.assertFalse(boardRobot.checkingDisplayCardByUI("SecondTask"));

    }

    @AfterMethod
    public void clearAndCloseAfterTest() {
        RestApiRobot.closedAllBoards();
        driver.close();
    }

}
