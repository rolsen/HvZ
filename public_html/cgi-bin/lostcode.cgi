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

$email = $INPUT{email};


   $fail = 1;

   $data_file = "PlayerData.txt";

   open(DAT, "$data_file") || die("Could not open file!");
   @raw_data = <DAT>;
   close(DAT);

   foreach $player (@raw_data)
   {
      chomp($player);
      ($p_name, $cname, $status, $feed_time, $kills, $code, $p_email,$deaths)=split(/\|/,$player);

      $ok=0;

      if($email eq $p_email)
      {
         $ok=1;
      }

      if($ok==1)
      {

         $fail=0;

         $ENV{PATH} = "/usr/sbin";

         open (MAIL, "|/usr/sbin/sendmail -oi -t") || die("Could not open file!");

         my $recipient = $email;

         print MAIL "To: $recipient\n";

         print MAIL "From: csmhvz\@gmail.com\n";

         print MAIL "Subject: Player Code\n\n";

         print MAIL "$p_name, you recently reported a lost player code. If you did not request your player code, or you are not $p_name, please email CSMHVZ\@gmail.com.\n\n";
         print MAIL "Your player code is $code.\n\n";
         print MAIL "PLEASE keep this code safe. We recommend saving this email for your records. Remember, without a player code you are not actually playing.\nGood luck!\n";
         print MAIL "-The MOD Core";

         close(MAIL);
      }
   }

if($fail==0)
{
   print "Location: http://inside.mines.edu/~mmazzocc/lostcodesuccess.html\n\n";
}

if($fail==1)
{
   print "Location: http://inside.mines.edu/~mmazzocc/lostcodefail.html\n\n";
}

#Copyright 2011 Max Mazzocchi

