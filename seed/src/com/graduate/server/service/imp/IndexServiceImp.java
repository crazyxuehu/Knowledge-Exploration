package com.graduate.server.service.imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.graduate.server.dao.SearchDao;
import com.graduate.server.service.IndexService;

@Service
public class IndexServiceImp implements IndexService{
	@Autowired SearchDao dao;
	
	public List getHistory(){
		return dao.getPopular();
	}
}
