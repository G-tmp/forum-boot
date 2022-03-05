package com.gtmp.enums;


/**
 * 对象类型
 */
public enum ObjectTypeEnum {

    USER("user", 1),
    POST("post", 2),
    REPLY("reply", 3);

    private String name;
    private int code;

    private ObjectTypeEnum(String name, int code) {
        this.name = name;
        this.code = code;
    }


    public static ObjectTypeEnum nameOf(String name) {
        for (ObjectTypeEnum e : ObjectTypeEnum.values()) {
            if (e.getName().equals(name)) {
                return e;
            }
        }

        return null;
    }

    public static ObjectTypeEnum codeOf(int code) {
        for (ObjectTypeEnum e : ObjectTypeEnum.values()) {
            if (e.getCode() == code) {
                return e;
            }
        }

        return null;
    }

    public String getName() {
        return name;
    }

    public int getCode() {
        return code;
    }

}
