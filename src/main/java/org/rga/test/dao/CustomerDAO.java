package org.rga.test.dao;

import java.util.List;

import org.rga.test.bean.Customer;
import org.rga.test.dao.exceptions.DAOException;

public interface CustomerDAO {
	int add(Customer customer);
	void modify(Customer customer);
	void deleteById(Integer idCustomer) throws DAOException;
	Customer findById(Integer idCustomer) throws DAOException;
	List<Customer> list();
	void removeAll(); 
}
