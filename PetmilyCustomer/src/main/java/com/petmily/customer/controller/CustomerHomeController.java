package com.petmily.customer.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.petmily.customer.command.AddressSearchCommand;
import com.petmily.customer.command.ChallengeVideoCommand;
import com.petmily.customer.command.ChattingCommand;
import com.petmily.customer.command.CustomerCommand;
import com.petmily.customer.command.HomeSlide1Command;
import com.petmily.customer.command.HomeSlide2Command;
import com.petmily.customer.command.HomeSlide3Command;
import com.petmily.customer.command.KakaoTokenCommand;
import com.petmily.customer.command.LectureCheckCommand;
import com.petmily.customer.command.LoginCommand;
import com.petmily.customer.command.LogoutCommand;
import com.petmily.customer.command.MypageAcceptListCommand;
import com.petmily.customer.command.MypageApplyDeleteCommand;
import com.petmily.customer.command.MypageApplyListCommand;
import com.petmily.customer.command.MypageApplyUpdateCommand;
import com.petmily.customer.command.MypageChallengeCommand;
import com.petmily.customer.command.MypageCompleteUpdateCommand;
import com.petmily.customer.command.MypageModifyLoginCommand;
import com.petmily.customer.command.MypageModifyUpdateCommand;
import com.petmily.customer.command.MypageParticipateListCommand;
import com.petmily.customer.command.MypageWriteListCommand;
import com.petmily.customer.command.PetDictinaryDetailCommand;
import com.petmily.customer.command.PetDictionaryCardCommand;
import com.petmily.customer.command.PostingClickCommand;
import com.petmily.customer.command.PostingCommand;
import com.petmily.customer.command.PostingLikeClickCommand;
import com.petmily.customer.command.PostingQueryCommand;
import com.petmily.customer.command.PostingReplyInsertCommand;
import com.petmily.customer.command.PostingWriteInsertCommand;
import com.petmily.customer.command.SignupCommand;
import com.petmily.customer.command.postingApplyInsertCommand;
import com.petmily.customer.command.postingDeleteCommand;
import com.petmily.customer.command.postingInfoCommand;
import com.petmily.customer.command.postingUpdateCommand;


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
		
		int result = 0;
		String viewpage = null;
		String content_viewpage = null;
		CustomerCommand command = null;
		
		String uri = request.getRequestURI();
		String context = request.getContextPath();
		String com = uri.substring(context.length());
		
		
	switch(com){
	// 홈페이지 관련 do
		//홈페이지로 이동
		case("/home.do"):
			command = new HomeSlide1Command();
			command.execute(request, response);
			command = new HomeSlide2Command();
			command.execute(request, response);
			command = new HomeSlide3Command();
			command.execute(request, response);
			viewpage = "home.jsp";
			break;			
			
			
	// 로그인 관련 do 
		//로그인화면으로 이동
		case("/login_page.do"):
			viewpage = "login.jsp";
		break;
		//로그인 버튼 클릭 시
		case("/login.do"):
			command = new LoginCommand();
			result = command.executeInt(request, response);
			if(result == 0) {
				viewpage = "home.do";
				request.setAttribute("message", "로그인 되었습니다.");
				//로그인
			}else {
				viewpage = "login_page.do";
				//비로그인
			}
			break;
		case("/logout.do"):
			command = new LogoutCommand();
			command.execute(request, response);
			viewpage = "home.do";
			break;
		
			
	// 회원 가입 관련 do
		//회원가입 유형 선택 화면으로 이동
		case("/signup_page.do"):
			viewpage = "signup.jsp";
			break;
		// 회원가입 양식 화면으로 이동
		case("/signup_form.do"):
			viewpage = "signup_page.jsp";
			break;
		//카카오로 회원 가입 버튼 클릭 시 
		case ("/sign_up_kakao.do"):
			command = new KakaoTokenCommand();
			command.execute(request, response);
			viewpage = "signup_form.do";
			break;
		//회원가입 화면에서 가입하기 버튼 클릭 시
		case("/sign_up.do"):
			command = new SignupCommand();
			result = command.executeInt(request, response);
			if(result == 0) {
				viewpage = "login.jsp";	
			}else {
				viewpage = "signup.jsp";
			}
			break;
		case("/address_search.do"):
			command = new AddressSearchCommand();
			viewpage = "";
			break;
			
	// 마이 페이지 관련 do
		//헤더에서 마이페이지 클릭시 기본화면 
		case("/mypage_modify.do"):
			viewpage = "mypage_modify_login.jsp";
		break;
		//마이페이지에서 비밀번호 확인시
		case("/mypage_modify_login.do"):
			command = new MypageModifyLoginCommand();
			result = command.executeInt(request, response);
			if(result == 1) {
				viewpage = "mypage_modify_update.do";
			}else {
				viewpage = "mypage_modify_login.jsp";
			}
			break;
		//마이페이지 개인정보수정화면
		case("/mypage_modify_update.do"):
			command = new MypageModifyUpdateCommand();
			command.execute(request, response);
			viewpage = "mypage_modify.jsp";
			break;
		//마이페이지에서 챌린지 클릭했을 때
		case("/mypage_challenge.do"):
			command = new MypageChallengeCommand();
			command.execute(request, response);
			viewpage = "mypage_challenge.jsp";
			break;	
		//마이페이지에서 사이트참여내역 클릭시
		case("/mypage_participate_list.do"):
			command = new MypageParticipateListCommand();
			command.execute(request, response);
			viewpage = "mypage_participate_list.jsp";
			break;
		//마이페이지에서 작성내역 클릭시 및 검색시
		case("/mypage_write_list.do"):
			command = new MypageWriteListCommand();
			command.execute(request, response);
			viewpage = "mypage_write_list.jsp";
			break;
		//마이페이지에서 신청내역 클릭시
		case("/mypage_apply_list.do"):
			command = new MypageApplyListCommand();
			command.execute(request, response);
			viewpage = "mypage_apply_list.jsp";
			break;		
		//신청내역에서 승락 버튼 누를 시 
		case("/apply_update.do"):
			command = new MypageApplyUpdateCommand();
			command.execute(request, response);
			viewpage = "mypage_apply_list.do";
			break;
		//신청내역에서 거절 버튼 누를 시 
		case("/apply_delete.do"):
			command = new MypageApplyDeleteCommand();
			command.execute(request, response);
			viewpage = "mypage_apply_list.do";
			break;	
		//마이페이지에서 승락내역 누를 시
		case("/mypage_accept_list.do"):
			command = new MypageAcceptListCommand();
			command.execute(request, response);
			viewpage = "mypage_accept_list.jsp";
			break;
		//승락내역에서 완료 누를 시
		case("/mypage_accept_complete_list.do"):
			command = new MypageCompleteUpdateCommand();
			command.execute(request, response);
			viewpage = "mypage_accept_list.do";
			break;
	
	// 게시판 관련 do
		//모든게시판 접속시 테이블보여주기 및 검색시
		case("/posting.do"):
			command = new PostingCommand();
			command.execute(request, response);
			viewpage = "board_list.jsp";
			break;
		//모든 게시판에서 검색 버튼 누를 시
		case("/posting_query.do"):
			command = new PostingQueryCommand();
			command.execute(request, response);
			viewpage = "board_list.jsp";
			break;
		//모든 게시판에서 작성 버튼 누를 시 
		case("/posting_write.do"):
			viewpage = "board_write.jsp";
			break;
		//게시판 작성에서 등록 버튼 누를 시
		case("/posting_write_inesrt.do"):
			command = new PostingWriteInsertCommand();
			result = command.executeInt(request, response);
			if(result == 1) {
				viewpage = "posting.do";
			}else {
				// 오류 표시
				viewpage = "posting_write.do";
			}
			break;
		//게시판에서 게시물 클릭 했을 떄 
		case("/posting_click.do"):
			command = new PostingClickCommand();
			result = command.executeInt(request, response);
			if(result == 1) {
				viewpage = "board_view.jsp";
				break;
			}else {
				viewpage = "error.do";
				break;
			}

		//게시물에서 좋아요 클릭 시
		case("/posting_like_click.do"):
			command = new PostingLikeClickCommand();
			command.execute(request, response);
			viewpage = "posting_click.do";
			break;
		//게시물에서 댓글 입력시
		case("/posting_reply_insert.do"):
			command = new PostingReplyInsertCommand();
			command.execute(request, response);
			viewpage = "posting_click.do";
			break;
		//신청하기 작성페이지에서 신청하기 버튼 클릭 시
		case("/posting_apply_insert.do"):
			command = new postingApplyInsertCommand();
			command.execute(request, response);
			viewpage = "posting_click.do";
			break;
		//게시물에서 수정하기 클릭시
		case("/posting_modify.do"):
			command = new postingInfoCommand();
			command.execute(request, response);
			viewpage = "board_modify.jsp";
			break;
		//게시물수정하기에서 수정하기 클릭시
		case("/posting_midify_update.do"):
			command = new postingUpdateCommand();
			command.execute(request, response);
			viewpage = "board_modify.do";
			break;
		//게시물에서 삭제하기 클릭시
		case("/posting_delete.do"):
			command = new postingDeleteCommand();
			command.execute(request, response);
			viewpage = "posting.do";
			break;
		

			
	// 펫과사전 관련 do
		//헤더에서 펫과사전 클릭시, 펫과사전 사이드바에서 동물종류 클릭시
		case("/pet_dictionary_card.do"):
			command = new PetDictionaryCardCommand();
			command.execute(request, response);
			viewpage = "pet_dictionary.jsp";
			break;
		// 펫과사전 카드 클릭 시 
		case("/pet_dictionary_detail.do"):
			command = new PetDictinaryDetailCommand();
			command.execute(request, response);
			viewpage = "pet_dictionary.jsp";
			break;
			
	
	// 도전(교육) 관련 do
		//헤더에서 도전하기 클릭시, 챌린지 사이드바에서 챕터 클릭시
		case("/challenge.do"):
			command = new ChallengeVideoCommand();
			viewpage = "challenge.jsp";
			break;
		// 도전에서 완료버튼 클릭 시 
		case("/lecture_check.do"):
			command = new LectureCheckCommand();
			command.execute(request, response);
			viewpage = "challenge.do";
			break;
	
	// 채팅 관련 do
		//헤더에서 채팅버튼 클릭시
		case("/chatting.do"):
			command = new ChattingCommand();
			command.execute(request, response);
			viewpage = "chatting.jsp";
			break;
			//error
		case("/error.do"):
			viewpage = "error.jsp";
			break;
		
				
		}
		
		RequestDispatcher dispatcher = request.getRequestDispatcher(viewpage);
		dispatcher.forward(request, response);
	}
}

