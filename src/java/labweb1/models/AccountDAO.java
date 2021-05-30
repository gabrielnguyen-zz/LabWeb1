/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labweb1.models;
//893391

import java.io.Serializable;
import java.sql.Array;
import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import javax.naming.NamingException;
import labweb1.constants.MyConstants;
import labweb1.utils.DBUtils;
import labweb1.utils.TextUtils;

/**
 *
 * @author Gabriel Nguyen
 */
public class AccountDAO implements Serializable {

    private Connection con;
    private PreparedStatement pst;
    private ResultSet rs;

    public AccountDAO() {

    }

    private void closeConnection() throws Exception {
        if (rs != null) {
            rs.close();
        }
        if (pst != null) {
            pst.close();
        }
        if (rs != null) {
            rs.close();
        }
    }

    public boolean createNewAccount(String username, String password, String phone, String fullName, String address, int role, String verifyCode) throws SQLException, NamingException, Exception {
        try {
            String sql = "INSERT into Account(userId, password, phone, address, fullname,roleId, verifyCode) values (?,?,?,?,?,?,?)";
            con = DBUtils.makeConnection();
            pst = con.prepareStatement(sql);
            pst.setString(1, username);
            pst.setString(2, password);
            pst.setString(3, phone);
            pst.setString(4, address);
            pst.setString(5, fullName);
            pst.setInt(6, role);
            pst.setString(7, verifyCode);
            int row = pst.executeUpdate();
            if (row > 0) {
                return true;
            }
        } finally {
            closeConnection();
        }
        return false;

    }

    public AccountDTO checkLogin(String accountName, String password) throws Exception {
        AccountDTO dto = new AccountDTO();

        try {
            String sql = "Select name, fullname,status from Role as r , Account as a where a.userId = ? and a.password = ? and a.roleId = r.id";
            con = DBUtils.makeConnection();
            pst = con.prepareStatement(sql);
            pst.setString(1, accountName);
            pst.setString(2, password);
            rs = pst.executeQuery();
            if (rs.next()) {
                String role = rs.getString("name");
                String name = rs.getString("fullname");
                String status = rs.getString("status");
                dto.setName(name);
                dto.setRole(role);
                dto.setStatus(status);
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            closeConnection();
        }
        return dto;
    }

    public AccountDTO checkVerify(String accountName, String verifyCode) throws Exception {
        AccountDTO dto = new AccountDTO();
        try {
            String sql = "Select name, fullname from Role as r , Account as a where a.userId = ? and a.verifyCode = ? and a.roleId = r.id";
            con = DBUtils.makeConnection();
            pst = con.prepareStatement(sql);
            pst.setString(1, accountName);
            pst.setString(2, verifyCode);
            rs = pst.executeQuery();
            if (rs.next()) {
                String role = rs.getString("name");
                String name = rs.getString("fullname");
                String updateSql = "Update Account set status = 'Verify' where userId = ?";
                pst = con.prepareStatement(updateSql);
                pst.setString(1, accountName);
                boolean check = pst.executeUpdate() > 0;
                if (check) {
                    dto.setName(name);
                    dto.setRole(role);
                }
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            closeConnection();
        }
        return dto;
    }

    public AccountDTO checkLoginGoogle(String accountName) throws Exception {
        AccountDTO dto = new AccountDTO();
        try {
            String sql = "Select name, fullname,status from Role as r , Account as a where a.userId = ? and a.roleId = r.id";
            con = DBUtils.makeConnection();
            pst = con.prepareStatement(sql);
            pst.setString(1, accountName);
            rs = pst.executeQuery();
            if (rs.next()) {
                String role = rs.getString("name");
                String name = rs.getString("fullname");
                String status = rs.getString("status");
                dto.setName(name);
                dto.setRole(role);
                dto.setStatus(status);
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            closeConnection();
        }
        return dto;
    }

    public List<ResourceDTO> searchAllResource(String searchValue) {
        List<ResourceDTO> list = new ArrayList();
        try {
            String sql = "Select * from Resource where name like ?";
            con = DBUtils.makeConnection();
            pst = con.prepareStatement(sql);
            pst.setString(1, "%" + searchValue + "%");
            rs = pst.executeQuery();
            while (rs.next()) {
                String name = rs.getString("name");
                String color = rs.getString("color");
                int quantity = rs.getInt("quantity");
                int id = rs.getInt("id");
                ResourceDTO dto = new ResourceDTO(name, color, null, id, quantity);
                list.add(dto);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return list;
    }

    public List<ResourceDTO> searchResource(String searchValue, String cate, String fromDate, String endDate) throws Exception {
        List<ResourceDTO> list = new ArrayList();
        TextUtils textUtils = new TextUtils();
        List<String> dates = new ArrayList();
        List<ResourceDTO> temp = new ArrayList();
        List<ResourceDTO> listResource = new ArrayList();
        listResource = searchAllResource(searchValue);

        try {
            String sql = "Select s.id, r.fromDate, r.endDate, r.quantity as useQuantity, s.name, s.color, s.quantity"
                    + "from Request as r,"
                    + "     Resource as s,"
                    + "     Category as c"
                    + "where c.id = ?"
                    + "  and c.id = s.categoryId"
                    + "  and s.name LIKE ?"
                    + "  and r.resourceId = s.id"
                    + "  and r.status = 'Accept'"
                    + "  and"
                    + "    (r.fromDate >= ?"
                    + "   OR r.endDate <=?) ";
            con = DBUtils.makeConnection();
            pst = con.prepareStatement(sql);
            pst.setString(1, cate);
            pst.setString(2, "%" + searchValue + "%");
            pst.setString(3, fromDate);
            pst.setString(4, endDate);
            rs = pst.executeQuery();
            while (rs.next()) {
                String from = rs.getString("fromDate");
                String end = rs.getString("endDate");
                int useQuantity = rs.getInt("useQuantity");
                String name = rs.getString("name");
                String color = rs.getString("color");
                int quantity = rs.getInt("quantity");
                int id = rs.getInt("id");
                dates.add(from);
                dates.add(end);
                ResourceDTO dto = new ResourceDTO(name, color, id, useQuantity, from, end);
                temp.add(dto);
            }
            if (temp.size() > 0) {
                Collections.sort(dates, new Comparator<String>() {
                    @Override
                    public int compare(String o1, String o2) {
                        try {
                            return o1.compareTo(o2);
                        } catch (Exception e) {
                            throw new IllegalArgumentException(e);
                        }
                    }
                });
                List<String> newDates = dates.stream().distinct().collect(Collectors.toList());
                for (String date : newDates) {
                    List<ResourceDTO> listEveryDay = new ArrayList();
                    listEveryDay = searchAllResource(searchValue);

                    for (int i = 0; i < temp.size(); i++) {
                        String from = temp.get(i).getFrom();
                        String end = temp.get(i).getEnd();
                        if (from.compareTo(date) * date.compareTo(end) >= 0) {
                            for (ResourceDTO dto : listEveryDay) {
                                dto.setFrom(date);
                                dto.setEnd(date);
                                if (dto.getName().equals(temp.get(i).getName())) {
                                    int quantity = dto.getQuantity() - temp.get(i).getQuantity();
                                    dto.setQuantity(quantity);

                                }
                            }
                        }
                    }
                    list.addAll(listEveryDay);
                }
                SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
                for (int i = 0; i < newDates.size(); i++) {
                    if (i < newDates.size() - 1) {
                        Date d1 = (Date) format.parse(textUtils.convertDate2(newDates.get(i)));
                        Date d2 = (Date) format.parse(textUtils.convertDate2(newDates.get(i + 1)));
                        int diff = d2.getDate() - d1.getDate();
                        if (diff >= 2) {
                            LocalDate start = LocalDate.parse(newDates.get(i));
                            LocalDate end = LocalDate.parse(newDates.get(i + 1));
                            List<LocalDate> totalDates = new ArrayList<>();
                            while (!start.isAfter(end) && !start.isEqual(end)) {
                                totalDates.add(start);
                                start = start.plusDays(1);
                            }
                            for (int j = 1; j < totalDates.size(); j++) {
                                for (ResourceDTO resource : listResource) {
                                    int quant = 0;
                                    for (ResourceDTO res : list) {
                                        if (res.getFrom().equals(newDates.get(i))) {
                                            quant = res.getQuantity();
                                        }
                                    }
                                    ResourceDTO newResource = new ResourceDTO(resource.getName(), resource.getColor(), resource.getId(), quant);
                                    newResource.setFrom(totalDates.get(j).toString());
                                    newResource.setEnd(totalDates.get(j).toString());
                                    list.add(newResource);
                                }
                            }
                        }
                    }

                }
                if (fromDate.compareTo(dates.get(0)) > 0) {
                    LocalDate start = LocalDate.parse(textUtils.convertDate(fromDate));
                    LocalDate end = LocalDate.parse(dates.get(0));
                    List<LocalDate> totalDates = new ArrayList<>();
                    while (!start.isAfter(end)) {
                        totalDates.add(start);
                        start = start.plusDays(1);
                    }
                    for (int j = 0; j < totalDates.size() - 1; j++) {
                        for (ResourceDTO resource : listResource) {
                            ResourceDTO newResource = new ResourceDTO(resource.getName(), resource.getColor(), resource.getId(), resource.getQuantity());
                            newResource.setFrom(totalDates.get(j).toString());
                            newResource.setEnd(totalDates.get(j).toString());
                            list.add(newResource);
                        }
                    }
                }
                if (dates.get(dates.size() - 1).compareTo(endDate) < 0) {
                    LocalDate start = LocalDate.parse(dates.get(dates.size() - 1));
                    LocalDate end = LocalDate.parse(textUtils.convertDate(endDate));
                    List<LocalDate> totalDates = new ArrayList<>();
                    while (!start.isAfter(end)) {
                        totalDates.add(start);
                        start = start.plusDays(1);
                    }
                    for (int j = 1; j < totalDates.size(); j++) {
                        for (ResourceDTO resource : listResource) {
                            ResourceDTO newResource = new ResourceDTO(resource.getName(), resource.getColor(), resource.getId(), resource.getQuantity());
                            newResource.setFrom(totalDates.get(j).toString());
                            newResource.setEnd(totalDates.get(j).toString());
                            list.add(newResource);
                        }

                    }
                }
                Collections.sort(list, new Comparator<ResourceDTO>() {
                    @Override
                    public int compare(ResourceDTO o1, ResourceDTO o2) {
                        return o1.getFrom().compareTo(o2.getFrom()); //To change body of generated methods, choose Tools | Templates.
                    }

                });
                Collections.sort(list, new Comparator<ResourceDTO>() {
                    @Override
                    public int compare(ResourceDTO o1, ResourceDTO o2) {
                        return o1.getName().compareTo(o2.getName()); //To change body of generated methods, choose Tools | Templates.
                    }

                });
                List<ResourceDTO> toRemove = new ArrayList<>();
                for (ResourceDTO obj : list) {
                    if (obj.getFrom().compareTo(textUtils.convertDate(endDate)) > 0) {
                        toRemove.add(obj);
                    }
                    if (obj.getFrom().compareTo(textUtils.convertDate(fromDate)) < 0) {
                        toRemove.add(obj);
                    }
                }
                list.removeAll(toRemove);
            } else {
                LocalDate start = LocalDate.parse(textUtils.convertDate(fromDate));
                LocalDate end = LocalDate.parse(textUtils.convertDate(endDate));
                List<LocalDate> totalDates = new ArrayList<>();
                while (!start.isAfter(end)) {
                    totalDates.add(start);
                    start = start.plusDays(1);
                }
                for (LocalDate totalDate : totalDates) {
                    for (ResourceDTO resource : listResource) {
                        resource.setFrom(totalDate.toString());
                        resource.setEnd(totalDate.toString());
                        list.add(resource);
                    }
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            closeConnection();
        }
        return list;
    }

    public int getResoureDTO(String name, String color) throws Exception {

        try {
            String sql = "Select id from Resource where name =? AND color= ?";
            con = DBUtils.makeConnection();
            pst = con.prepareStatement(sql);
            pst.setString(1, name);
            pst.setString(2, color);
            rs = pst.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("id");
                return id;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        } finally {
            closeConnection();
        }
        return 0;
    }

    public boolean booking(String username, int resourceId, String from, String end, int quantity) throws Exception {
        boolean check = false;
        try {
            String sql = "Insert into Request(resourceId, userId, fromDate, endDate , quantity) values(?,?,?,?,?)";
            con = DBUtils.makeConnection();
            pst = con.prepareStatement(sql);
            pst.setInt(1, resourceId);
            pst.setString(2, username);
            pst.setString(3, from);
            pst.setString(4, end);
            pst.setInt(5, quantity);
            int row = pst.executeUpdate();
            if (row > 0) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            closeConnection();
        }
        return check;
    }

    public List<ResourceDTO> getHistoryBooking(String username, int page) throws Exception {
        List<ResourceDTO> list = new ArrayList<>();
        int minIndex = 1;
        int maxIndex = MyConstants.ITEMPERPAGE;
        if (page > 1) {
            minIndex = page * MyConstants.ITEMPERPAGE - (page - 1) * MyConstants.ITEMPERPAGE + 1;
            maxIndex = minIndex + MyConstants.ITEMPERPAGE - 1;
        }
        try {
            String sql = "SELECT*"
                    + "FROM (SELECT r.id,"
                    + "             ROW_NUMBER() OVER(ORDER BY r.id) AS rownumber,"
                    + "             r.status,"
                    + "             r.isActive,"
                    + "             r.requestDate,"
                    + "             r.updatedDate,"
                    + "             r.fromDate,"
                    + "             r.endDate,"
                    + "             r.description,"
                    + "             r.quantity,"
                    + "             a.name as resourceName,"
                    + "             a.color,"
                    + "             c.name as categoryName"
                    + "      from Request as r"
                    + "           inner join Resource a on a.id = r.resourceId"
                    + "           inner join Category C on C.id = a.categoryId"
                    + "      where userId = ?"
                    + "        and r.isActive = 'true') as tmp where tmp.rownumber between ? and ?";
            con = DBUtils.makeConnection();
            pst = con.prepareStatement(sql);
            pst.setString(1, username);
            pst.setInt(2, minIndex);
            pst.setInt(3, maxIndex);
            rs = pst.executeQuery();
            while (rs.next()) {
                String resourceName = rs.getString("resourceName");
                String categoryName = rs.getString("categoryName");
                String from = rs.getString("fromDate");
                String end = rs.getString("endDate");
                String description = rs.getString("description");
                String status = rs.getString("status");
                String requestDate = rs.getString("requestDate");
                String updatedDate = rs.getString("updatedDate");
                String color = rs.getString("color");
                int quantity = rs.getInt("quantity");
                int id = rs.getInt("id");
                ResourceDTO dto = new ResourceDTO(end, color, categoryName, from, end, status, description, requestDate, updatedDate, id, quantity);
                list.add(dto);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            closeConnection();
        }
        return list;
    }

    public int getNoOfHistoryRec(String username) throws Exception {
        int i = 0;
        try {
            String sql = "SELECT r.id,"
                    + "             ROW_NUMBER() OVER(ORDER BY r.id) AS rownumber,"
                    + "             r.status,"
                    + "             r.isActive,"
                    + "             r.requestDate,"
                    + "             r.updatedDate,"
                    + "             r.fromDate,"
                    + "             r.endDate,"
                    + "             r.description,"
                    + "             r.quantity,"
                    + "             a.name as resourceName,"
                    + "             a.color,"
                    + "             c.name as categoryName"
                    + "      from Request as r"
                    + "           inner join Resource a on a.id = r.resourceId"
                    + "           inner join Category C on C.id = a.categoryId"
                    + "      where userId = ?"
                    + "        and r.isActive = 'true'";
            con = DBUtils.makeConnection();
            pst = con.prepareStatement(sql);
            pst.setString(1, username);
            rs = pst.executeQuery();
            while (rs.next()) {
                i++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return i;
    }

    public boolean deleteHistoryBooking(List<String> id) throws Exception {
        boolean check = false;
        try {
            String sql = "UPDATE Request SET isActive = 'false' WHERE ID IN (";
            String tmp = "";
            for (int i = 0; i < id.size(); i++) {
                tmp += ",?";
            }
            tmp = tmp.replaceFirst(",", "");
            tmp += ")";
            sql = sql + tmp;
            con = DBUtils.makeConnection();
            pst = con.prepareStatement(sql);
            for (int i = 0; i < id.size(); i++) {
                pst.setString(i + 1, id.get(i));
            }
            check = pst.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }

        return check;
    }

    public List<ResourceDTO> searchRequest(String value, String status, int page) throws Exception {
        List<ResourceDTO> list = new ArrayList<>();
        try {
            int minIndex = 1;
            int maxIndex = MyConstants.ITEMPERPAGE;
            if (page > 1) {
                minIndex = page * MyConstants.ITEMPERPAGE - (page - 1) * MyConstants.ITEMPERPAGE + 1;
                maxIndex = minIndex + MyConstants.ITEMPERPAGE - 1;
            }
            String sql = "SELECT * FROM ("
                    + "Select "
                    + "ROW_NUMBER() OVER (ORDER BY r.id) AS rownumber,"
                    + "r.id, "
                    + "r.fromDate, "
                    + "r.endDate, "
                    + "r.quantity as useQuantity, "
                    + "s.name, s.color, "
                    + "s.quantity,"
                    + "r.requestDate,"
                    + "r.updatedDate "
                    + "from Request as r,"
                    + "     Resource as s,"
                    + "     Category as c "
                    + "where c.id = s.categoryId"
                    + "  and s.name LIKE ?"
                    + "  and r.resourceId = s.id"
                    + "  and r.status = ?) as tmp where tmp.rownumber between ? and ?";
            con = DBUtils.makeConnection();
            pst = con.prepareStatement(sql);
            pst.setString(1, "%" + value + "%");
            pst.setString(2, status);
            pst.setInt(3, minIndex);
            pst.setInt(4, maxIndex);
            rs = pst.executeQuery();
            while (rs.next()) {
                String from = rs.getString("fromDate");
                String end = rs.getString("endDate");
                int useQuantity = rs.getInt("useQuantity");
                String name = rs.getString("name");
                String color = rs.getString("color");
                int id = rs.getInt("id");
                String requestDate = rs.getString("requestDate");
                String updatedDate = rs.getString("updatedDate");
                ResourceDTO dto = new ResourceDTO(name, color, id, useQuantity, from, end, requestDate, updatedDate);
                list.add(dto);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }

        return list;
    }

    public int getNoOfProcessRec(String searchValue, String status) throws Exception {
        int i = 0;
        try {
            String sql = "Select *"
                    + "from Request as r,"
                    + "     Resource as s,"
                    + "     Category as c "
                    + "where c.id = s.categoryId"
                    + "  and s.name LIKE ?"
                    + "  and r.resourceId = s.id"
                    + "  and r.status = ?";
            con = DBUtils.makeConnection();
            pst = con.prepareStatement(sql);
            pst.setString(1, "%" + searchValue + "%");
            pst.setString(2, status);
            rs = pst.executeQuery();
            while (rs.next()) {
                i++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return i;
    }
    
    public boolean updateRequest (String requestId,String answer, String description) throws Exception{
        boolean check = false;
        try {
            String sql = "UPDATE Request Set status = ? and description = ? where id = ? ";
            con= DBUtils.makeConnection();
            pst = con.prepareStatement(sql);
            pst.setString(1, answer);
            pst.setString(2, description);
            pst.setInt(3, Integer.parseInt(requestId));
            check = pst.executeUpdate() > 1;
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            closeConnection();
        }
        
        return check;
    }
}
