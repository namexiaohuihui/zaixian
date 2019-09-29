package toolskit;

import org.apache.poi.ss.usermodel.*;

import java.io.*;
import java.util.*;


public class ReadExcel extends ExcelOperating {
//    public static void main(String[] args) {
//        ReadExcel re = new ReadExcel();
//        String load = "C:\\Users\\LGYY-USER\\Desktop\\红包发放.xlsx";


//         方式一: 指定sheet来读取
//        List<Map<String, String>> excelList = re.readExcel(load, "登录");
//        System.out.println("从excel读取数据并开始使用:");
//        for (Map<String, String> list : excelList) {
//            System.out.println(list);
//            for (Map.Entry<String, String> entry : list.entrySet()) {
//                System.out.println(entry.getValue());
//            }
//            System.out.println();
//        }

//         方式二: 读取单个文件,单行数据
//            Map<String, String> stringStringMap = re.singleReadXlsx(load, "登录", 2);
//        System.out.println(stringStringMap);
//            for(Map.Entry<String, String> entry : stringStringMap.entrySet()){
//                System.out.print("Key = "+entry.getKey()+",value="+entry.getValue() + "\n");
//            }

//         方式三: 读取全部sheet的数据
//        List<Map<Integer, Object>> lists = re.wholeReadXlsx(load);
//        for (Map<Integer, Object> i : lists) {
//            System.out.print(i);
//        }

//    }

    /**
     * 通过Workbook来读取excle表格上的数据
     *
     * @param load
     * @return
     * @throws IOException
     */
    public List<Map<Integer, Object>> wholeReadXlsx(String load) {
        // excel中第几列 ： 对应的表头
        Map<Integer, String> colAndNameMap = new HashMap<Integer, String>();
        List<Map<Integer, Object>> resultList = new ArrayList<Map<Integer, Object>>();
        Workbook wb = null;
        try {
            wb = distinguishWorkbook(load);
            for (int sheetIndex = 0; sheetIndex < wb.getNumberOfSheets(); sheetIndex++) {
                //获取sheet数据
                Sheet st = wb.getSheetAt(sheetIndex);
                //遍历一个sheet中每一行
                for (int rowIndex = 0; rowIndex <= st.getLastRowNum(); rowIndex++) {
                    // 表头：值
                    Map<Integer, Object> nameAndValMap = new HashMap<Integer, Object>();
                    // 获取到一行数据
                    Row row = st.getRow(rowIndex);
                    for (int cellIndex = 0; cellIndex < row.getPhysicalNumberOfCells(); cellIndex++) {

                        if (rowIndex == 0) {
                            colAndNameMap.put(cellIndex, row.getCell(cellIndex).getStringCellValue());
                        } else if (!colAndNameMap.isEmpty()) {
                            nameAndValMap.put(cellIndex, getCellValue(row.getCell(cellIndex)));
                        }
                    }
                    if (!nameAndValMap.isEmpty()) {
                        resultList.add(nameAndValMap);
                    }
                }
            }
            return resultList;
        } catch (Exception e) {
            System.out.println(">>>>>>>>>>  读取excel文件时出错了！！！");
            e.printStackTrace();
        } finally {
            try {
                wb.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    /**
     * 读取指定工作薄里的指定行的内容
     * load文档所在地
     * numSheet当前文档中所读写的工作薄
     * rowNum当前工作薄中的第几个数据
     *
     * @param load
     * @param sheetName
     * @param rowNum
     * @return
     * @throws IOException
     */
    public Map<String, String> singleReadXlsx(String load, String sheetName, int rowNum) {
        Workbook xssfWorkbook = distinguishWorkbook(load);
        Map<String, String> aMap = new HashMap<String, String>();
        Sheet xssfSheet;
        if (sheetName.equals("")) {
            // 默认取第一个子表
            xssfSheet = xssfWorkbook.getSheetAt(0);
        } else {
            xssfSheet = xssfWorkbook.getSheet(sheetName);
        }

        if (xssfSheet != null) {
            // 获取指定行
            Row xssfRow = xssfSheet.getRow(rowNum);//获取该行的全部数据
            if (xssfRow != null) {

                int firstCellNum = (int) xssfRow.getFirstCellNum();// 首列
                int lastCellNum = (int) xssfRow.getLastCellNum();// 最后一列

                for (int col = firstCellNum; col < lastCellNum; col++) {
                    String sEnum = col + "";
                    aMap.put(sEnum, getCellValue(xssfRow.getCell(col)));
                }
            } else {
                System.out.println("xssfRow为空");
            }
        }

        return aMap;
    }


    /**
     * 返回表中数据的长度
     *
     * @param load
     * @return
     * @throws IOException
     */
    public int singleXlsx(String load, int numSheet) {
        int row = 0;
        // 获取每一个工作薄
        Sheet sheetAt = distinguishWorkbook(load).getSheetAt(numSheet);
        if (sheetAt != null) {
            row = sheetAt.getLastRowNum();
        }
        return row;

    }

    /**
     * 读取Excel文件中指定sheet的内容
     *
     * @param load      excel文件的所在路径
     * @param sheetName sheet名字
     * @return 以List返回excel中内容
     */
    public List<Map<String, String>> readExcel(String load, String sheetName) {

        Workbook xssfWorkbook = distinguishWorkbook(load);

        //定义工作表
        Sheet xssfSheet;

        if (sheetName.equals("")) {
            // 默认取第一个子表
            xssfSheet = xssfWorkbook.getSheetAt(0);
        } else {
            xssfSheet = xssfWorkbook.getSheet(sheetName);
        }

        List<Map<String, String>> list = new ArrayList<Map<String, String>>();

        // 默认第一行为标题行，index = 0
        Row titleRow = xssfSheet.getRow(0);

        // 根据第一行返回该行中单元格的数量
        int cellNumber = titleRow.getPhysicalNumberOfCells();

        // 返回sheet标中行的总数
        int rownumber = xssfSheet.getPhysicalNumberOfRows();

        // 循环取每行的数据
        for (int rowIndex = 1; rowIndex < rownumber; rowIndex++) {
            Row xssfRow = xssfSheet.getRow(rowIndex);
            if (xssfRow == null) {
                continue;
            }

            Map<String, String> map = new LinkedHashMap<String, String>();

            //循环取每个单元格(cell)的数据
            for (int cellIndex = 0; cellIndex < cellNumber; cellIndex++) {
                Cell titleCell = titleRow.getCell(cellIndex);
                Cell xssfCell = xssfRow.getCell(cellIndex);
                map.put(getCellValue(titleCell), getCellValue(xssfCell));
            }
            list.add(map);
        }
        return list;
    }

    /**
     * 把一个Map中的所有键和值分别放到一个list中，
     * 再把这两个list整个放到一个大的list里面，即 [ [key1,key2,key3...] , [value1,value2,value3...] ]
     *
     * @param map
     * @return
     */
    public static List<List> convertMapToList(Map map) {
        List<List> list = new ArrayList<List>();
        List<String> key_list = new LinkedList<String>();
        List<String> value_list = new LinkedList<String>();

        Set<Map.Entry<String, String>> set = map.entrySet();
        Iterator<Map.Entry<String, String>> iter1 = set.iterator();
        while (iter1.hasNext()) {
            key_list.add(iter1.next().getKey());
        }
        list.add(key_list);

        Collection<String> value = map.values();
        Iterator<String> iter2 = value.iterator();
        while (iter2.hasNext()) {
            value_list.add(iter2.next());
        }
        list.add(value_list);
        return list;
    }
}
