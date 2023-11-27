package org.trelloproject.pages;

import com.codeborne.selenide.Condition;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import static com.codeborne.selenide.Selenide.*;

public class LoginPage {

    private static String userName = "testuser@senthy.com";
    private static String userPass = "asdfg1234";

    private static final By USERNAME = By.id("user");
    private static final By LOGIN = By.id("login");
    private static final By PASSWORD = By.id("password");
    private static final By LOGIN_SUBMIT = By.id("login-submit");
    private static final By BUTTON_VIEW_ALL_BOARDS = By.xpath("//button[contains(@class, 'view-all-closed-boards-button')]");

    @Step("LogIn")
    public static void login() throws InterruptedException {

        open("https://trello.com/en/login");
        //Thread.sleep(1000);
        $(USERNAME).shouldBe(Condition.interactable).sendKeys(userName);
        $(LOGIN).shouldBe(Condition.interactable).click();
        $(PASSWORD).shouldBe(Condition.interactable).sendKeys(userPass);
        $(LOGIN_SUBMIT).shouldBe(Condition.interactable).click();
        $(BUTTON_VIEW_ALL_BOARDS).shouldBe(Condition.visible);

    }



}
