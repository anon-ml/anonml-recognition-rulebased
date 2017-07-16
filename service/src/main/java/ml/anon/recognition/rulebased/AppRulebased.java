package ml.anon.recognition.rulebased;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Created by mirco on 16.07.17.
 */
@SpringBootApplication
@EnableAutoConfiguration
@EnableScheduling
public class AppRulebased {

  public static void main(String[] args) {
    SpringApplication.run(AppRulebased.class, args);

  }
}
