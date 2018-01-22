package io.xlauncher.entity;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 根据Key创建的Node的信息
 */
@Setter
@Getter
public class KNodeInfos {

    /**
     * Node的Key
     */
    @JsonProperty("key")
    private String key;

    /**
     * 判断Node是否是一个目录
     */
    @JsonProperty("dir")
    private boolean isDir;

    /**
     * Node的value值
     */
    @JsonProperty("value")
    private String value;

    /**
     * Node的下一级Node
     */
    @JsonProperty("nodes")
    private KNodeInfos nodeInfos;

    /**
     * Node修改的索引值
     */
    @JsonProperty("modifiedIndex")
    private int modifiedIndex;

    /**
     * Node创建的索引值
     */
    @JsonProperty("createdIndex")
    private int createdIndex;

    /**
     * Node的生存时间
     */
    @JsonProperty("ttl")
    private int ttl;
}
