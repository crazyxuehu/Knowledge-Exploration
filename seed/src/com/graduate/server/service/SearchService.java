package com.graduate.server.service;

import java.util.List;

import com.graduate.server.model.Entity;
import com.graduate.server.model.Feature;
import com.graduate.server.model.MetaPath;
import com.graduate.server.model.Path;



public interface SearchService {
	public List getQueryEntity(List<String>list);
	public List<Feature>getQueryFeature(List<Entity>list);
	public void saveQuery(List<Entity>list);
	public List<MetaPath>getMetaPathByCategory(List<Entity>queryList);
	public List<MetaPath>getMetaPathByRelation(List<Entity>queryList);
	public List<MetaPath>getMetaPath(List<Entity>queryList);
}
