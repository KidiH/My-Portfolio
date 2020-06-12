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
import com.google.appengine.api.datastore.Blob;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Query;
import com.google.gson.Gson;
import com.google.sps.data.TotalCommentProtos.Comment;
import com.google.sps.data.TotalCommentProtos.TotalComments;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/** Servlet that returns some example content. TODO: modify this file to handle comments data */
@WebServlet("/data")
public class DataServlet extends HttpServlet {
  private List<String> comment = new ArrayList<>();
  private static final Logger LOGGER = Logger.getLogger(DataServlet.class.getName());
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
    // Builds comments protobuf and adds all stored comment protobufs to it
    TotalComments.Builder commentsBuilder = TotalComments.newBuilder();
    for (Entity entity : results) {
      Comment commentProto = Comment.parseFrom(((Blob) entity.getProperty("proto")).getBytes());
      commentsBuilder.addComments(commentProto);
    }
    TotalComments newcomments = commentsBuilder.build();
    String json = new Gson().toJson(newcomments);
    response.setContentType("application/json");
    response.getWriter().println(json);
  }
  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    String name = request.getParameter("id-name");
    String text = request.getParameter("text-input");
    // Add user comments to the comment variable
    comment.add(text);
    Comment.Builder newcomment = Comment.newBuilder();
    newcomment.setData(text);
    newcomment.setName(name);
    Comment commentBuilt = newcomment.build();
    // Serialize comment into a Blob
    Blob commentBlob = new Blob(commentBuilt.toByteArray());
    // Create Entity to store comments
    Entity commentEntity = new Entity("Fullcomment");
    commentEntity.setProperty("proto", commentBlob);
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