package ue.ia.entity;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;

import org.apache.commons.io.IOUtils;
import org.apache.commons.io.LineIterator;
import org.json.JSONArray;
import org.json.JSONObject;

import ue.ia.node.utils.NodeUtils;


public class Importer {

	public final static char HIDDEN_CHAR = '#';
	public final static String E_TYPE = "itemtype";
	
	private String entityFile;
	private String relationFile;
	
	public Importer(String filePath,String relationFile){
		this.entityFile=filePath;
		this.relationFile=relationFile;
	}
	
	public void saveNode(){
		
		
		//读取数据文件
		Path p = Paths.get(entityFile);
		LineIterator it = null;
		try {
			it = IOUtils.lineIterator(Files.newInputStream(p), "utf-8");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		while (it.hasNext()) {
			String json = it.next();
			
			// 以# 号打头的数据过滤掉
			if (HIDDEN_CHAR == (json.charAt(0))) {
				System.out.println(json.charAt(0));
				continue;
			}
			JSONObject dataJson = new JSONObject(json);
			Iterator<String> jsonIt = dataJson.keys();
			Node nodeData = new Node();
			while (jsonIt.hasNext()) {
				
				//属性的Key是 E_TYPE 的值，是节点的类型而不是属性。
				String key = jsonIt.next();
				if (key.equals(E_TYPE)) {
					continue;
				}

				//属性中可能带有list之类的值，需要转换一下，否则在写入neo4j时候会报错
				Object o = dataJson.get(key);
				if (o instanceof JSONArray){
					//System.out.println(o.toString());
					JSONArray ja=(JSONArray) o;
					StringBuffer value=new StringBuffer();
					//ja.forEach((s)-> { value.append(s+","); });
					for(int i=0;i<ja.length();i++){
						value.append(ja.get(i).toString()+",");
					}
					//System.out.println(value.toString().substring(0, value.length()-2));
					nodeData.addProperty(key, value.toString().substring(0, value.length()-1));
				}else if(o.toString().startsWith("{")){  //map类型的数据也过滤掉
					continue;
				}else{
					nodeData.addProperty(key, o.toString());
				}

			}
			String label = dataJson.getString(E_TYPE);
			nodeData.setLabel(label);
			
			//调用JDBC创建节点
			//NodeUtils.createNode(nodeData);
			
		}
		
		
	}
	
	private void saveRelation() {
		Path p = Paths.get(relationFile);
		LineIterator it = null;
		try {
			it = IOUtils.lineIterator(Files.newInputStream(p), "utf-8");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		while (it.hasNext()) {
			String line = it.next();
			// 以# 号打头的数据过滤掉
			if (HIDDEN_CHAR == (line.charAt(0))) {
				System.out.println(line.charAt(0));
				continue;
			}
			
			
			Relation relation=new Relation();
			String[] tmp=line.split("\\t");
			
			//获取第一个节点的信息
			String[] node=tmp[0].split(",");
			relation.setHeadNodeType(node[0]);
			relation.setHeadNodeProName(node[1].split("=")[0]);
			relation.setHeadNodeProValue(node[1].split("=")[1]);
			
			
			
			//获取第二个节点
			node=tmp[1].split(",");
			relation.setTailNodeType(node[0]);
			relation.setTailNodeProName(node[1].split("=")[0]);
			relation.setTailNodeProValue(node[1].split("=")[1]);
			//System.out.println(tmp[1]);
			//System.out.println(node[0]);
			
			relation.setLabel(tmp[2]);
			
			
			NodeUtils.createRelation(relation);
			
		}
	}	
	
	public static void main(String[] args) {
		//args[0] 是 Node数据的绝对地址
		//args[1] 是 Relation数据的绝对地址
		Importer imp=new Importer(args[0],args[1]);
		imp.saveNode();
		imp.saveRelation();
		
	}


}
