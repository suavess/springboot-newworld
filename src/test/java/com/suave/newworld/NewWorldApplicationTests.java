package com.suave.newworld;

import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.suave.newworld.beans.db.User;
import com.suave.newworld.beans.input.UserLoginInput;
import com.suave.newworld.common.RedisKeyConst;
import com.suave.newworld.dao.UserMapper;
import com.suave.newworld.utils.JwtTokenUtil;
import com.suave.newworld.utils.RedisUtil;
import org.apache.catalina.security.SecurityUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
class NewWorldApplicationTests {

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private UserMapper userMapper;

    @Test
    void contextLoads() {
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        HashMap<String, String> map = new HashMap<>(2);
        map.put("email","1132888093@qq.com");
        map.put("password",SecureUtil.md5("123456"));
        userQueryWrapper.allEq(map);
        System.out.println(userMapper.selectOne(userQueryWrapper));
//        String s = SecureUtil.md5("123456");
//        System.out.println("s = " + s);
    }

//    @Test
    public void testJwt(){
        User user = new User();
        user.setUsername("1132888093");
        String token = jwtTokenUtil.createToken(user);
        System.out.println("token = " + token);
        System.out.println("jwtTokenUtil.getUsernameFromToken(token) = " + jwtTokenUtil.getEmailFromToken(token));
    }

    //    @Test
    public void testGenerator() {
        //1. 全局配置
        GlobalConfig config = new GlobalConfig();
        config.setActiveRecord(true) // 是否支持AR模式
                .setAuthor("Suave") // 作者
                .setOutputDir("E:\\IDEA Project\\new_world\\src\\main\\java") // 生成路径
                .setFileOverride(true)  // 文件覆盖
                .setIdType(IdType.AUTO) // 主键策略
                .setEnableCache(false) //是否开启二级缓存
                .setServiceName("%sService")  // 设置生成的service接口的名字
                .setServiceImplName("%sServiceImpl")
                // IEmployeeService
                .setBaseResultMap(true)
                .setBaseColumnList(true);

        //2. 数据源配置
        DataSourceConfig dsConfig = new DataSourceConfig();
        dsConfig.setDbType(DbType.MYSQL)  // 设置数据库类型
                .setDriverName("com.mysql.cj.jdbc.Driver")
                .setUrl("jdbc:mysql://localhost:3306/new_world?serverTimezone=GMT%2B8")
                .setUsername("root")
                .setPassword("123456");

        //3. 数据库配置
        StrategyConfig stConfig = new StrategyConfig();
        stConfig.setCapitalMode(true) //全局大写命名
//                .setDbColumnUnderline(true)  // 指定表名 字段名是否使用下划线
                .setNaming(NamingStrategy.underline_to_camel) // 数据库表映射到实体的命名策略
//                .setTablePrefix("tab_") // 表前缀
                .setExclude("article_favorites", "article_tags") // 生成的表
                .setRestControllerStyle(true)
                .setEntityLombokModel(true)
                .setVersionFieldName("version");

        //4. 包名策略配置
        PackageConfig pkConfig = new PackageConfig();
        pkConfig.setParent("com.suave.newworld")
                .setMapper("dao")
                .setService("service")
                .setServiceImpl("service.impl")
                .setController("controller")
                .setEntity("beans")
                .setXml("mapper");

        //5. 整合配置
        AutoGenerator ag = new AutoGenerator();

        ag.setTemplateEngine(new FreemarkerTemplateEngine());

        ag.setGlobalConfig(config)
                .setDataSource(dsConfig)
                .setStrategy(stConfig)
                .setPackageInfo(pkConfig);

        //6. 执行
        ag.execute();
    }

}
