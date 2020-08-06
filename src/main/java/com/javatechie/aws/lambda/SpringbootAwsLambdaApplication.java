package com.javatechie.aws.lambda;

import com.javatechie.aws.lambda.domain.Order;
import com.javatechie.aws.lambda.respository.OrderDao;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@SpringBootApplication
@Log4j2
public class SpringbootAwsLambdaApplication {

    @Autowired
    private OrderDao orderDao;

    @Bean
    public Supplier<List<Order>> orders() {
        System.out.println("SpringbootAwsLambdaApplication orders - chandru");
        log.info("SpringbootAwsLambdaApplication orders - chandru");
        orderDao.buildOrders().stream().forEach(log::info);
        return () -> orderDao.buildOrders();
    }

    @Bean
    public Function<String, List<Order>> findOrderByName() {
        return (input) -> orderDao.buildOrders().stream().filter(order -> order.getName().equals(input)).collect(Collectors.toList());
    }


    @Bean
    public List<Order> findOrderByBook() {
        return orderDao.buildOrders().stream().filter(order -> order.getName().equals("Book")).collect(Collectors.toList());
    }

    @Bean
    public Supplier<String> secondApi() {
        System.out.println("inside SpringbootAwsLambdaApplication secondApi");
        return () -> "Hello There Chandru";
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringbootAwsLambdaApplication.class, args);
    }

}
