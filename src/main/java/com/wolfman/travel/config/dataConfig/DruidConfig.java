package com.wolfman.travel.config.dataConfig;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * druid配置
 */
@Configuration
public class DruidConfig {

    /**
     * 添加配置
     *
     * @return
     */
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource druid() {
        return new DruidDataSource();
    }

    /**
     * 配置Druid的监控 配置一个管理后台的Servlet
     *
     * @return
     */
    @Bean
    public ServletRegistrationBean statViewServlet() {
        ServletRegistrationBean bean = new ServletRegistrationBean(new StatViewServlet(), "/druid/*");
        Map<String, String> initParams = new HashMap<>();
        //druid后台用户名
        initParams.put("loginUsername", "admin");
        //druid后台密码
        initParams.put("loginPassword", "123456");
        //默认就是允许所有访问
        initParams.put("allow", "");//默认就是允许所有访问
        //设置参数
        bean.setInitParameters(initParams);
        return bean;
    }

}
