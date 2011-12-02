#!/usr/bin/python


if __name__ == "__main__":
    print "Content-type: text/html\n";
    print "\nHAIIII!\n";

    file = open('FlareData.txt', 'r+')

    #flares = [];
    #for line in file.readlines():
    #    if (stillValid(line)):
    #        flares.append(line)

    #file.truncate() # Erases the contents of the file
    #
    #for flare in flares.each(): # or something?
    #   file.write(flare)
    #   printFlare(flare)

    file.close()

    print "\nBAIII!\n"

