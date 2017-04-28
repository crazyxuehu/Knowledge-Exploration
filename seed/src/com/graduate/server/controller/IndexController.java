package com.graduate.server.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.graduate.server.model.Entity;
import com.graduate.server.model.Feature;
import com.graduate.server.model.MetaPath;
import com.graduate.server.model.Path;
import com.graduate.server.model.Result;
import com.graduate.server.model.SimEntity;
import com.graduate.server.model.Visual;
import com.graduate.server.service.ExploreService;
import com.graduate.server.service.IndexService;
import com.graduate.server.service.SearchService;
import com.graduate.server.service.imp.CommonService;

@Controller
@RequestMapping("/")
@Scope("prototype")
public class IndexController {
	@Autowired SearchService service;
	@Autowired ExploreService exservice;
	@Autowired IndexService inservice;
	@ResponseBody
	@RequestMapping(value="getIndex",method=RequestMethod.POST)
	public Result getIndex(@RequestBody String user){
		Result res=new Result();
		long time=System.currentTimeMillis();
		List<String> list=inservice.getSearchHistory(4);
		List<Entity> querylist=new ArrayList<Entity>();
		List<Entity>entityList=service.getQueryEntity(list);
		System.out.println(System.currentTimeMillis()-time);
		querylist.add(entityList.get(0));
		//querylist.add(entityList.get(1));
		List<Feature>featureList=service.getQueryFeature(querylist);
		System.out.println(System.currentTimeMillis()-time);
		List<Entity>simList=exservice.getSimEntity(querylist);
		List<Entity>categoryList=new ArrayList<Entity>();
		for(Entity entity:simList){
			categoryList.add(CommonService.getCategory(entity));
		}
		System.out.println(System.currentTimeMillis()-time);
		List<Feature>simFeaturelist=exservice.getSimFeature(simList);
		System.out.println(System.currentTimeMillis()-time);
		List<MetaPath> metaPathList=service.getMetaPath(querylist);
		System.out.println(System.currentTimeMillis()-time);
		entityList.remove(0);
		Visual vis=exservice.getAllPath(querylist.get(0).getName(),entityList);
		System.out.println(System.currentTimeMillis()-time);
		res.setQueryEntity(querylist);
		res.setQueryFeatureList(featureList);
		res.setSimEntityList(new SimEntity(simList,categoryList));
		res.setRecomendFeatureList(simFeaturelist);
		res.setMetaPathList(metaPathList);
		res.setVis(vis);
		System.out.println(System.currentTimeMillis()-time);
		return res;
	}
}
