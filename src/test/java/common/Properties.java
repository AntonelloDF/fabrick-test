package common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@Configuration
@PropertySource("classpath:application-test.properties")
public class Properties {

    @Autowired
    private Environment env;

    public String getProperty(String pPropertyKey) {
        return env.getProperty(pPropertyKey);
    }
} 