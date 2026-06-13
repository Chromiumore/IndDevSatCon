package me.chromiumore.satsystem.kafka.inbox;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class InboxService {
    private final InboxEventRepository inboxEventRepository;

    public void saveToInbox(InboxEvent event) {
        inboxEventRepository.save(event);
    }

    public boolean existsByEventId(UUID eventId) {
        return inboxEventRepository.existsByEventId(eventId);
    }
}
