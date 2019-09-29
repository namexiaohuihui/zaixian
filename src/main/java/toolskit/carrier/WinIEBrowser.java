package toolskit.carrier;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

public class WinIEBrowser extends BrowserDriver   {

    @Override
    public void setProperty() {
    // 设置系统变量,并设置iedriver的路径为系统属性值
    System.setProperty("webdriver.ie.driver", ".\\drivers\\IEDriverServer.exe");
    }


    @Override
    public WebDriver runCarrier(String url) {
        setProperty();
        driver = new InternetExplorerDriver();
        openCarrier(url);
        return driver;
    }

}
