package com.graduate.server.model;

import java.util.ArrayList;

public class Path {
	private Entity head;
	private ArrayList<Relation> relationList;
	private ArrayList<Entity> entityList;
	private double score;
	public Path(){
		relationList = new ArrayList<>();
		entityList = new ArrayList<>();
		score = 0;
	}
	public Entity getHead() {
		return head;
	}
	public void setHead(Entity head) {
		this.head = head;
	}
	public ArrayList<Relation> getRelationList() {
		return relationList;
	}
	public void setRelationList(ArrayList<Relation> relationList) {
		this.relationList = relationList;
	}
	public ArrayList<Entity> getEntityList() {
		return entityList;
	}
	public void setEntityList(ArrayList<Entity> entityList) {
		this.entityList = entityList;
	}
	public double getScore() {
		return score;
	}
	public void setScore(double score) {
		this.score = score;
	}
	
}
