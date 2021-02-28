package booking;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

import static java.util.Collections.*;

public class BookingTest {


    private static Stream<Arguments> bookedRooms() {
        Room chambre1 = new Room("chambre1");
        LocalDate reservationArrivalTestDate = LocalDate.of(2021, 2, 10);
        LocalDate reservationDepartureTestDate = LocalDate.of(2021, 2, 19);
        LocalDate arrivalDate = LocalDate.of(2021, 2, 21);
        LocalDate departureDate = LocalDate.of(2021, 2, 28);
        Booking booking1 = new Booking("chambre1", arrivalDate, departureDate);
        Booking booking2 = new Booking("chambre1", reservationArrivalTestDate, reservationDepartureTestDate);
        return Stream.of(Arguments.of(singletonList(chambre1),
                singletonList(booking1),
                singletonList(chambre1)),
                Arguments.of(singletonList(chambre1),
                        singletonList(booking2),
                        emptyList()));
    }

    @ParameterizedTest
    @MethodSource({"bookedRooms"})
    void should_return_booked_rooms(Collection<Room> availableRooms, Collection<Booking> bookings, Collection<Room> roomsBooked) {
        RoomService rooms = new RoomService(availableRooms, bookings);
        LocalDate arrivalDate = LocalDate.of(2021, 2, 21);
        LocalDate departureDate = LocalDate.of(2021, 2, 28);
        Collection<Room> actualRooms = rooms.bookedRooms(arrivalDate, departureDate);
        Assertions.assertThat(actualRooms).isEqualTo(roomsBooked);
    }

    @Test
    void should_book_rooms() {
    }

    private static Stream<Arguments> freeRooms() {
        Room chambre1 = new Room("chambre1");
        Room chambre2 = new Room("chambre2");
        List<Room> allRomms = Arrays.asList(chambre1, chambre2);
        return Stream.of(
                Arguments.of(
                        allRomms,
                        singletonList(new Booking("chambre1", LocalDate.of(2021, 2, 19), LocalDate.of(2021, 2, 20))),
                        allRomms),
                Arguments.of(
                        allRomms,
                        singletonList(new Booking("chambre1", LocalDate.of(2021, 2, 19), LocalDate.of(2021, 2, 25))),
                        singletonList(chambre2)),
                Arguments.of(
                        allRomms,
                        singletonList(new Booking("chambre1", LocalDate.of(2021, 2, 19), LocalDate.of(2021, 3, 1))),
                        singletonList(chambre2)),
                Arguments.of(
                        allRomms,
                        singletonList(new Booking("chambre1", LocalDate.of(2021, 2, 22), LocalDate.of(2021, 3, 1))),
                        singletonList(chambre2)),
                Arguments.of(
                        allRomms,
                        singletonList(new Booking("chambre1", LocalDate.of(2021, 2, 22), LocalDate.of(2021, 2, 27))),
                        singletonList(chambre2)),
                Arguments.of(
                        allRomms,
                        singletonList(new Booking("chambre1", LocalDate.of(2021, 3, 1), LocalDate.of(2021, 3, 2))),
                        allRomms)
        );
    }

    @ParameterizedTest
    @MethodSource("freeRooms")
    void should_return_freerooms(Collection<Room> allrooms, Collection<Booking> bookings, Collection<Room> freeRooms) {
        RoomService rooms = new RoomService(allrooms, bookings);
        LocalDate arrivalDate = LocalDate.of(2021, 2, 21);
        LocalDate departureDate = LocalDate.of(2021, 2, 28);
        Collection<Room> actualRooms = rooms.freeRooms(arrivalDate, departureDate);
        Assertions.assertThat(actualRooms).isEqualTo(freeRooms);
    }
}
