package com.graduate.server.service.imp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;

import com.graduate.server.common.DataLoad;
import com.graduate.server.common.DataUtil;
import com.graduate.server.model.Entity;
import com.graduate.server.model.Feature;
import com.graduate.server.model.Path;
import com.graduate.server.model.Relation;
import com.graduate.server.service.ExploreService;
@Service
public class ExploreServiceImp implements ExploreService{
	//推荐相似实体
		public List<Entity> getSimEntity(List<Entity>list){
			List<Entity>simList=DataLoad.EntityVector.entrySet().parallelStream()
											.map(e->DataUtil.getScoreofEntity(e.getKey(),list))
											.collect(Collectors.toList());
			return CommonService.RankEntity(simList,DataLoad.Out_Entity_Size*list.size());
		}
		@Override
		public List<Feature>getSimFeature(List<Entity>queryList){
			List<Feature>featureList=new ArrayList<Feature>();
			int count=0;
			for(Relation relation:CommonService.getRelationTop(queryList)){
				  if(DataLoad.RelationVector.containsKey(relation.getRelationId())){
					  //if(count++>=3) break;
					  List<Entity>targetList=DataLoad.EntityVector.entrySet().parallelStream()
						.map(e->DataUtil.getScoreofFeature(e.getKey(),e.getValue(),relation,queryList))
						.collect(Collectors.toList());
					  int id=DataUtil.getRelationId("subject");
					  CommonService.RankEntity(targetList,
					  relation.getRelationId()==id?10:DataLoad.Out_Feature_Size).stream()
					  .forEach(entity->{
						  featureList.add(new Feature(relation,entity,relation.getScore()*entity.getScore()));
					  });
				  }
			}
			 return featureList;
		}
		@Override
		public List<Path> getPath(String head, String tail){
			Entity headEntity=DataUtil.getEnityByName(head);
			Entity tailEntity=DataUtil.getEnityByName(tail);
			List<Path>pathList=new ArrayList<Path>();
			int count=0;
			HashSet<Integer> mp=new HashSet<Integer>();
			getPathEntity(headEntity,tailEntity,new Path(headEntity),pathList,count,mp);
			System.out.println(mp.size());
			return pathList;
		}
		private boolean getPathEntity(Entity head,Entity tail,Path path,List<Path>pathList,int count,HashSet mp){
			if(count>4) return false;
			if(pathList.size()>=3) return false;
			if(!mp.contains(head.getId())){
				mp.add(head.getId());
			}else return false;
			boolean flag=false;
			List<Entity>targetList=new ArrayList<Entity>();
			for(Relation relation:CommonService.getRelationTop(head)){
				targetList=CommonService.RankEntity(CommonService.getTarget(head, relation,tail),Integer.MAX_VALUE);
				int size=targetList.size();
				for(Entity entity:targetList){
					if(entity.getId()==tail.getId()){
						path.addEntity(entity);
						path.addRelation(relation);
						path.setScore(entity.getScore()/Math.log(size));
						pathList.add(new Path(path.getHead(),path.getRelationPath(),path.getEntityPath(),path.getScore()));
						return true;
					}else{
						path.addEntity(entity);
						path.addRelation(relation);
						getPathEntity(entity, tail, path, pathList, count+1,mp);
						path.popEntity();
						path.popRelation();
					}
				}
			}
			return flag;
		}

		/*public List<Path> getPath(String head, String tail) {
			Entity headEntity=DataUtil.getEnityByName(head);
			Entity tailEntity=DataUtil.getEnityByName(tail);
			List<Entity>queryList=new ArrayList<Entity>();
			List<Entity>targetList=new ArrayList<Entity>();
			List<Path>pathList=new ArrayList<Path>();
			
			queryList.add(headEntity);
			for(Relation relation:CommonService.getRelationTop(queryList)){
				targetList=CommonService.RankEntity(CommonService.getTarget(queryList, relation),Integer.MAX_VALUE);
				int size=targetList.size();
				for(Entity entity:targetList){
					System.out.println(relation.getDirection()+" "+relation.getName()+" "+entity.getName());
					if(entity.getId()==tailEntity.getId()){
						Path path=new Path();
						path.setHead(headEntity);
						path.addEntity(entity);
						path.addRelation(relation);
						path.setScore(entity.getScore()/Math.log(size));
						pathList.add(path);
					}else{
						Path path=new Path();
						path.setHead(headEntity);
						path.addRelation(relation);
						path.addEntity(entity);
						List<Entity>queryList1=new ArrayList<>();
						queryList1.add(entity);
						for(Relation relation1:CommonService.getRelationTop(queryList1)){
							List<Entity>targetList1=CommonService.RankEntity(CommonService.getTarget(queryList1, relation1),Integer.MAX_VALUE);
							int size1=targetList1.size();
							for(Entity entity1:targetList1){
								System.out.println(relation1.getDirection()+" "+relation1.getName()+" "+entity1.getName());
								if(entity1.getId()==tailEntity.getId()){
									path.addEntity(entity1);
									path.addRelation(relation1);
									path.setScore(entity1.getScore()/Math.log(size1));
								}else{
									path.addEntity(entity1);
									path.addRelation(relation1);
									List<Entity>queryList2=new ArrayList<Entity>();
									queryList2.add(entity1);
									for(Relation relation2:CommonService.getRelationTop(queryList2)){
										List<Entity>targetList2=CommonService.RankEntity(CommonService.getTarget(queryList2, relation2),Integer.MAX_VALUE);
										int size2=targetList2.size();
										for(Entity entity2:targetList2){
											System.out.println(relation2.getDirection()+" "+relation2.getName()+" "+entity2.getName());
											if(entity2.getId()==tailEntity.getId()){
												path.addEntity(entity2);
												path.addRelation(relation2);
												path.setScore(entity2.getScore()/Math.log(size2));
											}
										}
									}
								}
							}
						}
					}
				}
			}
			return pathList;
		}*/
		
		
}
