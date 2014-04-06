<?php

	// array for JSON response
	$response = array();

	// check for required fields
	if (isset($_POST['query'])) {

		$n = $_POST['query'];
		$q = explode("|", $n);

		// include db connect class
		require_once __DIR__ . '/db_connect.php';

		// connecting to db
		$db = new DB_CONNECT();

		$err = 1;
		foreach ($q as $arr) {
			$result = mysql_query("$arr");
			if (!$result) $err = 0;
		}

		// check if row inserted or not
		if ($err) {
			// successfully inserted into database
			$response["success"] = 1;
			$response["message"] = "Inserted successfully created.";

			// echoing JSON response
			echo json_encode($response);
		} else {
			// failed to insert row
			$response["success"] = 0;
			$response["message"] = "Oops! An error occurred.";

			// echoing JSON response
			echo json_encode($response);
		}
	} else {
		// required field is missing
		$response["success"] = 0;
		$response["message"] = "Required field(s) is missing";

		// echoing JSON response
		echo json_encode($response);
	}
?>
