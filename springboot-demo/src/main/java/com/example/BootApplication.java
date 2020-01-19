package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;

/**
 * @author: sufeng
 * @create: 2020-01-15 17:44
 */
@org.springframework.boot.autoconfigure.SpringBootApplication
@RestController
public class BootApplication {

    public static void main(String[] args) {
        SpringApplication.run(BootApplication.class,args);
    }


    @GetMapping("/boot/get/hello")
//    public void hello(@RequestHeader("hostname") String hostname,@RequestHeader("chainId") String chainId){
    public void hello(HttpServletRequest request){
        System.out.println("header: "+String.join(",",String.join(",", Collections.list(request.getHeaderNames()))));
    }
}
