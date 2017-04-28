package com.graduate.server.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.graduate.server.model.Entity;


public interface SearchDao {

	public String getPwd(String name);

	public List getResult();

	public void loadData(List list);
	public void testLobHandler(String entity,String text);

	public void saveQuery(List<Entity> list);
	public List getPopular();

	public List getSearchAll(int num);
}
