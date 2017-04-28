package com.graduate.server.service;

import java.util.List;

public interface IndexService {
	public List getHistory();
	public List getSearchHistory(int num);
	public List<String>autoComplete(String query,int k);
}
