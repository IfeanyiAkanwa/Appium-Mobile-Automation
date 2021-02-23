package admin;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import utils.TestBase;
import utils.TestUtils;

import java.io.FileReader;

public class AgentSupport extends TestBase {

    @Parameters({ "dataEnv"})
    @Test
    public void navigateToAgentSupport(String dataEnv) throws Exception {
        WebDriverWait wait = new WebDriverWait(getDriver(), 30);

        //Check that Home Page is displayed
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Home']")));

        //Click the Menu Button
        getDriver().findElement(By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity.glo:id/design_menu_item_text")));

        //Confirm Agent Support is on the Menu
        TestUtils.assertSearchText("XPATH", "//android.widget.CheckedTextView[@text='Agent Support']", "Agent Support");

        //Click Agent Support
        getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='Agent Support']")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Agent Support']")));

        //Confirm Agent Support View is displayed
        TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='Agent Support']", "Agent Support");
    }
}
