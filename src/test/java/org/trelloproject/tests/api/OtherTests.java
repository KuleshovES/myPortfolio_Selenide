package org.trelloproject.tests.api;

import org.trelloproject.entities.Board;
import org.trelloproject.entities.User;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import org.trelloproject.robots.api.RestApiRobot;

import static org.trelloproject.robots.api.RestApiRobot.*;

public class OtherTests {

    @Epic(value = "Api")
    @Feature(value = "Other tests")
    @Description(value = "Test check valid token")
    @Test
    public void getInfoAboutMe() throws InterruptedException {
        //This test only for check valid token
        User user = new User();
        RestApiRobot.getInfoUser(user);
    }

    @Epic(value = "Api")
    @Feature(value = "Other tests")
    @Description(value = "Special test for Fail")
    @Test
    public void checkGetCard() {
        //test for fail
        getCard("64da38d28fe02c8d11c6c0e5");
    }

    @Ignore
    @Test
    public void checkGetColumn() {
        //test for fail
        getColumn("64da6e3439d127ed04293f70");
    }

    @Ignore
    @Test
    public void checkCreateBoard() {
        Board board = new Board("boardForDebug");
        createBoard(board);
    }
}

