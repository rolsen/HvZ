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

$subject = $INPUT{subject};
$text = $INPUT{email};
$humans = $INPUT{humans};
$zombies = $INPUT{zombies};
$dead = $INPUT{dead};
$exempt = $INPUT{exempt};
$password = $INPUT{password};

$fail=1;

open(DAT, "Password.txt") || die("Could not open file!");
$pw = <DAT>;
close(DAT);

chomp($pw);

if($password eq $pw)
{

   $fail = 0;

   $data_file = "Mod_emails.txt";

   open(DAT, "$data_file") || die("Could not open file!");
   @mod_data = <DAT>;
   close(DAT);

   foreach $mod (@mod_data)
   {
      $ENV{PATH} = "/usr/sbin";

      open (MAIL, "|/usr/sbin/sendmail -oi -t") || die("Could not open file!");

      chomp($mod);

      my $recipient = $mod;

      print MAIL "To: $recipient\n";

      print MAIL "From: csmhvz\@gmail.com\n";

      print MAIL "Subject: [MOD] $subject\n\n";

      print MAIL "This message sent to: MODS \n";

      if($humans)
      {
         print MAIL "Humans ";
      }

      if($zombies)
      {
         print MAIL "Zombies ";
      }

      if($exempt)
      {
         print MAIL "Exempt ";
      }

      if($dead)
      {
      print MAIL "Dead";
      }

      print MAIL "\n---------------------------\n\n";

      print MAIL "$text";

      close(MAIL);
   }

   $data_file = "PlayerData.txt";

   open(DAT, "$data_file") || die("Could not open file!");
   @raw_data = <DAT>;
   close(DAT);

   foreach $player (@raw_data)
   {
      chomp($player);
      ($p_name, $cname, $status, $feed_time, $kills, $code, $email,$deaths)=split(/\|/,$player);

      $ok=0;

      if(($status eq "H") && ($humans))
      {
         $ok=1;
      }

      if(($status eq "Z") && ($zombies))
      {
         $ok=1;
      }

      if(($status eq "X") && ($exempt))
      {
         $ok=1;
      }

      if(($status eq "D") && ($dead))
      {
         $ok=1;
      }

      if($ok==1)
      {
         $ENV{PATH} = "/usr/sbin";

         open (MAIL, "|/usr/sbin/sendmail -oi -t") || die("Could not open file!");

         my $recipient = $email;

         print MAIL "To: $recipient\n";

         print MAIL "From: csmhvz\@gmail.com\n";

         print MAIL "Subject: $subject\n\n";

         print MAIL "$text";

         close(MAIL);
      }
   }
}

if($fail==0)
{
   print "Location: http://inside.mines.edu/~mmazzocc/modinter/emailsuccess.html\n\n";
}

if($fail==1)
{
   print "Location: http://inside.mines.edu/~mmazzocc/modinter/emailfail.html\n\n";
}

#Copyright 2011 Max Mazzocchi

