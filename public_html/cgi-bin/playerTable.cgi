#!/usr/bin/perl

$data_file="PlayerData.txt";

open(DAT, $data_file) || die("Could not open file!");
@raw_data=<DAT>;
close(DAT);

open(DAT, "/u/au/es/mmazzocc/public_html/players.html") || die("Could not open file!");
@old_page=<DAT>;
close(DAT);

open(DAT, ">/u/au/es/mmazzocc/public_html/players.html") || die("Could not open file!");

$var=1;

$players=0;
$zombies=0;
$humans=0;
$mostkills="";
$maxkills=-1;

foreach $line (@old_page)
{
   $start = "<!-Table-->\n";
   $end = "<!-EndTable-->\n";
   $ststart = "<!--BegStat-->\n";
   $endstat = "<!--EndStat-->\n";

   if(($line ne $start) && ($line ne $ststart))
   {
      if($var==1)
      {
         print DAT "$line";
      }
   }

   if($line eq $ststart)
   {
      $var=0;
      print DAT "<!--BegStat-->\n";
      print DAT "<br>Active Players: $players";
      print DAT "<br>Humans: $humans";
      print DAT "<br>Zombies: $zombies";
      print DAT "<br>Most Kills: $mostkills ($maxkills)";
      print DAT "<!--EndStat-->";
   }

   if($line eq $start)
   {
      $var=0;
      print DAT "<!-Table-->\n";
      print DAT "<table class=F4 border=2 bordercolor=#000000>\n";
      print DAT "<tr class=F3><td>Code Name</td><td>Status</td><td>Last Feed</td><td>Kills</td></tr>\n";

      @timeData = localtime(time);
      $c_time = join('.', @timeData);
      ($s,$c_minute,$c_hour,$c_day,$c_month,$y,$d1,$d_year,$ds)=split(/\./,$c_time);

      $c_month = $c_month+1;

      foreach $player (@raw_data)
      {
         chomp($player);
         ($name,$cname,$status,$feed_time,$kills,$code,$email,$deaths)=split(/\|/,$player);
         if($status eq "Z")
         {
            ($minute,$hour,$day,$month,$pd_year)=split(/\./,$feed_time);

            $dead = 0;

            $pd_year = $pd_year + 2;

            if($pd_year<$d_year)
            {
               $dead = 1;
            }

            if($pd_year==$d_year)
            {
               if($hour==$c_hour)
               {
                  if($minute<=$c_minute)
                  {
                     $dead = 1;
                  }
               }

               if($hour<$c_hour)
               {
                  $dead = 1;
               }
            }

            $AP ="AM";
            if($hour>=12)
            {
               $AP="PM";
               $hour = $hour-12;
            }

            if($hour==0)
            {
               $hour = 12;
            }

            if($dead==1)
            {
               open(FIL, ">/u/au/es/mmazzocc/public_html/cgi-bin/PlayerData.txt") || die("Could not open file!");
               foreach $n_line (@raw_data)
               {
                  chomp($n_line);
                  if($n_line eq $player)
                  {
                     print FIL "$name|$cname|D|$feed_time|$kills|$code|$email|$deaths\n";
                  }
                  else
                  {
                     print FIL "$n_line\n";
                  }
               }
               close(FIL);
               print DAT "<tr><td>$cname</td><td>D</td><td>$hour:$minute $AP, $month/$day</td><td>$kills</td></tr>\n";
            }
            else
            {
               print DAT "<tr><td>$cname</td><td>$status</td><td>$hour:$minute $AP, $month/$day</td><td>$kills</td></tr>\n";
               if($kills==$maxkills)
               {
                  $mostkills = "$mostkills, $cname";
               }
               if($kills>$maxkills)
               {
                  $mostkills = "$cname";
                  $maxkills = $kills;
               }
               $zombies = $zombies+1;
               $players = $players+1;
            }
         }
         else
         {
             if($status eq "D")
             {
              ($minute,$hour,$day,$month,$pd_year)=split(/\./,$feed_time);
               $AP ="AM";
               if($hour>12)
               {
                  $AP="PM";
                  $hour = $hour-12;
               }
                print DAT "<tr><td>$cname</td><td>$status</td><td>$hour:$minute $AP, $month/$day</td><td>$kills</td></tr>\n";
             }
             else
             {
                print DAT "<tr><td>$cname</td><td>$status</td><td>N/A</td><td>$kills</td></tr>\n";
		if($status eq "H")
		{
                   $humans=$humans+1;
		}
                $players=$players+1;
             }
         }
      }
      
      print DAT "</table>\n";

      print DAT "<!-EndTable-->\n"
   }

   if(($line eq $end) || ($line eq $endstat))
   {
      $var=1;
   }
}

print DAT "<br><br>&copy 2011 Maxwell Mazzocchi</div>\n<br>\n<br>\n<a href=\"/PH-Disclaimer\" class=F2>Disclaimer</a>\n<br>\n<br>\n</div>\n</div>\n</BODY>\n</HTML>";

close(DAT);

print "Location: http://inside.mines.edu/~mmazzocc/players1.html\n\n";

#Copyright 2011 Max Mazzocchi

