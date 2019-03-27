package admin;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;

import utils.TestBase;
import utils.TestUtils;


public class Login extends TestBase {
	
	@Test
	public void loginWithFingerprint() {
		 WebDriverWait wait = new WebDriverWait(getDriver(), 30);
		 //Try to login with fingerprint
		 String fpLogin = "Try to login with fingerprint";
			Markup m = MarkupHelper.createLabel(fpLogin, ExtentColor.BLUE);
			testInfo.get().info(m);
		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/finger_print")));
		 getDriver().findElement(By.id("com.sf.biocapture.activity:id/finger_print")).click();
		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/page_sub_title")));
		 TestUtils.assertSearchText("ID", "com.sf.biocapture.activity:id/page_sub_title", "Fingerprint");
		 //Try to login with a user that does not exist
		 getDriver().findElement(By.id("com.sf.biocapture.activity:id/finger_print")).click();
		 
	}
	@Test
    public void UsernamePasswordTest() throws InterruptedException {
        Thread.sleep(500);
        WebDriverWait wait = new WebDriverWait(getDriver(), 30);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Login']")));
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/otp_login")).click();
        Thread.sleep(1000);
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/login_username")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/login_username")).sendKeys("snapshot@seamfix.com");
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/login_password")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/login_password")).sendKeys("bankole1!!");
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/submit")).click();
        Thread.sleep(1000);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Home']")));
    }

}
