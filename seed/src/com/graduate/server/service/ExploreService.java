package com.graduate.server.service;

import java.util.List;

import com.graduate.server.model.Entity;
import com.graduate.server.model.Feature;
import com.graduate.server.model.Path;

public interface ExploreService {
	public List<Entity>getSimEntity(List<Entity>query);
	List<Feature> getSimFeature(List<Entity> queryList);
	public List<Path>getPath(String head,String tail);
}
