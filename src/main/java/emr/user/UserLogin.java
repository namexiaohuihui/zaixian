package emr.user;

import org.testng.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

import toolskit.OpenBrowserDrive;
import toolskit.carrier.BrowserDriver;
import toolskit.carrier.ChromeBrowser;
import toolskit.exhibition.ExecuteFramework;
import toolskit.exhibition.InterfaceShow;
import toolskit.incomprehension.AbnormalCodeError;
import toolskit.incomprehension.ResourceNotFoundException;




public class UserLogin {

    public static final String TAG = UserLogin.class.getSimpleName();

    private final long TIME_OUT_SECONDS = 3;
    private WebDriver driver;
    private LoginElement loginElement;
    private LoginExcelData loginData;
    private BrowserDriver browser;
    private InterfaceShow execute;

    @BeforeSuite
    public void getDriveBrowser(){

        loginElement = new LoginElement();
        loginData = new LoginExcelData();

    }

    @BeforeMethod
    public void runDriveBrowser() throws InterruptedException {
        browser = new ChromeBrowser();
        driver = browser.runCarrier("***");
        execute = new ExecuteFramework(driver);
    }

    @AfterMethod
    public void closeDrive() throws InterruptedException {
        JavascriptExecutor driver_js = ((JavascriptExecutor) driver);
//        利用js代码在指定的元素上失去焦点
        driver_js.executeScript("location.reload()");

        browser.closeBrowser(3000);
    }




    public String getElement(String elePath, String pathType) {
        String errorText = execute.getAttributeTextValue(elePath, pathType,null,"text");
        return errorText;
    }

    public void inputUserInfo(String loginFunction, String userData) {
        try {
            Method getPassErrorMsg = loginElement.getLoginPageElement(loginFunction);

            Map loginMap = (Map) getPassErrorMsg.invoke(loginElement);

            String elePath = (String) loginMap.get("elePath");
            String pathType = (String) loginMap.get("pathType");

            System.out.println("the element: " + loginMap.get("elePath") + " input data " + loginMap.get("pathType"));

            WebElement username = execute.VisibleFocus(elePath, pathType);
            execute.HandleVisibleInput("","",username,userData);

            // 利用js代码在指定的元素上失去焦点
            JavascriptExecutor driver_js = ((JavascriptExecutor) driver);
            driver_js.executeScript("arguments[0].blur();", username);

        } catch (InvocationTargetException | IllegalAccessException e) {
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
        // 找到错误提示语位置
        Method getPassErrorMsg = loginElement.getLoginPageElement("getLoginButton");

        Map loginMap = null;
        try {
            loginMap = (Map) getPassErrorMsg.invoke(loginElement);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        String elePath = (String) loginMap.get("elePath");
        String pathType = (String) loginMap.get("pathType");

        WebElement userError = execute.VisibleFocus(elePath, pathType);
        if (userError != null) {
            userError.click();
        } else {
            Assert.assertThrows(NullPointerException.class,
                    ()->{throw new AbnormalCodeError("excel中出现不符合要求的资源:");}); //failed
        }

    }


    /**
     * https://howtodoinjava.com/testng/testng-difference-between-factory-and-dataprovider/
     * dataProvider可以是定义的name名字也可以是函数名
     * @param param
     */
    @Test(description = "用户的登录", dataProvider = "getLoginData", dataProviderClass = LoginExcelData.class,groups={"function-test","unit-test"})
    public void mainTest(Map<?, ?> param) {
        // 输入内容
        inputUserInfo("getLoginUser", (String) param.get("username"));
        inputUserInfo("getLoginPass", (String) param.get("password"));

        // 点击登录按钮
        clickLogin();

        // 判断错误是否出现
        String errorType = (String)param.get("type");
        if ("info".equals(errorType)){
            getUserErrorInfo("getLoginUserError", "getUserErrorMsg");
            getUserErrorInfo("getLoginPassError", "getPassErrorMsg");
        }else if("page".equals(errorType)){
            WebElement userError = new WebDriverWait(driver, 5)
                    .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("body > div.el-message.el-message--error")));

            String errorText = userError.getText();
            System.out.println("page pupop error info: " + errorText);
        }else {
            //failed
            Assert.assertThrows(NullPointerException.class,()->{throw new ResourceNotFoundException();});
        }

    }


}
