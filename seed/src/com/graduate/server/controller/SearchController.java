package com.graduate.server.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.*;

import com.graduate.server.common.DataUtil;
import com.graduate.server.common.IndexBuild;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.function.*;

import com.graduate.server.model.Entity;
import com.graduate.server.model.Feature;
import com.graduate.server.model.Link;
import com.graduate.server.model.MetaPath;
import com.graduate.server.model.Path;
import com.graduate.server.model.Relation;
import com.graduate.server.model.Result;
import com.graduate.server.model.SimEntity;
import com.graduate.server.model.Vertex;
import com.graduate.server.model.Visual;
import com.graduate.server.model.Node;
import com.graduate.server.service.ExploreService;
import com.graduate.server.service.IndexService;
import com.graduate.server.service.SearchService;
import com.graduate.server.service.imp.CommonService;

@Controller
@RequestMapping("/")
@Scope("prototype")
public class SearchController {

	@Autowired SearchService service;
	@Autowired ExploreService exservice;
	@Autowired IndexService inservice;
	@ResponseBody
	@RequestMapping(value="test",method=RequestMethod.POST)
	public List<String> getQueryResult(String query){
		long time=System.currentTimeMillis();
		List<String>ll=inservice.autoComplete(query, 8);
		return ll;
		/*System.out.println(System.currentTimeMillis());	
		//list=inservice.getHistory();
		List ll=service.getQueryEntity((List<String>)(mp.get("mylist")));
		String head=mp.get("head").toString();
		String tail=mp.get("tail").toString();
		service.saveQuery(ll);
		res.setQueryEntity(ll);
		List<Feature> flist=service.getQueryFeature(ll);
		System.out.println("featureList:");
		for(Feature feature:flist){
			System.out.println(feature.getQuery().getName()
					+" "+feature.getRelation()
					.getName()+" "+feature
					.getTarget().getName());
		}
		List<Entity>simlist=exservice.getSimEntity(ll);
		System.out.println("similar entity:");
		for(Entity entity:simlist){
			System.out.println(entity.getName()+" "+entity.getScore());
		}
		System.out.println("similar feature");
		System.out.println(System.currentTimeMillis()-time);
		List<Feature>featureList=exservice.getSimFeature(simlist);
		for(Feature feature:featureList){
			System.out.println(feature.getRelation().getRelationId()+" "
					+feature.getRelation()
					.getName()+" "+feature
					.getTarget().getName());
		}
		
		//String head="iskul bukol";
		//String tail="category:intercontinental broadcasting corporation";
		List<Path>pathList;
		pathList=exservice.getPath(head, tail);
		System.out.println(pathList.size());
		for(Path path:pathList){
			System.out.print(path.getHead().getName()+" ");
			for(int i=0;i<path.getRelationPath().size();i++){
				Relation relation=path.getRelationPath().get(i);
				Entity entity=path.getEntityPath().get(i);
				System.out.print(relation.getName()+" "+entity.getName());
			}
			System.out.println();
		}
		//CommonService.test(DataUtil.getEnityByName(head));
		//CommonService.getRelationTop(ll);
		//System.out.println(System.currentTimeMillis()-time);
		pathList=service.getMetaPathByCategory(ll);
		for(Path path:pathList){
			for(Relation relation:path.getRelationList()){
				System.out.print(path.getHead().getName()+" ");
				System.out.println(relation.getName()+" ");
			}
		}
		pathList=service.getMetaPathByRelation(ll);
		for(Path path:pathList){
			for(Entity entity:path.getEntityList()){
				System.out.print(path.getRelation().getName()+" ");
				System.out.println(entity.getName()+" ");
			}
		}*/
		//System.out.println(System.currentTimeMillis()-time);
	}
	@ResponseBody
	@RequestMapping(value="getSearchResult",method=RequestMethod.POST)
	public Result getSearchResult(@RequestBody List<String>queryList){
		System.out.println(queryList.size());
		List ll=service.getQueryEntity(queryList);
		Result res=null;
		if(ll!=null){
			service.saveQuery(ll);
			res=new Result();
			res.setQueryEntity(ll);
			List<Feature>list=service.getQueryFeature(ll);
			/*for(Feature feature:list){
				System.out.println(feature.getQuery().getName()+" "+feature.getRelation().getName()+" "+feature.getTarget().getName());
			}*/
			res.setQueryFeatureList(service.getQueryFeature(ll));
		}
		return res;
	}
	@ResponseBody
	@RequestMapping(value="getSimEntity",method=RequestMethod.POST)
	public SimEntity getSimEntityResult(@RequestBody List<String>queryList){
		List ll=service.getQueryEntity(queryList);
		List<Entity>simlist=exservice.getSimEntity(ll);
		List<Entity>categoryList=new ArrayList<Entity>();
		for(Entity entity:simlist){
			categoryList.add(CommonService.getCategory(entity));
		}
		SimEntity sim=new SimEntity();
		sim.setSimCategoryList(categoryList);
		sim.setSimEntityList(simlist);
		return sim;
	}
	@ResponseBody
	@RequestMapping(value="getSimFeature",method=RequestMethod.POST)
	public List<Feature> getSimFeatureResult(@RequestBody List<String>queryList){
		List ll=service.getQueryEntity(queryList);
		List<Entity>simlist=exservice.getSimEntity(ll);
		List<Feature>featureList=exservice.getSimFeature(ll);
		/*for(Feature feature:featureList){
			System.out.println(feature.getRelation().getName()+" "+feature.getTarget().getName());
		}*/
		return featureList;
	}
	@ResponseBody
	@RequestMapping(value="getExAllPath",method=RequestMethod.POST)
	public Visual getExplainAllPath(@RequestBody Map mp){
		List<String>ll=(List<String>)mp.get("tail");
		List<Entity>list=new ArrayList<Entity>();
		String head=(String) mp.get("head");
		Entity headEntity=DataUtil.getEnityByName(head);
		if(ll.size()>0){
			list=service.getQueryEntity(ll);
		}else{
			list.add(headEntity);
			list=exservice.getSimEntity(list);
		}
		/*for(Entity entity:list){
			//System.out.println();
		}*/
		//System.out.println(head);
		//System.out.println(list.size());
		
		//long time=System.currentTimeMillis();
		long time=System.currentTimeMillis();
		Visual vis=exservice.getAllPath(head, list);
		/*for(Node nd:vis.getNodeList()){
			System.out.println(nd.getName()+" "+nd.getCategory());
		}*/
		System.out.println(System.currentTimeMillis()-time);
		//System.out.println(System.currentTimeMillis()-time);
		/*for(Node node :vis.getNodeList()){
			System.out.println(node.getName()+" "+node.getX()+" "+node.getY());
		}
		for(Link link:vis.getLinkList()){
			System.out.println(link.getSource()+" "+link.getTarget());
		}*/
		return vis;
	}
	@ResponseBody
	@RequestMapping(value="getExPath",method=RequestMethod.POST)
	public Visual getExplainPath(@RequestBody Map mp){
		String tail=(String) mp.get("tail");
		String head=(String) mp.get("head");
		/*Entity headEntity=DataUtil.getEnityByName(head);
		Entity tailEntity=DataUtil.getEnityByName(tail);*/
		//long time=System.currentTimeMillis();
		long time=System.currentTimeMillis();
		Visual vis=exservice.getPath(head,tail);
		/*for(Vertex nd:vis.getVertexList()){
			System.out.println(nd.getName()+" "+nd.getX()+" "+nd.getY());
		}*/
		System.out.println(System.currentTimeMillis()-time);
		//System.out.println(System.currentTimeMillis()-time);
		/*for(Node node :vis.getNodeList()){
			System.out.println(node.getName()+" "+node.getX()+" "+node.getY());
		}
		for(Link link:vis.getLinkList()){
			System.out.println(link.getSource()+" "+link.getTarget());
		}*/
		return vis;
	}
	@ResponseBody
	@RequestMapping(value="getMetaPath",method=RequestMethod.POST)
	public List<MetaPath> getMetaPath(@RequestBody List<String>list){
		List<Entity>queryList=service.getQueryEntity(list);
		List<MetaPath> pathList=service.getMetaPath(queryList);
		/*for(MetaPath path:pathList){
			System.out.println(path.getRelation().getName()+" "+path.getCategory().getName()+" "+path.getScore()+" "+path.getType());
		}*/
		return pathList;
	}
}
