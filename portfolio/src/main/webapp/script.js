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

google.charts.load('current', {'packages':['corechart']});
google.charts.setOnLoadCallback(drawChart);
google.charts.setOnLoadCallback(drawChart1);
google.charts.load("current", {packages:["sankey"]});
google.charts.setOnLoadCallback(drawChart3);



/**
 * Adds a random greeting to the page.
 */
function addRandomGreeting() {
  const greetings =
      ['Regret, like a tail, comes at the end.', 
      'A belt fastened while running will come undone while running.', 
      'As the wound inflames the finger, so the thought inflames the mind.', 
      'When webs of a spider join together, they can trap a lion.'];

  // Pick a random greeting.
  const greeting = greetings[Math.floor(Math.random() * greetings.length)];

  // Add it to the page.
  const greetingContainer = document.getElementById('greeting-container');
  greetingContainer.innerText = greeting;
}

$(function() {
                
                var documentEl = $(document),
                    fadeElem = $('.fade-scroll');
                
                
                documentEl.on('scroll', function() {
                    var currScrollPos = documentEl.scrollTop();
                    
                    fadeElem.each(function() {
                        var $this = $(this),
                            elemOffsetTop = $this.offset().top;
                        if (currScrollPos > elemOffsetTop) $this.css('opacity', 1 - (currScrollPos-elemOffsetTop)/1000);
                    }); 
                });
                
            });

// Function to fetch proverbs from server and pass to DOM
async function getRandomProverb(){
    // Fetch proverb from the server
    const response = await fetch('/random-proverb');
    // Convert the fetched proverb which is a promise to text type
    const proverb = await response.text();
    // Pass the proverb into the DOM 
    document.getElementById('proverb-container').innerText = proverb;
}

function printComments() {
    // Fetch comments from the server
    fetch('/data?max='+document.getElementById('quantity').value).then(response => response.json()).then((comment) => {
      const commentElements = document.getElementById('user-message');

    // Runs a loop for each content of the comments array to be printed out
      for (var i = 0; i < comment.comments_.length; i++) {
          //commentElements.appendChild(createheadElement(comment.comments_[i]));
          commentElements.appendChild(createListElement(comment.comments_[i]));  
        }
});
}

/** Creates an <p> element containing text. */
function createListElement(text) {
  const liElem = document.createElement('li');

  const heading = document.createElement('h5');
  if(String.fromCharCode.apply(String, text.name_.bytes) === ""){
      heading.innerText = "Anonymous";
  }
  else{
  heading.innerText = String.fromCharCode.apply(String, text.name_.bytes);
  }  

  const liElement = document.createElement('p');
  liElement.innerText = String.fromCharCode.apply(String, text.data_.bytes);
  
  liElem.appendChild(heading);
  liElem.appendChild(liElement);
  return liElem;
}

function deleteComments(){
  // Fetch comments from the server
    let request = new Request('/delete-data', {method:"POST"});
    fetch(request).then(unused => printComments());
}

/** Creates a chart and adds it to the page. */
function drawChart1() {
  const data = new google.visualization.DataTable();
  data.addColumn('string', 'Book');
  data.addColumn('number', 'Hours');
        data.addRows([
          ['Anthills on the Savannah', 10],
          ['Small Great Things', 11],
          ['All the Bright Places', 9]

        ]);

  const options = {
    'title': 'Reads',
    'width':600,
    'height':600
  };

  const chart = new google.visualization.BarChart(
      document.getElementById('chart-container'));
  chart.draw(data, options);
}

/** Fetches songs data and uses it to create a chart. */
function drawChart() {
  fetch('/song-data').then(response => response.json())
  .then((songWords) => {
    const data = new google.visualization.DataTable();
    data.addColumn('string', 'Song');
    data.addColumn('number', 'Number of "Yuhs"');
    Object.keys(songWords).forEach((song) => {
      data.addRow([song, songWords[song]]);
    });
 
    const options = {
      'title': 'Number of YUHs per song',
      'width':600,
      'height':500
    };

    const chart = new google.visualization.LineChart(
        document.getElementById('song-chart-container'));
    chart.draw(data, options);
  });
}

function drawChart3() {
    var data = new google.visualization.DataTable();
    data.addColumn('string', 'From');
    data.addColumn('string', 'To');
    data.addColumn('number', 'Weight');
    data.addRows([
       [ 'Ethiopia', 'Portugal', 5 ],
       [ 'Ethiopia', 'France', 1 ],
       [ 'Brazil', 'Spain', 1 ],
       [ 'Brazil', 'England', 1 ],
       [ 'Canada', 'Portugal', 1 ],
       [ 'Canada', 'France', 5 ],
       [ 'Canada', 'England', 1 ],
       [ 'Mexico', 'Portugal', 1 ],
       [ 'Mexico', 'France', 1 ],
       [ 'Mexico', 'Spain', 5 ],
       [ 'Mexico', 'England', 1 ],
       [ 'USA', 'Portugal', 1 ],
       [ 'USA', 'France', 1 ],
       [ 'USA', 'Spain', 1 ],
       [ 'USA', 'England', 5 ],
       [ 'Portugal', 'Angola', 2 ],
       [ 'Portugal', 'Senegal', 1 ],
       [ 'Portugal', 'Morocco', 1 ],
       [ 'Portugal', 'South Africa', 3 ],
       [ 'France', 'Angola', 1 ],
       [ 'France', 'Senegal', 3 ],
       [ 'France', 'Mali', 3 ],
       [ 'France', 'Morocco', 3 ],
       [ 'France', 'South Africa', 1 ],
       [ 'Spain', 'Senegal', 1 ],
       [ 'Spain', 'Morocco', 3 ],
       [ 'Spain', 'South Africa', 1 ],
       [ 'England', 'Angola', 1 ],
       [ 'England', 'Senegal', 1 ],
       [ 'England', 'Morocco', 2 ],
       [ 'England', 'South Africa', 7 ],
       [ 'South Africa', 'China', 5 ],
       [ 'South Africa', 'India', 1 ],
       [ 'South Africa', 'Japan', 3 ],
       [ 'Angola', 'China', 5 ],
       [ 'Angola', 'India', 1 ],
       [ 'Angola', 'Japan', 3 ],
       [ 'Senegal', 'China', 5 ],
       [ 'Senegal', 'India', 1 ],
       [ 'Senegal', 'Japan', 3 ],
       [ 'Mali', 'China', 5 ],
       [ 'Mali', 'India', 1 ],
       [ 'Mali', 'Japan', 3 ],
       [ 'Morocco', 'China', 5 ],
       [ 'Morocco', 'India', 1 ],
       [ 'Morocco', 'Japan', 3 ]
    ]);

    // Set chart options
    var options = {
      width: 600,
      explorer: { actions: ['dragToZoom', 'rightClickToReset'] }
    };

    // Instantiate and draw our chart, passing in some options.
    var chart = new google.visualization.Sankey(document.getElementById('sankey_multiple'));
    chart.draw(data, options);
   }