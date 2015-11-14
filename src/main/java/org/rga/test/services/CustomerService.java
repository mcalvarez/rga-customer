package org.rga.test.services;

import java.util.List;

import org.rga.test.bean.Customer;
import org.rga.test.services.exceptions.ServiceException;

public interface CustomerService {
	int add(Customer customer);
	void deleteById(Integer customer) throws ServiceException;
	List<Customer> list();
	Customer findById(Integer idCustomer) throws ServiceException;
}
