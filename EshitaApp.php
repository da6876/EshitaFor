<?php
date_default_timezone_set('Asia/Dhaka');
$date = date("Y-m-d H:i:s");
$QusFianl = $_POST['QusFianl'];
$Qus1 = $_POST['Qus1'];
$Qus2 = $_POST['Qus2'];
$Qus3 = $_POST['Qus3'];
$Qus4 = $_POST['Qus4'];
$Qus5 = $_POST['Qus5'];
$lat = $_POST['lat'];
$lon = $_POST['lon'];
if($QusFianl ==""){
	$myfile = fopen("EshitaAnser.txt", "a") or die("Unable to open file!");
	fwrite($myfile, "All Questions || ===> Qus 1: ".$Qus1." || Qus 2 :".$Qus2." || Qus 3 :".$Qus3." || Qus 4 :".$Qus4." || Qus 5 :".$Qus5." || lat :".$lat." || lon :".$lon." || === ||  Date:".$date."\n");
	fclose($myfile);
}else{
	$myfile = fopen("EshitaAnser.txt", "a") or die("Unable to open file!");
	fwrite($myfile, "Final Questions || ===> Questions Anser : ".$QusFianl." || === || Date:".$date."\n");
	fclose($myfile);	
}

?>