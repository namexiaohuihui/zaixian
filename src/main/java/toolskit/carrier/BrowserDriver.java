package toolskit.carrier;

import org.openqa.selenium.WebDriver;

import java.util.concurrent.TimeUnit;

public class BrowserDriver implements CarrierDriver  {

    WebDriver driver ;

    // 后期这个地址通过yaml来读取
    protected String loadRoute = ".\\drivers\\";


    public WebDriver getDriver(){
        return driver;
    }
    @Override
    public void setProperty() {

    }

    @Override
    public WebDriver runCarrier(String url) {
        return driver;
    }

    @Override
    public void openCarrier(String url){

        // 浏览器的大小
        driver.manage().window().maximize();

        // 打开网址
        driver.get(url);

        // 设置网页超时的时间
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    /**
     * 程序休眠指定时长后进行关闭已打开浏览器的操作
     * @param sleepNumber 需要休息的时长
     */
    @Override
    public void closeBrowser(int sleepNumber) {
        try {
            if (sleepNumber>0){
                Thread.sleep(sleepNumber);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            driver.close();
        }
    }
}
