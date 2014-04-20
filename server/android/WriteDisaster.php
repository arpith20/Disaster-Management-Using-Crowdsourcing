<?php

	$response = array();
	$message = array();

	function check_duplicate ($incident1,$incident2, $lat1, $lng1, $lat2, $lng2, $uid)
	{
		$theta = $lng1 - $lng2;
		$dist = sin(deg2rad($lat1)) * sin(deg2rad($lat2)) + cos(deg2rad($lat1)) * cos(deg2rad($lat2)) * cos(deg2rad($theta));
		$dist = acos($dist);
		$dist = rad2deg($dist);
		$dist = $dist * 60 * 1.1515;
		$dist = $dist * 1.609344;
		if($dist < 1 && (strcmp($incident1,$incident2)==0))
		{
			return $uid;
		}
		
	}

	
		$n = $_GET['query'];
		$n2 = $_GET['queryNoDup'];
		$lat = $_GET['lat'];
		$lng = $_GET['lng'];
		$i = $_GET['incident'];
		$flag=false;
		$uid = "";
		// include db connect class
		require_once __DIR__ . '/db_connect.php';

		// connecting to db
		$db = new DB_CONNECT();

		// mysql inserting a new row
		$result = mysql_query("select * from report_nodup");
		if ($result) {
			while ($row = mysql_fetch_array($result)) {
				$response["success"]    = 1;
				$response["incident"]		= $row['incident'];
				$response["lat"]       	= $row['lat'];
				$response["lng"]    	= $row['lng'];
				$response["uid"] = $row['uid'];
				if ($uid=check_duplicate($i, $response["incident"], $response["lat"],$response["lng"],$lat,$lng,$response["uid"]))
				{
					//if duplicate values are present, set flag to true;
					$flag = true;
					break;
				}
			}
		} else {
			$response["success"] = 0;
			$response["message"] = "Oops! An error occurred.";
		}
		if($flag)
		{
			mysql_query($n);
			$lat_new = (($response["lat"]+$lat)/2);
			$lng_new = (($response["lng"]+$lng)/2);
			$query = "update report_nodup set lat = '".$lat_new."', lng = '".$lng_new."', vote=vote+1 where uid = '".$uid."'";
			mysql_query($query);
			$message["message"] = "Reporting Incident. The accuracy has hence been inproved";
			$message["uid"] = $uid;
		}
		else
		{
			mysql_query($n);
			mysql_query($n2);
			$message["message"] = "Reporting new incident";
			$message["uid"] = "-1";
		}
		echo json_encode($message);

?>
