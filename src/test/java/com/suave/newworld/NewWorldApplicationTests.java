package com.suave.newworld;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.suave.newworld.beans.Page;
import com.suave.newworld.beans.Tags;
import com.suave.newworld.beans.User;
import com.suave.newworld.beans.input.ArticlesListInput;
import com.suave.newworld.beans.output.ArticlesOutput;
import com.suave.newworld.dao.ArticlesMapper;
import com.suave.newworld.dao.TagsMapper;
import com.suave.newworld.dao.UserMapper;
import com.suave.newworld.service.ArticlesService;
import com.suave.newworld.utils.JwtTokenUtil;
import com.suave.newworld.utils.RedisUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@SpringBootTest
class NewWorldApplicationTests {

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private TagsMapper tagsMapper;
    @Autowired
    ArticlesMapper articlesMapper;
    @Autowired
    ArticlesService articlesService;

    @Test
    void contextLoads() {
        List<Tags> tags = tagsMapper.selectList(null);
        List<Integer> tmp = new ArrayList<>();
        tags.forEach(tag->{
            tmp.add(tag.getId());
        });
        System.out.println("tmp = " + tmp);
    }

    @Test
    public void testJwt(){
        articlesMapper.adminArticleListCount(null, "vue");
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
