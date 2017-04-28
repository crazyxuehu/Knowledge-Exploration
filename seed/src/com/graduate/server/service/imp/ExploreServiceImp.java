package com.graduate.server.service.imp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;

import com.graduate.server.common.DataLoad;
import com.graduate.server.common.DataUtil;
import com.graduate.server.model.Edge;
import com.graduate.server.model.Entity;
import com.graduate.server.model.Feature;
import com.graduate.server.model.Link;
import com.graduate.server.model.Node;
import com.graduate.server.model.Path;
import com.graduate.server.model.Relation;
import com.graduate.server.model.Vertex;
import com.graduate.server.model.Visual;
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
		public Visual getPath(String head, String tail){
			//System.out.println(head+tail);
			Entity headEntity=DataUtil.getEnityByName(head);
			Entity tailEntity=DataUtil.getEnityByName(tail);
			//System.out.println(headEntity.getId()+tailEntity.getId());
			List<Path>pathList=new ArrayList<Path>();
			int count=0;
			HashSet<Integer> mp=new HashSet<Integer>();
			getPathEntity(headEntity,tailEntity,new Path(headEntity),pathList,count,mp);
			int size=pathList.size();
			HashMap<String,Integer>map=new HashMap<>();
			int max=0;
			/*for(Path path:pathList){
				for(Entity entity:path.getEntityPath()){
					System.out.print(entity.getName()+" ");
				}
				System.out.println();
			}*/
			for(Path path:pathList){
				max=max<path.getEntityPath().size()?path.getEntityPath().size():max;
			}
			int iterator=0;
			List<Vertex>nodeList=new ArrayList<Vertex>();
			List<Edge>linkList=new ArrayList<Edge>();
			//nodeList.add(new Node(headEntity.getName(),0,400));
			/*int x=100;
			int chg=-1;
			int y=600;
			int dy=200;*/
			/*while(iterator<max){
				for(Path path:pathList){
					if(iterator<path.getEntityPath().size()){
						String name=path.getEntityPath().get(iterator).getName();
						if(map.containsKey(name)&&!name.equals(tailEntity.getName())){
							//System.out.println("|||"+name);
							continue;
						}else{
							map.put(name, 1);
							if(!name.equals(tailEntity.getName())){
							//	nodeList.add(new Node(name,x,y));
								y+=chg*dy;
								System.out.println(name+" "+x+" "+y+" "+dy);
								if(dy<=0){
									y=400;
									chg*=-1;
									dy=100;
								}else if(dy>=200){
									y=400;
									chg*=-1;
									dy=200;
								}
								dy+=chg*100;
							}
							if(iterator==0){
								//linkList.add(new Link(headEntity.getName(),name));
							}else{
								String pre=path.getEntityPath().get(iterator-1).getName();
								//linkList.add(new Link(pre,name));
							}
							
						}
						
					}
					
				}
				iterator++;
				x+=100;
			}*/
			int y=600;
			int dy=300;
			int x=0;
			int dx=200;
			HashSet<Integer>setNode=new HashSet<Integer>();
			HashSet<String>setLink=new HashSet<String>();
			for(Path path:pathList){
				x=0;
				Stack<Entity>entityStack=path.getEntityPath();
				Stack<Relation>relationStack=path.getRelationPath();
				for(int i=0;i<entityStack.size();i++){
					Entity tnode=entityStack.get(i);
					if(i>0){
						Relation link=relationStack.get(i-1);
						Entity snode=entityStack.get(i-1);
						if(!setNode.contains(tnode.getId())&&tnode.getId()!=tailEntity.getId()){
							setNode.add(tnode.getId());
							int category=1;
							if(link.getName().equals("subject")&&tnode.getName().contains("category:")){
								category=0;
							}
							nodeList.add(new Vertex(tnode.getName(),x,y,category));
							if(link.getDirection()==0){
									linkList.add(new Edge(snode.getName(),tnode.getName(),link.getName()));
									setLink.add(snode.getName()+tnode.getName());
							}else{
									linkList.add(new Edge(tnode.getName(),snode.getName(),link.getName()));
									setLink.add(snode.getName()+tnode.getName());
							}
						}else{
							if(link.getDirection()==0){
								if(!setLink.contains(snode.getName()+tnode.getName())){
									linkList.add(new Edge(snode.getName(),tnode.getName(),link.getName()));
									setLink.add(snode.getName()+tnode.getName());
								}
							}else{
								if(!setLink.contains(tnode.getName()+snode.getName())){
									linkList.add(new Edge(tnode.getName(),snode.getName(),link.getName()));
									setLink.add(snode.getName()+tnode.getName());
								}
							}
						}
					}else{
						if(!setNode.contains(tnode.getId())){
							setNode.add(tnode.getId());
							nodeList.add(new Vertex(tnode.getName(),x,y,2));
						}
					}
					x+=dx;
				}
				dx-=20;
				y+=dy;
				dy=-500;
			}
			nodeList.add(new Vertex(tail,x,600,3));
			//nodeList.add(new Node(tailEntity.getName(),x,300));
			Visual vis=new Visual();
			vis.setEdgeList(linkList);
			vis.setVertexList(nodeList);
			return vis;
		}
		private boolean getPathEntity(Entity head,Entity tail,Path path,List<Path>pathList,int count,HashSet<Integer>mp){
			if(count>4) return false;
			if(pathList.size()>=3) return false;
			if(!mp.contains(head.getId())){
				mp.add(head.getId());
			}else return false;
			boolean flag=false;
			List<Entity>targetList=new ArrayList<Entity>();
			for(Relation relation:CommonService.getRelationTop(head)){
				targetList=CommonService.getTarget(head, relation,tail);
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
		@Override
		public Visual getAllPath(String head, List<Entity> tailList) {
			HashSet<Integer> targetMap=new HashSet<Integer>();
			Entity headEntity=DataUtil.getEnityByName(head);
			for(Entity target:tailList){
				targetMap.add(target.getId());
			}
			List<Path>pathList=new ArrayList<Path>();
			int count=0;
			HashSet<Integer> mp=new HashSet<Integer>();
			getAllPathEntity(headEntity,targetMap,new Path(headEntity),pathList,count,mp);
			System.out.println(pathList.size());
			/*for(Path path:pathList){
				for(Entity entity:path.getEntityPath()){
					System.out.print(entity.getName());
				}
				System.out.println();
			}*/
			int max=0;
			List<Node>ndlist=new ArrayList<Node>();
			List<Link>lklist=new ArrayList<Link>();
			HashSet<Integer>enSet=new HashSet<Integer>();
			HashSet<String>lkSet=new HashSet<String>();
			for(Path path:pathList){
				Stack<Entity>entityStack=path.getEntityPath();
				Stack<Relation>relationStack=path.getRelationPath();
				for(int i=0;i<entityStack.size();i++){
					if(i>0){
						Entity tnode=entityStack.get(i);
						Entity snode=entityStack.get(i-1);
						Relation relation=relationStack.get(i-1);
						if(!enSet.contains(tnode.getId())){
							//System.out.println(tnode.getId()+" "+tnode.getName());
							enSet.add(tnode.getId());
							int category=1;
							if(relation.getName().equals("subject")&&tnode.getName().contains("category:")){
								category=0;
							}
							if(targetMap.contains(tnode.getId())){
								category=3;
							}
							ndlist.add(new Node(tnode.getName(),category));
							if(relation.getDirection()==0){
								lkSet.add(snode.getName()+tnode.getName());
								lklist.add(new Link(snode.getName(),tnode.getName(),relation.getName()));
							}else{
								lkSet.add(tnode.getName()+snode.getName());
								lklist.add(new Link(tnode.getName(),snode.getName(),relation.getName()));
							}
							
						}else{
								if(relation.getDirection()==0){
									String tmp=snode.getName()+tnode.getName();
									if(!lkSet.contains(tmp)){
										lkSet.add(snode.getName()+tnode.getName());
										lklist.add(new Link(snode.getName(),tnode.getName(),relation.getName()));
									}
								}else{
									String tmp=tnode.getName()+snode.getName();
									if(!lkSet.contains(tmp)){
										lkSet.add(tnode.getName()+snode.getName());
										lklist.add(new Link(tnode.getName(),snode.getName(),relation.getName()));
									}
								}
						}
						
					}else{
						Entity node=entityStack.get(i);
						if(!enSet.contains(node.getId())){
							enSet.add(node.getId());
							ndlist.add(new Node(node.getName()));
						}
					}
				}
			}
			return new Visual(ndlist,lklist);
		}
		private boolean getAllPathEntity(Entity head,HashSet<Integer>tail,Path path,List<Path>pathList,int count,HashSet<Integer>mp){
			if(count>4) return false;
			if(pathList.size()>=9) return false;
			if(!mp.contains(head.getId())){
				mp.add(head.getId());
			}else return false;
			boolean flag=false;
			List<Entity>targetList=new ArrayList<Entity>();
			for(Relation relation:CommonService.getRelationTop(head)){
				targetList=CommonService.getTarget(head, relation);
				int size=targetList.size();
				for(Entity entity:targetList){
					if(tail.contains(entity.getId())){
						path.addEntity(entity);
						path.addRelation(relation);
						//System.out.println(entity.getName());
/*						path.setScore(entity.getScore()/Math.log(size));*/
						pathList.add(new Path(path.getHead(),path.getRelationPath(),path.getEntityPath(),path.getScore()));
						return true;
					}else{
						//System.out.println(entity.getName());
						path.addEntity(entity);
						path.addRelation(relation);
						getAllPathEntity(entity,tail, path, pathList, count+1,mp);
						path.popEntity();
						path.popRelation();
					}
				}
			}
			return flag;
		}
		
}
