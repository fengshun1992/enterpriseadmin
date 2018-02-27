package com.admin.util;

import org.apache.commons.collections.MapUtils;
import org.apache.poi.xwpf.converter.pdf.PdfConverter;
import org.apache.poi.xwpf.converter.pdf.PdfOptions;
import org.apache.poi.xwpf.usermodel.*;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

/**
 * @author Rocca
 */
public class Word2PdfUtils {

    /**
     * 将word文档,转换成pdf
     *
     * @param inputPath 源为word文档,必须为docx文档
     * @param outputPath 目标输出
     * @throws Exception
     */
    public static void wordToPdf(String inputPath, String outputPath) throws Exception {
        InputStream source = new FileInputStream(inputPath);
        OutputStream target = new FileOutputStream(outputPath);
        XWPFDocument doc = new XWPFDocument(source);
        PdfConverter.getInstance().convert(doc, target, null);
    }

    /**
     * 将word文档,转换成pdf,中间替换掉变量
     *
     * @param inputUrl  源为word文档,必须为docx文档
     * @param outputUrl  目标输出
     * @param params  需要替换的变量
     * @param options PdfOptions.create().fontEncoding( "windows-1250" ) 或者其他
     * @throws Exception
     */
    public static void wordConverterToPdf(String inputUrl, String outputUrl, PdfOptions options,
                                          Map<String, String> params) throws Exception {
        InputStream source = new FileInputStream(inputUrl);
        OutputStream target = new FileOutputStream(outputUrl);
        XWPFDocument doc = new XWPFDocument(source);
        paragraphReplace(doc.getParagraphs(), params);
        for (XWPFTable table : doc.getTables()) {
            for (XWPFTableRow row : table.getRows()) {
                for (XWPFTableCell cell : row.getTableCells()) {
                    paragraphReplace(cell.getParagraphs(), params);
                }
            }
        }
        PdfConverter.getInstance().convert(doc, target, options);
    }

    /**
     * 替换段落中内容
     */
    private static void paragraphReplace(List<XWPFParagraph> paragraphs, Map<String, String> params) {
        if (MapUtils.isNotEmpty(params)) {
            for (XWPFParagraph p : paragraphs) {
                for (XWPFRun r : p.getRuns()) {
                    String content = r.getText(r.getTextPosition());
                    if (StringUtils.isNotEmpty(content) && params.containsKey(content)) {
                        r.setText(params.get(content), 0);
                    }
                }
            }
        }
    }

    public static void main(String[] args) throws Exception {
        String inputPath = "C:\\Users\\fengshun\\Desktop\\【天弘票融】定融专项计划-票据质押合同-1号_1.docx";
        String outputPath = "C:\\Users\\fengshun\\Desktop\\test.pdf";

        wordToPdf(inputPath, outputPath);
    }
}