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

package com.google.sps;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public final class FindMeetingQuery {
  public Collection<TimeRange> query(Collection<Event> events, MeetingRequest request) {
    List<TimeRange> occupiedTime = new ArrayList<TimeRange>();
    List<TimeRange> eventAttendees = new ArrayList<TimeRange>();
    Collection<TimeRange> timeOptions = new ArrayList<TimeRange>();
    int endTimeOption = TimeRange.START_OF_DAY;
    int startTimeOption = TimeRange.START_OF_DAY;
    
    // Get all the time ranges the requested attendees are occupied on 
    for (Event event : events) {
      if (!Collections.disjoint(event.getAttendees(), request.getAttendees())) {
        occupiedTime.add(event.getWhen());
      }
    }
 
    // Sort the occupied times in order of start time
    Collections.sort(occupiedTime, TimeRange.ORDER_BY_START);
 
    // Case 1: If there is vacant time between an event's start time 
    //and the previous event's end time   
    for (TimeRange time : occupiedTime) { 
      if (time.start() >= endTimeOption && (time.start() - endTimeOption) >= request.getDuration()) { 
        timeOptions.add(TimeRange.fromStartEnd(endTimeOption, time.start(), false));
      }
      if (time.end() >= endTimeOption) {
        endTimeOption = time.end();
      }
    }
    // Case 2: If there is vacant time between an event's end time
    //and the next event's start time  
    if ((TimeRange.END_OF_DAY - endTimeOption) >= request.getDuration()) {
      timeOptions.add(TimeRange.fromStartEnd(endTimeOption, TimeRange.END_OF_DAY, true));
    }
    return timeOptions;
  }
}