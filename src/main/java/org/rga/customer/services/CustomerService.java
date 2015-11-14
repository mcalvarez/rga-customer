package org.rga.customer.services;

import java.util.List;

import org.rga.customer.bean.Customer;
import org.rga.customer.services.exceptions.ServiceException;

public interface CustomerService {
	int add(Customer customer);
	void deleteById(Integer customer) throws ServiceException;
	List<Customer> list();
	Customer findById(Integer idCustomer) throws ServiceException;
}
