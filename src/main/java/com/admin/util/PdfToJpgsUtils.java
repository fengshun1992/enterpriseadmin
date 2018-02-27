package com.admin.util;


import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/*
 * pdf 转 图片
 */
public class PdfToJpgsUtils {

    /**
     * 将pdf转图片 并且自定义图片得格式大小
     *
     * @param inputPath
     * @param prefixPath
     * @param imagePath
     * @throws Exception
     */
    public static List<String> getImages(String inputPath, String prefixPath, String imagePath) throws Exception {
        String outputPath = prefixPath + imagePath;
        List<String> imgList = new ArrayList();
        File file = new File(inputPath);
        PDDocument doc = PDDocument.load(file);
        PDFRenderer renderer = new PDFRenderer(doc);
        int pageCount = doc.getNumberOfPages();
        for (int i = 0; i < pageCount; i++) {
            BufferedImage image = renderer.renderImageWithDPI(i, 96); // Windows native DPI
            BufferedImage srcImage = resize(image, 960, 960);//产生缩略图
            ImageIO.write(srcImage, "PNG", new File(outputPath + i + ".png"));
            imgList.add(imagePath + i + ".png");
        }
        doc.close();
        return imgList;
    }

    private static BufferedImage resize(BufferedImage source, int targetW, int targetH) {
        int type = source.getType();
        BufferedImage target = null;
        double sx = (double) targetW / source.getWidth();
        double sy = (double) targetH / source.getHeight();
        if (sx > sy) {
            sx = sy;
            targetW = (int) (sx * source.getWidth());
        } else {
            sy = sx;
            targetH = (int) (sy * source.getHeight());
        }
        if (type == BufferedImage.TYPE_CUSTOM) {
            ColorModel cm = source.getColorModel();
            WritableRaster raster = cm.createCompatibleWritableRaster(targetW, targetH);
            boolean alphaPremultiplied = cm.isAlphaPremultiplied();
            target = new BufferedImage(cm, raster, alphaPremultiplied, null);
        } else {
            target = new BufferedImage(targetW, targetH, type);
        }
        Graphics2D g = target.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g.drawRenderedImage(source, AffineTransform.getScaleInstance(sx, sy));
        g.dispose();
        return target;
    }

    public static void main(String[] args) throws Exception {

        String inputPath = "C:\\Users\\fengshun\\Desktop\\test.pdf";
        String prePath = "c:\\";
        String imagePath = "Users\\fengshun\\Desktop\\test\\";
        FileUtil.createDir(prePath + imagePath);
        PdfToJpgsUtils.getImages(inputPath, prePath, imagePath);

    }
}