/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labweb1.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import labweb1.models.AccountDAO;
import labweb1.models.AccountDTO;
import labweb1.utils.VerifyCaptcha;

/**
 *
 * @author Gabriel Nguyen
 */
public class LoginServlet extends HttpServlet {
    private final String EMPLOYEE = "search.jsp";
    private final String MANAGER = "manager.jsp";
    private final String LEADER = "search.jsp";
    private final String LOGIN_PAGE = "index.jsp";
    private final String VERIFY_PAGE = "verify.jsp";
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

        String url = LOGIN_PAGE;
        String gRecaptchaResponse = request.getParameter("g-recaptcha-response");
        System.out.println(gRecaptchaResponse);
        boolean valid = VerifyCaptcha.verify(gRecaptchaResponse);
        request.setAttribute("ERROR", "Invalid Captcha");
        url = LOGIN_PAGE;
        try {
            if (valid) {
                String accountName = request.getParameter("txtUsername");
                String password = request.getParameter("txtPassword");

                AccountDAO dao = new AccountDAO();
                AccountDTO account = dao.checkLogin(accountName, password);
               
                if (account.getRole().isEmpty()) {
                    url = LOGIN_PAGE;
                    request.setAttribute("ERROR", "Invalid username or password");
                } else if(account.getStatus().equals("New")){
                   request.setAttribute("USERNAME", accountName);
                    url = VERIFY_PAGE;
                }else{
                    HttpSession session = request.getSession();
                    session.setAttribute("ACCOUNTNAME", account.getName());
                    session.setAttribute("ACCOUNTID", accountName);
                    session.setAttribute("ROLE", account.getRole());
                    if (account.getRole().equals("Employee")) {
                        url = EMPLOYEE;
                    }
                    if (account.getRole().equals("Manager")) {
                        url = MANAGER;
                    }
                    if (account.getRole().equals("Leader")) {
                        url = LEADER;
                    }
                }
            }

        } catch (Exception e) {
            log("Error at LoginServler :" + e.getMessage());
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
