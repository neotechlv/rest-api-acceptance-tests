package lv.schoollibrary.tests.steps;

import lv.schoollibrary.books.model.BookResponseDto;
import lv.schoollibrary.clients.BookServiceClient;
import lv.schoollibrary.clients.OrderServiceClient;
import lv.schoollibrary.orders.model.OrderInfoDto;

import java.util.List;

import javax.inject.Inject;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.runtime.java.guice.ScenarioScoped;
import lombok.extern.slf4j.Slf4j;
import retrofit2.Response;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@ScenarioScoped
public class OrderServiceTestSteps {

    @Inject
    private DataHolder data;

    @Inject
    private OrderServiceClient orderServiceClient;

    @Inject
    private BookServiceClient bookServiceClient;

    private Response<OrderInfoDto> addOrderResponse;


    @Given("^adding a book \"([^\"]*)\" for order for (\\w+) (\\w+) and it is taken until \"([^\"]*)\"$")
    public void addingABookForNameSurnameAndItIsTakenUntil(String bookName, String kidName, String kidSurname, String date) {
        Response<List<BookResponseDto>> bookByNameResponse = bookServiceClient.findByName(bookName);
        assertThat(bookByNameResponse.isSuccessful()).isTrue();

        List<BookResponseDto> responseDtos = bookByNameResponse.body();
        assertThat(responseDtos).isNotEmpty();

        BookResponseDto book = responseDtos.get(0);
        String kidId = data.findKidId(kidName, kidSurname);
        addOrderResponse = orderServiceClient.addNewOrder(kidId, book.getId(), date);
    }

    @Then("^order is successfully created$")
    public void orderIsSuccessfullyCreated() {
        assertThat(addOrderResponse.isSuccessful()).isTrue();
    }

}
