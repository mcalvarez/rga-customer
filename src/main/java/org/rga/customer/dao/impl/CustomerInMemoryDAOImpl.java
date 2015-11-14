package org.rga.customer.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.rga.customer.bean.Customer;
import org.rga.customer.dao.CustomerDAO;
import org.rga.customer.dao.exceptions.CustomerNotFoundException;
import org.rga.customer.dao.exceptions.DAOException;
import org.springframework.stereotype.Repository;

@Repository(value="customerDAO")
public class CustomerInMemoryDAOImpl implements CustomerDAO {
	private Integer currentId = 1;
	private Map<Integer, Customer> customerList;
	
	public CustomerInMemoryDAOImpl() {
		customerList = new HashMap<Integer, Customer>();
		customerList.put(1, new Customer(nextId(), "John1", "Doo1", "email1@email.com"));
		customerList.put(2, new Customer(nextId(), "John2", "Doo2", "email2@email.com"));
		customerList.put(3, new Customer(nextId(), "John3", "Doo3", "email3@email.com"));
		customerList.put(4, new Customer(nextId(), "John4", "Doo4", "email4@email.com"));
		customerList.put(5, new Customer(nextId(), "John5", "Doo5", "email5@email.com"));
	}
	
	@Override
	public int add(Customer customer) {
		if (customer.getId() == null) {
			customer.setId(nextId());
		}
		customerList.put(customer.getId(), customer);
		return customer.getId();
	}

	@Override
	public void modify(Customer customer) {
		customerList.put(customer.getId(), customer);
	}

	@Override
	public void deleteById(Integer idCustomer) throws DAOException {
		if (!customerList.containsKey(idCustomer)) {
			throw new CustomerNotFoundException();
		}
		customerList.remove(idCustomer);
	}

	@Override
	public List<Customer> list() {
		return new ArrayList<Customer>(customerList.values());
	}

	@Override
	public Customer findById(Integer idCustomer) throws DAOException {
		if (!customerList.containsKey(idCustomer)) {
			throw new CustomerNotFoundException();
		}
		return customerList.get(idCustomer);
	}
	
	public void removeAll() {
		this.customerList = new HashMap<Integer, Customer>();
	}
	
	public int nextId() {
		return currentId++;
	}

}
