package com.fd.tryout.jms.routes;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.apache.camel.test.spring.CamelSpringBootRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@RunWith(CamelSpringBootRunner.class)
@SpringBootTest
public class JmsTestRouterTest  {

	@Autowired
	private ProducerTemplate template;

	@Autowired
	private CamelContext context;

	@Test
	public void fileMoveTest() throws InterruptedException {
		// test for file transfer...
		template.sendBodyAndHeader("file://E:/file move test", "Hello World", Exchange.FILE_NAME, "hello.txt");

		Thread.sleep(2000);

		File target = new File("E:/file move test/hello.txt");
		assertTrue("File not moved", target.exists());

		String content = context.getTypeConverter().convertTo(String.class, target);
		assertEquals("Hello World", content);

	}

	@Test
	public void fileMoveFromJMS() throws InterruptedException {

		String inputMessage = "GZXFRTJ675FTRHJJJ87zyxtGovind Real         U000000000000017.450EURATZAT\n" + 
				":20:TR234567,Zu87656z,Bhj876t\n" + 
				":32A:180123 Ship dual FERT chem\n" + 
				":36:12";
		MockEndpoint mockOut = context.getEndpoint("mock:quote", MockEndpoint.class); 

		mockOut.expectedMessageCount(1);

		template.sendBody("jms:inbound.queue", inputMessage);

		mockOut.assertIsSatisfied();

	}
}