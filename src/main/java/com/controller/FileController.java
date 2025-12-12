package com.controller;

import com.annotation.IgnoreAuth;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.entity.ConfigEntity;
import com.entity.EIException;
import com.service.ConfigService;
import com.utils.R;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

/**
 * 上传文件映射表
 */
@RestController
@RequestMapping("file")
@SuppressWarnings({"unchecked", "rawtypes"})
public class FileController {
    @Autowired
    private ConfigService configService;

    private static final String PERSIST_UPLOAD_PATH = "D:\\springbootq33sd\\src\\main\\resources\\static\\upload";

    /**
     * 上传文件
     */
    @RequestMapping("/upload")
    @IgnoreAuth
    public R upload(@RequestParam("file") MultipartFile file, String type) throws Exception {
        if (file.isEmpty()) {
            throw new EIException("上传文件不能为空");
        }

        try {
            String originalFilename = file.getOriginalFilename();
            if (originalFilename == null || !originalFilename.contains(".")) {
                throw new EIException("文件格式错误，无扩展名");
            }
            String fileExt = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
            String fileName = new Date().getTime() + "." + fileExt;

            File persistUploadDir = new File(PERSIST_UPLOAD_PATH);
            if (!persistUploadDir.exists()) {
                boolean mkdirSuccess = persistUploadDir.mkdirs();
                if (!mkdirSuccess) {
                    throw new EIException("持久化上传目录创建失败：" + persistUploadDir.getAbsolutePath());
                }
            }
            File persistDestFile = new File(persistUploadDir, fileName);
            file.transferTo(persistDestFile);

            if (StringUtils.isNotBlank(type) && type.equals("1")) {
                ConfigEntity configEntity = configService.selectOne(new EntityWrapper<ConfigEntity>().eq("name", "faceFile"));
                if (configEntity == null) {
                    configEntity = new ConfigEntity();
                    configEntity.setName("faceFile");
                    configEntity.setValue(fileName);
                } else {
                    configEntity.setValue(fileName);
                }
                configService.insertOrUpdate(configEntity);
            }

            return R.ok().put("file", fileName);

        } catch (IOException e) {
            e.printStackTrace();
            throw new EIException("文件上传失败：" + e.getMessage() + "，请检查目录权限或路径是否正确");
        } catch (Exception e) {
            e.printStackTrace();
            throw new EIException("文件上传异常：" + e.getMessage());
        }
    }

    /**
     * 下载文件
     */
    @IgnoreAuth
    @RequestMapping("/download")
    public ResponseEntity<byte[]> download(@RequestParam String fileName) {
        try {
            File persistUploadDir = new File(PERSIST_UPLOAD_PATH);
            if (!persistUploadDir.exists()) {
                persistUploadDir.mkdirs();
            }
            File file = new File(persistUploadDir, fileName);
            if (file.exists()) {
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
                headers.setContentDispositionFormData("attachment", fileName);
                return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file), headers, HttpStatus.CREATED);
            } else {
                return new ResponseEntity<byte[]>("文件不存在".getBytes(), HttpHeaders.EMPTY, HttpStatus.NOT_FOUND);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<byte[]>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // 新增：直接返回头像图片二进制流（绕过静态资源映射）
    @IgnoreAuth // 关键：添加该注解，确保不被登录拦截
    @RequestMapping("/getAvatar")
    public void getAvatar(@RequestParam String fileName, HttpServletResponse response) {
        try {
            // 1. 读取图片文件
            File avatarFile = new File(PERSIST_UPLOAD_PATH, fileName);
            if (!avatarFile.exists()) {
                response.setStatus(HttpStatus.NOT_FOUND.value());
                return;
            }

            // 2. 设置响应头（支持跨域、指定图片类型）
            response.setContentType("image/jpeg");
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Access-Control-Allow-Methods", "GET");

            // 3. 写入图片流到响应
            InputStream in = FileUtils.openInputStream(avatarFile);
            ServletOutputStream out = response.getOutputStream();
            byte[] buffer = new byte[1024];
            int len;
            while ((len = in.read(buffer)) != -1) {
                out.write(buffer, 0, len);
            }
            in.close();
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

}