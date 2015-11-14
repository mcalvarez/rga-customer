package org.rga.test.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.rga.test.bean.Customer;
import org.rga.test.dao.exceptions.CustomerNotFoundException;
import org.rga.test.dao.exceptions.DAOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations =
{ "classpath:spring/applicationContext.xml" })
public class CustomerDAOTest {
	@Autowired
	CustomerDAO customerDAO;

	@Before
	public void setUp() {
		customerDAO.removeAll();
	}
	
	@Test
	public void testAddCustomerOk() {
		Customer customer = new Customer(999,"John","Doe","email@email.com");
		
		Integer idCustomer = customerDAO.add(customer);
		
		assertEquals(idCustomer, Integer.valueOf(999));
	}
	
	@Test
	public void testFindByIdCustomerOk() throws DAOException {
		Customer customer = new Customer(999,"John","Doe","email@email.com");
		Integer idCustomer = customerDAO.add(customer);
		assertEquals(idCustomer, Integer.valueOf(999));
		
		Customer customerRes = customerDAO.findById(idCustomer);
		
		assertEquals(Integer.valueOf(999), customerRes.getId());
		assertEquals( "John", customerRes.getFirstName());
		assertEquals( "Doe", customerRes.getLastName());
		assertEquals( "email@email.com", customerRes.getEmail());
	}
	
	@Test(expected = CustomerNotFoundException.class)
	public void testFindByIdCustomerNotFound() throws DAOException {
		Integer idCustomer = 999;
		customerDAO.findById(idCustomer);
	}
	
	@Test
	public void testModifyCustomerOk() throws DAOException {
		Customer customer = new Customer(1,"John","Doe","email@email.com");
		Integer idCustomer = customerDAO.add(customer);

		customer = new Customer(1,"John1","Doe1","email1@email.com");
		customerDAO.modify(customer);
		
		Customer customerRes = customerDAO.findById(customer.getId());
		
		assertEquals( Integer.valueOf(1), customerRes.getId());
		assertEquals( "John1", customerRes.getFirstName());
		assertEquals( "Doe1", customerRes.getLastName());
		assertEquals( "email1@email.com", customerRes.getEmail());
		assertEquals(Integer.valueOf(1), idCustomer);
	}
	
	@Test(expected = CustomerNotFoundException.class)
	public void testDeleteByIdCustomerNotExist() throws DAOException {
		Integer idCustomer = 999;
		customerDAO.deleteById(idCustomer);
	}
	
	@Test
	public void testDeleteByIdOk() throws DAOException {
		customerDAO.add(new Customer(999,"John1","Doe1","email1@email.com"));
		Customer customer = customerDAO.findById(999);
		assertNotNull(customer);
		
		customerDAO.deleteById(999);
		
		Customer customerToRet = null;
		try {
			customerToRet = customerDAO.findById(999);
		} catch (DAOException ex) {}
		assertNull(customerToRet);
	}
	
	@Test
	public void testListCustomerOk() {
		customerDAO.add(new Customer(null,"John1","Doe1","email1@email.com"));
		customerDAO.add(new Customer(null,"John2","Doe2","email2@email.com"));
		customerDAO.add(new Customer(null,"John3","Doe3","email3@email.com"));
		
		List<Customer> customerList = customerDAO.list();
		
		assertEquals(3, customerList.size());
	}
	
	public void testAddMultipleCustomer() {
		customerDAO.add(new Customer(null,"John1","Doe1","email1@email.com"));
		customerDAO.add(new Customer(null,"John2","Doe2","email2@email.com"));
		customerDAO.add(new Customer(null,"John3","Doe3","email3@email.com"));
		
		List<Customer> customerList = customerDAO.list();
		Customer lastCustomer = customerList.get(2);
		
		assertEquals( Integer.valueOf(3), lastCustomer.getId());
		assertEquals( "John3", lastCustomer.getFirstName());
		assertEquals( "Doe3", lastCustomer.getLastName());
		assertEquals( "email3@email.com", lastCustomer.getEmail());
	}
	
}
