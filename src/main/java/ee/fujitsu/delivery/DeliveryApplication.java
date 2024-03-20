package ee.fujitsu.delivery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class DeliveryApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(DeliveryApplication.class, args);
		Object dataSource = context.getBean("dataSource");
		System.out.println(dataSource);
	}

}
