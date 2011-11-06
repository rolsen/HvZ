#!/usr/bin/perl

$my_input = $ENV{QUERY_STRING};
@fv_pairs = split (/\&/ , $my_input);
foreach $pair (@fv_pairs) {
   if($pair=~m/([^=]+)=(.*)/) {
      $field = $1;
      $value = $2;
      $value =~ s/\+/ /g;
      $value =~ s/%([\dA-Fa-f]{2})/pack("C", hex($1))/eg;
      $INPUT{$field}=$value;
  }
}

$password = $INPUT{password};
$newpassword = $INPUT{newpassword};
$newpasswordverify = $INPUT{newpasswordverify};

$fail = 1;

open(DAT, "Password.txt") || die("Could not open file!");
$pw = <DAT>;
close(DAT);

chomp($pw);

if($pw eq $password)
{
   if($newpassword eq $newpasswordverify)
   {
      open(DAT, ">Password.txt") || die("Could not open file!");
      print DAT "$newpassword";
      close(DAT);
      $fail = 0;
   }
}

if($fail==1)
{
   print "Location: http://inside.mines.edu/~mmazzocc/modinter/pwunchanged.html\n\n";
}

if($fail==0)
{
   print "Location: http://inside.mines.edu/~mmazzocc/modinter/pwchanged.html\n\n";
}

#Copyright 2011 Max Mazzocchi

