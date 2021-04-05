package ua.darksoul.testprojects.soulsnet;

import io.sentry.Sentry;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDateTime;

@SpringBootApplication
public class SoulsNetApplication {
    public static void main(String[] args) {
        SpringApplication.run(SoulsNetApplication.class, args);
        Sentry.captureMessage("SoulsNet started " + LocalDateTime.now());
    }
}
