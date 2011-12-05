#!/usr/bin/python

import cgi
import time
import sys

# Comment out these next two lines after development:
import cgitb
cgitb.enable()

TIME_SPLIT = ':'
PIPE = '|'

if __name__ == "__main__":
    print "Content-type: text/html\n"
    print

    file = open('FlareData.txt', 'a')

    qString = cgi.FieldStorage()
    if "lat" not in qString or "lng" not in qString:
        print "error: invalid query string"
        file.close()
        sys.exit()

    # "year without century:month:day:24-clock hour:minute:second"
    dateAndTime = time.strftime("%y:%m:%d:%H:%M:%S")

    flareBasedOnArgs = qString["lat"].value + PIPE + qString["lng"].value + PIPE + dateAndTime
    print flareBasedOnArgs

    file.write(flareBasedOnArgs)
    file.write("\n")

    file.close()
