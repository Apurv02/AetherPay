package com.offlinepayment.payment_relay.controller;

import com.offlinepayment.payment_relay.model.PaymentRequest;
import com.offlinepayment.payment_relay.model.RelayNode;
import com.offlinepayment.payment_relay.model.RelayPacket;
import com.offlinepayment.payment_relay.service.RelayService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.offlinepayment.payment_relay.security.EncryptionService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/relay")
@RequiredArgsConstructor
public class RelayController {

    private final RelayService relayService;
    private final EncryptionService encryptionService;

    @PostMapping("/send")
    public String sendOfflinePayment(@RequestBody PaymentRequest request) throws Exception {

        RelayPacket packet = new RelayPacket();

        packet.setPacketId(request.getPaymentId());

        String paymentData =
                request.getPaymentId() + "," +
                        request.getSenderId() + "," +
                        request.getReceiverId() + "," +
                        request.getAmount();

        String encrypted = encryptionService.encrypt(paymentData);

        packet.setEncryptedData(encrypted);

        packet.setHopCount(0);
        packet.setTtl(5);
        packet.setRetryCount(3);
        packet.setStatus("IN_PROGRESS");

        List<RelayNode> nodes = new ArrayList<>();

        RelayNode node1 = new RelayNode();
        node1.setNodeName("Node A");
        node1.setHasInternet(false);

        RelayNode node2 = new RelayNode();
        node2.setNodeName("Node B");
        node2.setHasInternet(false);

        RelayNode node3 = new RelayNode();
        node3.setNodeName("Node C");
        node3.setHasInternet(true);

        nodes.add(node1);
        nodes.add(node2);
        nodes.add(node3);

        relayService.relayPacket(packet, nodes);

        return "Simulation Started";
    }
}