package common.model;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class RequestRoutingParameters {
    private String httpMethod;
    private String resource;
}
