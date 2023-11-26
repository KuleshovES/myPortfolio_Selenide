package org.trelloproject.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.trelloproject.ConfProperties;

import java.time.Duration;


public class BoardPage {

    private final WebDriverWait wait = new WebDriverWait(ConfProperties.driver, Duration.ofSeconds(3));

    private final String firstCardInActiveBoard = "//*[@data-testid=\"list-cards\"]/li[1]";
    private final String defaultSecondTask = "//a[text()='SecondTask']";

    //private final String buttonAddCard = "//*[@data-testid=\"list-add-card-button\"]";


    //open board
    private final String locFirstList = "//*[@aria-label=\"Backlog\"]";
    private final String locSecondList = "//*[@aria-label=\"To Do\"]";
    private final String buttonFiltered = "//*[@data-testid=\"filter-popover-button\"]";
    private final String inputFilter = "//input[@aria-placeholder]";
    private final String buttonAddNewColumn = ".js-add-list";
    private final String buttonSubmitAddColumn = "//*[@id=\"board\"]/div/form/div/input";
    private final String labelBoardName = "//*[@data-testid=\"board-name-display\"]";
    private final String inputBoardName = "//*[@data-testid=\"board-name-input\"]";

    //block edit Card
    private final String locCardNameEditCard = ".js-card-detail-title-input";
    private final String locClosedEditCard = ".js-close-window";

    private final String buttonCopyCardInEdit = ".js-copy-card";
    private final String buttonArchiveCardInEdit = ".js-archive-card";
    private final String buttonDeleteCardInEdit = ".js-delete-card";
    private final String buttonAddLabelToCardInEdit = ".js-edit-labels";

    //block pop-over
    private final String buttonSubmitPopover = ".js-submit";
    private final String buttonConfirmPopover = ".js-confirm";
    private final String buttonClosePopover = "//*[@data-testid=\"popover-close\"]";
    private final String textAreaCopyCard = "//textarea[@name=\"name\"]";

    //boards
    @Step("Open board on UI by name: {nameBoard}")
    public BoardPage openBoard(String nameBoard) {
        String locatorBoard = "//*[@title=\"" + nameBoard + "\"]";
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(locatorBoard)));
        ConfProperties.driver.findElement(By.xpath(locatorBoard)).click();
        return this;
    }

    @Step("Update board on UI by name: {nameBoard}")
    public void updateBoardName(String newBoardNameUI) {
        ConfProperties.driver.findElement(By.xpath(labelBoardName)).click();
        ConfProperties.driver.findElement(By.xpath(inputBoardName)).sendKeys(newBoardNameUI);
        ConfProperties.driver.findElement(By.xpath(inputBoardName)).sendKeys(Keys.ENTER);
    }



    @Step("Get board name on UI")
    public String getBoardNameUI() {
        return ConfProperties.driver.findElement(By.xpath(labelBoardName)).getAttribute("textContent");
    }

    //cards
    //TODO bad!!!
    @Step("Creating a card on UI with name: {nameCard}")
    public void createCard(String nameCard) throws InterruptedException {

        Thread.sleep(1000); //BAD!
        //ConfProperties.driver.findElement(By.xpath(buttonAddCard)).isDisplayed()
        WebElement buttonAddCard = ConfProperties.driver.findElement(By.xpath("//*[@data-testid=\"list-add-card-button\"]"));

        //ПЕРЕДЕЛАТЬ!
        if (buttonAddCard.isDisplayed()) {
            buttonAddCard.click();
            Thread.sleep(1000); //BAD!
            WebElement inputCardTitle = ConfProperties.driver.findElement(By.xpath("//*[@data-testid=\"list-card-composer-textarea\"]"));
            inputCardTitle.click();
            inputCardTitle.sendKeys(nameCard);
            Thread.sleep(1000); //BAD!
            WebElement submitAddCard = ConfProperties.driver.findElement(By.xpath("//*[@data-testid=\"list-card-composer-add-card-button\"]"));
            submitAddCard.click();
        } else {
            Thread.sleep(1000); //BAD!
            WebElement inputCardTitle = ConfProperties.driver.findElement(By.xpath("//*[@data-testid=\"list-card-composer-textarea\"]"));
            inputCardTitle.click();
            inputCardTitle.sendKeys(nameCard);
            Thread.sleep(1000); //BAD!
            WebElement submitAddCard = ConfProperties.driver.findElement(By.xpath("//*[@data-testid=\"list-card-composer-add-card-button\"]"));
            submitAddCard.click();
        }

    }

    @Step("Updating a card on UI with a new name: {newName}")
    public void updateCardName(String newName) {

        ConfProperties.driver.findElement(By.xpath("//a[text()='FirstTask']")).click();
        ConfProperties.driver.findElement(By.cssSelector(locCardNameEditCard)).click();
        ConfProperties.driver.findElement(By.cssSelector(locCardNameEditCard)).clear();
        ConfProperties.driver.findElement(By.cssSelector(locCardNameEditCard)).sendKeys(newName);
        ConfProperties.driver.findElement(By.cssSelector(locCardNameEditCard)).sendKeys(Keys.ENTER);
        ConfProperties.driver.findElement(By.cssSelector(locClosedEditCard)).click();

    }

    @Step("Copying a card on UI with a new name: {copiedCardName}")
    public BoardPage copyCard(String copiedCardName) {

        ConfProperties.driver.findElement(By.xpath("//a[text()='FirstTask']")).click();
        ConfProperties.driver.findElement(By.cssSelector(buttonCopyCardInEdit)).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(textAreaCopyCard))).clear();
        ConfProperties.driver.findElement(By.xpath(textAreaCopyCard)).sendKeys(copiedCardName);

        ConfProperties.driver.findElement(By.cssSelector(buttonSubmitPopover)).click();
        ConfProperties.driver.findElement(By.cssSelector(locClosedEditCard)).click();
        return this;
    }

    @Step("Get actual card name on active board")
    public String getActualCardNameInActiveBoard() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(firstCardInActiveBoard)));
        String name = ConfProperties.driver.findElement(By.xpath(firstCardInActiveBoard))
                .getAttribute("outerText");
        return name;
    }

    @Step("Removing a card in the UI with the name: {cardName}")
    public void deleteCard(String cardName) {
        String elCardByName = "//a[text()='" + cardName + "']";
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(elCardByName))).click();
        //driver.findElement(By.xpath(elCardByName)).click();
        ConfProperties.driver.findElement(By.cssSelector(buttonArchiveCardInEdit)).click();
        ConfProperties.driver.findElement(By.cssSelector(buttonDeleteCardInEdit)).click();
        ConfProperties.driver.findElement(By.cssSelector(buttonConfirmPopover)).click();

    }
    @Step("Moving a card in the UI with the name: {cardName}")
    public void moveCardByDrag(String cardName) {

        String locCardByName = "//a[text()='" + cardName + "']";

        WebElement elCardByName = ConfProperties.driver.findElement(By.xpath(locCardByName));
        WebElement elSecondList = ConfProperties.driver.findElement(By.xpath(locSecondList));

        Actions action = new Actions(ConfProperties.driver);
        action
                .dragAndDrop(elCardByName, elSecondList)
                .build()
                .perform();

    }

    @Step("Adding a label in the UI to a card: {cardName}")
    public void addLabelToCard(String cardName, String color) {
        String elCardByName = "//a[text()='" + cardName + "']";
        String addLabelColor = "//span[@data-color=\""+ color.toLowerCase() +"\"]";
        ConfProperties.driver.findElement(By.xpath(elCardByName)).click();
        ConfProperties.driver.findElement(By.cssSelector(buttonAddLabelToCardInEdit)).click();

        ConfProperties.driver.findElement(By.xpath(addLabelColor)).click();
        //driver.findElement(By.xpath(addLabelBlue)).click();

        ConfProperties.driver.findElement(By.xpath(buttonClosePopover)).click();
        ConfProperties.driver.findElement(By.cssSelector(locClosedEditCard)).click();
    }

    @Step("Filter in UI by value: {filterText}")
    public void filteredCardOnBoard(String filterText) {
        ConfProperties.driver.findElement(By.xpath(buttonFiltered)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(inputFilter))).sendKeys(filterText);
        wait.until(ExpectedConditions.attributeContains(By.xpath(inputFilter), "defaultValue", filterText));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(defaultSecondTask)));

    }

    @Step("Checking for existence in the card UI: {cardName}")
    public boolean cardByNameIsExist(String cardName) {
        ConfProperties.LOGGER.info("Start check exist card: " + cardName);
        String elCardByName = "//a[text()='" + cardName + "']";
        boolean result;

        try {
            result = (ConfProperties.driver.findElement(By.xpath(elCardByName)).isDisplayed());
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
        WebElement elSecondList = ConfProperties.driver.findElement(By.xpath(locSecondList));
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
        }
    }

    @Step("Checking for label presence in UI color: {color}")
    public boolean checkLabelOnCardExists(String color) {
        String locLabelColor = "//button[@data-color=\"" + color + "\"]";
        boolean result;

        try {
            result = (ConfProperties.driver.findElement(By.xpath(locLabelColor)).isDisplayed());
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
        String elCardByName = "//a[text()='" + cardName + "']";
        boolean result;

        try {
            result = (ConfProperties.driver.findElement(By.xpath(elCardByName)).isDisplayed());
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
        ConfProperties.driver.findElement(By.cssSelector(buttonAddNewColumn)).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".list-name-input")))
                .sendKeys(nameColumn);
        ConfProperties.driver.findElement(By.xpath(buttonSubmitAddColumn)).click();
    }

    @Step("Checking the display of a column in UI by name: {name}")
    public boolean columnByNameIsDisplayed(String name) {
        String locFindColumn = "//*[@aria-label=\"" + name + "\"]";
        boolean res;
        ConfProperties.LOGGER.info("Start searching column = " + name);
        try {
            ConfProperties.driver.findElement(By.xpath(locFindColumn)).isDisplayed();
            ConfProperties.LOGGER.info("Column find!");
            res = true;
        } catch (NoSuchElementException ex) {
            ConfProperties.LOGGER.info("Column not find!");
            res = false;
        }
        return res;
    }

}
