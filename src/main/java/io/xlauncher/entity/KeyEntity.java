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
}