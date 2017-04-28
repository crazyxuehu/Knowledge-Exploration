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
import com.graduate.server.model.MetaPath;
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
				.filter(word->word!=null)
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
		return featureList.stream()
		.sorted((a,b)->a.getQuery().getName().compareTo(b.getQuery().getName())==0?a.getRelation().getName().compareTo(b.getRelation().getName()):a.getQuery().getName().compareTo(b.getQuery().getName())).collect(Collectors.toList());
	}

	@Override
	public void saveQuery(List<Entity> list) {
		dao.saveQuery(list);	
	}

	@Override
	public List<MetaPath> getMetaPathByCategory(List<Entity> queryList) {
		List<Entity>categoryList=CommonService.getCategory(queryList);
		List<MetaPath>PathList=new ArrayList<MetaPath>();
		int count=0;
		for(Entity category:categoryList){
			count++;
			List<Relation>list=CommonService.getTargetRelation(queryList, category);
			//System.out.println(list.get(0).getScore()+" "+category.getScore());
			PathList.add(new MetaPath(category,list.get(0),list.get(0).getScore()+category.getScore(),1));
		}
		System.out.println("category:"+count);
		return PathList;
	}

	@Override
	public List<MetaPath> getMetaPathByRelation(List<Entity> queryList) {
		List<MetaPath>pathList=new ArrayList<MetaPath>();
		int count=0;
		for(Relation relation:CommonService.getRelationTop(queryList)){
			count++;
			List<Entity>entityList=CommonService.getTarget(queryList, relation);
			List<Entity> categoryList=CommonService.getCategory(entityList);
			//System.out.println(categoryList.get(0).getScore()+" "+relation.getScore());
			pathList.add(new MetaPath(categoryList.get(0),relation,relation.getScore()+categoryList.get(0).getScore(),0));
		}
		System.out.println("relation:"+count);
		return pathList;
	}

	@Override
	public List<MetaPath> getMetaPath(List<Entity> queryList) {
		List<MetaPath>categorList=getMetaPathByCategory(queryList);
		List<MetaPath>relationList=getMetaPathByRelation(queryList);
		List<MetaPath>list=Stream.concat(categorList.stream(),relationList.stream())
		.collect(Collectors.toList());
		list=list.stream().sorted((a,b)->a.getScore()<b.getScore()?1:-1).collect(Collectors.toList());
		return list;
	}
	
	
	
	
	
	
}
