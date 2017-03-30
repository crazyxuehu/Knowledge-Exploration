package com.graduate.server.service.imp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import com.graduate.server.common.DataLoad;
import com.graduate.server.common.DataUtil;
import com.graduate.server.model.Entity;
import com.graduate.server.model.Relation;
import com.graduate.server.model.RelationSize;

public class CommonService {
	public static List<Entity> RankEntity(List<Entity> target,int k) {
		int size=target.size()>k?k:target.size();
		return target.parallelStream()
				.sorted((a,b)->a.getScore()<b.getScore()?1:-1)
				.limit(size)
				.collect(Collectors.toList());
	}
	//计算top(k)的relation
	public static List<Relation>getRelationTop(List<Entity>list){
		int min_size,max_size;
		List<RelationSize>Relation_size=new ArrayList<RelationSize>();
		for(Entity i:list){
			min_size=Integer.MAX_VALUE;
			max_size=0;
			for(int j=0;j<2;j++){
				if(DataUtil.JuRelationByEntity(j,i.getId())){
					for(HashSet<Integer> relation:DataLoad.tripleHash.get(j).get(i.getId()).values()){
						int num=relation.size();
						min_size=min_size>num?num:min_size;
						max_size=max_size<num?num:max_size;
					}
				}
			}
			Relation_size.add(new RelationSize(i,min_size,max_size));
		}
		HashMap<Integer, Relation> relationMap = new HashMap<>();
		for(RelationSize i:Relation_size){
			int size=i.getRelation_size_max()+i.getRelation_size_min();
			for(int j=0;j<2;j++){
				if(DataUtil.JuRelationByEntity(j,i.getEntity().getId())){
					for(Entry<Integer,HashSet<Integer>> relationEntity:DataLoad.tripleHash.get(j).get(i.getEntity().getId()).entrySet()){
						int key=relationEntity.getKey();
						double score=0;
						Relation relation=new Relation(DataLoad.Relation_type,j,key);
						if(key==DataUtil.getRelationId("subject")){
							score=1;
						}else{
							score=(double)relationEntity.getValue().size()/size;
							score = (- score * Math.log(score)) *i.getEntity().getScore();
						}
						relation.setScore(score);
						if(relationMap.containsKey(key))
							relationMap.get(key).setScore(relationMap.get(key).getScore()+score);
						else{
							relationMap.put(relationEntity.getKey(), relation);
						}
					}
				}
			}
		}
		return RankRelation(new ArrayList<Relation>(relationMap.values()),Integer.MAX_VALUE);
	}
	//根据relation获取相似实体
	public static List<Entity> getTarget(List<Entity>queryList,Relation relation){
		HashMap<Integer, Entity> targetEntityMap = new HashMap<>();
		queryList.parallelStream()
			.forEach(f->{
			if(DataUtil.JuRelationByEntity(relation.getDirection(),f.getId())){
				if(DataUtil.JuRelationByRelation(relation.getDirection(),f.getId(),relation.getRelationId())){
					double []queryVector=DataUtil.getEntityVector(f.getId());
					DataLoad.tripleHash.get(relation.getDirection()).get(f.getId())
					.get(relation.getRelationId()).parallelStream()
							.map(e->DataUtil.getScoreofEntity(queryVector,DataUtil.getEntityVector(e),e))
							.forEach(e->{if(targetEntityMap.containsKey(e.getId())){
									targetEntityMap.get(e.getId()).setScore(targetEntityMap.get(e.getId()).getScore());
								}else{
									targetEntityMap.put(e.getId(),e);
								}
							});
				}
			}
		});
		return new ArrayList<Entity>(targetEntityMap.values());
	}
	//对relation序列排序
	public static List<Relation> RankRelation(ArrayList<Relation>list,int k){
		int size=list.size()<k?list.size():k;
		return list.parallelStream().sorted((a,b)->a.getScore()>b.getScore()?1:-1)
							.limit(size)
							.collect(Collectors.toList());
		/*for(int i=0;i<ll.size();i++){
			System.out.println(ll.get(i).getRelationId()+" "+ll.get(i).getScore());
		}*/
	}
}
