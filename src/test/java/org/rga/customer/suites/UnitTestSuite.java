package org.rga.customer.suites;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.rga.customer.dao.CustomerDAOTest;
import org.rga.customer.rest.CustomerResourceTest;
import org.rga.customer.services.CustomerServiceTest;

@RunWith(value=Suite.class)
@SuiteClasses({ 
	CustomerResourceTest.class,
	CustomerServiceTest.class,
	CustomerDAOTest.class
})
public class UnitTestSuite {

}