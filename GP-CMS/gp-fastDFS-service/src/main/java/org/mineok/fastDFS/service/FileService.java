package org.mineok.fastDFS.service;

import org.csource.common.MyException;
import org.csource.fastdfs.*;
import org.mineok.common.utils.R;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @Author Gaoming
 * @Email mineok@foxmail.com
 * @Date 2020/12/11/ 23:02
 * @Description
 */
@Service
public class FileService {

    @Value("${filePath}")
    String filePathInServer;

    private static final Logger LOGGER = LoggerFactory.getLogger(FileService.class);


    // 加载FastDFS的配置
    private void initFastDFSConfig() throws IOException, MyException {
        ClientGlobal.initByProperties("E:\\CodeRepositories\\Graduation-project\\GP-CMS\\gp-fastDFS-service\\src\\main\\resources\\config\\fastdfs-client.properties");
    }

    // 上传文件
    public R upload(MultipartFile file) throws IOException, MyException {
        if (ObjectUtils.isEmpty(file)) {
            R.error("文件异常！");
        }
        String fileId = fdfs_upload(file);
        return R.ok().put("fileId", fileId);
    }

    // 上传文件到fdfs，返回文件id
    public String fdfs_upload(MultipartFile file) throws IOException, MyException {
        // 加载fdfs的配置
        initFastDFSConfig();
        LOGGER.info("Fastdfs configuration loaded!");
        // 创建trackerClient
        TrackerClient trackerClient = new TrackerClient();
        // 获取trackerServer
        TrackerServer trackerServer = trackerClient.getConnection();
        // 获取storage
        StorageServer storeStorage = trackerClient.getStoreStorage(trackerServer);
        // 创建storage client
        StorageClient1 storageClient1 = new StorageClient1(trackerServer, storeStorage);
        // 上传文件
        // 文件字节
        byte[] bytes = file.getBytes();
        // 文件原始名称
        String originalFilename = file.getOriginalFilename();
        // 文件扩展名
        String extName = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
        // 文件id
        String fileId = storageClient1.upload_file1(bytes, extName, null);
        LOGGER.info("File: " + fileId);
        return fileId;
    }

    /**
     * @param fileName 原文件名
     * @param fileId   fastdfs中的data_url
     * @throws IOException
     * @throws MyException
     */
    public String fdfs_download(String fileName, String fileId) throws IOException, MyException {
        // 加载fdfs的配置
        initFastDFSConfig();
        LOGGER.info("Fastdfs configuration loaded!");
        TrackerClient trackerClient = new TrackerClient();
        // 连接Tracker
        TrackerServer trackerServer = trackerClient.getConnection();
        // 获取Storage
        StorageServer storeStorage = trackerClient.getStoreStorage(trackerServer);
        // 创建StroageClient
        StorageClient1 storageClient1 = new StorageClient1(trackerServer, storeStorage);
        // 下载文件
        byte[] bytes = storageClient1.download_file1(fileId);
        LOGGER.info(fileId + " The file has been downloaded!");
        // 为文件拼装路径名称
        StringBuilder path = new StringBuilder();
        // 1.拼接文件所在服务器文件夹
        path.append(filePathInServer).append("\\");
        // 2.拼接文件原名
        path.append(fileName);
        // 3.拼接文件扩展名
        String extName = fileId.substring(fileId.lastIndexOf(".") + 1);
        path.append(".").append(extName);
        // 输出流输出文件
        FileOutputStream fileOutputStream = new FileOutputStream(new File(path.toString()));
        fileOutputStream.write(bytes);
        LOGGER.info("The file has been generated: " + path.toString());
//        downloadFileTobrowser(response, path.toString());
        LOGGER.info("The file begins to download in the browser!");
        return path.toString();
    }

    /**
     * @param response
     * @param filePath fastdfs保存在服务器中的文件路径
     */
    public void downloadFileTobrowser(HttpServletResponse response, String filePath) throws IOException {
//        File file = new File(filePath);
//        Path path = Paths.get(filePathInServer, file.getName());
//        if (file.exists()) {
//            // 开始往浏览器运行
//            // 1.获取文件后缀
//            String fileSuffix = file.getName().substring(file.getName().lastIndexOf(".") + 1);
//            // 2.设置contentType ,只有指定contentType才能下载
//            response.setContentType("application/" + fileSuffix);
//            // 3.添加http头信息,因为fileName的编码格式是UTF-8
//            // 但是http头信息只识别 ISO8859-1 的编码格式,因此要对fileName重新编码
//            try {
////                response.addHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes("UTF-8"), "ISO8859-1"));
//            } catch (UnsupportedEncodingException e) {
//                e.printStackTrace();
//            }
//            Files.copy(path, response.getOutputStream());
////            ServletOutputStream outputStream = response.getOutputStream();
////            outputStream.write(data);
////            outputStream.close();
//        }
    }


}
