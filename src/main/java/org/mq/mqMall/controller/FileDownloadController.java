package org.mq.mqMall.controller;

import org.mq.mqMall.dto.SecKillResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.StandardCharsets;

@Controller
public class FileDownloadController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 前台页面获取的项目基本路径
     *
     * @return 项目基本路径
     */
    @GetMapping(path = "/fileDownload")
    public String fileDownload(HttpServletRequest request, Model model) {
        String basePath = ControllerUtil.basePath(request);
        model.addAttribute("basePath", basePath);
        return "fileDownload";
    }

    @GetMapping(path = "/{fileName}/downloadFile")
    public SecKillResult<String> downloadFile(HttpServletRequest request, HttpServletResponse response, @PathVariable("fileName") String fileName) {

        String realPath = ControllerUtil.realPath(request);
        //读取文件夹
        File dir = new File(realPath + "download/");
        if (!dir.exists()) {
            logger.error("文件夹不存在");
            return new SecKillResult<>(false, "下载失败,文件不存在");
        }
        //读取文件
        logger.info("读取文件的路径" + realPath + "download/" + fileName);
        File targetFile = new File(realPath + "download/" + fileName);
        if (!targetFile.exists()) {
            logger.error("文件不存在");
            return new SecKillResult<>(false, "下载失败,文件不存在");
        }
        //设置响应头
        try {
            response.setContentType("image/png");
            response.setHeader("Content-disposition", "attachment; filename=" + new String(fileName.getBytes(StandardCharsets.UTF_8), "ISO8859-1"));
            response.setHeader("Content-Length", String.valueOf(targetFile.length()));
        } catch (UnsupportedEncodingException e) {
            logger.error("设置响应头信息失败");
            return new SecKillResult<>(false, "下载失败");
        }
        //文件传输
        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(targetFile.getAbsoluteFile()))) {
            BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream());
            byte[] buf = new byte[1024];
            int count;
            while (-1 != (count = bis.read(buf))) {
                bos.write(buf, 0, count);
            }
            logger.info("文件传输成功");
        } catch (Exception e) {
            logger.error("文件传输失败");
            return new SecKillResult<>(false, "下载失败");
        }

        return null;
    }

}
