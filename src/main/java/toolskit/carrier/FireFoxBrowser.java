package toolskit.carrier;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class FireFoxBrowser extends BrowserDriver {

    FirefoxOptions options ;

    @Override
    public void setProperty() {
        options = new FirefoxOptions();

        //导入firefox安装路径,安装在默认路径下时无需配置
        options.setBinary("C:\\Program Files\\Mozilla Firefox\\firefox.exe");

        // 设置系统变量,并设置 geckodriver 的路径为系统属性值
        System.setProperty("webdriver.gecko.driver", loadRoute + "geckodriver.exe");
    }


    @Override
    public WebDriver runCarrier(String url) {
        setProperty();
        driver = new FirefoxDriver(options);
        openCarrier(url);
        return driver;
    }

}
