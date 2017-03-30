package com.graduate.server.model;

import java.util.ArrayList;
import java.util.List;


public class Result {
	private List<String>  queryEntity ;
	private List<Feature> queryFeatureList ;
	private List<Entity> recomendEntityList ;
	private List<Feature>recomendFeatureList;
	private List<Path>pathList;
	public Result(){
		queryEntity=new ArrayList<String>();
		queryFeatureList=new ArrayList<Feature>();
		recomendEntityList=new ArrayList<Entity>();
		recomendFeatureList=new ArrayList<Feature>();
		pathList=new ArrayList<Path>();
	}
	public List getQueryEntity() {
		return queryEntity;
	}
	public void setQueryEntity(List queryEntity) {
		this.queryEntity = queryEntity;
	}
	public List<Feature> getQueryFeatureList() {
		return queryFeatureList;
	}
	public void setQueryFeatureList(List<Feature> queryFeatureList) {
		this.queryFeatureList = queryFeatureList;
	}
	public List<Entity> getRecomendEntityList() {
		return recomendEntityList;
	}
	public void setRecomendEntityList(List<Entity> recomendEntityList) {
		this.recomendEntityList = recomendEntityList;
	}
	public List<Feature> getRecomendFeatureList() {
		return recomendFeatureList;
	}
	public void setRecomendFeatureList(List<Feature> recomendFeatureList) {
		this.recomendFeatureList = recomendFeatureList;
	}
	public List<Path> getPathList() {
		return pathList;
	}
	public void setPathList(List<Path> pathList) {
		this.pathList = pathList;
	}
	
}
