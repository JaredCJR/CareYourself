<?php
header('Content-type=application/json; charset=UTF-8');
/*
 * Following code will list all the messages
 */

// array for JSON response
$response = array();


// include db connect class
//require_once __DIR__ . '\db_connect.php';
require_once '/home/a2522779/public_html/careyourself/db_connect.php';

// connecting to db
$db = new DB_CONNECT();

// get all messages from _messages table
mysql_query("set names utf8");
$result = mysql_query("SELECT *FROM _messages_north") or die(mysql_error());

// check for empty result
if (mysql_num_rows($result) > 0) {
    // looping through all results
    // messages node
    $response["my_messages"] = array();
    
    while ($row = mysql_fetch_array($result)) {
        // temp user array
        $var_messages = array();
        $var_messages["pid"] = $row["pid"];
        $var_messages["title"] = $row["title"];
        $var_messages["content"] = $row["content"];
        $var_messages["send_time"] = $row["send_time"];

        // push single message into final response array
        array_push($response["my_messages"], $var_messages);
    }
    // success
    $response["success"] = 1;

    // echoing JSON response
    echo json_encode($response);
} else {
    // no messages found
    $response["success"] = 0;
    $response["message"] = "No MMessage found";

    // echo no users JSON
    echo json_encode($response);
}

/*

Listing all Products{

"my_messages": [
{
"pid": "1",
"title": "iPhone 4S",
"content": "300.00",
"send_time": "2012-04-29 02:04:02"
},
{
"pid": "2",
"title": "Macbook Pro",
"content": "600.00",
"send_time": "2012-04-29 02:04:51"
}

when fail
{
"success": 0,
"message": "No MMessage found"
}
*/
?>
