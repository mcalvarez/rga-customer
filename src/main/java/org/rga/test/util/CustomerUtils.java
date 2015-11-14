package org.rga.test.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.validator.routines.EmailValidator;
import org.rga.test.bean.Customer;
import org.rga.test.dto.CustomerDTO;

public class CustomerUtils {
	public static Customer getCustomerBeanFromDTO(CustomerDTO customerDTO) {
		Customer customer = new Customer();
		customer.setId(customerDTO.getId());
		customer.setFirstName(customerDTO.getFirstName());
		customer.setLastName(customerDTO.getLastName());
		customer.setEmail(customerDTO.getEmail());
		return customer;
	}
	
	public static CustomerDTO getCustomerDTOFromBean(Customer customer) {
		CustomerDTO customerDTO = new CustomerDTO();
		customerDTO.setId(customer.getId());
		customerDTO.setFirstName(customer.getFirstName());
		customerDTO.setLastName(customer.getLastName());
		customerDTO.setEmail(customer.getEmail());
		return customerDTO;
	}
	
	public static List<String> validate(CustomerDTO customer) {
		List<String> errors = new ArrayList<String>();
		EmailValidator emailValidator=EmailValidator.getInstance();
		if (customer.getEmail() == null || !emailValidator.isValid(customer.getEmail())) {
			errors.add("email");
		}
		if (customer.getFirstName() == null || customer.getFirstName().isEmpty()) {
			errors.add("firtName");
		}
		if (customer.getLastName() == null || customer.getLastName().isEmpty()) {
			errors.add("lastName");
		}
		return errors;
	}
}
