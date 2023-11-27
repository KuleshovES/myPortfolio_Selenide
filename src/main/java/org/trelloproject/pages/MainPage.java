package org.trelloproject.pages;

import com.codeborne.selenide.Condition;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.trelloproject.ConfProperties;
import java.time.Duration;
import com.codeborne.selenide.SelenideElement;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.$x;


public class MainPage {

    private static final By BUTTON_CREATE_NEW_BOARD = By.xpath("//*[@data-testid=\"create-board-tile\"]/div");
    private static final By INPUT_NAME_BOARD = By.xpath("//*[@data-testid=\"create-board-title-input\"]");
    private static final By DISPLAY_NAME_BOARD = By.xpath("//*[@data-testid=\"board-name-display\"]");
    private static final By BUTTON_APPLY_CREATE_NEW_BOARD = By.xpath("//*[@data-testid=\"create-board-submit-button\"]");
    private static final By BUTTON_CREATE_FROM_TEMPLATE = By.xpath("//*[@data-testid=\"create-from-template-button\"]");
    private static final By FIRST_ELEMENT_TEMPLATE_LIST = By.xpath("//*[@role=\"menu\"]/li[1]/div/button");
    private static final By BUTTON_MAIN_PAGE = By.xpath("//*[@id=\"header\"]/a");
    private static final By BUTTON_LOGIN_HOME_PAGE = By.xpath("//a[contains(text(),'Log in')]");

    private static final By FIRST_BOARD_ON_BASE_PAGE = By.xpath("//a[@class=\"board-tile\"]");
    private static final By BUTTON_SHOWED_CLOSED_BOARDS = By.cssSelector(".view-all-closed-boards-button");

    private static final By BUTTON_GO_TO_BOARDS_WORK_SPACE = By.xpath("//*[@data-testid=\"open-boards-link\"]");

    //account-info-menu
    private static final By BUTTON_OPEN_ACCOUNT_MENU = By.xpath("//*[@data-testid=\"header-member-menu-button\"]");
    private static final By BUTTON_LOGOUT_ACCOUNT_MENU = By.xpath("//*[@data-testid=\"account-menu-logout\"]");
    private static final By BUTTON_CONFIRM_LOGOUT = By.xpath("logout-submit");

    //boards
    @Step("Create board by UI with name: {boardName}")
    public void createNewBoard(String boardNameUI) {
        SelenideElement BOARD_IN_WORKS_SPACE = $x("//a[contains(text(),'" + boardNameUI + "')]");

        $(BUTTON_CREATE_NEW_BOARD).shouldBe(Condition.interactable);
        $(BUTTON_CREATE_NEW_BOARD).click();
        $(INPUT_NAME_BOARD).sendKeys(boardNameUI);
        $(BUTTON_APPLY_CREATE_NEW_BOARD).shouldBe(Condition.interactable).click();
        $(BOARD_IN_WORKS_SPACE).shouldBe(Condition.interactable);
        $(BUTTON_GO_TO_BOARDS_WORK_SPACE).click();
    }

    @Step("Create template board by UI with name: {boardName}")
    public void createNewBoardFromTemplate(String boardNameUI) {
        $(BUTTON_CREATE_NEW_BOARD).shouldBe(Condition.interactable).click();
        $(BUTTON_CREATE_FROM_TEMPLATE).click();
        $(FIRST_ELEMENT_TEMPLATE_LIST).shouldBe(Condition.interactable).click();
        $(INPUT_NAME_BOARD).clear();
        $(INPUT_NAME_BOARD).sendKeys(boardNameUI);
        $(BUTTON_APPLY_CREATE_NEW_BOARD).shouldBe(Condition.interactable).click();
        $(BUTTON_GO_TO_BOARDS_WORK_SPACE).click();
    }

    //account
    @Step("Open account menu")
    public static void openAccountMenu() {
        $(BUTTON_OPEN_ACCOUNT_MENU).click();

    }

    @Step("Get Current Username")
    public String getCurrentUserName() {
        openAccountMenu();
        SelenideElement CURRENT_USERNAME = $x("//div[contains(@class, \"tS3UagTaVXEivA\")]");
        return $(CURRENT_USERNAME).getAttribute("textContent");
    }

    @Step("LogOut")
    public static void logOut() {
        openAccountMenu();
        $(BUTTON_LOGOUT_ACCOUNT_MENU).shouldBe(Condition.interactable).click();
        $(BUTTON_CONFIRM_LOGOUT).shouldBe(Condition.interactable).click();
    }

    @Step("Checking the opening of the main page")
    public boolean mainPageIsOpen() {
        boolean result;

        try {
            result = $(BUTTON_LOGIN_HOME_PAGE).isDisplayed();
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
        $(BUTTON_SHOWED_CLOSED_BOARDS).shouldBe(Condition.interactable).click();
        try {
            ConfProperties.LOGGER.info("Try find Closed Boards");
            result = $x("//button[text()='Reopen']").isDisplayed();

        } catch (NoSuchElementException ex) {
            ConfProperties.LOGGER.info("Closed Boards not found");
            result = false;
        }
        return result;
    }

    @Step("Get actual name active board")
    public String getActualNameActiveBoard() throws InterruptedException {
        wait(1000);

        String name = $(DISPLAY_NAME_BOARD).getAttribute("textContent");

        return name;
    }

    @Step("Get name first board")
    public String getNameFirstBoard() {
        return $(FIRST_BOARD_ON_BASE_PAGE).getAttribute("outerText");

    }

    @Step("Open main Page")
    public void openMainPage() {
        $(BUTTON_MAIN_PAGE).click();
    }
}


