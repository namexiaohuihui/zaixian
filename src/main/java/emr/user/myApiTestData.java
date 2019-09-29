package emr.user;

import org.testng.ITestContext;
import org.testng.annotations.DataProvider;
import toolskit.ReadExcel;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

public class myApiTestData {


    @DataProvider
    public Object[][] getLoginData(ITestContext context) {
        String[] groups = context.getIncludedGroups();
        System.out.println("@DataProvider(name='loginProvider')");
        System.out.println("dataProvider"+context.getName());
        for(String group : groups){
            System.out.println("dataProvider"+group);
        }
        String load = ".\\drivers\\红包发放.xlsx";
        String sheetName = "登录";
        ReadExcel re = new ReadExcel();

        List<Map<String, String>> maps = re.readExcel(load, sheetName);
        Object[][] testData = mapsTpArray(maps);

        return testData;
    }

    /**
     * 大佬: https://www.jianshu.com/p/8e333a0ec42a
     * @param maps
     * @return
     */
    public Object[][] mapsTpArray(List<Map<String, String>> maps) {
        int singleListSize = maps.size();

        // 创建一个二维数组
        Object[][] testData = new Object[singleListSize][];

        for (int singleSize = 0; singleSize < singleListSize; singleSize++) {

            Map<String, String> singleMap = maps.get(singleSize);
            testData[singleSize] = new Object[] { singleMap};

        }

        // 返回数据传给脚本
        return testData;


    }
}
