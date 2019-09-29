package toolskit.carrier;

import org.openqa.selenium.WebDriver;

// https://www.jianshu.com/p/305ea89b87e9
interface CarrierDriver {

    // 设置载体属性
    void setProperty();

    // 运行载体
    WebDriver runCarrier(String url);

    void openCarrier(String url);

    // 关闭载体
    void closeBrowser(int sleepNumber);


}
