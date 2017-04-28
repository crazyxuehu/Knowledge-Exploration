package com.graduate.server.model;

public class MetaPath {
	private Entity category;
	private Relation relation;
	private double score;
	private int type;
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public MetaPath(Entity category,Relation relation,double score,int type){
		this.category=category;
		this.relation=relation;
		this.score=score;
		this.type=type;
	}
	public Entity getCategory() {
		return category;
	}
	public void setCategory(Entity category) {
		this.category = category;
	}
	public Relation getRelation() {
		return relation;
	}
	public void setRelation(Relation relation) {
		this.relation = relation;
	}
	public double getScore() {
		return score;
	}
	public void setScore(double score) {
		this.score = score;
	}
}