package booking;

public class BookingData {

        private String firstname;
        private String lastname;
        private Integer totalprice;
        private Boolean depositpaid;
        private BookingDates bookingdates;
        private String additionalneeds;

        // Конструктор
        public BookingData(String firstname, String lastname, Integer totalprice, Boolean depositpaid, BookingDates bookingdates, String additionalneeds) {
                this.firstname = firstname;
                this.lastname = lastname;
                this.totalprice = totalprice;
                this.depositpaid = depositpaid;
                this.bookingdates = bookingdates;
                this.additionalneeds = additionalneeds;
        }

        public String getFirstName() {
                return firstname;
        }

        public String getLastName() {
                return lastname;
        }

        public int getTotalPrice() {
                return totalprice;
        }

        public boolean getDepositPaid() {
                return depositpaid;
        }

        public BookingDates getBookingDates() {
                return bookingdates;
        }

        public String getAdditionalNeeds() {
                return additionalneeds;
        }
}

class BookingDates {
        private String checkin;
        private String checkout;

        public BookingDates(String checkin, String checkout) {
                this.checkin = checkin;
                this.checkout = checkout;
        }

        public String getCheckIn() {
                return checkin;
        }

        public String getCheckOut() {
                return checkout;
        }

}
