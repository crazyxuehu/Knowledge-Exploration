package com.graduate.server.dao;

import java.util.List;

import org.springframework.stereotype.Repository;


public interface SearchDao {

	String getPwd(String name);

	List getResult();

	void loadData(List list);
	void testLobHandler(String entity,String text);

}
