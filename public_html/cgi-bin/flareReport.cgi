#!/usr/bin/python


if __name__ == "__main__":
    print "Content-type: text/html\n";
    print "\nflareReport!\n";

    file = open('FlareData.txt', 'r+')

    #file.append(flareBasedOnArgs)

    file.close()
