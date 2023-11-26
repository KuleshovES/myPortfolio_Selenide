package org.trelloproject;

import org.trelloproject.robots.api.RestApiRobot;
import org.trelloproject.entities.Board;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.trelloproject.pages.LoginPage;

import java.io.File;
import java.time.Duration;
import java.util.logging.Logger;

public class ConfProperties {
    public static final Logger LOGGER = Logger.getLogger(RestApiRobot.class.getName());

    public static WebDriver driver;

    @Step("Prepare WebDriver")
    public static WebDriver chrome() {
        System.setProperty("webdriver.chrome.driver", "webdriver/chromedriver.exe");
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--start-maximized");
        driver = new ChromeDriver(chromeOptions);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        return driver;
    }

    @Step("Precondition login")
    public static WebDriver preconditionWithLogin() throws InterruptedException {
        driver = chrome();
        LoginPage.login();
        return driver;
    }


    public static void clearDataAndCloseDriver () {
        RestApiRobot.closedAllBoards();
    }

    public static void clearDataUI (Board board) {
        RestApiRobot.deleteBoard(board.getId());
    }
}