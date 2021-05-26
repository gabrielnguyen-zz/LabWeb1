/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labweb1.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Random;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import labweb1.models.AccountDAO;
import labweb1.models.AccountError;
import labweb1.utils.MailUtils;

/**
 *
 * @author Gabriel Nguyen
 */

public class CreateServlet extends HttpServlet {

    private final String CREATE_ERROR_PAGE = "createNewAccount.jsp";
    private final String MANAGER_PAGE = "manager.jsp";

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
        String username = request.getParameter("txtUser");
        String password = request.getParameter("txtPass");
        String fullName = request.getParameter("txtFullname");
        String phone = request.getParameter("txtPhone");
        String address = request.getParameter("txtAddress");
        String role = request.getParameter("role");
        int roleId = 0;
        String url = CREATE_ERROR_PAGE;
        boolean error = false;
        AccountError errorObj = new AccountError();
        try {
            if (username.trim().length() < 5 || username.trim().length() > 40) {
                error = true;
                errorObj.setUsernameLengthErr("Username length has 5 - 40 chars");
            } else if (!username.trim().matches("^([_a-zA-Z0-9-]+(\\.[_a-zA-Z0-9-]+)*@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*(\\.[a-zA-Z]{1,6}))?$")) {
                error = true;
                errorObj.setUsernameFormatErr("Username format is invalid");
            }
            if (password.trim().length() < 2 || password.trim().length() > 40) {
                error = true;
                errorObj.setPasswordLengthErr("Password length has 5-40 chars");
            }
            if (fullName.trim().length() < 2 || fullName.trim().length() > 40) {
                error = true;
                errorObj.setFullNameLengthErr("Fullname length has 5-40 chars");
            }
            if (!phone.trim().matches("^0(3|7|8|9|1[2|6|8|9])([0-9]{8})$")) {
                error = true;
                errorObj.setPhoneFormatErr("Phone format is not valid");
            }
            if (address.trim().length() < 5 || address.trim().length() > 100) {
                error = true;
                errorObj.setAddressErr("Address length has 5-100 chars");
            }
            if (error == true) {
                request.setAttribute("CREATEERROR", errorObj);
            } else {
                Random rnd = new Random();
                int number = rnd.nextInt(999999);
                String verifyCode = String.format("%06d", number);
                if (role.equals("emp")) {
                    roleId = 2;
                } else if (role.equals("lead")) {
                    roleId = 3;
                }
                AccountDAO dao = new AccountDAO();
                boolean result = dao.createNewAccount(username, password, phone, fullName, address, roleId, verifyCode);
                if (result) {
                    MailUtils mail = new MailUtils();
                    mail.openMailConnection(username, verifyCode);
                    url = MANAGER_PAGE;
                }
            }
        } catch (SQLException ex) {
            log("Create Servlet SQL: " + ex.getMessage());
            if (ex.getMessage().contains("duplicate")) {
                errorObj.setUsernameIsExisted(username + " is Existed !!!");
                request.setAttribute("CREATEERROR", errorObj);
            }
        } catch (NamingException ex) {
            log("Create Servlet Naming : " + ex.getMessage());
        } catch (Exception ex) {
            log("Create Servlet Exception : " + ex.getMessage());
        } finally {
            RequestDispatcher rd = request.getRequestDispatcher(url);
            rd.forward(request, response);
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
