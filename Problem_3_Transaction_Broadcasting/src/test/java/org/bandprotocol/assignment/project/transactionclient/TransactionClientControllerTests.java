package org.bandprotocol.assignment.project.transactionclient;

import org.bandprotocol.assignment.project.transactionclient.controller.TransactionClientController;
import org.bandprotocol.assignment.project.transactionclient.model.TransactionRequest;
import org.bandprotocol.assignment.project.transactionclient.service.TransactionClientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TransactionClientControllerTest {

    @Mock
    private TransactionClientService transactionClientService;

    @InjectMocks
    private TransactionClientController transactionClientController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testBroadcastTransaction() throws Exception {
        TransactionRequest request = new TransactionRequest();
        request.setSymbol("ETH");
        request.setPrice(4500);
        request.setTimestamp(1678912345);

        String expectedTxHash = "mockTxHash";
        when(transactionClientService.broadcastTransaction(anyString(), anyLong(), anyLong())).thenReturn(expectedTxHash);

        ResponseEntity<Map<String, String>> response = transactionClientController.broadcastTransaction(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(expectedTxHash, response.getBody().get("tx_hash"));

        verify(transactionClientService).broadcastTransaction("ETH", 4500, 1678912345);
    }

    @Test
    void testCheckTransactionStatus() throws Exception {
        String txHash = "mockTxHash";
        String expectedStatus = "CONFIRMED";

        when(transactionClientService.monitorTransactionStatus(txHash)).thenReturn(expectedStatus);

        ResponseEntity<Map<String, String>> response = transactionClientController.checkTransactionStatus(txHash);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(expectedStatus, response.getBody().get("tx_status"));

        verify(transactionClientService).monitorTransactionStatus(txHash);
    }
}