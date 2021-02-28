package booking;

import java.time.LocalDate;
import java.util.Collection;
import java.util.stream.Collectors;

public class RoomService {
    private final Collection<Room> rooms;
    private final Collection<Booking> bookings;

    public RoomService(Collection<Room> rooms, Collection<Booking> bookings) {
        this.rooms = rooms;
        this.bookings = bookings;
    }


    public Collection<Room> bookedRooms(LocalDate arrivalDate, LocalDate departureDate) {
        return rooms.stream()
                .filter(room -> bookings.stream()
                        .anyMatch(b -> predicate(arrivalDate, departureDate, room, b)))
                .collect(Collectors.toList());
    }

    public Collection<Room> freeRooms(LocalDate arrivalDate, LocalDate departureDate) {
        return rooms.stream()
                .filter(room -> bookings.stream()
                        .noneMatch(b -> predicate(arrivalDate, departureDate, room, b)))
                .collect(Collectors.toList());
    }

    private boolean predicate(LocalDate arrivalDate, LocalDate departureDate, Room room, Booking b) {
        return b.isOnThisRoom(room) && !b.isAvailableBetweenDates(arrivalDate, departureDate);
    }


}
