package com.petmily.customer.dao;

import java.io.StringBufferInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.petmily.customer.dto.ApplyDTO;
import com.petmily.customer.dto.PagingDTO;
import com.petmily.customer.dto.PostingDTO;
import com.petmily.customer.dto.UserDTO;

public class ApplyDAO {

	DataSource dataSource;
	Connection connection = null;
	PreparedStatement preparedStatement = null;
	ResultSet resultSet = null;

	public ApplyDAO() {
		try {
			Context context = new InitialContext();
			dataSource = (DataSource) context.lookup("java:comp/env/jdbc/petmily");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int applyListRow(String user_uid) {
		int totalRow = 0;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;// 검색

		try {
			connection = dataSource.getConnection();
			String query = "select count(*) from apply where posting_user_uid = '" + user_uid + "' ";
			String query2 = "and apcanceldate is null and apmatchingdate is null and apcompletedate is null";
			preparedStatement = connection.prepareStatement(query + query2);
			resultSet = preparedStatement.executeQuery();
			// 한개일때는 if, 여러개일때는while

			if (resultSet.next() == true) {
				totalRow = resultSet.getInt(1);
			}
			return totalRow;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (resultSet != null) {
					resultSet.close();
				}
				if (preparedStatement != null) {
					preparedStatement.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return 0;
	}
	
	public PagingDTO applyListPaging(int cPage, int totalRows, int pageLength) {

		int currentBlock = 0;
		int startPage = 0;// 시작 페이지
		int endPage = 0;// 마지막 페이지
		int totalPages = 0;// 총 페이지수
		int startRow = 0;// 시작 로우
		int endRow = 0;// 마지막로우

		totalPages = totalRows % pageLength == 0 ? totalRows / pageLength : (totalRows / pageLength) + 1;

		currentBlock = cPage % pageLength == 0 ? cPage / pageLength : (cPage / pageLength) + 1;
		startPage = (currentBlock - 1) * pageLength + 1;
		endPage = startPage + pageLength - 1;

		startRow = (cPage - 1) * pageLength;
		endRow = totalRows - startRow;

		if (endPage > totalPages) {
			endPage = totalPages;
		}

		PagingDTO dto = new PagingDTO(cPage, currentBlock, pageLength, startPage, endPage, totalPages, startRow,
				endRow);

		return dto;

	}
	
	public ArrayList<ApplyDTO> applyGetList(int cPage, int rowLength,String uid){
		
		ArrayList<ApplyDTO> dtos = new ArrayList<ApplyDTO>();
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;// 검색

		int start = (cPage - 1) * rowLength;

		try {
			connection = dataSource.getConnection();
			
			// apid , aptitle , apcontent , apdate , posting_pid , user_uid
			String query1 = "select apid , aptitle , apcontent, apdate , posting_pid , user_uid from apply ";
			String query2 = "where posting_user_uid = '" + uid + "' ";
			String query3 = "and apcanceldate is null and apmatchingdate is null and apcompletedate is null";
			String query4 = "order by apdate desc limit "+ start + "," + rowLength;
			preparedStatement = connection.prepareStatement(query1 + query2 + query3 + query4);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				int apid = resultSet.getInt(1);
				String aptitle = resultSet.getString(2);
				String apcontent = resultSet.getString(3);
				Timestamp apdate = resultSet.getTimestamp(4);
				int posting_pid = resultSet.getInt(5);
				String user_uid = resultSet.getString(6);

				ApplyDTO dto = new ApplyDTO(apid, aptitle, apcontent, apdate ,user_uid ,posting_pid);
				dtos.add(dto);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (resultSet != null) {
					resultSet.close();
				}
				if (preparedStatement != null) {
					preparedStatement.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return dtos;
		
	}
}
