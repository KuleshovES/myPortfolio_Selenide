package org.trelloproject.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.trelloproject.ConfProperties;

import java.time.Duration;


public class LoginPage {

    private static String userName = "testuser@senthy.com";
    private static String userPass = "asdfg1234";
    private static final WebDriverWait wait = new WebDriverWait(ConfProperties.driver, Duration.ofSeconds(10));

    private static String buttonViewAllBoards = "//button[contains(@class, 'view-all-closed-boards-button')]";

    @Step("LogIn")
    public static void login() throws InterruptedException {

        ConfProperties.driver.get("https://trello.com/en/login");
        Thread.sleep(1000);

        wait.until(ExpectedConditions.elementToBeClickable(ConfProperties.driver.findElement(By.id("user"))))
                .sendKeys(userName);

        wait.until(ExpectedConditions.elementToBeClickable(ConfProperties.driver.findElement(By.id("login"))))
                .click();

        Thread.sleep(1000); //BAD!
        wait.until(ExpectedConditions.elementToBeClickable(ConfProperties.driver.findElement(By.id("password"))))
                .sendKeys(userPass);

        wait.until(ExpectedConditions.elementToBeClickable(ConfProperties.driver.findElement(By.id("login-submit"))))
                .click();

        Thread.sleep(5000); //BAD!
        //wait.until(ExpectedConditions.elementToBeClickable(By.xpath(buttonViewAllBoards)));
    }



}
