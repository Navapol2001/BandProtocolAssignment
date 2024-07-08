package org.bandprotocol.assignment.project.transactionclient;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.bandprotocol.assignment.project.transactionclient.service.TransactionClientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpEntity;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class TransactionClientServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private TransactionClientService transactionClientService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testBroadcastTransaction() throws Exception {
        String expectedTxHash = "mockTxHash";
        ObjectNode mockResponse = mock(ObjectNode.class);
        when(mockResponse.get("tx_hash")).thenReturn(mock(ObjectNode.class));
        when(mockResponse.get("tx_hash").asText()).thenReturn(expectedTxHash);

        when(restTemplate.postForObject(anyString(), any(HttpEntity.class), eq(String.class))).thenReturn("mockResponse");
        when(objectMapper.readTree(anyString())).thenReturn(mockResponse);

        String result = transactionClientService.broadcastTransaction("ETH", 4500, 1678912345);

        assertEquals(expectedTxHash, result);
        verify(restTemplate).postForObject(eq("https://mock-node-wgqbnxruha-as.a.run.app/broadcast"), any(HttpEntity.class), eq(String.class));
    }

    @Test
    void testCheckTransactionStatus() throws Exception {
        String txHash = "mockTxHash";
        String expectedStatus = "CONFIRMED";
        ObjectNode mockResponse = mock(ObjectNode.class);
        when(mockResponse.get("tx_status")).thenReturn(mock(ObjectNode.class));
        when(mockResponse.get("tx_status").asText()).thenReturn(expectedStatus);

        when(restTemplate.getForObject(anyString(), eq(String.class))).thenReturn("mockResponse");
        when(objectMapper.readTree(anyString())).thenReturn(mockResponse);

        String result = transactionClientService.checkTransactionStatus(txHash);

        assertEquals(expectedStatus, result);
        verify(restTemplate).getForObject(eq("https://mock-node-wgqbnxruha-as.a.run.app/check/" + txHash), eq(String.class));
    }

    @Test
    void testMonitorTransactionStatus_Confirmed() throws Exception {
        String txHash = "mockTxHash";
        when(restTemplate.getForObject(anyString(), eq(String.class))).thenReturn("mockResponse");
        ObjectNode mockResponse = mock(ObjectNode.class);
        when(mockResponse.get("tx_status")).thenReturn(mock(ObjectNode.class));
        when(mockResponse.get("tx_status").asText()).thenReturn("CONFIRMED");
        when(objectMapper.readTree(anyString())).thenReturn(mockResponse);

        String result = transactionClientService.monitorTransactionStatus(txHash);

        assertEquals("CONFIRMED", result);
        verify(restTemplate, times(1)).getForObject(anyString(), eq(String.class));
    }

    @Test
    void testMonitorTransactionStatus_Pending() throws Exception {
        String txHash = "mockTxHash";
        when(restTemplate.getForObject(anyString(), eq(String.class))).thenReturn("mockResponse");
        ObjectNode mockResponse = mock(ObjectNode.class);
        when(mockResponse.get("tx_status")).thenReturn(mock(ObjectNode.class));
        when(mockResponse.get("tx_status").asText())
                .thenReturn("PENDING")
                .thenReturn("PENDING")
                .thenReturn("CONFIRMED");
        when(objectMapper.readTree(anyString())).thenReturn(mockResponse);

        String result = transactionClientService.monitorTransactionStatus(txHash);

        assertEquals("CONFIRMED", result);
        verify(restTemplate, times(3)).getForObject(anyString(), eq(String.class));
    }
}