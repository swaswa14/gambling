package ph.jsalcedo.backendv2.utility;

import com.github.javafaker.Faker;
import ph.jsalcedo.backendv2.entity.Transaction;
import ph.jsalcedo.backendv2.entity.user.User;
import ph.jsalcedo.backendv2.entity.user.details.AbstractUserDetails;
import ph.jsalcedo.backendv2.entity.user.details.AdminDetails;
import ph.jsalcedo.backendv2.entity.user.details.AgentDetails;
import ph.jsalcedo.backendv2.entity.user.details.ClientDetails;
import ph.jsalcedo.backendv2.model.*;
import ph.jsalcedo.backendv2.request.ClientTransactionRequest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class EntityUtilityBuilder {
    private static final  Faker faker = new Faker();
    private static final Random ran = new Random();


    public static  User createRandomUser(){
            return User.builder()
                    .username(faker.internet().emailAddress())
                    .password(faker.internet().password())
                    .role(Role.values()[ran.nextInt(Role.values().length)])
                    .build();
    }

    public static User createRandomUser(AbstractUserDetails details){
        Role role = null;
        if(details instanceof ClientDetails)
            role = Role.Client;
        if(details instanceof AgentDetails)
            role = Role.Agent;
        if(details instanceof AdminDetails)
            role = Role.Admin;

        User user = createRandomUser();
        user.setRole(role);
        user.setUsername(details.getBasicInformation().getEmail());
        return user;
    }


    public  static BasicInformation createRandomBasicInfo(){
        return BasicInformation.builder()
                .birthday(faker.date().birthday(18, 65))
                .email(faker.internet().emailAddress())
                .phone(Phone.builder()
                        .countryCode(faker.phoneNumber().extension())
                        .number(faker.phoneNumber().cellPhone())
                        .build())
                .name(Name.builder()
                        .firstname(faker.name().firstName())
                        .lastname(faker.name().lastName())
                        .build())
                .build();
    }

    public static ClientDetails createRandomClient(){
        return ClientDetails.builder()
                .basicInformation(createRandomBasicInfo())
                .balance(BigDecimal.valueOf(0L))
                .transactions(new ArrayList<Transaction>())
                .build();
    }

    public static AgentDetails createRandomAgent(){
        return AgentDetails.builder()
                .basicInformation(createRandomBasicInfo())
                .agentCode(faker.number().digits(6))
                .build();
    }

    public static AdminDetails createRandomAdmin(){
        return AdminDetails.builder()
                .basicInformation(createRandomBasicInfo())
                .agentCode(faker.number().digits(6))
                .build();
    }

    public static ClientTransactionRequest createClientTransactionRequest(double value){
        return ClientTransactionRequest.builder().amount(BigDecimal.valueOf(value)).build();
    }

    public static Transaction createRandomTransaction(){
      return   Transaction.builder()
                .type(TransactionType.values()[ran.nextInt(TransactionType.values().length)])
                .createDate(faker.date().past(1000, TimeUnit.DAYS))
                        .amount(BigDecimal.valueOf(faker.number().randomDouble(2, 1000, 10000L)))
                                .build();


    }

    public static Transaction createRandomTransaction(int year, int month, int day){
        return   Transaction.builder()
                .type(TransactionType.values()[ran.nextInt(TransactionType.values().length)])
                .createDate(Date.from(LocalDate.of(year, month, day).atStartOfDay(ZoneId.systemDefault()).toInstant()))
                .amount(BigDecimal.valueOf(faker.number().randomDouble(2, 1000, 10000L)))
                .build();


    }
}
