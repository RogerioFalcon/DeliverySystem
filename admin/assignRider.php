<?php 
if( isset($_SESSION['sessionTokon']))
{
        $url=$baseurl . 'showRiders';
        $data = "";
        //echo json_encode($data);
        $json_data=@curl_request($data,$url);
        
        $allusers = [];
        if ($json_data['code'] == 200) {
            $allusers = $json_data['msg'];
        }
        
        
        if(@$_GET['AssignToRider']=="ok")
    	{
    		$rider_user_id = $_GET['rider_user_id'];
    		$assigner_user_id = $_SESSION['id'];
    		$order_id = $_GET['order_id'];
    
    		$headers = array(
    		    "Accept: application/json",
    		    "Content-Type: application/json"
    		   );
    		   $data = array("rider_user_id" => $rider_user_id, "assigner_user_id" => $assigner_user_id, "order_id" => $order_id);
    		   
    		 
    		   //die();
    		   
    		   $ch = curl_init( $baseurl.'assignOrderToRider' );
    		   curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
    		   curl_setopt($ch, CURLOPT_CUSTOMREQUEST, 'POST');
    		   curl_setopt($ch, CURLOPT_POSTFIELDS, json_encode($data));
    		   curl_setopt($ch, CURLOPT_HTTPHEADER, $headers);
    		   $return = curl_exec($ch);
    		   $curl_error = curl_error($ch);
    		   $http_code = curl_getinfo($ch, CURLINFO_HTTP_CODE);
    		   curl_close($ch);
    		   // do some checking to make sure it sent
    		   //var_dump($http_code);
    		   //die();
    		   //print_r($return);
    		   if($http_code == "200")
    		   {
    		    	echo "<script>window.location='dashboard.php?p=manageOrders&status=success'</script>";
    		   }
    		   else
    		   {
    		    	echo "<script>window.location='dashboard.php?p=manageOrders&status=success'</script>";
    		   }
    
    	}
    	
    	
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
                                
                                <script src="https://maps.google.com/maps/api/js?key=AIzaSyAEDq8M6WsXVmo_08lPapjlqYCFVRBt6ro"></script>
                        		<!--<script src="../js/locationpicker.jquery.js"></script>-->
                         		<div id="map" style="height: 35%; width: 100%; float: left;margin-bottom: 30px !important;" class="qr-el qr-el-1"></div>
                         	
                         		<script>
                        
                                
                                    window.onload = function () {
                                      initialize();
                                    //   generateMarkers(
                                    //     ['malik pura', 31.45623, 73.12974],
                                    //     ['Nishat ababd', 31.461088, 73.112302],
                                    //     ['ameen town',31.4442286, 73.1175795]
                                        
                                    //   );
                                    };
        
                                    var map;
        
                                    // Cretes the map
                                 
                                     function initialize() 
                                     {
            
                
        							         <?php  
        							         
        					                    $url = $baseurl."showRiders"; $params = ""; $ch = curl_init($url); curl_setopt($ch, CURLOPT_POST, 1);
        					                    curl_setopt($ch, CURLOPT_POSTFIELDS, $params);
        					                    curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
        					                    $result = curl_exec($ch);
        					                    $json_data = json_decode($result, true);
        					                    
        					                    foreach($json_data['msg']['OnlineRiders'] as $str => $data) 
        					                    {
        					                        foreach($data['RiderLocation'] as $d=>$rd)
        					                        {
        					                           
        					                            $var ='var '.'a'.$rd['RiderLocation']['id'].' = {
        					                           	b'. $rd['RiderLocation']['user_id'].
        					                           ': '.
        					                           '"'.'<srtong><b>'.$data['UserInfo']['first_name'].' '.$data['UserInfo']['last_name']."<p  style='margin:0px;padding:0px;display:inherit;color:#0c930b'>".'(Online)'."</p></b>".'<br/>'. $rider_full_name= $rd['RiderLocation']['city']." , ".$rd['RiderLocation']['country'].'<br/>'.'<b>Start Shift Place:</b> '. $rd['RiderLocation']['address_to_start_shift'].'<br/>'.'<b>Phone:</b>  +92'.$data['UserInfo']['phone'].'<br/>'.'<b>Order Count:</b> '.$data['UserInfo']['total_orders']."<br/><a href='https://www.google.com/maps/place/".$rd['RiderLocation']['lat'].','.$rd['RiderLocation']['long']."'  style='margin-top:5px; color:#be2c2c; text-decoration:underline;'>View On Map</a><br><a class='btn btn-danger btn-block btn-sm' href='?p=assignRider&AssignToRider=ok&rider_user_id=".$data['UserInfo']['user_id']."&assigner_user_id=".@$_SESSION['id']."&order_id=".$_GET['orderID']."' style='background:#be2c2c; margin-top:5px; color:white; text-decoration: none; width:75%; padding:5px 0px; font-size: 11px;' >Assign Order</a><br><br>".'</srtong>'.'",'.$rider_full_name='lat:'. $rd['RiderLocation']['lat'].",long:".$rd['RiderLocation']['long'].",countt:".$data['UserInfo']['total_orders']; 
        					                                 echo $var.''.' }; ';
        					                             
        					                                        
        					                         }
        					                    }
        							                       
        							        ?>
        		          
        
        							    
        							    var locations = [
        							    
        								    <?php  
        								     
        								        $url = $baseurl."showRiders"; $params = ""; $ch = curl_init($url); curl_setopt($ch, CURLOPT_POST, 1);
        								        curl_setopt($ch, CURLOPT_POSTFIELDS, $params);
        								        curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
        								        $result = curl_exec($ch);
        								        $json_data = json_decode($result, true);
        								        foreach($json_data['msg']['OnlineRiders'] as $str => $data) 
        								        {
        								            foreach($data['RiderLocation'] as $d=>$rd)
        								            {
        								                $var ='[a'.$rd['RiderLocation']['id'].'.b'.$rd['RiderLocation']['user_id'].', a'.$rd['RiderLocation']['id'].'.lat'.', a'.$rd['RiderLocation']['id'].'.long'.', a'.$rd['RiderLocation']['id'].'.countt'.',0],';
        								                echo $var;
        								            }
        								        }
        								     
        								    
        								                       
        								    ?>
        							    
        							    ];
            
            
        
        		        				var map = new google.maps.Map(document.getElementById('map'), {
        		        				zoom: 14,
        		        
        						            <?php  
        						                    //  $url = $baseurl."/showRiders"; $params = ""; $ch = curl_init($url); curl_setopt($ch, CURLOPT_POST, 1);
        						                    // curl_setopt($ch, CURLOPT_POSTFIELDS, $params);
        						                    // curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
        						                    // $result = curl_exec($ch);
        						                    // $json_data = json_decode($result, true);
        						                    // foreach($json_data['msg']['OnlineRiders'] as $str => $data) 
        						                    // {
        						                    //     foreach($data['RiderLocation'] as $d=>$rd)
        						                    //     {
        						                    //         //$var ='center: new google.maps.LatLng'.'('. $rider_full_name= $rd['RiderLocation']['lat']." , ".$rd['RiderLocation']['long'].')'; 
        
        						                    //          $var ='center: new google.maps.LatLng'.'('. $_GET['orderLocation'] .')'; 
        						                    //          echo $var.','.'';
        						                    //          //echo "icon: 'http://maps.google.com/mapfiles/ms/icons/blue-dot.png'";
        						                                        
        						                    //      }
        						                    // }
        						    
        						                       
        						            ?>
        		       						center: new google.maps.LatLng(<?php echo $_GET['hotelLocation']; ?>),
        		       						mapTypeId: google.maps.MapTypeId.ROADMAP
        		    					});
        
        
        		        				
        
        			                    var infowindow = new google.maps.InfoWindow({});
        
        			                    var marker, i;
        			                    
        			                    
        			                    for (i = 0; i < locations.length; i++) 
        			                    {
        
        			                    	
        			                    	if(locations[i][3]!="0")
        			                        {
        			                        	marker = new google.maps.Marker({
        			                            position: new google.maps.LatLng(locations[i][1], locations[i][2]),
        			                            map: map ,
        			                            title:'Rider',
        			                            icon: 'http://maps.google.com/mapfiles/ms/icons/red-dot.png'
        				                        });
        
        				                        google.maps.event.addListener(marker, 'click', (function (marker, i) {
        				                            return function () {
        				                                infowindow.setContent(locations[i][0]);
        				                                infowindow.open(map, marker);
        				                            }
        				                        })(marker, i));
        			                        }
        			                        else
        			                        {
        			                        	marker = new google.maps.Marker({
        			                            position: new google.maps.LatLng(locations[i][1], locations[i][2]),
        			                            map: map ,
        			                            title:'Rider',
        			                            icon: 'http://maps.google.com/mapfiles/ms/icons/blue-dot.png'
        				                        });
        
        				                        google.maps.event.addListener(marker, 'click', (function (marker, i) {
        				                            return function () {
        				                                infowindow.setContent(locations[i][0]);
        				                                infowindow.open(map, marker);
        				                            }
        				                        })(marker, i));
        			                        }	
        
        			                        
        
        
        			                    }
        
        
        
        			                    marker = new google.maps.Marker({
        		                            //position: new google.maps.LatLng(locations[i][1], locations[i][2]),
        		                            position: new google.maps.LatLng(<?php echo $_GET['orderLocation']; ?>),
        		                            map: map ,
        		                            title:'Customer Location',
        		                            icon: 'assets/house.png'
        		                        });
        		                        marker = new google.maps.Marker({
        		                            //position: new google.maps.LatLng(locations[i][1], locations[i][2]),
        		                            position: new google.maps.LatLng(<?php echo $_GET['hotelLocation']; ?>),
        		                            map: map ,
        		                            title:'Hotel Location',
        		                            icon: 'assets/hotel.png'
        		                        });
        
        						}
        
        
        
                                </script>
                               
                                <div class="qr-el qr-el-3">
                                    <div>
                                     
                                        <table id="table_view" class="display" style="width:100%;">
                                            <thead>
                                                <tr>
                                                    <th>ID</th>
                                                    <th>Name</th>
                                                    <th>Start Location</th>
                                                    <th>Action</th>
                                                </tr>
                                            </thead>
                                            <tbody>
        
                                           <?php 
        	                	                foreach($allusers['OnlineRiders'] as $str => $data) 
                            					{	
                            						//print_r($json_data);
                            	                       ?>
                            	                        <tr>
                            							    <td><?php echo $data['UserInfo']['user_id']; ?></td>
                            	                            <td>	
                            	                            	<?php echo $data['UserInfo']['first_name']." ".$data['UserInfo']['last_name'] ?>
                            	                            </td>
                            	                            <td>	
                            	                            	<?php echo $data['RiderLocation'][0]['RiderLocation']['address_to_start_shift']; ?>
                            	                            </td>
                            	                            <td><a href="?p=assignRider&AssignToRider=ok&rider_user_id=<?php echo $data['UserInfo']['user_id']; ?>&assigner_user_id=<?php echo @$_SESSION['id']; ?>&order_id=<?php echo $_GET['orderID']; ?>" style='background:#be2c2c; margin-top:5px; color:white; text-decoration: none; width:100%; padding:5px 20px; ' >Assign Order</a></td>
                            	                        </tr>
                            	                      <?php
                            	                }
                            
                            	                curl_close($ch);
                        	                ?>
        
                                            </tbody>
                                            <tfoot>
                                                <tr>
                                                    <th>ID</th>
                                                    <th>Name</th>
                                                    <th>Start Location</th>
                                                    <th>Action</th>
                                                </tr>
                                            </tfoot>
                                        </table>
                                    </div>
                                </div>
                                
                                <div style="clear:both;"></div>
                                
                            </div>
                        </div>
                    </div>
                </div>

            </div>
     <script>
            // window.onload = function () {
            //   initialize();
            //   generateMarkers(
            //     ['malik pura', 31.45623, 73.12974],
            //     ['Nishat ababd', 31.461088, 73.112302],
            //     ['ameen town',31.4442286, 73.1175795]
                
            //   );
            // };
    
            //var map;
    </script>   
    
    <script>
        $(document).ready(function () {
            $('#table_view').DataTable({
                    "pageLength": 10
                }
            );
            $('#table_view2').DataTable({
                    "pageLength": 35
                }
            );
        });

    </script>
    <?php
    
} 
else 
{
	
	@header("Location: index.php");
    echo "<script>window.location='index.php'</script>";
    die;
    
} ?>