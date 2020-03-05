<?php
@session_start();
include("constant.php");

if (isset($_GET['action'])) {
      
      if ($_GET['action'] == "add_salary") {
            
            
            $transfer_amount = htmlspecialchars($_POST['transfer_amount'], ENT_QUOTES);
            $transfer_date = htmlspecialchars($_POST['transfer_date'], ENT_QUOTES);
            $transfer_source = $_POST['transfer_source'];
            $note = htmlspecialchars($_POST['note'], ENT_QUOTES);
            $user_id = htmlspecialchars($_POST['user_id'], ENT_QUOTES);
            
            $headers = [
                "Accept: application/json",
                "Content-Type: application/json"
            ];
            
            $data = [
                "user_id" => $user_id,
                "transfer_amount" => $transfer_amount,
                "transfer_source" => $transfer_source,
                "transfer_date" => $transfer_date,
                "note" => $note,
            
            ];
            
            
            $ch = curl_init($baseurl . 'salaries_paid');
            curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
            curl_setopt($ch, CURLOPT_CUSTOMREQUEST, 'POST');
            curl_setopt($ch, CURLOPT_POSTFIELDS, json_encode($data));
            curl_setopt($ch, CURLOPT_HTTPHEADER, $headers);
            $return = curl_exec($ch);
            $json_data = json_decode($return, true);
            $curl_error = curl_error($ch);
            $http_code = curl_getinfo($ch, CURLINFO_HTTP_CODE);
            
            
            if ($json_data['code'] == 201) {
                  echo "<script>window.location='" . $frontend_url . "/all_users.php?error'</script>";
            }
            else {
                  
                  echo "<script>window.location='" . $frontend_url . "/all_users.php?sucess'</script>";
            }
      }
      
      else
      if ($_GET['action'] == "leave_apply") {
            
            $start_date = htmlspecialchars($_POST['start_date'], ENT_QUOTES);
            $end_date = htmlspecialchars($_POST['end_date'], ENT_QUOTES);
            $reason = htmlspecialchars($_POST['reason'], ENT_QUOTES);
            $user_id = htmlspecialchars($_POST['user_id'], ENT_QUOTES);
            
            $headers = [
                "Accept: application/json",
                "Content-Type: application/json"
            ];
            
            $data = [
                "user_id" => $user_id,
                "reason" => $reason,
                "leave_date" => $start_date,
                "status" => 0,
                "leave_end" => $end_date,
            
            ];
            
            $ch = curl_init($baseurl . 'apply_leave');
            curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
            curl_setopt($ch, CURLOPT_CUSTOMREQUEST, 'POST');
            curl_setopt($ch, CURLOPT_POSTFIELDS, json_encode($data));
            curl_setopt($ch, CURLOPT_HTTPHEADER, $headers);
            $return = curl_exec($ch);
            $json_data = json_decode($return, true);
            $curl_error = curl_error($ch);
            $http_code = curl_getinfo($ch, CURLINFO_HTTP_CODE);
            print_r(json_encode($data));
            exit();
            if ($json_data['code'] == 201) {
                  echo "<script>window.location='" . $frontend_url . "/leave_applicaton_user.php?error'</script>";
            }
            else {
                  
                  echo "<script>window.location='" . $frontend_url . "/leave_applicaton_user.php?sucess'</script>";
            }
      }
}

?>