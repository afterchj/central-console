package com.example.blt.entity.dd;

/**
 * @author hongjian.chen
 * @date 2019/7/2 13:21
 */
public enum Topics {

    CONSOLE_TOPIC("blt_console_topic"),
    LOCAL_TOPIC("blt_console_local_topic"),
    CMD_TOPIC("blt_cmd_console_topic"),
    CMD_LOCAL("blt_cmd_local_topic"),
    LIGHT_TOPIC("blt_light_topic"),
    UPDATE_TOPIC("blt_update_light_topic"),
    HOST_TOPIC("blt_host_topic");
    private String topic;

    Topics(String topic) {
        this.topic = topic;
    }

    public String getTopic() {
        return topic;
    }
}
