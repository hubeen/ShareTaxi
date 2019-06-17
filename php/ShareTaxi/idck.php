<?php
	$con = mysqli_connect('localhost', 'root', '{password}', '{db}');
	if(!$con) die('Not connected : ' . mysqli_error()); 
	
	$uid = addslashes($_POST["uid"]);
	
	$sql = "SELECT userID FROM USER";
	$result = mysqli_query($con,$sql);
	$res = array();
	$n = 1;
	$res['success'] = false;
	while($row = mysqli_fetch_array($result)){
		if( strcmp($row[0], $uid) == 0){
			$res['success'] = true;
		}
		$n++;
	}


	echo json_encode($res);
	mysqli_free_result($result); 
	mysqli_close($connect);

?>
