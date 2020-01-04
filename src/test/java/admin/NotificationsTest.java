package admin;

import java.io.FileReader;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;

import utils.TestBase;
import utils.TestUtils;

public class NotificationsTest extends TestBase {
    @Test
    public static void navigateToNotifications() throws InterruptedException {

    	WebDriverWait wait = new WebDriverWait(getDriver(), 30);
    	// Navigate to Registration Type
    	String regType = "Navigate to Notification Page";
    	Markup r = MarkupHelper.createLabel(regType, ExtentColor.BLUE);
    	testInfo.get().info(r);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Home']")));
        getDriver().findElement(By.xpath("//android.widget.ImageButton[@content-desc=\"Navigate up\"]")).click();
        Thread.sleep(500);
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/navigation_item_notification")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Notification']")));
        TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='Notification']", "Notification");
    }

    @Parameters({ "dataEnv"})
    @Test
    public static void viewNotifications(String dataEnv) throws Exception {

		WebDriverWait wait = new WebDriverWait(getDriver(), 30);
		JSONParser parser = new JSONParser();
		JSONObject config = (JSONObject) parser
				.parse(new FileReader("src/test/resource/" + dataEnv + "/data.conf.json"));
		JSONObject envs = (JSONObject) config.get("Notifications");

		String searchParam = (String) envs.get("searchParam");

		if (TestUtils.isElementPresent("ID", "com.sf.biocapture.activity:id/message")) {
			String invalidPass = "Search before viewing notification: " + searchParam;
			Markup m = MarkupHelper.createLabel(invalidPass, ExtentColor.BLUE);
			testInfo.get().info(m);
			getDriver().findElement(By.id("com.sf.biocapture.activity:id/searchView")).sendKeys(searchParam);
			getDriver().findElement(By.id("com.sf.biocapture.activity:id/message")).click();
			Thread.sleep(1000);
			wait.until(ExpectedConditions.textToBePresentInElement(
					getDriver().findElement(By.id("com.sf.biocapture.activity:id/alertTitle")), "Message"));
			TestUtils.assertSearchText("ID", "com.sf.biocapture.activity:id/alertTitle", "Message");
			String message = getDriver().findElement(By.id("android:id/message")).getText();
			testInfo.get().log(Status.INFO, "Message:  " + message);
			getDriver().findElement(By.id("android:id/button1")).click();
			Thread.sleep(500);
		} else {
			testInfo.get().log(Status.INFO, "Empty Nofications.");
		}
		getDriver().findElement(By.className("android.widget.ImageButton")).click();

	}
}
