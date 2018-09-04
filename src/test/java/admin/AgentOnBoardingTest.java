package admin;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;
import utils.TestBase;

public class AgentOnBoardingTest extends TestBase {
    @Test
    public static void navigateToAgentOnBoardingPage() {

        WebDriverWait wait = new WebDriverWait(getDriver(), 30);

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Home']")));
        getDriver().findElement(By.xpath("//android.widget.ImageButton[@content-desc=\"Navigate up\"]"));
        getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@Text='Agent OnBoarding']")).click();
    }
}
