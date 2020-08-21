package io.sufeng.jmybatis;

/**
 * @author: sufeng
 * @create: 2020-04-22 16:47
 */
public interface BaseMapper<T,PK> {


    <T> T getOne(PK pk);
}
