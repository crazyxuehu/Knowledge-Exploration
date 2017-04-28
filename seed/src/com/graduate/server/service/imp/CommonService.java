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
		/*int min_size,max_size;*/
		int min_size=Integer.MAX_VALUE;
		int max_size=0;
		List<RelationSize>Relation_size=new ArrayList<RelationSize>();
		for(Entity i:list){
			/*min_size=Integer.MAX_VALUE;
			max_size=0;*/
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
		//int count=0;
		for(Entity i:list){
			//.int size=i.getRelation_size_max()+i.getRelation_size_min();
				int size=min_size+max_size;
			for(int j=0;j<2;j++){
				if(DataUtil.JuRelationByEntity(j,i.getId())){
					for(Entry<Integer,HashSet<Integer>> relationEntity:DataLoad.tripleHash.get(j).get(i.getId()).entrySet()){
					//	count++;
						int key=relationEntity.getKey();
						double score=0;
						Relation relation=new Relation(DataLoad.Relation_type,j,key);
						if(key==DataUtil.getRelationId("subject")){
							score=1;
						}else{
							score=(double)relationEntity.getValue().size()/size;
							score = (- score * Math.log(score)) *i.getScore();
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
		//System.out.println("Entity Num:"+count);
		//System.out.println("Entity Num:"+relationMap.size());
		return RankRelation(new ArrayList<Relation>(relationMap.values()),Integer.MAX_VALUE);
	}
	//计算1个entity的top(K)的relation
	public static List<Relation>getRelationTop(Entity queryEntity){
		/*int min_size,max_size;*/
		int min_size=Integer.MAX_VALUE;
		int max_size=0;
		List<RelationSize>Relation_size=new ArrayList<RelationSize>();
		for(int j=0;j<2;j++){
			if(DataUtil.JuRelationByEntity(j,queryEntity.getId())){
				for(HashSet<Integer> relation:DataLoad.tripleHash.get(j).get(queryEntity.getId()).values()){
					int num=relation.size();
					min_size=min_size>num?num:min_size;
					max_size=max_size<num?num:max_size;
				}
			}
		}
		HashMap<Integer, Relation> relationMap = new HashMap<>();
		//int count=0;
			int size=min_size+max_size;
			for(int j=0;j<2;j++){
				if(DataUtil.JuRelationByEntity(j,queryEntity.getId())){
					for(Entry<Integer,HashSet<Integer>> relationEntity:DataLoad.tripleHash.get(j).get(queryEntity.getId()).entrySet()){
						int key=relationEntity.getKey();
						double score=0;
						Relation relation=new Relation(DataLoad.Relation_type,j,key);
						if(key==DataUtil.getRelationId("subject")){
							score=1;
						}else{
							score=(double)relationEntity.getValue().size()/size;
							score = (- score * Math.log(score)) *queryEntity.getScore();
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
		//System.out.println("Entity Num:"+count);
		//System.out.println("Entity Num:"+relationMap.size());
		return RankRelation(new ArrayList<Relation>(relationMap.values()),Integer.MAX_VALUE);
	}
	//根据relation获取相似实体
	public static List<Entity> getTarget(List<Entity>queryList,Relation relation){
		HashMap<Integer, Entity> targetEntityMap = new HashMap<>();
		queryList.stream()
			.forEach(f->{
			if(DataUtil.JuRelationByEntity(relation.getDirection(),f.getId())){
				if(DataUtil.JuRelationByRelation(relation.getDirection(),f.getId(),relation.getRelationId())){
					double []queryVector=DataUtil.getEntityVector(f.getId());
					DataLoad.tripleHash.get(relation.getDirection()).get(f.getId())
					.get(relation.getRelationId()).stream()
							.map(e->DataUtil.getScoreofEntity(queryVector,DataUtil.getEntityVector(e),e))
							.forEach(e->{
								if(targetEntityMap.containsKey(e.getId())){
									targetEntityMap.get(e.getId()).setScore(targetEntityMap.get(e.getId()).getScore()+e.getScore());
								}else{
									targetEntityMap.put(e.getId(),e);
								}
							});
				}
			}
		});
		return new ArrayList<Entity>(targetEntityMap.values());
	}
	public static List<Entity> getTarget(Entity queryEntity,Relation relation){
		if(DataUtil.JuRelationByEntity(relation.getDirection(),queryEntity.getId())){
			if(DataUtil.JuRelationByRelation(relation.getDirection(),queryEntity.getId(),relation.getRelationId())){
				return DataLoad.tripleHash.get(relation.getDirection()).get(queryEntity.getId())
				.get(relation.getRelationId()).stream()
				.map(id->DataUtil.getEntityById(id))
				.collect(Collectors.toList());
			}
		}
		return null;
	}
	//根据relation和查询实体获取相似实体
	public static List<Entity> getTarget(Entity queryEntity,Relation relation,Entity targetEntity){
		HashMap<Integer, Entity> targetEntityMap = new HashMap<>();
			if(DataUtil.JuRelationByEntity(relation.getDirection(),queryEntity.getId())){
				if(DataUtil.JuRelationByRelation(relation.getDirection(),queryEntity.getId(),relation.getRelationId())){
					double []queryVector=DataUtil.getEntityVector(queryEntity.getId());
					DataLoad.tripleHash.get(relation.getDirection()).get(queryEntity.getId())
					.get(relation.getRelationId()).stream()
							.map(e->DataUtil.getScoreofEntity(queryVector,DataUtil.getEntityVector(e),e))
							.forEach(e->{
								if(targetEntityMap.containsKey(e.getId())){
									targetEntityMap.get(e.getId()).setScore(targetEntityMap.get(e.getId()).getScore());
								}else{
									targetEntityMap.put(e.getId(),e);
								}
							});
				}
			}
		return new ArrayList<Entity>(targetEntityMap.values());
	}
	//对relation序列排序
	public static List<Relation> RankRelation(ArrayList<Relation>list,int k){
		int size=list.size()<k?list.size():k;
		//System.out.println("Relation Size:"+size);
		return list.parallelStream().sorted((a,b)->a.getScore()>b.getScore()?1:-1)
							.limit(size)
							.collect(Collectors.toList());
		/*for(int i=0;i<ll.size();i++){
			System.out.println(ll.get(i).getRelationId()+" "+ll.get(i).getScore());
		}*/
	}
	public static void test(List<Entity>list){
		List<Entity> ll=RankEntity(list, 10);
		/*for(Entity entity:ll){
			System.out.println(entity.getId()+" "+entity.getScore());
		}*/
		ll=RankEntity(list, 15);
		/*for(Entity entity:ll){
			System.out.println(entity.getId()+" "+entity.getScore());
		}*/
	}
	public static List<Entity>getCategory(List<Entity>queryList){
		HashMap<Integer,Entity> categoryMap=new HashMap<Integer,Entity>();
		int min_size=Integer.MAX_VALUE;
		int max_size=0;
		int relationId=DataUtil.getRelationId("subject");
		for(Entity entity:queryList){
			if(DataUtil.JuRelationByEntity(0,entity.getId())){
				if(DataUtil.JuRelationByRelation(0, entity.getId(), relationId)){
					for(Integer category:DataLoad.tripleHash.get(0).get(entity.getId()).get(relationId)){
						if(DataUtil.JuRelationByEntity(1,category)){
							int size=DataLoad.tripleHash.get(1).get(category).values().size();
							min_size=min_size>size?size:min_size;
							max_size=max_size<size?size:max_size;
						}
					}
				}
			}else if(DataUtil.JuRelationByEntity(1,entity.getId())){
				int size=DataLoad.tripleHash.get(1).get(entity.getId()).values().size();
				min_size=min_size>size?size:min_size;
				max_size=max_size<size?size:max_size;
			}
		}
		int size=min_size+max_size;
		for(Entity entity:queryList){
			if(DataUtil.JuRelationByEntity(0,entity.getId())){
				if(DataUtil.JuRelationByRelation(0, entity.getId(), relationId)){
					for(Integer category:DataLoad.tripleHash.get(0).get(entity.getId()).get(relationId)){
						if(DataUtil.JuRelationByEntity(1,category)){
							double score=DataLoad.tripleHash.get(1).get(category).values().size()/(size*1.0);
							//System.out.println("score1"+DataLoad.tripleHash.get(1).get(category).values().size()+" "+size);
							score=-score*Math.log(score)*1;
							//System.out.println("1//"+score);
							score+=entity.getScore();
							//System.out.println("2//"+score);
							if(categoryMap.containsKey(category)){
								//System.out.println("categorymap:"+categoryMap.get(category).getScore());
								categoryMap.get(category).setScore(categoryMap.get(category).getScore()+score);
							}else{
								categoryMap.put(category,new Entity(category,score));
							}
						}
					}
				}
			}else if(DataUtil.JuRelationByEntity(1,entity.getId())){
				double score=DataLoad.tripleHash.get(1).get(entity.getId()).values().size()/(size*1.0);
				score=-score*Math.log(score)*entity.getScore();
				if(categoryMap.containsKey(entity.getId())){
					categoryMap.get(entity.getId()).setScore(categoryMap.get(entity.getId()).getScore()+score);
				}else{
					categoryMap.put(entity.getId(),new Entity(entity.getId(),score));
				}
			}
		}
		return RankEntity(new ArrayList<Entity>(categoryMap.values()), categoryMap.size());
	}
	
	public static List<Relation>getTargetRelation(List<Entity>queryList,Entity category){
		int relationId=DataUtil.getRelationId("subject");
		List<Relation>relationList=null;
			if(DataUtil.JuRelationByEntity(1,category.getId())){
				if(DataUtil.JuRelationByRelation(1, category.getId(), relationId)){
					List<Entity>targetList=DataLoad.tripleHash.get(1).get(category.getId()).get(relationId).stream()
							.map(entity->DataUtil.getScoreofEntity(entity, queryList))
							.collect(Collectors.toList());
					targetList=RankEntity(targetList,DataLoad.Out_Feature_Size);
					relationList=getRelationTop(targetList);
				}
			}
		return relationList;
	}
	public static Entity getCategory(Entity query){
		HashMap<Integer,Entity> categoryMap=new HashMap<Integer,Entity>();
		int min_size=Integer.MAX_VALUE;
		int max_size=0;
		int relationId=DataUtil.getRelationId("subject");
			if(DataUtil.JuRelationByEntity(0,query.getId())){
				if(DataUtil.JuRelationByRelation(0, query.getId(), relationId)){
					for(Integer category:DataLoad.tripleHash.get(0).get(query.getId()).get(relationId)){
						if(DataUtil.JuRelationByEntity(1,category)){
							int size=DataLoad.tripleHash.get(1).get(category).values().size();
							min_size=min_size>size?size:min_size;
							max_size=max_size<size?size:max_size;
						}
					}
				}
			}else if(DataUtil.JuRelationByEntity(1,query.getId())){
				int size=DataLoad.tripleHash.get(1).get(query.getId()).values().size();
				min_size=min_size>size?size:min_size;
				max_size=max_size<size?size:max_size;
			}
		int size=min_size+max_size;
			if(DataUtil.JuRelationByEntity(0,query.getId())){
				if(DataUtil.JuRelationByRelation(0, query.getId(), relationId)){
					for(Integer category:DataLoad.tripleHash.get(0).get(query.getId()).get(relationId)){
						if(DataUtil.JuRelationByEntity(1,category)){
							double score=DataLoad.tripleHash.get(1).get(category).values().size()/(size*1.0);
							//System.out.println("score1"+DataLoad.tripleHash.get(1).get(category).values().size()+" "+size);
							score=-score*Math.log(score)*1;
							//System.out.println("1//"+score);
							score+=query.getScore();
							//System.out.println("2//"+score);
							if(categoryMap.containsKey(category)){
								//System.out.println("categorymap:"+categoryMap.get(category).getScore());
								categoryMap.get(category).setScore(categoryMap.get(category).getScore()+score);
							}else{
								categoryMap.put(category,new Entity(category,score));
							}
						}
					}
				}
			}else if(DataUtil.JuRelationByEntity(1,query.getId())){
				double score=DataLoad.tripleHash.get(1).get(query.getId()).values().size()/(size*1.0);
				score=-score*Math.log(score)*query.getScore();
				if(categoryMap.containsKey(query.getId())){
					categoryMap.get(query.getId()).setScore(categoryMap.get(query.getId()).getScore()+score);
				}else{
					categoryMap.put(query.getId(),new Entity(query.getId(),score));
				}
			}
		return RankEntity(new ArrayList<Entity>(categoryMap.values()), categoryMap.size()).get(0);
	}
}
