<?php
	$con = mysqli_connect('localhost', 'root', '{password}', '{db}');
	if(!$con) die('Not connected : ' . mysqli_error()); 
		
	$uid = addslashes($_POST['uid']);
	
	$res = array();
	
	$sql = "select joins from USER where userID='" . $uid . "'";	

	$result = mysqli_query($con,$sql);
	$row = mysqli_fetch_array($result);
	if(!(is_null($row[0]))){ // 방에 입장되있음
		$mid = $row[0];
		if($mid != $uid){
			$res['success'] = true;
			$sql = "select * from room where master='". $mid  .  "'";
			$result = mysqli_query($con,$sql);
			$row = mysqli_fetch_array($result);
			$title = $row['title'];
			$cnt = $row['cnt'];
			$sql = "update USER set joins=NULL where userID='" . $uid . "'";
			mysqli_query($con,$sql);
			$sql = "update room set cnt=" . strval($cnt-1) . " where master='" . $mid . "'";
			mysqli_query($con,$sql);
		}else{ // 방장일 경우 모든 멤버들도 나가게
			$res['success'] = true;
			$sql = "delete from room where master='" . $uid . "'";
			mysqli_query($con,$sql);
			$sql ="update USER set joins=NULL where joins='" . $uid . "'";
			mysqli_query($con,$sql);
			
		}
	}
	else{
		$res['success'] = false;
	}
	echo json_encode($res);
	mysqli_free_result($result); 
	mysqli_close($connect);

?>
