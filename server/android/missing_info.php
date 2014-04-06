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
		$result = mysql_query("select * from missing where phone='$n'");
		if ($result) {
			while ($row = mysql_fetch_array($result)) {
				$response["success"]     = 1;
				$response["phone"]       = $row['phone'];
				$response["phone_by"]    = $row['phone_by'];
				$response["dress"]       = $row['dress'];
				$response["description"] = $row['description'];
				$response["lat"]         = $row['lat'];
				$response["lng"]         = $row['lng'];
				$response["reportedon"]  = $row['reportedon'];
				$response["found"]       = $row['found'];
				$response["more_info"]   = $row['more_info'];
				$response["f_lat"]       = $row['f_lat'];
				$response["f_lng"]       = $row['f_lng'];
				$response["name"]        = $row['Name'];
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
