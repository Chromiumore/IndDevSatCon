package me.chromiumore;

import me.chromiumore.repositories.ConstellationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
@DisplayName("Тест выполнения репозитория")
class ConstellationRepositoryUnitTest {
    private static final String CONSTELLATION_1 = "test1";
    private static final String CONSTELLATION_2 = "test2";

    private SatelliteConstellation constellation1;
    private SatelliteConstellation constellation2;

    private Map<String, SatelliteConstellation> constellations;

    @Mock
    private ConstellationRepository repository;

    @BeforeEach
    void setup() {
        constellation1 = new SatelliteConstellation(CONSTELLATION_1);
        constellation2 = new SatelliteConstellation(CONSTELLATION_2);

        constellations = Map.of(
                CONSTELLATION_1, constellation1,
                CONSTELLATION_2, constellation2
        );
    }

    @Test
    @DisplayName("Добавление нескольких группировок")
    void testAddConstellations() {
        when(repository.contains(CONSTELLATION_1)).thenReturn(true);
        when(repository.contains(CONSTELLATION_2)).thenReturn(true);

        when(repository.getAll()).thenReturn(constellations);

        assertTrue(repository.contains(CONSTELLATION_1));
        assertTrue(repository.contains(CONSTELLATION_2));
        assertEquals(2, repository.getAll().size());
    }

    @Test
    @DisplayName("Получение группировки")
    void testGetExistingConstellation() {
        when(repository.get(CONSTELLATION_1)).thenReturn(constellations.get(CONSTELLATION_1));

        assertEquals(constellation1, repository.get(CONSTELLATION_1));
    }

    @Test
    @DisplayName("Проверка наличия группировки")
    void testContains() {
        when(repository.contains(CONSTELLATION_1)).thenReturn(true);
        when(repository.contains("123")).thenReturn(false);

        assertTrue(repository.contains(CONSTELLATION_1));
        assertFalse(repository.contains("123"));
    }
}
