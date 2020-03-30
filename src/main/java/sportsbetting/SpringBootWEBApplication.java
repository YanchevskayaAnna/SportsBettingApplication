package sportsbetting;

import sportsbetting.utils.DataLoader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class SpringBootWEBApplication {

    public static void main(String[] args) throws Exception {
        log.info("STARTING THE APPLICATION");
        SpringApplication.run(SpringBootWEBApplication.class, args);
        DataLoader.run();
        log.info("APPLICATION FINISHED");
    }

}
