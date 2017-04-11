package com.graduate.server.model;

import com.graduate.server.common.DataUtil;

public class Relation {
	int direction;
	int type;
	int relationId;
	String name;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	double score;
	public double getScore() {
		return score;
	}
	public void setScore(double score) {
		this.score = score;
	}
	public Relation(int type,int dirction,int relaitonId){
		this.type=type;
		this.direction=dirction;
		this.relationId=relaitonId;
		this.name=DataUtil.getRelationName(relaitonId);
		this.score=1;
	}
	public Relation(int type,int direction){
		this.type=type;
		this.direction=direction;
		this.score=1;
	}
	public Relation(){
		type=1;
		this.score=1;
	}
	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getRelationId() {
		return relationId;
	}

	public void setRelationId(int relationId) {
		this.relationId = relationId;
	}

	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}
	
}
