package com.gtmp.enums;


/**
 * 消息动作
 */
public enum NotifyActionEnum {

    REPLY("reply", 1),
    LIKE("like", 2),
    FOLLOW("follow", 3);

    private final int code;
    private final String name;

    private NotifyActionEnum(String name, int code) {
        this.name = name;
        this.code = code;
    }

    public NotifyActionEnum nameOf(String name) {
        for (NotifyActionEnum e : NotifyActionEnum.values()) {
            if (e.getName().equals(name)) {
                return e;
            }
        }

        return null;
    }

    public NotifyActionEnum codeOf(int code) {
        for (NotifyActionEnum e : NotifyActionEnum.values()) {
            if (e.getCode() == code) {
                return e;
            }
        }

        return null;
    }



    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
