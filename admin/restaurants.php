<?php
if (isset($_SESSION['sessionTokon'])) {
    if (isset($_GET['action'])) {

        if ($_GET['action'] == "blockRestaurant") {

            $id = htmlspecialchars($_GET['id'], ENT_QUOTES);
            $block = htmlspecialchars($_GET['block'], ENT_QUOTES);

            $url = $baseurl . 'blockRestaurant';

            $data = array(
                "id" => $id,
                "block" => $block
            );


            $json_data = @curl_request($data, $url);

            $json_return = $json_data['msg'];
            
            if ($json_data['code'] !== 200) {
                echo "<script>window.location='dashboard.php?p=restaurants&action=error'</script>";
            } else {
                echo "<script>window.location='dashboard.php?p=restaurants&action=success'</script>";
            }


        }
        if ($_GET['action'] == "favRestaurant") {

            $id = htmlspecialchars($_GET['id'], ENT_QUOTES);
            $promoted = htmlspecialchars($_GET['promoted'], ENT_QUOTES);

            $url = $baseurl . 'promotedRestaurant';

            $data = array(
                "id" => $id,
                "promoted" => $promoted
            );


            $json_data = @curl_request($data, $url);

            $json_return = $json_data['msg'];
           
           if ($json_data['code'] !== 200) {
                echo "<script>window.location='dashboard.php?p=restaurants&action=error'</script>";
            } else {
                echo "<script>window.location='dashboard.php?p=restaurants&action=success'</script>";
            }


        }
        else
        if ($_GET['action'] == "SingleRestaurant") {

            $id = htmlspecialchars($_GET['id'], ENT_QUOTES);
            $single_restaurant = htmlspecialchars($_GET['single_restaurant'], ENT_QUOTES);

            $url = $baseurl . 'updateSingleRestaurant';

            $data = array(
                "restaurant_id" => $id,
                "single_restaurant" => $single_restaurant
            );


            $json_data = @curl_request($data, $url);

            $json_return = $json_data['msg'];
           

            if ($json_data['code'] !== 200) {
                echo "<script>window.location='dashboard.php?p=restaurants&action=error'</script>";
            } else {
                echo "<script>window.location='dashboard.php?p=restaurants&action=success'</script>";
            }


        } else
            if ($_GET['action'] == "addRestaurant") {
               // print_r($_POST);

                $searchReplaceArray = array(
                    '(' => '',
                    ')' => '',
                    '-' => '',
                    '_' => '',
                    ' ' => ''
                );

                for ($i = 0; $i < 7; $i++) {
                    $opening_time[$i] = $_POST['opening_time'][$i];
                    $closing_time[$i] = $_POST['closing_time'][$i];
                    $day[$i] = $_POST['day'][$i];

                    $restaurant_timings_details[] = array('opening_time' => $opening_time[$i], 'closing_time' => $closing_time[$i], 'day' => $day[$i]);
                }
                $name = $_POST['name'];
               // $currency_id = $_POST['currency_id'];//
                $preparation_time = 30;//
                $speciality = $_POST['speciality'];//
                $tax_id = $_POST['tax_id'];//
                $slogan = $_POST['slogan'];
                $about = $_POST['about'];
                $phone = str_replace(array_keys($searchReplaceArray), array_values($searchReplaceArray), $_POST['phone']);
                $timezone = $_POST['timezone'];
                $state = $_POST['state'];
                //$menu_style = $_POST['menu_style'];
               // $promoted = $_POST['promoted'];
                $city = $_POST['city'];
                $country = $_POST['country'];
                $zip = $_POST['zip'];
                $lat = $_POST['lat'];
                $long = $_POST['long'];
                $restaurant_timing = $restaurant_timings_details;
                /*$opening_time = $_POST['opening_time'];
                $closing_time = $_POST['closing_time'];
                $day = $_POST['day'];*/
                $email = $_POST['email'];
                $password = $_POST['password'];
                $first_name = $_POST['first_name'];
                $last_name = $_POST['last_name'];
                $currencyid = $_POST['currencyid'];
                //$image = $_POST['image'];

                $min_order_price = $_POST['min_order_price'];
                $delivery_free_range = $_POST['delivery_free_range'];
                $preparation_time = $_POST['preparation_time'];
                $tax_free = $_POST['tax_free'];
                //$google_analytics = $_POST['google_analytics'];
               // $notes = $_POST['notes'];
                $added_by = $_SESSION['id'];


                $image_base = file_get_contents($_FILES['upload_image']['tmp_name']);
                $image = base64_encode($image_base);
                //Cover_upload_image
                $image_base1 = file_get_contents($_FILES['Cover_upload_image']['tmp_name']);
                $image1 = base64_encode($image_base1);


                $headers = array(
                    "Accept: application/json",
                    "Content-Type: application/json"
                );
                $data = array(
                    "name" => $name,
                 "currency_id" => $currencyid,//
                    "preparation_time" => $preparation_time,//
                    "speciality" => $speciality,//

                    "min_order_price" => $min_order_price,//
                    "delivery_free_range" => $delivery_free_range,//
                    "preparation_time" => $preparation_time,//
                    "tax_free" => $tax_free,//
                    "google_analytics" => "",//
                    "notes" => "",//
                    "added_by" => $added_by,//


                    "tax_id" => $tax_id,//
                    "slogan" => $slogan,
                    "about" => $about,
                    "phone" => $phone,
                    "timezone" => $timezone,
                    "state" => $state,
                    "menu_style" => "",
                  "promoted" => "",
                    "city" => $city,
                    "country" => $country,
                    "zip" => $zip,
                    "lat" => $lat,
                    "long" => $long,
                    "restaurant_timing" => $restaurant_timing,
                    /*"opening_time" => $opening_time,
                    "closing_time" => $closing_time,
                    "day" => $day, */
                    "email" => $email,
                    "password" => $password,
                    "first_name" => $first_name,
                    "last_name" => $last_name,
                    //"image" => $image
                    "image" => array("file_data" => $image),
                    "cover_image" => array("file_data" => $image1)
                );


                $url = $baseurl . 'addRestaurant';
                $json_data = @curl_request($data, $url);

                if ($json_data['code'] == 200) {
                    echo "<script>window.location='dashboard.php?p=restaurants&action=success'</script>";
                }

            }

    }

    $url = $baseurl . 'showRestaurants';
    $data = [];

    $json_data = @curl_request($data, $url);

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
                            <h2>All Restaurants</h2>
                            <div class="head-area">
                            </div>
                        </div>

                        <div class="right" style="padding: 10px 0;">
                            <button onclick="addRestaurant();"
                                    class="com-button com-submit-button com-button--large com-button--default">
                                <div class="com-submit-button__content"><span>Add Restaurants</span></div>
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
                                <th>Owner Name</th>
                                <th>Register Date</th>
                                <th>Action</th>
                            </tr>
                            </thead>
                            <tbody>

                            <?php foreach ($allusers as $single_user): ?>


                                <tr>
                                    <td>
                                        <?php echo $single_user['Restaurant']['id']; ?>
                                    </td>
                                    <td>
                                        <?php
                                            if(!isset($single_user['Currency']['id'])){
                                                    echo "<span class='fa fa-exclamation-circle redLink' title='Currency information is missing or deleted'></span>";

                                            }
                                            else
                                                if(!isset($single_user['Tax']['id'])) {

                                                        echo "<span class='fa fa-exclamation-circle redLink' title='Tax information is missing or deleted'></span>";

                                                }
                                        ?>
                                        <?php echo $single_user['Restaurant']['name']; ?>
                                    </td>
                                    <td>
                                        <?php echo $single_user['User']['email']; ?>
                                    </td>
                                    <td>
                                        <?php echo $single_user['Restaurant']['phone']; ?>
                                    </td>
                                    <td><?php echo $single_user['UserInfo']['first_name'] . " " . $single_user['UserInfo']['last_name']; ?></td>
                                    <td>
                                        <?php echo $single_user['Restaurant']['created']; ?>
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
                                                    <a href="manage_menu.php?resid=<?php echo $single_user['Restaurant']['id']; ?>&userid=<?php echo $single_user['Restaurant']['user_id']; ?>"
                                                       target="_blank">
                                                        <li class="more-menu-item" role="presentation">
                                                            <button type="button" class="more-menu-btn" role="menuitem">
                                                                Menu Item
                                                            </button>
                                                        </li>
                                                    </a>
                                                    <!--<li class="more-menu-item" role="presentation"-->
                                                    <!--    onclick="viewRestaurant(<?php echo $single_user['Restaurant']['id']; ?>)">-->
                                                    <!--    <button type="button" class="more-menu-btn" role="menuitem">-->
                                                    <!--        Details-->
                                                    <!--    </button>-->
                                                    <!--</li>-->
                                                   <a href="?p=editResturent&id=<?php echo $single_user['Restaurant']['id']; ?>">
                                                        <li class="more-menu-item" role="presentation">
                                                            <button type="button" class="more-menu-btn" role="menuitem">
                                                                Edit
                                                            </button>
                                                        </li>
                                                    </a>
                                                    <?php
                                                    if ($single_user['Restaurant']['single_restaurant'] == "0") {
                                                        ?>
                                                        <a href="?p=restaurants&action=SingleRestaurant&id=<?php echo $single_user['Restaurant']['id']; ?>&single_restaurant=1" style="display:none;">
                                                            <li class="more-menu-item" role="presentation">
                                                                <button type="button" class="more-menu-btn"
                                                                        role="menuitem">Spicy Select
                                                                </button>
                                                            </li>
                                                        </a>
                                                        <?php
                                                    } else {
                                                        ?>
                                                        <a href="?p=restaurants&action=SingleRestaurant&id=<?php echo $single_user['Restaurant']['id']; ?>&single_restaurant=0" style="display:none;">
                                                            <li class="more-menu-item" role="presentation">
                                                                <button type="button" class="more-menu-btn"
                                                                        role="menuitem" style="color:red;">Spicy Un
                                                                    select
                                                                </button>
                                                            </li>
                                                        </a>
                                                        <?php
                                                    }
                                                    ?>
                                                    
                                                    <?php
                                                    if ($single_user['Restaurant']['promoted'] == "0") {
                                                        ?>
                                                        <a href="?p=restaurants&action=favRestaurant&id=<?php echo $single_user['Restaurant']['id']; ?>&promoted=1">
                                                            <li class="more-menu-item" role="presentation">
                                                                <button type="button" class="more-menu-btn" role="menuitem">Promote</button>
                                                            </li>
                                                        </a>
                                                        <?php
                                                    } else {
                                                        ?>
                                                        <a href="?p=restaurants&action=favRestaurant&id=<?php echo $single_user['Restaurant']['id']; ?>&promoted=0">
                                                            <li class="more-menu-item" role="presentation">
                                                                <button type="button" class="more-menu-btn" role="menuitem" style="color:red;">Un Promote</button>
                                                            </li>
                                                        </a>
                                                        <?php
                                                    }
                                                    ?>
                                                    
                                                    
                                                    <?php
                                                    if ($single_user['Restaurant']['block'] == "0") {
                                                        ?>
                                                        <a href="?p=restaurants&action=blockRestaurant&id=<?php echo $single_user['Restaurant']['id']; ?>&block=1">
                                                            <li class="more-menu-item" role="presentation">
                                                                <button type="button" class="more-menu-btn"
                                                                        role="menuitem" style="color:red;">Block
                                                                </button>
                                                            </li>
                                                        </a>
                                                        <?php
                                                    } else {
                                                        ?>
                                                        <a href="?p=restaurants&action=blockRestaurant&id=<?php echo $single_user['Restaurant']['id']; ?>&block=0">
                                                            <li class="more-menu-item" role="presentation">
                                                                <button type="button" class="more-menu-btn"
                                                                        role="menuitem">UnBlock
                                                                </button>
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
                                <th>Owner Name</th>
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
            'pageLength': 100
          }
        )
        $('#table_view2').DataTable({
            'pageLength': 35
          }
        )
      })

      function viewRestaurant (id) {

        document.getElementById('PopupParent').style.display = 'block'
        document.getElementById('contentReceived').innerHTML = 'loading...'

        var xmlhttp
        if (window.XMLHttpRequest) {// code for IE7+, Firefox, Chrome, Opera, Safari
          xmlhttp = new XMLHttpRequest()
        } else {// code for IE6, IE5
          xmlhttp = new ActiveXObject('Microsoft.XMLHTTP')
        }
        xmlhttp.onreadystatechange = function () {
          if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
            // alert(xmlhttp.responseText);
            document.getElementById('contentReceived').innerHTML = xmlhttp.responseText
          }
        }
        xmlhttp.open('GET', 'ajex-events.php?action=viewRestaurant&id=' + id)
        xmlhttp.send()
      }

      function addRestaurant () {

        document.getElementById('PopupParent').style.display = 'block'
        document.getElementById('contentReceived').innerHTML = 'loading...'

        var xmlhttp
        if (window.XMLHttpRequest) {// code for IE7+, Firefox, Chrome, Opera, Safari
          xmlhttp = new XMLHttpRequest()
        } else {// code for IE6, IE5
          xmlhttp = new ActiveXObject('Microsoft.XMLHTTP')
        }
        xmlhttp.onreadystatechange = function () {
          if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
            // alert(xmlhttp.responseText);
            document.getElementById('contentReceived').innerHTML = xmlhttp.responseText
          }
        }
        xmlhttp.open('GET', 'ajex-events.php?action=addRestaurant')
        xmlhttp.send()
      }

      function Upload_image_desktop () {

        var fileUpload = document.getElementById('uploadFile')

        var regex = new RegExp('([a-zA-Z0-9\s_\\.\-:])+(.jpg|.png)$')
        if (regex.test(fileUpload.value.toLowerCase())) {

          if (typeof (fileUpload.files) != 'undefined') {

            var reader = new FileReader()

            reader.readAsDataURL(fileUpload.files[0])
            reader.onload = function (e) {

              var image = new Image()

              image.src = e.target.result

              image.onload = function () {
                var height = this.height
                var width = this.width

                if (height == 90 && width == 90) {

                  //document.getElementById("sliderImageform").submit();

                } else {

                  alert('Size 90x90')
                  return false
                }
              }

            }
          } else {
            alert('This browser does not support HTML5.')
            return false
          }
        } else {
          alert('Please select a valid Image file.')
          return false
        }
      }

      function Upload_image_desktopCover () {

        var fileUpload = document.getElementById('uploadFileCover')

        var regex = new RegExp('([a-zA-Z0-9\s_\\.\-:])+(.jpg|.png)$')
        if (regex.test(fileUpload.value.toLowerCase())) {

          if (typeof (fileUpload.files) != 'undefined') {

            var reader = new FileReader()

            reader.readAsDataURL(fileUpload.files[0])
            reader.onload = function (e) {

              var image = new Image()

              image.src = e.target.result

              image.onload = function () {
                var height = this.height
                var width = this.width

                if (height == 220 && width == 320) {

                  //document.getElementById("sliderImageform").submit();

                } else {

                  alert('Size 320x220')
                  return false
                }
              }

            }
          } else {
            alert('This browser does not support HTML5.')
            return false
          }
        } else {
          alert('Please select a valid Image file.')
          return false
        }
      }

    </script>
    <?php

} else {

    @header("Location: index.php");
    echo "<script>window.location='index.php'</script>";
    die;

} ?>