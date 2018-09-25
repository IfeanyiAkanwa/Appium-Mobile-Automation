package admin;

import io.appium.java_client.android.Activity;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;
import utils.TestBase;

public class NotificationsTest extends TestBase {
    @Test
    public static void navigateToNotifications() throws InterruptedException {

        WebDriverWait wait = new WebDriverWait(getDriver(), 30);

        getDriver().findElementByAccessibilityId("Navigate up").click();
        Thread.sleep(500);
        getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@Text='Notifications']")).click();
    }

    @Test
    public static void navigateToDashboard() {

    }
}
