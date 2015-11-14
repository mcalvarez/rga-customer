package org.rga.test.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.rga.test.bean.Customer;
import org.rga.test.dao.CustomerDAO;
import org.rga.test.dao.exceptions.CustomerNotFoundException;
import org.rga.test.services.exceptions.CustomerNotFoundServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.util.ReflectionTestUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations =
{ "classpath:spring/applicationContext.xml" })
public class CustomerServiceTest {

	private CustomerDAO customerDAO;
	
	@Autowired
	private CustomerService customerService;

	@Before
	public void before() {
		customerDAO = mock(CustomerDAO.class);
		ReflectionTestUtils.setField(customerService, "customerDAO", customerDAO);
	}
	
	@Test
	public void testListCustomersOk() {
		List<Customer> all = new LinkedList<Customer>();
		all.add(new Customer(1,"John","Doe","email@email.com"));
		all.add(new Customer(2,"John2","Doe2","email2@email.com"));
		
		when(customerDAO.list()).thenReturn(all);
		
		List<Customer> customerList = customerService.list();
		assertEquals(2, customerList.size());
		
		verify(customerDAO).list();
	}
	
	@Test
	public void testListCustomersEmpty() {
		List<Customer> all = new LinkedList<Customer>();
		
		when(customerDAO.list()).thenReturn(all);
		
		List<Customer> customerList = customerService.list();
		assertEquals(0, customerList.size());
		
		verify(customerDAO).list();
	}
	
	@Test
	public void testAddCustomerOk() {
		Customer customer = 
				new Customer(null,"John","Doe","email@email.com");
		when(customerDAO.add(customer)).thenReturn(1);
		
		assertEquals(1, customerService.add(customer));
		
		verify(customerDAO).add(customer);
	}
	
	@Test
	public void testFindByIdOk() throws Exception {
		Integer idCustomer = 1;
		when(customerDAO.findById(idCustomer)).thenReturn(
				mock(Customer.class));
		
		Customer customer = customerService.findById(idCustomer);
		assertNotNull(customer);
		
		verify(customerDAO).findById(idCustomer);
	}
	
	@Test(expected = CustomerNotFoundServiceException.class)
	public void testFindByIdNotFound() throws Exception {
		Integer idCustomer = 1;
		doThrow(CustomerNotFoundException.class).
			when(customerDAO).findById(idCustomer);
		
		Customer customer = customerService.findById(idCustomer);
		assertNull(customer);
		
		verify(customerDAO).findById(idCustomer);
	}
	
	@Test(expected = CustomerNotFoundServiceException.class)
	public void testRemoveCustomerNotExist() throws Exception {
		Integer idCustomer = 1;
		doThrow(CustomerNotFoundException.class).when(customerDAO).deleteById(idCustomer);

		customerService.deleteById(idCustomer);
		
		verify(customerDAO).deleteById(idCustomer);
	}
	
	@Test
	public void testUpdateCustomerOk() {
		Customer customer = 
				new Customer(1,"John","Doe","email@email.com");
		doNothing().when(customerDAO).modify(customer);
		
		customerService.add(customer);
		
		verify(customerDAO).modify(customer);;
	}
}
