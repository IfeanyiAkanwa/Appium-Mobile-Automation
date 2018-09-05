package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.aventstack.extentreports.Status;
import enums.TargetTypeEnum;
import io.appium.java_client.TouchAction;
import org.apache.commons.codec.binary.Base64;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.Assert;

public class TestUtils extends TestBase {

    @SuppressWarnings("resource")
    public static String addScreenshot() {

        TakesScreenshot ts = (TakesScreenshot) getDriver();
        File scrFile = ts.getScreenshotAs(OutputType.FILE);

        String encodedBase64 = null;
        FileInputStream fileInputStreamReader = null;
        try {
            fileInputStreamReader = new FileInputStream(scrFile);
            byte[] bytes = new byte[(int) scrFile.length()];
            fileInputStreamReader.read(bytes);
            encodedBase64 = new String(Base64.encodeBase64(bytes));


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "data:image/png;base64," + encodedBase64;
    }

    /**
     * @param type
     * @param element
     * @param value
     * @description to check if the expected text is present in the page.
     */
    public static void assertSearchText(String type, String element, String value) {

        StringBuffer verificationErrors = new StringBuffer();
        TargetTypeEnum targetTypeEnum = TargetTypeEnum.valueOf(type);
        String ttype = null;

        switch (targetTypeEnum) {
            case ID:
                ttype = getDriver().findElement(By.id(element)).getText();
                break;
            case NAME:
                ttype = getDriver().findElement(By.name(element)).getText();
                break;
            case CSS:
                ttype = getDriver().findElement(By.cssSelector(element)).getText();
                break;

            case XPATH:
                ttype = getDriver().findElement(By.xpath(element)).getText();
                break;
            case CLASS:
                ttype = getDriver().findElement(By.className(element)).getText();
                break;

            default:
                ttype = getDriver().findElement(By.id(element)).getText();
                break;
        }

        try {
            Assert.assertEquals(ttype, value);
            testInfo.get().log(Status.INFO, value + " found");
        } catch (Error e) {
            verificationErrors.append(e.toString());
            String verificationErrorString = verificationErrors.toString();
            testInfo.get().error(value + " not found");
            testInfo.get().error(verificationErrorString);
        }
    }

    public static void scroll(int fromX, int fromY, int toX, int toY) {
        TouchAction touchAction = new TouchAction(getDriver());
        touchAction.longPress(fromX, fromY).moveTo(toX, toY).release().perform();
    }

    public static void scrollDown() {
        int pressX = getDriver().manage().window().getSize().width / 2;
        int bottomY = getDriver().manage().window().getSize().height * 4 / 5;
        int topY = getDriver().manage().window().getSize().height / 8;
        scroll(pressX, bottomY, pressX, topY);
    }

    public static void hideKeyboard() throws InterruptedException {
        if(getDriver().isKeyboardShown()) {
            getDriver().hideKeyboard();
            Thread.sleep(500);
        }
    }
}
