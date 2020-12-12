package org.mineok.fastDFS.controller;

import org.csource.common.MyException;
import org.mineok.common.utils.R;
import org.mineok.fastDFS.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

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

    @RequestMapping("/upload")
    public R uploadFile(@RequestParam("file") MultipartFile file) throws IOException, MyException {
        return fileService.upload(file);
    }
}
