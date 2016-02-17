package ue.ia.parse;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.commons.io.LineIterator;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.json.JSONObject;

public class ParseJson {

	public final static char HIDDEN_CHAR = '#';
	public static HashMap<String, Node> xml = new HashMap<>();
	public final static String ENITITY = "entity";
	public final static String RELATION = "relation";
	public final static String E_TYPE = "entity-type";

	public static class Relation {
		
		String name; 
		// HashMap<String,String> properties;
		String expression; //这玩意我好像用不着
		
		String headNodeType;  //前面节点的标志 (表名)
		String tailNodeType;  //后面节点的标志 (表名)
		
		
		String headNodePro = "name";  //前面节点的属性名
		String tailNodePro = "author";  //后面节点的属性名
		
		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getExpression() {
			return expression;
		}

		public void setExpression(String expression) {
			this.expression = expression;
		}

		@Override
		public String toString() {
			return expression;
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

		public String getHeadNodePro() {
			return headNodePro;
		}

		public void setHeadNodePro(String headNodePro) {
			this.headNodePro = headNodePro;
		}

		public String getTailNodePro() {
			return tailNodePro;
		}

		public void setTailNodePro(String tailNodePro) {
			this.tailNodePro = tailNodePro;
		}
	}

	public static class Node {
		String name;  //标识
		HashMap<String, String> properties;
		HashMap<String, Relation> relations;

		public Node() {
			relations = new HashMap<String, Relation>();
			properties = new HashMap<String, String>();
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public HashMap<String, String> getProperties() {
			return properties;
		}

		public void setProperties(HashMap<String, String> properties) {
			this.properties = properties;
		}

		public void addProperty(String key, String value) {
			this.properties.put(key, value);
		}

		public String getProperty(String key) {
			return this.properties.get(key);
		}

		public HashMap<String, Relation> getRelations() {
			return relations;
		}

		public void setRelations(HashMap<String, Relation> relations) {
			this.relations = relations;
		}

		public Relation getRelation(String key) {
			return this.relations.get(key);
		}

		public void addRelation(Relation r) {
			this.relations.put(r.getName(), r);
		}
	}

	public static List<Node> json2Node(String path) {

		try {
			File f = new File( "G:\\javawork\\back\\neo4jutils\\src\\main\\java\\entity.xml");
			if (!f.exists()) {
				System.out.println("  Error : Config file doesn't exist!");
				System.exit(1);
			}
			SAXReader reader = new SAXReader();
			Document doc = reader.read(f);
			Element root = doc.getRootElement();
			Element data;

			Iterator<?> itr = root.elementIterator("entity");
			Node n;
			while (itr.hasNext()) {
				data = (Element) itr.next();
				n = new Node();
				n.setName(data.elementText("name"));
				Element relations = (Element) data.element("relations");
				Iterator<?> itRelation = relations.elementIterator("relation");
				Element relation;
				while (itRelation.hasNext()) {
					relation = (Element) itRelation.next();
					Relation r = new Relation();
					r.setExpression(relation.elementText("expression"));
					r.setName(relation.elementText("name"));
					n.addRelation(r);
				}
				xml.put(n.getName(), n);
			}
		} catch (Exception ex) {
			System.out.println("Error : " + ex.toString());
		}

		Path p = Paths.get(path);
		LineIterator it = null;
		try {
			it = IOUtils.lineIterator(Files.newInputStream(p), "utf-8");
		} catch (IOException e) {
			e.printStackTrace();
		}

		List<Node> nodes = new ArrayList<Node>();
		
		while (it.hasNext()) {
			String json = it.next();
			if (HIDDEN_CHAR == (json.charAt(0))) {
				continue;
			}
			JSONObject dataJson = new JSONObject(json);
			Iterator<String> jsonIt = dataJson.keys();
			Node nodeData = new Node();
			while (jsonIt.hasNext()) {
				String key = jsonIt.next();
				if (key.equals(E_TYPE)) {
					continue;
				}

				Object o = dataJson.get(key);
				nodeData.addProperty(key, o.toString());
				//System.out.println(key + ":" + o.toString());
			}
			String type = dataJson.getString(E_TYPE);
			Node configNode = xml.get(type);

			nodeData.setName(type);
			nodeData.setRelations(configNode.getRelations());
			
			nodes.add(nodeData);
		}
		
		return nodes;
	}
}
