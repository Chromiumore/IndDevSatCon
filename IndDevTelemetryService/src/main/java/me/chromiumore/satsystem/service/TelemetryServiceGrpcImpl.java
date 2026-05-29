package me.chromiumore.satsystem.service;

import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import me.chromiumore.satsystem.Telemetry;
import me.chromiumore.satsystem.TelemetryServiceGrpc;
import net.devh.boot.grpc.server.service.GrpcService;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@GrpcService
public class TelemetryServiceGrpcImpl extends TelemetryServiceGrpc.TelemetryServiceImplBase {
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private final Random random = new Random();
    private final SatelliteIdRepository satelliteIdRepository;

    @Override
    public void streamTelemetry(Telemetry.TelemetryRequest request, StreamObserver<Telemetry.TelemetryUpdate> responseObserver) {
        scheduler.scheduleAtFixedRate(() -> {
            try {
                Set<Long> activeIds = satelliteIdRepository.getAll();
                if (activeIds.isEmpty()) return;

                List<Long> idList = new ArrayList<>(activeIds);

                Long satId = idList.get(random.nextInt(idList.size()));
                Telemetry.TelemetryUpdate update = Telemetry.TelemetryUpdate.newBuilder()
                        .setSatelliteId(satId)
                        .setTemperatureInside(20.0 + random.nextDouble() * 10)
                        .setTemperatureOutside(-50.0 + random.nextDouble() * 30)
                        .setTimestamp(Instant.now().getEpochSecond())
                        .build();
                responseObserver.onNext(update);
            }  catch (Exception e) {
                responseObserver.onError(e);
            }
        }, 0, 10, TimeUnit.SECONDS);
    }
}
