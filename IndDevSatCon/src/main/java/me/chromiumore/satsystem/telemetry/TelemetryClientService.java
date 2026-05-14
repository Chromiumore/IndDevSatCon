package me.chromiumore.satsystem.telemetry;

import io.grpc.stub.StreamObserver;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chromiumore.satsystem.repository.SatelliteRepository;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;
import me.chromiumore.satsystem.Telemetry;
import me.chromiumore.satsystem.TelemetryServiceGrpc;

@Slf4j
@Service
@RequiredArgsConstructor
public class TelemetryClientService {
    @GrpcClient("telemetry-service")
    private TelemetryServiceGrpc.TelemetryServiceStub asyncStub;

    private final SatelliteRepository satelliteRepository;

    @PostConstruct
    public void startStreaming() {
        log.info("Запуск клиента телеметрии. Подключение к telemetry-service:9092");
        Telemetry.TelemetryRequest request = Telemetry.TelemetryRequest.getDefaultInstance();

        StreamObserver<Telemetry.TelemetryUpdate> responseObserver = new StreamObserver<>() {
            @Override
            public void onNext(Telemetry.TelemetryUpdate update) {
                log.info("Получена телеметрия: спутник {}, tInside={}, tOutside={}",
                        update.getSatelliteId(), update.getTemperatureInside(), update.getTemperatureOutside());
                satelliteRepository.findById(update.getSatelliteId()).ifPresent(sat -> {
                    sat.setTemperatureInside(update.getTemperatureInside());
                    sat.setTemperatureOutside(update.getTemperatureOutside());
                    satelliteRepository.save(sat);
                });
            }

            @Override
            public void onError(Throwable t) {
                log.error("Ошибка стрима телеметрии", t);
            }

            @Override
            public void onCompleted() {
                log.info("Стрим завершён");
            }
        };
    }
}
