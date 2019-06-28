package com.example.blt.entity.dd;

/**
 * @author hongjian.chen
 * @date 2019/6/27 9:24
 */
public enum Scenes {

    SCENES1("场景一", "7701021901"),
    SCENES2("场景二", "7701021902"),
    SCENES3("场景三", "7701021903"),
    SCENES4("场景四", "7701021904"),
    SCENES5("场景五", "7701021905"),
    SCENES6("场景六", "7701021906"),
    SCENES7("场景七", "7701021907"),
    SCENES8("场景八", "7701021908"),
    ;

    private String sname;
    private String scode;

    Scenes(String sname, String scode) {
        this.scode = scode;
        this.sname = sname;
    }

    public String getSname() {
        return sname;
    }

    public String getScode() {
        return scode;
    }

    public static void main(String[] args) {
        for (Scenes scene : Scenes.values()) {
            System.out.println(scene.getSname() + "\t" + scene.getScode());
        }
        String scene = "场景2";
        Scenes scenes = Scenes.SCENES1;
        switch (scene) {
            case "场景1":
                System.out.println(scenes.getSname() + "\t" + scenes.getScode());
                break;
            default:
                System.out.println(Scenes.SCENES2.getSname() + "\t" + Scenes.SCENES2.getScode());
        }
    }
}
