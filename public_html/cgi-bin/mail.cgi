#!/usr/bin/perl

$ENV{PATH} = "/usr/sbin";

open (MAIL, "|/usr/sbin/sendmail -oi -t") || die("Could not open file!");

my $recipient = "maxwell.mazzocchi\@gmail.com";

print MAIL "To: $recipient\n";

print MAIL "From: HVZCSM\@gmail.com\n";

print MAIL "Subject: Form Data\n\n";

print MAIL "This is a test. Woohoo for tests!!!\n";

close(MAIL);

print "Location: http://inside.mines.edu/~mmazzocc/players1.html\n\n";

#Copyright 2011 Max Mazzocchi

