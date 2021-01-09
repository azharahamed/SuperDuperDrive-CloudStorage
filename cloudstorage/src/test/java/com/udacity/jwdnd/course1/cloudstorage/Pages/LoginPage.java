package com.udacity.jwdnd.course1.cloudstorage.Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {

    private WebDriver driver;

    @FindBy(id="inputUsername")
    private WebElement inputUsername;
    @FindBy(id="inputPassword")
    private WebElement inputPassword;
    @FindBy(id="signup-link")
    private WebElement signuplink;
    @FindBy(id="submitbutton")
    private WebElement submitbutton;

    public LoginPage(WebDriver webDriver){
        driver = webDriver;
        PageFactory.initElements(webDriver, this);
    }

    public void login(String username, String password){
        inputUsername.sendKeys(username);
        inputPassword.sendKeys(password);
        submitbutton.click();
    }

    private void waitForVisibility(WebElement element) throws Error {
        new WebDriverWait(driver, 60)
                .until(ExpectedConditions.visibilityOf(element));
    }

    public void signUpUser() {
        waitForVisibility(signuplink);
        signuplink.click();
    }

}
