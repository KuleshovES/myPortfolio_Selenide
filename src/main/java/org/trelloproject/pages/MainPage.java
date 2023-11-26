package org.trelloproject.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.trelloproject.ConfProperties;

import java.time.Duration;

//import static resources.ConfProperties.LOGGER;
//import static resources.ConfProperties.driver;

public class MainPage {
    public static final WebDriverWait wait = new WebDriverWait(ConfProperties.driver, Duration.ofSeconds(5));

    String buttonCreateNewBoard = "//*[@data-testid=\"create-board-tile\"]/div";

    String inputNameBoard = "//*[@data-testid=\"create-board-title-input\"]";
    String displayNameBoard = "//*[@data-testid=\"board-name-display\"]";
    String buttonApplyCreateNewBoard = "//*[@data-testid=\"create-board-submit-button\"]";
    String buttonCreateFromTemplate = "//*[@data-testid=\"create-from-template-button\"]";
    String firstElementTemplate = "//*[@role=\"menu\"]/li[1]/div/button";
    String buttonMainPage = "//*[@id=\"header\"]/a";

    String buttonLogInHomePage = "//a[contains(text(),'Log in')]";

    //--
    private final String firstBoardOnBasePage = "//a[@class=\"board-tile\"]";
    private final String buttonShowedClosedBoards = ".view-all-closed-boards-button";

    //leftMenu
    private final String buttonGoToBoardsWorkSpace = "//*[@data-testid=\"open-boards-link\"]";


    //account-info-menu
    static String buttonOpenAccountMenu = "//*[@data-testid=\"header-member-menu-button\"]";
    static String buttonLogOutAccountMenu = "//*[@data-testid=\"account-menu-logout\"]";
    static String buttonConfirmLogOut = "logout-submit";

    //boards
    @Step("Create board by UI with name: {boardName}")
    public void createNewBoard(String boardNameUI) {
        String boardInWorkSpace = "//a[contains(text(),'" + boardNameUI + "')]";
        wait.until(ExpectedConditions.elementToBeClickable(ConfProperties.driver.findElement(By.xpath(buttonCreateNewBoard))));
        ConfProperties.driver.findElement(By.xpath(buttonCreateNewBoard)).click();
        ConfProperties.driver.findElement(By.xpath(inputNameBoard))
                .sendKeys(boardNameUI);
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(buttonApplyCreateNewBoard))).click();
        //driver.findElement(By.xpath(ButtonApplyCreateNewBoard)).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(boardInWorkSpace)));
        ConfProperties.driver.findElement(By.xpath(buttonGoToBoardsWorkSpace)).click();
    }

    @Step("Create template board by UI with name: {boardName}")
    public void createNewBoardFromTemplate(String boardNameUI) {
        wait.until(ExpectedConditions.elementToBeClickable(ConfProperties.driver.findElement(By.xpath(buttonCreateNewBoard))));
        ConfProperties.driver.findElement(By.xpath(buttonCreateNewBoard)).click();
        ConfProperties.driver.findElement(By.xpath(buttonCreateFromTemplate)).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(firstElementTemplate))).click();
        ConfProperties.driver.findElement(By.xpath(inputNameBoard))
                .clear();
        ConfProperties.driver.findElement(By.xpath(inputNameBoard))
                .sendKeys(boardNameUI);
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(buttonApplyCreateNewBoard))).click();
        ConfProperties.driver.findElement(By.xpath(buttonGoToBoardsWorkSpace)).click();
    }

    //account
    @Step("Open account menu")
    public static void openAccountMenu() {
        ConfProperties.driver.findElement(By.xpath(buttonOpenAccountMenu)).click();

    }

    @Step("Get Current Username")
    public String getCurrentUserName() {
        openAccountMenu();
        String locCurrentUserName = "//div[contains(@class, \"tS3UagTaVXEivA\")]";
        return ConfProperties.driver.findElement(By.xpath(locCurrentUserName)).getAttribute("textContent");
    }

    @Step("LogOut")
    public static void logOut() {
        openAccountMenu();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(buttonLogOutAccountMenu))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.id(buttonConfirmLogOut))).click();
    }

    @Step("Checking the opening of the main page")
    public boolean mainPageIsOpen() {
        boolean result;

        try {
            result = (ConfProperties.driver.findElement(By.xpath(buttonLogInHomePage)).isDisplayed());
            ConfProperties.LOGGER.info("Your tab: " + ConfProperties.driver.getCurrentUrl());
        } catch (NoSuchElementException ex) {
            result = false;
            ConfProperties.LOGGER.info("Your tab: " + ConfProperties.driver.getCurrentUrl());
        }
        return result;

    }

    @Step("Show closed boards on UI")
    public boolean showClosedBoards() {
        boolean result;
        ConfProperties.driver.findElement(By.cssSelector(buttonShowedClosedBoards)).click();
        try {
            ConfProperties.LOGGER.info("Try find Closed Boards");
            ConfProperties.driver.findElement(By.xpath("//button[text()='Reopen']")).isDisplayed();
            result = true;

        } catch (NoSuchElementException ex) {
            ConfProperties.LOGGER.info("Closed Boards not found");
            result = false;
        }
        return result;
    }

    @Step("Get actual name active board")
    public String getActualNameActiveBoard() throws InterruptedException {
        wait(1000);
        String name = ConfProperties.driver.findElement(By.xpath(displayNameBoard))
                .getAttribute("textContent");

        return name;
    }

    @Step("Get name first board")
    public String getNameFirstBoard() {
        return ConfProperties.driver.findElement(By.xpath(firstBoardOnBasePage))
                .getAttribute("outerText");
    }

    @Step("Open main Page")
    public void openMainPage() {
        ConfProperties.driver.findElement(By.xpath(buttonMainPage)).click();
    }
}


