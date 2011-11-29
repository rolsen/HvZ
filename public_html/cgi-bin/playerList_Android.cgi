#!/usr/bin/python

SPLIT = '.'
SPLITTER = '|'
DEAD_STATUS = "D"
HUMAN_STATUS = "H"
ZOMBIE_STATUS = "Z"

def cleanAndPrintLine(line):
    """ Cleans and prints the given line """
    cleanedLine = cleanLine(line)
    printLine(cleanedLine)

def cleanLine(line):
    """ Cleans a line from the Player Data file so we 
    only send the data we need """
    vals = line.split(SPLITTER)

    codeName = vals[1]
    status = vals[2]
    if status == HUMAN_STATUS:
        date = "N/A"
    else:
        date_vals = vals[3].split(SPLIT)
        min = date_vals[0]
        hr = date_vals[1]
        day = date_vals[2]
        mon = date_vals[3]
        ap = ""
        if hr > 12:
            ap = "PM"
            hr = int(hr) - 12
        else :
            ap = "AM"
        date = ""
        date += hr
        date += ":"
        date += min
        date += " "
        date += ap
        date += ", "
        date += mon
        date += "/"
        date += day
    kills = vals[4]

    return [codeName, status, date, kills]

def printLine(line):
    """ Prints the line separated by the SPLITTER """
    out = ""
    for entry in line:
        out = out + entry + SPLITTER
    print out


if __name__ == "__main__":
    print "Content-type: text/html\n";
    print "\n";

    file = open('PlayerData.txt', 'r')

    for line in file.readlines():
        cleanAndPrintLine(line)
