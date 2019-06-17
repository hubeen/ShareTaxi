<?php
	$con = mysqli_connect('localhost', 'root', '{password}', '{db}');
	if(!$con) die('Not connected : ' . mysqli_error()); 
		
	$uid = addslashes($_POST['uid']);
	$upw = addslashes($_POST['upw']);
	$res = array();
	
	$sql = "SELECT * FROM USER WHERE userID='". $uid . "'";
	
	$result = mysqli_query($con,$sql);
	$row = mysqli_fetch_array($result);
	if(password_verify($upw, $row['userPW'])){
		$res['success'] = true;
		$res['uid'] = $uid;
	}
	else{
		$res['success'] = false;
	}
	echo json_encode($res);
	mysqli_free_result($result); 
	mysqli_close($connect);

?>
