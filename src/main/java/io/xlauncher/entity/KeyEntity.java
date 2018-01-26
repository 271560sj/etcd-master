package io.xlauncher.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 用户请求以后,返回Key的信息
 */
@Setter
@Getter
public class KeyEntity {

    /**
     * 用户请求的类型
     */
    @JsonProperty("action")
    private String aciton;

    /**
     * Node 的当前信息
     */
    @JsonProperty("node")
    private KNodeInfos node;

    /**
     * Node的上一个更新前的信息
     */
    @JsonProperty("prevNode")
    private KNodeInfos prevNode;

    /**
     * 错误代码
     */
    @JsonProperty("errorCode")
    private int errorCode;

    /**
     * 错误信息
     */
    @JsonProperty("message")
    private String message;

    /**
     * 导致错误的原因
     */
    @JsonProperty("cause")
    private String cause;

    /**
     * 返回错误的请求Index
     */
    @JsonProperty("index")
    private int index;

}