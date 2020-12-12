package test.fasdfs;

import org.csource.common.MyException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mineok.fastDFS.FastDFSApplication;
import org.mineok.fastDFS.service.FileService;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import javax.swing.*;
import java.io.File;
import java.io.IOException;

/**
 * @Author Gaoming
 * @Email mineok@foxmail.com
 * @Date 2020/12/12/ 16:39
 * @Description
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FastDFSApplication.class)
public class SimpleTest {

    @Test
    public void test01() {
        // 文件扩展名
        String name = "开题报告";
        StringBuilder path = new StringBuilder("E:\\CodeRepositories\\Graduation-project\\GP-CMS\\gp-fastDFS-service\\downloadFiles\\");
        // group1/M00/00/00/rBEZ9l_UmA2AdDE3AAAh4CydHpI229.zip
        // group1/M00/00/00/rBEZ9l_Um2GAUbHsAAHNz1zH-1g995.pdf
        // group1/M00/00/00/rBEZ9l_Un9eAYLjxAADF9L6oaFg383.jpg
        String fileName = "group1/M00/00/00/rBEZ9l_TavWAGuDHAAHJznqkte842.docx";
        String extName = fileName.substring(fileName.lastIndexOf(".") + 1);
        path.append(name).append(".").append(extName);
        System.out.println(path.toString());
    }

    @Resource
    FileService fileService;
    @Test
    public void test02() throws IOException, MyException {
//        fileService.fdfs_download("开题报告","group1/M00/00/00/rBEZ9l_TavWAGuDHAAHJznqkte842.docx");
    }

    @Test
    public void test03() throws IOException, MyException {
//        fileService.fdfs_download("开题报告","group1/M00/00/00/rBEZ9l_TavWAGuDHAAHJznqkte842.docx");
        File file = new File("E:\\CodeRepositories\\Graduation-project\\GP-CMS\\gp-fastDFS-service\\downloadFiles\\开题报告.docx");
        System.out.println(file.getName());
        System.out.println(file.getAbsolutePath());
        System.out.println(file.getCanonicalPath());
    }
}
