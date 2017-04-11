package com.graduate.server.service.imp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Service;

import com.graduate.server.common.DataLoad;
import com.graduate.server.common.DataUtil;
import com.graduate.server.dao.SearchDao;
import com.graduate.server.model.Entity;
import com.graduate.server.model.Feature;
import com.graduate.server.model.Path;
import com.graduate.server.model.Relation;
import com.graduate.server.model.RelationSize;
import com.graduate.server.service.SearchService;

@Service
public class SearchServiceImp implements SearchService {

	@Autowired SearchDao dao;

	@Override//获取查询实体
	public List getQueryEntity(List<String>list) {
		List<Entity>ll=list.stream()
				.map(word->DataUtil.getEnityByName(word))
				.collect(Collectors.toList());
		return ll;
	}

	@Override//获取查询实体的特征集
	public List<Feature>getQueryFeature(List<Entity> list) {
		List<Feature> featureList=new ArrayList<Feature>();
		int num=0;
		for(Relation relation:CommonService.getRelationTop(list)){
			int id=DataUtil.getRelationId("subject");
			for(Entity entity:CommonService.RankEntity(CommonService.getTarget(list,relation),
			relation.getRelationId()==id?10:DataLoad.Out_Feature_Size)){
				for(Entity query:list){
					if(DataUtil.JuTriple(query, relation, entity)){
						featureList.add(new Feature(query,relation, entity
						, entity.getScore() * relation.getScore()));
					}
				}
			}
		}
		
		//System.out.println(System.currentTimeMillis());
		return featureList;
	}

	@Override
	public void saveQuery(List<Entity> list) {
		dao.saveQuery(list);	
	}

	@Override
	public List<Path> getMetaPathByCategory(List<Entity> queryList) {
		List<Entity>categoryList=CommonService.getCategory(queryList);
		List<Path>PathList=new ArrayList<Path>();
		for(Entity category:categoryList){
			List<Relation>list=CommonService.getTargetRelation(queryList, category);
			PathList.add(new Path(category,list));
		}
		return PathList;
	}

	@Override
	public List<Path> getMetaPathByRelation(List<Entity> queryList) {
		List<Path>pathList=new ArrayList<Path>();
		for(Relation relation:CommonService.getRelationTop(queryList)){
			List<Entity>entityList=CommonService.getTarget(queryList, relation);
			List<Entity> categoryList=CommonService.getCategory(entityList);
			pathList.add(new Path(relation,categoryList));
		}
		return pathList;
	}
	
	
	
	
	
	
}
