#!/usr/bin/python

import cgi
import time
import sys

# Comment out these next two lines after development:
import cgitb
cgitb.enable()

TIME_SPLIT = ':'
PIPE = '|'

# Tracker format
#playerid|playerstate|lat|lng|datetime
PLAYER_ID = 0
PLAYER_STATE = 1
LAT = 2
LNG = 3
DATETIME = 4

PLAYER_FILE = "PlayerData.txt"
TRACKER_FILE = 'TrackerData.txt'

def readLines():
    """ Get all the lines from the file """
    file = open(PLAYER_FILE, 'r')
    lines  = file.readlines()
    file.close()
    return lines

def findStatus(line, code):
    """ Cleans a line from the Player Data file so we
    only send the data we need """
    vals = line.split(PIPE)

    if int(vals[5]) == int(code):
        return vals[2]
    else:
        return 0

def getStatus(code):
    for line in readLines():
        status = findStatus(line, code)
        if(status):
            return status

if __name__ == "__main__":
    print "Content-type: text/html\n"

    # Comment out these next two lines after development:
    print 'Number of arguments:', len(sys.argv), 'arguments.'
    print 'Argument List:', str(sys.argv)

    file = open(TRACKER_FILE, 'a')

    qString = cgi.FieldStorage()
    if "pid" not in qString or "lat" not in qString or "lng" not in qString:
        print "error: invalid query string"
        file.close()
        sys.exit()

    # "year without century:month:day:24-clock hour:minute:second"
    dateAndTime = time.strftime("%y:%m:%d:%H:%M:%S")

    playerCode = qString["pid"].value
    playerStatus = getStatus(playerCode)
    if playerStatus != "H" and playerStatus != "Z":
        file.close()
        print "That player is dead"
        sys.exit()

    trackerBasedOnArgs = playerCode + PIPE + \
        playerStatus + PIPE + \
        qString["lat"].value + PIPE + \
        qString["lng"].value + PIPE + \
        dateAndTime

    # Comment out the next line after development:
    print trackerBasedOnArgs

    file.write(trackerBasedOnArgs)
    file.write("\n")

    file.close()
