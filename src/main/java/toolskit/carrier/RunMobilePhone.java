package toolskit.carrier;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;


public class RunMobilePhone {

    public AndroidDriver startAndroid() throws MalformedURLException {
        System.out.println("配置开始读取");

        //relative path to apk file
        // final File classpathRoot = new File(System.getProperty("user.dir"));
        //final File appDir = new File(classpathRoot, "E:\\drivers");
        //        final File appDir = new File("C:\\Users\\Administrator\\Desktop");
        //        final File app = new File(appDir, "app-Test_-release_08101700.apk");

        int waiting_time = 10;

        //setting up desired capability
        DesiredCapabilities caps = new DesiredCapabilities();

        //指定测试机的ID,通过adb命令`adb devices`获取
        caps.setCapability("deviceName", "192.168.1.3：5555");

        //指定测试平台及版本号
        caps.setCapability("platform", "ANDROID");
        caps.setCapability("platformVersion", "7.1.1");

        //将上面获取到的包名和Activity名设置为值
        caps.setCapability("appPackage", "cn.com.open.mooc");
        caps.setCapability("appActivity", "com.imooc.component.imoocmain.splash.MCSplashActivity");

        //A new session could not be created:无法创建新会话的解决方法:指定需要跳转的activity
        caps.setCapability("appWaitActivity", "com.imooc.component.imoocmain.splash.MCSplashActivity");

        //每次启动时覆盖session，否则第二次后运行会报错不能新建session
        caps.setCapability("sessionOverride", true);

        // 是否重新安装
        caps.setCapability("noReset", true);

        // 等待设备启动时间
        caps.setCapability("deviceReadyTimeout", waiting_time);

        // 是否使用自定义的keystore对应用重签名
        caps.setCapability("userKeystore", true);

        // 支持中文输入
        caps.setCapability("unicodeKeyboard", true);

        // 支持中文输入之后将键盘关闭
        caps.setCapability("resetKeyboard", true);


        //初始化驱动对象
        AndroidDriver driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), caps);

        //初始化显式等待对象,可以不需要这个步骤
        WebDriverWait wait = new WebDriverWait(driver, waiting_time);
        //  等待10S等待元素出现.
        driver.manage().timeouts().implicitlyWait(waiting_time, TimeUnit.SECONDS);
        return driver;
    }
}
