package toolskit.carrier;

public class DriverFactory {

    public static BrowserDriver getDriverBrowser(String driverType,String webUrl){
        BrowserDriver browser = null;
        if (driverType == null || driverType.equals("")){
            return browser;
        }

        if (driverType.equalsIgnoreCase("chrome")){
            browser = new ChromeBrowser();
        }else if (driverType.equalsIgnoreCase("firefox")){
            browser = new ChromeBrowser();
        }else if (driverType.equalsIgnoreCase("ie")){
            browser = new ChromeBrowser();
        }else if (driverType.equalsIgnoreCase("edge")){
            browser = new ChromeBrowser();
        }

        if (browser !=null){
            browser.runCarrier(webUrl);
        }

        return browser;
    }
}
