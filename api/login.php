<?php

$method  = $_SERVER['REQUEST_METHOD'];
$request = trim($_SERVER['PATH_INFO'], '/');
$input   = file_get_contents('php://input');

file_put_contents('php_login_debug.log', 'login start >'.PHP_EOL, FILE_APPEND | LOCK_EX); 
file_put_contents('php_login_debug.log', "\tmethod: " . $method . PHP_EOL, FILE_APPEND | LOCK_EX); 
file_put_contents('php_login_debug.log', "\trequest: " . $request . PHP_EOL, FILE_APPEND | LOCK_EX); 
file_put_contents('php_login_debug.log', "\tinput: " . $input . PHP_EOL, FILE_APPEND | LOCK_EX);

//load and connect to MySQL database stuff
require("config.inc.php");

file_put_contents('php_login_debug.log', 'test0 login found config >'.PHP_EOL, FILE_APPEND | LOCK_EX);    ob_start();
$toss = ob_get_clean(); file_put_contents('php_login_debug.log', $toss .PHP_EOL, FILE_APPEND | LOCK_EX);

//gets user's info based off of a username.
$req_data = json_decode($input, true);
$username = $req_data['username'];
$query = " 
  SELECT 
    id, 
    username, 
    password
  FROM user 
  WHERE 
    username = :username 
  ";
    
$query_params = array(':username' => $req_data['username']);


try {
  file_put_contents('php_login_debug.log', 'getTable try select user >'.PHP_EOL, FILE_APPEND | LOCK_EX);    ob_start();
  var_dump("username=", $req_data['username'], "END");
  var_dump("password=", $req_data['password'], "END");
  $toss = ob_get_clean(); file_put_contents('php_login_debug.log', $toss .PHP_EOL, FILE_APPEND | LOCK_EX);

  $stmt   = $db->prepare($query);
  $result = $stmt->execute($query_params);
} catch (PDOException $ex) {
  file_put_contents('php_login_debug.log', 'getTable cannot access database >'.PHP_EOL, FILE_APPEND | LOCK_EX);    ob_start();
  //var_dump("_POST=", $_POST, "END");
  $toss = ob_get_clean(); file_put_contents('php_login_debug.log', $toss .PHP_EOL, FILE_APPEND | LOCK_EX);
  
  // For testing, you could use a die and message. 
  //die("Failed to run query: " . $ex->getMessage());

  //or just use this use this one to product JSON data:
  $response["success"] = 0;
  $response["message"] = "Database Error.";
  die(json_encode($response));
}
  
//This will be the variable to determine whether or not the user's information is correct.
//we initialize it as false.
$validated_info = false;
    
//fetching all the rows from the query
file_put_contents('php_login_debug.log', 'getTable check password >'.PHP_EOL, FILE_APPEND | LOCK_EX);    ob_start();
var_dump("req_data=", $req_data, "END");
$toss = ob_get_clean(); file_put_contents('php_login_debug.log', $toss .PHP_EOL, FILE_APPEND | LOCK_EX);
$row = $stmt->fetch();
if ($row) {
  //if we encrypted the password, we would unencrypt it here, but in our case we just
  //compare the two passwords
  //if ($_POST['password'] === $row['password']) {
  if (md5($req_data['password']) === $row['password'] || $req_data['password'] === $row['password']) {
    $login_ok = true;
  } else {
    file_put_contents('php_login_debug.log', 'login bad password >'.PHP_EOL, FILE_APPEND | LOCK_EX);    ob_start();
    var_dump("req_data=", $req_data, "END");
    $toss = ob_get_clean(); file_put_contents('php_login_debug.log', $toss .PHP_EOL, FILE_APPEND | LOCK_EX);
  }
}
  
// If the user logged in successfully give them a token
if ($login_ok) {
  $jwt = gen_jwt();
  
  $response["success"] = 1;
  $response["jwt"] = $jwt;
  die(json_encode($response));
    
} else {
  $response["success"] = 0;
  $response["message"] = "Invalid Credentials.";
  die(json_encode($response));
  }
    
function gen_jwt() {
  global $username;
  file_put_contents("in jwtEncode()" .PHP_EOL, FILE_APPEND | LOCK_EX);
  
  //MOVE TO A PROTECTED CONFIG FILE
  $secret_key = '0Ba8^BfY6$AjCI@LyGUBz%yyRlEkDEAk';
  
  $header = [
    'alg'=>'HS256',
    'typ'=>'JWT'
  ];
  $header = json_encode($header);
  $header_encoded = base64_encode($header);
  
  //change payload fields as you want to use
  $payload = [
    'usr' => $username,
    'iss' => "vmmc",
    'exp' => "0"
  ];
  $payload = json_encode($payload);
  $payload_encoded = base64_encode($payload);
    
  file_put_contents('php_login_debug.log',"made fields" .PHP_EOL, FILE_APPEND | LOCK_EX);

  ob_start();
  var_dump($header, $payload,"END");
  $toss = ob_get_clean(); file_put_contents('php_login_debug.log', $toss .PHP_EOL, FILE_APPEND | LOCK_EX);
    
  $signature = hash_hmac('SHA256',"$header_encoded.$payload_encoded", $secret_key, true);
  $signature_encoded = base64_encode($signature);
    
  file_put_contents('php_login_debug.log',"hash and encode signature" .PHP_EOL, FILE_APPEND | LOCK_EX);
  
  file_put_contents('php_login_debug.log',"jwt = $header_encoded.$payload_encoded.$signature_encoded" .PHP_EOL, FILE_APPEND | LOCK_EX);
  return "$header_encoded.$payload_encoded.$signature_encoded"; 
      
      
}
?>