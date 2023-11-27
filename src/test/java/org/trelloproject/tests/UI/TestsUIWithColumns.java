package org.trelloproject.tests.UI;

import org.trelloproject.pages.LoginPage;
import org.trelloproject.robots.BoardRobot;
import org.trelloproject.entities.Board;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.trelloproject.robots.api.RestApiRobot;
import static com.codeborne.selenide.Selenide.closeWebDriver;


public class TestsUIWithColumns {
    private BoardRobot boardRobot = new BoardRobot();
    private final String newColumnName = "myTestColumn";

    @BeforeMethod
    public void logIn() throws InterruptedException {
        LoginPage.login();
    }

    @Epic(value = "UI")
    @Feature(value = "Tests with Columns")
    @Description(value = "Test check create Column By UI")
    @Test
    public void createColumnUI() {
        Board newBoard = boardRobot.createEmptyBoardByRest("MyTestBoard");
        boardRobot.createColumnByUI(newBoard, newColumnName);
        Assert.assertTrue(boardRobot.checkingExistenceColumnByUI(newColumnName));
    }

    @AfterMethod
    public void clearAndCloseAfterTest() throws InterruptedException {
        RestApiRobot.closedAllBoards();
        closeWebDriver();
    }

}
