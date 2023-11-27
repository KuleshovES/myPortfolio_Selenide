package org.trelloproject.tests.UI;

import io.qameta.allure.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
//import org.trelloproject.helpers.TestExecutionListener;
import org.trelloproject.pages.LoginPage;
import org.trelloproject.robots.BaseRobot;
import org.trelloproject.robots.BoardRobot;
import org.trelloproject.entities.Board;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import org.trelloproject.robots.api.RestApiRobot;
import static com.codeborne.selenide.Selenide.*;
import com.codeborne.selenide.*;

//@Listeners(TestExecutionListener.class)
public class TestsUIWithBoards {
    private final BaseRobot baseRobot = new BaseRobot();
    private final BoardRobot boardRobot = new BoardRobot();
    private final String defaultBoardName = "MyTestBoard";
    private final String defaultColumnName = "Backlog";
    private final String defaultFirstCardName = "FirstTask";
    private final String defaultSecondCardName = "SecondTask";

    @BeforeMethod
    public void logIn() throws InterruptedException {
        LoginPage.login();
        Configuration.reportsFolder = "test-result/reports";
        //ScreenShooter.captureSuccessfulTests = true;

    }

    @Epic(value = "UI")
    @Feature(value = "Tests with Boards")
    @Description(value = "Test check create Board without template By UI")
    @Test
    public void createBoardSimpleUI() throws InterruptedException {
        baseRobot.createBoardByUI(defaultBoardName);
        Assert.assertEquals(defaultBoardName, baseRobot.getNameFirstBoardByUI());

    }

    @Epic(value = "UI")
    @Feature(value = "Tests with Boards")
    @Description(value = "Test check create Board with template By UI")
    @Flaky
    @Test
    public void createBoardWithTemplateUI() throws InterruptedException {
        baseRobot.createTemplateBoardByUI(defaultBoardName);
        Assert.assertEquals(defaultBoardName, baseRobot.getNameFirstBoardByUI());
    }

    @Epic(value = "UI")
    @Feature(value = "Tests with Boards")
    @Description(value = "Test check update Board By UI")
    @Test
    public void updateBoardUI() throws InterruptedException {
        String newBoardName = "new name";
        Board newBoard = boardRobot.createFullBoardByRest(defaultBoardName, defaultColumnName, defaultFirstCardName, defaultSecondCardName);
        boardRobot.updateBoardNameByUI(newBoard, newBoardName);
        Assert.assertNotEquals(defaultBoardName, boardRobot.getNameBoardUI());
    }

    @Epic(value = "UI")
    @Feature(value = "Tests with Boards")
    @Description(value = "Test check button shows Closed Boards By UI")
    @Test
    public void showClosedBoards() throws InterruptedException {
        //precondition unusual
        Board newBoard = boardRobot.createFullBoardByRest(defaultBoardName, defaultColumnName, defaultFirstCardName, defaultSecondCardName);
        boardRobot.closedBoardByRest(newBoard.getId());
        Assert.assertTrue(baseRobot.showClosedBoardByUI());

    }

    @AfterMethod
    public void clearAndCloseAfterTest() throws InterruptedException {
        RestApiRobot.closedAllBoards();
        closeWebDriver();
    }

}
