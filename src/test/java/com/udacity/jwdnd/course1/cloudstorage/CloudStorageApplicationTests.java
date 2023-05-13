package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Order;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import javax.validation.constraints.AssertTrue;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import static java.lang.Thread.sleep;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CloudStorageApplicationTests {
	@LocalServerPort
	private int port;
	private static WebDriver driver;
	private static String baseUrl;
	private final String fname = "huy";
	private final String lname = "ngoc";
	private final String uname = "lan";
	private final String pword = "trung";
	private final String noteTitle_org = "org note title";
	private final String noteTitle_upt = "upt note title";
	private final String noteDes_org = "org note des";
	private final String noteDes_upt = "upt note des";
	private final String[] urls = new String[] { "nfl.com", "ncaa.com", "nba.com" };
	private final String[] unames = new String[] { "brady", "cane", "james" };
	private final String[] pws = new String[] { "sb", "nc", "wc" };
	private final String[] urls_upt = new String[] { "nflball.com", "ncaaball.com", "nbaball.com" };
	private final String[] unames_upt = new String[] { "tombrady", "hurricane", "kingjames" };
	private final String[] pws_upt = new String[] { "sbfb", "ncuni", "wcbb" };

	@Autowired
	private EncryptionService encryptionService;
	@Autowired
	private CredentialService credentialService;

	private Logger logger = LoggerFactory.getLogger(CloudStorageApplicationTests.class);

	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
	}

	@AfterAll
	public static void afterAll() {
		if (driver != null) {
			driver.quit();
		}
	}

	@BeforeEach
	public void beforeEach() throws InterruptedException {
		baseUrl = "http://localhost:" + this.port;
		sleep(1000);
	}

	@AfterEach
	public void takeABreak() throws InterruptedException {
		sleep(2000);
	}

	@Test
	@Order(1)
	public void a_getLoginPage() throws InterruptedException {
		driver.get(baseUrl + "/login");
		Assertions.assertEquals("Login", driver.getTitle());
		sleep(2000);
		driver.get(baseUrl + "/signup");
		Assertions.assertEquals("Sign Up", driver.getTitle());
		sleep(2000);
		driver.get(baseUrl + "/home");
		Assertions.assertNotEquals("Home", driver.getTitle());
	}

	@Test
	@Order(2)
	public void b_signupSuccess() throws InterruptedException {
		logger.error("test 2 -signup");
		driver.get(baseUrl + "/signup");
		SignupPage signupPage = new SignupPage(driver);
		signupPage.signUpNow(fname, lname, uname, pword);
		sleep(4000);
		assertEquals("Login", driver.getTitle());
	}

	@Test
	@Order(3)
	public void c_loginSuccess() throws Exception {
		logger.error("test 3 -login-logout-login again");
		driver.get(baseUrl + "/login");
		LoginPage loginPage = new LoginPage(driver);
		loginPage.LoginNow(uname, pword);
		sleep(1000);
		Assertions.assertEquals("Home", driver.getTitle());
		driver.get(baseUrl + "/home");
		HomePage homePage = new HomePage(driver);
		homePage.clickLogoutBtn();
		sleep(1000);
		Assertions.assertNotEquals("Home", driver.getTitle());
		driver.get(baseUrl + "/login");
		loginPage.LoginNow(uname, pword);
		sleep(1000);
		Assertions.assertEquals("Home", driver.getTitle());

	}

	public void waitForVisibility(String id) {
		WebDriverWait wait = new WebDriverWait(driver, 4000);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(id)));

	}

	@Test
	@Order(4)
	public void testNotes() throws Exception {
		logger.error("test 4 -Notes");
		driver.get(baseUrl + "/home");
		NotePage notePage = new NotePage(driver);
		waitForVisibility(notePage.getNoteTabId());
		notePage.clickNoteTab();
		waitForVisibility(notePage.getAddNoteBtnId());
		notePage.clickAddNoteBtn();
		notePage.inputNoteTitle(noteTitle_org);
		notePage.inputNoteDescription(noteDes_org);
		sleep(2000);
		notePage.submitNote();
		sleep(2000);
		waitForVisibility(notePage.getNoteTabId());
		notePage.clickNoteTab();
		Assertions.assertEquals(notePage.getNoteTitleDisplay(), noteTitle_org);
		Assertions.assertEquals(notePage.getNoteDesDisplay(), noteDes_org);
		sleep(3000);
		notePage.clickNoteEditBtn();
		sleep(1000);
		notePage.inputNoteTitle(noteTitle_upt);
		notePage.inputNoteDescription(noteDes_upt);
		sleep(2000);
		notePage.submitNote();
		waitForVisibility(notePage.getNoteTabId());
		notePage.clickNoteTab();
		sleep(1000);
		Assertions.assertEquals(notePage.getNoteTitleDisplay(), noteTitle_upt);
		Assertions.assertEquals(notePage.getNoteDesDisplay(), noteDes_upt);
		waitForVisibility(notePage.getNoteTabId());
		notePage.clickNoteTab();
		notePage.clickNoteDeleteBtn();
		sleep(3000);
		waitForVisibility(notePage.getNoteTabId());
		notePage.clickNoteTab();
		sleep(1000);
		Assertions.assertEquals(0, notePage.getNoteEditBtns().size());
	}

	@Test
	@Order(5)
	public void testCredentials() throws Exception {
		logger.error("test 5 - Credentials");
		driver.get(baseUrl + "/home");
		CredentialPage credentialPage = new CredentialPage(driver);
		int total = 3;
		for (int pos = 0; pos < total; pos++) {
			waitForVisibility(credentialPage.getCrenTabId());
			credentialPage.clickCrenTab();
			sleep(1000);
			waitForVisibility(credentialPage.getAddCrenBtnId());
			credentialPage.clickAddCrenBtn();
			credentialPage.inputUrl(urls[pos]);
			credentialPage.inputUserName(unames[pos]);
			credentialPage.inputPasswd(pws[pos]);
			sleep(2000);
			credentialPage.clickCrenSubmitBtn();
			sleep(3000);
		}
		waitForVisibility(credentialPage.getCrenTabId());
		credentialPage.clickCrenTab();
		sleep(1000);
		for (int pos = 0; pos < total; pos++) {
			String displayedUrl = credentialPage.getUrl(pos);
			String displayedUname = credentialPage.getUname(pos);
			String displayedPwd = credentialPage.getPw(pos);
			String key = credentialService.getKeyById(pos + 1);
			displayedPwd = encryptionService.decryptValue(displayedPwd, key);
			Assertions.assertEquals(displayedUrl, urls[pos]);
			Assertions.assertEquals(displayedUname, unames[pos]);
			Assertions.assertEquals(displayedPwd, pws[pos]);
		}

		for (int pos = 0; pos < total; pos++) {
			waitForVisibility(credentialPage.getCrenTabId());
			credentialPage.clickCrenTab();
			sleep(1000);
			credentialPage.clickEditCredBtn(pos);
			sleep(1000);
			credentialPage.inputUrl(urls_upt[pos]);
			credentialPage.inputUserName(unames_upt[pos]);
			Assertions.assertEquals(credentialPage.getPasswdInModal(), pws[pos]);
			credentialPage.inputPasswd(pws_upt[pos]);
			sleep(2000);
			credentialPage.clickCrenSubmitBtn();
			sleep(3000);
		}
		waitForVisibility(credentialPage.getCrenTabId());
		credentialPage.clickCrenTab();
		sleep(1000);
		for (int pos = 0; pos < total; pos++) {
			String displayedUrl = credentialPage.getUrl(pos);
			String displayedUname = credentialPage.getUname(pos);
			String displayedPwd = credentialPage.getPw(pos);
			String key = credentialService.getKeyById(pos + 1);
			displayedPwd = encryptionService.decryptValue(displayedPwd, key);
			Assertions.assertEquals(displayedUrl, urls_upt[pos]);
			Assertions.assertEquals(displayedUname, unames_upt[pos]);
			Assertions.assertEquals(displayedPwd, pws_upt[pos]);
		}

		for (int pos = 0; pos < total; pos++) {
			waitForVisibility(credentialPage.getCrenTabId());
			credentialPage.clickCrenTab();
			sleep(1000);
			credentialPage.clickDeleteCredBtn(0);
			sleep(3000);
		}
		waitForVisibility(credentialPage.getCrenTabId());
		credentialPage.clickCrenTab();
		Assertions.assertEquals(0, credentialPage.getEditBtns().size());
		sleep(2000);

	}
}
