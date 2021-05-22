package com.wolfman.travel.Component;

import com.wolfman.travel.bean.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.LinkedHashMap;
import java.util.Map;

@Component
public final class LoginComponent {

    @Autowired
    CheckCodeComponent checkCodeComponent;

    @Autowired
    UserInfoComponent userInfoComponent;

    /**
     * 检查用户登录信息
     *
     * @param u
     * @param session
     * @param response
     * @return
     */
    public Map loginCheck(User u, HttpSession session, HttpServletResponse response, HttpServletRequest request) {
        Map map = new LinkedHashMap<String, Object>();
        //确认验证码
        if (!checkCodeComponent.checkCode(request)) {
            map.put("flag", false);
            map.put("errorMsg", "验证码错误");
            return map;
        }
        //判断用户对象是否为null
        if (u == null) {
            //用户名密码或错误
            map.put("flag", false);
            map.put("errorMsg", "用户名密码或错误");
            return map;
        }
        //判断用户是否激活
        if (u != null && !"Y".equals(u.getStatus())) {
            //用户尚未激活
            map.put("flag", false);
            map.put("errorMsg", "您尚未激活，请激活");
            return map;
        }
        //防止用户重复登录
        if(userInfoComponent.findOneUser(request)!=null)
        {
            map.put("flag", false);
            map.put("errorMsg", "您已登录，无需重复登录");
            return map;
        }
        //判断登录成功
        if (u != null && "Y".equals(u.getStatus())) {
            //登录成功
            map.put("flag", true);
            //保存用户信息到cookie
            userInfoComponent.saveUserInCookie(response, u);
            //保存用户到session
            userInfoComponent.saveUserInSession(session, u);
        }
        return map;
    }

}
