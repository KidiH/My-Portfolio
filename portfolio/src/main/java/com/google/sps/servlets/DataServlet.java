// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
package com.google.sps.servlets;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Query;
import com.google.gson.Gson;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/** Servlet that returns some example content. TODO: modify this file to handle comments data */
@WebServlet("/data")
public class DataServlet extends HttpServlet {
  private List<String> comment = new ArrayList<>();
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    int max = maxComments(request);
    if (max <= 0) {
      response.setContentType("text/html");
      response.getWriter().println("Please enter a valid Integer");
      return;
    }
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    Query query = new Query("Fullcomment");
    List<Entity> results = datastore.prepare(query).asList(FetchOptions.Builder.withLimit(max));
    List<String> comments = new ArrayList<String>();
    for (Entity entity : results) {
      String remark = (String) entity.getProperty("comment");
      comments.add(remark);
    }
    String json = new Gson().toJson(comments);
    response.setContentType("application/json");
    response.getWriter().println(json);
  }
  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    String text = request.getParameter("text-input");
    // Add user comments to the comment variable
    comment.add(text);
    // Create Entity to store comments
    Entity commentEntity = new Entity("Fullcomment");
    commentEntity.setProperty("comment", text);
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    datastore.put(commentEntity);
    // Redirect to the same page
    response.sendRedirect("/blog.html");
    response.getWriter().println(comment);
  }
  private int maxComments(HttpServletRequest request) {
    String maxString = request.getParameter("max");
    try {
      return Integer.parseInt(maxString);
    } catch (NumberFormatException e) {
      System.err.println("Could not convert to integer");
      return -1;
    }
  }
}