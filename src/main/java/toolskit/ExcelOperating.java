package toolskit;

import java.io.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.IOCase;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.DateUtil;





public class ExcelOperating {

    private final String XLS_VERSION = "xls";
    private final String XLSX_VERSION = "xlsx";

    /**
     * 判断Excel的版本,获取Workbook
     * @param fileName 文件名
     * @return sheet对象
     */
    public Workbook distinguishWorkbook(String fileName) {
        Workbook workbook = null;
        InputStream is = null;
        try {
            File file = new File(fileName);
            is = new FileInputStream(file);

            if (IOCase.SENSITIVE.checkEndsWith(fileName, XLS_VERSION)) {

                workbook = new HSSFWorkbook(is);

            } else if (IOCase.SENSITIVE.checkEndsWith(fileName, XLSX_VERSION)) {

                workbook = new XSSFWorkbook(is);

            } else {
                System.out.println("该文件不是excle表格:" + fileName);

            }
        } catch (FileNotFoundException e) {
            System.out.println(">>>>>>>>>>  读取excel文件时出错了！！！");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println(">>>>>>>>>>  读取excel文件时出错了！！！");
            e.printStackTrace();
        }finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return workbook;
    }


    public   String getIntTypeValue(Cell cell) {
        String cellValue = "";
        double value = cell.getNumericCellValue();
        if (cell.getCellStyle().getDataFormat() == 176) {

            // 处理自定义日期格式:M月D日(通过判断单元格的格式id解决,ID的值是58)
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = DateUtil.getJavaDate(value);
            cellValue = sdf.format(date);

        } else {
            CellStyle style = cell.getCellStyle();
            DecimalFormat format = new DecimalFormat();
            String temp = style.getDataFormatString();

            // 把数字当成String来读，避免出现1读成1.0的情况.这个方式没这么灵活
            //        if (cell.getCellType() == CellType.NUMERIC) {
            //            cell.setCellType(CellType.STRING);
            //        }

            // 单元格设置成常规
            if (temp.equals("General")) {
                format.applyPattern("#");
            }
            cellValue = format.format(value);
        }
        return cellValue;
    }

    /**
     * 判断获取当前内容的格式，然后进行返回内容
     * 把单元格的内容转为字符串
     * 高版本的import org.apache.poi.ss.usermodel.CellType变为了import org.apache.poi.ss.usermodel.Cell;
     * 同时cellRowName.setCellType(CellType.STRING);变为了cellRowName.setCellType(Cell.CELL_TYPE_STRING);
     * 并且xssfCell.getCellTypeEnum()变成xssfCell.getCellType()
     * CellType	                类型	        值
     * NUMERIC	                数值型	        0
     * STRING	                字符串型	    1
     * FORMULA	                公式型	        2
     * BLANK	                空值	        3
     * BOOLEAN	                布尔型	        4
     * ERROR	                错误	        5
     * @param cell 单元格
     * @return 字符串
     */
    @SuppressWarnings("deprecation")
    public  String getCellValue(Cell cell) {
        String cellValue = "";
        if (cell == null) {
            return cellValue;
        }

        //判断数据的类型
        CellType cellType = cell.getCellType();

        switch (cellType) {
            case NUMERIC: //数字
                cellValue = getIntTypeValue(cell);
                break;
            case STRING: //字符串
                cellValue = String.valueOf(cell.getStringCellValue());
                break;
            case BOOLEAN: //Boolean
                cellValue = String.valueOf(cell.getBooleanCellValue());

                break;
            case FORMULA: //公式
                cellValue = String.valueOf(cell.getCellFormula());
                break;
            case BLANK: //空值
                cellValue = "";
                break;
            case ERROR: //故障
                cellValue = "非法字符";
                break;
            case _NONE: //故障
                cellValue = "非法字符";
                break;
            default:
                cellValue = "未知类型";
                break;
        }
        return cellValue;
    }

}
