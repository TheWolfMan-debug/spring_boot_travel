package com.wolfman.travel.service.impl;


import com.wolfman.travel.Component.MailComponent;
import com.wolfman.travel.Component.Md5Component;
import com.wolfman.travel.Component.UuidComponent;
import com.wolfman.travel.bean.User;
import com.wolfman.travel.mapper.UserMapper;
import com.wolfman.travel.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper; //注入userMapper

    @Autowired
    MailComponent mailComponent;

    @Autowired
    UuidComponent uuidComponent;

    @Autowired
    Md5Component md5Component;

    /**
     * 用户注册
     *
     * @param user
     * @param request
     * @return
     */
    @Override
    public boolean register(User user, HttpServletRequest request) {
        //根据用户名查询用户对象
        User u = userMapper.findByUsername(user.getUsername());
        //判断u是否为null
        if (u != null) {
            //用户名存在，注册失败
            return false;
        }
        //保存用户信息
        //设置激活码，唯一字符串
        user.setCode(uuidComponent.getUuid());
        //设置激活状态
        user.setStatus("N");
        try {
            //将用户密码加密
            user.setPassword(md5Component.encodeByMd5(user.getPassword()));
            //保存用户
            userMapper.save(user);
            //邮件内容
            String content = "<a href='http://localhost:8888/user/active?code=" + user.getCode() + "'>点击激活【黑马旅游网】</a>";
            mailComponent.sendMail(user.getEmail(), content);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;

    }


    /**
     * 用户登录
     *
     * @param user
     * @return
     */
    @Override
    public User login(User user) {
        User u;
        try {
            //根据用户名和密码查询用户
            u = userMapper.findByUsernameAndPassword(user.getUsername(), md5Component.encodeByMd5(user.getPassword()));
        } catch (Exception e) {
            return null;
        }
        return u;
    }

    /**
     * 激活用户
     *
     * @param code
     * @return
     */
    @Override
    public boolean active(String code) {
        //1.根据激活码查询用户对象
        User user = userMapper.findByCode(code);
        if (user != null) {
            //2.调用dao的修改激活状态的方法
            userMapper.updateStatus(user);
            return true;
        } else {
            return false;
        }
    }


}
