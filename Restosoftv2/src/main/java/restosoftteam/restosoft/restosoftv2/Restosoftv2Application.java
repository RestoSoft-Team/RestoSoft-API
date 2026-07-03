package restosoftteam.restosoft.restosoftv2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableAsync
public class Restosoftv2Application {

    public static void main(String[] args) {
        SpringApplication.run(Restosoftv2Application.class, args);
    }

}
