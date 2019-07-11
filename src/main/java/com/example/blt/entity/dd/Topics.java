package com.example.blt.entity.dd;

/**
 * @author hongjian.chen
 * @date 2019/7/2 13:21
 */
public enum Topics {

    CONSOLE_TOPIC("save_blt_console_topic"),
    LOCAL_TOPIC("save_blt_console_local_topic"),
    CMD_TOPIC("blt_cmd_console_topic"),
    CMD_LOCAL("blt_cmd_local_topic"),
    LIGHT_TOPIC("save_light_topic");
    private String topic;

    Topics(String topic) {
        this.topic = topic;
    }

    public String getTopic() {
        return topic;
    }
}
