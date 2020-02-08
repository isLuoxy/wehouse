package cn.l99.wehouse.pojo.baseEnum;

/**
 * 房屋朝向枚举
 *<h1>
 *     East: 东
 *     South：南
 *     West：西
 *     North：北
 *</h1>
 * @author l99
 */
public enum Orientation {

    E("东"),
    S("南"),
    W("西"),
    N("北");

    private String value;

    private Orientation(String value){
        this.value = value;
    }

    public String getValue(){
        return value;
    }
}
