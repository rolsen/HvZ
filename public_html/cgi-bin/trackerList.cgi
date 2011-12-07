#!/usr/bin/python

import time

# Comment out these next two lines after development:
import cgitb
cgitb.enable()

TIME_SPLIT = ':'
PIPE = '|'

# Minutes To Seconds
def mToS(minutes):
    return (int(minutes) * 60)

VALID_TRACKER_MINUTES = 15
VALID_TRACKER_SECONDS = mToS(VALID_TRACKER_MINUTES)

# Tracker format
#playerid|playerstatus|lat|lng|datetime
PLAYER_ID = 0
PLAYER_STATUS = 1
LAT = 2
LNG = 3
DATETIME = 4

def printAndWritetrackers(trackers, file):
    for tracker in trackers:
        if (tracker == "\n"):
            continue
        file.write(tracker)

        print tracker + "<br/>"

# Time on the server is formatted as so:
#   "year without century:month:day:24-clock hour:minute:second"
#   dateAndTime = time.strftime("%y:%m:%d:%H:%M:%S")
def stillValidTime(line):
    if line == "\n":
        return False

    tokens = line.split(PIPE)
    trackerTime = tokens[DATETIME].split(TIME_SPLIT)

    currentTime = time.strftime("%y:%m:%d:%H:%M:%S").split(TIME_SPLIT)
    if (trackerTime[0] < currentTime[0] or
        trackerTime[1] < currentTime[1] or
        trackerTime[2] < currentTime[2] or
        trackerTime[3] < currentTime[3]):
        return False

    trackerTotalSeconds = mToS(int(trackerTime[4])) + int(trackerTime[5])
    currentTotalSeconds = mToS(int(currentTime[4])) + int(currentTime[5])
    if ((currentTotalSeconds - trackerTotalSeconds) > VALID_TRACKER_SECONDS):
        return False

    return True

def removeDuplicateTrackers(trackers):
    uniqueTrackers = []
    uniqueIDs = []

    for tracker in trackers:
        t1 = tracker.split(PIPE)
        playerID = t1[PLAYER_ID]
        if playerID not in uniqueIDs:
            uniqueIDs.append(playerID)
            uniqueTrackers.append(tracker)

    uniqueTrackers.sort()
    return uniqueTrackers

if __name__ == "__main__":
    print "Content-type: text/html\n"

    file = open('TrackerData.txt', 'r+') or die

    trackers = [];
    for line in file.readlines():
        if (stillValidTime(line)):
            trackers.append(line)

    trackers = removeDuplicateTrackers(trackers)

    file.truncate(0) # Erases the contents of the file
    file.seek(0)

    printAndWritetrackers(trackers, file)

    file.close()
