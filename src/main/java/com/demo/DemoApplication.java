package com.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import com.demo.service.EventHandler;

import jakarta.annotation.PostConstruct;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.ApplicationContext;


@SpringBootApplication
@ComponentScan(basePackages = { "com.demo.service"})


public class DemoApplication {
	
	@Autowired
	private EventHandler eventHandler;
	

	public static void main(String[] args) {
//		SpringApplication.run(DemoApplication.class, args);
		
		System.out.println("=============ANANDK==============");

		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ApplicationContext context = SpringApplication.run(DemoApplication.class, args);

        System.out.println("=============ANANDK111111111111111111111==============");

        // Get EventHandler bean from Spring context
        EventHandler eventHandler = context.getBean(EventHandler.class);


		Data data=new Data();
		data.addSession("11111", "INITIAL_EVT");
		
		eventHandler.generateEvent("11111", data, "INITIAL_EVT");
		
		
	}
	

}
