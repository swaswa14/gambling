package ph.cdo.backend.config;

import com.github.javafaker.Faker;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ph.cdo.backend.entity.user.Client;
import ph.cdo.backend.repository.UserRepository;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

    @Bean
    public Faker faker(){
        return new Faker();
    }

//    @Bean
//    public UserRepository<?> clientUserRepository(){
//        return
//    }



}
