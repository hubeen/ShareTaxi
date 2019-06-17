<?php
	$con = mysqli_connect('localhost', 'root', '{password}', '{db}');
	if(!$con) die('Not connected : ' . mysqli_error()); 
		
	$uid = addslashes($_POST['uid']);
	$title = addslashes($_POST['title']);
	$place = addslashes($_POST['place']);
	
	$res = array();
	
	$sql = "select joins from USER where userID='" . $uid . "'";	

	$result = mysqli_query($con,$sql);
	$row = mysqli_fetch_array($result);
	if(is_null($row[0])){
		$sql = "INSERT INTO room (master, title, cnt, place) VALUES ('". $uid . "','". $title . "',1,'" . $place."')";
		$result = mysqli_query($con,$sql);
		if($result){
			$sql = "update USER SET joins = '" . $uid . "' where userID='" . $uid . "'";
			$result = mysqli_query($con,$sql);
			if($result){
				$res['success'] = true;
			}
			else{
				$res['success'] = false;
			}
		}
		else{
			$res['success'] = false;
		}
	}
	else{
		$res['success'] = false;
	}
	echo json_encode($res);
	mysqli_free_result($result); 
	mysqli_close($connect);

?>
