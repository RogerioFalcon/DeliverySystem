<?php 
if( isset($_SESSION['sessionTokon']))
{       
        if(isset($_GET['action'])){
            
            if($_GET['action']=="addRider") 
            {
    
                $email = htmlspecialchars($_POST['email'], ENT_QUOTES);
                $password = htmlspecialchars($_POST['password'], ENT_QUOTES);
                $first_name = htmlspecialchars($_POST['first_name'], ENT_QUOTES);
                $last_name = htmlspecialchars($_POST['last_name'], ENT_QUOTES);
                $phone = htmlspecialchars($_POST['phone'], ENT_QUOTES);
                $city = htmlspecialchars($_POST['city'], ENT_QUOTES);
                $country = htmlspecialchars($_POST['country'], ENT_QUOTES);
                $address_to_start_shift = htmlspecialchars($_POST['address_to_start_shift'], ENT_QUOTES);
                $note = htmlspecialchars($_POST['note'], ENT_QUOTES);
                
                $device_token = "";
                $role = "rider";
                            
                $url=$baseurl . 'registerRider';
                
                $data = 
                    array(
                        "email" => $email, 
                        "password" => $password, 
                        "first_name" => $first_name, 
                        "last_name" => $last_name,
                        "phone" => $phone,
                        "device_token" => $device_token,
                        "role" => $role,
                        "city" => $city,
                        "country" => $country,
                        "address_to_start_shift" => $address_to_start_shift,
                        "note" => $note
                    );
                
                $json_data=@curl_request($data,$url);
               
                
             
               if($json_data['code'] !== 200)
               {
                    echo "<script>window.location='dashboard.php?p=rider&action=error'</script>";
               }
               else
               {
                    echo "<script>window.location='dashboard.php?p=rider&action=success'</script>";
               }
    
                
            }
            else
            if($_GET['action']=="blockUser") 
            {
    
                $user_id = htmlspecialchars($_GET['user_id'], ENT_QUOTES);
                $block = htmlspecialchars($_GET['block'], ENT_QUOTES);
                
                $url=$baseurl . 'userBlockStatus';
                
                $data = 
                    array(
                        "user_id" => $user_id, 
                        "block" => $block
                    );
                
                $json_data=@curl_request($data,$url);
               
               if($json_data['code'] !== 200)
               {
                    echo "<script>window.location='dashboard.php?p=rider&action=error'</script>";
               }
               else
               {
                    echo "<script>window.location='dashboard.php?p=rider&action=success'</script>";
               }
    
                
            }
            
        }
        
        $url=$baseurl . 'showRiders';
        $data = [];
        
        $json_data=@curl_request($data,$url);
        
        
        ?>

        <div class="qr-content">
            <div class="qr-page-content">
                <div class="qr-page zeropadding">
                    <div class="qr-content-area">
                        <div class="qr-row">
                            <div class="qr-el">

                                <div class="page-title">
                                    <h2>All Riders</h2>
                                    <div class="head-area">
                                    </div>
                                </div>

                                <div class="right" style="padding: 10px 0;">
                                   <button onclick="addRider();"
                                           class="com-button com-submit-button com-button--large com-button--default">
                                       <div class="com-submit-button__content"><span>Add Rider</span></div>
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
                                        <th>Online</th>
                                        <th>Action</th>
                                    </tr>
                                    </thead>
                                    <tbody>

                                    <?php foreach($json_data['msg']['OnlineRiders'] as $str => $data): ?>

                                        
                                        <tr>
                                            <td><?php echo $data['User']['id']; ?></td>
                                            <td><?php echo $data['UserInfo']['first_name'] . " " . $data['UserInfo']['last_name']; ?></td>
                                            <td>
                                                <?php echo $data['UserInfo']['phone']; ?>
                                            </td>
                                            <td>
                                                <?php echo $data['User']['email']; ?>
                                            </td>
                                            <td style="color:green;">
                                                Online
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
                                                            <li class="more-menu-item" role="presentation" onclick="editUser(<?php echo $data['User']['id']; ?>)">
                                                                <button type="button" class="more-menu-btn" role="menuitem">Details</button>
                                                            </li>
                                                            
                                                            <li class="more-menu-item" role="presentation">
                                                                <a href="dashboard.php?p=verifyDoc&user_id=<?php echo $data['User']['id']; ?>">
                                                                    <button type="button" class="more-menu-btn" role="menuitem">Verify Doc</button>
                                                                </a>    
                                                            </li>
                                                            
                                                            <?php
                                                                if($data['User']['block']=="0")
                                                                {
                                                                    ?>
                                                                        <li class="more-menu-item" role="presentation">
                                                                            <a href="dashboard.php?p=rider&action=blockUser&user_id=<?php echo $data['User']['id']; ?>&block=1">
                                                                                <button type="button" class="more-menu-btn" role="menuitem">Block</button>
                                                                            </a>    
                                                                        </li>
                                                                    <?php  
                                                                }
                                                                else
                                                                {
                                                                   ?>
                                                                        <li class="more-menu-item" role="presentation">
                                                                            <a href="dashboard.php?p=rider&action=blockUser&user_id=<?php echo $data['User']['id']; ?>&block=0">
                                                                                <button type="button" class="more-menu-btn" role="menuitem" style="color:red;">Unblock</button>
                                                                            </a>    
                                                                        </li>
                                                                   <?php 
                                                                }
                                                            ?>
                                                            
                                                            
                                                        </ul>
                                                    </div>
                                                </div>
                                                
                                            </td>
                                            
                                            
                                        </tr>

                                    <?php endforeach; ?>
                                    
                                    
                                    
                                    <?php foreach($json_data['msg']['OfflineRiders'] as $str => $data): ?>

                                        
                                        <tr>
                                            <td><?php echo $data['User']['id']; ?></td>
                                            <td><?php echo $data['UserInfo']['first_name'] . " " . $data['UserInfo']['last_name']; ?></td>
                                            <td>
                                                <?php echo $data['UserInfo']['phone']; ?>
                                            </td>
                                            <td>
                                                <?php echo $data['User']['email']; ?>
                                            </td>
                                            <td style="color:orange;">
                                                Offline
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
                                                            
                                                            <li class="more-menu-item" role="presentation" onclick="editUser(<?php echo $data['User']['id']; ?>)">
                                                                <button type="button" class="more-menu-btn" role="menuitem">Details</button>
                                                            </li>
                                                            <li class="more-menu-item" role="presentation">
                                                                <a href="dashboard.php?p=verifyDoc&user_id=<?php echo $data['User']['id']; ?>">
                                                                    <button type="button" class="more-menu-btn" role="menuitem">Verify Doc</button>
                                                                </a>    
                                                            </li>
                                                            
                                                            
                                                            <?php
                                                                if($data['User']['block']=="0")
                                                                {
                                                                    ?>
                                                                        <li class="more-menu-item" role="presentation">
                                                                            <a href="dashboard.php?p=rider&action=blockUser&user_id=<?php echo $data['User']['id']; ?>&block=1">
                                                                                <button type="button" class="more-menu-btn" role="menuitem">Block</button>
                                                                            </a>    
                                                                        </li>
                                                                    <?php  
                                                                }
                                                                else
                                                                {
                                                                   ?>
                                                                        <li class="more-menu-item" role="presentation">
                                                                            <a href="dashboard.php?p=rider&action=blockUser&user_id=<?php echo $data['User']['id']; ?>&block=0" style="color:red;">
                                                                                <button type="button" class="more-menu-btn" role="menuitem" style="color:red;">Unblock</button>
                                                                            </a>    
                                                                        </li>
                                                                   <?php 
                                                                }
                                                            ?>
                                                            
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
                                            <th>Online</th>
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
        
        function addRider() 
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
            xmlhttp.open("GET", "ajex-events.php?action=addRider");
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