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

$email=$INPUT{email};
$password=$INPUT{password};
$addrem=$INPUT{addrem};

open(DAT, "Password.txt") || die("Could not open file!");
$pw = <DAT>;
close(DAT);

chomp($pw);

$fail=1;

if($password eq $pw)
{
   $fail=0;

   $data_file = "Mod_emails.txt";
   
   if($addrem eq "add")
   {

      open(DAT, ">>$data_file") || die("Could not open file!");
      print DAT "$email\n";

      close(DAT);
   }

   if($addrem eq "remove")
   {
      open(DAT, "$data_file") || die("Could not open file!");
      @raw_data = <DAT>;
      close(DAT);

      open(DAT, ">$data_file") || die("Could not open file!");

      foreach $mod (@raw_data)
      {
         chomp($mod);
         if($mod ne $email)
         {
            print DAT "$mod\n";
         }
      }
      
      close(DAT);
   }
}

if($fail==0)
{
   print "Location: http://inside.mines.edu/~mmazzocc/modinter/modemailsuccess.html\n\n";
}

if($fail==1)
{
   print "Location: http://inside.mines.edu/~mmazzocc/modinter/modemailfail.html\n\n";
}

#Copyright 2011 Max Mazzocchi

