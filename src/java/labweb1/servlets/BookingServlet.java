/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labweb1.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import labweb1.models.AccountDAO;
import labweb1.utils.TextUtils;

/**
 *
 * @author Gabriel Nguyen
 */
public class BookingServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String lastSearch = request.getParameter("lastSearch");
        String lastFrom = request.getParameter("lastFrom");
        String lastEnd = request.getParameter("lastEnd");
        HttpSession session = request.getSession();
        String lastCate = session.getAttribute("CATEGORY").toString();
        String url = "SearchServlet?txtSearchValue=" + lastSearch + "&txtFrom=" + lastFrom + "&txtEnd=" + lastEnd + "&category=" + lastCate;
        try {
            TextUtils textUtil = new TextUtils();
            String quantity = request.getParameter("txtQuantity");
            String[] bookingList = request.getParameterValues("chkBooking");
            if (quantity != null) {
                
                boolean quantityCheck = false;
                for (String temp : bookingList) {
                    String realQuantity = temp.split(",")[0];
                    if (Integer.parseInt(realQuantity) < Integer.parseInt(quantity)) {
                        quantityCheck = true;
                    }
                }
                if (quantityCheck) {
                    request.setAttribute("BOOKINGERROR", "Quantity is higher the current quantity that inventory has");
                } else {
                    if (bookingList != null) {
                        // Xử lí tên resource
//                String quantity = temp.split(",")[0];
//                    String date = temp.split(",")[1];
//                    String name = temp.split(",")[2];
//                    String color = temp.split(",")[3];
                        String nameCheck = bookingList[0].split(",")[2];
                        boolean nameChecking = false;
                        for (String temp : bookingList) {
                            String name = temp.split(",")[2];
                            if (!nameCheck.equals(name)) {
                                nameChecking = true;
                            }
                        }
                        if (nameChecking) {
                            request.setAttribute("BOOKINGERROR", "Selected resources dont have a same type!! Check again");
                        } else {
                            String colorCheck = bookingList[0].split(",")[3];
                            boolean colorChecking = false;
                            for (String temp : bookingList) {
                                String color = temp.split(",")[3];
                                if (!colorCheck.equals(color)) {
                                    colorChecking = true;
                                }
                            }
                            if (colorChecking) {
                                request.setAttribute("BOOKINGERROR", "Selected resources dont have a same type!! Check again");
                            } else {
                                SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
                                boolean dateCheck = false;
                                for (int i = 0; i < bookingList.length; i++) {
                                    if (i < bookingList.length - 1) {
                                        Date d1 = (Date) format.parse(textUtil.convertDate2(bookingList[i].split(",")[1]));
                                        Date d2 = (Date) format.parse(textUtil.convertDate2(bookingList[i + 1].split(",")[1]));
                                        int diff = d2.getDate() - d1.getDate();
                                        if (diff != 1) {
                                            dateCheck = true;
                                        }
                                    }
                                }
                                if (!dateCheck) {
                                    AccountDAO dao = new AccountDAO();
                                    int id = dao.getResoureDTO(nameCheck, colorCheck);
                                    String username = session.getAttribute("ACCOUNTID").toString();
                                    String from = bookingList[0].split(",")[1];
                                    String end = bookingList[bookingList.length - 1].split(",")[1];
                                    
                                    boolean check = dao.booking(username, id, from, end, Integer.parseInt(quantity));
                                    if (check) {
                                        request.setAttribute("BOOKINGERROR", "Booking success!!!");
                                    }
                                    
                                } else {
                                    request.setAttribute("BOOKINGERROR", "Selected days is not in order");
                                }
                            }
                        }
                    }
                }
            }
            
        } catch (Exception e) {
            log("Booking Servlet Exception : " + e);
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
        }
        
    }

// <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
