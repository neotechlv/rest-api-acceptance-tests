package lv.schoollibrary.tests.config;

import lv.neotech.tests.configuration.TestConfiguration;

public class EnvironmentConfig extends TestConfiguration {

    private static final String BOOK_SERVICE_BASE_URI = "book-service.baseUri";

    private static final String BOOK_SERVICE_HOST = "book-service.host";

    private static final String BOOK_SERVICE_PORT = "book-service.port";

    private static final String SCHOOLKID_SERVICE_HOST = "schoolkid-service.host";

    private static final String SCHOOLKID_SERVICE_PORT = "schoolkid-service.port";

    private static final String ORDERS_SERVICE_BASE_URI = "orders-service.baseUri";

    private static final String ORDERS_SERVICE_HOST = "orders-service.host";

    private static final String ORDERS_SERVICE_PORT = "orders-service.port";


    public String bookServiceBaseUri() {
        return getString(BOOK_SERVICE_BASE_URI);
    }

    public String bookServiceHost() {
        return getString(BOOK_SERVICE_HOST);
    }

    public Integer bookServicePort() {
        return getInteger(BOOK_SERVICE_PORT, null);
    }

    public String schoolkidServiceHost() {
        return getString(SCHOOLKID_SERVICE_HOST);
    }

    public Integer schoolkidServicePort() {
        return getInteger(SCHOOLKID_SERVICE_PORT, null);
    }

    public String orderServiceBaseUri() {
        return getString(ORDERS_SERVICE_BASE_URI);
    }

    public String ordersServiceHost() {
        return getString(ORDERS_SERVICE_HOST);
    }

    public Integer ordersServicePort() {
        return getInteger(ORDERS_SERVICE_PORT, null);
    }

    public void setBookServicePort(Integer port) {
        setProperty(BOOK_SERVICE_PORT, port);
    }

    public void setSchoolKidServicePort(Integer port) {
        setProperty(SCHOOLKID_SERVICE_PORT, port);
    }

    public void setOrdersServicePort(Integer port) {
        setProperty(ORDERS_SERVICE_PORT, port);
    }

}
