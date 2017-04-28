package com.graduate.server.model;

import java.util.ArrayList;
import java.util.List;


public class Result {
	private List<Entity>  queryEntity ;
	private List<Feature> queryFeatureList ;
	private SimEntity simEntityList ;
	private List<Feature>recomendFeatureList;
	private List<MetaPath> metaPathList;
	private Visual vis;
	public Result(){
		queryEntity=new ArrayList<Entity>();
		queryFeatureList=new ArrayList<Feature>();
		simEntityList=new SimEntity();
		recomendFeatureList=new ArrayList<Feature>();
		metaPathList=new ArrayList<MetaPath>();
	}
	public List<Entity>getQueryEntity() {
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
	public SimEntity getSimEntityList() {
		return simEntityList;
	}
	public void setSimEntityList(SimEntity simEntityList) {
		this.simEntityList = simEntityList;
	}
	public List<Feature> getRecomendFeatureList() {
		return recomendFeatureList;
	}
	public void setRecomendFeatureList(List<Feature> recomendFeatureList) {
		this.recomendFeatureList = recomendFeatureList;
	}
	public List<MetaPath> getMetaPathList() {
		return metaPathList;
	}
	public void setMetaPathList(List<MetaPath> metaPathList) {
		this.metaPathList = metaPathList;
	}
	public Visual getVis() {
		return vis;
	}
	public void setVis(Visual vis) {
		this.vis = vis;
	}

}
