package io.sufeng.shell;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

/**
 * @author: sufeng
 * @create: 2020-01-20 17:07
 */
@ShellComponent
public class HttpShell {
//
//    String[][] cmd = new String[][]{
//            {"ls"," "," 显示当前目录的文件"},



//            {"http","-X[GET,POST,PUT,DELETE] -H[application/json,application/xml]  -D{\"key\":\"value\"}","执行http请求"},
//            {"query","",""}
//    };


    @ShellMethod("执行Http请求  http -xGET -H[content-type=application/json;charset=utf-8,token=123] -D[{\"name\":\"123\"}] http://localhost:8080/api/v1/query")
    public String http(@ShellOption(value = "-X" ,defaultValue = "GET") String method,
                       @ShellOption(value = "-H",defaultValue = "application/x-www-form-urlencoded;charset=utf-8") String headers,
                       @ShellOption(value = "-D" ,defaultValue = "") String data,
                       @ShellOption("-P") String url) {
        System.out.println(method);
        System.out.println(headers);
        System.out.println(data);
        System.out.println(url);
        return "response status : 200";
    }


}
