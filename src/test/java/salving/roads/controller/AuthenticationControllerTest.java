package salving.roads.controller;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import salving.roads.domain.AuthenticationToken;
import salving.roads.domain.User;
import salving.roads.repository.AuthenticationTokenRepository;
import salving.roads.repository.UserRepository;
import salving.roads.service.AuthenticationTokenService;
import salving.roads.utils.TimeUtils;

import static org.hamcrest.CoreMatchers.containsString;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(AuthenticationController.class)
public class AuthenticationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private AuthenticationTokenRepository tokenRepository;

    @MockBean
    private AuthenticationTokenService authTokenService;

    private static final String login = "user",
                    password = "54321",
                    email = "TestMail@Roads.com";

    private static final String authString = "P1whr784kIur82";

    private static User user;
    private static AuthenticationToken token;

    private void setUpUser(boolean isUserExists) {
        when(userRepository.existsUserByLogin(login)).thenReturn(isUserExists);
        when(userRepository.existsUserByEmail(email)).thenReturn(isUserExists);
        when(userRepository.findUserByLogin(login)).thenReturn(user);
        when(userRepository.findUserByEmail(email)).thenReturn(user);
    }

    private void setUpAuthentication(boolean isTokenExists) {
        when(tokenRepository.existsAuthenticationTokenByAuthenticationString(authString)).thenReturn(isTokenExists);
        when(tokenRepository.existsAuthenticationTokenByUser(user)).thenReturn(isTokenExists);
        when(tokenRepository.findAuthenticationTokenByAuthenticationString(authString)).thenReturn(token);
        when(tokenRepository.findAuthenticationTokenByUser(user)).thenReturn(token);

        when(authTokenService.getNewAuthenticationToken(user)).thenReturn(token);
    }

    @BeforeClass
    public static void setUpClass() {
        user = new User(login, password, email);
        token = new AuthenticationToken(user, authString, TimeUtils.nextMonth());
    }

    @After
    public void tearDown() {
        reset(userRepository);
        reset(tokenRepository);
        reset(authTokenService);
    }

    @Test
    public void getUserReturnsValidData() throws Exception {
        setUpUser(true);

        this.mockMvc.perform(get("/user/get/" + login).param("login", login))
                .andDo(print()).andExpect(status().isOk()).andExpect(content().string(containsString(login)))
                .andExpect(content().string(containsString(email)));
    }

    @Test
    public void authenticateReturnsToken() throws Exception {
        setUpUser(true);
        setUpAuthentication(false);

        this.mockMvc.perform(post("/user/auth").param("login", login)
                .param("password", password)).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString(authString)));
    }
}