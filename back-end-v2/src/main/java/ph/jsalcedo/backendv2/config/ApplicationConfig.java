package ph.jsalcedo.backendv2.config;

import com.github.javafaker.Faker;
import org.springframework.context.annotation.Bean;

public class ApplicationConfig {
    @Bean
    public Faker faker(){
        return new Faker();
    }

}
