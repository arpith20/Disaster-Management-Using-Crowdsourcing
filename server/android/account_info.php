<?php

	$response = array();

	// check for required fields
	if (isset($_GET['phone'])) {

		$n = $_GET['phone'];

		// include db connect class
		require_once __DIR__ . '/db_connect.php';

		// connecting to db
		$db = new DB_CONNECT();

		// mysql inserting a new row
		$result  = mysql_query("select * from main_details where phone='$n'");
		$result2 = mysql_query("select * from location where phone='$n'");
		if ($result) {
			while ($row = mysql_fetch_array($result)) {
				$response["success"]       = 1;
				$response["phone"]         = $row['phone'];
				$response["name"]          = $row['name'];
				$response["dob"]           = $row['dob'];
				$response["email"]         = $row['email'];
				$response["points"]        = $row['points'];
				$response["registered_on"] = $row['registered_on'];
			}
			while ($row = mysql_fetch_array($result2)) {
				$response["h_lat"] = $row['h_lat'];
				$response["h_lng"] = $row['h_lng'];
			}
			echo json_encode($response);
		} else {
			// failed to insert row
			$response["success"] = 0;
			$response["message"] = "Oops! An error occurred.";

			echo json_encode($response);
		}
	} else {
		// required field is missing
		$response["success"] = 0;
		$response["message"] = "Required field(s) is missing";

		echo json_encode($response);
	}
?>
