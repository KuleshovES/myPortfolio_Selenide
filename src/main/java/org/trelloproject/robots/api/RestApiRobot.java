package org.trelloproject.robots.api;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import io.restassured.RestAssured;
import org.trelloproject.entities.Board;
import org.trelloproject.entities.Card;
import org.trelloproject.entities.Column;
import org.trelloproject.entities.User;
import io.qameta.allure.Step;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import java.util.List;
import java.util.logging.Logger;


public class RestApiRobot {
    static String key = "071815818d26f6434f3fc4e0ee46fc56";
    static String token = "ATTAf983a9423e40ba35f912ae4d70b0351c23cde66706caad0584c53eb47b8cfddfFED14199";
    private static final Logger LOGGER = Logger.getLogger(RestApiRobot.class.getName());

    private static final RequestSpecification REQ_SPEC =
            new RequestSpecBuilder()
                    .setBaseUri("https://api.trello.com/1")
                    .setContentType(ContentType.JSON)
                    .addHeader("Accept", "application/json")
                    .addHeader("accept", "*/*")
                    .addQueryParam("key", key)
                    .addQueryParam("token", token)
                    .build();

    //Preconditions
    @Step("Precondition: Create board with data")
    public static Board createFullBoard(String boardName, String columnName, String firstCardName, String secondCardName) {
        Board board = RestApiRobot.preConditionBoard(boardName);
        Column column = RestApiRobot.preConditionColumn(columnName, board);
        Card firstCard = new Card(firstCardName);
        Card secondCard = new Card(secondCardName);
        RestApiRobot.preConditionCard(column, firstCard);
        RestApiRobot.preConditionCard(column, secondCard);
        return board;
    }

    @Step("Precondition: Create board {boardName}")
    public static Board createEmptyBoard(String boardName) {
        Board board = new Board(boardName);
        RestApiRobot.createBoard(board);
        return board;

    }

    @Step("Precondition: Create board {boardName}")
    public static Board preConditionBoard(String boardName) {
        Board board = new Board(boardName);
        RestApiRobot.createBoard(board);
        return board;

    }

    @Step("Precondition: Create column {columnName}")
    public static Column preConditionColumn(String columnName, Board board) {
        Column column = new Column(columnName, board);
        RestApiRobot.createColumns(column, board);
        return column;

    }

    @Step("Precondition: Create card")
    public static Card preConditionCard(Column column, Card externCard) {
        RestApiRobot.createCard(column, externCard);
        return externCard;

    }

    //boards
    @Step("Create board")
    public static Board createBoard(Board board) {
        String response;
        try {
            LOGGER.info("Send request to create Board");
            response = RestAssured.given()
                    .spec(REQ_SPEC)
                    .basePath("/boards/")
                    .queryParams("name", board.getName())
                    .log().uri()
                    .when().post()
                    .then().statusCode(200)
                    .extract().body().asString();
            LOGGER.info("RESPONSE:" + response);
        } catch (AssertionError ex) {
            LOGGER.info("Failed Create board with name: " + board.getName());
            throw ex;
        }
        Gson gson = new Gson();
        board.setId(gson.fromJson(response, Board.class).getId());
        return board;
    }

    @Step("Get board {boardId}")
    public static Board getBoard(String boardId) {
        String resGetBoard;
        try {
            LOGGER.info("Start send request GET/boards/" + boardId);
            resGetBoard = RestAssured.given()
                    .spec(REQ_SPEC)
                    .basePath("/boards/" + boardId)
                    .when().get()
                    .then().statusCode(200)
                    .extract().body().asString();
            LOGGER.info("RESPONSE:" + resGetBoard);
        } catch (AssertionError ex) {
            LOGGER.info("Failed GET/boards/:" + boardId);
            throw ex;
        }
        Gson gson = new Gson();
        Board board = gson.fromJson(resGetBoard, Board.class);
        return board;
    }

    @Step("Closed board {boardId}")
    public static void closedBoard(String boardId) {
        try {
            LOGGER.info("Send request to closed Board");
            RestAssured.given()
                    .spec(REQ_SPEC)
                    .basePath("/boards/" + boardId)
                    .queryParams("closed", "true")
                    .log().uri()
                    .when().put()
                    .then().statusCode(200);
            LOGGER.info("Board id:" + boardId + "successfully closed");
        } catch (AssertionError ex) {
            LOGGER.info("Failed closed Board id: " + boardId);
            throw ex;
        }
    }

    @Step("Delete board {boardId}")
    public static void deleteBoard(String boardId) {
        try {
            LOGGER.info("Start send request DELETE/board/" + boardId);
            RestAssured.given()
                    .spec(REQ_SPEC)
                    .basePath("/boards/" + boardId)
                    .when().delete()
                    .then().statusCode(200);
            LOGGER.info("Successful delete board with id:" + boardId);
        } catch (AssertionError ex) {
            LOGGER.info("Failed DELETE/board/:" + boardId);
            throw ex;
        }
    }

    @Step("Closed boards")
    public static void closedAllBoards() {
        User user = new User();
        User currentUser = getInfoUser(user);

        List<String> listOfIdBoards = currentUser.getIdBoards();
        listOfIdBoards
                .forEach(idBoard -> deleteBoard(idBoard));
    }

    //Columns
    @Step("Create column")
    public static Column createColumns(Column column, Board board) {
        String responseColumn;
        try {
            LOGGER.info("Send request to create column");
            responseColumn = RestAssured.given()
                    .spec(REQ_SPEC)
                    .basePath("/boards/" + board.getId() + "/lists")
                    .queryParams("name", column.getName())
                    .log().uri()
                    .when().post()
                    .then().statusCode(200)
                    .extract().body().asString();
            LOGGER.info("RESPONSE:" + responseColumn);
            LOGGER.info("Create column with id: " + column.getId());
        } catch (AssertionError ex) {
            LOGGER.info("Failed Create column with name: " + column.getName());
            throw ex;
        }
        Gson gson = new Gson();
        column.setId(gson.fromJson(responseColumn, Column.class).getId());
        return column;
    }

    @Step("Get list columns from board {boardId}")
    public static List<Column> getColumn(String boardId) {
        List<Column> column;
        try {
            LOGGER.info("Start send request GET/boards/" + boardId + "/lists/");
            String respColumn = RestAssured.given()
                    .spec(REQ_SPEC)
                    .basePath("/boards/" + boardId + "/lists")
                    .when().get()
                    .then().statusCode(200)
                    .extract().body().asString();
            Gson gson = new Gson();
            column = gson.fromJson(respColumn, new TypeToken<List<Column>>() {
            }.getType());
            LOGGER.info("Result:" + respColumn);

        } catch (AssertionError ex) {
            LOGGER.info("Failed GET/boards/" + boardId + "/lists/");
            throw ex;
        }
        return column;

    }

    @Step("Get column by filter from board")
    public static String getFilteredNameColumn(Board exBoard, Column exColumn) {
        LOGGER.info("Start filter2");
        String columnName = getColumn(exBoard.getId())
                .stream()
                .filter(column -> column.getId().equals(exColumn.getId()))
                .findFirst()
                .orElseThrow(() -> new AssertionError("element not found"))
                .getName();
        LOGGER.info("Find columnName: " + columnName);
        return columnName;
    }

    //cards
    @Step("Create Card")
    public static Card createCard(Column exColumn, Card exCard) {
        String response;
        try {
            LOGGER.info("Send request to create cards");
            response = RestAssured.given()
                    .spec(REQ_SPEC)
                    .basePath("/cards")
                    .queryParams("idList", exColumn.getId())
                    .queryParams("name", exCard.getName())
                    .log().uri()
                    .when().post()
                    .then().statusCode(200)
                    .extract().body().asString();
            LOGGER.info("RESPONSE:" + response);
        } catch (AssertionError ex) {
            LOGGER.info("Failed Create card with name: " + exCard.getName() + "in column: " + exColumn.getId());
            throw ex;
        }
        Gson gson = new Gson();
        exCard.setId(gson.fromJson(response, Card.class).getId());
        return exCard;
    }

    @Step("Get Card by id {cardId}")
    public static Card getCard(String cardId) {
        String resGet;
        try {
            resGet = RestAssured.given()
                    .spec(REQ_SPEC)
                    .basePath("/cards/" + cardId)
                    .when().get()
                    .then().statusCode(200)
                    .extract().body().asString();
        } catch (AssertionError ex) {
            LOGGER.info("Not found card: " + cardId);
            throw ex;
        }
        Gson gson = new Gson();
        Card card = gson.fromJson(resGet, Card.class);
        LOGGER.info("Response: name: " + card.getName() + " id: " + card.getId());
        return card;

    }

    @Step("Get Cards on Board by id {boardId}")
    public static List<Card> getCards(String boardId) {
        List<Card> cards;
        String resGet;
        try {
            resGet = RestAssured.given()
                    .spec(REQ_SPEC)
                    .basePath("/boards/" + boardId + "/cards")
                    .when().get()
                    .then().statusCode(200)
                    .extract().body().asString();
        } catch (AssertionError ex) {
            LOGGER.info("Not found board: " + boardId);
            throw ex;
        }
        Gson gson = new Gson();
        cards = gson.fromJson(resGet, new TypeToken<List<Card>>(){}.getType());
        LOGGER.info("Response: " + resGet);
        return cards;
    }

    @Step("Update Card by id {cardId}")
    public static void updateCard(String queryParams, String valueParams, String cardId) {
        try {
            LOGGER.info("Send request to update cards");
            RestAssured.given()
                    .spec(REQ_SPEC)
                    .basePath("/cards/" + cardId)
                    .queryParams(queryParams, valueParams)
                    .log().uri()
                    .when().put()
                    .then().statusCode(200);
            LOGGER.info("Card id:" + cardId + "successfully update");
            LOGGER.info("New value for " + queryParams + "=" + valueParams);
        } catch (AssertionError ex) {
            LOGGER.info("Failed update card id: " + cardId);
            throw ex;
        }
    }

    //Users
    @Step("Get info about User")
    public static User getInfoUser(User user) {
        String response;
        try {
            LOGGER.info("Start get info about user");
            response = RestAssured.given()
                    .spec(REQ_SPEC)
                    .basePath("/members/me")
                    .when().get()
                    .then().statusCode(200)
                    .extract().body().asString();
            LOGGER.info("RESPONSE:" + response);
        } catch (AssertionError ex) {
            LOGGER.info("Failed get info about user");
            throw ex;
        }
        Gson gson = new Gson();
        user.setId(gson.fromJson(response, User.class).getId());
        user.setEmail(gson.fromJson(response, User.class).getEmail());
        user.setFullName(gson.fromJson(response, User.class).getFullName());
        user.setUsername(gson.fromJson(response, User.class).getUsername());
        user.setIdBoards(gson.fromJson(response, User.class).getIdBoards());
        user.setIdOrganizations(gson.fromJson(response, User.class).getIdOrganizations());
        return user;
    }

    //AfterTests
    public static void clearData() {

    }

}