package org.bandprotocol.assignment.project.transactionclient;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.bandprotocol.assignment.project.transactionclient.service.TransactionClientService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class TransactionClientApplication {

    private static final Logger logger = LoggerFactory.getLogger(TransactionClientApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(TransactionClientApplication.class, args);
    }

    // Bean for RestTemplate to make HTTP requests
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    // Bean for ObjectMapper to handle JSON serialization/deserialization
    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    // CommandLineRunner for demonstration purposes
    @Bean
    public CommandLineRunner run(TransactionClientService transactionClientService) {
        return args -> {
            try {
                // Broadcast a transaction
                String txHash = transactionClientService.broadcastTransaction("ETH", 4500, 1678912345);
                logger.info("Transaction broadcasted. Hash: {}", txHash);

                // Monitor the transaction status
                String status = transactionClientService.monitorTransactionStatus(txHash); // 10 attempts, 5 seconds interval
                logger.info("Transaction monitored. Status: {}", status);
            } catch (Exception e) {
                logger.error("An error occurred while broadcasting or monitoring the transaction", e);
            }
        };
    }
}