package com.graduate.server.common;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;






import com.graduate.server.model.Entity;
import com.graduate.server.model.Relation;

public class DataUtil {
	public DataUtil(){}
	public static Entity getEnityByName(String name){
		if(DataLoad.EntityId.containsKey(name))
			return new Entity(DataLoad.EntityId.get(name),name);
		return null;
	}
	public static Entity getEntityById(int id){
		if(DataLoad.IdEntity.containsKey(id)){
			return new Entity(id,DataLoad.IdEntity.get(id));
		}
		System.out.println("No Entity");
		return null;
	}
	public static double[] getEntityVector(int id){
		if(DataLoad.EntityVector.containsKey(id)){
		return DataLoad.EntityVector.get(id);
		}
		return null;
	}
	public static int getRelationId(String name){
		if(DataLoad.RelationId.containsKey(name)){
			return DataLoad.RelationId.get(name);
		}
		return DataLoad.Error_type;
	}
	public static String getRelationName(int id){
		if(DataLoad.IdRelation.containsKey(id)){
			return DataLoad.IdRelation.get(id);
		}
		return ""+DataLoad.Error_type;
	}
	public static boolean JuRelationByEntity(int searchDirection,int id){
		return DataLoad.tripleHash.get(searchDirection).containsKey(id);
	}
	public static boolean JuRelationByRelation(int searchDirection,int entityId,int relationId){
		return DataLoad.tripleHash.get(searchDirection).get(entityId).containsKey(relationId);
	}
	public static boolean JuTriple(Entity query,Relation relation,Entity target){
		if(JuRelationByEntity(relation.getDirection(),query.getId())){
			if(JuRelationByRelation(relation.getDirection(),query.getId(),
			relation.getRelationId())){
				if(DataLoad.tripleHash.get(relation.getDirection()).get(query.getId()).
					get(relation.getRelationId()).contains(target.getId()))
					return true;
				}
			}
		return false;
	}
	public static HashMap<Integer,HashSet<Integer>> getRelationByEntity(int searchDirection,int id){
			return DataLoad.tripleHash.get(searchDirection).get(id);
	}
	//计算两个实体的欧式距离并且返回目标实体
	public static Entity getScoreofEntity(double[] queryVector,double[] targetVector,int id) {
		double sum=0;
		Entity entity=DataUtil.getEntityById(id);
		int dimension=queryVector.length;
		for(int i=0;i<dimension;i++){
			sum+=Math.pow(queryVector[i] - targetVector[i], 2);
		}
		if(sum==0) entity.setScore(-100);
		else entity.setScore((Math.sqrt(dimension) - Math.sqrt(sum)) / Math.sqrt(dimension));
		return entity;
	}
	public static double computeDistance(double[] query,double[] relation,double[] target,int type){
		double[] vector = new double[DataLoad.D_Entity];
		if(type==0){
			for(int i=0;i<query.length;i++)
				vector[i]=query[i]-relation[i];
		}else{
			for(int i=0;i<query.length;i++)
				vector[i]=query[i]+relation[i];
		}
		return computeScore(vector, target);
	}
	public static double computeScore(double[] vector1,double[] vector2){
		int dimension=vector1.length;
		double sum=0;
		for(int i=0;i<dimension;i++){
			sum+=Math.pow(vector1[i] - vector2[i], 2);
		}
		if(sum==0) return -100;
		return (Math.sqrt(dimension) - Math.sqrt(sum))/ Math.sqrt(dimension);
	}
	public static double computeScore(Entity query,double[]relation,int direction,double[]target){
		double[]queryVector=getEntityVector(query.getId());
		double[]vector=new double[DataLoad.D_Entity];
		if(direction==0){
			for(int i=0;i<queryVector.length;i++){
				vector[i]=queryVector[i]+relation[i];
			}
		}else{
			for(int i=0;i<queryVector.length;i++){
				vector[i]=queryVector[i]-relation[i];
			}
		}
		return query.getScore()*computeScore(vector,target);
	}
	public static String getEntityName(int id) {
		if(DataLoad.IdEntity.containsKey(id)){
			return DataLoad.IdEntity.get(id);
		}
		return ""+DataLoad.Error_type;
	}
	public static Entity getScoreofEntity(Integer key,
			List<Entity> list) {
		if(DataLoad.EntityVector.containsKey(key)){
			double[]vector=DataLoad.EntityVector.get(key);
			double score=list.stream()
				.map(e->getEntityVector(e.getId()))
				.map(e->computeScore(e,vector))
				.reduce(0.0,(a,e)->a+e);
			return new Entity(key,score);
		}
		return null;
	}
	public static Entity getScoreofFeature(Integer key, double[] value,
		  Relation relation, List<Entity> queryList) {
		  double[] relationVector=DataLoad.RelationVector.get(relation.getRelationId());
		  double score=queryList.stream()
		  			.map(e->computeScore(e,relationVector,relation.getDirection(),value))
		  			.reduce(0.0,(a,e)->a+e);
		return new Entity(key,score);
	}
}	
