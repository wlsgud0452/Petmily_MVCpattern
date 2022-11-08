package com.petmily.customer.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.petmily.customer.command.CustomerCommand;
import com.petmily.customer.command.KakaoTokenCommand;
import com.petmily.customer.command.MypageModifyInsertCommand;
import com.petmily.customer.command.MypageSelectCommand;
import com.petmily.customer.command.loginCommand;
import com.petmily.customer.command.signupCommand;


@WebServlet("*.do")
public class CustomerHomeController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public CustomerHomeController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		actionDo(request,response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		actionDo(request,response);
	}
	
	private void actionDo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		
		String viewpage = null;
		String content_viewpage = null;
		CustomerCommand command = null;
		
		String uri = request.getRequestURI();
		String context = request.getContextPath();
		String com = uri.substring(context.length());
		
		switch(com){


		case ("/sign_up_kakao.do"):
			command = new KakaoTokenCommand();
			command.execute(request, response);
			viewpage = "sign_up_form.jsp";
			content_viewpage = "mypage_modify.jsp";
			request.setAttribute("content_viewpage", content_viewpage);
			break;
		
		//회원정보 수정 페이지에서 수정버튼 눌렀을 때
		case("mypage_modify_update.do"):
			command = new MypageModifyInsertCommand();
			command.execute(request, response);
			viewpage = "modify_update.jsp";
			break;
		//로그인 후 개인정보수정 들어갔을 때
		case("mypage_modify_select.do"):
			command = new MypageSelectCommand();
			command.execute(request, response);
			viewpage = "modify_update.jsp";
			break;
		//로그인 버튼 클릭 시
		case("login.do"):
			command = new loginCommand();
			if(command.executeInt(request, response) == 0) {
				viewpage = "loginTest.jsp";	
				System.out.println("로그인성공");
				//로그인
			}else {
				viewpage = "loginTest.jsp";
				System.out.println("로그인 실패");
				//비로그인
			}
			break;
		//회원가입 화면에서 가입하기 버튼 클릭 시
		case("sign_up.do"):
			command = new signupCommand();
			command.execute(request, response);
			viewpage = "";
			break;
				
		}
		
		RequestDispatcher dispatcher = request.getRequestDispatcher(viewpage);
		dispatcher.forward(request, response);
	}
}
