package com.petmily.customer.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.petmily.customer.dao.*;
import com.petmily.customer.dto.*;

public class MypageSelectCommand implements CustomerCommand {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub

	}
	
	// 나중애 바꿔야됨
	@Override
	public int executeInt(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		
		
		// by 은빈  -- myPageList
		String uid = request.getParameter("uid");
		userDAO dao = new userDAO();
		userDTO dto = dao.myPageView(uid);
		request.setAttribute("uid", dto);
				
		return 0;

	}

}
