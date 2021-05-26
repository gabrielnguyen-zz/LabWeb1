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
import javax.servlet.RequestDispatcher;
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
public class SearchServlet extends HttpServlet {

    private final static String SEARCH_PAGE = "search.jsp";
    
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
        if (request.getParameter("page") != null) {
            page = Integer.parseInt(request.getParameter("page"));
        }
        String searchValue = request.getParameter("txtSearchValue");
        String cate = request.getParameter("category");
        String from = request.getParameter("txtFrom");
        String end = request.getParameter("txtEnd");
        try {
            if (!from.isEmpty() && !end.isEmpty()) {
                AccountDAO dao = new AccountDAO();
                List<ResourceDTO> result = dao.searchResource(searchValue, cate, from, end);
                List<ResourceDTO> newResult = new ArrayList<>();
                int noOfPages = (int) Math.ceil(result.size() * 1.0 / MyConstants.ITEMPERPAGE);
                int index = 0;
                if (page == 1) {
                    index = 0;
                } else {
                    index = page * MyConstants.ITEMPERPAGE - (page - 1) * MyConstants.ITEMPERPAGE;
                }
                if (result.size() < MyConstants.ITEMPERPAGE) {
                    for (int i = 0; i < result.size(); i++) {
                        newResult.add(result.get(index + i));
                    }
                } else {
                    int amount = result.size() % MyConstants.ITEMPERPAGE;
                    if ( amount == 0 || page == 1) {
                        for (int i = 0; i < MyConstants.ITEMPERPAGE; i++) {
                            newResult.add(result.get(index + i));
                        }
                    }else{
                         for (int i = 0; i < amount ; i++) {
                            newResult.add(result.get(index + i));
                        }
                    }

                }

                request.setAttribute("noOfPages", noOfPages);
                request.setAttribute("RESULTSEARCH", newResult);
                HttpSession session = request.getSession();
                session.setAttribute("CATEGORY", cate);
                request.setAttribute("currentPage", page);
            }
        } catch (Exception e) {
            log("Error at SearchServlet  " + e.getMessage());

        } finally {
            RequestDispatcher rd = request.getRequestDispatcher(SEARCH_PAGE);
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
