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
		return null;
	}
	public static double[] getEntityVector(int id){
		return DataLoad.EntityVector.get(id);
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
}
