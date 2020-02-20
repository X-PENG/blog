package com.peng.blog.enums;
/**
* @Author:         PENG
* @CreateDate:     2020/2/10
*/
//类的对象有限且固定！
public enum BlogCopyrightSource {
    ORIGINAL("原创","orange"),TRANSLATION("转载","teal"),REPRINTED("翻译","green");
    private final String description;
    private final String color;
    BlogCopyrightSource(String description,String color) {
        this.description = description;
        this.color=color;
    }

    public String getDescription() {
        return description;
    }

    public String getColor() {
        return color;
    }

    public static String getDescriptionByOrdinal(int ordinal){
        for(BlogCopyrightSource bcrs:values()){
            if(bcrs.ordinal()==ordinal)
                return bcrs.getDescription();
        }
        return null;
    }
    public static String getColorByOrdinal(int ordinal){
        for(BlogCopyrightSource bcrs:values()){
            if(bcrs.ordinal()==ordinal)
                return bcrs.getColor();
        }
        return null;
    }
}
