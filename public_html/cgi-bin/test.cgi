#!/usr/bin/perl

open(DAT, "/u/au/es/mmazzocc/public_html/cgi-bin/PlayerData.txt") || die("Could not open file!");
@raw_data=<DAT>;
close(DAT);

print "Content-type: text/html\n\n";
print "<HTML>";
print "<BODY>";
print "<center>";

foreach $player (@raw_data)
{
      chomp($player);
      ($name,$cname,$status,$feed_time,$kills,$code,$email,$deaths)=split(/\|/,$player);
      if($status eq "H")
      {
      print "$name<br>";
      }
}
