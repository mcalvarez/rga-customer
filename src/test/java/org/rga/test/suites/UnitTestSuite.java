package org.rga.test.suites;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.rga.test.dao.CustomerDAOTest;
import org.rga.test.rest.CustomerResourceTest;
import org.rga.test.services.CustomerServiceTest;

@RunWith(value=Suite.class)
@SuiteClasses({ 
	CustomerResourceTest.class,
	CustomerServiceTest.class,
	CustomerDAOTest.class
})
public class UnitTestSuite {

}