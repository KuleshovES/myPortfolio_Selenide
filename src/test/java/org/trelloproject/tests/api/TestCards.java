package org.trelloproject.tests.api;

import io.qameta.allure.*;
import org.trelloproject.entities.Board;
import org.trelloproject.entities.Card;
import org.trelloproject.entities.Column;
import org.trelloproject.robots.api.RestApiRobot.*;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import org.trelloproject.robots.api.RestApiRobot;

import java.util.List;

public class TestCards {

    @Epic(value = "Api")
    @Feature(value = "Tests with Cards")
    @Test
    @Description(value = "Test check create card")
    public void createCards() {
        //precondition
        Board board = RestApiRobot.preConditionBoard("myTestCreateCards");
        Column column = RestApiRobot.preConditionColumn("MyTestListFirst", board);

        //test
        Card expectedCard = new Card("MyFirstTask");
        RestApiRobot.createCard(column, expectedCard);
        Card actualCard = RestApiRobot.getCard(expectedCard.getId());
        Assert.assertEquals(expectedCard.getName(), actualCard.getName());

    }

    @Epic(value = "Api")
    @Feature(value = "Tests with Cards")
    @Test
    @Description(value = "Test check update Card")
    public void updateCard() {
        //precondition
        Board board = RestApiRobot.createFullBoard("sd","sd","MyFirstTaskOriginalName", "MySecondTaskOriginalName");
        List<Card> cards = RestApiRobot.getCards(board.getId());

        //test
        Card firstCard = RestApiRobot.getCard(cards.get(0).getId());
        Card secondCard = RestApiRobot.getCard(cards.get(1).getId());

        RestApiRobot.updateCard("name", "newCardName_first", cards.get(0).getId());

        Card actualFirstCard = RestApiRobot.getCard(firstCard.getId());
        Card actualSecondCard = RestApiRobot.getCard(secondCard.getId());

        //check
        Assert.assertNotEquals(firstCard.getName(), actualFirstCard.getName());
        Assert.assertEquals(secondCard.getName(), actualSecondCard.getName());

    }

    @AfterMethod
    public void clearData() {
        RestApiRobot.closedAllBoards();
    }
}
