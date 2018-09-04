package admin;

import DemographicForm.Form;
import utils.TestBase;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;
import utils.TestUtils;

public class captureNewSimSerialRegistration extends TestBase {
	
	@Test
    public void navigateToCaptureMenuTest() throws InterruptedException {
	Thread.sleep(500);
    WebDriverWait wait = new WebDriverWait(getDriver(), 30);
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Home']")));
    Thread.sleep(500);
    getDriver().findElement(By.id("com.sf.biocapture.activity:id/button_start_capture")).click();
    Thread.sleep(500);
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Registration Type']")));
    TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='Registration Type']", "Registration Type");
    Thread.sleep(500);
    getDriver().findElement(By.id("com.sf.biocapture.activity:id/linear_layout_username")).click();
    getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='New Registration (Sim Serial)']")).click();
    Thread.sleep(500);
    getDriver().findElement(By.id("com.sf.biocapture.activity:id/next_button")).click();
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/page_title")));
    TestUtils.assertSearchText("ID", "com.sf.biocapture.activity:id/page_title", "New Registration (Serial)");
    

    
	}

	@Test
    public void RegisterNewSimSerial() throws InterruptedException{

	    WebDriverWait wait = new WebDriverWait(getDriver(), 30);

        //Enter SIM Serial
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/sim_serial_field")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/sim_serial_field")).sendKeys("11111111111111111111");
        Thread.sleep(500);
        //Request Dya
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/dya_check_box")).click();
        Thread.sleep(500);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/alertTitle")));
        TestUtils.assertSearchText("ID", "android:id/alertTitle", "[Select Yellow Account Type]");
        getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='DYA']")).click();
        getDriver().findElement(By.id("android:id/button1")).click();
        Thread.sleep(500);
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/add_sim_serial")).click();
        Thread.sleep(500);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/alertTitle")));
        TestUtils.assertSearchText("ID", "android:id/message", "Sim Serial: 11111111111111111111  verified!");
        getDriver().findElement(By.id("android:id/button1")).click(); // ok button
        Thread.sleep(1000);
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/next_button")).click(); // ok button
        Thread.sleep(1000);
        Form.IndividualForeignerForm();

    }
}
