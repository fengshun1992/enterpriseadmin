package com.admin.util;

import com.admin.entity.enterprise.ContractStamp;
import com.lowagie.text.Image;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class PdfStampUtils {

    /**
     * @param inputPath
     * @param list
     * @param outputPath
     * @throws Exception
     */
    public static void setWatermark(String inputPath, List<ContractStamp> list, String outputPath) throws Exception {
        PdfReader reader = new PdfReader(inputPath);
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(new File(outputPath)));
        PdfStamper stamper = new PdfStamper(reader, bos);

        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                ContractStamp contractStamp = list.get(i);
                PdfContentByte content = stamper.getOverContent(contractStamp.getPageNo());// 在内容上方加水印
                //content = stamper.getUnderContent(i);//在内容下方加水印
                Image image = Image.getInstance(contractStamp.getImgUrl());
                image.setAlignment(Image.LEFT | Image.TEXTWRAP);
                image.setRotationDegrees(contractStamp.getRotate() == null ? 0 : contractStamp.getRotate());//旋转角度
                image.setAbsolutePosition(contractStamp.getAbsoluteX(), contractStamp.getAbsoluteY());
                content.addImage(image);
            }
        }

        stamper.close();
    }


    public static void main(String[] args) throws Exception {
        String inputPath = "C:\\Users\\fengshun\\Desktop\\data\\qiye\\model\\1\\【天弘票融】定融专项计划-票据质押合同-1号_5.pdf";
        String outputPath = "C:\\Users\\fengshun\\Desktop\\data\\qiye\\model\\1\\【天弘票融】定融专项计划-票据质押合同-1号_6.pdf";
        String imgUrl = "C:\\Users\\fengshun\\Desktop\\data\\qiye\\stamp\\yitong\\fr\\1-1.png";
        //File file = new File(imgUrl);
        List<ContractStamp> list = new ArrayList<>();
        ContractStamp contractStamp = new ContractStamp();
        contractStamp.setPageNo(9);
        contractStamp.setAbsoluteX(470);
        contractStamp.setAbsoluteY(270);
        contractStamp.setRotate(10);
        contractStamp.setImgUrl(imgUrl);
        list.add(contractStamp);

        /*ContractStamp contractStamp2 = new ContractStamp();
        contractStamp2.setPageNo(9);
        contractStamp2.setAbsoluteX(180);
        contractStamp2.setAbsoluteY(250);
        contractStamp2.setImgUrl(imgUrl);
        list.add(contractStamp2);*/
        // 将pdf文件先加水印然后输出
        setWatermark(inputPath, list, outputPath);
    }
}