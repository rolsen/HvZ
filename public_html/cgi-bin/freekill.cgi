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

$codetext=$INPUT{codes};
$password = $INPUT{password};
$hour=$INPUT{hour};
$minute=$INPUT{minute};
$AP=$INPUT{AP};
$month=$INPUT{month};
$day=$INPUT{day};

$fail=1;

open(DAT, "Password.txt") || die("Could not open file!");
$pw = <DAT>;
close(DAT);

chomp($pw);

if($password eq $pw)
{

	$fail = 0;

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

	$n_time = "$minute.$hour.$day.$month.$d_year";

	@emails = ();

	@codes = split(/\n/,$codetext);

	open(DAT, "/u/au/es/mmazzocc/public_html/cgi-bin/PlayerData.txt") || die("Could not open file!");
	@old_data=<DAT>;
	close(DAT);

	open(DAT, ">/u/au/es/mmazzocc/public_html/cgi-bin/PlayerData.txt") || die("Could not open file!");

	foreach $player (@old_data)
	{
		chomp($player);
		($name,$cname,$status,$feed_time,$kills,$p_code,$email,$deaths)=split(/\|/,$player);
		$ok=0;
		foreach $code (@codes)
		{
			if($p_code==$code)
			{
				$ok=1;
			}
		}
		if($ok==1)
		{
			print DAT "$name|$cname|Z|$n_time|$kills|$p_code|$email|$deaths\n";
			push(@emails,$email);
		}
		else
		{
			print DAT "$player\n";
		}
	}

	close(DAT);

	foreach $n_email (@emails)
	{
		open (MAIL, "|/usr/sbin/sendmail -oi -t") || die("Could not open file!");

		chomp($n_email);

		my $recipient = $n_email;

		print MAIL "To: $recipient\n";

		print MAIL "From: csmhvz\@gmail.com\n";

		print MAIL "Subject: Free Kill\n\n";

		print MAIL "Congrats on your free kill!\n\n";

		print MAIL "Recieving this message means that you have recieved a free kill. Your feed timer has been reset. If you died before this message was sent, you are ";

		print MAIL "now a zombie again. This is effective immediately, please check the Players page to confirm your new feed time. Note: This does NOT count towards your kill count.\n\n";

		print MAIL "If you are recieving this message by accident, or do not understand, please email CSMHVZ\@gmail.com as soon as possible.\n\n";

		print MAIL "Good luck!\n\n-The MOD Core";

		close(MAIL);
	}

}

if($fail==0)
{
	print "Location: http://inside.mines.edu/~mmazzocc/modinter/emailsuccess.html\n\n";
}
else
{
	print "Location: http://inside.mines.edu/~mmazzocc/modinter/emailfail.html\n\n";
}
