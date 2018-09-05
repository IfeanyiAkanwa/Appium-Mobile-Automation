package admin;

import io.appium.java_client.android.Activity;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;
import utils.TestBase;
import utils.TestUtils;

public class AgentOnBoardingTest extends TestBase {
    @Test
    public static void navigateToAgentOnBoardingPage() {

        WebDriverWait wait = new WebDriverWait(getDriver(), 30);

//        getDriver().startActivity(new Activity("com.sf.biocapture.activity", "com.sf.biocapture.activity.NewHomeActivity"));

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Home']")));
        getDriver().findElement(By.xpath("//android.widget.ImageButton[@content-desc=\"Navigate up\"]")).click();
        getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='Agent OnBoarding']")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/page_title")));
        TestUtils.assertSearchText("ID", "com.sf.biocapture.activity:id/page_title", "Agent Onboarding");
        testInfo.get().info("Successful landing to Agent Onboarding Page");
    }

    @Test
    public static void navigateToDashboard() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(getDriver(), 30);
        getDriver().findElementByAccessibilityId("Navigate up").click();
        Thread.sleep(1000);
        if (getDriver().findElement(By.id("android:id/message")).getText().contains("Do you wish to exit Agent OnBoarding")) {
            getDriver().findElement(By.xpath("//android.widget.Button[@text='YES']")).click();
        }
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Home']")));
    }
}
