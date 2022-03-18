package com.cy.springboot.controller;

import com.cy.springboot.entity.User;
import com.cy.springboot.service.IUserService;
import com.cy.springboot.service.ex.*;
import com.cy.springboot.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

//@Controller
@RestController
@RequestMapping("/user")
public class UserController extends BaseController {

    /** 头像文件大小的上限值(10MB) */
    public static final int AVATAR_MAX_SIZE = 10 * 1024 * 1024;
    /** 允许上传的头像的文件类型 */
    public static final List<String> AVATAR_TYPES = new ArrayList<String>();

    /** 初始化允许上传的头像的文件类型 */
    static {
        AVATAR_TYPES.add("image/jpeg");
        AVATAR_TYPES.add("image/png");
        AVATAR_TYPES.add("image/bmp");
        AVATAR_TYPES.add("image/gif");
    }

    @Autowired
    private IUserService userService;

    @RequestMapping("/reg")
    //@ResponseBody //表示此方法的响应结果以json格式进行数据的响应给到前端
    public JsonResult<Void> reg(User user) {
        userService.reg(user);
        return new JsonResult<>(OK);

    }

    @RequestMapping("/login")
    public JsonResult<User> login(String username, String password, HttpSession session) {
        User data = userService.login(username, password);
        session.setAttribute("uid", data.getUid());
        session.setAttribute("username", data.getUsername());
        System.out.println("Session中的uid=" + getUidFromSession(session));
        System.out.println("Session中的username=" + getUsernameFromSession(session));
        return new JsonResult<User>(OK, data);
    }

    @RequestMapping("/change_password")
    public JsonResult<Void> change_password(String oldPassword,String newPassword,HttpSession session){
        Integer uid = getUidFromSession(session);
        String username=getUsernameFromSession(session);
        userService.changePassword(uid,username,oldPassword,newPassword);
        return new JsonResult<>(OK);
    }

    @GetMapping("/get_by_uid")
    public JsonResult<User> getByUid(HttpSession session){
        // 从HttpSession对象中获取uid
        Integer uid = getUidFromSession(session);
        // 调用业务对象执行获取数据
        User data = userService.getByUid(uid);
        // 响应成功和数据
        return new JsonResult<User>(OK,data);
    }

    @RequestMapping("/change_info")
    public JsonResult<Void> changeInfo(User user, HttpSession session) {
        // 从HttpSession对象中获取uid和username
        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);
        // 调用业务对象执行修改用户资料
        userService.changeInfo(uid,username,user);
        // 响应成功
        return  new JsonResult<Void>(OK);
    }

    @PostMapping("change_avatar")
    public JsonResult<String> changeAvatar(@RequestParam("file") MultipartFile file, HttpSession session) {
        // 判断上传的文件是否为空
        // 是：抛出异常
        if (file.isEmpty()){
            throw new FileEmptyException("上传的头像文件不允许为空");
        }

        // 判断上传的文件大小是否超出限制值
        // 是：抛出异常
        if (file.getSize()>AVATAR_MAX_SIZE){
            throw new FileSizeException("不允许上传超过" + (AVATAR_MAX_SIZE / 1024) + "KB的头像文件");
        }

        // 判断上传的文件类型是否超出限制
        // 是：抛出异常
        String type = file.getContentType();
        if (!AVATAR_TYPES.contains(type)){
            throw new FileTypeException("不支持使用该类型的文件作为头像，允许的文件类型：\n" + AVATAR_TYPES);
        }

        // 获取当前项目的绝对磁盘路径
        String parent = session.getServletContext().getRealPath("upload");
        // 保存头像文件的文件夹
        File dir = new File(parent);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        // 保存的头像文件的文件名
        String suffix="";
        String originalFilename = file.getOriginalFilename();
        int beginIndex = originalFilename.lastIndexOf(".");
        if (beginIndex>0){
             suffix = originalFilename.substring(beginIndex);
        }
        String filename = UUID.randomUUID().toString() + suffix;
        // 创建文件对象，表示保存的头像文件
        File dest = new File(dir,filename);
        // 执行保存头像文件
        // 如果产生异常则抛出
        try {
            file.transferTo(dest);
        } catch (IllegalStateException e) {
            // 抛出异常
            throw new FileStateException("文件状态异常，可能文件已被移动或删除");
        } catch (IOException e) {
            // 抛出异常
            throw new FileUploadIOException("上传文件时读写错误，请稍后重尝试");
        }
        // 头像路径
        String avatar="/upload/"+filename;
        // 从Session中获取uid和username
        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);
        // 将头像写入到数据库中
        userService.changeAvatar(uid,username,avatar);
        // 返回成功和头像路径
        return new JsonResult<String>(OK,avatar);
    }
}
