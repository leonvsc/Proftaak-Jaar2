package nl.avans.drivio.service;

import nl.avans.drivio.model.*;
import nl.avans.drivio.repository.IHydrogenCarRepository;
import nl.avans.drivio.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@TestPropertySource(locations = "classpath:application-test.properties")
class HydrogenCarServiceTest {
    @Autowired
    private IHydrogenCarRepository repository;
    @Autowired
    private UserRepository userRepository;

    @AfterEach
    void tearDown() {
        repository.deleteAll();
    }


    @Test
    void addHydrogenCarTest() {
        // Given
        Password password1 = new Password(
                "Voorbeeld1");
        User user1 = new User(
                "Niels",
                "Warnaar",
                "Linschoten",
                12345L,
                "niels-warnaar@live.nl",
                password1);
        HydrogenCar car = new HydrogenCar(
                "Opel",
                "Corsa",
                "Fuelcar",
                2022,
                "aa180",
                "Hatchback",
                "Manual",
                user1,
                300,
                50.4,
                700,
                300);
        userRepository.save(user1);
        repository.save(car);

        // When
        boolean expected = repository.existsById(car.getCarId());

        // Then
        assertThat(expected).isTrue();
    }
}