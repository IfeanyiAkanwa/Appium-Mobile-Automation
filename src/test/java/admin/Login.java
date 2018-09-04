package admin;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;
import utils.TestBase;


public class Login extends TestBase {
	
	@Test
    public void UsernamePasswordTest() throws InterruptedException {
        Thread.sleep(500);
        WebDriverWait wait = new WebDriverWait(getDriver(), 30);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Login']")));
        getDriver().findElement(By.id("com.seamfix.biocapture.activity:id/otp_login")).click();
        Thread.sleep(1000);
        getDriver().findElement(By.id("com.seamfix.biocapture.activity:id/login_username")).clear();
        getDriver().findElement(By.id("com.seamfix.biocapture.activity:id/login_username")).sendKeys("oosifala@seamfix.com");
        getDriver().findElement(By.id("com.seamfix.biocapture.activity:id/login_password")).clear();
        getDriver().findElement(By.id("com.seamfix.biocapture.activity:id/login_password")).sendKeys("bankole1!!");
        getDriver().findElement(By.id("com.seamfix.biocapture.activity:id/submit")).click();
        Thread.sleep(1000);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Home']")));
    }

}
