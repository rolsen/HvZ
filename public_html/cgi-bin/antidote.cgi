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

$fail=1;

open(DAT, "Password.txt") || die("Could not open file!");
$pw = <DAT>;
close(DAT);

chomp($pw);

if($password eq $pw)
{

	$fail = 0;

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
			print DAT "$name|$cname|H|N/A|0|$p_code|$email|0\n";
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

		print MAIL "Subject: Revival\n\n";

		print MAIL "Welcome back to the land of the living!\n\n";

		print MAIL "Recieving this message means that you have been revived. Your Zombie status has been changed back to Human, and you may rejoin the human team. ";

		print MAIL "This change is effective immediately, please play as a Human from now on (meaning write your code on three new cards).\n\n";

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
