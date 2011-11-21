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

$ok = 1;
$failcode = 0;

$z_code=$INPUT{z_code};
$h_code=$INPUT{h_code};
$hour=$INPUT{hour};
$minute=$INPUT{minute};
$AP=$INPUT{AP};
$month=$INPUT{month};
$day=$INPUT{day};

if($z_code eq $h_code)
{
$ok =0;
$failcode=1;
}

$data_file="kill_log.txt";

open(DAT, "$data_file") || die("Could not open file!");
@kill_data = <DAT>;
close(DAT);

foreach $kill (@kill_data)
{
   chomp($kill);
   ($zk_code, $hk_code)=split(/\|/,$kill);
   if(($z_code eq $zk_code) && ($h_code eq $hk_code))
   {
      $ok=0;
      $failcode=2;
   }
}

$data_file = "PlayerData.txt";

open(DAT, "$data_file") || die("Could not open file!");
@raw_data = <DAT>;
close(DAT);

open(DAT, ">$data_file") || die("Could not open file!");

$h_email=" ";
$z_email=" ";

$old_hour=$hour;

if($AP eq "PM")
{
	$hour = $hour+12;
	if($hour%12==0)
	{
		$hour = $hour - 12;
	}
}
else
{
	if($hour%12==0)
	{
		$hour = $hour - 12;
	}
}

@timeData = localtime(time);
$c_time = join('.', @timeData);
($s,$c_minute,$c_hour,$c_day,$c_month,$y,$d1,$d_year,$ds)=split(/\./,$c_time);

$cd_year = $d_year;
$d_year = $day-1;

for ($count=1; $count<$month; $count++)
{

   if($count==1 || $count==3 || $count==5 || $count==7 || $count==8 || $count==10)
   {
      $d_year = $d_year + 31;
   }

   if($count==4 || $count==6 || $count==9 || $count==11)
   {
      $d_year = $d_year + 30;
   }

   if($count==2)
   {
      $y = $y +1900;
      if(($y % 4)==0)
      {
         $d_year += 29;
      }
      else
      {
         $d_year +=28;
      }
   }
}

if(abs($d_year-$cd_year)>3)
{
$ok=0;
$failcode=7;
}

$n_time = "$minute.$hour.$day.$month.$d_year";

$h_exists = 0;
$z_exists = 0;

foreach $player (@raw_data)
{
   chomp($player);
   ($p_name, $c_name, $status, $feed_time, $kills, $code, $email, $deaths)=split(/\|/,$player);

   if($code eq $h_code)
   {
      $h_exists = 1;
      if($status eq "D" || $status eq "X")
      {
         $ok=0;
         $failcode=3;
      }
      if(($status eq "Z") && ($deaths>=3))
      {
         $ok=0;
         $failcode=4;
      }
   }

   if($code eq $z_code)
   {
      $z_exists = 1;
      if($status ne "Z")
      {
         $failcode=5;
         $ok=0;
      }
   }
}

if(($z_exists==0) || ($h_exists==0))
{
   $ok=0;
   $failcode=6;
}

foreach $player (@raw_data)
{
   chomp($player);
   ($p_name, $c_name, $status, $feed_time, $kills, $code, $email, $deaths)=split(/\|/,$player);

   if(($code eq $h_code) && ($ok==1))
   {
         $deaths=$deaths+1;
         print DAT "$p_name|$c_name|Z|$n_time|$kills|$code|$email|$deaths\n";
         $h_email=$email;
   }
   else
   {
      if(($code eq $z_code) && ($ok==1))
      {
         $kills = $kills+1;
         print DAT "$p_name|$c_name|$status|$n_time|$kills|$code|$email|$deaths\n";
         $z_email=$email;
      }
      else
      {
         print DAT "$p_name|$c_name|$status|$feed_time|$kills|$code|$email|$deaths\n";
      }
   }
}

close(DAT);

if($ok==1)
{




$data_file="kill_log.txt";

open(DAT, ">>$data_file") || die("Could not open file!");

print DAT "$z_code|$h_code\n";
close(DAT);





$ENV{PATH} = "/usr/sbin";

open (MAIL, "|/usr/sbin/sendmail -oi -t") || die("Could not open file!");

my $recipient = $h_email;

print MAIL "To: $recipient\n";

print MAIL "From: csmhvz\@gmail.com\n";

print MAIL "Subject: Death\n\n";

print MAIL "You are recieving this email because you have been reported INFECTED. A member of the zombie team ";
print MAIL "reported infecting you at approximately $old_hour:$minute $AP on $month/$day. If this is not true, please contact ";
print MAIL "the MOD team immediately at CSMHVZ\@gmail.com.\n\nCongratulations on your addition to the zombie ";
print MAIL "team. For a full list of zombie rules, go to inside.mines.edu/~cloew/rules.html.\n\n";
print MAIL "Good luck! \n\n -HVZ Mod Core";

close(MAIL);



open (MAIL, "|/usr/sbin/sendmail -oi -t") || die("Could not open file!");

my $recipient = $z_email;

print MAIL "To: $recipient\n";

print MAIL "From: csmhvz\@gmail.com\n";

print MAIL "Subject: Kill Report\n\n";

print MAIL "You are recieving this email because you have reported a kill. Your reported kill occurred at ";
print MAIL "approximately $old_hour:$minute $AP on $month/$day. If this is not true, please contact ";
print MAIL "the MOD team immediately at CSMHVZ\@gmail.com.\n\nCongratulations on your kill. ";
print MAIL "For a full list of killing rules, go to inside.mines.edu/~cloew/rules.html.\n\n";
print MAIL "Good luck! \n\n -HVZ Mod Core";

close(MAIL);

print "Location: http://inside.mines.edu/~cloew/killconfirm.html\n\n";
}
else
{
   if($failcode==1)
   {
      print "Location: http://inside.mines.edu/~cloew/killfails/killinvalid-ZombieSelfCannibalism.html\n\n";
   }
   if($failcode==2)
   {
      print "Location: http://inside.mines.edu/~cloew/killfails/killinvalid-RepeatKill.html\n\n";
   }
   if($failcode==3)
   {
      print "Location: http://inside.mines.edu/~cloew/killfails/killinvalid-DeadOrExemptPlayer.html\n\n";
   }
   if($failcode==4)
   {
      print "Location: http://inside.mines.edu/~cloew/killfails/killinvalid-ZombieAteOtherZombie.html\n\n";
   }
   if($failcode==5)
   {
      print "Location: http://inside.mines.edu/~cloew/killfails/killinvalid-OnlyZombiesCanEnterKills.html\n\n";
   }
   if($failcode==6)
   {
      print "Location: http://inside.mines.edu/~cloew/killfails/killinvalid-InvalidCode.html\n\n";
   }
   if($failcode==7)
   {
      print "Location: http://inside.mines.edu/~cloew/killfails/killinvalid-IncorrectTime.html\n\n";
   }
   #print "Location: http://inside.mines.edu/~cloew/killinvalid.html\n\n";
}

#Copyright 2011 Max Mazzocchi

