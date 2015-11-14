package org.rga.customer.dao;

import java.util.List;

import org.rga.customer.bean.Customer;
import org.rga.customer.dao.exceptions.DAOException;

public interface CustomerDAO {
	int add(Customer customer);
	void modify(Customer customer);
	void deleteById(Integer idCustomer) throws DAOException;
	Customer findById(Integer idCustomer) throws DAOException;
	List<Customer> list();
	void removeAll(); 
}
