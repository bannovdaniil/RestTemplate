package org.example.servlet;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.exception.NotFoundException;
import org.example.service.RoleService;
import org.example.service.impl.RoleServiceImpl;
import org.example.servlet.dto.RoleIncomingDto;
import org.example.servlet.dto.RoleUpdateDto;
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
class RoleServletTest {
    private static RoleService mockRoleService;
    @InjectMocks
    private static RoleServlet roleServlet;
    private static RoleServiceImpl oldInstance;
    @Mock
    private HttpServletRequest mockRequest;
    @Mock
    private HttpServletResponse mockResponse;
    @Mock
    private BufferedReader mockBufferedReader;

    private static void setMock(RoleService mock) {
        try {
            Field instance = RoleServiceImpl.class.getDeclaredField("instance");
            instance.setAccessible(true);
            oldInstance = (RoleServiceImpl) instance.get(instance);
            instance.set(instance, mock);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @BeforeAll
    static void beforeAll() {
        mockRoleService = Mockito.mock(RoleService.class);
        setMock(mockRoleService);
        roleServlet = new RoleServlet();
    }

    @AfterAll
    static void afterAll() throws Exception {
        Field instance = RoleServiceImpl.class.getDeclaredField("instance");
        instance.setAccessible(true);
        instance.set(instance, oldInstance);
    }

    @BeforeEach
    void setUp() throws IOException {
        Mockito.doReturn(new PrintWriter(Writer.nullWriter())).when(mockResponse).getWriter();
    }

    @AfterEach
    void tearDown() {
        Mockito.reset(mockRoleService);
    }

    @Test
    void doGetAll() throws IOException {
        Mockito.doReturn("role/all").when(mockRequest).getPathInfo();

        roleServlet.doGet(mockRequest, mockResponse);

        Mockito.verify(mockRoleService).findAll();
    }

    @Test
    void doGetById() throws IOException, NotFoundException {
        Mockito.doReturn("role/2").when(mockRequest).getPathInfo();

        roleServlet.doGet(mockRequest, mockResponse);

        Mockito.verify(mockRoleService).findById(Mockito.anyLong());
    }

    @Test
    void doGetNotFoundException() throws IOException, NotFoundException {
        Mockito.doReturn("role/100").when(mockRequest).getPathInfo();
        Mockito.doThrow(new NotFoundException("not found.")).when(mockRoleService).findById(100L);

        roleServlet.doGet(mockRequest, mockResponse);

        Mockito.verify(mockResponse).setStatus(HttpServletResponse.SC_NOT_FOUND);
    }

    @Test
    void doGetBadRequest() throws IOException {
        Mockito.doReturn("role/2q").when(mockRequest).getPathInfo();

        roleServlet.doGet(mockRequest, mockResponse);

        Mockito.verify(mockResponse).setStatus(HttpServletResponse.SC_BAD_REQUEST);
    }

    @Test
    void doDelete() throws IOException, NotFoundException {
        Mockito.doReturn("role/2").when(mockRequest).getPathInfo();
        Mockito.doReturn(true).when(mockRoleService).delete(Mockito.anyLong());

        roleServlet.doDelete(mockRequest, mockResponse);

        Mockito.verify(mockRoleService).delete(Mockito.anyLong());
        Mockito.verify(mockResponse).setStatus(HttpServletResponse.SC_NO_CONTENT);
    }

    @Test
    void doDeleteNotFound() throws IOException, NotFoundException {
        Mockito.doReturn("role/100").when(mockRequest).getPathInfo();
        Mockito.doThrow(new NotFoundException("not found.")).when(mockRoleService).delete(100L);

        roleServlet.doDelete(mockRequest, mockResponse);

        Mockito.verify(mockResponse).setStatus(HttpServletResponse.SC_NOT_FOUND);
        Mockito.verify(mockRoleService).delete(Mockito.anyLong());
    }

    @Test
    void doDeleteBadRequest() throws IOException {
        Mockito.doReturn("role/a100").when(mockRequest).getPathInfo();

        roleServlet.doDelete(mockRequest, mockResponse);

        Mockito.verify(mockResponse).setStatus(HttpServletResponse.SC_BAD_REQUEST);
    }

    @Test
    void doPost() throws IOException {
        String expectedName = "New role Admin";
        Mockito.doReturn(mockBufferedReader).when(mockRequest).getReader();
        Mockito.doReturn(
                "{\"name\":\"" + expectedName + "\"}",
                null
        ).when(mockBufferedReader).readLine();

        roleServlet.doPost(mockRequest, mockResponse);

        ArgumentCaptor<RoleIncomingDto> argumentCaptor = ArgumentCaptor.forClass(RoleIncomingDto.class);
        Mockito.verify(mockRoleService).save(argumentCaptor.capture());

        RoleIncomingDto result = argumentCaptor.getValue();
        Assertions.assertEquals(expectedName, result.getName());
    }

    @Test
    void doPostBadRequest() throws IOException {
        Mockito.doReturn(mockBufferedReader).when(mockRequest).getReader();
        Mockito.doReturn(
                "{\"id\":1}",
                null
        ).when(mockBufferedReader).readLine();

        roleServlet.doPost(mockRequest, mockResponse);

        Mockito.verify(mockResponse).setStatus(HttpServletResponse.SC_BAD_REQUEST);
    }

    @Test
    void doPut() throws IOException, NotFoundException {
        String expectedName = "Update role Admin";
        Mockito.doReturn(mockBufferedReader).when(mockRequest).getReader();
        Mockito.doReturn(
                "{\"id\": 4,\"name\": \"" +
                expectedName + "\"}",
                null
        ).when(mockBufferedReader).readLine();

        roleServlet.doPut(mockRequest, mockResponse);

        ArgumentCaptor<RoleUpdateDto> argumentCaptor = ArgumentCaptor.forClass(RoleUpdateDto.class);
        Mockito.verify(mockRoleService).update(argumentCaptor.capture());

        RoleUpdateDto result = argumentCaptor.getValue();
        Assertions.assertEquals(expectedName, result.getName());
    }

    @Test
    void doPutBadRequest() throws IOException {
        Mockito.doReturn(mockBufferedReader).when(mockRequest).getReader();
        Mockito.doReturn(
                "{Bad json:1}",
                null
        ).when(mockBufferedReader).readLine();

        roleServlet.doPut(mockRequest, mockResponse);

        Mockito.verify(mockResponse).setStatus(HttpServletResponse.SC_BAD_REQUEST);
    }

    @Test
    void doPutNotFound() throws IOException, NotFoundException {
        Mockito.doReturn(mockBufferedReader).when(mockRequest).getReader();
        Mockito.doReturn(
                "{\"id\": 4,\"name\": \"Admin\"}",
                null
        ).when(mockBufferedReader).readLine();
        Mockito.doThrow(new NotFoundException("not found.")).when(mockRoleService)
                .update(Mockito.any(RoleUpdateDto.class));

        roleServlet.doPut(mockRequest, mockResponse);

        Mockito.verify(mockResponse).setStatus(HttpServletResponse.SC_NOT_FOUND);
        Mockito.verify(mockRoleService).update(Mockito.any(RoleUpdateDto.class));
    }

}