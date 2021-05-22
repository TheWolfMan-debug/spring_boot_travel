package com.wolfman.travel.Component;

import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * 产生UUID随机字符串工具类
 */
@Component
public final class UuidComponent {
    private UuidComponent(){}

    /**
     * 产生一个随机数
     * @return
     */
    public String getUuid(){
        return UUID.randomUUID().toString().replace("-","");
    }
}
