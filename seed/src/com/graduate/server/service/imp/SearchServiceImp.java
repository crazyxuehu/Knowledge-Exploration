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
import com.graduate.server.model.Relation;
import com.graduate.server.model.RelationSize;
import com.graduate.server.service.SearchService;

@Service
public class SearchServiceImp implements SearchService {

	@Autowired SearchDao dao;
	
	public void getTrainData(String path) {
		System.out.println(System.currentTimeMillis());
		HashMap mp=DataLoad.EntityId;
		System.out.println("EntityId:"+mp.size());
		mp=DataLoad.IdEntity;
		System.out.println("IdEntity:"+mp.size());
		mp=DataLoad.EntityVector;
		System.out.println("EntityVector:"+mp.size());
		mp=DataLoad.IdRelation;
		System.out.println("IdRelation:"+mp.size());
		mp=DataLoad.RelationId;
		System.out.println("RelationId:"+mp.size());
		mp=DataLoad.RelationVector;
		System.out.println("RelationVector:"+mp.size());
		mp=DataLoad.tripleHash;
		System.out.println("TripleHash:"+mp.size());
		System.out.println(System.currentTimeMillis());
	}

	@Override//获取查询实体
	public List getQueryEntity(List<String>list) {
		List<Entity>ll=list.parallelStream()
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

	
	
	
	
}
