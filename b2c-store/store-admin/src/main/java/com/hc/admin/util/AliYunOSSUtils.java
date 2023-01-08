package com.hc.admin.util;

/**
 * @author: 何超
 * @date: 2022/11/18
 */

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.GetObjectRequest;
import com.aliyun.oss.model.ObjectMetadata;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.Date;

/**
 * projectName: b2c-cloud-store
 *
 * @author: 赵伟风
 * description:
 */
@Component
@Data
@ConfigurationProperties(prefix = "aliyun.oss.file")
public class AliYunOSSUtils {

    //基本属性 读取配置文件
    private String endPoint;
    private String keyId;
    private String keySecret;
    private String bucketName;


    /**
     * byte数组格式上传文件并返回上传后的URL地址
     *
     * @param objectName  完整文件名, 例如abc/efg/123.jpg
     * @param content     文件内容, byte数组格式
     * @param contentType 文件类型   image/png  image/jpeg
     * @param hours       过期时间   单位小时
     * @Author zhaoweifeng
     */
    public String uploadImage(String objectName,
                              byte[] content, String contentType, int hours) throws Exception {
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endPoint, keyId, keySecret);
        // 创建上传文件的元信息，可以通过文件元信息设置HTTP header(设置了才能通过返回的链接直接访问)。
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(contentType);
        // 文件上传
        ossClient.putObject(bucketName, objectName, new ByteArrayInputStream(content), objectMetadata);
        // 设置URL过期时间为hours小时。
        Date expiration = new Date(System.currentTimeMillis() + 3600 * 1000 * 300 * hours);
        //返回url地址
        String url = ossClient.generatePresignedUrl(bucketName, objectName, expiration).toString();
        //关闭OSSClient。
        ossClient.shutdown();
        return url;
    }

    /**
     * 下载文件到本地
     *
     * @param objectName 完整文件名, 例如abc/efg/123.jpg
     * @param localFile  下载到本地文件目录
     * @Author zhaoweifeng
     */
    public void downFile(String objectName,
                         String localFile) throws Exception {
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endPoint, keyId, keySecret);

        // 下载OSS文件到本地文件。如果指定的本地文件存在会覆盖，不存在则新建。
        ossClient.getObject(new GetObjectRequest(bucketName, objectName), new File(localFile));

        // 关闭OSSClient。
        ossClient.shutdown();
    }

    /**
     * 删除文件
     *
     * @param objectName 完整文件名, 例如abc/efg/123.jpg
     * @Author zhaoweifeng
     */
    public void deleteFile(String objectName) {
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endPoint, keyId, keySecret);

        // 删除文件。如需删除文件夹，请将ObjectName设置为对应的文件夹名称。如果文件夹非空，则需要将文件夹下的所有object删除后才能删除该文件夹。
        ossClient.deleteObject(bucketName, objectName);

        // 关闭OSSClient。
        ossClient.shutdown();
    }

}
