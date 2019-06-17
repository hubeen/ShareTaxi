<?php
	$con = mysqli_connect('localhost', 'root', '{password}', '{db}');
	if(!$con) die('Not connected : ' . mysqli_error()); 
	
	$uid = addslashes($_POST['uid']);
	$upw = addslashes($_POST['upw']);
	
	$res = array();
	$hash = password_hash($upw, PASSWORD_DEFAULT); 
	
	$sql = "INSERT INTO USER (userID, userPW)  VALUES ('". $uid . "','" . $hash ."')";
	
	$result = mysqli_query($con,$sql);	
	if($result){
		$res["success"] = true;
	}else{
		$res['success'] = false;
	}
	
	echo json_encode($res);
	
	mysqli_close($con);

?>
