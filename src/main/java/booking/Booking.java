package booking;

import java.time.LocalDate;

public class Booking {

    private final String room;
    private final LocalDate arrivalDate;
    private final LocalDate departureDate;

    public boolean isOnThisRoom(Room room) {
        return room.name.equals(this.room);
    }

    public boolean isAvailableBetweenDates(LocalDate arrivalDate, LocalDate departureDate) {
        return this.arrivalDate.isAfter(departureDate) || this.departureDate.isBefore(arrivalDate);
    }

    public Booking(String room, LocalDate arrivalDate, LocalDate departureDate) {
        this.room = room;
        this.arrivalDate = arrivalDate;
        this.departureDate = departureDate;
    }
}
