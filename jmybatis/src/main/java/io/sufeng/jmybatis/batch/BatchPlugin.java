package io.sufeng.jmybatis.batch;

import io.sufeng.jmybatis.page.PagePlugin;
import org.apache.ibatis.executor.CachingExecutor;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.Properties;

/**
 * @author: sufeng
 * @create: 2020-04-20 11:14
 */
@Intercepts({@Signature(
        type = Executor.class,
        method = "query",
        args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class})})
public class BatchPlugin implements Interceptor {

    private int batchCount = 200;

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Long aLong = PagePlugin.count.get();
        aLong++;
        PagePlugin.count.set(aLong);
        System.out.println(invocation.getTarget().getClass().getSimpleName()+":"+aLong);
        Method method = invocation.getMethod();
        CachingExecutor cachingExecutor = (CachingExecutor)invocation.getTarget();
        System.out.println("BatchPlugin:"+invocation.getTarget());
        System.out.println("BatchPlugin:"+method.getName());
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
        String batchCount = properties.getProperty("batchCount");
        if (batchCount != null && !"".equals(batchCount)) {
            this.batchCount = Integer.valueOf(batchCount);
        }
    }
}
