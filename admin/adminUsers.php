<?php 
if( isset($_SESSION['sessionTokon']))
{       
        if(isset($_GET['action'])){
            
            if($_GET['action']=="addAdminUser") 
            {
    
                $first_name = htmlspecialchars($_POST['first_name'], ENT_QUOTES);
                $last_name = htmlspecialchars($_POST['last_name'], ENT_QUOTES);
                $email= htmlspecialchars($_POST['email'], ENT_QUOTES);
                $phone=htmlspecialchars($_POST['phone'], ENT_QUOTES);
    			$password=htmlspecialchars($_POST['password'], ENT_QUOTES);
    			$role="0";
    			$role_name='Super admin';
    			
                
                $url=$baseurl . 'addAdminUser';
                
                $data = array(
                    "first_name" => $first_name, 
                    "last_name" => $last_name, 
                    "email" => $email,
    				"phone" =>$phone,
    				"password"=> $password,
                    "role_name" => $role_name,
                    "role" => $role
    			);
                
                $json_data=@curl_request($data,$url);
               
                
                print_r(json_encode($data));
               
               if($json_data['code'] !== 200)
               {
                    echo "<script>window.location='dashboard.php?p=adminUsers&action=error'</script>";
               }
               else
               {
                    echo "<script>window.location='dashboard.php?p=adminUsers&action=success'</script>";
               }
    
                
            }
            else
            if($_GET['action']=="changeAdminPassword") 
            {
    
                $user_id = htmlspecialchars($_POST['user_id'], ENT_QUOTES);
                $password = htmlspecialchars($_POST['password'], ENT_QUOTES);
                
                $url=$baseurl . 'editAdminUserPassword';
                
                $data = array(
    				"user_id" => $user_id,
    				"password" => $password
				);
                
                $json_data=@curl_request($data,$url);
                
                
               if($json_data['code'] !== 200)
               {
                    echo "<script>window.location='dashboard.php?p=adminUsers&action=error'</script>";
               }
               else
               {
                    echo "<script>window.location='dashboard.php?p=adminUsers&action=success'</script>";
               }
    
                
            }
            else
            if($_GET['action']=="deleteUserAdmin") 
            {
    
                $user_id = htmlspecialchars($_GET['user_id'], ENT_QUOTES);
                
                $url=$baseurl . 'deleteAdminAccount';
                
                $data = array(
    				"user_id" => $user_id
				);
                
                $json_data=@curl_request($data,$url);
                
                
               if($json_data['code'] !== 200)
               {
                    echo "<script>window.location='dashboard.php?p=adminUsers&action=error'</script>";
               }
               else
               {
                    echo "<script>window.location='dashboard.php?p=adminUsers&action=success'</script>";
               }
    
                
            }
        }
        
        $url=$baseurl . 'showAdminUsers';
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
                                    <h2>All Admins</h2>
                                    <div class="head-area">
                                    </div>
                                </div>
                                
                                <div class="right" style="padding: 10px 0;">
                                    <button onclick="addAdminUser();" class="com-button com-submit-button com-button--large com-button--default">
                                        <div class="com-submit-button__content"><span>Add Admin</span></div>
                                    </button>
                                </div>
                                <!--start of datatable here-->


                                <table id="table_view" class="display" style="width:100%">
                                    <thead>
                                    <tr>
                                        <th>ID</th>
                                        <th>Name</th>
                                        <th>Email</th>
                                        <th>Phone</th>
                                        <th>Role</th>
                                        <th>Created</th>
                                        <th>Action</th>
                                    </tr>
                                    </thead>
                                    <tbody>

                                    <?php foreach ($allusers as $single_user): ?>


                                        <tr>
                                            <td><?php echo $single_user['UserAdmin']['id']; ?></td>
                                            <td style="width:150px; overflow:hidden;"><?php echo $single_user['UserAdmin']['first_name'] . " " . $single_user['UserAdmin']['last_name']; ?></td>
                                            <td>
                                                <?php echo $single_user['UserAdmin']['email']; ?>
                                            </td>
                                            <td>
                                                <?php echo $single_user['UserAdmin']['phone']; ?>
                                            </td>
                                            <td>
                                                <?php echo $single_user['UserAdmin']['role_name']; ?>
                                            </td>
                                            <td>
                                                <?php echo $single_user['UserAdmin']['created']; ?>
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
                                                            <li class="more-menu-item" role="presentation" onclick="changeAdminPassword(<?php echo $single_user['UserAdmin']['id']; ?>)">
                                                                <button type="button" class="more-menu-btn" role="menuitem">Change Password</button>
                                                            </li>
                                                            <a href="dashboard.php?p=adminUsers&action=deleteUserAdmin&user_id=<?php echo $single_user['UserAdmin']['id']; ?>">
                                                                <li class="more-menu-item" role="presentation" >
                                                                    <button type="button" class="more-menu-btn" role="menuitem">Delete</button>
                                                                </li>
                                                            </a>
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
        
        
        function addAdminUser() 
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
            xmlhttp.open("GET", "ajex-events.php?action=addAdminUser");
            xmlhttp.send();
        }
        
        function changeAdminPassword(id) 
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
            xmlhttp.open("GET", "ajex-events.php?action=changeAdminPassword&id="+id);
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