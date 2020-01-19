package io.sufeng.core.config;

import io.sufeng.core.json.JSON;

import java.io.*;

/**
 * @author: sufeng
 * @create: 2020-01-19 13:54
 */

public class AppConfig {

    private String name = "工具";
    private String version ="1.0.0";
    private Object http;
    private Object code;
    private Object encryption;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public void setCode(Object code) {
        this.code = code;
    }

    public void setEncryption(Object encryption) {
        this.encryption = encryption;
    }

    public Object getHttp() {
        return http;
    }

    public void setHttp(Object http) {
        this.http = http;
    }

    public Object getCode() {
        return code;
    }

    public Object getEncryption() {
        return encryption;
    }

    public static class Builder{

        static AppConfig appConfig = null;

        public static AppConfig load(){

            if(appConfig != null){
                return appConfig;
            }

            try(InputStream resourceAsStream = AppConfig.class.getClassLoader().getResourceAsStream(".appConfig")) {
                    StringBuffer json = new StringBuffer();
                    InputStreamReader inputStreamReader = new InputStreamReader(resourceAsStream);
                    BufferedReader br = new BufferedReader(inputStreamReader);

                    String line = "";
                    while ((line = br.readLine()) != null) json.append(line);
                    appConfig = JSON.MP.toObject(json.toString(), AppConfig.class);
                    return  appConfig;
            } catch (Exception e) {
                appConfig = new AppConfig();
            }
            return appConfig;
        }

        public static void saveConfig() throws IOException {
            AppConfig load = load();
            System.out.println(load);
            File file  = new File(".appConfig");
            System.out.println(file.getAbsolutePath());
            FileOutputStream fis = new FileOutputStream(file);
            fis.write(JSON.MP.toString(load).getBytes());
            fis.close();
        }

    }


}
