package codeu.controller;

import static org.junit.Assert.*;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.mockito.Mockito;
import org.junit.Test;

public class AdminServletTest {

	private AdminServlet adminServlet;
	private HttpServletRequest mockRequest;
	private HttpServletResponse mockResponse;
	private RequestDispatcher mockRequestDispatcher;
	
	@Before
	public void setup() {
		adminServlet = new AdminServlet();
		mockRequest = Mockito.mock(HttpServletRequest.class);
	    mockResponse = Mockito.mock(HttpServletResponse.class);
	    mockRequestDispatcher = Mockito.mock(RequestDispatcher.class);
	    Mockito.when(mockRequest.getRequestDispatcher("/WEB-INF/view/adminpage.jsp"))
	        .thenReturn(mockRequestDispatcher);
	}
	
	@Test
	public void testDoGet() throws IOException, ServletException {
	    adminServlet.doGet(mockRequest, mockResponse);
	    Mockito.verify(mockRequestDispatcher).forward(mockRequest, mockResponse);
	  }

}
