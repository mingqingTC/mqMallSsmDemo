package org.mq.mqMall.controller;

import org.mq.mqMall.dto.SecKillResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;

@Controller
public class FileUploadController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 前台页面获取的项目基本路径
     *
     * @return 项目基本路径
     */
    @GetMapping(path = "/fileUpload")
    public String fileUpload(HttpServletRequest request, Model model) {
        String basePath = ControllerUtil.basePath(request);
        model.addAttribute("basePath", basePath);
        return "fileUpload";
    }

    /**
     * 单文件上传
     *
     * @param request request请求对象
     * @param imgFile 文件对象
     * @return 上传结果
     */
    @PostMapping(path = "/uploadFileSingle", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public SecKillResult<String> uploadFileSingle(HttpServletRequest request, @RequestParam("imgFile") MultipartFile imgFile) {
        if (imgFile == null) {
            logger.error("上传文件为空");
            return new SecKillResult<>(false, "上传失败");
        }

        String fileName = imgFile.getOriginalFilename();
        String realPath = ControllerUtil.realPath(request);
        //创建文件夹
        File dir = new File(realPath + "upload/");
        if (!dir.exists()) {
            boolean result = dir.mkdir();
            if (!result) {
                logger.error("创建文件夹失败");
                return new SecKillResult<>(false, "上传失败");
            }
        }

        File targetFile = new File(realPath + "upload/" + fileName);
        try {
            if (!targetFile.exists()) {
                boolean result = targetFile.createNewFile();
                if (!result) {
                    logger.error("创建文件失败");
                    return new SecKillResult<>(false, "上传失败");
                }
            } else {
                logger.info("文件已存在");
                return new SecKillResult<>(true, "文件已存在");
            }
            imgFile.transferTo(targetFile);
            logger.info("文件上传成功,保存路径为: " + targetFile.getAbsolutePath());
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new SecKillResult<>(false, "上传失败");
        }

        return new SecKillResult<>(true, "上传成功");
    }

    @PostMapping(path = "/uploadFileMulti", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public SecKillResult<String> uploadFileMulti(HttpServletRequest request) {

        MultipartHttpServletRequest mRequest = (MultipartHttpServletRequest) request;
        MultiValueMap<String, MultipartFile> fileMap = mRequest.getMultiFileMap();
        String realPath = ControllerUtil.realPath(request);
        //创建文件夹
        File dir = new File(realPath + "upload/");
        if (!dir.exists()) {
            boolean result = dir.mkdir();
            if (!result) {
                logger.info("创建文件夹失败");
                return new SecKillResult<>(false, "上传失败");
            }
        }
        for (String fileName : fileMap.keySet()) {
            MultipartFile file = fileMap.get(fileName).get(0);
            File targetFile = new File(realPath + "upload/" + file.getOriginalFilename());
            try {
                if (!targetFile.exists()) {
                    boolean result = targetFile.createNewFile();
                    if (!result) {
                        logger.error("创建文件失败");
                        return new SecKillResult<>(false, "上传失败");
                    }
                    file.transferTo(targetFile);
                    logger.info("文件上传成功,保存路径为: " + targetFile.getAbsolutePath());
                } else {
                    logger.info(file.getOriginalFilename() + "文件已存在");
                }
            } catch (Exception e) {
                logger.error(e.getMessage());
                return new SecKillResult<>(false, "上传失败");
            }
        }
        return new SecKillResult<>(true, "上传成功");
    }
}
