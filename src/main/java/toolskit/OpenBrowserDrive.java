package toolskit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;

public class OpenBrowserDrive {

    private WebDriver driver;

    private String loadRoute = "F:\\drivers\\";


    public WebDriver getWebDrivaer() {
        if (driver == null) {
            System.out.println("调用时提示：浏览器对象为空");
        }
        return driver;
    }

    public void setProperty() {
        System.setProperty("webdriver.chrome.driver", loadRoute + "chromedriver.exe");
    }

    //   获取对象：谷歌
    public WebDriver openChrome() {
        if (driver == null) {
            setProperty();
            driver = new ChromeDriver();

        }
        return driver;
    }

    public WebDriver openBrowser() {
        //        创建浏览器对象
        driver = openChrome();
        //        是浏览器的大小
        driver.manage().window().maximize();
        //        设置测试的网页
        String openUrl = "**";

        driver.get(openUrl);

        //        设置网页超时的时间
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

        String title = driver.getTitle();

        return driver;
    }

    public void closeBrowser(int sleepNumber) throws InterruptedException {
        Thread.sleep(sleepNumber);
        driver.close();
    }

}
