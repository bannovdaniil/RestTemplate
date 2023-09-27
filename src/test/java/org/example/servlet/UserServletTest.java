package org.example.servlet;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.exception.NotFoundException;
import org.example.service.UserService;
import org.example.service.impl.UserServiceImpl;
import org.example.servlet.dto.UserIncomingDto;
import org.example.servlet.dto.UserUpdateDto;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.lang.reflect.Field;

@ExtendWith(
        MockitoExtension.class
)
class UserServletTest {
    private static UserService mockUserService;
    @InjectMocks
    private static UserServlet userServlet;
    private static UserServiceImpl oldInstance;
    @Mock
    private HttpServletRequest mockRequest;
    @Mock
    private HttpServletResponse mockResponse;
    @Mock
    private BufferedReader mockBufferedReader;

    private static void setMock(UserService mock) {
        try {
            Field instance = UserServiceImpl.class.getDeclaredField("instance");
            instance.setAccessible(true);
            oldInstance = (UserServiceImpl) instance.get(instance);
            instance.set(instance, mock);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @BeforeAll
    static void beforeAll() {
        mockUserService = Mockito.mock(UserService.class);
        setMock(mockUserService);
        userServlet = new UserServlet();
    }

    @AfterAll
    static void afterAll() throws Exception {
        Field instance = UserServiceImpl.class.getDeclaredField("instance");
        instance.setAccessible(true);
        instance.set(instance, oldInstance);
    }

    @BeforeEach
    void setUp() throws IOException {
        Mockito.doReturn(new PrintWriter(Writer.nullWriter())).when(mockResponse).getWriter();
    }

    @AfterEach
    void tearDown() {
        Mockito.reset(mockUserService);
    }

    @Test
    void doGetAll() throws IOException {
        Mockito.doReturn("user/all").when(mockRequest).getPathInfo();

        userServlet.doGet(mockRequest, mockResponse);

        Mockito.verify(mockUserService).findAll();
    }

    @Test
    void doGetById() throws IOException, NotFoundException {
        Mockito.doReturn("user/2").when(mockRequest).getPathInfo();

        userServlet.doGet(mockRequest, mockResponse);

        Mockito.verify(mockUserService).findById(Mockito.anyLong());
    }

    @Test
    void doGetNotFoundException() throws IOException, NotFoundException {
        Mockito.doReturn("user/100").when(mockRequest).getPathInfo();
        Mockito.doThrow(new NotFoundException("not found.")).when(mockUserService).findById(100L);

        userServlet.doGet(mockRequest, mockResponse);

        Mockito.verify(mockResponse).setStatus(HttpServletResponse.SC_NOT_FOUND);
    }

    @Test
    void doGetBadRequest() throws IOException {
        Mockito.doReturn("user/2q").when(mockRequest).getPathInfo();

        userServlet.doGet(mockRequest, mockResponse);

        Mockito.verify(mockResponse).setStatus(HttpServletResponse.SC_BAD_REQUEST);
    }

    @Test
    void doDelete() throws IOException, NotFoundException {
        Mockito.doReturn("user/2").when(mockRequest).getPathInfo();

        userServlet.doDelete(mockRequest, mockResponse);

        Mockito.verify(mockUserService).delete(Mockito.anyLong());
        Mockito.verify(mockResponse).setStatus(HttpServletResponse.SC_NO_CONTENT);
    }

    @Test
    void doDeleteNotFound() throws IOException, NotFoundException {
        Mockito.doReturn("user/100").when(mockRequest).getPathInfo();
        Mockito.doThrow(new NotFoundException("not found.")).when(mockUserService).delete(100L);

        userServlet.doDelete(mockRequest, mockResponse);

        Mockito.verify(mockResponse).setStatus(HttpServletResponse.SC_NOT_FOUND);
        Mockito.verify(mockUserService).delete(100L);
    }

    @Test
    void doDeleteBadRequest() throws IOException {
        Mockito.doReturn("user/a100").when(mockRequest).getPathInfo();

        userServlet.doDelete(mockRequest, mockResponse);

        Mockito.verify(mockResponse).setStatus(HttpServletResponse.SC_BAD_REQUEST);
    }

    @Test
    void doPost() throws IOException {
        String expectedFirstname = "New first";
        String expectedLastname = "New last";

        Mockito.doReturn(mockBufferedReader).when(mockRequest).getReader();
        Mockito.doReturn("{\"firstName\":\"" + expectedFirstname + "\"" +
                         ",\"lastName\":\"" + expectedLastname + "\"" +
                         ", \"role\":{\"id\":4,\"name\":\"Администратор\"} " +
                         "}",
                null
        ).when(mockBufferedReader).readLine();

        userServlet.doPost(mockRequest, mockResponse);

        ArgumentCaptor<UserIncomingDto> argumentCaptor = ArgumentCaptor.forClass(UserIncomingDto.class);
        Mockito.verify(mockUserService).save(argumentCaptor.capture());

        UserIncomingDto result = argumentCaptor.getValue();
        Assertions.assertEquals(expectedFirstname, result.getFirstName());
        Assertions.assertEquals(expectedLastname, result.getLastName());
    }

    @Test
    void doPut() throws IOException, NotFoundException {
        String expectedFirstname = "New first";
        String expectedLastname = "New last";

        Mockito.doReturn(mockBufferedReader).when(mockRequest).getReader();
        Mockito.doReturn("{\"id\": 1," +
                         "\"firstName\":\"" + expectedFirstname + "\"" +
                         ",\"lastName\":\"" + expectedLastname + "\"" +
                         ", \"role\":{\"id\":4}, " +
                         "\"phoneNumberList\": [{ \"id\": 1,\"number\": \"+1(123)123 1111\"}]," +
                         "\"departmentList\": [{\"id\": 2}]" +
                         "}",
                null
        ).when(mockBufferedReader).readLine();

        userServlet.doPut(mockRequest, mockResponse);

        ArgumentCaptor<UserUpdateDto> argumentCaptor = ArgumentCaptor.forClass(UserUpdateDto.class);
        Mockito.verify(mockUserService).update(argumentCaptor.capture());

        UserUpdateDto result = argumentCaptor.getValue();
        Assertions.assertEquals(expectedFirstname, result.getFirstName());
        Assertions.assertEquals(expectedLastname, result.getLastName());
    }

    @Test
    void doPutBadRequest() throws IOException {
        Mockito.doReturn(mockBufferedReader).when(mockRequest).getReader();
        Mockito.doReturn(
                "{Bad json:1}",
                null
        ).when(mockBufferedReader).readLine();

        userServlet.doPut(mockRequest, mockResponse);

        Mockito.verify(mockResponse).setStatus(HttpServletResponse.SC_BAD_REQUEST);
    }

}