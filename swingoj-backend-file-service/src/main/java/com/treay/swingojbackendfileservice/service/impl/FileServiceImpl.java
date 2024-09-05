package com.treay.swingojbackendfileservice.service.impl;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import com.treay.swingojbackendfileservice.service.FileService;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.InputStream;
import java.util.Objects;

/**
 * @author Treay
 * 阿里云对象存储实现类
 */
@Service
public class FileServiceImpl implements FileService {

    /**
     * 上传头像到OSS
     */
    @Override
    public String uploadFileAvatar(MultipartFile multipartFile) {

        try {
            // MultipartFile与File之间转化
            File file = File.createTempFile(Objects.requireNonNull(multipartFile.getOriginalFilename()), null);
            try (InputStream inputStream = multipartFile.getInputStream()) {
                FileUtils.copyInputStreamToFile(inputStream, file);
            }

            String request = "https://f5c98967.cloudflare-imgbed-5yc.pages.dev/upload?authCode=3";
            // 构造请求
            String responseStr = HttpUtil.createPost(request)
                    .form("file",file) // form方法可以将文件作为body传送，
                    .header("User-Agent", "Apifox/1.0.0 (https://apifox.com)")
                    .execute()
                    .body();
            System.out.println(responseStr);
            // 获取响应中的图片url
            JSONArray responseArray = JSONUtil.parseArray(responseStr);
            if (responseArray.isEmpty()) {
                System.out.println("上传失败");
            }
            System.out.println(responseArray);
            String partUrl = responseArray.getJSONObject(0).getStr("src");
            String url = "https://f5c98967.cloudflare-imgbed-5yc.pages.dev" + partUrl;
            System.out.println(url);
            return url;
        } catch (Exception e) {
            e.printStackTrace();
            // 上传失败 降级改为默认头像
            return "https://p3-passport.byteimg.com/img/user-avatar/2ea9106b748a0b88d5bfcf517a4dc2ef~180x180.awebp";
        }
    }

    /**
     * Description: 判断OSS服务文件上传时文件的contentType
     *
     * @param filenameExtension 文件后缀
     * @return String
     */
    public static String getcontentType(String filenameExtension) {
        if (filenameExtension.equalsIgnoreCase("bmp")) {
            return "image/bmp";
        }
        if (filenameExtension.equalsIgnoreCase("gif")) {
            return "image/gif";
        }
        if (filenameExtension.equalsIgnoreCase("jpeg") || filenameExtension.equalsIgnoreCase("jpg")
                || filenameExtension.equalsIgnoreCase("png")) {
            return "image/jpeg";
        }
        if (filenameExtension.equalsIgnoreCase("html")) {
            return "text/html";
        }
        if (filenameExtension.equalsIgnoreCase("txt")) {
            return "text/plain";
        }
        if (filenameExtension.equalsIgnoreCase("vsd")) {
            return "application/vnd.visio";
        }
        if (filenameExtension.equalsIgnoreCase("pptx") || filenameExtension.equalsIgnoreCase("ppt")) {
            return "application/vnd.ms-powerpoint";
        }
        if (filenameExtension.equalsIgnoreCase("docx") || filenameExtension.equalsIgnoreCase("doc")) {
            return "application/msword";
        }
        if (filenameExtension.equalsIgnoreCase("xml")) {
            return "text/xml";
        }
        return "application/octet-stream";
    }

    /**
     * 上传头像到OSS
     */
//    public String uploadFileAvatarOSS(MultipartFile file) {
//
//        //工具类获取值
//        String endpoint = FileUtils.END_POINT;
//        String accessKeyId = FileUtils.KEY_ID;
//        String accessKeySecret = FileUtils.KEY_SECRET;
//        String bucketName = FileUtils.BUCKET_NAME;
//        InputStream inputStream = null;
//        try {
//            // 创建OSS实例
//            OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
//            // 把文件按照日期分类，获取当前日期
//            String datePath = new DateTime().toString("yyyy-MM-dd");
//            // 获取上传文件的输入流
//            inputStream = file.getInputStream();
//            // 获取文件名称
//            String originalFileName = file.getOriginalFilename();
//
//            // 拼接日期和文件路径
//            String fileName = datePath + "/" + originalFileName;
//
//            // 判断文件是否存在
//            boolean exists = ossClient.doesObjectExist(bucketName, fileName);
//            if (exists) {
//                // 如果文件已存在，则先删除原来的文件再进行覆盖
//                ossClient.deleteObject(bucketName, fileName);
//            }
//
//            // 创建上传Object的Metadata
//            ObjectMetadata objectMetadata = new ObjectMetadata();
//            objectMetadata.setContentLength(inputStream.available());
//            objectMetadata.setContentType(getcontentType(fileName.substring(fileName.lastIndexOf("."))));
//            objectMetadata.setContentDisposition("attachment; filename=" + URLEncoder.encode(fileName, "UTF-8"));
//
//            //调用oss实例中的方法实现上传
//            //参数1： Bucket名称
//            //参数2： 上传到oss文件路径和文件名称 /aa/bb/1.jpg
//            //参数3： 上传文件的输入流
//            ossClient.putObject(bucketName, fileName, inputStream);
//
//            // 关闭OSSClient。
//            ossClient.shutdown();
//
//            // 把上传后文件路径返回，需要把上传到阿里云oss路径手动拼接出来
//            String url = "https://" + bucketName + "." + endpoint + "/" + fileName;
//            return url;
//        } catch (IOException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
}