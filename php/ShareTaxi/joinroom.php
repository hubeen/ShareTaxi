<?php
	$con = mysqli_connect('localhost', 'root', '{password}', '{db}');
	if(!$con) die('Not connected : ' . mysqli_error()); 
		
	$uid = addslashes($_POST['uid']);
	$mid = addslashes($_POST['mid']);	

	$res = array();
	
	$sql = "SELECT joins FROM USER WHERE userID='". $uid . "'";
	
	// 방에 들어가있는지 확인
	// 방 인원수 체크
	// 방 입장

	$result = mysqli_query($con,$sql);
	$row = mysqli_fetch_array($result);
	if(is_null($row[0])){
		$sql = "select cnt from room where master='" . $mid . "'";
		$result = mysqli_query($con,$sql);
		$row = mysqli_fetch_array($result);
		
		$cnt =  $row[0];

		if($cnt < 4){
			$sql = "update room set cnt=". strval($cnt+1) ." where master='". $mid. "'";
			mysqli_query($con,$sql);
			$sql = "update USER set joins='" . $mid . "' where userID='" . $uid . "'";
			mysqli_query($con,$sql); 
			$res['success'] = true;
		}else{
			$res['success'] = false;
		}
	}
	else{ // null이 아니면 이미 방이 있으니까 
		$res['success'] = false;
	}

	echo json_encode($res);
	mysqli_free_result($result); 
	mysqli_close($connect);

?>
