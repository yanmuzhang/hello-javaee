package io.sufeng.shell;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.util.StringUtils;

/**
 * @author: sufeng
 * @create: 2020-01-20 17:16
 */
@SpringBootApplication
public class ShellApplication {

    public static void main(String[] args) {
        String[] disabledCommands = {"--spring.shell.command.help.enabled=true"};
        String[] fullArgs = StringUtils.concatenateStringArrays(args,disabledCommands);
        SpringApplication.run(ShellApplication.class, fullArgs);
    }
}
