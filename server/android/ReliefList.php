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
 
// get all products from products table
$result = mysql_query("SELECT *FROM donate_location") or die(mysql_error());
 
// check for empty result
if (mysql_num_rows($result) > 0) {
    // looping through all results

    $response["locations"] = array();
 
    while ($row = mysql_fetch_array($result)) {
        // temp user array
        $rep = array();
        $rep["pid"] = $row["phone"];	//phoneID
        $rep["name"] = $row["name"];
        $rep["address"] = $row["address"];
        $rep["lat"] = $row["lat"];
        $rep["lng"] = $row["lng"];
    
        // push single product into final response array
        array_push($response["locations"], $rep);
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
