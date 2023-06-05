package ph.cdo.backend.service.unitTests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import ph.cdo.backend.dto.ClientDTO;
import ph.cdo.backend.dto.mapper.ClientDTOMapper;
import ph.cdo.backend.entity.user.Client;
import ph.cdo.backend.enums.Role;
import ph.cdo.backend.errors.DuplicateEmailException;
import ph.cdo.backend.repository.AdminRepository;
import ph.cdo.backend.repository.AgentRepository;
import ph.cdo.backend.repository.ClientRepository;
import ph.cdo.backend.request.AuthenticationRequest;
import ph.cdo.backend.request.ClientRegistrationRequest;
import ph.cdo.backend.response.AuthenticationResponse;
import ph.cdo.backend.response.ClientRegistrationResponse;
import ph.cdo.backend.service.AdminService;
import ph.cdo.backend.service.AgentService;
import ph.cdo.backend.service.ClientService;
import ph.cdo.backend.service.JwtService;
import ph.cdo.backend.service.impl.AuthenticationServiceImpl;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthenticationServiceTest {

    @Mock
    private ClientRepository clientRepository;
    @Mock
    private AdminRepository adminRepository;
    @Mock
    private AgentRepository agentRepository;
    @Mock
    private ClientService clientService;
    @Mock
    private AdminService adminService;
    @Mock
    private AgentService agentService;

    @Mock
    private ClientDTOMapper clientDTOMapper;
    @Mock
    private JwtService jwtService;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private AuthenticationManager authenticationManager;
    @InjectMocks
    private AuthenticationServiceImpl authenticationService;

    @BeforeEach
    public void setUp() {
        // Any setup logic goes here
    }

    @Test
    public void registerClient_ShouldThrowException_WhenEmailIsTaken() {

        // Arrange
        ClientRegistrationRequest request = ClientRegistrationRequest.builder()
                .email("test@example.com")
                .password("Password!234")
                .mobilePhone("123456789")
                .invitationCode("123456")
                .build();

        when(clientService.isEmailTaken(any())).thenReturn(true);

        // Act & Assert
        assertThrows(DuplicateEmailException.class, () -> authenticationService.registerClient(request));
    }

    @Test
    public void registerClient_ShouldReturnClientRegistrationResponse_WhenRegistrationIsSuccessful() {
        // Arrange
        ClientRegistrationRequest request = ClientRegistrationRequest.builder()
                .email("test@example.com")
                .password("Password!234")
                .mobilePhone("123456789")
                .invitationCode("123456")
                .build();
        Client client = new Client();
        client.setEmail(request.getEmail());
        client.setPassword(passwordEncoder.encode(request.getPassword()));
        client.setInvitationCode(request.getInvitationCode());
        client.setMobilePhone(request.getMobilePhone());
        when(clientService.isEmailTaken(any())).thenReturn(false);
        when(clientService.save(any())).thenReturn(new ClientDTO(1L, Role.Client, "test@example.com", "123456789", BigDecimal.valueOf(1000.0)));
        when(clientRepository.findById(any())).thenReturn(Optional.of(client));
        when(jwtService.generateToken(any())).thenReturn("testToken");

        // Act
        ClientRegistrationResponse response = authenticationService.registerClient(request);

        // Assert
        assertNotNull(response);
        assertEquals("Registration Successful", response.getHeader());
        assertTrue(response.getBody().contains("Please confirm your email to activate your account!"));
        assertEquals("Email will expired in 24 hours", response.getFooter());
    }

    @Test
    public void authenticate_ShouldReturnAuthenticationResponse_WhenCredentialsAreValid() throws Exception {
        // Arrange
        AuthenticationRequest request = AuthenticationRequest.builder()
                .username("test@example.com")
                .password("testPassword123")
                .build();
        Client client = new Client();
        client.setEmail(request.getUsername());
        client.setPassword(passwordEncoder.encode(request.getPassword()));
        client.setRole(Role.Client);  // Make sure you're setting the role here
        when(clientRepository.findAllByEmail(any())).thenReturn(List.of(client));

        client.setEmail(request.getUsername());
        client.setPassword(passwordEncoder.encode(request.getPassword()));
        when(authenticationManager.authenticate(any())).thenReturn(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        when(clientRepository.findAllByEmail(any())).thenReturn(List.of(client));
        when(adminRepository.findAllByEmail(any())).thenReturn(Collections.emptyList()); // Add this line
        when(agentRepository.findAllByEmail(any())).thenReturn(Collections.emptyList()); // Add this line
        when(jwtService.generateToken(any())).thenReturn("testToken");

        // Act
        AuthenticationResponse response = authenticationService.authenticate(request);

        // Assert
        assertNotNull(response);
        assertEquals("testToken", response.getToken());
        assertEquals(Role.Client, response.getRole());
    }

    @Test
    public void authenticate_ShouldThrowException_WhenCredentialsAreInvalid() {
        // Arrange
        AuthenticationRequest request = AuthenticationRequest.builder()
                .username("test@example.com")
                .password("testPassword123")
                .build();

        Client client = new Client();
        client.setEmail(request.getUsername());
        client.setPassword(passwordEncoder.encode("testPassword123!!"));
        when(authenticationManager.authenticate(any())).thenThrow(new BadCredentialsException("INVALID_CREDENTIALS"));

        // Act & Assert
        assertThrows(BadCredentialsException.class, () -> authenticationService.authenticate(request));
    }
}
