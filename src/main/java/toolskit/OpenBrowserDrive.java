package toolskit;

import org.apache.commons.exec.util.StringUtils;
import org.openqa.selenium.WebDriver;

import toolskit.carrier.*;

public class OpenBrowserDrive {

    public BrowserDriver getBrowserDriver(String browserType){
        if(browserType == null){
            return null;
        }
        BrowserDriver driver = null;
        if(browserType.equalsIgnoreCase("chrome")){
            driver = new ChromeBrowser();
        } else if(browserType.equalsIgnoreCase("firefox")){
            driver = new FireFoxBrowser();
        } else if(browserType.equalsIgnoreCase("ie")){
            driver = new WinIEBrowser();
        }else if(browserType.equalsIgnoreCase("edge")){
            driver = new WinEdgeBrowser();
        }

        if (driver !=null){
            driver.runCarrier();
        }

        return driver;
    }

}
