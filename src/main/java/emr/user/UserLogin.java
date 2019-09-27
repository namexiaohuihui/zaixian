package emr.user;


import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;
import toolskit.OpenBrowserDrive;
import toolskit.ReadExcel;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

public class UserLogin {

    public static final String TAG = UserLogin.class.getSimpleName();

    private final long TIME_OUT_SECONDS = 3;
    private WebDriver driver;
    private OpenBrowserDrive openDrive;
    private LoginElement loginElement;
    private myApiTestData testData;

    @BeforeSuite
    public void getDriveBrowser(){

        loginElement = new LoginElement();
        testData = new myApiTestData();
    }

    @BeforeMethod
    public void runDriveBrowser() throws InterruptedException {
        openDrive = new OpenBrowserDrive();
        driver = openDrive.openBrowser();
    }

    @AfterMethod
    public void closeDrive() throws InterruptedException {

//        JavascriptExecutor driver_js = ((JavascriptExecutor) driver);
//        利用js代码在指定的元素上失去焦点
//        driver_js.executeScript("location.reload()");

        openDrive.closeBrowser(3000);
    }

    public By getByAttribute(String elePath, String pathType) {
        By eleBy = null;
        switch (pathType) {
            case "id":
                eleBy = By.id(elePath);
                break;
            case "xpath":
                eleBy = By.xpath(elePath);
                break;
            case "css":
                eleBy = By.cssSelector(elePath);
                break;
            case "name":
                eleBy = By.name(elePath);
                break;
            default:
                eleBy = null;

        }
        return eleBy;
    }

    public WebElement getVisibilityStatus(String elePath, String pathType) {
        By s = getByAttribute(elePath, pathType);
        WebElement username = null;
        try {
            username = new WebDriverWait(driver, TIME_OUT_SECONDS)
                    .until(ExpectedConditions.visibilityOfElementLocated(s));
        } catch (TimeoutException a) {
            a.printStackTrace();
        }

        return username;
    }

    public String getElement(String elePath, String pathType) {
        String errorText;
        WebElement userError = getVisibilityStatus(elePath, pathType);
        if (userError != null) {
            errorText = userError.getText();
        } else {
            errorText = null;
        }

        return errorText;
    }

    public void inputUserInfo(String loginFunction, String userData) {
        try {
            Method getPassErrorMsg = loginElement.getLoginPageElement(loginFunction);

            Map loginMap = (Map) getPassErrorMsg.invoke(loginElement);

            String elePath = (String) loginMap.get("elePath");
            String pathType = (String) loginMap.get("pathType");

            System.out.println("the element: " + loginMap.get("elePath") + " input data " + loginMap.get("pathType"));

            WebElement username = getVisibilityStatus(elePath, pathType);
            username.click();
            username.clear();
            username.sendKeys(userData);

            JavascriptExecutor driver_js = ((JavascriptExecutor) driver);
            // 利用js代码在指定的元素上失去焦点
            driver_js.executeScript("arguments[0].blur();", username);

        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }

    /**
     * 获取输入框中出现错误的提示语
     *
     * @param loginFunction 提示语所在位置的指定函数
     * @param loginErrorFun 提示语内容
     */
    public void getUserErrorInfo(String loginFunction, String loginErrorFun) {
        Method getPassErrorMsg;
        Map loginMap;
        String loginError;
        try {
            // 找到错误提示语位置
            getPassErrorMsg = loginElement.getLoginPageElement(loginFunction);

            loginMap = (Map) getPassErrorMsg.invoke(loginElement);


            String elePath = (String) loginMap.get("elePath");
            String pathType = (String) loginMap.get("pathType");

            // 找到错误提示语
            String errorText = getElement(elePath, pathType);

            // 获取内置提示语进行比较
            getPassErrorMsg = loginElement.getLoginPageElement(loginErrorFun);

            loginError = (String) getPassErrorMsg.invoke(loginElement);

            if (errorText != null && errorText.equals(loginError)) {
                System.out.println(" element error message: " + errorText);
            } else {
                System.out.println(" element No error message: ");
            }

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

    }

    public void clickLogin() {
        driver.findElement(By.cssSelector("form > button:nth-child(4)")).click();

        WebElement userError = new WebDriverWait(driver, 5)
                .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("body > div.el-message.el-message--error")));

        String errorText = userError.getText();
        System.out.println("page pupop error info: " + errorText);
    }


    @Test(description = "用户的登录", dataProvider = "user", dataProviderClass = myApiTestData.class)
    public void mainTest(Map<?, ?> param) {

        inputUserInfo("getLoginUser", (String) param.get("username"));
        getUserErrorInfo("getLoginUserError", "getUserErrorMsg");

        inputUserInfo("getLoginPass", (String) param.get("password"));
        getUserErrorInfo("getLoginPassError", "getPassErrorMsg");

        clickLogin();

    }


}