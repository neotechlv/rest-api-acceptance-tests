package lv.schoollibrary.tests.steps;

import lv.schoollibrary.books.model.BookResponseDto;
import lv.schoollibrary.clients.BookServiceClient;

import javax.inject.Inject;

import cucumber.api.java.en.Given;
import cucumber.runtime.java.guice.ScenarioScoped;
import lombok.extern.slf4j.Slf4j;
import retrofit2.Response;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@ScenarioScoped
public class BookServiceTestSteps {

    @Inject
    private BookServiceClient bookServiceClient;

    @Given("^book \"([^\"]*)\" with ISBN \"([^\"]*)\" exists$")
    public void havingABook(String bookName, String isbn) {
        Response<BookResponseDto> bookResponseDtoResponse = bookServiceClient.addNewBook(bookName, isbn);
        assertThat(bookResponseDtoResponse.isSuccessful()).isTrue();
    }

}
