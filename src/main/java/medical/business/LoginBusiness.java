package medical.business;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import toolskit.InformationLog;
import toolskit.carrier.BrowserDriver;
import toolskit.carrier.DriverFactory;
import toolskit.exhibition.ExecuteFramework;
import toolskit.exhibition.InterfaceShow;
import toolskit.incomprehension.AbnormalCodeError;
import toolskit.incomprehension.ResourceNotFoundException;
import medical.handles.LoginElement;

public class LoginBusiness {

    public BrowserDriver browser;
    private InterfaceShow execute;
    private LoginElement loginElement;
    private WebDriver driver;


    public LoginBusiness(String driverType,String webUrl) {

        this.browser = DriverFactory.getDriverBrowser(driverType,webUrl);
        this.driver = browser.getDriver();

        this.loginElement = new LoginElement();
        this.execute = new ExecuteFramework(driver);
    }


    private String getElement(String elePath, String pathType) {
        String errorText = execute.getAttributeTextValue(elePath, pathType, null, "text");
        return errorText;
    }

    private void inputUserInfo(String loginFunction, String userData) {
        try {
            Method getPassErrorMsg = loginElement.getLoginPageElement(loginFunction);

            Map loginMap = (Map) getPassErrorMsg.invoke(loginElement);

            String elePath = (String) loginMap.get("elePath");
            String pathType = (String) loginMap.get("pathType");

            InformationLog.inputLogInfo("the element: " + loginMap.get("elePath") + " input data " + loginMap.get("pathType"));

            WebElement username = execute.VisibleFocus(elePath, pathType);
            execute.HandleVisibleInput("", "", username, userData);

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
    private void getUserErrorInfo(String loginFunction, String loginErrorFun) {
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
                InformationLog.inputLogInfo(" element error message: " + errorText);
            } else {
                InformationLog.inputLogInfo(" element No error message: ");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void clickLogin() {
        // 找到错误提示语位置
        Method getPassErrorMsg = loginElement.getLoginPageElement("getLoginButton");

        Map loginMap = null;
        try {
            loginMap = (Map) getPassErrorMsg.invoke(loginElement);
        } catch (Exception e) {
            e.printStackTrace();
        }

        assert loginMap != null;
        String elePath = (String) loginMap.get("elePath");
        String pathType = (String) loginMap.get("pathType");

        WebElement userError = execute.VisibleFocus(elePath, pathType);
        if (userError != null) {
            userError.click();
        } else {
            Assert.assertThrows(NullPointerException.class,
                    () -> {
                        throw new AbnormalCodeError("excel中出现不符合要求的资源:");
                    }); //failed
        }

    }

    public void medicalRecords(Map<?, ?> param) {

        // 输入内容
        inputUserInfo("getLoginUser", (String) param.get("username"));
        inputUserInfo("getLoginPass", (String) param.get("password"));

        // 点击登录按钮
        clickLogin();

        // 判断错误是否出现
        String errorType = (String) param.get("type");
        if ("info".equals(errorType)) {
            getUserErrorInfo("getLoginUserError", "getUserErrorMsg");
            getUserErrorInfo("getLoginPassError", "getPassErrorMsg");
        } else if ("page".equals(errorType)) {
            WebElement userError = new WebDriverWait(driver, 5)
                    .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("body > div.el-message.el-message--error")));

            String errorText = userError.getText();
            InformationLog.inputLogInfo("page pupop error info: " + errorText);
        } else {
            //failed
            Assert.assertThrows(NullPointerException.class, () -> {
                throw new ResourceNotFoundException();
            });
        }
    }
}
