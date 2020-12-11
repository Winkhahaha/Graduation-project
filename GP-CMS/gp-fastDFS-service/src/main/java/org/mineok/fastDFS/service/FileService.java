package org.mineok.fastDFS.service;

import org.csource.common.MyException;
import org.csource.fastdfs.*;
import org.mineok.common.utils.R;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @Author Gaoming
 * @Email mineok@foxmail.com
 * @Date 2020/12/11/ 23:02
 * @Description
 */
@Service
public class FileService {

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

}
