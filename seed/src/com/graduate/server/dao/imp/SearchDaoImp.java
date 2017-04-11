package com.graduate.server.dao.imp;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.AbstractLobCreatingPreparedStatementCallback;
import org.springframework.jdbc.support.lob.LobCreator;
import org.springframework.jdbc.support.lob.LobHandler;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.support.lob.DefaultLobHandler;

import com.graduate.server.basedao.BaseDao;
import com.graduate.server.dao.SearchDao;
import com.graduate.server.model.Entity;

@Repository
public class SearchDaoImp extends BaseDao implements SearchDao {

	@Override
	public String getPwd(String name) {
		StringBuffer sql=new StringBuffer();
		sql.append("select paassword from xuehu.seed");
		sql.append(" where name=?");
		return getJdbcTemplate().queryForObject(sql.toString(), String.class, new Object[]{name});
	}

	@Override
	public List getResult() {
		StringBuffer sql=new StringBuffer();
		sql.append(" select * from xuehu.result");
		return getJdbcTemplate().queryForList(sql.toString());
	}

	@Override
	public void loadData(List list) {
		StringBuffer sql=new StringBuffer();
		sql.append("insert into xuehu.RELATIONID values(?,?)");
		for(int i=0;i<list.size();i++){
			String content=(String) list.get(i);
			String[]arg=content.split("	");
			String entity=arg[0];
			int id1=Integer.parseInt(arg[1]);
			getJdbcTemplate().update(sql.toString(),new Object[]{entity,id1});
		}
	}

	@Override
	public void testLobHandler(final String entity,final String text) {
		final LobHandler lobHandler=new DefaultLobHandler();  
		String sql = "insert into xuehu.abstract values(?,?)";
		getJdbcTemplate().execute(sql,new AbstractLobCreatingPreparedStatementCallback(lobHandler){
		@Override
		protected void setValues(PreparedStatement ps, LobCreator lobCreator)
		throws SQLException, DataAccessException {
		ps.setString(1,entity);
			lobCreator.setClobAsString(ps,2,text);
		}
		});
		}

	@Override
	public void saveQuery(List<Entity> list) {
		for(Entity entity:list){
			StringBuffer  sql=new StringBuffer();
			sql.append("insert into xuehu.history values(historyid.nextval,?,?,' ',sysdate)");
			try{
			getJdbcTemplate().update(sql.toString(),new Object[]{entity.getId(),entity.getName()});
			}catch(Exception e){
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
		}
		
	}

	@Override
	public List getPopular() {
		StringBuffer sql=new StringBuffer();
		sql.append("select entityname from(select count(*) as num,entityname from xuehu.history");
		sql.append(" group by entityname order by num desc) where rownum<4");
		List<String>list=getJdbcTemplate().queryForList(sql.toString(),String.class);
		return list;
	}
	
}
