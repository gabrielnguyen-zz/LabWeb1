/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labweb1.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Gabriel Nguyen
 */
public class DispatchServlet extends HttpServlet {

    private final String LOGIN_PAGE = "login.jsp";
    private final String loginServlet = "LoginServlet";
    private final String searchServlet = "SearchServlet";
    private final String createServlet = "CreateServlet";
    private final String verifyServlet = "VerifyServlet";
    private final String bookingServlet = "BookingServlet";
    private final String deleteServlet = "DeleteHistoryServlet";
    private final String acceptProcessServlet = "AcceptProcessServlet";
    private final String deleteProcessServlet = "DeleteProcessServlet";
    private final String searchProcessServlet = "SearchProcessServlet";
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
        try {
            String action = request.getParameter("btAction");
            String url = LOGIN_PAGE;
            if(action == null){
                //do nothing
            }else if(action.equals("Login")){
                url = loginServlet;
            }else if(action.equals("Search")){
                url = searchServlet;
            }else if(action.equals("Create New Account")){
                url = createServlet;
            }else if(action.equals("Verify")){
                url = verifyServlet;
            }else if(action.equals("Booking")){
                url = bookingServlet;
            }else if(action.equals("Delete")){
                url = deleteServlet;
            }else if(action.equals("Accept Process")){
                url= acceptProcessServlet;
            }else if(action.equals("Delete Process")){
                url = deleteProcessServlet;
            }else if(action.equals("Search Process")){
                url = searchProcessServlet;
            }
                 
            
            RequestDispatcher rd = request.getRequestDispatcher(url);
            rd.forward(request, response);
        } finally {
            out.close();
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
