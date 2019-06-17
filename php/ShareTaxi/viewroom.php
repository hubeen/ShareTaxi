<?php
	$con = mysqli_connect('localhost', 'root', '{password}', '{db}');
	if(!$con) die('Not connected : ' . mysqli_error()); 
		
	$place = addslashes($_POST['place']);
	
	$res = array();
	$room = array();
	$room_tmp = array();
	
	$sql = "SELECT * FROM room WHERE place='". $place . "'";
	
	$result = mysqli_query($con,$sql);
	$row = mysqli_fetch_array($result);
	if(is_null($row)){
		$res['success'] = false;
		
	}else{
		$res['success'] = true;
		$result = mysqli_query($con,$sql);

		while($row = mysqli_fetch_array($result)){
			$tmp = array("master" => $row['master'], "title" => $row['title'], 'date' => $row['date'], 'count'=> $row['cnt']);
			array_push($room, $tmp);
		}
		$res['room'] = $room;
	}

	echo json_encode($res);
	mysqli_free_result($result); 
	mysqli_close($connect);

?>
