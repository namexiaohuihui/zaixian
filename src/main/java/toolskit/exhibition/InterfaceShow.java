package toolskit.exhibition;

import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * 实现类为:
 * ExecuteProtogenesis : 主要运行js来实现效果
 * ExecuteFramework : 主要运行selenium内置函数来实现预期
 */
public interface InterfaceShow {

    /**
     * 用于判断元素是否可见或焦点是否集中在该元素上
     * @param elePath 元素路径
     * @param eleType 路径类型
     */
    WebElement VisibleFocus(String elePath, String eleType);

    /**
     * 根据路径获取相关联的全部元素
     * @param elePath 元素路径
     * @param eleType 路径类型
     * @return
     */
    List<WebElement> VisibleFocusGruop(String elePath, String eleType);

    /**
     * 用于判断元素是否隐藏或焦点是否该元素上失去
     * @param elePath 元素路径
     * @param eleType 路径类型
     */
    boolean HideFocus(String elePath,String eleType);

    /**
     * 找到元素后进行输入操作或直接通过js进行输入操作
     * @param elePath 元素路径
     * @param eleType 元素对象
     * @param eleData 输入内容
     */
    void HandleVisibleInput(String elePath,String eleType,WebElement pageTab,String eleData);

    /**
     * 找到元素后进行点击操作或直接通过js进行点击操作
     * @param elePath 元素路径
     * @param eleType 路径类型
     */
    void HandleVisibleTouch(String elePath,String eleType,WebElement pageTab);

    /**
     * 通过元素来执行输入操作或者指定元素来执行js输入操作
     * @param pageTab 元素本身
     * @param eleData 输入内容
     */
    void PerformInputAction(WebElement pageTab,String eleData);

    /**
     * 通过元素来执行点击操作或者指定元素来执行js点击操作
     * @param pageTab 元素本身
     */
    void PerformTouchAction(WebElement pageTab);


    /**
     * 获取元素的属性或者text
     * @param elePath 元素路径
     * @param eleType 路径类型
     * @param pageTab 元素本身
     * @return
     */
    String getAttributeTextValue(String elePath,String eleType,WebElement pageTab,String valueType);
}
