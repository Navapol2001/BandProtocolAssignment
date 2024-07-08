package org.bandprotocol.assignment.project.transactionclient.controller;

import org.bandprotocol.assignment.project.transactionclient.TransactionClientApplication;
import org.bandprotocol.assignment.project.transactionclient.model.TransactionRequest;
import org.bandprotocol.assignment.project.transactionclient.service.TransactionClientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class TransactionClientController {
    private static final Logger logger = LoggerFactory.getLogger(TransactionClientApplication.class);

    private final TransactionClientService transactionClientService;

    public TransactionClientController(TransactionClientService transactionClientService) {
        this.transactionClientService = transactionClientService;
    }

    // Endpoint to broadcast a new transaction
    @PostMapping("/broadcast")
    public ResponseEntity<Map<String, String>> broadcastTransaction(@RequestBody TransactionRequest request) throws Exception {
        // Call service to broadcast transaction and get transaction hash
        String txHash = transactionClientService.broadcastTransaction(request.getSymbol(), request.getPrice(), request.getTimestamp());
        Map<String, String> response = new HashMap<>();
        response.put("tx_hash", txHash);

        // Get the HTTP status code from ResponseEntity
        ResponseEntity<Map<String, String>> responseEntity = ResponseEntity.ok(response);
        HttpStatus httpStatus = (HttpStatus) responseEntity.getStatusCode();

        if (httpStatus.equals(HttpStatus.OK)) {
            logger.info("Transaction broadcasted. Hash: {}", txHash);
        }

        return ResponseEntity.ok(response);
    }

    // Endpoint to check the status of a transaction
    @GetMapping("/check/{txHash}")
    public ResponseEntity<Map<String, String>> checkTransactionStatus(@PathVariable String txHash) throws Exception {
        String status = transactionClientService.monitorTransactionStatus(txHash);

        Map<String, String> response = new HashMap<>();
        response.put("tx_status", status);

        // Get the HTTP status code from ResponseEntity
        ResponseEntity<Map<String, String>> responseEntity = ResponseEntity.ok(response);
        HttpStatus httpStatus = (HttpStatus) responseEntity.getStatusCode();

        if (httpStatus.equals(HttpStatus.OK)) {
            logger.info("Transaction monitored. Status: {}", status);
        }

        return ResponseEntity.ok(response);
    }
}
