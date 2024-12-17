package com.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.app.mysql.entities.User;
import com.app.mysql.repo.UserRepo;
import com.app.postgres.entities.Product;
import com.app.postgres.repo.ProductRepo;

@SpringBootApplication
public class MultipleDatasourceApplication implements CommandLineRunner {

    @Autowired
    UserRepo userRepo;
    
    @Autowired
    ProductRepo productRepo;
    
	public static void main(String[] args) {
		SpringApplication.run(MultipleDatasourceApplication.class, args);
		
		
	}

    @Override
    public void run(String... args) throws Exception {
        User user = new User();
        user.setEmail("pramod@gmail.com");
        user.setFirstName("PRamod");
        user.setLastName("Mishra");
        userRepo.save(user);
        
        Product product = new Product();
        product.setName("Toy");
        product.setDescription("This is toy");
        product.setPrice(100);
        productRepo.save(product);
        
    }

}
