#!/usr/bin/python

from datetime import datetime

DATE_SPLIT = '.'
VAL_SPLITTER = '|'
DEAD_STATUS = "D"
HUMAN_STATUS = "H"
ZOMBIE_STATUS = "Z"
EXEMPT_STATUS = "X"


FILE = "PlayerData.txt"

daysInMonth = [31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31]

def readLines():
    """ Get all the lines from the file """
    file = open(FILE, 'r')
    lines  = file.readlines()
    file.close()
    return lines

def cleanAndPrintLine(line):
    """ Cleans and prints the given line """
    cleanedLine = cleanLine(line)
    printLine(cleanedLine)

def cleanLine(line):
    """ Cleans a line from the Player Data file so we 
    only send the data we need """
    vals = line.split(VAL_SPLITTER)

    codeName = vals[1]
    status = vals[2]
    if status == HUMAN_STATUS:
        date = "N/A"
	elif status == EXEMPT_STATUS:
	    date = "N/A"
    else:
        date = getDate(vals[3])
    kills = vals[4]

    return [codeName, status, date, kills]
    
def getDate(dateString):
    """ Returns the properly formed date string """
    date_vals = dateString.split(DATE_SPLIT)
    
    min = date_vals[0]
    hr = int(date_vals[1])
    day = date_vals[2]
    mon = date_vals[3]
    
    ap = ""
    if int(hr) > 12:
        ap = "PM"
        hr = int(hr) - 12
    else:
        ap = "AM"
        if int(hr) == 12:
            ap = "PM"
        elif int(hr) == 0:
            hr = 12
        

    return "%d:%s %s, %s/%s" % (hr, min, ap, mon, day)
    
    
def updatePlayerData():
    """ Updates player data file """
    lines = readLines()
    file = open(FILE, 'w')
    for line in lines:
        updatePlayerRecord(line, file)
        
    file.close()
    
def updatePlayerRecord(line, file):
    """ Updates a player record in the """
    vals = line.split(VAL_SPLITTER)
    last_feed = vals[3]
    
    if vals[2] == ZOMBIE_STATUS:
        if dead(last_feed):
            vals[2] = DEAD_STATUS
            
    writeValsToFile(vals, file)
    
def dead(last_feed):
    """ Return whether the zombie with this feed_time """
    date = last_feed.split(DATE_SPLIT)
    now = datetime.now()
    daysThroughYear = getDaysThroughYear(now.year, now.month, now.day)
    
    dead = False
    
    if (int(date[4]) + 2) < daysThroughYear:
        dead = True
    elif (int(date[4]) + 2) == daysThroughYear :
        if  int(date[1]) < now.hour:
            dead = True
        elif int(date[1]) == now.hour:
            if  int(date[0]) <= now.minute:
                dead = True
                
    return dead
    
def writeValsToFile(vals, file):
    """ Writes vals back to the file """
    toWrite = ""
    
    for val in vals:
        toWrite += val + VAL_SPLITTER
        
    toWrite = toWrite[:-1]
    
    file.write(toWrite)
    
def getDaysThroughYear(year, month, day):
    """ Returns the number of days through the year """
    month = month - 1
    daysThroughYear = day
    
    for i in range(month):
        daysThroughYear += daysInMonth[i]
        
    if year%4 == 0 and month > 1:
        daysThroughYear += 1
        
    return daysThroughYear    

def printLine(line):
    """ Prints the line separated by the VAL_SPLITTER """
    out = ""
    for entry in line:
        out = out + entry + VAL_SPLITTER
    print out


if __name__ == "__main__":
    print "Content-type: text/html\n";
    print "\n";
    
    updatePlayerData()
    
    for line in readLines():
        cleanAndPrintLine(line)
