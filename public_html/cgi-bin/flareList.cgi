#!/usr/bin/python

import time

TIME_SPLIT = ':'
PIPE = '|'

# Minutes To Seconds
def mToS(minutes):
    return (int(minutes) * 60)

VALID_FLARE_MINUTES = 5
VALID_FLARE_SECONDS = mToS(VALID_FLARE_MINUTES)

def printAndWriteFlares(flares, file):
    for flare in flares:
        if (flare == "\n"):
            continue
        file.write(flare)

        print flare + "<br/>"

# Time on the server is formatted as so:
#   "year without century:month:day:24-clock hour:minute:second"
#   dateAndTime = time.strftime("%y:%m:%d:%H:%M:%S")
def stillValid(line):
    tokens = line.split(PIPE)
    flareTime = tokens[2].split(TIME_SPLIT)

    currentTime = time.strftime("%y:%m:%d:%H:%M:%S").split(TIME_SPLIT)
    if (flareTime[0] < currentTime[0] or
        flareTime[1] < currentTime[1] or
        flareTime[2] < currentTime[2] or
        flareTime[3] < currentTime[3]):
        return False

    flareTotalSeconds = mToS(int(flareTime[4])) + int(flareTime[5])
    currentTotalSeconds = mToS(int(currentTime[4])) + int(currentTime[5])
    if ((currentTotalSeconds - flareTotalSeconds) > VALID_FLARE_SECONDS):
        return False

    return True

if __name__ == "__main__":
    print "Content-type: text/html\n"

    file = open('FlareData.txt', 'r+')

    flares = [];
    for line in file.readlines():
        if (stillValid(line)):
            flares.append(line)

    file.truncate(0) # Erases the contents of the file
    file.seek(0)

    printAndWriteFlares(flares, file)

    file.close()
