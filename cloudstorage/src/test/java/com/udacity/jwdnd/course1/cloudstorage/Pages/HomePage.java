package com.udacity.jwdnd.course1.cloudstorage.Pages;

import com.udacity.jwdnd.course1.cloudstorage.model.Notes;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage {
    private WebDriver driver;
    @FindBy(xpath = "//button[@type='submit']")
    private WebElement logoutButton;
    @FindBy(xpath = "//a[@href='#nav-notes']")
    private WebElement notesTab;
    @FindBy(xpath = "//a[@id='nav-credentials-tab']")
    private WebElement credentialsTab;
    @FindBy(xpath = "//button[@onclick='showNoteModal()']")
    private WebElement addNewNote;
    @FindBy(xpath = "//button[@onclick='showCredentialModal()']")
    private WebElement addNewCredential;
    @FindBy(xpath = "//input[@id='note-title']")
    private WebElement noteTitleEdit;
    @FindBy(xpath = "//textarea[@id='note-description']")
    private WebElement noteDescriptionEdit;
    @FindBy(xpath = "//input[@id='credential-url']")
    private WebElement credentialsUrlEdit;
    @FindBy(xpath = "//input[@id='credential-username']")
    private WebElement credentialsUsernameEdit;
    @FindBy(xpath = "//input[@id='credential-password']")
    private WebElement credentialsPasswordEdit;
    @FindBy(xpath = "//button[@id='credentails-save-button']")
    private WebElement credentialsSaveButton;
    @FindBy(xpath = "//button[@id='note-save-button']")
    private WebElement noteSaveButton;
    @FindBy(xpath ="//button[@id='noteEditButton']")
    private WebElement noteEditButton;
    @FindBy(xpath = "//a[@id='noteDeleteButton']")
    private WebElement noteDeleteButton;
    @FindBy(xpath ="//button[@id='credentialsEditButton']")
    private WebElement credentialsEditButton;
    @FindBy(xpath = "//a[@id='credentialsDeleteButton']")
    private WebElement credentialsDeleteButton;

    public HomePage(WebDriver webDriver){
        driver = webDriver;
        PageFactory.initElements(webDriver, this);
    }

    private void waitForVisibility(WebElement element) throws Error {
        new WebDriverWait(driver, 20)
                .until(ExpectedConditions.visibilityOf(element));
    }

    public void goToNotesTab(){
        waitForVisibility(notesTab);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", notesTab);
    }

    public void goToCredentialsTab(){
        waitForVisibility(credentialsTab);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", credentialsTab);
    }

    public void setAddNewNote(String noteTitle, String noteDescription){
        goToNotesTab();
        waitForVisibility(addNewNote);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", addNewNote);
        saveNote(noteTitle, noteDescription);
    }

    private void saveNote(String noteTitle, String noteDescription) {
        waitForVisibility(noteTitleEdit);
        this.noteTitleEdit.sendKeys(noteTitle);
        this.noteDescriptionEdit.sendKeys(noteDescription);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", this.noteSaveButton);
    }

    public void editNote(String noteTitle, String noteDescription) {
        waitForVisibility(noteEditButton);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", noteEditButton);
        saveNote(noteTitle, noteDescription);
    }

    public void deleteNote() {
        waitForVisibility(noteDeleteButton);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", noteDeleteButton);
    }

    public void setAddNewCredentails(String url,String username,String password) {
        goToCredentialsTab();
        waitForVisibility(addNewCredential);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", addNewCredential);
        saveCredentials(url,username,password);
    }

    private void saveCredentials(String url, String username, String password) {
        waitForVisibility(credentialsUrlEdit);
        this.credentialsUrlEdit.sendKeys(url);
        this.credentialsUsernameEdit.sendKeys(username);
        this.credentialsPasswordEdit.sendKeys(password);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", this.credentialsSaveButton);
    }

    public void editCredentails(String url, String username, String password) {
        waitForVisibility(credentialsEditButton);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", credentialsEditButton);
        saveCredentials(url, username, password);
    }

    public void deleteCredentials() {
        waitForVisibility(credentialsDeleteButton);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", credentialsDeleteButton);
    }

    public boolean getPasswordText(String passwordText) {
        waitForVisibility(credentialsEditButton);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", credentialsEditButton);
        waitForVisibility(credentialsPasswordEdit);
        return (passwordText.equals(this.credentialsPasswordEdit.getAttribute("value")));
    }
}
