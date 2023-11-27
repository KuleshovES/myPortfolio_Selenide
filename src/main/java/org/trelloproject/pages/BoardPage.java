package org.trelloproject.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.trelloproject.ConfProperties;

import static com.codeborne.selenide.Condition.value;
import static com.codeborne.selenide.Selenide.*;

public class BoardPage {

    private static final By FIRST_CARD_IN_ACTIVE_BOARD = By.xpath("//*[@data-testid=\"list-cards\"]/li[1]");
    private static final By DEFAULT_FIRST_TASK = By.xpath("//a[text()='FirstTask']");
    private static final By DEFAULT_SECOND_TASK = By.xpath("//a[text()='SecondTask']");

    //open board
    private static final By FIRST_LIST_LOCATOR = By.xpath("//*[@aria-label=\"Backlog\"]");
    private static final By SECOND_LIST_LOCATOR = By.xpath("//*[@aria-label=\"To Do\"]");
    private static final By BUTTON_FILTERED = By.xpath("//*[@data-testid=\"filter-popover-button\"]");
    private static final By INPUT_FILTER = By.xpath("//input[@aria-placeholder]");
    private static final By BUTTON_ADD_NEW_COLUMN = By.cssSelector(".js-add-list");
    private static final By INPUT_COLUMN_NAME = By.cssSelector(".list-name-input");
    private static final By BUTTON_SUBMIT_ADD_COLUMN = By.xpath("//*[@id=\"board\"]/div/form/div/input");
    private static final By LABEL_BOARD_NAME = By.xpath("//*[@data-testid=\"board-name-display\"]");
    private static final By INPUT_BOARD_NAME = By.xpath("//*[@data-testid=\"board-name-input\"]");
    private static final By BUTTON_ADD_CARD = By.xpath("//*[@data-testid=\"list-add-card-button\"]");
    private static final By INPUT_CARD_TITLE = By.xpath("//*[@data-testid=\"list-card-composer-textarea\"]");
    private static final By SUBMIT_ADD_CARD = By.xpath("//*[@data-testid=\"list-card-composer-add-card-button\"]");


    //block edit Card
    private static final By CARD_NAME_EDIT_CARD_LOCATOR = By.cssSelector(".js-card-detail-title-input");
    private static final By CLOSED_EDIT_CARD_LOCATOR = By.cssSelector(".js-close-window");

    private static final By BUTTON_COPY_CARD_IN_EDIT = By.cssSelector(".js-copy-card");
    private static final By BUTTON_ARCHIVE_CARD_IN_EDIT = By.cssSelector(".js-archive-card");
    private static final By BUTTON_DELETE_CARD_IN_EDIT = By.cssSelector(".js-delete-card");
    private static final By BUTTON_ADD_LABEL_TO_CARD_IN_EDIT = By.cssSelector(".js-edit-labels");

    //block pop-over
    private static final By BUTTON_SUBMIT_POPOVER = By.cssSelector(".js-submit");
    private static final By BUTTON_CONFIRM_POPOVER = By.cssSelector(".js-confirm");

    private static final By BUTTON_CLOSE_POPOVER = By.xpath("//*[@data-testid=\"popover-close\"]");
    private static final By TEXT_AREA_COPY_CARD = By.xpath("//textarea[@name=\"name\"]");
    private static By CARD_BY_NAME (String cardName){return By.xpath("//a[text()='" + cardName + "']"); };

    //SelenideElement CARD_BY_NAME = $x("//a[text()='" + cardName + "']");
    //SelenideElement ADD_LABEL_COLOR = $x("//span[@data-color=\"" + color.toLowerCase() + "\"]");

    //boards
    @Step("Open board on UI by name: {nameBoard}")
    public BoardPage openBoard(String nameBoard) {
        SelenideElement LOCATOR_BOARD = $x("//*[@title=\"" + nameBoard + "\"]");
        $(LOCATOR_BOARD).shouldBe(Condition.interactable).click();
        return this;
    }

    @Step("Update board on UI by name: {nameBoard}")
    public void updateBoardName(String newBoardNameUI) {
        $(LABEL_BOARD_NAME).click();
        $(INPUT_BOARD_NAME).sendKeys(newBoardNameUI);
        $(INPUT_BOARD_NAME).sendKeys(Keys.ENTER);
    }


    @Step("Get board name on UI")
    public String getBoardNameUI() {
        return $(LABEL_BOARD_NAME).getAttribute("textContent");
    }

    //cards
    //TODO bad!!!
    @Step("Creating a card on UI with name: {nameCard}")
    public void createCard(String nameCard) throws InterruptedException {

        //Thread.sleep(1000); //BAD!

        //ОТладить!
        if ($(BUTTON_ADD_CARD).isDisplayed()) {
            $(BUTTON_ADD_CARD).click();
            $(INPUT_CARD_TITLE).shouldBe(Condition.interactable).click();
            $(INPUT_CARD_TITLE).sendKeys(nameCard);
            $(SUBMIT_ADD_CARD).shouldBe(Condition.visible).click();
        } else {
            $(INPUT_CARD_TITLE).shouldBe(Condition.interactable).click();
            $(INPUT_CARD_TITLE).sendKeys(nameCard);
            $(SUBMIT_ADD_CARD).shouldBe(Condition.visible).click();
        }

    }

    @Step("Updating a card on UI with a new name: {newName}")
    public void updateCardName(String newName) {

        $(DEFAULT_FIRST_TASK).click();
        $(CARD_NAME_EDIT_CARD_LOCATOR).clear();
        $(CARD_NAME_EDIT_CARD_LOCATOR).click();
        $(CARD_NAME_EDIT_CARD_LOCATOR).sendKeys(newName);
        $(CARD_NAME_EDIT_CARD_LOCATOR).sendKeys(Keys.ENTER);
        $(CLOSED_EDIT_CARD_LOCATOR).click();

    }

    @Step("Copying a card on UI with a new name: {copiedCardName}")
    public BoardPage copyCard(String copiedCardName) {

        $(DEFAULT_FIRST_TASK).click();
        $(BUTTON_COPY_CARD_IN_EDIT).click();
        $(TEXT_AREA_COPY_CARD).shouldBe(Condition.visible).clear();
        $(TEXT_AREA_COPY_CARD).sendKeys(copiedCardName);
        $(BUTTON_SUBMIT_POPOVER).click();
        $(CLOSED_EDIT_CARD_LOCATOR).click();
        return this;
    }

    @Step("Get actual card name on active board")
    public String getActualCardNameInActiveBoard() {
        String name = $(FIRST_CARD_IN_ACTIVE_BOARD).shouldBe(Condition.visible).getAttribute("outerText");
        return name;
    }

    @Step("Removing a card in the UI with the name: {cardName}")
    public void deleteCard(String cardName) {
        SelenideElement CARD_BY_NAME = $x("//a[text()='" + cardName + "']");
        $(CARD_BY_NAME).shouldBe(Condition.interactable).click();
        $(BUTTON_ARCHIVE_CARD_IN_EDIT).click();
        $(BUTTON_DELETE_CARD_IN_EDIT).click();
        $(BUTTON_CONFIRM_POPOVER).click();

    }

    @Step("Moving a card in the UI with the name: {cardName}")
    public void moveCardByDrag(String cardName) {

        //NEED DEBUG!
        SelenideElement CARD_BY_NAME = $x("//a[text()='" + cardName + "']");
        Actions action = new Actions(ConfProperties.driver);
        action
                .dragAndDrop(CARD_BY_NAME, $(SECOND_LIST_LOCATOR))
                .build()
                .perform();

        /*String locCardByName = "//a[text()='" + cardName + "']";

        WebElement elCardByName = ConfProperties.driver.findElement(By.xpath(locCardByName));
        WebElement elSecondList = ConfProperties.driver.findElement(By.xpath(locSecondList));

        Actions action = new Actions(ConfProperties.driver);
        action
                .dragAndDrop(elCardByName, elSecondList)
                .build()
                .perform();*/

    }

    @Step("Adding a label in the UI to a card: {cardName}")
    public void addLabelToCard(String cardName, String color) {
        $(CARD_BY_NAME(cardName)).click();
        SelenideElement ADD_LABEL_COLOR = $x("//span[@data-color=\"" + color.toLowerCase() + "\"]");
        $(BUTTON_ADD_LABEL_TO_CARD_IN_EDIT).click();
        $(ADD_LABEL_COLOR).click();
        $(BUTTON_CLOSE_POPOVER).click();
        $(CLOSED_EDIT_CARD_LOCATOR).click();
    }

    @Step("Filter in UI by value: {filterText}")
    public void filteredCardOnBoard(String filterText) {
        $(BUTTON_FILTERED).click();
        $(INPUT_FILTER).shouldBe(Condition.visible).sendKeys(filterText);
        $(INPUT_FILTER).shouldHave(value(filterText));
        $(DEFAULT_SECOND_TASK).shouldNotBe(Condition.visible);
        //wait.until(ExpectedConditions.attributeContains(By.xpath(inputFilter), "defaultValue", filterText));
        //wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(defaultSecondTask)));

    }

    @Step("Checking for existence in the card UI: {cardName}")
    public boolean cardByNameIsExist(String cardName) {
        ConfProperties.LOGGER.info("Start check exist card: " + cardName);
        boolean result;
        try {
            result = $(CARD_BY_NAME(cardName)).isDisplayed();
            ConfProperties.LOGGER.info("Find card by name :" + cardName);
        } catch (NoSuchElementException ex) {
            result = false;
            ConfProperties.LOGGER.info("Card not found by name :" + cardName);
        }
        return result;
    }

    //TODO need change logic
    @Step("Checking cardInSecondList by UI for card: {cardName}")
    public boolean cardInSecondList(String cardName) {
        String locCardByName = "//span[text()='" + cardName + "']";
        return false;
        /*WebElement elSecondList = ConfProperties.driver.findElement(By.xpath(locSecondList));
        WebElement elCardByName = ConfProperties.driver.findElement(By.xpath(locCardByName));
        if (elCardByName.getLocation() == elSecondList.getLocation()) {
            ConfProperties.LOGGER.info("Место первого: " + elCardByName.getLocation());
            ConfProperties.LOGGER.info("Место второго: " + elSecondList.getLocation());
            ConfProperties.LOGGER.info(cardName + " In second List");
            return true;
        } else {
            ConfProperties.LOGGER.info(cardName + " In first List");
            ConfProperties.LOGGER.info("Иначе Место первого: " + elCardByName.getLocation());
            ConfProperties.LOGGER.info("Иначе Место второго: " + elSecondList.getLocation());
            return false;
        }*/
    }

    @Step("Checking for label presence in UI color: {color}")
    public boolean checkLabelOnCardExists(String color) {
        SelenideElement LABEL_COLOR_LOCATOR = $x("//button[@data-color=\"" + color + "\"]");
        boolean result;

        try {
            result = $(LABEL_COLOR_LOCATOR).isDisplayed();
            ConfProperties.LOGGER.info("Find label: " + color);
        } catch (NoSuchElementException ex) {
            result = false;
            ConfProperties.LOGGER.info("Label: " + color + " not found!");
        }
        return result;

    }

    @Step("Checking the display of a card in UI by name: {cardName}")
    public boolean cardIsDisplayed(String cardName) {
        ConfProperties.LOGGER.info("Start check card is displayed: " + cardName);
        boolean result;

        try {
            result = $(CARD_BY_NAME(cardName)).isDisplayed();
            ConfProperties.LOGGER.info("Card isn't Displayed: " + cardName);
        } catch (NoSuchElementException ex) {
            result = true;
            ConfProperties.LOGGER.info("Card is Displayed :" + cardName);
        }
        return result;
    }

    //columns
    @Step("Creating a Column on UI with a name: {nameColumn}")
    public void createColumn(String nameColumn) {
        $(BUTTON_ADD_NEW_COLUMN).click();
        $(INPUT_COLUMN_NAME).shouldBe(Condition.interactable).sendKeys(nameColumn);
        $(BUTTON_SUBMIT_ADD_COLUMN).click();
    }

    @Step("Checking the display of a column in UI by name: {name}")
    public boolean columnByNameIsDisplayed(String name) {
        SelenideElement FIND_COLUMN_LOCATOR = $x("//*[@aria-label=\"" + name + "\"]");
        boolean res;
        ConfProperties.LOGGER.info("Start searching column = " + name);
        try {
            res = $(FIND_COLUMN_LOCATOR).isDisplayed();
            ConfProperties.LOGGER.info("Column find!");
        } catch (NoSuchElementException ex) {
            ConfProperties.LOGGER.info("Column not find!");
            res = false;
        }
        return res;
    }

}
