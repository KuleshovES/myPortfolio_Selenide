package org.trelloproject.tests.api;

import org.trelloproject.entities.Board;
import org.trelloproject.entities.Column;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import org.trelloproject.robots.api.RestApiRobot;

import org.trelloproject.robots.api.RestApiRobot;

import static org.trelloproject.robots.api.RestApiRobot.getBoard;
import static org.trelloproject.robots.api.RestApiRobot.getFilteredNameColumn;

public class TestBoards {

    @Epic(value = "Api")
    @Feature(value = "Tests with Boards")
    @Description(value = "Test check create Board")
    @Test
    public void createBoard() {
        Board expectedBoard = RestApiRobot.preConditionBoard("myTestDesk13");
        Board actualBoard = getBoard(expectedBoard.getId());
        Assert.assertEquals(expectedBoard.getName(), actualBoard.getName());
    }

    @Epic(value = "Api")
    @Feature(value = "Tests with Boards")
    @Description(value = "Test check create Columns")
    @Test
    public void createColumns() {
        Board board = RestApiRobot.preConditionBoard("myTestDesk12");
        Column expectedFirstColumn = RestApiRobot.preConditionColumn("MyTestListFirst", board);

        RestApiRobot.createColumns(expectedFirstColumn, board);
        String actualFirstColumn = getFilteredNameColumn(board, expectedFirstColumn);

        Assert.assertEquals(expectedFirstColumn.getName(), actualFirstColumn);

    }


    @AfterMethod
    public void clearData () {
        RestApiRobot.closedAllBoards();
    }


}
