<?php 
if( isset($_SESSION['sessionTokon']))
{
        $url=$baseurl . 'showAllOrders';
        
        if(@$_GET['filter']=="completed")
        {
            $data =array (
                  'status' => 
                         array (
                            array (
                              'status' => '2',
                            )
                        ),
                );  
        }
		else
        if(@$_GET['filter']=="restaurantRejected")
        {
            $data =array (
                  'status' => 
                         array (
                            array (
                              'status' => '3',
                            )
                        ),
                );
        }
		else
        if(@$_GET['filter']=="AllOrder")
        {
            $data =array (
                  'status' => 
                         array (
                            array (
                              'status' => '0',
                            ),
                            array (
                              'status' => '1',
                            ),
                            array (
                              'status' => '2',
                            ),
                            array (
                              'status' => '3',
                            ),
                            array (
                              'status' => '4',
                            )
                        ),
                );
        }
        else
        {
            $data =array (
                  'status' => 
                         array (
                            array (
                              'status' => '0',
                            ),
                            array (
                              'status' => '1',
                            ),
                            array (
                              'status' => '3',
                            )
                        ),
                );
        }
        //echo json_encode($data);
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

                                <div class="page-title" >
                                    <h2>All Orders</h2>
                                    <div class="head-area">
                                    </div>
                                </div>

                                <div class="right" style="padding: 10px 0;">
                                    <a href="dashboard.php?p=manageOrders&filter=AllOrder" style="color:white;">
                                        <button class="com-button com-submit-button com-button--large com-button--default" style="padding: 5px 15px;font-size: 12px;">
                                           <div class="com-submit-button__content"><span>All Orders</span></div>
                                        </button>
                                    </a>
                                    
                                    <a href="dashboard.php?p=manageOrders&filter=AssignedOrders" style="color:white;">
                                        <button class="com-button com-submit-button com-button--large com-button--default" style="padding: 5px 15px;font-size: 12px;">
                                           <div class="com-submit-button__content"><span>Assigned Orders</span></div>
                                        </button>
                                    </a>
                                    
                                    <a href="dashboard.php?p=manageOrders" style="color:white;">
                                        <button class="com-button com-submit-button com-button--large com-button--default" style="padding: 5px 15px;font-size: 12px;">
                                           <div class="com-submit-button__content"><span>Active Orders</span></div>
                                        </button>
                                    </a>
                                    
                                    <a href="dashboard.php?p=manageOrders&filter=completed" style="color:white;">
                                        <button class="com-button com-submit-button com-button--large com-button--default" style="padding: 5px 15px;font-size: 12px;">
                                           <div class="com-submit-button__content"><span>Completed Orders</span></div>
                                        </button>
                                    </a>
                                    
                                    <a href="dashboard.php?p=manageOrders&filter=restaurantRejected" style="color:white;">
                                        <button class="com-button com-submit-button com-button--large com-button--default" style="padding: 5px 15px;font-size: 12px;">
                                           <div class="com-submit-button__content"><span>Rejected From Restaurant</span></div>
                                        </button>
                                    </a>
                                </div>
                                <div style="clear:both;"></div>
                                
                                <table id="table_view" class="display" style="width:100%">
                                    <thead>
                                    <tr>
                                        <th>ID</th>
                                        <th>Name</th>
                                        <th>Restaurant</th>
                                        <th>Price</th>
                                        <th>Type</th>
                                        <th>Status</th>
                                        <th>Created</th>
                                        <th>Action</th>
                                    </tr>
                                    </thead>
                                    <tbody>

                                    <?php foreach ($json_data['msg'] as $single_user): ?>


                                        <tr>
                                            <td><?php echo $single_user['Order']['id']; ?></td>
                                            <td><?php echo $single_user['UserInfo']['first_name'] . " " . $single_user['UserInfo']['last_name']; ?></td>
                                            <td><?php echo $single_user['Restaurant']['name']; ?></td>
                                            <td>
                                                <?php echo $single_user['Order']['price']; ?>
                                            </td>
                                            <td>
                                                <?php 
                                                    if($single_user['Order']['delivery']=="1")
                                                    {
                                                        ?>
                                                           <span style="color:black;" class='fa fa-biking' title="delivery"></span> 
                                                        <?php
                                                    }
                                                    else
                                                    {
                                                        ?>
                                                           <span style="color:black;" class='fa fa-concierge-bell' title="take away"></span> 
                                                        <?php
                                                    }
                                                ?>
                                            </td>
                                            <td>
                                                <?php 
                                                    $riderOrderCount=count($single_user['Order']['RiderOrder']);
                                                    
                                                    if($single_user['Order']['hotel_accepted']=="0")
                                                    {
                                                        ?>
                                                           <span style='color: #ff8700;font-weight: 600;'>Pending from restaurant</span> 
                                                        <?php
                                                    }
                                                    else
                                                    if($single_user['Order']['hotel_accepted']=="2")
                                                    {
                                                        ?>
                                                           <span style='color: red;font-weight: 600;' title="<?php echo $single_user['Order']['rejected_reason']; ?>">Rejected from restaurant</span> 
                                                        <?php
                                                    }
                                                    else
                                                    if($single_user['Order']['hotel_accepted']=="1" && $riderOrderCount=="0" && $single_user['Order']['delivery']=="1")
                                                    {
                                                        ?>
                                                           <span style='color: green;font-weight: 600;'>Waiting Assign Rider</span> 
                                                        <?php
                                                    }
                                                    else
                                                    if($riderOrderCount!="0")
                                                    {
                                                        echo $single_user['Order']['RiderOrder']['order_status'];   
                                                    }
                                                    
                                                    if($single_user['Order']['delivery']=="0")
                                                    {
                                                        ?>
                                                           <span style='color: black;font-weight: 600;'>Take Away Order</span> 
                                                        <?php
                                                    }
                                                ?>
                                            </td>
                                            <td><?php echo $single_user['Order']['created']; ?></td>
                                            
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
                                                            <li class="more-menu-item" role="presentation" onclick="orderDetails('<?php echo $single_user['Order']['id'];?>')">
                                                                <button type="button" class="more-menu-btn" role="menuitem">Details</button>
                                                            </li>
                                                            
                                                            <?php
                                                                if($single_user['Order']['hotel_accepted']=="1" && $riderOrderCount=="0" && $single_user['Order']['delivery']=="1")
                                                                {
                                                                    ?>
                                                                        <a href="dashboard.php?p=assignRider&getriderlist=ok&orderID=<?php echo $single_user['Order']['id']; ?>&orderLocation=<?php echo $single_user['Address']['lat']; ?>,<?php echo $single_user['Address']['long']; ?>&hotelLocation=<?php echo $single_user['Restaurant']['RestaurantLocation']['lat']; ?>,<?php echo $single_user['Restaurant']['RestaurantLocation']['long']; ?>" target="_blank">
                                                                            <li class="more-menu-item" role="presentation">
                                                                                <button type="button" class="more-menu-btn" role="menuitem">Assign Rider</button>
                                                                            </li>
                                                                        </a>    
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
                                        <th>Type</th>
                                        <th>Status</th>
                                        <th>Created</th>
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
                    "pageLength": 25,
                    "order": [[ 0, "desc" ]],
                }
              
            );
        
            
        });
        
        
        function orderDetails($id)
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
            xmlhttp.open("GET", "ajex-events.php?action=orderDetails&id="+$id);
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