package com.petmily.customer.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.petmily.customer.dao.PostingDAO;
import com.petmily.customer.dao.ShowDAO;
import com.petmily.customer.dao.UserDAO;
import com.petmily.customer.dto.PostingDTO;
import com.petmily.customer.dto.UserDTO;

public class PostingClickCommand implements CustomerCommand {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		PostingDAO pdao = new PostingDAO();
		ShowDAO sdao = new ShowDAO();
		UserDAO udao = new UserDAO();
		HttpSession session = request.getSession();
		
		UserDTO udto = (UserDTO)session.getAttribute("user");
		
		int view = 0;
		int like = 0;
		int likeCheck = 0;
		int viewCheck=0;
		int pid = Integer.parseInt(request.getParameter("pid"));
		String postingUid = "";
		String postingUserNickname = "";
		String uimage = "";
		String user_uid = udto.getUid();
		
		PostingDTO pdto = pdao.postingGetDetail(pid); 
		
		postingUid = pdto.getUser_uid();
		
		viewCheck = sdao.showViewCount(pid, user_uid);
		likeCheck = sdao.showLikeCount(pid, user_uid);
		
		uimage = udao.selectImage(postingUid);
		
		if( viewCheck == 0 ) {
			sdao.showInsertView(pid, user_uid);
		}
		
		view = sdao.showViewAllCount(pid);
		like = sdao.showLikeAllCount(pid);
		
		request.setAttribute("pid", pid);
		request.setAttribute("postingView", view);
		request.setAttribute("postingLike", like);
		request.setAttribute("likeCheck", likeCheck);
		request.setAttribute("postingDetail", pdto);
		request.setAttribute("postingUid", postingUid);
		request.setAttribute("postingUimage", uimage);
		
		
	}

	@Override
	public int executeInt(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		return 0;
	}

}
