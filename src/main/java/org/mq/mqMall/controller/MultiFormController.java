package org.mq.mqMall.controller;

import org.mq.mqMall.dto.SecKillResult;
import org.mq.mqMall.entity.PersonalInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;

@Controller
public class MultiFormController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 前台页面获取的项目基本路径
     *
     * @return 项目基本路径
     */
    @GetMapping(path = "/personalInfo")
    public String personalInfo(HttpServletRequest request, Model model) {
        String basePath = ControllerUtil.basePath(request);
        model.addAttribute("basePath", basePath);
        return "personalInfo";
    }

    @PostMapping(path = "/personalInfo/save", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public SecKillResult<String> save(HttpServletRequest request, PersonalInfo personalInfo,
                                      @RequestParam(value = "images", required = false) MultipartFile[] images) {

        if (images == null) {
            logger.error("取不到图片文件");
            return new SecKillResult<>(true, "提交失败");
        }
        logger.info("personalInfo={}", personalInfo);

        //获取保存文件的路径
        String path = ControllerUtil.realPath(request) + File.separator + "upload" + File.separator;
        File dir = new File(path);
        if (!dir.exists()) {
            boolean result = dir.mkdir();
            if (!result) {
                logger.error("文件夹创建失败");
                return new SecKillResult<>(true, "提交失败");
            }
        }

        //存放文件
        for (MultipartFile image : images) {
            String imageName = image.getOriginalFilename();
            //判断文件名是否已存在
            File targetFile = new File(path + imageName);
            if (!targetFile.exists()) {
                try {
                    image.transferTo(targetFile); //保存文件
                } catch (IOException e) {
                    logger.error("保存文件" + imageName + "失败");
                    return new SecKillResult<>(true, "提交失败");
                }
            } else {
                logger.info(imageName + "文件已存在");
            }
        }

        return new SecKillResult<>(true, "提交成功");
    }

}
