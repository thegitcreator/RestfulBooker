package booking;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.jupiter.api.*;

import java.util.ArrayList;

import static io.restassured.RestAssured.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

public class BookingTest {

    // Задача: Создай бронирование, найди эту запись, обнови цену, удали, убедись бронь удалена

    private final static String URL = "https://restful-booker.herokuapp.com";
    private static Number bookingId;
    private static String authToken = "Basic YWRtaW46cGFzc3dvcmQxMjM=";


    // Создание бронирования
    @Test
    @Order(1)
    public void createBooking() {
        Specifications.installSpecification(Specifications.requestSpecification(URL), Specifications.responseSpecOk200());

        BookingDates bookingDates = new BookingDates("2018-01-01", "2019-01-01");
        BookingData booking = new BookingData("Jim", "Brown", 120, true, bookingDates, "Breakfast");

        SuccessBookingCreation successBookingCreate = given()
                .header("Authorization", authToken)
                .body(booking)
                .when()
                .post("/booking")
                .then().log().all()
                .extract().as(SuccessBookingCreation.class);

        bookingId = successBookingCreate.getBookingId().intValue();
        Assertions.assertNotNull(bookingId);

    }

    // Поиск бронирования
    @Test
    @Order(2)
    public void getBooking() {
        Assertions.assertNotNull(bookingId, "Booking ID should be available");

        Specifications.installSpecification(Specifications.requestSpecification(URL), Specifications.responseSpecOk200());

        BookingData getBooking = given()
                .header("Authorization", authToken)
                .when()
                .get("/booking/" + bookingId)
                .then().log().all()
                .extract().as(BookingData.class);
    }

    // Обновляем цену
    @Test
    @Order(3)
    public void updateBookingPrice(){
        Specifications.installSpecification(Specifications.requestSpecification(URL), Specifications.responseSpecOk200());

        String updateTotalPrice = "{ \"totalprice\": 250 }";

        given()
                .contentType("application/json")
                .header("Authorization", authToken)
                .body(updateTotalPrice)
                .when()
                .patch("/booking/" + bookingId)
                .then().log().all();

    }

    // Удалаяем запись о бронировании
    @Test
    @Order(4)
    public void deleteBooking(){
        Specifications.installSpecification(Specifications.requestSpecification(URL), Specifications.responseSpecOk201());

        given()
                .header("Authorization", authToken)
                .when()
                .delete("/booking/" + bookingId)
                .then().log().all();

    }

    // Проверка, что запись была удалена
    @Test
    @Order(5)
    public void verifyDeleteBooking(){
        Specifications.installSpecification(Specifications.requestSpecification(URL), Specifications.responseSpecError404());

        Response response = (Response) given()
                .header("Authorization", authToken)
                .when()
                .get("/booking/" + bookingId)
                .then().log().all()
                .extract().response();

        Assertions.assertEquals(404, response.getStatusCode(), "Запись была успешно удалена");
    }

}
