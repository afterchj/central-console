package com.example.blt.entity.dd;

/**
 * Created by nannan.li on 2019/5/23.
 */
public enum ConsoleKeys {

    lMAC("l_lmac"),
    VADDR("l_vaddr"),
    HOSTS("l_host"),
    LSIZE("l_size"),
    TSIZE("t_size"),
    LTIMES("l_times"),
    LINFO("l_info");

    ConsoleKeys(String value) {
        this.value = value;
    }

    private String value;

    public String getValue() {
        return value;
    }
}
