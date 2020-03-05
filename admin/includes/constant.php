<?php

date_default_timezone_set('Asia/Karachi');

//API host link
$baseurl = "http://bringthings.com/API/attandance_system/index.php?p=";
$frontend_url = "/portal";

if (isset($_GET['p']) == "login") {
      
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
      
      $ch = curl_init($baseurl . 'Admin_Login');
      curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
      curl_setopt($ch, CURLOPT_CUSTOMREQUEST, 'POST');
      curl_setopt($ch, CURLOPT_POSTFIELDS, json_encode($data));
      curl_setopt($ch, CURLOPT_HTTPHEADER, $headers);
      $return = curl_exec($ch);
      $json_data = json_decode($return, true);
      $curl_error = curl_error($ch);
      $http_code = curl_getinfo($ch, CURLINFO_HTTP_CODE);
      $data = $json_data['msg'];
   
      if ($json_data['code'] == 201) {
            echo "<script>window.location='index.php?error=1'</script>";
      }
      else {
            
            $_SESSION['id'] = $data[0]['id'];
            $_SESSION['role'] = $data[0]['role'];
            
            
            if ($_SESSION['role'] == "user") {
                  $_SESSION['email'] = $data[0]['email'];
                  $_SESSION['name']= $data[0]['first_name'] . " " .$data[0]['last_name'];
                  
                  echo "<script>window.location='ShowAttendance.php'</script>";
            }
            elseif ($_SESSION['role'] == "admin") {
                  echo "<script>window.location='all_users.php'</script>";
            }
            
            
      }
      
}
if (isset($_POST['logout-btn'])) {
      
      
      if ($_POST['logout'] == "ok") {
            @session_destroy();
            @header("Location: index.php");
            echo "<script>window.location='index.php'</script>";
      }
      
      
}


?>