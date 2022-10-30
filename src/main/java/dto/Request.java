package dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * DTO representing a Request sent/received from a Bin
 * <p>
 *
 * @since 2022-10-30
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Request {
    private String id;
    private JsonNode urlParams;
    private String body;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @JsonProperty("query_string")
    public JsonNode getUrlParams() {
        return urlParams;
    }

    public void setUrlParams(JsonNode urlParams) {
        this.urlParams = urlParams;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
