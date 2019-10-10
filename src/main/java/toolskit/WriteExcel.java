package toolskit;

import java.io.*;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;



public class WriteExcel extends ExcelOperating{

    // 当前文件已经存在
    private String excelPath = "E:/MyFirstExcel.xlsx";

    // 从第几行插入进去
    private int insertStartPointer;

    // 根据工作薄名进行插入
    private String sheetName;

    // 根据工作薄所在位置进行插入
    private int sheetInsert = 0;

    public WriteExcel(String excelPath, int insertStartPointer, String sheetName, int sheetInsert) {
        this.excelPath = excelPath;
        this.insertStartPointer = insertStartPointer;
        this.sheetName = sheetName;
        this.sheetInsert = sheetInsert;
    }

    public WriteExcel() {
    }

    /**
     * 总的入口方法
     */
    public static void main(String[] args){
        WriteExcel crt = new WriteExcel();
        int i = new ReadExcel().singleXlsx(crt.excelPath, "sheet");
        crt.insertStartPointer = i + 1;
        crt.insertRows();
    }

    /**
     * 在已有的Excel文件中插入一行新的数据的入口方法
     */
    public void insertRows() {
        Workbook wb = returnWorkBookGivenFileHandle();
        // XSSFSheet sheet1 = wb.getSheet(sheetName);
        sheetName = wb.getSheetName(sheetInsert);
        Sheet sheet = wb.getSheet(sheetName);
        Row row = createRow(sheet, insertStartPointer);
        createCell(row);
        saveExcel(wb);

    }

    /**
     * 保存工作薄
     *
     * @param wb
     */
    private void saveExcel(Workbook wb) {
        FileOutputStream fileOut;
        try {
            fileOut = new FileOutputStream(excelPath);
            wb.write(fileOut);
            fileOut.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 创建要出入的行中单元格
     *
     * @param row
     * @return
     */
    private Cell createCell(Row row) {
        Cell cell = row.createCell((short) 0);
        cell.setCellValue(999999);
        row.createCell(1).setCellValue(1.2);
        row.createCell(2).setCellValue("This is a string cell");
        return cell;
    }

    /**
     * 得到一个已有的工作薄的POI对象
     *
     * @return
     */
    private Workbook returnWorkBookGivenFileHandle() {
        Workbook wb = null;
        File f = new File(excelPath);
        try {
            if (f != null) {
                wb = distinguishWorkbook(excelPath);
            }
        } catch (Exception e) {
            return null;
        }
        return wb;
    }

    /**
     * 找到需要插入的行数，并新建一个POI的row对象
     *
     * @param sheet
     * @param rowIndex
     * @return
     */
    private Row createRow(Sheet sheet, Integer rowIndex) {
        Row row = null;
        if (sheet.getRow(rowIndex) != null) {
            int lastRowNo = sheet.getLastRowNum();
            sheet.shiftRows(rowIndex, lastRowNo, 1);
        }
        row = sheet.createRow(rowIndex);
        return row;
    }

    /**
     * 把内容写入Excel
     *
     * @param list         传入要写的内容，此处以一个List内容为例，先把要写的内容放到一个list中
     * @param outputStream 把输出流怼到要写入的Excel上，准备往里面写数据
     */
    public void writeExcel(List<List> list, OutputStream outputStream) {
        //创建工作簿
        XSSFWorkbook xssfWorkbook;
        xssfWorkbook = new XSSFWorkbook();

        //创建工作表
        XSSFSheet xssfSheet;
        xssfSheet = xssfWorkbook.createSheet();

        //创建行
        XSSFRow xssfRow;

        //创建列，即单元格Cell
        XSSFCell xssfCell;

        //把List里面的数据写到excel中
        for (int i = 0; i < list.size(); i++) {
            //从第一行开始写入
            xssfRow = xssfSheet.createRow(i);
            //创建每个单元格Cell，即列的数据
            List sub_list = list.get(i);
            for (int j = 0; j < sub_list.size(); j++) {
                xssfCell = xssfRow.createCell(j); //创建单元格
                xssfCell.setCellValue((String) sub_list.get(j)); //设置单元格内容
            }
        }

        //用输出流写到excel
        try {
            xssfWorkbook.write(outputStream);
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
