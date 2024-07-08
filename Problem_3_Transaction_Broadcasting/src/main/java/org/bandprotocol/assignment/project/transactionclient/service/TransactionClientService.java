package org.bandprotocol.assignment.project.transactionclient.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class TransactionClientService {
    private static final String BROADCAST_URL = "https://mock-node-wgqbnxruha-as.a.run.app/broadcast";
    private static final String CHECK_URL = "https://mock-node-wgqbnxruha-as.a.run.app/check/";
    private static final int MAX_RETRIES = 10;
    private static final int RETRY_DELAY_SECONDS = 5;

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public TransactionClientService(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    // Method to broadcast a new transaction
    public String broadcastTransaction(String symbol, long price, long timestamp) throws JsonProcessingException {
        // Prepare payload for the transaction
        Map<String, Object> payload = new HashMap<>();
        payload.put("symbol", symbol);
        payload.put("price", price);
        payload.put("timestamp", timestamp);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(payload, headers);
        String response = restTemplate.postForObject(BROADCAST_URL, request, String.class);
        JsonNode root = objectMapper.readTree(response);
        return root.get("tx_hash").asText();
    }

    // Method to check the status of a transaction
    public String checkTransactionStatus(String txHash) throws Exception {
        String response = restTemplate.getForObject(CHECK_URL + txHash, String.class);
        JsonNode root = objectMapper.readTree(response);
        return root.get("tx_status").asText();
    }

    // Method to monitor transaction status with retries
    public String monitorTransactionStatus(String txHash) throws Exception {
        for (int attempt = 1; attempt <= MAX_RETRIES; attempt++) {
            try {
                String status = checkTransactionStatus(txHash);
                System.out.println("Attempt " + attempt + ": Transaction status - " + status);
                switch (status) {
                    case "CONFIRMED":
                        System.out.println("Transaction has been processed and confirmed.");
                        return status;
                    case "FAILED":
                        System.out.println("Transaction failed to process.");
                        return status;
                    case "PENDING":
                        System.out.println("Transaction is awaiting processing.");
                        TimeUnit.SECONDS.sleep(RETRY_DELAY_SECONDS);
                        break;
                    case "DNE":
                        System.out.println("Transaction does not exist.");
                        return status;
                    default:
                        throw new RuntimeException("Unknown transaction status: " + status);
                }
            } catch (Exception e) {
                throw new RuntimeException("Failed to parse transaction status response", e);
            }
        }
        return checkTransactionStatus(txHash);
    }
}
