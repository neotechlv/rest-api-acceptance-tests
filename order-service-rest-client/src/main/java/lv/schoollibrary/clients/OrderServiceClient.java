package lv.schoollibrary.clients;


import lv.schoollibrary.orders.ApiClient;
import lv.schoollibrary.orders.api.OrdersControllerApi;
import lv.schoollibrary.orders.model.OrderInfoDto;
import lv.schoollibrary.orders.model.OrderRequestDto;
import lv.schoollibrary.tests.config.EnvironmentConfig;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.Call;
import retrofit2.Response;

@Singleton
public class OrderServiceClient {

    private ApiClient apiClient;

    @Inject
    private EnvironmentConfig config;

    @Inject
    public void init() {
        apiClient = new ApiClient();
        apiClient.getAdapterBuilder()
                .baseUrl(config.orderServiceBaseUri());
        apiClient.getOkBuilder()
                .retryOnConnectionFailure(true)
                .readTimeout(30, TimeUnit.SECONDS)
                .connectTimeout(30, TimeUnit.SECONDS);
    }

    public Response<OrderInfoDto> addNewOrder(String schoolKidId, String bookId, String takenUntil) {

        OrderRequestDto item = createNewOrderDto(schoolKidId, bookId, takenUntil);
        OrdersControllerApi controllerApi = apiClient.createService(OrdersControllerApi.class);

        Call<OrderInfoDto> call = controllerApi.addUsingPOST(item);
        try {
            return call.execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private OrderRequestDto createNewOrderDto(String schoolKidId, String bookId, String takenUntil) {
        OrderRequestDto item = new OrderRequestDto();
        item.setSchoolKidId(schoolKidId);
        item.setBookId(bookId);
        item.setTakenUntil(takenUntil);

        return item;
    }

}
