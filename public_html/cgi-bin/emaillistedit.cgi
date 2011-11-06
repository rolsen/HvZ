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
$password="None";

open(DAT, "Password.txt") || die("Could not open file!");
$pw = $password;
close(DAT);

chomp($pw);

$fail=1;

if($password eq $pw)
{
   $fail=0;

   $data_file = "emaillist.txt";

      open(DAT, "$data_file") || die("Could not open file!");
      @raw_data = <DAT>;
      close(DAT);

      open(DAT, ">>$data_file") || die("Could not open file!");

      foreach $mod (@raw_data)
      {
	 ($pre_input,$suf_input)=split(/\@/,$email);
         ($pre,$suf)=split(/\@/,$mod);
         chomp($mod);
         if($pre eq $pre_input)
         {
            $fail=1;
         }
      }

	if($fail==0)
	{
		print DAT "$email\n";
	}
      
      close(DAT);

}

if($fail==0)
{
   print "Content-type: text/html\n\n";
print "Email added";
}

if($fail==1)
{
   print "Content-type: text/html\n\n";
print "Email already exists on list.";
}

#Copyright 2011 Max Mazzocchi

