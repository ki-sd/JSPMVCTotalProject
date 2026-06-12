package com.sist.dao;
import java.util.*;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.sist.vo.SeoulVO;

import java.io.*;
//<select id="seoulListData" resultType="SeoulVO" parameterType="hashmap">
//SELECT no,title,poster
//FROM ${table}
//ORDER BY no ASC
//OFFSET #{start} ROWS FETCH NEXT 12 ROWS ONLY
//</select>
//<select id="seoulTotalPage" resultType="int" parameterType="hashmap">
//SELECT CEIL(COUNT(*)/12.0)
//FROM ${table}
//</select>
public class SeoulDAO {
	private static SqlSessionFactory ssf;
	static {
		try {
			Reader reader=Resources.getResourceAsReader("config.xml");
			ssf=new SqlSessionFactoryBuilder().build(reader);
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	public static List<SeoulVO> seoulListData(Map map){
		List<SeoulVO> list=new ArrayList<SeoulVO>();
		SqlSession session=null;
		try {
			session=ssf.openSession();
			list=session.selectList("seoulListData",map);
		}catch(Exception ex) {
			ex.printStackTrace();
		}finally {
			if(session!=null) session.close();
		}
		return list;
	}
	public static int seoulTotalPage(Map map) {
		int total=0;
		SqlSession session=null;
		try {
			session=ssf.openSession();
			total=session.selectOne("seoulTotalPage",map);
		}catch(Exception ex) {
			ex.printStackTrace();
		}finally {
			if(session!=null) session.close();
		}
		return total;
	}
	public static SeoulVO seoulDetailData(Map map) {
		SeoulVO vo=new SeoulVO();
		SqlSession session=null;
		try {
			session=ssf.openSession();
			String t=(String)map.get("table");
			if(!t.endsWith("hotel"))
				session.update("hitImplement",map);
			vo=session.selectOne("seoulDetailData",map);
			session.commit();
		}catch(Exception ex) {
			ex.printStackTrace();
		}finally {
			if(session!=null) session.close();
		}
		return vo;
	}
}
