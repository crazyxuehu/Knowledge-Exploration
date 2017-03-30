package com.graduate.server.service.imp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.graduate.server.common.DataLoad;
import com.graduate.server.common.DataUtil;
import com.graduate.server.model.Entity;
import com.graduate.server.service.ExploreService;
@Service
public class ExploreServiceImp implements ExploreService{
	//推荐相似实体
		public List<Entity> getSimEntity(List<Entity>list){
			HashMap<Integer,Entity>mp=new HashMap();
			list.parallelStream()
				.map(e->e.getId())
				.forEach(e->{
					if(DataLoad.EntityVector.containsKey(e)){
					double[] vector=DataLoad.EntityVector.get(e);
					DataLoad.EntityVector.entrySet().parallelStream()
							.map(f->DataUtil.getScoreofEntity(vector,f.getValue(),f.getKey()))
							.forEach(f->{if(mp.containsKey(f.getId()))
								mp.get(f.getId()).setScore(mp.get(f.getId()).getScore()+f.getScore());
								else mp.put(f.getId(),f);
							});
					}
					
			});
			return CommonService.RankEntity(new ArrayList<Entity>(mp.values()),DataLoad.Out_Entity_Size*list.size());
		}

}
