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
$size=$INPUT{size};

$data_file="shirtorders.txt";

open(DAT, "$data_file") || die("Could not open file!");
@raw_data = <DAT>;
close(DAT);

open(DAT, "$data_file") || die("Could not open file!");
@n_raw_data = <DAT>;
close(DAT);

open(DAT, ">$data_file") || die("Could not open file!");

$ok=1;

#Test to see if there was input entered for each entry
if($email && $name && $size)
{
$ok=1;
}
else
{
$ok=0;
}

my $var = 1;
$xs = 0;
$s=0;
$m=0;
$l=0;
$xl=0;

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
      ($o_name, $o_email, $o_size)=split(/\|/,$order);
      if($o_size eq "xs")
      {
         $xs = $xs+1;
      }
      if($o_size eq "s")
      {
         $s = $s+1;
      }
      if($o_size eq "m")
      {
         $m= $m+1;
      }
      if($o_size eq "l")
      {
         $l = $l+1;
      }
      if($o_size eq "xl")
      {
         $xl = $xl+1;
      }
      
      print DAT "$o_name|$o_email|$o_size\n";
   }
}


print DAT "$name|$email|$size\n";
print DAT "----------\n";

if($size eq "xs")
      {
         $xs = $xs+1;
      }
      if($size eq "s")
      {
         $s = $s+1;
      }
      if($size eq "m")
      {
         $m= $m+1;
      }
      if($size eq "l")
      {
         $l = $l+1;
      }
      if($size eq "xl")
      {
         $xl = $xl+1;
      }


print DAT "$xs|$s|$m|$l|$xl";

close(DAT);

if($ok==1)
{
$ENV{PATH} = "/usr/sbin";

open (MAIL, "|/usr/sbin/sendmail -oi -t") || die("Could not open file!");

my $recipient = $email;

print MAIL "To: $recipient\n";

print MAIL "From: csmhvz\@gmail.com\n";

print MAIL "Subject: TShirt Order\n\n";

print MAIL "$name,\n";
print MAIL "This email is to confirm your order for an official Urban Gaming Club TShirt. Information concerning ";
print MAIL "pick up of your shirt will be sent to you once it is available. Thank you for your patience and ";
print MAIL "your support of the Mines Urban Gaming Club.\n\n";
print MAIL "If you are not $name, you have not ordered a tshirt, or you would like to cancel your order, please ";
print MAIL "email CSMHVZ\@gmail.com. \n\n";
print MAIL "We reccomend you save ths email as proof of your order.\n\n";
print MAIL "-Mines UGC";

close(MAIL);

print "Location: http://inside.mines.edu/~mmazzocc/orderconfirm.html\n\n";
}
else
{
print "Location: http://inside.mines.edu/~mmazzocc/orderfail.html\n\n";
}

#Copyright 2011 Max Mazzocchi

