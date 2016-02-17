package ue.node.utils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;

import ue.ia.parse.ParseJson.Node;
import ue.ia.parse.ParseJson.Relation;
import ue.neo4j.utils.CypherRunner;
import ue.neo4j.utils.IsExistHandler;
import ue.neo4j.utils.JdbcUtils;

public class NodeUtils {

    private static CypherRunner runner = new CypherRunner(JdbcUtils.getDataSource());

    /**
     * 判断节点是否存在
     * 
     * @author 刘超
     * @time 2016年2月1日 下午2:39:32
     * @email liuchao@useease.com
     * @version 0.1
     * @param node
     * @return
     */
    public static Boolean exist(Node node) {
        String nodIdentify = node.getName();
        String nodeName = node.getProperty("name");
        String cypher = "match (node:" + nodIdentify + ") where node.name = ? return node";

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

        String nodeIdentify = node.getName();
        HashMap<String, String> properties = node.getProperties();

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

        // {key1:"value1", key2:"value2", key3:"value3"}

        String cypher = "create (node: " + nodeIdentify + " " + pros + ")";

        System.out.println(cypher);

        runner.update(cypher, params);
    }

    /**
     * 创建两个节点之间的关系
     * 
     * @author 刘超
     * @time 2016年2月2日 上午9:38:58
     * @email liuchao@useease.com
     * @version 0.1
     * @param node
     */
    public static void createRelation(Node node) {
        // MATCH (a:Person {name: {value1}}) MERGE (a)-[r:KNOWS]->(b:Person
        // {name: {value3}})
        // MATCH (a:Person {name: {value1}}), (b:Person {name: {value2}}) MERGE
        // (a)-[r:LOVES]->(b)
        // MATCH (a:Person) where a.name = "" MERGE (a)-[r:KNOWS]->(b:Person
        // {name:""})
        String cypher = "";
        HashMap<String, Relation> relations = node.getRelations();
        Set<Entry<String, Relation>> entrySet = relations.entrySet();
        Iterator<Entry<String, Relation>> it = entrySet.iterator();

        while (it.hasNext()) {
            Entry<String, Relation> entry = it.next();
            // String relIdentify = entry.getKey();
            // //write...write....Person.name=Article.author
            Relation rel = entry.getValue();
            cypher = "MATCH (a:" + node.getName() + " {" + rel.getHeadNodePro() + ": ?}), (b:" + rel.getTailNodeType() + " {" + rel.getTailNodePro() + ": ?}) CREATE (a)-[r:"
                    + rel.getName() + "]->(b)";
            runner.update(cypher, node.getProperty(rel.getHeadNodePro()), node.getProperty(rel.getHeadNodePro()));
        }
    }

    /**
     * 更新节点
     * 
     * @author 刘超
     * @time 2016年2月2日 上午10:42:30
     * @email liuchao@useease.com
     * @version 0.1
     * @param node
     * @param condition
     */
    public static void updateNode(Node node, String condition) {

        // MATCH (n:`Author` { name: 'liuchao' }) SET n.address = "Andres",
        // n.province = "Developer";
        StringBuffer sb = new StringBuffer();
        sb.append("MATCH (n:" + node.getName() + " ) where " + condition + " SET ");

        HashMap<String, String> pros = node.getProperties();
        Object[] params = new Object[pros.size()];
        int index = 0;
        Set<Entry<String, String>> entrySet = pros.entrySet();
        Iterator<Entry<String, String>> iterator = entrySet.iterator();

        while (iterator.hasNext()) {
            Entry<String, String> entry = iterator.next();
            String key = entry.getKey();
            sb.append("n." + key + "= ?,");

            String value = entry.getValue();
            params[index] = value;
            index = index + 1;
        }

        String cypher = sb.toString().substring(0, sb.toString().length() - 1);

        System.out.println(cypher);

        runner.update(cypher, params);
    }

    // 节点查询
    public static void queryNode() {

    }
}
