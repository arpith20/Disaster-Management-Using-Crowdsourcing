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
$result = mysql_query("SELECT *FROM report") or die(mysql_error());
 
// check for empty result
if (mysql_num_rows($result) > 0) {
    // looping through all results

    $response["reports"] = array();
 
    while ($row = mysql_fetch_array($result)) {
        // temp user array
        $rep = array();
        $rep["pid"] = $row["phone"];	//phoneID
        $rep["incident"] = $row["incident"];
        $rep["large"] = $row["large"];
        $rep["lat"] = $row["lat"];
        $rep["lng"] = $row["lng"];
        $rep["damage"] = $row["damage"];
        $rep["no_casualty"] = $row["no_casualty"];
        $rep["you"] = $row["you"];
        $rep["comments"] = $row["comments"];
        $rep["done"] = $row["done"];
        $rep["report_time"] = $row["report_time"];
        $rep["modified_time"] = $row["modified_time"];
 
        // push single product into final response array
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
