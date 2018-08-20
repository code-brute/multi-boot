package com.jackson.core.config;


import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContextException;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.sql.SQLException;
import java.util.Arrays;

import javax.sql.DataSource;

/**
 *
 * 凡是被Spring管理的类，实现接口 EnvironmentAware 重写方法 setEnvironment
 * 可以在工程启动时，获取到系统环境变量和application配置文件中的变量。
 *
 * @Description:
 * @author: Mitnick
 * @date: 2017-08-19 14:04
 */
@Configuration
@EnableTransactionManagement
@ConditionalOnClass(com.alibaba.druid.pool.DruidDataSource.class)
@ConditionalOnProperty(name = "spring.datasource.type",
        havingValue = "com.alibaba.druid.pool.DruidDataSource",
        matchIfMissing = true)
@MapperScan("com.dingxuan.atrm.**.dao")
public class DatabaseConfiguration implements EnvironmentAware {

    private final Logger logger = LoggerFactory.getLogger(DatabaseConfiguration.class);

    private Environment environment;

    private RelaxedPropertyResolver propertyResolver;

    @Override
    public void setEnvironment(Environment environment) {
        this.propertyResolver = new RelaxedPropertyResolver(environment, "spring.datasource.");
    }

    @Bean
    public ServletRegistrationBean servletRegistrationBean() {
        logger.debug("连接池初始化：开始注册DRUID。");
        logger.debug("连接池初始化：DRUID白名单【{}】",propertyResolver.getProperty("allow"));
        logger.debug("连接池初始化：黑名单【{}】",propertyResolver.getProperty("deny"));
        logger.debug("连接池初始化：DRUID登陆名【{}】", propertyResolver.getProperty("loginUsername"));
        logger.debug("连接池初始化：DRUID密码【{}】", propertyResolver.getProperty("loginPassword"));
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(new StatViewServlet(), "/druid/*");
        //白名单：
        servletRegistrationBean.addInitParameter("allow", propertyResolver.getProperty("allow"));
        //IP黑名单 (存在共同时，deny优先于allow) : 如果满足deny的即提示:Sorry, you are not permitted to view this page.
        servletRegistrationBean.addInitParameter("deny", propertyResolver.getProperty("deny"));
        //登录查看信息的账号密码.
        servletRegistrationBean.addInitParameter("loginUsername", propertyResolver.getProperty("loginUsername"));
        servletRegistrationBean.addInitParameter("loginPassword", propertyResolver.getProperty("loginPassword"));
        //是否能够重置数据.
        servletRegistrationBean.addInitParameter("resetEnable", propertyResolver.getProperty("resetEnable"));
        logger.debug("连接池初始化：注册DRUID完成。");
        return servletRegistrationBean;
    }

    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        logger.debug("连接池初始化：DRUID开始添加过滤规则。");
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(new WebStatFilter());
        //添加过滤规则.
        filterRegistrationBean.addUrlPatterns("/*");
        //添加不需要忽略的格式信息.
        filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
        logger.debug("连接池初始化：DRUID过滤规则添加完成。");
        return filterRegistrationBean;
    }

    @Bean
    PersistenceExceptionTranslationPostProcessor persistenceExceptionTranslationPostProcessor() {
        return new PersistenceExceptionTranslationPostProcessor();
    }

    /**
     * 数据源配置
     *
     * @return
     */
    @Bean
    @Primary
    public DataSource datasource() {
        logger.debug("连接池初始化：开始初始化DataSource");

        if (propertyResolver.getProperty("url") == null) {
            logger.error("Your database connection pool configuration is incorrect! The application" +
                            "cannot start. Please check your Spring profile, current profiles are: {}",
                    Arrays.toString(environment.getActiveProfiles()));
            throw new ApplicationContextException("Database connection pool is not configured correctly");
        }
        logger.debug("连接池初始化：URL【{}】",propertyResolver.getProperty("url"));
        logger.debug("连接池初始化：USERNAME【{}】",propertyResolver.getProperty("username"));
        logger.debug("连接池初始化：PASSWORD【{}】",propertyResolver.getProperty("password"));
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setUrl(propertyResolver.getProperty("url"));
        druidDataSource.setDriverClassName(propertyResolver.getProperty("driver-class-name"));
        druidDataSource.setUsername(propertyResolver.getProperty("username"));
        druidDataSource.setPassword(propertyResolver.getProperty("password"));
        druidDataSource.setInitialSize(Integer.parseInt(propertyResolver.getProperty("initialSize")));
        druidDataSource.setMaxActive(Integer.parseInt(propertyResolver.getProperty("maxActive")));
        druidDataSource.setMinIdle(Integer.parseInt(propertyResolver.getProperty("minIdle")));
        druidDataSource.setMaxWait(Integer.parseInt(propertyResolver.getProperty("maxWait")));
        druidDataSource.setPoolPreparedStatements(Boolean.parseBoolean(propertyResolver.getProperty("poolPreparedStatements")));
        druidDataSource.setMaxOpenPreparedStatements(Integer.parseInt(propertyResolver.getProperty("maxOpenPreparedStatements")));
        druidDataSource.setValidationQuery(propertyResolver.getProperty("validationQuery"));
        druidDataSource.setTestOnBorrow(Boolean.parseBoolean(propertyResolver.getProperty("testOnBorrow")));
        druidDataSource.setTestOnReturn(Boolean.parseBoolean(propertyResolver.getProperty("testOnReturn")));
        druidDataSource.setTestWhileIdle(Boolean.parseBoolean(propertyResolver.getProperty("testWhileIdle")));
        druidDataSource.setTimeBetweenConnectErrorMillis(Long.parseLong(propertyResolver.getProperty("timeBetweenEvictionRunsMillis")));
        druidDataSource.setMinEvictableIdleTimeMillis(Long.parseLong(propertyResolver.getProperty("minEvictableIdleTimeMillis")));
        try {
            druidDataSource.setFilters(propertyResolver.getProperty("filters"));
        } catch (SQLException e) {
            logger.error("Init druidDataSource exception :{}", e.getMessage(), e);
        }
        logger.debug("连接池初始化：初始化DataSource完成。");
        return druidDataSource;
    }

}

