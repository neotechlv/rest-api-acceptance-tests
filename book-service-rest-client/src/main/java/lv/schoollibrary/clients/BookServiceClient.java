package lv.schoollibrary.clients;


import lv.schoollibrary.books.ApiClient;
import lv.schoollibrary.books.api.BooksControllerApi;
import lv.schoollibrary.books.model.BookRequestDto;
import lv.schoollibrary.books.model.BookResponseDto;
import lv.schoollibrary.tests.config.EnvironmentConfig;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.Call;
import retrofit2.Response;

@Singleton
public class BookServiceClient {

    private ApiClient apiClient;

    @Inject
    private EnvironmentConfig config;

    @Inject
    public void init() {
        apiClient = new ApiClient();
        apiClient.getAdapterBuilder()
                .baseUrl(config.bookServiceBaseUri());
        apiClient.getOkBuilder()
                .retryOnConnectionFailure(true)
                .readTimeout(30, TimeUnit.SECONDS)
                .connectTimeout(30, TimeUnit.SECONDS);
    }

    public Response<BookResponseDto> addNewBook(String bookName, String isbn) {
        BookRequestDto item = createNewBook(bookName, isbn);
        BooksControllerApi booksControllerApi = apiClient.createService(BooksControllerApi.class);

        Call<BookResponseDto> call = booksControllerApi.addUsingPOST(item);
        try {
            return call.execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Response<BookResponseDto> findByIsbn(String isbn) {
        BooksControllerApi booksControllerApi = apiClient.createService(BooksControllerApi.class);
        Call<BookResponseDto> call = booksControllerApi.getBookByIsbnUsingGET(isbn);
        try {
            return call.execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Response<List<BookResponseDto>> findByName(String name) {
        BooksControllerApi booksControllerApi = apiClient.createService(BooksControllerApi.class);
        Call<List<BookResponseDto>> call = booksControllerApi.allBooksUsingGET(name);
        try {
            return call.execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private BookRequestDto createNewBook(String bookName, String isbn) {
        BookRequestDto item = new BookRequestDto();
        item.setIsbn(isbn);
        item.setName(bookName);
        return item;
    }

}
