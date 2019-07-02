package com.example.blt.entity.dd;

/**
 * @author hongjian.chen
 * @date 2019/7/2 13:21
 */
public enum Topics {

    LOCAL_TOPIC("blt_local_console_topic"),
    CMD_TOPIC("blt_cmd_console_topic"),
    REMOTE_TOPIC("blt_remote_console_topic");
    private String topic;

    Topics(String topic) {
        this.topic = topic;
    }

    public String getTopic() {
        return topic;
    }
}
