package ue.ia.entity;

/**
 * 描述node之前的relationships
 * 
 * @author 韦誉
 * @time 2016年2月18日 下午2:30:31
 * @version 0.1
 */
public class Relation {

    /**
     * relationship的标识，相当于关系型数据库中的表名
     */
    private String label;

    /**
     * relationship前面节点的类型，也就是前面节点的label
     */
    private String headNodeType;

    /**
     * relationship后面节点的类型，也就是后面节点的label
     */
    private String tailNodeType;

    /**
     * 确定relationship前面节点唯一性的属性名
     */
    private String headNodeProName;

    /**
     * 确定relationship前面节点唯一性的属性值
     */
    private String headNodeProValue;

    /**
     * 确定relationship后面节点唯一性的属性名
     */
    private String tailNodeProName;

    /**
     * 确定relationship后面节点唯一性的属性名
     */
    private String tailNodeProValue;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getHeadNodeType() {
        return headNodeType;
    }

    public void setHeadNodeType(String headNodeType) {
        this.headNodeType = headNodeType;
    }

    public String getTailNodeType() {
        return tailNodeType;
    }

    public void setTailNodeType(String tailNodeType) {
        this.tailNodeType = tailNodeType;
    }

    public String getHeadNodeProName() {
        return headNodeProName;
    }

    public void setHeadNodeProName(String headNodeProName) {
        this.headNodeProName = headNodeProName;
    }

    public String getHeadNodeProValue() {
        return headNodeProValue;
    }

    public void setHeadNodeProValue(String headNodeProValue) {
        this.headNodeProValue = headNodeProValue;
    }

    public String getTailNodeProName() {
        return tailNodeProName;
    }

    public void setTailNodeProName(String tailNodeProName) {
        this.tailNodeProName = tailNodeProName;
    }

    public String getTailNodeProValue() {
        return tailNodeProValue;
    }

    public void setTailNodeProValue(String tailNodeProValue) {
        this.tailNodeProValue = tailNodeProValue;
    }
}
