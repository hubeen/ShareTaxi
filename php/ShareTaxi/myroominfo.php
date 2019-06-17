<?php
	$con = mysqli_connect('localhost', 'root', '{password}', '{db}');
	if(!$con) die('Not connected : ' . mysqli_error()); 
		
	$uid = addslashes($_POST['uid']);
	
	$res = array();
	$room = array();	

	$sql = "select joins from USER where userID='" . $uid . "'";	

	$result = mysqli_query($con,$sql);
	$row = mysqli_fetch_array($result);
	if(!(is_null($row[0]))){ // 방에 입장되있음
		$res['success'] = true;
		$mid = $row[0];
		$sql = "select * from room where master='". $mid . "'";
		$result = mysqli_query($con,$sql);
		$row = mysqli_fetch_array($result);
		$res['title'] = $row['title'];
		$res['master'] = $row['master'];
		$sql = "select * from USER where joins='". $mid . "'";
		$result = mysqli_query($con,$sql);
		while($row = mysqli_fetch_array($result)){
			$tmp = array("user" => $row['userID']);
			array_push($room, $tmp);
		}
		$res['room'] = $room;
	}
	else{
		$res['success'] = false;
	}
	echo json_encode($res);
	mysqli_free_result($result); 
	mysqli_close($connect);

?>
