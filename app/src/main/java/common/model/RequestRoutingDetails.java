package common.model;

import common.activity.Activity;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class RequestRoutingDetails {
    private Activity apiToCall;
}
