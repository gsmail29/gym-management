package common.model;
import java.util.HashMap;
import java.util.Map;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ApiResponse {
    private Map<String, String> responseHeadersMap;
    private String errorCode;
    private int status;

    public ApiResponse() {
        responseHeadersMap = new HashMap<>();
    }

    public void addResponseHeader(final String key, final String value) {
        responseHeadersMap.put(key, value);
    }

    public void removeResponseHeader(final String key) {
        if(responseHeadersMap.containsKey(key)) {
            responseHeadersMap.remove(key);
        }
    }

    public void setStatus(final int status) {
        this.status = status;
    }

    public boolean isErrorResponse() {
        if(Integer.toString(status).charAt(0) != '2') {
            return true;
        }
        return false;
    }

}
