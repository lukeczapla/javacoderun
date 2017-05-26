package frisch.java.conf;

import com.mangofactory.swagger.configuration.SpringSwaggerConfig;
import com.mangofactory.swagger.models.dto.ApiInfo;
import com.mangofactory.swagger.plugin.EnableSwagger;
import com.mangofactory.swagger.plugin.SwaggerSpringMvcPlugin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by luke on 6/4/16.
 */
@EnableSwagger
@Configuration
public class SwaggerConfig {

    @Autowired
    private SpringSwaggerConfig springSwaggerConfig;

    @Bean
    public SwaggerSpringMvcPlugin customSwaggerSpringMvcPlugin() {
        return new SwaggerSpringMvcPlugin(springSwaggerConfig)
                .apiInfo(apiInfo())
                .includePatterns(".*/conf/*.*")
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
                "CodeBot web API",
                "Java web assignment system",
                null,
                "luke.czapla@frisch.org",
                "2016",
                null
        );
    }
}
