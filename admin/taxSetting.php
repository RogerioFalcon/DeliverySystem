<?php 
if( isset($_SESSION['sessionTokon']))
{
        if(isset($_GET['action'])){
            
            if($_GET['action']=="addTax") 
            {
    
                $city = htmlspecialchars($_POST['city'], ENT_QUOTES);
                $state = htmlspecialchars($_POST['state'], ENT_QUOTES);
                $country = htmlspecialchars($_POST['country'], ENT_QUOTES);
                $tax = htmlspecialchars($_POST['tax'], ENT_QUOTES);
                $deliveryfee = htmlspecialchars($_POST['deliveryfee'], ENT_QUOTES);
                $countrycode = htmlspecialchars($_POST['countrycode'], ENT_QUOTES);
                $delivery_time = htmlspecialchars($_POST['delivery_time'], ENT_QUOTES);
                $id=@$_POST['id'];
                
                $url=$baseurl . 'addTax';
                
                if(@$id=="")
                {
                   $data = 
                        array(
                            "city" => $city, 
                            "state" => $state, 
                            "country" => $country, 
                            "tax" => $tax,
                            "delivery_fee_per_km" => $deliveryfee,
                            "country_code" => $countrycode,
                            "delivery_time" => $delivery_time
                        ); 
                }
                else
                {
                     $data = 
                        array(
                            "id" =>$id,
                            "city" => $city, 
                            "state" => $state, 
                            "country" => $country, 
                            "tax" => $tax,
                            "delivery_fee_per_km" => $deliveryfee,
                            "country_code" => $countrycode,
                            "delivery_time" => $delivery_time
                        ); 
                }
                
                
                
                $json_data=@curl_request($data,$url);
                //$json_data=$json_data['msg'];
                
               // do some checking to make sure it sent
              //var_dump($json_data);
              //die;
        
               if($json_data['code'] !== 200)
               {
                    echo "<script>window.location='dashboard.php?p=taxSetting&action=error'</script>";
               }
               else
               {
                    echo "<script>window.location='dashboard.php?p=taxSetting&action=success'</script>";
               }
    
                
            }
            else
            if($_GET['action']=="deleteTax") 
            {
    
                $id = htmlspecialchars($_GET['id'], ENT_QUOTES);
                
                $url=$baseurl . 'deleteTax';
                
                $data = 
                    array(
                        "id" => $id
                    );
                
                $json_data=@curl_request($data,$url);
                //$json_data=$json_data['msg'];
                
                // do some checking to make sure it sent
                //var_dump($json_data);
                //die;
        
               if($json_data['code'] !== 200)
               {
                    echo "<script>window.location='dashboard.php?p=taxSetting&action=error'</script>";
               }
               else
               {
                    echo "<script>window.location='dashboard.php?p=taxSetting&action=success'</script>";
               }
    
                
            }
        }
    
        
        $url=$baseurl . 'showAllTaxes';
        $data = [];
        
        $json_data=@curl_request($data,$url);
        
        $allusers = [];
        if ($json_data['code'] == 200) 
        {
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
                                    <h2>Tax Information</h2>
                                    <div class="head-area">
                                    </div>
                                </div>
                                
                                <?php
                                    //if(count($allusers)==0)
                                    //{
                                        ?>
                                            <div class="right" style="padding: 10px 0;">
                                               <button onclick="addTax();" class="com-button com-submit-button com-button--large com-button--default">
                                                   <div class="com-submit-button__content"><span>Add Tax</span></div>
                                               </button>
                                            </div>
                                        <?php
                                    //}
                                ?>
                                
                                <!--start of datatable here-->


                                <table id="table_view" class="display" style="width:100%">
                                    <thead>
                                    <tr>
                                        <th>ID</th>
                                        <th>City</th>
                                        <th>State</th>
                                        <th>Country</th>
                                        <th>Country Code</th>
                                        <th>Tax</th>
                                        <th>Delivery fee (per Km)</th>
                                        <th>Action</th>
                                    </tr>
                                    </thead>
                                    <tbody>

                                    <?php foreach ($allusers as $single_user): ?>


                                        <tr>
                                            <td><?php echo $single_user['Tax']['id']; ?></td>
                                            <td><?php echo $single_user['Tax']['city']; ?></td>
                                            <td>
                                                <?php echo $single_user['Tax']['state']; ?>
                                            </td>
                                            <td>
                                                <?php echo $single_user['Tax']['country']; ?>
                                            </td>
                                            <td>
                                                <?php echo $single_user['Tax']['country_code']; ?>
                                            </td>
                                            <td>
                                                <?php echo $single_user['Tax']['tax']."%"; ?>
                                            </td>
                                            <td>
                                                <?php echo $single_user['Tax']['delivery_fee_per_km']; ?>
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
                                                            <li class="more-menu-item" role="presentation" onclick="editTax(<?php echo $single_user['Tax']['id']; ?>)">
                                                                <button type="button" class="more-menu-btn" role="menuitem">Edit</button>
                                                            </li>
                                                            <li class="more-menu-item" role="presentation">
                                                                <a href="dashboard.php?p=taxSetting&action=deleteTax&id=<?php echo $single_user['Tax']['id']; ?>" onclick="return ConfirmDelete()">
                                                                    <button type="button" class="more-menu-btn" role="menuitem">Delete</button>
                                                                </a>
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
                                        <th>City</th>
                                        <th>State</th>
                                        <th>Country</th>
                                        <th>Country Code</th>
                                        <th>Tax</th>
                                        <th>Delivery fee (per Km)</th>
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
        
        function addTax() 
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
            xmlhttp.open("GET", "ajex-events.php?action=addTax");
            xmlhttp.send();
        }
        
        function editTax(id) 
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
            xmlhttp.open("GET", "ajex-events.php?action=editTax&id="+id);
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