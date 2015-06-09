package org.oa.tp.servlets;

import org.oa.tp.dao.DaoFacade;

import org.oa.tp.data.Order;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;


public class OrdersViewServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        DaoFacade facade = new DaoFacade();
        List<Order> orders = facade.getOrderDao().loadAll();
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {

            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Orders</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Orders</h1>");
            out.println("<table border=\"1\" style=\"width:100%\">");
            out.println("<tr>");
            out.println("<th>ID</th>");
            out.println("<th>DATE</th>");
            out.println("<th>AUDIO ID</th>");
            out.println("<th>AMOUNT</th>");
            out.println("<th>CUSTOMER ID</th>");
            out.println("<th>DELETE</th>");
            out.println("</tr>");
            for (Order order : orders) {
                out.println("<tr>");
                out.println("<td>" + order.getId() + "</td>");
                out.println("<td>" + order.getDate() + "</td>");
                out.println("<td>" + order.getAudio_id() + "</td>");
                out.println("<td>" + order.getAmount() + "</td>");
                out.println("<td>" + order.getCustomer_id() + "</td>");
                out.println("<td><a href=\"orders?method=delete&id=" + order.getId() + "\">Delete</a></td>");
                out.println("</tr>");
            }
            out.println("</table>");
            out.println("<a href=\"create_order.html\">Create</a>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
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
