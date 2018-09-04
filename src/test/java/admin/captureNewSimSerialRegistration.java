package admin;

import utils.TestBase;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

public class captureNewSimSerialRegistration extends TestBase {
	
	@Test
    public void navigateToCaptureMenuTest() throws InterruptedException {
	Thread.sleep(500);
    WebDriverWait wait = new WebDriverWait(getDriver(), 30);
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Home']")));
    Thread.sleep(500);
    getDriver().findElement(By.className("android.widget.ImageButton")).click();
    Thread.sleep(500);
    getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='Capture Subscriber']")).click();
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='[Select Registration Type]']")));
    getDriver().findElement(By.id("android:id/text1")).click();
    Thread.sleep(500);
    getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='New Registration (Sim Serial)']")).click();
    Thread.sleep(500);
    getDriver().findElement(By.id("com.seamfix.biocapture.activity:id/next_button")).click();
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.seamfix.biocapture.activity:id/add_sim_serial")));
    
    //Enter SIM Serial
    getDriver().findElement(By.id("com.seamfix.biocapture.activity:id/sim_serial_field")).clear();
    getDriver().findElement(By.id("com.seamfix.biocapture.activity:id/sim_serial_field")).sendKeys("11111111111111111111");
    
    Thread.sleep(500);
    getDriver().findElement(By.id("com.seamfix.biocapture.activity:id/add_sim_serial")).click();
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/button1")));
    getDriver().findElement(By.id("android:id/button1")).click(); // ok button
    Thread.sleep(1000);
    getDriver().findElement(By.id("com.seamfix.biocapture.activity:id/next_button")).click(); // ok button
    Thread.sleep(1000);
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Personal Details']")));
    
	}
}
