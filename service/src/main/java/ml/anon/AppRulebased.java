package ml.anon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

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
