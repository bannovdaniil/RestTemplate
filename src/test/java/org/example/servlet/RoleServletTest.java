package org.example.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.exception.NotFoundException;
import org.example.service.RoleService;
import org.example.service.impl.RoleServiceImpl;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.lang.reflect.Field;

@ExtendWith(
        MockitoExtension.class
)
class RoleServletTest {
    @Mock
    private HttpServletRequest mockRequest;
    @Mock
    private HttpServletResponse mockResponse;

    private static RoleService mockRoleService;
    @InjectMocks
    private static RoleServlet roleServlet;

    private static void setMock(RoleService mock) {
        try {
            Field instance = RoleServiceImpl.class.getDeclaredField("instance");
            instance.setAccessible(true);
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

    @BeforeEach
    void setUp() throws IOException {
        Mockito.doReturn(new PrintWriter(Writer.nullWriter())).when(mockResponse).getWriter();
    }

    @AfterAll
    static void afterAll() {
        Mockito.reset(mockRoleService);
    }

    @Test
    void doGetAll() throws ServletException, IOException {
        Mockito.doReturn("role/all").when(mockRequest).getPathInfo();

        roleServlet.doGet(mockRequest, mockResponse);

        Mockito.verify(mockRoleService).findAll();
    }

    @Test
    void doGetById() throws ServletException, IOException, NotFoundException {
        Mockito.doReturn("role/2").when(mockRequest).getPathInfo();

        roleServlet.doGet(mockRequest, mockResponse);

        Mockito.verify(mockRoleService).findById(Mockito.anyLong());
    }

    @Test
    void doGetNotFoundException() throws ServletException, IOException, NotFoundException {
        Mockito.doReturn("role/100").when(mockRequest).getPathInfo();
        Mockito.doThrow(new NotFoundException("not found.")).when(mockRoleService).findById(100L);

        roleServlet.doGet(mockRequest, mockResponse);

        Mockito.verify(mockResponse).setStatus(HttpServletResponse.SC_NOT_FOUND);
    }

    @Test
    void doGetBadRequest() throws ServletException, IOException, NotFoundException {
        Mockito.doReturn("role/2q").when(mockRequest).getPathInfo();

        roleServlet.doGet(mockRequest, mockResponse);

        Mockito.verify(mockResponse).setStatus(HttpServletResponse.SC_BAD_REQUEST);
    }

    @Test
    void doDelete() {
    }

    @Test
    void doPost() {
    }

    @Test
    void doPut() {
    }
}