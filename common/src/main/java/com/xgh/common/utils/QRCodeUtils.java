package com.xgh.common.utils;

import com.google.zxing.*;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.Path;
import java.util.HashMap;

/**
 * Created by DELL on 2018/9/17.
 */
public class QRCodeUtils {
    private static final String format = "png";
    private static int defaultWidth = 300;//默认图片宽度
    private static int defaultHeight = 300;//默认高度
    private static  HashMap<EncodeHintType,Object> hints = new HashMap<>();
    {
        hints.put(EncodeHintType.CHARACTER_SET,"utf-8");
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
        hints.put(EncodeHintType.MARGIN, 2);
    }

    /**
     * 生产二维码
     * @param content 内容
     * @param destPath 存储路径
     */
    public static void createQRCode(String content,int width,int height,String destPath)throws Exception{

        BitMatrix bitMatrix = new MultiFormatWriter().encode(
                content, BarcodeFormat.QR_CODE,width,height,hints);

        File file = new File(destPath);
        MatrixToImageWriter.writeToFile(bitMatrix,format,file);
    }

    public static void createQRCode(String content,String destPath)throws Exception{

        BitMatrix bitMatrix = new MultiFormatWriter().encode(
                content, BarcodeFormat.QR_CODE,defaultWidth,defaultHeight,hints);

        File file = new File(destPath);
        MatrixToImageWriter.writeToFile(bitMatrix,format,file);
    }

    /**
     * 解析二维码
     * @throws Exception
     */
    public static String readQRCode(String qrcodePath) throws Exception{
        MultiFormatReader formatReader = new MultiFormatReader();
        File file = new File(qrcodePath);
        BufferedImage image = ImageIO.read(file);
        BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(new BufferedImageLuminanceSource(image)));
        HashMap hints=new HashMap();
        hints.put(EncodeHintType.CHARACTER_SET,"utf-8");    //指定字符编码为“utf-8”
        Result result=formatReader.decode(binaryBitmap,hints);
        return result.getText();
    }


    public static void main(String[] args) throws Exception{
//        QRCodeUtils.createQRCode("http://www.baidu.com","E:\\qr\\qrcode.png");
        System.out.println(readQRCode("E:\\qr\\qrcode.png"));
    }
}
