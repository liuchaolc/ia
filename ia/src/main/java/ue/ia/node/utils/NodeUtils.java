package ue.ia.node.utils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import ue.ia.entity.Node;
import ue.ia.entity.Relation;
import ue.ia.neo4j.utils.CypherRunner;
import ue.ia.neo4j.utils.IsExistHandler;
import ue.ia.neo4j.utils.JdbcUtils;

public class NodeUtils {

    private static CypherRunner runner = new CypherRunner(JdbcUtils.getDataSource());

    /**
     * 判断节点是否存在,这里默认是用节点的name属性来确定节点的唯一性，每个节点都会有name属性
     * 
     * @author 刘超
     * @time 2016年2月1日 下午2:39:32
     * @email liuchao@useease.com
     * @version 0.1
     * @param node
     * @return
     */
    public static Boolean exist(Node node) {
        String label = node.getLabel();
        String nodeName = node.getProperty("name");
        String cypher = "match (node:" + label + ") where node.name = ? return node";

        return runner.query(cypher, new IsExistHandler(), new Object[] { nodeName });
    }

    /**
     * 创建节点
     * 
     * @author 刘超
     * @time 2016年2月1日 下午2:39:48
     * @email liuchao@useease.com
     * @version 0.1
     * @param node
     */
    public static void createNode(Node node) {

        String nodeLabel = node.getLabel();
        Map<String, String> properties = node.getProperties();

        StringBuffer sb = new StringBuffer();
        sb.append("{");
        Object[] params = new Object[properties.size()];
        int index = 0;

        Set<Entry<String, String>> entrySet = properties.entrySet();

        Iterator<Entry<String, String>> iterator = entrySet.iterator();
        while (iterator.hasNext()) {
            Entry<String, String> entry = iterator.next();
            String key = entry.getKey();
            sb.append(key + ":?,");

            String value = entry.getValue();
            params[index] = value;
            index = index + 1;
        }
        String pros = sb.toString().substring(0, sb.toString().length() - 1);
        pros = pros + "}";
        String cypher = "create (node: " + nodeLabel + " " + pros + ")";
        runner.update(cypher, params);
    }

    /**
     * 创建两个节点之间的关系,首先将两个节点查询出来，然后再建立它们之间的关系
     * 
     * @author 刘超
     * @time 2016年2月2日 上午9:38:58
     * @email liuchao@useease.com
     * @version 0.1
     * @param node
     */
    public static void createRelation(Relation relation) {
        String cypher = "MATCH (headNode:" + relation.getHeadNodeType() + " {" + relation.getHeadNodeProName() + ": ?}), (tailNode:" + relation.getTailNodeType() + " {"
                + relation.getTailNodeProName() + ": ?}) CREATE (headNode)-[rel:" + relation.getLabel() + "]->(tailNode)";
         runner.update(cypher, relation.getHeadNodeProValue(),relation.getTailNodeProValue());
    }

  
    // 节点查询
    public static void queryNode() {

    }
}
