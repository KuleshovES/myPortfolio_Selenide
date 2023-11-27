package org.trelloproject.tests.UI;

import org.trelloproject.pages.LoginPage;
import org.trelloproject.robots.BaseRobot;
import org.trelloproject.entities.User;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Flaky;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.trelloproject.robots.api.RestApiRobot;
import static com.codeborne.selenide.Selenide.closeWebDriver;


public class TestUIAuthorization {
    private BaseRobot baseRobot = new BaseRobot();
    private User user = new User();

    @BeforeMethod
    public void logIn() throws InterruptedException {
        LoginPage.login();
    }

    @Epic(value = "UI")
    @Feature(value = "Tests with Auth")
    @Description(value = "Test check login By UI")
    @Test
    public void sigInUI() {
        Assert.assertEquals(baseRobot.currentUserNameByUI(), RestApiRobot.getInfoUser(user).getFullName());
    }

    @Epic(value = "UI")
    @Feature(value = "Tests with Auth")
    @Description(value = "Test check logout By UI")
    @Flaky
    @Test
    public void logOutUI() throws InterruptedException {
        baseRobot.logOutByUI();
        //Thread.sleep(2000);
        Assert.assertTrue(baseRobot.checkingOpenedMainPageByUI());
    }

    @AfterMethod
    public void driverClose() {
        closeWebDriver();
    }
}
