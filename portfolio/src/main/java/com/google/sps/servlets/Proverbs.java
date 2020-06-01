// Stores hardcoded Proverbs. Serves to be a point of retrival of data. 

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

import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** Servlet that returns a random proverb. */
@WebServlet("/random-proverb")
public final class RandomProverbServlet extends HttpServlet {
    private List<String> proverbs;

// Hardcoded list of the random proverbs
    @Override
    public void init() {
      proverbs = new ArrayList<>();
      proverbs.add(
        "Regret, like a tail, comes at the end.");
      proverbs.add(
        "A belt fastened while running will come undone while running.");
      proverbs.add(
        "As the wound inflames the finger, so the thought inflames the mind.");
      proverbs.add(
        "When webs of a spider join together, they can trap a lion.");
  }

  // Function to randomly reference and display the proverbs

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    String proverb = proverbs.get((int) (Math.random() * proverbs.size()));

    response.setContentType("text/html;");
    response.getWriter().println(proverb);
  }
}
   
    