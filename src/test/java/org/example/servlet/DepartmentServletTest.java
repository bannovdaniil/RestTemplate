package org.example.servlet;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.exception.NotFoundException;
import org.example.service.DepartmentService;
import org.example.service.impl.DepartmentServiceImpl;
import org.example.servlet.dto.DepartmentIncomingDto;
import org.example.servlet.dto.DepartmentUpdateDto;
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
class DepartmentServletTest {
    private static DepartmentService mockDepartmentService;
    @InjectMocks
    private static DepartmentServlet departmentServlet;
    private static DepartmentServiceImpl oldInstance;
    @Mock
    private HttpServletRequest mockRequest;
    @Mock
    private HttpServletResponse mockResponse;
    @Mock
    private BufferedReader mockBufferedReader;

    private static void setMock(DepartmentService mock) {
        try {
            Field instance = DepartmentServiceImpl.class.getDeclaredField("instance");
            instance.setAccessible(true);
            oldInstance = (DepartmentServiceImpl) instance.get(instance);
            instance.set(instance, mock);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @BeforeAll
    static void beforeAll() {
        mockDepartmentService = Mockito.mock(DepartmentService.class);
        setMock(mockDepartmentService);
        departmentServlet = new DepartmentServlet();
    }

    @AfterAll
    static void afterAll() throws Exception {
        Field instance = DepartmentServiceImpl.class.getDeclaredField("instance");
        instance.setAccessible(true);
        instance.set(instance, oldInstance);
    }

    @BeforeEach
    void setUp() throws IOException {
        Mockito.doReturn(new PrintWriter(Writer.nullWriter())).when(mockResponse).getWriter();
    }

    @AfterEach
    void tearDown() {
        Mockito.reset(mockDepartmentService);
    }

    @Test
    void doGetAll() throws IOException {
        Mockito.doReturn("department/all").when(mockRequest).getPathInfo();

        departmentServlet.doGet(mockRequest, mockResponse);

        Mockito.verify(mockDepartmentService).findAll();
    }

    @Test
    void doGetById() throws IOException, NotFoundException {
        Mockito.doReturn("department/2").when(mockRequest).getPathInfo();

        departmentServlet.doGet(mockRequest, mockResponse);

        Mockito.verify(mockDepartmentService).findById(Mockito.anyLong());
    }

    @Test
    void doGetNotFoundException() throws IOException, NotFoundException {
        Mockito.doReturn("department/100").when(mockRequest).getPathInfo();
        Mockito.doThrow(new NotFoundException("not found.")).when(mockDepartmentService).findById(100L);

        departmentServlet.doGet(mockRequest, mockResponse);

        Mockito.verify(mockResponse).setStatus(HttpServletResponse.SC_NOT_FOUND);
    }

    @Test
    void doGetBadRequest() throws IOException {
        Mockito.doReturn("department/2q").when(mockRequest).getPathInfo();

        departmentServlet.doGet(mockRequest, mockResponse);

        Mockito.verify(mockResponse).setStatus(HttpServletResponse.SC_BAD_REQUEST);
    }

    @Test
    void doDelete() throws IOException, NotFoundException {
        Mockito.doReturn("department/2").when(mockRequest).getPathInfo();

        departmentServlet.doDelete(mockRequest, mockResponse);

        Mockito.verify(mockDepartmentService).delete(Mockito.anyLong());
        Mockito.verify(mockResponse).setStatus(HttpServletResponse.SC_NO_CONTENT);
    }

    @Test
    void doDeleteBadRequest() throws IOException {
        Mockito.doReturn("department/a100").when(mockRequest).getPathInfo();

        departmentServlet.doDelete(mockRequest, mockResponse);

        Mockito.verify(mockResponse).setStatus(HttpServletResponse.SC_BAD_REQUEST);
    }

    @Test
    void doPost() throws IOException {
        String expectedName = "New department";
        Mockito.doReturn(mockBufferedReader).when(mockRequest).getReader();
        Mockito.doReturn(
                "{\"name\":\"" + expectedName + "\"}",
                null
        ).when(mockBufferedReader).readLine();

        departmentServlet.doPost(mockRequest, mockResponse);

        ArgumentCaptor<DepartmentIncomingDto> argumentCaptor = ArgumentCaptor.forClass(DepartmentIncomingDto.class);
        Mockito.verify(mockDepartmentService).save(argumentCaptor.capture());

        DepartmentIncomingDto result = argumentCaptor.getValue();
        Assertions.assertEquals(expectedName, result.getName());
    }

    @Test
    void doPut() throws IOException, NotFoundException {
        String expectedName = "Update department";

        Mockito.doReturn("department/").when(mockRequest).getPathInfo();
        Mockito.doReturn(mockBufferedReader).when(mockRequest).getReader();
        Mockito.doReturn(
                "{\"id\": 4,\"name\": \"" +
                expectedName + "\"}",
                null
        ).when(mockBufferedReader).readLine();

        departmentServlet.doPut(mockRequest, mockResponse);

        ArgumentCaptor<DepartmentUpdateDto> argumentCaptor = ArgumentCaptor.forClass(DepartmentUpdateDto.class);
        Mockito.verify(mockDepartmentService).update(argumentCaptor.capture());

        DepartmentUpdateDto result = argumentCaptor.getValue();
        Assertions.assertEquals(expectedName, result.getName());
    }

    @Test
    void doPutBadRequest() throws IOException {
        Mockito.doReturn("department/").when(mockRequest).getPathInfo();
        Mockito.doReturn(mockBufferedReader).when(mockRequest).getReader();
        Mockito.doReturn(
                "{Bad json:1}",
                null
        ).when(mockBufferedReader).readLine();

        departmentServlet.doPut(mockRequest, mockResponse);

        Mockito.verify(mockResponse).setStatus(HttpServletResponse.SC_BAD_REQUEST);
    }

}