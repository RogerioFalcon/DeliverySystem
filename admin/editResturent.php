<?php
if (isset($_SESSION['sessionTokon'])) {
    if (isset($_GET['action'])) {

         if ($_GET['action'] == "addlogoRestaurant") 
         {
			$id = htmlspecialchars($_POST['id'], ENT_QUOTES);
		    $url = $baseurl . 'addRestaurantImage';
			$image_base = file_get_contents($_FILES['upload_image']['tmp_name']);
			$image = base64_encode($image_base);
			
			$data = array(
                "image" => array("file_data" => $image),
                'id'=>$id,
            );
            
            $json_data = @curl_request($data, $url);

            $json_return = $json_data['msg'];
            $json_code = $json_data['code'];



            if ($json_code !== 200) {
                echo "<script>window.location='dashboard.php?p=editResturent&id=".$id."&action=error'</script>";
            } else {
                echo "<script>window.location='dashboard.php?p=editResturent&id=".$id."&action=success'</script>";
            }


        }
        
        if ($_GET['action'] == "addRestaurantCoverImage") 
         {
			$id = htmlspecialchars($_POST['id'], ENT_QUOTES);
		    $url = $baseurl . 'addRestaurantCoverImage';
			$image_base = file_get_contents($_FILES['cover_image']['tmp_name']);
			$image = base64_encode($image_base);
			$data = array(
                "cover_image" => array("file_data" => $image),
                'id'=>$id,
            );
            
            
            $json_data = @curl_request($data, $url);

            $json_return = $json_data['msg'];
            $json_code = $json_data['code'];



            if ($json_code !== 200) {
                echo "<script>window.location='dashboard.php?p=editResturent&id=".$id."&action=error'</script>";
            } else {
                echo "<script>window.location='dashboard.php?p=editResturent&id=".$id."&action=success'</script>";
            }


        }
       
        if ($_GET['action'] == "resturnettiming") {
            $id = htmlspecialchars($_POST['id'], ENT_QUOTES);
            for ($i = 0; $i < 7; $i++) {
                $day[$i] = $_POST['day'][$i];
                $opening_time[$i] = $_POST['opening_time'][$i];
                $closing_time[$i] = $_POST['closing_time'][$i];


                $restaurant_timings_details[] = array('day' => $day[$i],'opening_time' => $opening_time[$i], 'closing_time' => $closing_time[$i]);
            }


            $url = $baseurl . 'addRestaurantTiming';

            $data = array(
                "restaurant_timing" => $restaurant_timings_details,
                'id'=>$id,
            );


            $json_data = @curl_request($data, $url);

            $json_return = $json_data['msg'];
            $json_code = $json_data['code'];



            if ($json_code !== 200) {
                echo "<script>window.location='dashboard.php?p=editResturent&id=".$id."&action=error'</script>";
            } else {
                echo "<script>window.location='dashboard.php?p=editResturent&id=".$id."&action=success'</script>";
            }


        }
          if ($_GET['action'] == "addtoresturentdata") {
            
            $id = htmlspecialchars($_POST['id'], ENT_QUOTES);
            $searchReplaceArray = array(
                '(' => '',
                ')' => '',
                '-' => '',
                '_' => '',
                ' ' => ''
            );
            $name = $_POST['name'];
            $currency_id = $_POST['currency_id'];//
            $speciality = $_POST['speciality'];//
            $min_order_price = $_POST['min_order_price'];
            $delivery_free_range = $_POST['delivery_free_range'];
            $preparation_time = $_POST['preparation_time'];
            $tax_free = $_POST['tax_free'];
            $tax_id = $_POST['tax_id'];//
            $slogan = $_POST['slogan'];
            $about = $_POST['about'];
            $phone = str_replace(array_keys($searchReplaceArray), array_values($searchReplaceArray), $_POST['phone']);
            $timezone ="-05:00"; //$_POST['timezone'];
            $state = $_POST['state'];
            $city = $_POST['city'];
            $country = $_POST['country'];
            $zip = $_POST['zip'];
            $lat = $_POST['lat'];
            $long = $_POST['long'];
           
            $data = array(
                'id'=>$id,
                "name" => $name,
                "slogan" => $slogan,
                "about" => $about,
                "min_order_price" => $min_order_price,//
                "delivery_free_range" => $delivery_free_range,//
                "tax_free" => $tax_free,//
                "phone" => $phone,
                "timezone" => $timezone,
                "city" => $city,
                "state" => $state,
                "country" => $country,
                "notes"=> "",
                "zip" => $zip,
                "lat" => $lat,
                "long" => $long,
                "currency_id" => $currency_id,//
                "tax_id" => $tax_id,//
                "speciality" => $speciality,//
                "preparation_time" => $preparation_time,//
            );

            $url = $baseurl . 'addrestaurant';
            $json_data = @curl_request($data, $url);

            $json_return = $json_data['msg'];
            $json_code = $json_data['code'];

            if ($json_code !== 200) {
                echo "<script>window.location='dashboard.php?p=editResturent&id=".$id."&action=error'</script>";
            } else {
                echo "<script>window.location='dashboard.php?p=editResturent&id=".$id."&action=success'</script>";
            }


        }

    }

    $id=$_GET['id'];

    $url=$baseurl . 'showRestaurantDetail';

    $data = array(
        "id" => $id
    );

    $json_data=@curl_request($data,$url);

    if ($json_data['code'] == 200) {
        $alldata = $json_data['msg'];
    }


    ?>

    <div class="qr-content">
    <div class="qr-page-content">
        <div class="qr-page zeropadding">
            <div class="qr-content-area">
                <div class="qr-row">
                    <div class="qr-el">

                        <div class="page-title">
                            <h2>Edit Restaurant</h2>
                            <div class="head-area">
                            </div>
                        </div>


                        <!--start of datatable here-->
<?php
    $url=$baseurl . 'showCountries';

    $data = "";

    $country=@curl_request($data,$url);

    //get all currency
    $url=$baseurl . 'showCurrencies';
    $data = [];

    $currency=@curl_request($data,$url);

    //get all tax
    $url=$baseurl . 'showAllTaxes';
    $data = [];

    $tax=@curl_request($data,$url);

    ?>
    <div class="main-container dataTables_wrapper" id="table_view_wrapper" >
        <h2 style="font-weight: 300;" align="center"></h2>

        <div>

            <form id="logoform" action="dashboard.php?p=editResturent&id=<?php echo $id ?>&action=addlogoRestaurant" method="post" enctype="multipart/form-data">
                <div class="qr-el qr-el-3" style="min-height: auto; float:left; box-shadow: 2px 0px 30px 5px rgba(0, 0, 0, 0.03);">
                    <label for="uploadFile" class="hoviringdell uploadBox" id="uploadTrigger" style="height: 110px;">
                        <img src="<?php echo $imagebaseurl . $alldata[0]['Restaurant']['image']; ?>" style="width: 38px; opacity: 1">
                        <div class="uploadText" style="font-size: 12px;">
                            <span style="color:#F69518;">Upload Logo</span><br>
                            Size 90x90
                        </div>
                    </label>
                    <input name="id" type="hidden" value="<?php echo $_GET['id'] ?>" >
                    <input name="upload_image" class="hidden" id="uploadFile" type="file" onchange="return Upload_image_desktop()" required="required">
                </div>
            </form>
            
            <form id="coverform" action="dashboard.php?p=editResturent&id=<?php echo $id ?>&action=addRestaurantCoverImage" method="post" enctype="multipart/form-data">
            <div class="qr-el qr-el-3" style="min-height: auto; float:right; box-shadow: 2px 0px 30px 5px rgba(0, 0, 0, 0.03);">
                <label for="uploadFileCover" class="hoviringdell uploadBox" id="uploadTrigger" style="height: 110px;">
                    <img src="<?php echo $imagebaseurl . $alldata[0]['Restaurant']['cover_image']; ?>" style="width: 38px; opacity: 1">
                    <div class="uploadText" style="font-size: 12px;">
                        <span style="color:#F69518;">Upload Cover</span><br>
                        Size 320x220
                    </div>
                </label>
                <input name="id" type="hidden" value="<?php echo $_GET['id'] ?>" >
                <input name="cover_image" class="hidden" id="uploadFileCover" type="file" onchange="return Upload_image_desktopCover()" required="required">

            </div>
            
            </form>
            <div style="clear:both;"></div>
            
            <form id="logoform" action="dashboard.php?p=editResturent&id=<?php echo $id ?>&action=addtoresturentdata" method="post" enctype="multipart/form-data">

            <div class="half_width float_left">
                <label class="field_title">Restaurant Name</label>
                <input name="name" value="<?php echo $alldata[0]['Restaurant']['name'] ?>" type="text" required>
            </div>

            <div class="half_width float_right">
                <label class="field_title">Slogan</label>
                <input name="slogan" value="<?php echo $alldata[0]['Restaurant']['slogan'] ?>" type="text" required>
            </div>

            <div class="full_width clear_both">
                <label class="field_title">About</label>
                <textarea name="about"  type="text" required><?php echo $alldata[0]['Restaurant']['about'] ?></textarea>
            </div>

            <div class="half_width float_left">
                <label class="field_title">Speciality</label>
                <input name="speciality"  value="<?php echo $alldata[0]['Restaurant']['speciality'] ?>" type="text" required>
            </div>

            <div class="half_width float_right">
                <label class="field_title">Phone</label>
                <input name="phone" value="<?php echo $alldata[0]['Restaurant']['phone'] ?>" type="text" required>
            </div>

            <!--<div class="half_width float_left">-->
            <!--    <label class="field_title">Timezone</label>-->
            <!--    <select name="timezone" class="form-control" required="">-->
            <!--        <option value="">Select timezone</option>-->
            <!--        <option value="-05:00" <?php if($alldata[0]['Restaurant']['timezone'] == '-05:00' ){echo "selected='selected'";} ?>>America/New York (-05:00)</option>-->
            <!--   </select>-->
            <!--</div>-->

            <div class="half_width float_left">
                <label class="field_title">Country</label>
                <select name="country"  class="form-control" required="">
                    <option value="">Select Country</option>

                    <?php  foreach( $country['msg']['countries'] as $str => $val ): ?>

                        <option <?php if($alldata[0]['Currency']['country'] == $val['Currency']['country'] ){echo "selected='selected'";} ?> value="<?php echo $val['Currency']['country']; ?>"><?php echo $val['Currency']['country']; ?></option>

                    <?php endforeach; ?>
               </select>
            </div>

            <div class="half_width float_right">
                <label class="field_title">State</label>
                <select name="state"   class="form-control" required="">
                    <option value="">Select State</option>

                    <?php  foreach( $tax['msg'] as $str => $val ): ?>

                        <option <?php if($alldata[0]['Tax']['state'] == $val['Tax']['state'] ){echo "selected='selected'";} ?> value="<?php echo $val['Tax']['state']; ?>"><?php echo $val['Tax']['state']; ?></option>

                    <?php endforeach; ?>
               </select>
            </div>

            <div class="half_width float_left">
                <label class="field_title">City</label>
                <select name="city" class="form-control" required="">
                    <option value="">Select City</option>

                    <?php  foreach( $tax['msg'] as $str => $val ): ?>

                        <option <?php if($alldata[0]['Tax']['city'] == $val['Tax']['city'] ){echo "selected='selected'";} ?> value="<?php echo $val['Tax']['city']; ?>"><?php echo $val['Tax']['city']; ?></option>

                    <?php endforeach; ?>
               </select>
            </div>
            <input name="id" type="hidden" value="<?php echo $_GET['id'] ?>" >
            <div class="half_width float_right">
                <label class="field_title">Tax</label>
                <select name="tax_id" class="form-control" required="">
                    <option value="">Select Tax</option>

                    <?php  foreach( $tax['msg'] as $str => $val ): ?>

                        <option <?php if($alldata[0]['Tax']['id'] == $val['Tax']['id'] ){echo "selected='selected'";} ?> value="<?php echo $val['Tax']['id']; ?>"><?php echo $val['Tax']['tax']; ?> %</option>

                    <?php endforeach; ?>
               </select>
            </div>
            
            <div class="half_width float_left">
                <label class="field_title">Zip Code</label>
                <input name="zip" value="<?php echo $alldata[0]['RestaurantLocation']['zip'] ?>" type="text" required>
            </div>

            <div class="half_width float_right">
                <label class="field_title">Locatino Lat</label>
                <input name="lat" value="<?php echo $alldata[0]['RestaurantLocation']['lat'] ?>" type="text" required>
            </div>

            <div class="half_width float_left">
                <label class="field_title">Location Long</label>
                <input name="long" value="<?php echo $alldata[0]['RestaurantLocation']['long'] ?>" type="text" required>
            </div>
            
            <div class="half_width float_right">
                <label class="field_title">Currency</label>
                <select name="currency_id" class="form-control" required="">
                    <option value="<?php echo $currency['msg'][0]['Currency']['id'] ?>"><?php echo $currency['msg'][0]['Currency']['currency'] ?></option>

                </select>

            </div>
            <div class="half_width float_left">
                <label class="field_title">Minimum Order Price</label>
                <select name="min_order_price" class="form-control" required="">
                    <option value="">Select Amount</option>

                    <?php

                        for($i = 1; $i<=999; $i++) {
                            ?>
                                <option <?php if($alldata[0]['Restaurant']['min_order_price'] == $i ){echo "selected='selected'";} ?> value="<?php echo $i; ?>"><?php echo $currency['msg'][0]['Currency']['symbol']; echo $i; ?></option>
                            <?php
                        }

                    ?>

               </select>

            </div>



            <div class="half_width float_right">
                <label class="field_title">Free Delivery Range</label>
                <select name="delivery_free_range"  class="form-control" required="">
                    <option value="">Select KM Range</option>

                    <?php

                        for($i = 1; $i<=49; $i++) {
                            ?>
                                <option <?php if($alldata[0]['Restaurant']['delivery_free_range'] == $i ){echo "selected='selected'";} ?> value="<?php echo $i; ?>"><?php echo $i; ?> KM</option>
                            <?php
                        }

                    ?>

               </select>
            </div>

            <div class="half_width float_left">
                <label class="field_title">AVG Food Prepation Time</label>
                <select name="preparation_time"   class="form-control" required="">
                    <option value="">Select Minutes</option>
                    <option <?php if($alldata[0]['Restaurant']['preparation_time'] == "30" ){echo "selected='selected'";} ?> value="30">30 min</option>
                    <option <?php if($alldata[0]['Restaurant']['preparation_time'] == "40" ){echo "selected='selected'";} ?> value="40">40 min</option>
                    <option <?php if($alldata[0]['Restaurant']['preparation_time'] == "50" ){echo "selected='selected'";} ?> value="50">50 min</option>
                    <option <?php if($alldata[0]['Restaurant']['preparation_time'] == "60" ){echo "selected='selected'";} ?> value="60">60 min</option>
               </select>
            </div>

            <div class="half_width float_right">
                <label class="field_title">Tax implementation</label>
                <select name="tax_free"   class="form-control" required="">
                    <option <?php if($alldata[0]['Restaurant']['tax_free'] == "1" ){echo "selected='selected'";} ?> value="1">No Tax will implement</option>
                    <option <?php if($alldata[0]['Restaurant']['tax_free'] == "0" ){echo "selected='selected'";} ?>  value="0">Tax % will implement</option>
                </select>
            </div>
            <div class="clear_both"></div>
            <div class="full_width">
                <button class="com-button com-submit-button com-button--large " type="submit" style="width: 100%;" align="center">
                    Submit
                </button>
            </div>
            </form>
            





            <h3 style="font-weight: 300;" align="center">Restaurant Open/Close Timing</h3>
            <br>
            <form action="dashboard.php?p=editResturent&action=resturnettiming" method="post" enctype="multipart/form-data">

            <div class="full_width">
                <input name="day[]" type="text" value="Sunday" style="background: #e1e1e11a;" readonly>
            </div>
                    <input name="id" type="hidden" value="<?php echo $_GET['id'] ?>" >
            <div class="half_width float_left">
                <label class="field_title">Opening Time</label>
                <?php
                $result = substr($alldata[0]['RestaurantTiming'][0]['opening_time'], 0, 2);

                    ?>
                <select name="opening_time[]" class="form-control" required="">
                    <option value="">Select Time</option>
                    <?php

                        for($i = 0; $i<=23; $i++) {
                            ?>
                                <option  value="<?php echo $i; ?>:00:00" <?php if($i==$result){echo "selected";} ?>><?php echo $i; ?>:00:00</option>
                            <?php
                        }


                    ?>
               </select>
            </div>

            <div class="half_width float_right">
                <label class="field_title">Closing Time</label>
                <?php
                $result = substr($alldata[0]['RestaurantTiming'][0]['closing_time'], 0, 2);

                ?>
                <select name="closing_time[]" class="form-control" required="">
                    <option value="">Select Time</option>
                    <?php

                    for($i = 0; $i<=23; $i++) {
                        ?>
                        <option value="<?php echo $i; ?>:00:00" <?php if($i==$result){echo "selected";} ?>><?php echo $i; ?>:00:00</option>
                        <?php
                    }

                    ?>
               </select>
            </div>
            <div class="clear_both"></div>

            <br>
            <div class="full_width">
                <input name="day[]" type="text" value="Monday" style="background: #e1e1e11a;" readonly >
            </div>

            <div class="half_width float_left">
                <label class="field_title">Opening Time</label>
                <?php
                $result = substr($alldata[0]['RestaurantTiming'][1]['opening_time'], 0, 2);

                ?>
                <select name="opening_time[]" class="form-control" required="">
                    <option value="">Select Time</option>
                    <?php

                    for($i = 0; $i<=23; $i++) {
                        ?>
                        <option value="<?php echo $i; ?>:00:00" <?php if($i==$result){echo "selected";} ?>><?php echo $i; ?>:00:00</option>
                        <?php
                    }

                    ?>
               </select>
            </div>

            <div class="half_width float_right">
                <label class="field_title">Closing Time</label>
                <?php
                $result = substr($alldata[0]['RestaurantTiming'][1]['closing_time'], 0, 2);

                ?>
                <select name="closing_time[]" class="form-control" required="">
                    <option value="">Select Time</option>
                    <?php

                    for($i = 0; $i<=23; $i++) {
                        ?>
                        <option value="<?php echo $i; ?>:00:00" <?php if($i==$result){echo "selected";} ?>><?php echo $i; ?>:00:00</option>
                        <?php
                    }

                    ?>
               </select>
            </div>
            <div class="clear_both"></div>

            <br>
            <div class="full_width">
                <input name="day[]" type="text" value="Tuesday" style="background: #e1e1e11a;" readonly>
            </div>

            <div class="half_width float_left">

                <label class="field_title">Opening Time</label>
                <?php
                $result = substr($alldata[0]['RestaurantTiming'][2]['opening_time'], 0, 2);

                ?>
                <select name="opening_time[]" class="form-control" required="">
                    <option value="">Select Time</option>
                    <?php

                    for($i = 0; $i<=23; $i++) {
                        ?>
                        <option value="<?php echo $i; ?>:00:00" <?php if($i==$result){echo "selected";} ?>><?php echo $i; ?>:00:00</option>
                        <?php
                    }

                    ?>
               </select>
            </div>

            <div class="half_width float_right">
                <label class="field_title">Closing Time</label>
                <?php
                $result = substr($alldata[0]['RestaurantTiming'][2]['closing_time'], 0, 2);

                ?>
                <select name="closing_time[]" class="form-control" required="">
                    <option value="">Select Time</option>
                    <?php

                    for($i = 0; $i<=23; $i++) {
                        ?>
                        <option value="<?php echo $i; ?>:00:00" <?php if($i==$result){echo "selected";} ?>><?php echo $i; ?>:00:00</option>
                        <?php
                    }

                    ?>
               </select>
            </div>
            <div class="clear_both"></div>

            <br>
            <div class="full_width">
                <input name="day[]" type="text" value="wednesday" style="background: #e1e1e11a;" readonly>
            </div>

            <div class="half_width float_left">
                <label class="field_title">Opening Time</label>
                <?php
                $result = substr($alldata[0]['RestaurantTiming'][3]['opening_time'], 0, 2);

                ?>
                <select name="opening_time[]" class="form-control" required="">
                    <option value="">Select Time</option>
                    <?php

                    for($i = 0; $i<=23; $i++) {
                        ?>
                        <option value="<?php echo $i; ?>:00:00" <?php if($i==$result){echo "selected";} ?>><?php echo $i; ?>:00:00</option>
                        <?php
                    }
                    ?>
               </select>
            </div>

            <div class="half_width float_right">
                <label class="field_title">Closing Time</label>
                <?php
                $result = substr($alldata[0]['RestaurantTiming'][3]['closing_time'], 0, 2);

                ?>
                <select name="closing_time[]" class="form-control" required="">
                    <option value="">Select Time</option>
                    <?php

                    for($i = 0; $i<=23; $i++) {
                        ?>
                        <option value="<?php echo $i; ?>:00:00" <?php if($i==$result){echo "selected";} ?>><?php echo $i; ?>:00:00</option>
                        <?php
                    }
                    ?>
               </select>
            </div>
            <div class="clear_both"></div>

            <br>
            <div class="full_width">
                <input name="day[]" type="text" value="Thursday" style="background: #e1e1e11a;" readonly>
            </div>

            <div class="half_width float_left">
                <label class="field_title">Opening Time</label>
                <?php
                $result = substr($alldata[0]['RestaurantTiming'][4]['opening_time'], 0, 2);

                ?>
                <select name="opening_time[]" class="form-control" required="">
                    <option value="">Select Time</option>
                    <?php

                    for($i = 0; $i<=23; $i++) {
                        ?>
                        <option value="<?php echo $i; ?>:00:00" <?php if($i==$result){echo "selected";} ?>><?php echo $i; ?>:00:00</option>
                        <?php
                    }

                    ?>
               </select>
            </div>

            <div class="half_width float_right">
                <label class="field_title">Closing Time</label>
                <?php
                $result = substr($alldata[0]['RestaurantTiming'][4]['closing_time'], 0, 2);

                ?>
                <select name="closing_time[]" class="form-control" required="">
                    <option value="">Select Time</option>
                    <?php

                    for($i = 0; $i<=23; $i++) {
                        ?>
                        <option value="<?php echo $i; ?>:00:00" <?php if($i==$result){echo "selected";} ?>><?php echo $i; ?>:00:00</option>
                        <?php
                    }
                    ?>
               </select>
            </div>
            <div class="clear_both"></div>

            <br>
            <div class="full_width">
                <input name="day[]" type="text" value="Friday" style="background: #e1e1e11a;" readonly>
            </div>

            <div class="half_width float_left">
                <label class="field_title">Opening Time</label>
                <?php
                $result = substr($alldata[0]['RestaurantTiming'][5]['opening_time'], 0, 2);

                ?>
                <select name="opening_time[]" class="form-control" required="">
                    <option value="">Select Time</option>
                    <?php

                    for($i = 0; $i<=23; $i++) {
                        ?>
                        <option value="<?php echo $i; ?>:00:00" <?php if($i==$result){echo "selected";} ?>><?php echo $i; ?>:00:00</option>
                        <?php
                    }

                    ?>
               </select>
            </div>

            <div class="half_width float_right">
                <label class="field_title">Closing Time</label>
                <?php
                $result = substr($alldata[0]['RestaurantTiming'][5]['closing_time'], 0, 2);

                ?>
                <select name="closing_time[]" class="form-control" required="">
                    <option value="">Select Time</option>
                    <?php

                    for($i = 0; $i<=23; $i++) {
                        ?>
                        <option value="<?php echo $i; ?>:00:00" <?php if($i==$result){echo "selected";} ?>><?php echo $i; ?>:00:00</option>
                        <?php
                    }

                    ?>
               </select>
            </div>
            <div class="clear_both"></div>

            <br>
            <div class="full_width">
                <input name="day[]" type="text" value="Saturday" style="background: #e1e1e11a;" readonly>
            </div>

            <div class="half_width float_left">
                <label class="field_title">Opening Time</label>
                <?php
                $result = substr($alldata[0]['RestaurantTiming'][6]['opening_time'], 0, 2);

                ?>
                <select name="opening_time[]" class="form-control" required="">
                    <option value="">Select Time</option>
                    <?php
                    for($i = 0; $i<=23; $i++) {
                        ?>
                        <option value="<?php echo $i; ?>:00:00" <?php if($i==$result){echo "selected";} ?>><?php echo $i; ?>:00:00</option>
                        <?php
                    }
                    ?>
               </select>
            </div>

            <div class="half_width float_right">
                <label class="field_title">Closing Time</label>
                <?php
                $result = substr($alldata[0]['RestaurantTiming'][6]['closing_time'], 0, 2);

                ?>
                <select name="closing_time[]" class="form-control" required="">
                    <option value="">Select Time</option>
                    <?php

                    for($i = 0; $i<=23; $i++) {
                        ?>
                        <option value="<?php echo $i; ?>:00:00" <?php if($i==$result){echo "selected";} ?>><?php echo $i; ?>:00:00</option>
                        <?php
                    }
                    ?>
               </select>
            </div>
            <div class="clear_both"></div>



            <div class="full_width">
                <button class="com-button com-submit-button com-button--large " type="submit" style="width: 100%;" align="center">
                   Submit
                </button>
            </div>

        </form>
        </div>
    </div>





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

                  document.getElementById("logoform").submit();

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

                  document.getElementById("coverform").submit();

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