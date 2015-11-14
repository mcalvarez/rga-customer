package org.rga.test.services.impl;

import java.util.List;

import org.rga.test.bean.Customer;
import org.rga.test.dao.CustomerDAO;
import org.rga.test.dao.exceptions.CustomerNotFoundException;
import org.rga.test.dao.exceptions.DAOException;
import org.rga.test.services.CustomerService;
import org.rga.test.services.exceptions.CustomerNotFoundServiceException;
import org.rga.test.services.exceptions.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value="customerService")
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private CustomerDAO customerDAO;
	
	@Override
	public int add(Customer customer) {
		
		Integer idCustomer = null;;
		if (customer.getId() != null) {
			customerDAO.modify(customer);
			idCustomer = customer.getId();
		} else {
			idCustomer = 
					customerDAO.add(customer);
		}
		return idCustomer;
	}

	@Override
	public void deleteById(Integer idCustomer) throws ServiceException {
		try {
			customerDAO.deleteById(idCustomer);
		} catch (CustomerNotFoundException ex) {
			// TODO Auto-generated catch block
			throw new CustomerNotFoundServiceException(ex);
		} catch (DAOException ex) {
			throw new ServiceException(ex);
		}
	}

	@Override
	public List<Customer> list() {
		return customerDAO.list();
	}

	@Override
	public Customer findById(Integer idCustomer) throws ServiceException {
		try {
			return customerDAO.findById(idCustomer);
		} catch (CustomerNotFoundException ex) {
			throw new CustomerNotFoundServiceException(ex);
		} catch (DAOException ex) {
			throw new ServiceException(ex);
		}
	}

}
