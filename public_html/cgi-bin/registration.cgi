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


#Get data from user inputs
$name=$INPUT{name};
$email=$INPUT{email};
$cname=$INPUT{cname};

$data_file="PlayerData.txt";

open(DAT, "$data_file") || die("Could not open file!");
@raw_data = <DAT>;
close(DAT);

open(DAT, "$data_file") || die("Could not open file!");
@n_raw_data = <DAT>;
close(DAT);

open(DAT, ">$data_file") || die("Could not open file!");

#var designates if the name has been written to file yet. It is 1 if the player has been added; zero otherwise.
$var = 0;
$ok=1;

$total = "$name$email$cname";

#Test to see if there was input entered for each entry
if($email && $name && $cname)
{
$ok=1;
}
else
{
$ok=0;
}

#Test for symbols that could be read as HTML symbols; someday, this should be reimplemented as a system that converts sybols to HTML escaped symbols
if(index($total,"\<")>=0)
{
$ok=0;
}

if(index($total,"\$")>=0)
{
$ok=0;
}

if(index($total,"\>")>=0)
{
$ok=0;
}

if(index($total,"\"")>=0)
{
$ok=0;
}

if(index($total,"\;")>=0)
{
$ok=0;
}

if(index($total,"\|")>=0)
{
$ok=0;
}

if($name eq "asdf")
{
$ok=0;
}

$n_code =0;
$repeat=1;

#Create a unique player code for the new player. After generating a five-digit code starting with 1, it checks it aginst all other codes. If it exists already, it re-generates another code.
while($repeat==1)
{
   $n_code =10000+int(rand(9999));
   $repeat=0;
   foreach $player (@n_raw_data)
   {
      chomp($player);
      ($p_name, $c_name, $status, $feed_time, $kills, $code, $p_email)=split(/\|/,$player);

      if($n_code==$code)
      {
         $repeat = 1;
      }
   }
}

#Comment this out when Registration is open.
$ok=0;

#Places the player in the list alphabetically by codename. _x designates a lowercase name.
foreach $player (@raw_data)
{
   chomp($player);
   ($p_name, $c_name, $status, $feed_time, $kills, $code, $p_email)=split(/\|/,$player);

   $cname_x = lc $cname;
   $c_name_x = lc $c_name;

   #If the codename is already taken, it will not be placed in the list.
   if($cname_x eq $c_name_x)
   {
      $ok=0;
   }

   #If the given codename is less than than the one being tested, it is placed and var is set to 1.
   if($cname_x lt $c_name_x)
   {
      if($var==0 && $ok==1)
      {
         $var=1;
         print DAT "$name|$cname|H|N/A|0|$n_code|$email|0\n";
      }
   }
   #After given codename is placed, the next codename is written.
   print DAT "$p_name|$c_name|$status|$feed_time|$kills|$code|$p_email|0\n";
}

#If, at the end of the loop, no codename has been added, it is added to the end of the list.
if($var==0 && $ok==1)
{
print DAT "$name|$cname|H|N/A|0|$n_code|$email|0\n";
}

close(DAT);

if($ok==1)
{
$ENV{PATH} = "/usr/sbin";

open (MAIL, "|/usr/sbin/sendmail -oi -t") || die("Could not open file!");

my $recipient = $email;

print MAIL "To: $recipient\n";

print MAIL "From: csmhvz\@gmail.com\n";

print MAIL "Subject: Registration\n\n";

print MAIL "Welcome to the Humans Vs. Zombies system! You have officially been registered as $name, code name $cname.\n";
print MAIL "Your player code is: $n_code.\nWRITE DOWN YOUR PLAYER CODE. You will need to have it during the game. ";
print MAIL "For fairness, player codes will NOT be posted on the website. We recomend you save this email, so you ";
print MAIL "don't forget. If you do lose your player code, email mmazzocc\@mines.edu with your name.\n\n";
print MAIL "All new registrants are registered as Human. You will receive an email when you have been reported ";
print MAIL "killed. For more info on reporting kills and becoming a Zombie, visit ";
print MAIL "the CSM HVZ website.\n\n";
print MAIL "Good luck! \n\n -HVZ Mod Core";

close(MAIL);

print "Location: http://inside.mines.edu/~mmazzocc/regconfirm.html\n\n";
}
else
{
print "Location: http://inside.mines.edu/~mmazzocc/regclosed.html\n\n";
}

#Copyright 2011 Max Mazzocchi

