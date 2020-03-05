<?php 
if( isset($_SESSION['sessionPortal']))
{       
        if(isset($_GET['action'])){
            
            if($_GET['action']=="editProfile") 
            {
    
                $user_id = htmlspecialchars($_POST['user_id'], ENT_QUOTES);
                $first_name = htmlspecialchars($_POST['first_name'], ENT_QUOTES);
                $last_name = htmlspecialchars($_POST['last_name'], ENT_QUOTES);
                $email = htmlspecialchars($_POST['email'], ENT_QUOTES);
                $phone = htmlspecialchars($_POST['phone'], ENT_QUOTES);
                
                $url=$baseurl . 'editUserProfile';
                
                $data = array(
                    "user_id" => $user_id,
                    "first_name" => $first_name, 
                    "last_name" => $last_name, 
                    "email" => $email,
                    "phone" => $phone 
                  );
                
                $json_data=@curl_request($data,$url);
               
                
                //print_r(json_encode($data));
               // do some checking to make sure it sent
               //var_dump($json_data);
               //die();
        
               if($json_data['code'] !== 200)
               {
                    echo "<script>window.location='dashboard.php?p=users&action=error'</script>";
               }
               else
               {
                    echo "<script>window.location='dashboard.php?p=users&action=success'</script>";
               }
    
                
            }
            else
            if($_GET['action']=="changePassword") 
            {
    
                $user_id = htmlspecialchars($_POST['user_id'], ENT_QUOTES);
                $password = htmlspecialchars($_POST['password'], ENT_QUOTES);
                
                $url=$baseurl . 'editUserPassword';
                
                $data = array(
                    "user_id" => $user_id,
          		    "password" => $password
                );
                
                $json_data=@curl_request($data,$url);
                
               
               if($json_data['code'] !== 200)
               {
                    echo "<script>window.location='dashboard.php?p=users&action=error'</script>";
               }
               else
               {
                    echo "<script>window.location='dashboard.php?p=users&action=success'</script>";
               }
    
                
            }
        }
        
        $url=$baseurl . 'showAllUsers';
        $data = [];
        
        $json_data=@curl_request($data,$url);
        
        $allusers = [];
        if ($json_data['code'] == 200) {
            $allusers = $json_data['msg'];
        }

        ?>

        <div class="qr-content">
            <div class="qr-page-content">
                <div class="qr-page zeropadding">
                    <div class="qr-content-area">
                        <div class="qr-row">
                            <div class="qr-el">

                                <div class="page-title">
                                    <h2>All Users</h2>
                                    <div class="head-area">
                                    </div>
                                </div>

                                <!--start of datatable here-->


                                <table id="table_view" class="display" style="width:100%">
                                    <thead>
                                    <tr>
                                        <th>ID</th>
                                        <th>Name</th>
                                        <th>Email</th>
                                        <th>Phone</th>
                                        <th>Type</th>
                                        <th>Register Date</th>
                                        <th>Action</th>
                                    </tr>
                                    </thead>
                                    <tbody>

                                    <?php foreach ($allusers as $single_user): ?>


                                        <tr>
                                            <td><?php echo $single_user['User']['id']; ?></td>
                                            <td style="width:150px; overflow:hidden;"><?php echo $single_user['UserInfo']['first_name'] . " " . $single_user['UserInfo']['last_name']; ?></td>
                                            <td>
                                                <?php echo $single_user['User']['email']; ?>
                                            </td>
                                            <td>
                                                <?php echo $single_user['UserInfo']['phone']; ?>
                                            </td>
                                            <td>
                                                <?php echo $single_user['User']['role']; ?>
                                            </td>
                                            <td>
                                                <?php echo $single_user['User']['created']; ?>
                                            </td>
                                            
                                            
                                            <td>
                                                <div class="more">
                                                    <button id="more-btn" class="more-btn">
                                                        <span class="more-dot"></span>
                                                        <span class="more-dot"></span>
                                                        <span class="more-dot"></span>
                                                    </button>
                                                    <div class="more-menu">
                                                        <div class="more-menu-caret">
                                                            <div class="more-menu-caret-outer"></div>
                                                            <div class="more-menu-caret-inner"></div>
                                                        </div>
                                                        <ul class="more-menu-items" tabindex="-1" role="menu" aria-labelledby="more-btn" aria-hidden="true">
                                                            <li class="more-menu-item" role="presentation" onclick="editUser(<?php echo $single_user['User']['id']; ?>)">
                                                                <button type="button" class="more-menu-btn" role="menuitem">Edit</button>
                                                            </li>
                                                            <li class="more-menu-item" role="presentation" onclick="changePassword(<?php echo $single_user['User']['id']; ?>)">
                                                                <button type="button" class="more-menu-btn" role="menuitem">Change Password</button>
                                                            </li>
                                                            
                                                        </ul>
                                                    </div>
                                                </div>
                                                
                                            </td>
                                            
                                            
                                        </tr>

                                    <?php endforeach; ?>

                                    </tbody>
                                    <tfoot>
                                    <tr>
                                        <th>ID</th>
                                        <th>Name</th>
                                        <th>Email</th>
                                        <th>Phone</th>
                                        <th>Type</th>
                                        <th>Register Date</th>
                                        <th>Action</th>
                                    </tr>
                                    </tfoot>
                                </table>


                            </div>
                        </div>
                    </div>
                </div>

            </div>
        
    <script>
        $(document).ready(function () {
            $('#table_view').DataTable({
                    "pageLength": 100
                }
            );
            $('#table_view2').DataTable({
                    "pageLength": 35
                }
            );
        });
        
        
        function editUser(id) 
        {
            
            document.getElementById("PopupParent").style.display = "block";
            document.getElementById("contentReceived").innerHTML = "loading...";

            var xmlhttp;
            if (window.XMLHttpRequest) {// code for IE7+, Firefox, Chrome, Opera, Safari
                xmlhttp = new XMLHttpRequest();
            } else {// code for IE6, IE5
                xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
            }
            xmlhttp.onreadystatechange = function () {
                if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
                    // alert(xmlhttp.responseText);
                    document.getElementById('contentReceived').innerHTML = xmlhttp.responseText;
                }
            }
            xmlhttp.open("GET", "ajex-events.php?action=editUser&id="+id);
            xmlhttp.send();
        }
        
        function changePassword(id) 
        {
            
            document.getElementById("PopupParent").style.display = "block";
            document.getElementById("contentReceived").innerHTML = "loading...";

            var xmlhttp;
            if (window.XMLHttpRequest) {// code for IE7+, Firefox, Chrome, Opera, Safari
                xmlhttp = new XMLHttpRequest();
            } else {// code for IE6, IE5
                xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
            }
            xmlhttp.onreadystatechange = function () {
                if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
                    // alert(xmlhttp.responseText);
                    document.getElementById('contentReceived').innerHTML = xmlhttp.responseText;
                }
            }
            xmlhttp.open("GET", "ajex-events.php?action=changePassword&id="+id);
            xmlhttp.send();
        }
        
        
    </script>
    <?php
    
} 
else 
{
	
	@header("Location: index.php");
    echo "<script>window.location='index.php'</script>";
    die;
    
} ?>