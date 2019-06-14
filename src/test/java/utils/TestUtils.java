package utils;

import com.aventstack.extentreports.Status;
import com.testinium.deviceinformation.helper.ProcessHelper;
import enums.TargetTypeEnum;
import io.appium.java_client.TouchAction;
import org.apache.commons.codec.binary.Base64;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.io.*;
import java.util.Collection;

public class TestUtils extends TestBase {

    @SuppressWarnings("resource")
    public static String addScreenshot() {

        TakesScreenshot ts = getDriver();
        File scrFile = ts.getScreenshotAs(OutputType.FILE);

        String encodedBase64 = null;
        FileInputStream fileInputStreamReader;
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
    public static void scrollUp() {
        int pressX = getDriver().manage().window().getSize().width / 2;
        int bottomY = getDriver().manage().window().getSize().height * 4 / 5;
        int topY = getDriver().manage().window().getSize().height / 8;
        scroll(pressX, topY, pressX, bottomY);
    }

    public static void hideKeyboard() throws InterruptedException {
        if (getDriver().isKeyboardShown()) {
            getDriver().hideKeyboard();
            Thread.sleep(500);
        }
    }

    public static boolean isElementPresent(String elementType, String locator) {

        WebElement elementPresent = null;

        TargetTypeEnum targetTypeEnum = TargetTypeEnum.valueOf(elementType);
        switch (targetTypeEnum) {
            case ID:
                try {
                    elementPresent = getDriver().findElement(By.id(locator));
                } catch (Exception e) {
                }
                break;
            case NAME:
                try {
                    elementPresent = getDriver().findElement(By.name(locator));
                } catch (Exception e) {
                }
                break;
            case CSS:
                try {
                    elementPresent = getDriver().findElement(By.cssSelector(locator));
                } catch (Exception e) {
                }
                break;
            case XPATH:
                try {
                    elementPresent = getDriver().findElement(By.xpath(locator));
                } catch (Exception e) {
                }
                break;
            default:
                try {
                    elementPresent = getDriver().findElement(By.id(locator));
                } catch (Exception e) {
                }
        }
        if (elementPresent != null) {
            return true;
        } else {
            return false;
        }
    }

    public static void scrollUntilElementIsVisible(String elementType, String locator) throws InterruptedException {
        Thread.sleep(1000);
        while (!TestUtils.isElementPresent(elementType, locator)) {
            scrollDown();
        }
    }

    //Possible solution to interacting with Android Internal or External memory
    public static String executeAdbCommand(String command) throws IOException {
        Process process = null;
        StringBuilder builder = new StringBuilder();
        String commandString;
        commandString = String.format("%s", command);
        //System.out.print("Command is " + commandString + "\n");
        try {
            process = ProcessHelper.runTimeExec(commandString);
        } catch (IOException e) {
        }
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line;
        while ((line = reader.readLine()) != null) {
            System.out.print(line + "\n");
            builder.append(line);
        }
        return builder.toString();
    }
    
    public static boolean validateParams(Object... params) {

        for (Object param : params) {
            if (param == null) {
                return false;
            }

            if (param instanceof String && ((String) param).isEmpty()) {
                return false;
            }

            if (param instanceof Collection<?> && ((Collection<?>) param).isEmpty()) {
                return false;
            }

            if (param instanceof Long && ((Long) param).compareTo(0L) == 0) {
                return false;
            }
            if (param instanceof Double && ((Double) param).compareTo(0D) == 0) {
                return false;
            }

            if (param instanceof Integer && ((Integer) param).compareTo(0) == 0) {
                return false;
            }

        }

        return true;
    }
    
    public static Integer convertToInt(String value) throws InterruptedException {
        Integer result = null;
        String convertedString = value.replaceAll("[^0-9]", "");
        if (validateParams(convertedString)) {
            try {
                return result = Integer.parseInt(convertedString);
            } catch (NumberFormatException e) {
                testInfo.get().error("convertToInt  Error converting to integer ");
                testInfo.get().error(e);
            }
        }
        return result;

    }

}
