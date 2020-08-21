package io.sufeng.jmybatis;

import io.sufeng.jmybatis.page.Page;
import io.sufeng.jmybatis.page.PageParames;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;
import java.util.Map;

/**
 * @author: sufeng
 * @create: 2020-04-20 11:44
 */
public interface TestMapper {
    //    @Select("select * from T_DIGICCY_BILLS")
    @SelectProvider(type = BaseSelectAllProvider.class, method = "all")
    List<Map<String, Object>> all();
//    @Select("select * from T_DIGICCY_BILLS")
//    Page<Map<String,Object>> page(PageParames pageParames);

}
