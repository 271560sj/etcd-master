package io.xlauncher.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RegistryEntity {

    /**
     * master服务的IP地址
     */
    @JsonProperty("serviceIP")
    private String serviceIP;

    /**
     * master服务的port
     */
    @JsonProperty("servicePort")
    private String servicePort;

    /**
     * master服务的名称
     */
    @JsonProperty("serviceName")
    private String serviceName;
}
