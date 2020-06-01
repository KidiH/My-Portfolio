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
import java.util.ArrayList;
import java.util.List;
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
        "A single stick may smoke, but it will not burn");
      proverbs.add(
        "Do not blame God for having created the tiger, but thank Him for not giving it wings");
      proverbs.add(
        "Fifty lemons are a load for one person, but for fifty persons they are perfume.");
      proverbs.add(
        "It is foolhardy to start a fire just to see the flames.");
  }

  // Function to randomly reference and display the proverbs

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    String proverb = proverbs.get((int) (Math.random() * proverbs.size()));

    response.setContentType("text/html;");
    response.getWriter().println(proverb);
  }
}
   
    