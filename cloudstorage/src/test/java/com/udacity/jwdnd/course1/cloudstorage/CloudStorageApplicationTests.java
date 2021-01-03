package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.Pages.HomePage;
import com.udacity.jwdnd.course1.cloudstorage.Pages.LoginPage;
import com.udacity.jwdnd.course1.cloudstorage.Pages.SignUpPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

	@LocalServerPort
	private int port;

	private WebDriver driver;

	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	public void beforeEach() {
		this.driver = new ChromeDriver();
	}

	@AfterEach
	public void afterEach() {
		if (this.driver != null) {
			driver.get("http://localhost:" + this.port + "/logout");
			driver.quit();
		}
	}

	@Test
	public void getLoginPage() {
		driver.get("http://localhost:" + this.port + "/login");
		Assertions.assertEquals("Login", driver.getTitle());
	}

	@Test
	public void getHomePageWithoutLogin(){
		driver.get("http://localhost:" + this.port + "/home");
		Assertions.assertNotEquals("Home", driver.getTitle());
	}

	@Test
	public void loginAndAccessHomePage(){
		driver.get("http://localhost:" + this.port + "/login");
		newSignUpAndLogin();
		assertEquals("Home", driver.getTitle());
	}

	@Test
	public void testSaveANote() throws InterruptedException {
		newSignUpAndLogin();
		HomePage homePage = new HomePage(driver);
		homePage.setAddNewNote("Title 1", "Test Description");

		driver.get("http://localhost:" + this.port + "/home");
		homePage.goToNotesTab();

		WebElement notesTab = driver.findElement(By.xpath("//a[@id='nav-notes-tab']"));
		assertThat(notesTab.getText().contains("Title 1"));
		assertThat(notesTab.getText().contains("Test Description"));
	}

	@Test
	public void testSaveCredentials() {
		newSignUpAndLogin();
		HomePage homePage = new HomePage(driver);
		homePage.setAddNewCredentails("www.udacity.com","azhar","testing");
		driver.get("http://localhost:" + this.port + "/home");
		homePage.goToCredentialsTab();
		WebElement credentialsTab = driver.findElement(By.xpath("//a[@id='nav-credentials-tab']"));
		assertThat(credentialsTab.getText().contains("www.udacity.com"));
		assertThat(credentialsTab.getText().contains("azhar"));
		assertFalse(credentialsTab.getText().contains("testing"));
	}

	@Test
	public void testEditANote() {
		newSignUpAndLogin();
		HomePage homePage = new HomePage(driver);
		homePage.setAddNewNote("Title 1", "Test Description");

		driver.get("http://localhost:" + this.port + "/home");
		homePage.goToNotesTab();

		homePage.editNote("Updated Title 1", "Updated Test Description");

		driver.get("http://localhost:" + this.port + "/home");
		homePage.goToNotesTab();
		WebElement notesTab = driver.findElement(By.xpath("//a[@id='nav-notes-tab']"));
		assertThat(notesTab.getText().contains("Updated Title 1"));
		assertThat(notesTab.getText().contains("Updated Test Description"));
	}

	@Test
	public void testEditCredentials() {
		newSignUpAndLogin();
		HomePage homePage = new HomePage(driver);
		homePage.setAddNewCredentails("www.udacity.com","azhar","testing");
		driver.get("http://localhost:" + this.port + "/home");
		homePage.goToCredentialsTab();
		homePage.editCredentails("www.udacity.com","azhar1","testing1");
		driver.get("http://localhost:" + this.port + "/home");
		homePage.goToCredentialsTab();
		WebElement credentialsTab = driver.findElement(By.xpath("//a[@id='nav-credentials-tab']"));
		assertThat(credentialsTab.getText().contains("www.udacity.com"));
		assertThat(credentialsTab.getText().contains("azhar1"));
		assertFalse(credentialsTab.getText().contains("testing1"));
	}

	@Test
	void testCredentialsPasswordDisplay(){
		newSignUpAndLogin();
		HomePage homePage = new HomePage(driver);
		homePage.setAddNewCredentails("www.udacity.com","azhar","testing");
		driver.get("http://localhost:" + this.port + "/home");
		homePage.goToCredentialsTab();
		assertTrue(homePage.getPasswordText("testing"));
	}

	@Test
	public void testDeleteNote(){
		newSignUpAndLogin();
		HomePage homePage = new HomePage(driver);
		homePage.setAddNewNote("Title 1", "Test Description");

		driver.get("http://localhost:" + this.port + "/home");
		homePage.goToNotesTab();

		homePage.deleteNote();

		driver.get("http://localhost:" + this.port + "/home");
		homePage.goToNotesTab();

		WebElement notesTab = driver.findElement(By.xpath("//a[@id='nav-notes-tab']"));
		assertFalse(notesTab.getText().contains("Title 1"));
		assertFalse(notesTab.getText().contains("Test Description"));
	}

	@Test
	public void testDeleteCredentials(){
		newSignUpAndLogin();
		HomePage homePage = new HomePage(driver);
		homePage.setAddNewCredentails("www.udacity.com","azhar","testing");
		driver.get("http://localhost:" + this.port + "/home");
		homePage.goToCredentialsTab();
		homePage.deleteCredentials();
		driver.get("http://localhost:" + this.port + "/home");
		homePage.goToCredentialsTab();
		WebElement credentialsTab = driver.findElement(By.xpath("//a[@id='nav-credentials-tab']"));
		assertThat(credentialsTab.getText().contains("www.udacity.com"));
		assertThat(credentialsTab.getText().contains("azhar"));
		assertFalse(credentialsTab.getText().contains("testing"));
	}

	private void newSignUpAndLogin(){
		driver.get("http://localhost:" + this.port + "/signup");
		SignUpPage signUpPage = new SignUpPage(driver);
		signUpPage.signUpUser("Azhar", "Ahamed", "azu","testing");

		driver.get("http://localhost:" + this.port + "/login");
		LoginPage loginPage = new LoginPage(driver);
		loginPage.login("azu", "testing");
	}


}
