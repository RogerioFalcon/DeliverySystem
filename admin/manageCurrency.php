<?php 
if( isset($_SESSION['sessionTokon']))
{   
    if(isset($_GET['action'])){
        if($_GET['action']=="addCurrency") 
        {

            $currency_name = htmlspecialchars($_POST['currency_name'], ENT_QUOTES);
            $country = htmlspecialchars($_POST['country'], ENT_QUOTES);
            $code = htmlspecialchars($_POST['code'], ENT_QUOTES);
            $symbol = htmlspecialchars($_POST['symbol'], ENT_QUOTES);
            
            $id = @$_POST['id'];
        
            $url=$baseurl . 'addCurrency';
            
            if($id=="")
            {
                $data = 
                    array(
                        "currency" => $currency_name, 
                        "country" => $country, 
                        "code" => $code, 
                        "symbol" => $symbol
                    );  
            }
            else
            {
                $data = 
                    array(
                        "id" =>$id,
                        "currency" => $currency_name, 
                        "country" => $country, 
                        "code" => $code, 
                        "symbol" => $symbol
                    );
            }
            
            
            $json_data=@curl_request($data,$url);
            
           if($json_data['code'] !== 200)
           {
                echo "<script>window.location='dashboard.php?p=manageCurrency&action=error'</script>";
           }
           else
           {
                echo "<script>window.location='dashboard.php?p=manageCurrency&action=success'</script>";
           }

            
        }
        else
        if($_GET['action']=="delete") 
        {

            $id = htmlspecialchars($_GET['id'], ENT_QUOTES);
            $url=$baseurl . 'deleteCurrency';
            
            $data = 
                array(
                    "id" => $id
                );
            
            $json_data=@curl_request($data,$url);
            
    
           if($json_data['code'] !== 200)
           {
                echo "<script>window.location='dashboard.php?p=manageCurrency&action=error'</script>";
           }
           else
           {
                echo "<script>window.location='dashboard.php?p=manageCurrency&action=success'</script>";
           }

            
        }
        
    } 
    
    
        $url=$baseurl . 'showCurrencies';
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
                                    <h2>Manage Currency</h2>
                                    <div class="head-area">
                                    </div>
                                </div>
                                
                                <?php
                                    if(count($allusers)=="0")
                                    {
                                        ?>
                                            <div class="right" style="padding: 10px 0;">
                                               <button onclick="addCurrency();"
                                                       class="com-button com-submit-button com-button--large com-button--default">
                                                   <div class="com-submit-button__content"><span>Add Currency</span></div>
                                               </button>
                                            </div>
                                        <?php
                                    }
                                ?>
                                
                                <!--start of datatable here-->


                                <table id="table_view" class="display" style="width:100%">
                                    <thead>
                                    <tr>
                                        <th>ID</th>
                                        <th>Country</th>
                                        <th>Currency</th>
                                        <th>Code</th>
                                        <th>Symbol</th>
                                        <th>Action</th>
                                    </tr>
                                    </thead>
                                    <tbody>

                                    <?php foreach ($allusers as $single_user): ?>


                                        <tr>
                                            <td><?php echo $single_user['Currency']['id']; ?></td>
                                            <td><?php echo $single_user['Currency']['country']; ?></td>
                                            <td>
                                                <?php echo $single_user['Currency']['currency']; ?>
                                            </td>
                                            <td>
                                                <?php echo $single_user['Currency']['code']; ?>
                                            </td>
                                            <td>
                                                <?php echo $single_user['Currency']['symbol']; ?>
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
                                                            <li class="more-menu-item" role="presentation" onclick="editCurrency(<?php echo $single_user['Currency']['id']; ?>)">
                                                                <button type="button" class="more-menu-btn" role="menuitem">Edit</button>
                                                            </li>
                                                            <li class="more-menu-item" role="presentation">
                                                                <a href="dashboard.php?p=manageCurrency&action=delete&id=<?php echo $single_user['Currency']['id']; ?>">
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
                                        <th>Country</th>
                                        <th>Currency</th>
                                        <th>Code</th>
                                        <th>Symbol</th>
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
        
        function addCurrency() 
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
            xmlhttp.open("GET", "ajex-events.php?action=addCurrency");
            xmlhttp.send();
        }
        
        function editCurrency(id) 
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
            xmlhttp.open("GET", "ajex-events.php?action=editCurrency&id="+id);
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