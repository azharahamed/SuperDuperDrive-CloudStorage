package com.udacity.jwdnd.course1.cloudstorage.Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SignUpPage {

    @FindBy(name = "firstName")
    private WebElement firstName;
    @FindBy(name = "lastName")
    private WebElement lastName;
    @FindBy(name = "username")
    private WebElement userName;
    @FindBy(name = "password")
    private WebElement password;
    @FindBy(id = "signupbutton")
    private WebElement signupbutton;

    public SignUpPage(WebDriver webDriver){
        PageFactory.initElements(webDriver, this);
    }

    public void signUpUser(String firstName, String lastName, String userName, String password){
        this.firstName.sendKeys(firstName);
        this.lastName.sendKeys(lastName);
        this.userName.sendKeys(userName);
        this.password.sendKeys(password);
        this.signupbutton.click();
    }
}
