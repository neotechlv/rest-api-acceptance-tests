package lv.schoollibrary.tests;

import com.github.tomakehurst.wiremock.common.ClasspathFileSource;
import com.github.tomakehurst.wiremock.matching.EqualToPattern;

import javax.inject.Inject;
import javax.inject.Singleton;

import lv.neotech.tests.mocks.AbstractServiceMock;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.matchingJsonPath;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;


@Singleton
public class EklaseMock extends AbstractServiceMock {

    private static final int PORT = 8100;

    // For local mock tests, can be used during app development
    public static void main(String[] args) {
        EklaseMock mock = new EklaseMock();
        mock.init();
        mock.mockSearchSuccess("111-22-33345", "Andrey", "Volkov");
    }

    @Inject
    public void init() {
        start(PORT, new ClasspathFileSource("eklase"));
        mockDefaultSearchFailure();
    }

    public void mockSearchSuccess(String idToFind, String nameToFind, String surnameToFind) {
        mock.stubFor(
                post(urlPathEqualTo("/api/search"))
                        .withRequestBody(matchingJsonPath("$.name", new EqualToPattern(nameToFind)))
                        .withRequestBody(matchingJsonPath("$.surname", new EqualToPattern(surnameToFind)))
                        .willReturn(aResponse()
                                .withHeader("Content-Type", "application/json")
                                .withBodyFile("eklase_response_success.json")
                                .withTransformerParameter("kidId", idToFind)
                                .withTransformerParameter("kidName", nameToFind)
                                .withTransformerParameter("kidSurname", surnameToFind)
                                .withStatus(200)
                        ));
    }

    public void mockDefaultSearchFailure() {
        mock.stubFor(
                post(urlPathEqualTo("/api/search"))
                        .willReturn(aResponse()
                                .withHeader("Content-Type", "application/json")
                                .withStatus(404)
                        ));
    }
}



