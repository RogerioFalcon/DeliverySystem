<?php

@session_start();
@ini_set('session.gc_maxlifetime',12*60*60);
@ini_set('session.cookie_lifetime',12*60*60);

error_reporting(E_ALL);
ini_set('display_errors', 1);
date_default_timezone_set('Asia/Karachi');

//API host link api url should be  https://domain.com/  OR http://domain.com/ other wise it will be your configration error 
$baseurl = "http://domian.com/";

//dont change any thing here
$imagebaseurl=$baseurl."foodies/mobileapp_api/";
$baseurl = $baseurl."foodies/mobileapp_api/superAdmin/";


if (@$_GET['p'] == "login") {
      
      $email = $_POST['email'];
      $password = $_POST['password'];
      
      
      $headers = [
          "Accept: application/json",
          "Content-Type: application/json"
      ];
      
      $data = [
          "email" => $email,
          "password" => $password,
      ];
      
      $ch = curl_init($baseurl . 'login');
      curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
      curl_setopt($ch, CURLOPT_CUSTOMREQUEST, 'POST');
      curl_setopt($ch, CURLOPT_POSTFIELDS, json_encode($data));
      curl_setopt($ch, CURLOPT_HTTPHEADER, $headers);
      $return = curl_exec($ch);
      $json_data = json_decode($return, true);
        
      //print_r($return);
      //die();
      
      $curl_error = curl_error($ch);
      $http_code = curl_getinfo($ch, CURLINFO_HTTP_CODE);
      $data = $json_data['msg'];
   
      if ($json_data['code'] !== 200) 
      {
            echo "<script>window.location='index.php?action=error'</script>";
      }
      else 
      {
            $_SESSION['sessionPortal'] = "foodAdmin";
            $_SESSION['sessionTokon'] = time();
            $_SESSION['id'] = $json_data['msg']['UserAdmin']['id'];
            //$_SESSION['name'] = $json_data['msg']['UserAdmin']['first_name']." ".$json_data['msg']['UserAdmin']['last_name'];
            $_SESSION['role'] = $json_data['msg']['UserAdmin']['role'];
            $_SESSION['UserAdmin'] = $json_data['msg']['UserAdmin']['role_name'];
            
            //die();
            echo "<script>window.location='dashboard.php?p=users'</script>";
            
            
      }
      
}

if(@$_GET['p'] == "logout" ) 
{ 
	@session_destroy();
	echo "<script>window.location='index.php'</script>";
}




function RemoveSpecialChar($value)
{
    $result  = preg_replace('/[^a-zA-Z0-9_ -]/s','',$value);
    return $result;
}


function curl_request($data,$url)
{
    $headers = [
        "Accept: application/json",
        "Content-Type: application/json"
    ];

    $data = $data;

    $ch = curl_init($url);
    curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
    curl_setopt($ch, CURLOPT_CUSTOMREQUEST, 'POST');
    curl_setopt($ch, CURLOPT_POSTFIELDS, json_encode($data));
    curl_setopt($ch, CURLOPT_HTTPHEADER, $headers);
    $return = curl_exec($ch);
    $json_data = json_decode($return, true);
    $curl_error = curl_error($ch);
    $http_code = curl_getinfo($ch, CURLINFO_HTTP_CODE);
    
    return $json_data;
}


?>