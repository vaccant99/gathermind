package woongjin.gatherMind;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class GatherMindApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatherMindApplication.class, args);
    }

}