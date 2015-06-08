package org.oa.tp.servlets;

import com.google.gson.Gson;
import org.oa.tp.dao.DaoFacade;
import org.oa.tp.data.Audio;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;


public class AudiosServiceServlet extends HttpServlet {

    private static final String PAREMETR_METHOD = "method";
    private static final String PAREMETR_ID = "id";
    private static final String PAREMETR_NAME = "name";
    private static final String PAREMETR_AUTHOR_ID= "author_id";
    private static final String PAREMETR_DURATION= "duration";
    private static final String PAREMETR_PRICE= "price";
    private static final String PAREMETR_GENRE_ID = "genre_id";
    private static final String PAREMETR_ALBUM_ID= "album_id";

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
            List<Audio> audios = facade.getAudioDao().loadAll();
            try (PrintWriter out = response.getWriter()) {
                Gson gson = new Gson();
                gson.toJson(audios, out);
            }
        } else if (CREATE_METHOD.equalsIgnoreCase(queryMethod)) {
            String idString=request.getParameter(PAREMETR_ID);
            int id = Integer.parseInt(idString);
            String nameString = request.getParameter(PAREMETR_NAME);
            String authorIdString = request.getParameter(PAREMETR_AUTHOR_ID);
            int authorId = Integer.parseInt(authorIdString);
            String durationString = request.getParameter(PAREMETR_DURATION);
            int duration=Integer.parseInt(durationString);
            String priceString = request.getParameter(PAREMETR_PRICE);
            double price=Double.parseDouble(priceString);
            String genreIdString = request.getParameter(PAREMETR_GENRE_ID);
            int genreId=Integer.parseInt(genreIdString);
            String albumIdString=request.getParameter(PAREMETR_ALBUM_ID);
            int albumId=Integer.parseInt(albumIdString);

            Audio audio = new Audio(id, nameString,authorId,price,genreId,duration,albumId);
            boolean created = facade.getAudioDao().add(audio);
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
            String nameString = request.getParameter(PAREMETR_NAME);
            String authorIdString = request.getParameter(PAREMETR_AUTHOR_ID);
            int authorId = Integer.parseInt(authorIdString);
            String durationString = request.getParameter(PAREMETR_DURATION);
            int duration=Integer.parseInt(durationString);
            String priceString = request.getParameter(PAREMETR_PRICE);
            double price=Double.parseDouble(priceString);
            String genreIdString = request.getParameter(PAREMETR_GENRE_ID);
            int genreId=Integer.parseInt(genreIdString);
            String albumIdString=request.getParameter(PAREMETR_ALBUM_ID);
            int albumId=Integer.parseInt(albumIdString);

            Audio audio = new Audio(id, nameString,authorId,price,genreId,duration,albumId);
            boolean updated = facade.getAudioDao().update(audio);
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
            boolean deleted = facade.getAudioDao().delete(id);
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
