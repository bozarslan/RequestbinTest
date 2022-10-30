package dto;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * DTO representing a Delivery sent as the content of a webhook
 * <p>
 *
 * @since 2022-10-30
 */
public class Delivery {
    private String sender;
    private String receiver;
    private String address;

    public Delivery(String sender, String receiver, String address) {
        this.sender = sender;
        this.receiver = receiver;
        this.address = address;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String convertToJsonString()
    {
        ObjectMapper mapper = new ObjectMapper();
        String jsonString;
        try {
            jsonString= mapper.writeValueAsString(this);
        } catch (IOException e) {
            throw new RuntimeException("Object cannot be serialized", e);
        }
        return jsonString;
    }
}
