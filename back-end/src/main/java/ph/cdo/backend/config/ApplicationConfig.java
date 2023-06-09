package ph.cdo.backend.config;

import com.github.javafaker.Faker;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;
import ph.cdo.backend.entity.user.Admin;
import ph.cdo.backend.entity.user.Agent;
import ph.cdo.backend.entity.user.Client;
import ph.cdo.backend.repository.AdminRepository;
import ph.cdo.backend.repository.AgentRepository;
import ph.cdo.backend.repository.ClientRepository;

import java.util.Optional;
import java.util.Properties;

@Configuration

public class ApplicationConfig {

    @Qualifier("ClientRepository")
    private final ClientRepository clientRepository;

    @Qualifier("AdminRepository")
    private final AdminRepository adminRepository;

    @Qualifier("AgentRepository")
    private final AgentRepository agentRepository;


    @Autowired
    public ApplicationConfig(ClientRepository clientRepository, AdminRepository adminRepository, AgentRepository agentRepository) {
        this.clientRepository = clientRepository;
        this.adminRepository = adminRepository;
        this.agentRepository = agentRepository;
    }

    @Bean
    public Faker faker(){
        return new Faker();
    }

//    @Bean
//    public UserRepository<?> clientUserRepository(){
//        return
//    }

    @Bean
    public UserDetailsService userDetailsService(){
        return username -> {

            Optional<Client> optionalClient = clientRepository.findAllByEmail(username).stream().findFirst();
            Optional<Admin> optionalAdmin =  adminRepository.findAllByEmail(username).stream().findFirst();
            Optional<Agent> optionalAgent = agentRepository.findAllByEmail(username).stream().findFirst();


            if(optionalClient.isPresent())
                return optionalClient.get();
            if(optionalAdmin.isPresent())
                return optionalAdmin.get();
            if(optionalAgent.isPresent())
                return optionalAgent.get();
            else
                throw new UsernameNotFoundException("User not found");
        };
    }


    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public Gson gson(){
        return new Gson();
    }


    @Bean
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);

        mailSender.setUsername("unscathedfitness@gmail.com");
        mailSender.setPassword("pxyrmlvwawujuvtf");
        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");

        return mailSender;
    }



}
