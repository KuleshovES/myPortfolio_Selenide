package org.trelloproject.tests.UI;

import org.trelloproject.robots.BaseRobot;
import org.trelloproject.robots.BoardRobot;
import org.trelloproject.entities.Board;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Flaky;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import org.trelloproject.ConfProperties;
import org.trelloproject.robots.api.RestApiRobot;

import static org.trelloproject.ConfProperties.driver;

public class TestsUIWithBoards {
    private BaseRobot baseRobot = new BaseRobot();
    private BoardRobot boardRobot = new BoardRobot();
    private final String defaultBoardName = "MyTestBoard";
    private final String defaultColumnName = "Backlog";
    private final String defaultFirstCardName = "FirstTask";
    private final String defaultSecondCardName = "SecondTask";

    @Epic(value = "UI")
    @Feature(value = "Tests with Boards")
    @Description(value = "Test check create Board without template By UI")
    @Test
    public void createBoardSimpleUI() throws InterruptedException {
        //precondition
        driver = ConfProperties.preconditionWithLogin();

        baseRobot.createBoardByUI(defaultBoardName);
        Assert.assertEquals(defaultBoardName, baseRobot.getNameFirstBoardByUI());
    }

    @Epic(value = "UI")
    @Feature(value = "Tests with Boards")
    @Description(value = "Test check create Board with template By UI")
    @Flaky
    @Test
    public void createBoardWithTemplateUI() throws InterruptedException {
        //precondition
        driver = ConfProperties.preconditionWithLogin();

        baseRobot.createTemplateBoardByUI(defaultBoardName);
        Assert.assertEquals(defaultBoardName, baseRobot.getNameFirstBoardByUI());

    }

    @Epic(value = "UI")
    @Feature(value = "Tests with Boards")
    @Description(value = "Test check update Board By UI")
    @Test
    public void updateBoardUI() throws InterruptedException {
        //precondition
        driver = ConfProperties.preconditionWithLogin();

        String newBoardName = "new name";
        Board newBoard = boardRobot.createFullBoardByRest(defaultBoardName, defaultColumnName, defaultFirstCardName, defaultSecondCardName);
        boardRobot.updateBoardNameByUI(newBoard,newBoardName);
        Assert.assertNotEquals(defaultBoardName, boardRobot.getNameBoardUI());

    }

    @Epic(value = "UI")
    @Feature(value = "Tests with Boards")
    @Description(value = "Test check button shows Closed Boards By UI")
    @Test
    public void showClosedBoards() throws InterruptedException {
        //precondition unusual
        Board newBoard = boardRobot.createFullBoardByRest(defaultBoardName, defaultColumnName, defaultFirstCardName, defaultSecondCardName);

        driver = ConfProperties.preconditionWithLogin();

        boardRobot.closedBoardByRest(newBoard.getId());
        Assert.assertTrue(baseRobot.showClosedBoardByUI());

    }

    @AfterMethod
    public void clearAndCloseAfterTest() throws InterruptedException {
        RestApiRobot.closedAllBoards();
        Thread.sleep(5000);
        driver.close();
    }

}
