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
  fetch('/data').then(response => response.json()).then((comment) => {
    // Display the contents of comment in the page
    const commentListElement = document.getElementById('comment-container');
    commentListElement.innerHTML = comment;
  });
}

