package ph.jsalcedo.backendv2.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ph.jsalcedo.backendv2.dto.user.details.ClientDetailsDto;
import ph.jsalcedo.backendv2.entity.user.User;
import ph.jsalcedo.backendv2.entity.user.details.ClientDetails;
import ph.jsalcedo.backendv2.exceptions.InsufficientFundsException;
import ph.jsalcedo.backendv2.exceptions.InvalidDepositAmountException;
import ph.jsalcedo.backendv2.exceptions.InvalidWithdrawalAmountException;
import ph.jsalcedo.backendv2.repository.ClientDetailsRepository;
import ph.jsalcedo.backendv2.repository.TransactionRepository;
import ph.jsalcedo.backendv2.repository.UserRepository;
import ph.jsalcedo.backendv2.request.ClientTransactionRequest;

import java.math.BigDecimal;

import static ph.jsalcedo.backendv2.utility.EntityUtilityBuilder.*;
@SpringBootTest
@ActiveProfiles("test")
public class ClientDetailsServiceTest {
    private final ClientDetailsService service;


    private final ClientDetailsRepository repository;

    private final UserRepository userRepository;

    private final TransactionRepository transactionRepository;
    @Autowired
    public ClientDetailsServiceTest(@Qualifier("ClientDetailsService")ClientDetailsService service, ClientDetailsRepository repository,
                                    UserRepository userRepository, TransactionRepository transactionRepository) {
        this.service = service;
        this.repository = repository;
        this.userRepository = userRepository;
        this.transactionRepository = transactionRepository;
    }

    @BeforeEach
    void setUp() {
        repository.deleteAll();
        userRepository.deleteAll();
    }

    @AfterEach
    void tearDown() {
        repository.deleteAll();
        userRepository.deleteAll();
    }


    @Test
    void assertNotNull(){
        Assertions.assertNotNull(service);
        Assertions.assertNotNull(repository);
        Assertions.assertNotNull(userRepository);
        Assertions.assertNotNull(transactionRepository);
    }

    @Test
    void testAddDeposit(){
        ClientTransactionRequest request = createClientTransactionRequest(1000L);


        User user = createRandomUser();

        user = userRepository.save(user);

        ClientDetails clientDetails = createRandomClient();

        ClientDetailsDto dto = service.create(clientDetails);



        dto = service.addUser(clientDetails, user);

        Assertions.assertNotNull(dto);



      //when deposit is okay
        dto = service.deposit(dto.getId(), request);

        Assertions.assertNotNull(dto);
        Assertions.assertEquals(dto.getBalance().doubleValue(), 1000L);


        //when deposit is negative
        request.setAmount(BigDecimal.valueOf(-1000L));
        final Long id = dto.getId();
        Assertions.assertThrows(InvalidDepositAmountException.class, ()->{
          service.deposit(id, request);
        });


        //when deposit is 0
        request.setAmount(BigDecimal.valueOf(0L));
        Assertions.assertThrows(InvalidDepositAmountException.class, ()->{
            service.deposit(id, request);
        });
    }


    @Test
    void withdrawal(){
        ClientTransactionRequest request = createClientTransactionRequest(1000L);

        User user = createRandomUser();

        user = userRepository.save(user);

        ClientDetails clientDetails = createRandomClient();





        ClientDetailsDto dto = service.addUser(clientDetails, user);

        Assertions.assertNotNull(dto);



        //when deposit is okay
        dto = service.deposit(dto.getId(), request);


        Assertions.assertNotNull(dto);
        Assertions.assertEquals(dto.getBalance().compareTo(request.getAmount()),0);

        //When withdrawal is okay
        request.setAmount(BigDecimal.valueOf(500L));
        dto = service.withdraw(dto.getId(), request);

        Assertions.assertEquals(dto.getBalance().compareTo(BigDecimal.valueOf(500L)),0);


        //When withdrawal is too much
        request.setAmount(BigDecimal.valueOf(50000L));
        final Long id = dto.getId();
        Assertions.assertThrows(InsufficientFundsException.class, ()->{
           service.withdraw(id, request);
        });

        request.setAmount(BigDecimal.valueOf(0L));
        //when withdrawal is 0
        Assertions.assertThrows(InvalidWithdrawalAmountException.class, ()->{
            System.out.println(request.getAmount());
           service.withdraw(id, request);
        });
    }







}
