<?php
	$con = mysqli_connect('localhost', 'root', '{password}', '{db}');
	if(!$con) die('Not connected : ' . mysqli_error()); 
		
	$uid = addslashes($_POST['uid']);
	
	$res = array();
	
	$sql = "select joins from USER where userID='" . $uid . "'";	

	$result = mysqli_query($con,$sql);
	$row = mysqli_fetch_array($result);
	if(is_null($row[0])){
		$res['success'] = false;  // 있을 경우
	}
	else{
		$res['success'] = true; // 없을 경우
	}
	echo json_encode($res);
	mysqli_free_result($result); 
	mysqli_close($connect);

?>
