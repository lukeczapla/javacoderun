package frisch.java;

import frisch.java.conf.SwaggerConfig;
import frisch.java.conf.WebSecurityConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

import java.io.IOException;

/**
 * Created by luke on 5/31/16.
 */
@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan("frisch.java")
@Import({WebSecurityConfig.class, SwaggerConfig.class})
public class coderun {

    public static void main(String[] args) throws IOException {
        SpringApplication.run(coderun.class, args);
    }

}
