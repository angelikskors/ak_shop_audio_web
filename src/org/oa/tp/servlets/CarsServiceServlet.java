package org.oa.tp.servlets;


import com.google.gson.Gson;
import org.oa.tp.dao.DaoFacade;
import org.oa.tp.data.Car;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class CarsServiceServlet extends HttpServlet {

    private static final String PAREMETR_METHOD = "method";
    private static final String PAREMETR_ID = "id";
    private static final String PAREMETR_BRAND = "brand";

    private static final String GET_ALL_METHOD = "get";
    private static final String CREATE_METHOD = "create";
    private static final String DELETE_METHOD = "delete";
    private static final String UPDATE_METHOD = "update";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        final String queryMethod = request.getParameter(PAREMETR_METHOD);

        System.out.println("method " + queryMethod);
        response.setContentType("application/json;charset=UTF-8");

        DaoFacade facade = new DaoFacade();
        if (GET_ALL_METHOD.equalsIgnoreCase(queryMethod)) {
            List<Car> cars = facade.getCarDao().loadAll();
            try (PrintWriter out = response.getWriter()) {
                Gson gson = new Gson();
                gson.toJson(cars, out);
            }
        } else if (CREATE_METHOD.equalsIgnoreCase(queryMethod)) {
            String idString=request.getParameter(PAREMETR_ID);
            int id = Integer.parseInt(idString);
            String brandString = request.getParameter(PAREMETR_BRAND);

            Car car = new Car(id, brandString);
            boolean created = facade.getCarDao().add(car);
            try (PrintWriter out = response.getWriter()) {
                if (created) {
                    response.setStatus(HttpServletResponse.SC_OK);
                    out.print("{\"response\":\"Album created\"}");
                } else {
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    out.print("{\"error\":\"Failed create album\"}");
                }
            }
        } else if (UPDATE_METHOD.equalsIgnoreCase(queryMethod)) {
            String idString=request.getParameter(PAREMETR_ID);
            int id = Integer.parseInt(idString);
            String brandString = request.getParameter(PAREMETR_BRAND);

            Car car = new Car(id, brandString);
            boolean updated = facade.getCarDao().update(car);
            try (PrintWriter out = response.getWriter()) {
                if (updated) {
                    response.setStatus(HttpServletResponse.SC_OK);
                    out.print("{\"response\":\"Album updated\"}");
                } else {
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    out.print("{\"error\":\"Failed update album\"}");
                }
            }
        } else if (DELETE_METHOD.equalsIgnoreCase(queryMethod)) {
            String idString = request.getParameter(PAREMETR_ID);
            long id = Long.parseLong(idString);
            boolean deleted = facade.getCarDao().delete(id);
            try (PrintWriter out = response.getWriter()) {
                if (deleted) {
                    response.setStatus(HttpServletResponse.SC_OK);
                    out.print("{\"response\":\"Album deleted\"}");
                } else {
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    out.print("{\"error\":\"Failed delete album\"}");
                }
            }
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            try (PrintWriter out = response.getWriter()) {
                out.print("{\"error\":\"Not found method\"}");
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        final String queryString = request.getQueryString();
        System.out.println("query string " + queryString);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
