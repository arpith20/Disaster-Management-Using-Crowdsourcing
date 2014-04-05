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
    $result = mysql_query("select * from report where phone='$n'");
    $result2 = mysql_query("select * from main_details where phone='$n'");
    if ($result) {
	while($row = mysql_fetch_array($result))
	{
		$response["success"]=1;
		$response["uid"]=$row['uid'];
		$response["pid"]=$row['phone'];
		$response["incident"]=$row['incident'];
		$response["lat"]=$row['lat'];
		$response["lng"]=$row['lng'];
		$response["damage"]=$row['damage'];
		$response["no_casualty"]=$row['no_casualty'];
		$response["comments"]=$row['comments'];
		$response["done"]=$row['done'];
		$response["report_time"]=$row['report_time'];
	}
while($row = mysql_fetch_array($result2))
	{
		$response["name"]=$row['name'];
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
