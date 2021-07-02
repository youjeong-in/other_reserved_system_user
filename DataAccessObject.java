package user.services.pattern;

import java.sql.SQLException;
import java.util.ArrayList;

import beans.pattern.RestaurantInfo;

class DataAccessObject extends parents.pattern.DataAccessObject {
	
	DataAccessObject() {
		super();
	}

	//사용자 검색조회 결과
	ArrayList<RestaurantInfo> getSearchResult(RestaurantInfo ri) {
		 ArrayList<RestaurantInfo> list = new ArrayList<>(); 
		 String query = "SELECT * FROM REINFO WHERE SWORD LIKE '%' || ? || '%'";
		 
		 try {
			pstmt =connection.prepareStatement(query);
			pstmt.setNString(1, ri.getWord());
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				RestaurantInfo record = new RestaurantInfo();
				record.setReCode(rs.getNString("RECODE"));
				record.setRestaurant(rs.getNString("RESTAURANT"));
				record.setFcCode(rs.getNString("FCCODE"));
				record.setCategory(rs.getNString("CATEGORY"));
				record.setLcCode(rs.getNString("LCCODE"));
				record.setLocation(rs.getNString("LOCATION"));
				record.setMlCode(rs.getNString("MLCODE"));
				record.setMenu(rs.getNString("MENU"));
				record.setPrice(rs.getNString("PRICE"));
				list.add(record);
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	//예약가능날짜 출력 :: 내일부터~오늘날짜에서 7일날짜까지 C로 되어있는 특정가게의 예약날짜 뽑아오기
	ArrayList<String> getreservedDate(RestaurantInfo ri) {
		ArrayList<String> reservedDate = new ArrayList<>();
		
		String query = "SELECT TO_CHAR(BL_DATE,'YYYYMMDD') AS \"RESERVED\" "
				+ "		FROM BL WHERE BL_RECODE = ? AND BL_STATE= 'C' "
				+ "     AND (TO_CHAR(BL_DATE,'YYYYMMDD') > TO_CHAR(SYSDATE,'YYYYMMDD') AND "
				+ "	    TO_CHAR(BL_DATE,'YYYYMMDD') <= TO_CHAR(SYSDATE+7,'YYYYMMDD'))";
		
		try {
			pstmt =connection.prepareStatement(query);
			pstmt.setNString(1, ri.getReCode());
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				reservedDate.add(rs.getNString("RESERVED"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return reservedDate;
	}

	ArrayList<RestaurantInfo> getMenu(RestaurantInfo ri) {
		ArrayList<RestaurantInfo> list = new ArrayList<>();
		
		String query = "SELECT * FROM ML WHERE ML_RECODE = ?";
		
		
		try {
			pstmt =connection.prepareStatement(query);
			pstmt.setNString(1, ri.getReCode());
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				RestaurantInfo menu = new RestaurantInfo();
				menu.setReCode(rs.getNString("ML_RECODE"));
				menu.setMlCode(rs.getNString("ML_CODE"));
				menu.setMenu(rs.getNString("ML_NAME"));
				menu.setPrice(rs.getNString("ML_PRICE"));
				list.add(menu);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

		int insBookingList(RestaurantInfo ri) {
			int result = -1;
			String query =  "INSERT INTO BL(BL_RECODE,BL_MMCODE,BL_DATE,BL_STATE) VALUES (?,?,?,'W')";
			
			try {
				pstmt =connection.prepareStatement(query);
				pstmt.setNString(1, ri.getReCode());
				pstmt.setNString(2, ri.getUserId());
				pstmt.setNString(3, ri.getrDate());
				
				result = pstmt.executeUpdate();
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		return result;
	}

	 int insBookingDetail(RestaurantInfo ri) {
		int result = -1;
		String query = "INSERT INTO BD(BD_BLRECODE,BD_BLMMCODE,BD_BLDATE,BD_MLCODE,BD_QTY)VALUES (?,?,?,?,?)";
		
		 try {
			pstmt = connection.prepareStatement(query);
			pstmt.setNString(1, ri.getReCode());
			pstmt.setNString(2, ri.getUserId());
			pstmt.setNString(3, ri.getrDate());
			pstmt.setNString(4, ri.getMlCode());
			pstmt.setNString(5, ri.getQuantity());
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
	
			e.printStackTrace();
		}
		 
		return result;
	}
	 
	 

	 ArrayList<RestaurantInfo> getBookList(RestaurantInfo ri) {
		 ArrayList<RestaurantInfo> list = new ArrayList<>();
		 String query = "SELECT * FROM BL_LIST WHERE USERID=?";
		 try {
			pstmt = connection.prepareStatement(query);
			pstmt.setNString(1, ri.getUserId());
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				RestaurantInfo booking = new RestaurantInfo();
				booking.setReCode(rs.getNString("RECODE"));
				booking.setRestaurant(rs.getNString("RESTAURANT"));
				booking.setrDate(rs.getNString("RDATE"));
				booking.setState(rs.getNString("RSTATE"));
				booking.setLocation(rs.getNString("LOCATION"));
				list.add(booking);
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
			
			return list;
	}

	 ArrayList<RestaurantInfo> getBookDetail(RestaurantInfo ri) {
		 ArrayList<RestaurantInfo> list = new ArrayList<>();
		String query = "SELECT *FROM BD_LIST WHERE USERID=?";
		
		try {
			pstmt = connection.prepareStatement(query);
			pstmt.setNString(1, ri.getUserId());
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				RestaurantInfo booking  = new RestaurantInfo();
				booking.setReCode(rs.getNString("RECODE"));
				booking.setrDate(rs.getNString("RDATE"));
				booking.setMenu(rs.getNString("MENU"));
				booking.setPrice(rs.getNString("PRICE"));
				booking.setQuantity(rs.getNString("QUANTITY"));
				booking.setAmount(rs.getNString("TOTAL"));
				list.add(booking);
			}
			
		} catch (SQLException e) {

			e.printStackTrace();
		}
		return list;
	}
	
	
}
