#!/usr/bin/perl

$data_file="Mod_emails.txt";

open(DAT, $data_file) || die("Could not open file!");
@raw_data=<DAT>;
close(DAT);

print "Content-type: text/html\n\n";

print "<HTML><BODY>Moderator Email List<br><br>";

foreach $mod (@raw_data)
{
chomp($mod);
print "$mod<br>";
}

#Copyright 2011 Max Mazzocchi

