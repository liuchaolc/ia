package ue.ia.entity;

import java.util.HashMap;
import java.util.Map;

/**
 * node类，用来描述实体的
 * 
 * @author 韦誉
 * @time 2016年2月18日 下午2:19:02
 * @version 0.1
 */
public class Node {

    /**
     * node的标识(label)，相当于关系型数据中的表名
     */
    private String label;

    /**
     * node的属性
     */
    private Map<String, String> properties;

    public Node() {
        properties = new HashMap<String, String>();
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setProperties(Map<String, String> properties) {
        this.properties = properties;
    }

    public Map<String, String> getProperties() {
        return properties;
    }

    public void addProperty(String key, String value) {
        this.properties.put(key, value);
    }

    public String getProperty(String key) {
        return this.properties.get(key);
    }
}
