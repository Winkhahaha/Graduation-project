package org.mineok.fastDFS.controller;

import org.csource.common.MyException;
import org.mineok.common.utils.R;
import org.mineok.fastDFS.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @Author Gaoming
 * @Email mineok@foxmail.com
 * @Date 2020/12/11/ 23:50
 * @Description
 */
@RestController
@RequestMapping("/file")
public class FileController {

    @Autowired
    FileService fileService;
    @Value("${filePath}")
    String filePathInServer;

    @RequestMapping("/upload")
    public R uploadFile(@RequestParam("file") MultipartFile file) throws IOException, MyException {
        return fileService.upload(file);
    }

    @RequestMapping("/download")
    public void downloadFile(HttpServletResponse response,
                          @RequestParam("fileId") String fileId) throws IOException, MyException {
        String filePath = fileService.fdfs_download("开题报告", fileId);
        File file = new File(filePath);
        Path path = Paths.get(filePathInServer, file.getName());
        if (file.exists()) {
            // 开始往浏览器运行
            // 1.获取文件后缀
            String fileSuffix = file.getName().substring(file.getName().lastIndexOf(".") + 1);
            // 2.设置contentType ,只有指定contentType才能下载
            response.setContentType("application/" + fileSuffix);
            // 3.添加http头信息,因为fileName的编码格式是UTF-8
            // 但是http头信息只识别 ISO8859-1 的编码格式,因此要对fileName重新编码
            try {
                response.addHeader("Content-Disposition", "attachment;filename=" + new String(file.getName().getBytes("UTF-8"), "ISO8859-1"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            Files.copy(path, response.getOutputStream());
//            ServletOutputStream outputStream = response.getOutputStream();
//            outputStream.write(data);
//            outputStream.close();
        }
    }

//    @RequestMapping("/download/test")
//    public void download(HttpServletResponse response) throws IOException, MyException {
//        fileService.downloadFileTobrowser(response, "开题报告", "");
//    }
}
