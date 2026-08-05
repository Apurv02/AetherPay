package com.offlinepayment.payment_relay.service;

import com.offlinepayment.payment_relay.model.RelayNode;
import com.offlinepayment.payment_relay.model.RelayPacket;
import com.offlinepayment.payment_relay.repository.RelayPacketRepository;
import com.offlinepayment.payment_relay.security.EncryptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.offlinepayment.payment_relay.model.PaymentRequest;
import com.offlinepayment.payment_relay.model.RelayHistory;
import com.offlinepayment.payment_relay.repository.RelayHistoryRepository;

import java.time.LocalDateTime;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RelayService {

    private final EncryptionService encryptionService;
    private final AccountService accountService;
    private final RelayHistoryRepository relayHistoryRepository;
    private final RelayPacketRepository relayPacketRepository;

    public void relayPacket(RelayPacket packet, List<RelayNode> nodes) throws Exception {

        relayPacketRepository.save(packet);

        System.out.println("Encrypted Data : " + packet.getEncryptedData());

        for (RelayNode node : nodes) {

            if (packet.getHopCount() >= packet.getTtl()) {

                packet.setStatus("TTL_EXCEEDED");

                relayPacketRepository.save(packet);

                System.out.println("TTL Expired!");

                return;
            }

            packet.setCurrentNode(node.getNodeName());

            RelayHistory history = new RelayHistory();

            history.setPacketId(packet.getPacketId());
            history.setNodeName(node.getNodeName());
            history.setHopNumber(packet.getHopCount() + 1);
            history.setStatus("FORWARDED");
            history.setTimestamp(LocalDateTime.now());

            relayHistoryRepository.save(history);

            packet.setHopCount(packet.getHopCount() + 1);

            System.out.println("Packet reached : " + node.getNodeName());

            if (node.isHasInternet()) {

                String decrypted = encryptionService.decrypt(packet.getEncryptedData());

                System.out.println("Decrypted Data : " + decrypted);

                String[] data = decrypted.split(",");

                String paymentId = data[0];
                Long senderId = Long.parseLong(data[1]);
                Long receiverId = Long.parseLong(data[2]);
                Double amount = Double.parseDouble(data[3]);

                PaymentRequest request = new PaymentRequest();

                request.setPaymentId(paymentId);
                request.setSenderId(senderId);
                request.setReceiverId(receiverId);
                request.setAmount(amount);

                String result = accountService.sendPayment(request);

                System.out.println(result);

                System.out.println("Payment ID : " + paymentId);
                System.out.println("Sender ID : " + senderId);
                System.out.println("Receiver ID : " + receiverId);
                System.out.println("Amount : " + amount);

                packet.setStatus("DELIVERED");

                relayPacketRepository.save(packet);

                System.out.println("Internet Found");
                System.out.println("Packet Delivered To Server");

                return;
            }

            System.out.println("Forwarding to next node...");
        }

        while (packet.getRetryCount() > 0) {

            System.out.println("Retrying... Remaining Attempts: "
                    + packet.getRetryCount());

            packet.setRetryCount(packet.getRetryCount() - 1);
        }

        packet.setStatus("FAILED");

        relayPacketRepository.save(packet);

        System.out.println("All Retry Attempts Failed");
    }
}