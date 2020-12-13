package org.mineok.fastDFS.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.File;

/**
 * @Author Gaoming
 * @Email mineok@foxmail.com
 * @Date 2020/12/13/ 21:47
 * @Description 定时任务 - 删除/downloadFile下的文件
 */
@Service
public class FileDeleteScheduleService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileService.class);

    @Value("${filePath}")
    String filePathInServer;

    /**
     * 设置定时任务:每天零点执行删除任务
     * cron = "0/5 * * * * ? 每隔5秒
     */
    @Scheduled(cron = "0 0 0 * * ?")
    public void deleteEmailFileTask() {
        LOGGER.info("Timed task start --> Temporary files in the server are deleted!");
        File folder = new File(filePathInServer);
        File[] files = folder.listFiles();
        for (int i = 0; i < files.length; i++) {
            files[i].delete();
        }
        LOGGER.info("Timed task end --> Temporary files in the server have been deleted!");
    }
}
