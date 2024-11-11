package sky.pro.bankstar.configuration;

import org.springframework.boot.info.BuildProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Configuration
public class ConfigurationBuildProperties {

    @Bean
    public BuildProperties buildProperties() {
        return new BuildProperties(new Properties());
    }

}
