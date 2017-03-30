package com.graduate.server.controller;

import java.util.ArrayList;
import java.util.List;
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
import com.graduate.server.model.Result;
import com.graduate.server.service.ExploreService;
import com.graduate.server.service.SearchService;

@Controller
@RequestMapping("/")
@Scope("prototype")
public class SearchController {

	@Autowired SearchService service;
	@Autowired ExploreService exservice;
	
	@ResponseBody
	@RequestMapping(value="test",method=RequestMethod.POST)
	public Result getQueryResult(@RequestBody List<String>list){
		long time=System.currentTimeMillis();
		//System.out.println(System.currentTimeMillis());
		Result res=new Result();
		List ll=service.getQueryEntity(list);
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
			System.out.println(entity.getName());
		}
		System.out.println(System.currentTimeMillis()-time);
		return res;
	}
	
}
