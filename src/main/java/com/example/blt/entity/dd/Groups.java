package com.example.blt.entity.dd;

/**
 * @author hongjian.chen
 * @date 2019/6/27 9:24
 */
public enum Groups {

    GROUPS01("77010416013737CCCC", "77010416013232CCCC"),
    GROUPS02("77010416023737CCCC", "77010416023232CCCC"),
    GROUPS03("77010416033737CCCC", "77010416033232CCCC"),
    GROUPS04("77010416043737CCCC", "77010416043232CCCC"),
    GROUPS05("77010416053737CCCC", "77010416053232CCCC"),
    GROUPS06("77010416053737CCCC", "77010416053232CCCC"),
    GROUPS07("77010416063737CCCC", "77010416063232CCCC"),
    GROUPS08("77010416083737CCCC", "77010416083232CCCC"),
    GROUPS09("77010416093737CCCC", "77010416093232CCCC"),
    GROUPS10("770104160A3737CCCC", "770104160A3232CCCC"),
    GROUPSA("770103153737CCCC", "770103153232CCCC");

    private String on;
    private String off;

    Groups(String on, String off) {
        this.on = on;
        this.off = off;
    }

    public String getOn() {
        return on;
    }

    public String getOff() {
        return off;
    }

}
