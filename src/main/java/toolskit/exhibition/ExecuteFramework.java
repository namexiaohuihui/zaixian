package toolskit.exhibition;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;


public class ExecuteFramework implements  InterfaceShow {

    WebDriver driver;

    private final int TIME_OUT_SECONDS = 5 ;

    public ExecuteFramework(WebDriver driver){
        this.driver = driver;
    }

    public By distinguishByAttribute(String elePath, String pathType) {
        By eleBy ;
        if(elePath ==null||elePath.equals("")||pathType ==null||pathType.equals("")){
            return null;
        }

        switch (pathType) {
            case "id":
                eleBy = By.id(elePath);
                break;
            case "xpath":
                eleBy = By.xpath(elePath);
                break;
            case "css":
                eleBy = By.cssSelector(elePath);
                break;
            case "name":
                eleBy = By.name(elePath);
                break;
            default:
                eleBy = null;
        }
        return eleBy;
    }

    @Override
    public WebElement VisibleFocus(String elePath, String eleType) {
        By by = distinguishByAttribute(elePath, eleType);
        if (by == null){
            Assert.assertFalse(false);
        }
        WebElement username = null;
        try {
            username = new WebDriverWait(driver, TIME_OUT_SECONDS)
                    .until(ExpectedConditions.visibilityOfElementLocated(by));
        } catch (TimeoutException a) {
            a.printStackTrace();
        }

        return username;
    }

    @Override
    public List<WebElement> VisibleFocusGruop(String elePath, String eleType) {
        By by = distinguishByAttribute(elePath, eleType);
        if (by == null){
            Assert.assertFalse(false);
        }
        List<WebElement> eleList =null;
        try {
            eleList = new WebDriverWait(driver, TIME_OUT_SECONDS)
                    .until(ExpectedConditions.visibilityOfAllElementsLocatedBy(by));
        } catch (TimeoutException a) {
            a.printStackTrace();
        }
        return eleList;
    }

    @Override
    public boolean HideFocus(String elePath, String eleType) {
        By by = distinguishByAttribute(elePath, eleType);
        if (by == null){
            Assert.assertFalse(false);
        }
        //页面元素在页面是否存在
        boolean hide= false;
        try {
            WebElement  until = new WebDriverWait(driver, TIME_OUT_SECONDS).until(ExpectedConditions.presenceOfElementLocated(by));
            if(until != null){
                hide = true;
            }
        } catch (TimeoutException a) {
//            a.printStackTrace();
        }
        return hide;
    }

    @Override
    public void HandleVisibleInput(String elePath, String eleType, WebElement pageTab, String eleData) {
        if (pageTab!=null) {
            PerformInputAction(pageTab, eleData);
        }else{
             pageTab = VisibleFocus(elePath, eleType);
            if (pageTab!=null){
                PerformInputAction(pageTab,eleData);
            }
        }
    }

    @Override
    public void HandleVisibleTouch(String elePath, String eleType,WebElement pageTab) {
        if (pageTab!=null){
            PerformTouchAction(pageTab);
        }else {
            pageTab = VisibleFocus(elePath, eleType);
            if (pageTab!=null){
                PerformTouchAction(pageTab);
            }
        }
    }

    @Override
    public void PerformInputAction(WebElement pageTab, String eleData) {
        try {
            pageTab.clear();
            pageTab.sendKeys(eleData);
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void PerformTouchAction(WebElement pageTab) {
        try {
            pageTab.click();
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    private String getElementValue(WebElement pageTab,String valueType){
        String textValue ;


        if (valueType.equalsIgnoreCase("text")){
            textValue = pageTab.getText();

        }else {
            textValue = pageTab.getAttribute(valueType);
        }
        return textValue;
    }
    @Override
    public String getAttributeTextValue(String elePath, String eleType, WebElement pageTab,String valueType) {
        if(pageTab==null){
            pageTab = VisibleFocus(elePath, eleType);
        }

        String value = getElementValue(pageTab, valueType);
        return value;
    }
}
