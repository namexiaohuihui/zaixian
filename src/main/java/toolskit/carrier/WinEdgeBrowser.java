package toolskit.carrier;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;

public class WinEdgeBrowser extends BrowserDriver  {

    @Override
    public void setProperty() {
        // 指定MicrosoftWebDriver路径
        System.setProperty("webdriver.edge.driver",loadRoute +  "MicrosoftWebDriver.exe");
    }


    @Override
    public WebDriver runCarrier(String url) {
        setProperty();
        driver = new EdgeDriver();
        openCarrier(url);
        return driver;
    }

}
