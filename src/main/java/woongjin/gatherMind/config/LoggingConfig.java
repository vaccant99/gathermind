package woongjin.gatherMind.config;

import jakarta.servlet.Filter;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.AbstractRequestLoggingFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;

import java.nio.charset.StandardCharsets;


@Configuration
public class LoggingConfig {

    @Bean
    public Filter cachingFilter() {
        return new CachingFilter();
    }
}