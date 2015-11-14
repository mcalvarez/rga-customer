package org.rga.customer.rest;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;
import org.rga.customer.TestUtils;
import org.rga.customer.dto.CustomerDTO;
import org.rga.customer.rest.CustomerResource;
import org.springframework.web.filter.RequestContextFilter;

public class CustomerResourceTest extends JerseyTest {
	@Override
	protected Application configure() {
		ResourceConfig rc = new ResourceConfig();
		
		rc.register(CustomerResource.class);
		
		// Enable jackson
		rc.register(JacksonFeature.class);
		
		// Enable jackson
		rc.register(RequestContextFilter.class);
		
		rc.property("contextConfigLocation",
				"classpath:spring/applicationContext.xml");
		
		return rc;
	}
	
	@Override
	protected void configureClient(ClientConfig config) {
		super.configureClient(config);
		config.property("com.sun.jersey.api.json.POJOMappingFeature",
				Boolean.TRUE).register(JacksonFeature.class);
	}
	
	@Test
	public void testListCustomersOk() {
		final WebTarget target = target("customer").path("list");
		final Response response = target.request().get();
		TestUtils.assertOkStatus(response);
		
		List<CustomerDTO> costumerList 
			= response.readEntity(new GenericType<List<CustomerDTO>>(){});
		
		assertEquals(5, costumerList.size());
	}
	
	@Test
	public void testAddCustomerOk() {
		final CustomerDTO customerDTO = new CustomerDTO(
				1, "John", "Doo", "email@emai.com");
		TestUtils.assertOkStatus(
				addCustomer(customerDTO));
	}
	
	@Test
	public void testAddCustomerEmailWrong() {
		final CustomerDTO customerDTO = new CustomerDTO(
				1, "John", "Doo", "wrongEmail");
		final WebTarget target = target("customer").path("add");
		final Response response = target.request().post(
				Entity.entity(
						customerDTO,
		                MediaType.APPLICATION_JSON_TYPE
		            ));
		final CustomerDTO customerDTORes = 
				response.readEntity(new GenericType<CustomerDTO>(){});
		assertEquals(customerDTORes.getErrors().size(), 1);
		assertEquals(
				Response.Status.FORBIDDEN.getStatusCode(), 
				response.getStatus());
	}
	
	@Test
	public void testAddCustomerNull() {
		final WebTarget target = target("customer").path("add");
		final Response response = target.request().post(
				Entity.entity(
						null,
		                MediaType.APPLICATION_JSON_TYPE
		            ));
		final CustomerDTO customerDTORes = 
				response.readEntity(new GenericType<CustomerDTO>(){});
		assertEquals(customerDTORes.getErrors().size(), 3);
		assertEquals(
				Response.Status.FORBIDDEN.getStatusCode(), 
				response.getStatus());
	}
	
	@Test
	public void testGetCustomerOk() {
		// Insert element
		final CustomerDTO customerDTO = new CustomerDTO(
				null, "John", "Doo", "email@emai.com");
		addCustomer(customerDTO);
	
		// Get the inserted element
		WebTarget target = target("customer").path("get").path("1");
		final Response response = 
				target.request().get();
		TestUtils.assertOkStatus(response);
		
		final CustomerDTO customerDTORes = 
				response.readEntity(new GenericType<CustomerDTO>(){});
		assertNotNull(customerDTORes);
	}
	
	@Test
	public void testGetCustomerNotFound() {
		final WebTarget target = target("customer").path("get").path("999");
		final Response response =
				target.request().get();
		assertEquals(
				Response.Status.FORBIDDEN.getStatusCode(), 
				response.getStatus());
	}
	
	@Test
	public void testDeleteCustomerOk() {
		// Insert element
		final CustomerDTO customerDTO = new CustomerDTO(
				null, "John", "Doo", "email@emai.com");
		addCustomer(customerDTO);
		
		WebTarget target = target("customer").path("remove").path("1");
		final Response response = 
				target.request().get();
		TestUtils.assertOkStatus(response);
	}

	@Test
	public void testDeleteCustomerNotFound() {
		WebTarget target = target("customer").path("remove").path("999");
		final Response response = 
				target.request().get();
		assertEquals(
				Response.Status.FORBIDDEN.getStatusCode(), 
				response.getStatus());
	}
	
	private Response addCustomer(CustomerDTO customerDTO) {
		final WebTarget target = target("customer").path("add");
		return target.request().post(
				Entity.entity(
						customerDTO,
		                MediaType.APPLICATION_JSON_TYPE
		            ));
	}
}
