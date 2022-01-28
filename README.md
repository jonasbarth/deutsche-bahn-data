# deutsche-bahn-data
Project for acquisition and storage of Deutsche Bahn train timetable data

# station data
https://data.deutschebahn.com/dataset/data-haltestellen.html

# API data
https://developer.deutschebahn.com/store/apis/info?name=Timetables&version=v1&provider=DBOpenData

# Medium article about getting delays
https://techlabsdus.medium.com/analysis-of-deutsche-bahn-ag-long-distance-train-traffic-delays-b183aacad9a2

I need to get the planned timetable from /plan/{evaNo}/{date}/{hour}
and then the changes from /fchg/{evaNo} and /rchg/{evaNo}

1. load all station information (which is static) once
2. load all future changes (which is static) once and check which trains are affected
3. every 30s load the rchg, and check which trains are affected

How can I store the data?
* Station evaNo
* stop id -(from this we can get the number of the current stop in the path)
* planned arrival time
* planned departure time
* planned platform
* actual arrival time
* actual departure time
* actual platform
* train number
* train type
* previous stop
* next stop
* final stop

# How do I know the delay?
By comparing the planned arrival and departure time to the actual arrival and departure time.

# How do I know the:
* planned arrival time -> /plan/{evaNo}/{date}/{hour}
* planned departure time -> /plan/{evaNo}/{date}/{hour}
* actual arrival time -> query the recent changes of the station /rchg/{evaNo} and update the planned arrival time
* actual departure time ->  query the recent changes of the station /rchg/{evaNo} and update the planned departure time

# Algorithm
* For each station, construct an instance of TimeTableRequest
* For each station, schedule a request once per day to get the plan and for future known changes
* For each station, schedule a request once per minute to get the recent changes.
* Once per day, write the timetable into the database

## How do schedule jobs? 
* Schedule a single job that just goes through a queue of plan requests to be run
* Schedule a single job that goes through a queue of future changes requests to be run
* Schedule one job per station that runs the recent changes once per minute
