package org.mineok.fastDFS.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.io.File;

/**
 * @Author Gaoming
 * @Email mineok@foxmail.com
 * @Date 2020/12/13/ 21:47
 * @Description 定时任务
 */
@Service
public class FileDeleteScheduleService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileService.class);

    @Value("${filePath}")
    String filePathInServer;

    /**
     * 设置定时任务:每日零点删除/downloadFile下的临时文件
     * cron = "0/5 * * * * ? 每隔5秒(测试用)
     */
    @Scheduled(cron = "0 0 0 * * ?")
    public void deleteEmailFileTask() {
        LOGGER.info("Timed task start --> Temporary files in the server are deleted!");
        File folder = new File(filePathInServer);
        File[] files = folder.listFiles();
        for (File file : files) {
            if (!ObjectUtils.isEmpty(file)) {
                file.delete();
            }
        }
        LOGGER.info("Timed task end --> Temporary files in the server have been deleted!");
    }
}
