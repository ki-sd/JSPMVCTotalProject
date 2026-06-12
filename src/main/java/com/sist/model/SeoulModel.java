package com.sist.model;

import java.util.*;
import com.sist.dao.*;
import com.sist.vo.*;
import com.sist.controller.Controller;
import com.sist.controller.RequestMapping;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class SeoulModel {
	private String[] table= {
			"",
			"seoul_location",
			"seoul_nature",
			"seoul_shop",
			"seoul_hotel"
	};
	private String[] title= {
			"",
			"서울 명소",
			"서울 자연",
			"서울 쇼핑",
			"서울 호텔"
	};
	@RequestMapping("seoul/list.do")
	public String seoulList(HttpServletRequest request, HttpServletResponse response) {
		
		String strtype=request.getParameter("type");
		if(strtype==null) 
			strtype="1";
		int type=Integer.parseInt(strtype);
		String strpage=request.getParameter("page");
		if(strpage==null)
			strpage="1";
		int curpage=Integer.parseInt(strpage);
		String start=Integer.toString((curpage*12)-12);
		
		Map<String,String> map=new HashMap<String, String>();
		map.put("table", table[type]);
		map.put("start", start);
		int totalpage=SeoulDAO.seoulTotalPage(map);
		List<SeoulVO> list=SeoulDAO.seoulListData(map);
		
		final int BLOCK=10;
		int startPage=((curpage-1)/BLOCK*BLOCK)+1;
		int endPage=((curpage-1)/BLOCK*BLOCK)+BLOCK;
		
		if(endPage>totalpage)
			endPage=totalpage;
		
		request.setAttribute("list", list);
		request.setAttribute("totalpage", totalpage);
		request.setAttribute("curpage", curpage);
		request.setAttribute("endPage",endPage);
		request.setAttribute("startPage",startPage);
		request.setAttribute("type", type);
		request.setAttribute("title", title[type]);
		
		return "../seoul/list.jsp";
	}
	@RequestMapping("seoul/detail.do")
	public String seoulDetail(HttpServletRequest request, HttpServletResponse response) {
		String strtype=request.getParameter("type");
		if(strtype==null) 
			strtype="1";
		int type=Integer.parseInt(strtype);
		String no=request.getParameter("no");
		
		Map<String,String> map=new HashMap<String, String>();
		map.put("table", table[type]);
		map.put("no", no);
		
		SeoulVO vo=SeoulDAO.seoulDetailData(map);
		
		request.setAttribute("vo", vo);
		request.setAttribute("type", type);
		
		return "../seoul/detail.jsp";
	}
}
