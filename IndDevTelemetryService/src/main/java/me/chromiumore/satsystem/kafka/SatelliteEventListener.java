package me.chromiumore.satsystem.kafka;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chromiumore.satsystem.kafka.inbox.InboxService;
import me.chromiumore.satsystem.kafka.inbox.InboxUtils;
import me.chromiumore.satsystem.service.SatelliteIdRepository;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Slf4j
@Component
public class SatelliteEventListener {
    private static final String TOPIC = "satellite-events";
    private final SatelliteIdRepository satelliteIdRepository;
    private final InboxService inboxService;

    @Transactional
    @KafkaListener(topics = TOPIC, groupId = "telemetry-service-group")
    public void handleSatelliteEvent(ConsumerRecord<String, SatelliteEvent> record) {
        try {
            SatelliteEvent event = record.value();
            log.info("Получено событие: type={}, satelliteId={}, name={}, offset={}",
                    event.eventType(), event.satelliteId(), event.satelliteName(), record.offset());

            if (inboxService.existsByEventId(InboxUtils.generateStableEventId(event))) {
                log.debug("Событие {} уже обработано, пропуск", event);
                return;
            }

            switch (event.eventType()) {
                case CREATED -> satelliteIdRepository.add(event.satelliteId());
                case DELETED -> satelliteIdRepository.remove(event.satelliteId());
            }

            inboxService.saveToInbox(InboxUtils.createInboxEvent(event));
        } catch (Exception e) {
            log.error("Ошибка обработки события (offset={}): {}", record.offset(), e.getMessage(), e);
        }
    }
}
