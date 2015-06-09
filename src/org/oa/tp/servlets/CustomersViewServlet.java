package org.oa.tp.servlets;

import org.oa.tp.dao.DaoFacade;
import org.oa.tp.data.Customer;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;


public class CustomersViewServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        DaoFacade facade = new DaoFacade();
        List<Customer> customers = facade.getCustomerDao().loadAll();
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {

            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Customers</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Customers</h1>");
            out.println("<table border=\"1\" style=\"width:100%\">");
            out.println("<tr>");
            out.println("<th>ID</th>");
            out.println("<th>NAME</th>");
            out.println("<th>PHONE</th>");
            out.println("<th>ADDRESS</th>");
            out.println("<th>RATING</th>");
            out.println("<th>DELETE</th>");
            out.println("</tr>");
            for (Customer customer : customers) {
                out.println("<tr>");
                out.println("<td>" + customer.getId() + "</td>");
                out.println("<td>" + customer.getName() + "</td>");
                out.println("<td>" + customer.getPhone() + "</td>");
                out.println("<td>" + customer.getAddress() + "</td>");
                out.println("<td>" + customer.getRating() + "</td>");
                out.println("<td><a href=\"customers?method=delete&id=" + customer.getId() + "\">Delete</a></td>");
                out.println("</tr>");
            }
            out.println("</table>");
            out.println("<a href=\"create_customer.html\">Create</a>");
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

