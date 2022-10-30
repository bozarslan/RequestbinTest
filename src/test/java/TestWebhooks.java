import dto.Delivery;
import dto.Request;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.BinService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


/**
 * Contains webhook tests
 * <p>
 *
 * @since 2022-10-30
 */

public class TestWebhooks {
    private final String SENDER = "Sender";
    private final String RECEIVER = "Receiver";
    private final String ADDRESS = "Address";
    private BinService binService;
    private String binName;

    @BeforeEach
    public void setUp() {
        binService = new BinService();
        binName = binService.createBin();
    }

    @Test
    public void testReceiveWebhook() {
        Delivery delivery = new Delivery(SENDER, RECEIVER, ADDRESS);
        Response response = binService.sendWebhookToBin(binName, delivery);
        assertEquals(HttpStatus.SC_OK, response.statusCode(), "Webhook cannot be sent to the bin successfully.");

        List<Request> requests = binService.getRequests(binName);
        assertNotNull(requests, "No request is returned.");
        assertEquals(1, requests.size(), "The number of requests sent to the bin is wrong.");

        Request request = binService.getRequest(binName, requests.get(0).getId());
        assertEquals(delivery.convertToJsonString(), request.getBody(), "Request content is wrong.");
    }
}
