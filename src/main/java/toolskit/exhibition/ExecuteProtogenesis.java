package toolskit.exhibition;

import org.openqa.selenium.*;

import java.util.List;


/**
 * 该函数尚未完成
 */
public class ExecuteProtogenesis implements InterfaceShow {

    private   JavascriptExecutor driver_js ;

    public ExecuteProtogenesis(WebDriver driver){
        this.driver_js = ((JavascriptExecutor) driver);

    }
    /**
     * 通过js来查找元素
     * @param elePath 元素路径
     * @param eleType 路径类型;目前没用到。
     * @return
     */
    @Override
    public WebElement VisibleFocus(String elePath, String eleType) {
       return null;

    }

    @Override
    public List<WebElement> VisibleFocusGruop(String elePath, String eleType) {
        return null;
    }

    /**
     * 通过js来判断元素是否隐藏;该函数有坑不建议用
     * @param elePath 元素路径
     * @param eleType 路径类型;目前尚未使用
     * @return
     */
    @Override
    public boolean HideFocus(String elePath, String eleType) {
        return false;
    }

    /**
     * 通过js来对元素进行输入操作
     * @param elePath 元素路径
     * @param eleType 元素对象
     * @param eleData 输入内容
     */
    @Override
    public void HandleVisibleInput(String elePath,  String eleType,WebElement pageTab, String eleData) {

    }

    /**
     * 通过js来对元素进行点击操作
     * @param elePath 元素路径
     * @param eleType 路径类型
     */
    @Override
    public void HandleVisibleTouch(String elePath,  String eleType,WebElement pageTab) {

    }

    /**
     * 通过已找到的元素进行输入操作
     * @param pageTab 元素本身
     * @param eleData 输入内容
     */
    @Override
    public void PerformInputAction(WebElement pageTab, String eleData) {
        driver_js.executeScript("arguments[0].value='" + eleData + "'", pageTab);
    }
    /**
     * 通过已找到的元素进行点击操作
     * @param pageTab 元素本身
     */
    @Override
    public void PerformTouchAction(WebElement pageTab) {
        driver_js.executeScript("arguments[0].click()", pageTab);
    }

    @Override
    public String getAttributeTextValue(String elePath, String eleType, WebElement pageTab,String valueType) {
        String text = (String) driver_js.executeScript("return arguments[0].value", pageTab);
        return text;
    }

}
