package admin;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

import utils.TestBase;
import utils.TestUtils;

public class NotificationsTest extends TestBase {
    @Test
    public static void navigateToNotifications() throws InterruptedException {

    	WebDriverWait wait = new WebDriverWait(getDriver(), 30);

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Home']")));
        getDriver().findElement(By.xpath("//android.widget.ImageButton[@content-desc=\"Navigate up\"]")).click();
        Thread.sleep(500);
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/navigation_item_notification")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Notification']")));
        TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='Notification']", "Notification");
        testInfo.get().info("Successful landing to Notification Page");
    }

    @Test
    public static void viewNotifications() throws InterruptedException {

    	WebDriverWait wait = new WebDriverWait(getDriver(), 30);

    	if (TestUtils.isElementPresent("ID", "com.sf.biocapture.activity:id/message")) {
    		getDriver().findElement(By.id("com.sf.biocapture.activity:id/message")).click();
    		Thread.sleep(1000);
    		wait.until(ExpectedConditions.textToBePresentInElement(getDriver().findElement(By.id("com.sf.biocapture.activity:id/alertTitle")), "Message"));
    		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity:id/alertTitle", "Message");
    		String message = getDriver().findElement(By.id("android:id/message")).getText();
    		testInfo.get().log(Status.INFO,"Message:  " + message);
    		getDriver().findElement(By.id("android:id/button1")).click();
    		Thread.sleep(500);
    	}
    	else
    	{
    		testInfo.get().log(Status.INFO,"Empty Nofications." );
    	}
    	getDriver().findElement(By.className("android.widget.ImageButton")).click();

    }
}
