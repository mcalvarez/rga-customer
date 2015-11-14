package org.rga.customer.rest;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.CookieParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.hibernate.validator.internal.constraintvalidators.EmailValidator;
import org.rga.customer.bean.Customer;
import org.rga.customer.dto.CustomerDTO;
import org.rga.customer.services.CustomerService;
import org.rga.customer.services.exceptions.CustomerNotFoundServiceException;
import org.rga.customer.services.exceptions.ServiceException;
import org.rga.customer.util.CustomerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component(value = "customerResource")
@Path("/customer")
@Produces(MediaType.APPLICATION_JSON)
public class CustomerResource {

	@Autowired
	private CustomerService customerService;
	
	@GET
	@Path("/list")
	public Response list() {
		List<CustomerDTO> customers = new ArrayList<CustomerDTO>();
		
		for (Customer customer:customerService.list()) {
			customers.add(CustomerUtils.getCustomerDTOFromBean(
					customer));
		}
		
		return Response.ok(customers, MediaType.APPLICATION_JSON).build();
	}

	@POST
	@Path("/add")
	public Response add(CustomerDTO customer) {
		Response toRet = null;
		
		if (customer != null) {
			customer.setErrors(CustomerUtils.validate(customer));
			
			if (customer.getErrors().isEmpty()) {
				customerService.add(CustomerUtils.getCustomerBeanFromDTO (
						customer));
				toRet = Response.ok().build();
			} else {
				toRet = Response.status(HttpServletResponse.SC_FORBIDDEN).
						entity(customer).build();
			}
		} else {
			CustomerDTO customerEmpty = new CustomerDTO();
			customerEmpty.setErrors(CustomerUtils.validate(customerEmpty));
			toRet = Response.status(HttpServletResponse.SC_FORBIDDEN).
					entity(customerEmpty).build();
		}
			
		return toRet;
	}

	@GET
	@Path("/get/{id}")
	public Response getById(@PathParam("id") Integer id) {
		Response toRet = null;
		try {
			final Customer customer = customerService.findById(id);
			toRet = Response.ok(CustomerUtils.getCustomerDTOFromBean(
					customer), MediaType.APPLICATION_JSON).build();
		} catch (CustomerNotFoundServiceException ex) {
			toRet = Response.status(HttpServletResponse.SC_FORBIDDEN).build();
		} catch (ServiceException ex) {
			toRet = Response.serverError().entity(
					ex.getMessage()).build();
		}
		
		return toRet;
	}
	
	@GET
	@Path("/remove/{id}")
	public Response removeById(@CookieParam("token") String token,
			@PathParam("id") Integer id) {
			Response toRet = null;
			try {
				customerService.deleteById(id);
				toRet = Response.ok().build();
			} catch (CustomerNotFoundServiceException ex) {
				toRet = Response.status(HttpServletResponse.SC_FORBIDDEN).build();
			} catch (ServiceException ex) {
				toRet = Response.serverError().entity(
						ex.getMessage()).build();
			}
			return toRet;
	}
	
}
