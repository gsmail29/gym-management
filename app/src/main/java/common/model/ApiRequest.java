package common.model;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ApiRequest {
    private Map<String, String> requestHeadersMap;

    public ApiRequest() {
        requestHeadersMap = new HashMap<>();
    }

    public void addRequestHeader(final String key, final String value) {
        requestHeadersMap.put(key, value);
    }

    public void addRequestHeaders(final Map<String, String> requestHeadersMap) {
        this.requestHeadersMap.putAll(requestHeadersMap);
    }

    public void removeRequestHeader(final String key) {
        if(requestHeadersMap.containsKey(key)) {
            requestHeadersMap.remove(key);
        }
    }

}
