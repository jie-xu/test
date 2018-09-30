package com.xgh.service;

import com.xgh.common.utils.QRCodeUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;

/**
 * Created by DELL on 2018/9/27.
 */
@Service
public class ImageService {

    @Value("${nfs.xgh.rootPath}")
    private String imageRootPath;

    @Value("${web.domain}")
    private String domain;

    public String getQRCodeImage(String content,String userId) throws Exception{
        String qrCodePath = imageRootPath + File.separator + "qrcode/deduct";
        String path = "/qrcode/deduct" + "/" + "deduct_" + userId + ".png";
        String imageFilePath = qrCodePath + path;
        File f = new File(imageFilePath);
        if(!f.exists()){
            //生成二维码
            QRCodeUtils.createQRCode(content,imageFilePath);
        }
        return domain + "/static" + path;
    }

}
