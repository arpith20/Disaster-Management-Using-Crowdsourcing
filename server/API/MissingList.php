<?php

	/*
	 * Following code will list all the Disasters
	 */

	// array for JSON response
	$response = array();

	// include db connect class
	require_once __DIR__ . '/db_connect.php';

	// connecting to db
	$db = new DB_CONNECT();

	$result = mysql_query("SELECT *FROM missing") or die(mysql_error());

	// check for empty result
	if (mysql_num_rows($result) > 0) {
		// looping through all results
		$response["reports"] = array();

		while ($row = mysql_fetch_array($result)) {
			// temp user array
			$rep                = array();
			$rep["uid"]			= $row["uid"];
			$rep["pid"]         = $row["phone"]; //phoneID
			$rep["name"]        = $row["Name"];
			$rep["dress"]       = $row["dress"];
			$rep["description"] = $row["description"];
			$rep["lat"]         = $row["lat"];
			$rep["lng"]         = $row["lng"];
			$rep["reportedon"]  = $row["reportedon"];
			$rep["found"]       = $row["found"];

			array_push($response["reports"], $rep);
		}
		// success
		$response["success"] = 1;

		// echoing JSON response
		echo json_encode($response);
	} else {
		// no products found
		$response["success"] = 0;
		$response["message"] = "No reports found";

		// echo no users JSON
		echo json_encode($response);
	}
?>
