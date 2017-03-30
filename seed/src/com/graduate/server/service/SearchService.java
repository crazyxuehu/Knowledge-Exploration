package com.graduate.server.service;

import java.util.List;

import com.graduate.server.model.Entity;
import com.graduate.server.model.Feature;



public interface SearchService {
	public List getQueryEntity(List<String>list);
	public List<Feature>getQueryFeature(List<Entity>list);
}
