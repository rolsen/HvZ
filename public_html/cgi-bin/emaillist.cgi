#!/usr/bin/perl

$data_file="emaillist.txt";

open(DAT, $data_file) || die("Could not open file!");
@raw_data=<DAT>;
close(DAT);

print "Content-type: text/html\n\n";

print "<HTML><BODY>Full Player Email List<br><br>";

foreach $player (@raw_data)
{
	chomp($player);
print "$player<br>";
}

#Copyright 2011 Max Mazzocchi

