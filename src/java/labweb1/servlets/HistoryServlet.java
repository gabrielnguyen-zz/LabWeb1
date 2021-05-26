/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labweb1.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import labweb1.constants.MyConstants;
import labweb1.models.AccountDAO;
import labweb1.models.ResourceDTO;

/**
 *
 * @author Gabriel Nguyen
 */
public class HistoryServlet extends HttpServlet {
    private final static String HISTORY_PAGE = "bookingHistory.jsp";
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
        int page = 1;
        if (request.getParameter("pageHistory") != null) {
            page = Integer.parseInt(request.getParameter("pageHistory"));
        }
        try {
            AccountDAO dao = new AccountDAO();
            HttpSession session = request.getSession();
            String username = session.getAttribute("ACCOUNTID").toString();
            int noOfRecords = dao.getNoOfHistoryRec(username);
            int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / MyConstants.ITEMPERPAGE);
            List<ResourceDTO> result = dao.getHistoryBooking(username, page);
            request.setAttribute("noOfPagesHistory", noOfPages);
            request.setAttribute("RESULTHISTORY", result);
            request.setAttribute("currentPageHistory", page);
        } catch (Exception e) {
            log("Error at HistoryServlet  " + e.getMessage());
        }
        finally{
             request.getRequestDispatcher(HISTORY_PAGE).forward(request, response);
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
