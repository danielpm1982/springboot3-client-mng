package com.danielpm1982.springboot3clientmng;
import com.danielpm1982.springboot3clientmng.bootstrap.Bootstrap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class Springboot3ClientMngApplication {
	@Autowired
	private static Bootstrap bootstrap;
	private static ApplicationContext applicationContext;
	public static void main(String[] args) {
		applicationContext = SpringApplication.run(Springboot3ClientMngApplication.class, args);
		init();
	}
	public static void init(){
		bootstrap = (Bootstrap)applicationContext.getBean("bootstrap");
		bootstrap.testClientRepository();
		bootstrap.loadInitialSampleClients();
	}
}
