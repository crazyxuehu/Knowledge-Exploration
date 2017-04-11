package com.graduate.server.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.*;

import com.graduate.server.common.DataUtil;

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
import com.graduate.server.model.Path;
import com.graduate.server.model.Relation;
import com.graduate.server.model.Result;
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
	public Result getQueryResult(@RequestBody Map mp){
		long time=System.currentTimeMillis();
		//System.out.println(System.currentTimeMillis());
		Result res=new Result();
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
		}
		System.out.println(System.currentTimeMillis()-time);
		return res;
	}
	
}
