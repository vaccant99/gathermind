package woongjin.gatherMind;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@OpenAPIDefinition(
		info = @Info(title = "gatherMind API", version = "0.1", description = "gatherMind API documentation")
)
@SpringBootApplication
@EnableJpaAuditing
public class GatherMindApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatherMindApplication.class, args);
    }

}