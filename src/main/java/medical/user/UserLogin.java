package medical.user;

import java.util.Map;

import org.testng.annotations.*;

import medical.business.LoginBusiness;
import medical.handles.LoginElement;

public class UserLogin {

    private LoginBusiness lb;

    @BeforeMethod
    public void runDriveBrowser() {
        this.lb = new LoginBusiness("chrome","***");

    }

    @AfterMethod
    public void closeDrive() {
        lb.browser.closeBrowser(1000);
    }


    /**
     * dataProvider可以是定义的name名字也可以是函数名
     * @param param 单行excel数据
     */
    @Test(description = "用户的登录", dataProvider = "getLoginData", dataProviderClass = LoginElement.class)
    public void mainTest(Map<?, ?> param) {
        lb.medicalRecords(param);
    }


}
