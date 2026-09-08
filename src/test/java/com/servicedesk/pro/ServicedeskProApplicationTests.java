package com.servicedesk.pro;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(
		properties = {
				"spring.autoconfigure.exclude=" +
						"org.springframework.boot.jdbc.autoconfigure.DataSourceAutoConfiguration," +
						"org.springframework.boot.hibernate.autoconfigure.HibernateJpaAutoConfiguration"
		}
)
class ServicedeskProApplicationTests {

	@Test
	void contextLoads() {
	}

}