package toolskit.carrier;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
public class ChromeBrowser extends BrowserDriver{


    @Override
    public void setProperty() {
        System.setProperty("webdriver.chrome.driver", loadRoute + "chromedriver.exe");
    }

    @Override
    public WebDriver runCarrier(String url) {
        setProperty();
        driver = new ChromeDriver();
        openCarrier(url);
        return driver;
    }

}
