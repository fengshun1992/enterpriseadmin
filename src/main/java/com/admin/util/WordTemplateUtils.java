package com.admin.util;

import com.admin.entity.enterprise.TableCell;
import com.admin.entity.enterprise.WordTable;
import org.apache.commons.collections.map.HashedMap;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.*;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WordTemplateUtils {
    //匹配${param}
    private static String reg_charset = "\\$\\{(.+?)}";

    /**
     * 根据模板生成新word文档
     * 判断表格是需要替换还是需要插入，判断逻辑有$为替换，表格无$为插入
     *
     * @param is 文件输入流
     */
    public static Map getWord(InputStream is) throws Exception {

        Map map = new HashedMap();
        List<String> textList = new ArrayList<>();
        List<WordTable> tableList = new ArrayList<>();
        try {
            //获取docx解析对象
            XWPFDocument document = new XWPFDocument(OPCPackage.open(is));
            //解析替换文本段落对象
            WordTemplateUtils.getText(document, textList);
            //list去重
            List<String> newList = new ArrayList<>();
            for (String str : textList) {
                if (!newList.contains(str)) {
                    newList.add(str);
                }
            }
            map.put("textList", newList);
            //解析替换表格对象
            WordTemplateUtils.getTable(document, tableList);
            map.put("tableList", tableList);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return map;
    }

    /**
     * @param is
     * @param outputPath
     * @param textMap
     * @param tableList
     * @return
     * @throws Exception
     */
    public static boolean changWord(InputStream is, String outputPath,
                                    Map textMap, List<WordTable> tableList) throws Exception {

        //模板转换默认成功
        boolean changeFlag = true;
        try {
            //获取docx解析对象
            XWPFDocument document = new XWPFDocument(OPCPackage.open(is));
            //解析替换文本段落对象
            WordTemplateUtils.changeText(document, textMap);
            //解析替换表格对象
            if (tableList.size() > 0) {
                WordTemplateUtils.changeTable(document, tableList);
            }

            //生成新的word
            File file = new File(outputPath);
            FileOutputStream stream = new FileOutputStream(file);
            document.write(stream);
            stream.close();

        } catch (IOException e) {
            e.printStackTrace();
            changeFlag = false;
        }

        return changeFlag;
    }

    /**
     * 获取需要替换的段落文本参数
     *
     * @param document docx解析对象
     */
    private static void getText(XWPFDocument document, List<String> textList) {
        //获取段落集合
        List<XWPFParagraph> paragraphs = document.getParagraphs();
        for (XWPFParagraph paragraph : paragraphs) {
            //判断此段落时候需要进行替换
            String text = paragraph.getText();
            //判断是否有要替换字符
            Pattern p = Pattern.compile(reg_charset);
            Matcher m = p.matcher(text);
            while (m.find()) {
                System.out.println("发现paragraph匹配:" + m.group(0));
                textList.add(m.group(1));
            }

        }
    }

    /**
     * 替换段落文本
     *
     * @param document docx解析对象
     * @param textMap  需要替换的信息集合
     */
    private static void changeText(XWPFDocument document, Map<String, String> textMap) {
        //获取段落集合
        List<XWPFParagraph> paragraphs = document.getParagraphs();

        for (XWPFParagraph paragraph : paragraphs) {

            getCustomRuns(paragraph, textMap);
        }
    }

    /**
     * split切割法
     *
     * @param paragraph
     * @param textMap
     */
    private static void getCustomRuns(XWPFParagraph paragraph, Map<String, String> textMap) {
        //判断此段落时候需要进行替换
        String text = paragraph.getText();
        //判断是否有要替换字符
        if (checkText(text)) {
            String[] values = text.split("&");
            String result = "";
            for (String value : values) {
                result += changeValue(value, textMap);
            }
            List<XWPFRun> runs = paragraph.getRuns();
            for (int i = 0; i < runs.size(); i++) {
                if (i == 0) {
                    runs.get(i).setUnderline(UnderlinePatterns.NONE);
                    runs.get(i).setText(result, 0);
                } else {
                    runs.get(i).setText("", 0);
                }
            }
        }
    }

    /**
     * 获取表格对象参数方法
     *
     * @param document docx解析对象,获取参数
     */
    private static void getTable(XWPFDocument document, List<WordTable> tableList) {
        //获取表格对象集合
        List<XWPFTable> tables = document.getTables();
        for (int i = 0; i < tables.size(); i++) {
            WordTable wordTable = new WordTable();
            String tableName = "";
            List<TableCell> list = new ArrayList<>();

            XWPFTable table = tables.get(i);
            List<XWPFTableRow> rows = table.getRows();
            for (XWPFTableRow row : rows) {
                List<XWPFTableCell> cells = row.getTableCells();
                for (int j = 0; j < cells.size(); j++) {
                    XWPFTableCell cell = cells.get(j);
                    String text = cell.getText();
                    Pattern p = Pattern.compile(reg_charset);
                    Matcher m = p.matcher(text);
                    while (m.find()) {
                        System.out.println("发现table匹配:" + m.group(0));
                        tableName = m.group(1).split("\\.")[0];
                        TableCell tableCell = new TableCell();
                        tableCell.setColumnIndex(j + 1);
                        tableCell.setParam(m.group(1));
                        list.add(tableCell);
                    }
                }
            }
            if (list.size() > 0) {
                wordTable.setColumnList(list);
                wordTable.setTableName(tableName);
                tableList.add(wordTable);
            }
        }
    }

    /**
     * 替换表格对象方法
     *
     * @param document  docx解析对象
     * @param tableList 表格信息集合
     */
    private static void changeTable(XWPFDocument document, List<WordTable> tableList) {
        //获取表格对象集合
        List<XWPFTable> tables = document.getTables();
        for (int i = 0; i < tables.size(); i++) {
            XWPFTable table = tables.get(i);
            String msg = table.getText();
            String tableName = "";
            WordTable wordTable = tableList.get(i);
            //只处理行数大于等于2的表格，且不循环表头
            if (table.getRows().size() > 1) {
                //判断表格是需要替换还是需要插入，判断逻辑有$为替换，表格无$为插入
                Pattern p = Pattern.compile(reg_charset);
                Matcher m = p.matcher(msg);
                if (m.find()) {
                    tableName = m.group(1).split("\\.")[0];
                }
                if (wordTable.getTableName().equals(tableName)) {
                    System.out.println("替换表格" + table.getText());

                    //暂时无法解决添加新行问题
                    /*while (table.getNumberOfRows() < wordTable.getRows()) {
                        table.createRow();
                    }*/

                    List<TableCell> columnList = wordTable.getColumnList();
                    for (int j = 0; j < columnList.size(); j++) {
                        TableCell tableCell = columnList.get(j);
                        insertTable(table, tableCell);
                    }
                }
            }
        }
    }

    /**
     * 为表格替换数据
     *
     * @param table     需要插入数据的表格
     * @param tableCell 单个数据对象
     */
    private static void insertTable(XWPFTable table, TableCell tableCell) {
        String msg = table.getText();
        XWPFTableRow row = table.getRow(tableCell.getRowIndex() - 1);
        XWPFTableCell cell = row.getCell(tableCell.getColumnIndex() - 1);
        String cellText = cell.getText();
        //如果需要替换的cell有占位符或者为空
        if (checkText(cellText) || StringUtils.isEmpty(cellText)) {
            List<XWPFParagraph> paragraphs = cell.getParagraphs();
            for (int i = 0; i < paragraphs.size(); i++) {
                cell.removeParagraph(i);
            }
            XWPFParagraph paragraph = cell.addParagraph();
            XWPFRun run = paragraph.createRun();
            paragraph.setAlignment(ParagraphAlignment.CENTER);
            run.setBold(false); //加粗
            run.setColor("000000");
            run.setFontSize(14);
            run.setText(tableCell.getValue());
        }
    }

    /**
     * 判断文本中时候包含${}
     *
     * @param text 文本
     * @return 包含返回true, 不包含返回false
     */
    private static boolean checkText(String text) {
        boolean check = false;
        Pattern p = Pattern.compile(reg_charset);
        Matcher m = p.matcher(text);
        while (m.find()) {
            check = true;
            System.out.println("发现匹配" + m.group(0));
        }

        return check;
    }

    /**
     * 自定义每行文字分割方法(暂时废弃,有bug)
     *
     * @param paragraph
     * @param textMap
     */
    private static void oldMethod(XWPFParagraph paragraph, Map<String, String> textMap) {
        List<XWPFRun> runs = paragraph.getRuns();

        int start = -1;
        String curKey = "";
        //循环处理所有的runs
        for (int i = 0; i < runs.size(); i++) {
            String curText = runs.get(i).getText(runs.get(i).getTextPosition());
            if (curText != null && curText.indexOf("${") != -1) {
                //如果找到了属性起始标志符，则进行切分，提炼出key，同时，重设该run的文本，保留"${“前的部分
                curKey += curText.substring(curText.indexOf("${") + 2);
                runs.get(i).setText(curText.substring(0, curText.indexOf("${")), 0);
                start = i;
                continue;
            }
            //如果当前是拼接状态
            if (start != -1) {
                //继续拼接key
                curKey += curText;
                //如果还没到属性的结尾，则清除掉内部的内容
                if (curText.indexOf("}") == -1) {
                    runs.get(i).setText("", 0);
                } else {
                    //如果已经到属性结尾，则进行属性替换，保留"}“ 后的内容
                    runs.get(i).setText(textMap.get(curKey.substring(0, curKey.indexOf("}")))
                            + curText.substring(curText.indexOf("}") + 1), 0);
                    //TODO   此处需要进行细化
                    //初始化，继续下一项
                    curKey = "";
                    start = -1;
                }
            }
        }
    }

    /**
     * 匹配传入信息集合与模板(暂时废弃)
     *
     * @param value   模板需要替换的区域
     * @param textMap 传入信息集合
     * @return 模板需要替换区域信息集合对应值
     */
    private static String changeValue(String value, Map<String, String> textMap) {
        Set<Map.Entry<String, String>> textSets = textMap.entrySet();
        for (Map.Entry<String, String> textSet : textSets) {
            //匹配模板与替换值 格式${key}
            String key = "${" + textSet.getKey() + "}";
            if (value.indexOf(key) != -1) {
                System.out.println("value=" + value + ",key=" + textSet.getValue());
                value = textSet.getValue();
            }
        }
        //模板未匹配到区域替换为空
        if (checkText(value)) {
            //value = "";
        }
        return value;
    }


    public static void main(String[] args) throws Exception {
        //模板文件地址
        String inputUrl = "C:\\Users\\fengshun\\Desktop\\data\\qiye\\model\\1\\【天弘票融】定融专项计划-产品说明书-1号.docx";
        //新生产的模板文件
        String path = "C:\\Users\\fengshun\\Desktop\\";
        //FileUtil.createDir(path);
        String outputPath = path + "_1.docx";

        InputStream is = new FileInputStream(inputUrl);

        Map testMap = new HashMap();
        testMap.put("年", "2018");
        testMap.put("产品编号", "13220837526");
        testMap.put("票据质押清单.票号", "13220837526");
        testMap.put("address", "软件园");
        testMap.put("phone", "88888888");

        List<WordTable> tableList = new ArrayList<WordTable>();
        WordTable wordTable = new WordTable();
        wordTable.setTableName("票据质押清单");
        wordTable.setRows(10);
        List<TableCell> tableCells = new ArrayList<>();
        TableCell tableCell = new TableCell();
        tableCell.setParam("${票据质押清单.票号}");
        tableCell.setValue("票号12345678");
        tableCell.setRowIndex(2);
        tableCell.setColumnIndex(2);
        tableCells.add(tableCell);

        tableCell = new TableCell();
        tableCell.setParam("${票据质押清单.票号}");
        tableCell.setValue("厉害了我的国");
        tableCell.setRowIndex(3);
        tableCell.setColumnIndex(3);
        tableCells.add(tableCell);
        wordTable.setColumnList(tableCells);
        tableList.add(wordTable);

        wordTable = new WordTable();
        wordTable.setTableName("票据质押清单2");
        wordTable.setRows(1);
        tableCells = new ArrayList<>();
        tableCell = new TableCell();
        tableCell.setParam("${票据质押清单2.票号2}");
        tableCell.setValue("哈哈哈哈");
        tableCell.setRowIndex(1);
        tableCell.setColumnIndex(1);
        tableCells.add(tableCell);
        wordTable.setColumnList(tableCells);
        tableList.add(wordTable);

        WordTemplateUtils.changWord(is, outputPath, testMap, tableList);
        //WordTemplateUtils.getWord(is);
    }
}
