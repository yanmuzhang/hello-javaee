package io.sufeng.jmybatis.batch;


import com.alibaba.druid.pool.DruidDataSource;
import io.sufeng.jmybatis.TestMapper;
import io.sufeng.jmybatis.page.Page;
import io.sufeng.jmybatis.page.PageParames;
import io.sufeng.jmybatis.page.PagePlugin;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;

import java.util.List;
import java.util.Map;

public class BatchPluginTest {


    public static void main(String[] args) {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl("jdbc:mysql://10.0.0.1:3306/icbc_account?characterEncoding=utf8&useSSL=false&useTimezone=true&serverTimezone=Asia/Shanghai");
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUsername("root");
        dataSource.setPassword("admin");
        TransactionFactory transactionFactory = new JdbcTransactionFactory();
        Environment environment = new Environment("development", transactionFactory, dataSource);
        Configuration configuration = new Configuration(environment);
        configuration.setLazyLoadingEnabled(true);
        configuration.setEnvironment(environment);
        configuration.addMapper(TestMapper.class);
        configuration.addInterceptor(new BatchPlugin());
        configuration.addInterceptor(new PagePlugin());

        SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
        SqlSessionFactory factory = builder.build(configuration);
        SqlSession sqlSession = factory.openSession();
        TestMapper mapper = sqlSession.getMapper(TestMapper.class);
        List<Map<String,Object>> mapList = mapper.all();
        System.out.println(mapList.size());

//        Page<Map<String, Object>> page = mapper.page(PageParames.create(1, 3));
//        System.out.println(page);
    }
}