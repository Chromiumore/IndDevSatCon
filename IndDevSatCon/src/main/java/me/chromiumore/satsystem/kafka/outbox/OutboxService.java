package me.chromiumore.satsystem.kafka.outbox;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chromiumore.satsystem.kafka.KafkaService;
import me.chromiumore.satsystem.kafka.SatelliteEvent;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
@EnableScheduling
public class OutboxService {
    private final KafkaService kafkaService;
    private final OutboxEventRepository outboxRepository;
    private final ObjectMapper objectMapper;

    private static final String SATELLITE_EVENTS_TOPIC = "satellite-events";
    private static final int BATCH_SIZE = 50;

    public void publishToOutbox(Long satId, SatelliteEvent event) {
        try {
            String payload = objectMapper.writeValueAsString(event);

            OutboxEvent outbox = OutboxEvent.builder()
                    .aggregateId(satId)
                    .eventType(event.eventType().name())
                    .payload(payload)
                    .createdAt(LocalDateTime.now())
                    .status(OutboxEvent.OutboxStatus.PENDING)
                    .build();
            outboxRepository.save(outbox);
        } catch (Exception e) {
            log.error("Ошибка сериализации outbox-события", e);
            throw new RuntimeException("Failed to serialize outbox event", e);
        }
    }

    @Scheduled(fixedDelay = 5000)
    @Transactional
    public void publishAndPendingEvents() {
        List<OutboxEvent> pending = outboxRepository.findByStatusOrderByCreatedAtAsc(
                OutboxEvent.OutboxStatus.PENDING, PageRequest.of(0, BATCH_SIZE)
        );

        if (pending.isEmpty()) {
            return;
        }

        for (OutboxEvent event : pending) {
            try {
                SatelliteEvent satelliteEvent = objectMapper.readValue(event.getPayload(), SatelliteEvent.class);
                kafkaService.sendToKafkaSatellite(
                        SATELLITE_EVENTS_TOPIC,
                        satelliteEvent
                );

                outboxRepository.updateStatus(event.getId(), OutboxEvent.OutboxStatus.SENT);
                log.info("Outbox event {} sent to Kafka", event.getId());
            } catch (Exception e) {
                log.error("Failed to send outbox event {}: {}", event.getId(), e.getMessage());
                outboxRepository.updateStatus(event.getId(), OutboxEvent.OutboxStatus.FAILED);
            }
        }
    }
}
