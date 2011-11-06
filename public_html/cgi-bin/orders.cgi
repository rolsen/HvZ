#!/usr/bin/perl

$data_file="shirtorders.txt";

open(DAT, "$data_file") || die("Could not open file!");
@raw_data = <DAT>;
close(DAT);

$var=1;

print "Content-type: text/html\n\n";
print "<HTML>";
print "<BODY>";
print "<center>";

print "<table border=2 bordercolor=#000000>\n";
print "<tr class=F3><td>Name</td><td>Email</td><td>Size</td></tr>\n";


foreach $order (@raw_data)
{
   chomp($order);
   if($order eq "----------")
   {
      $var =0;
   }
   
   if($var==1)
   {
      chomp($order);
      ($name, $email, $size)=split(/\|/,$order);
      
      print "<tr><td>$name</td><td>$email</td><td>$size</td></tr>\n";
   }

   if(($var==0) && ($order ne "----------"))
   {
      print "</table><br>";
      chomp($order);
      ($xs, $s, $m, $l, $xl)=split(/\|/,$order);
      print "Extra Small: $xs<br> Small: $s<br>Medium: $m<br>Large: $l<br>Extra Large: $xl<br>"; 
   }
}

print "</BODY>";
print "</HTML>";