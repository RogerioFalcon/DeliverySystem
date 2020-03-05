<?php
include("config.php");

if (@$_GET['action'] == "addCurrency")
{

    $url=$baseurl . 'showCountries';

    $data = "";

    $json_data=@curl_request($data,$url);


    ?>
    <div class="main-container dataTables_wrapper" id="table_view_wrapper" >
        <h2 style="font-weight: 300;" align="center">Add Currency</h2>

        <form class="addcategory" action="?p=manageCurrency&action=addCurrency" method="post" >

            <div class="full_width">
                <label class="field_title">Currency Name</label>
                <input name="currency_name" type="text" required>
            </div>

            <div class="full_width">
                <label class="field_title">Country</label>
                <input name="country" type="text" required>
            </div>

            <div class="full_width">
                <label class="field_title">Currency Code</label>
                <input name="code" type="text" required>
            </div>

            <div class="full_width">
                <label class="field_title">Symbol</label>
                <input name="symbol" type="text" required>
            </div>
            <input type="hidden" name="user_id" value="<?php echo $id; ?>">

            <div class="full_width">
                <button class="com-button com-submit-button com-button--large " type="submit" style="width: 100%;" align="center">
                   Submit
                </button>
            </div>


        </form>

    </div>
    <?php

}
else
if (@$_GET['action'] == "addTax")
{

    $url=$baseurl . 'showCountries';

    $data = "";

    $json_data=@curl_request($data,$url);
    
    //get all currency
    $url=$baseurl . 'showCurrencies';
    $data = [];

    $currency=@curl_request($data,$url);

    //get all tax
    $url=$baseurl . 'showAllTaxes';
    $data = [];

    $tax=@curl_request($data,$url);
    
    
    if(count($currency['msg'])=="0" || count($currency['msg'])=="")
    {
        echo"<em>Note: You have to add currency first <a href='dashboard.php?p=manageCurrency' class='redLink'>Add Currency</a></em>";
        die();
    }
   

    ?>
    <div class="main-container dataTables_wrapper" id="table_view_wrapper" >
        <h2 style="font-weight: 300;" align="center">Add Tax</h2>

        <div style="height:420px; overflow:scroll;">
            <form class="addcategory" action="?p=taxSetting&action=addTax" method="post" >

                <div class="full_width">
                    <label class="field_title">Country</label>
                    <select name="country" class="full_width" required>
                        <option value="">Select Country</option>

                        <?php  foreach( $json_data['msg']['countries'] as $str => $val ): ?>

                            <option value="<?php echo $val['Currency']['country']; ?>"><?php echo $val['Currency']['country']; ?></option>

                        <?php endforeach; ?>

                    </select>
                </div>
                
                <div class="full_width">
                    <label class="field_title">City</label>
                    <input name="city" type="text" required>
                </div>

                <div class="full_width">
                    <label class="field_title">State</label>
                    <input name="state" type="text" required>
                </div>

                <div class="full_width">
                    <label class="field_title">Tax %</label>
                    <input name="tax" type="number" required>
                </div>

                <div class="full_width">
                    <label class="field_title">Delivery Fee Per Km</label>
                    <input name="deliveryfee" type="number" required>
                </div>

                <div class="full_width">
                    <label class="field_title">Country Code  eg +1</label>
                    <input name="countrycode" type="text" required>
                </div>

                <div class="full_width">
                    <label class="field_title">Delivery Est time</label>
                    <input name="delivery_time" type="number" required>
                </div>


                <div class="full_width">
                    <button class="com-button com-submit-button com-button--large " type="submit" style="width: 100%;" align="center">
                       Submit
                    </button>
                </div>
                
                <em>Note:Make sure you have added the currency 1st then country will be show.</em>

            </form>
        </div>
    </div>
    <?php

}
else
if (@$_GET['action'] == "editUser")
{
    $id=$_GET['id'];

    $url=$baseurl . 'showUserDetail';

    $data = array(
                    "user_id" => $id
                );

    $json_data=@curl_request($data,$url);

    $id=$json_data['msg']['User']['id'];
    $first_name=$json_data['msg']['UserInfo']['first_name'];
    $last_name=$json_data['msg']['UserInfo']['last_name'];
    $phone=$json_data['msg']['UserInfo']['phone'];
    $email=$json_data['msg']['User']['email'];
    $role=$json_data['msg']['User']['role'];


    ?>
    <div class="main-container dataTables_wrapper" id="table_view_wrapper" >
        <h2 style="font-weight: 300;" align="center">Edit User</h2>

        <div style="height:400px; overflow:scroll;">
            <form action="?p=users&action=editProfile" method="post" >
                <input name="user_id" type="hidden" value="<?php echo $id; ?>" required>
                <div class="full_width">
                    <label class="field_title">First Name</label>
                    <input name="first_name" type="text" value="<?php echo $first_name; ?>" required>
                </div>

                <div class="full_width">
                    <label class="field_title">Last Name</label>
                    <input name="last_name" type="text" value="<?php echo $last_name; ?>" required>
                </div>

                <div class="full_width">
                    <label class="field_title">Phone #</label>
                    <input name="phone" type="text" value="<?php echo $phone; ?>" required>
                </div>

                <div class="full_width">
                    <label class="field_title">Email</label>
                    <input name="email" type="text" value="<?php echo $email; ?>" required>
                </div>

                <div class="full_width">
                    <label class="field_title">Role</label>
                    <input name="state" type="text" value="<?php echo $role; ?>" readonly required>
                </div>
                <div class="full_width">
                    <button class="com-button com-submit-button com-button--large " type="submit" style="width: 100%;" align="center">
                       Submit
                    </button>
                </div>
            </form>
        </div>
    </div>
    <?php

}
else
if (@$_GET['action'] == "changePassword")
{
    $id=$_GET['id'];

    $url=$baseurl . 'showUserDetail';

    $data = array(
        "user_id" => $id
    );

    $json_data=@curl_request($data,$url);

    $id=$json_data['msg']['User']['id'];
    $first_name=$json_data['msg']['UserInfo']['first_name'];
    $last_name=$json_data['msg']['UserInfo']['last_name'];
    $phone=$json_data['msg']['UserInfo']['phone'];
    $email=$json_data['msg']['User']['email'];
    $role=$json_data['msg']['User']['role'];


    ?>
    <div class="main-container dataTables_wrapper" id="table_view_wrapper" >
        <h2 style="font-weight: 300;" align="center">Change Password</h2>

        <div style="height:130px; overflow:scroll;">
            <form action="?p=users&action=changePassword" method="post" >
                <input name="user_id" type="hidden" value="<?php echo $id; ?>" required>
                <div class="full_width">
                    <label class="field_title">New Password</label>
                    <input name="password" type="text" required>
                </div>
                <div class="full_width">
                    <button class="com-button com-submit-button com-button--large " type="submit" style="width: 100%;" align="center">
                       Submit
                    </button>
                </div>
            </form>
        </div>
    </div>
    <?php

}
else
if (@$_GET['action'] == "changeAdminPassword")
{
    $id=$_GET['id'];

    
    ?>
    <div class="main-container dataTables_wrapper" id="table_view_wrapper" >
        <h2 style="font-weight: 300;" align="center">Change Admin Password</h2>

        <div style="height:130px; overflow:scroll;">
            <form action="?p=adminUsers&action=changeAdminPassword" method="post" >
                <input name="user_id" type="hidden" value="<?php echo $id; ?>" required>
                <div class="full_width">
                    <label class="field_title">New Password</label>
                    <input name="password" type="text" required>
                </div>
                <div class="full_width">
                    <button class="com-button com-submit-button com-button--large " type="submit" style="width: 100%;" align="center">
                       Submit
                    </button>
                </div>
            </form>
        </div>
    </div>
    <?php

}
else
if (@$_GET['action'] == "editTax")
{
    $id=$_GET['id'];

    $url=$baseurl . 'showTaxDetail';

    $data = array(
                    "id" => $id
                );

    $json_data=@curl_request($data,$url);

    $id=$json_data['msg'][0]['Tax']['id'];
    $city=$json_data['msg'][0]['Tax']['city'];
    $state=$json_data['msg'][0]['Tax']['state'];
    $country=$json_data['msg'][0]['Tax']['country'];
    $country_code=$json_data['msg'][0]['Tax']['country_code'];
    $tax=$json_data['msg'][0]['Tax']['tax'];
    $delivery_fee_per_km=$json_data['msg'][0]['Tax']['delivery_fee_per_km'];
    $delivery_time=$json_data['msg'][0]['Tax']['delivery_time'];

    //get countries
    $url=$baseurl . 'showCountries';

    $data = "";

    $json_data=@curl_request($data,$url);

    ?>
    <div class="main-container dataTables_wrapper" id="table_view_wrapper" >
        <h2 style="font-weight: 300;" align="center">Edit Tax</h2>

        <div style="height:450px; overflow:scroll;">
            <form class="addcategory" action="?p=taxSetting&action=addTax" method="post" >
                <input name="id" type="hidden" value="<?php echo $id; ?>" required>

                <div class="full_width">
                    <label class="field_title">Country</label>
                    <select name="country" class="full_width" required>
                        <option value="">Select Country</option>

                        <?php  foreach( $json_data['msg']['countries'] as $str => $val ): ?>

                            <option value="<?php echo $val['Currency']['country'];?>" <?php if($country==$val['Currency']['country']){ echo "selected";} ?> ><?php echo $val['Currency']['country']; ?></option>

                        <?php endforeach; ?>

                    </select>
                </div>

                <div class="full_width">
                    <label class="field_title">City</label>
                    <input name="city" type="text" value="<?php echo $city; ?>"  required readonly>
                </div>

                <div class="full_width">
                    <label class="field_title">State</label>
                    <input name="state" type="text" value="<?php echo $state; ?>"  required readonly>
                </div>


                <div class="full_width">
                    <label class="field_title">Tax %</label>
                    <input name="tax" type="number" value="<?php echo $tax; ?>"  required>
                </div>

                <div class="full_width">
                    <label class="field_title">Delivery Fee Per Km</label>
                    <input name="deliveryfee" type="number" value="<?php echo $delivery_fee_per_km; ?>"  required>
                </div>

                <div class="full_width">
                    <label class="field_title">Country Code  eg +1</label>
                    <input name="countrycode" type="text" value="<?php echo $country_code; ?>"  required>
                </div>

                <div class="full_width">
                    <label class="field_title">Delivery Est time</label>
                    <input name="delivery_time" type="number" value="<?php echo $delivery_time; ?>"  required>
                </div>


                <div class="full_width">
                    <button class="com-button com-submit-button com-button--large " type="submit" style="width: 100%;" align="center">
                       Submit
                    </button>
                </div>


            </form>
        </div>
    </div>
    <?php

}
else
if (@$_GET['action'] == "editCurrency")
{
    $id=$_GET['id'];

    $url=$baseurl . 'showCurrencyDetail';

    $data = array(
                    "id" => $id
                );

    $showCurrencyDetail=@curl_request($data,$url);

    //get countries
    $url=$baseurl . 'showCountries';

    $data = "";

    $json_data=@curl_request($data,$url);

    ?>
    <div class="main-container dataTables_wrapper" id="table_view_wrapper" >
        <h2 style="font-weight: 300;" align="center">Edit Currency</h2>

        <div style="height:350px; overflow:scroll;">
            <form class="addcategory" action="?p=manageCurrency&action=addCurrency" method="post" >
                <input name="id" type="hidden" value="<?php echo $showCurrencyDetail['msg'][0]['Currency']['id']; ?>" required>

                <div class="full_width">
                    <label class="field_title">Country</label>
                    <input name="country" type="text" value="<?php echo $showCurrencyDetail['msg'][0]['Currency']['country']; ?>"  required>
                </div>

                <div class="full_width">
                    <label class="field_title">Currency Name</label>
                    <input name="currency_name" type="text" value="<?php echo $showCurrencyDetail['msg'][0]['Currency']['currency']; ?>"  required>
                </div>

                <div class="full_width">
                    <label class="field_title">Currency Code</label>
                    <input name="code" type="text" value="<?php echo $showCurrencyDetail['msg'][0]['Currency']['code']; ?>"  required>
                </div>

                <div class="full_width">
                    <label class="field_title">Symbol</label>
                    <input name="symbol" type="text" value="<?php echo $showCurrencyDetail['msg'][0]['Currency']['symbol']; ?>"  required>
                </div>

                <div class="full_width">
                    <button class="com-button com-submit-button com-button--large " type="submit" style="width: 100%;" align="center">
                       Submit
                    </button>
                </div>


            </form>
        </div>
    </div>
    <?php

}
else
if (@$_GET['action'] == "viewRestaurant")
{
    $id=$_GET['id'];

    $url=$baseurl . 'showRestaurantDetail';

    $data = array(
                    "id" => $id
                );

    $json_data=@curl_request($data,$url);



    ?>
    <div class="main-container dataTables_wrapper" id="table_view_wrapper" >
        <h2 style="font-weight: 300;" align="center">Restaurant Details</h2>

        <div style="height:400px; overflow:scroll;">

            <input name="id" type="hidden" value="<?php echo $json_data['msg'][0]['Restaurant']['id'];  ?>" required>
            <div class="full_width">
                <label class="field_title">Name</label>
                <input name="name" type="text" value="<?php echo $json_data['msg'][0]['Restaurant']['name']; ?>"  required>
            </div>

            <div class="full_width">
                <label class="field_title">Slogan</label>
                <input name="slogan" type="text" value="<?php echo $json_data['msg'][0]['Restaurant']['slogan']; ?>"  required>
            </div>

            <div class="full_width">
                <label class="field_title">About</label>
                <textarea name="about" type="text" required><?php echo $json_data['msg'][0]['Restaurant']['about']; ?></textarea>
            </div>

            <div class="full_width">
                <label class="field_title">Speciality</label>
                <input name="speciality" type="text" value="<?php echo $json_data['msg'][0]['Restaurant']['speciality']; ?>"  required>
            </div>

            <div class="full_width">
                <label class="field_title">Phone</label>
                <input name="phone" type="text" value="<?php echo $json_data['msg'][0]['Restaurant']['phone']; ?>"  required>
            </div>

            <div class="full_width">
                <label class="field_title">Time Zone</label>
                <input name="timezone" type="text" value="<?php echo $json_data['msg'][0]['Restaurant']['timezone']; ?>"  required>
            </div>

            <div class="full_width">
                <label class="field_title">Min Order Price</label>
                <input name="min_order_price" type="text" value="<?php echo $json_data['msg'][0]['Restaurant']['min_order_price']; ?>"  required>
            </div>

            <div class="full_width">
                <label class="field_title">Delivery Free Range</label>
                <input name="delivery_free_range" type="text" value="<?php echo $json_data['msg'][0]['Restaurant']['delivery_free_range']; ?>"  required>
            </div>

            <div class="full_width">
                <label class="field_title">currency_id</label>
                <input name="currency_id" type="text" value="<?php echo $json_data['msg'][0]['Restaurant']['currency_id']; ?>"  required>
            </div>

            <div class="full_width">
                <label class="field_title">tax_id</label>
                <input name="tax_id" type="text" value="<?php echo $json_data['msg'][0]['Restaurant']['tax_id']; ?>"  required>
            </div>

            <div class="full_width">
                <label class="field_title">Google Analytics</label>
                <input name="google_analytics" type="text" value="<?php echo $json_data['msg'][0]['Restaurant']['google_analytics']; ?>"  required>
            </div>


            <h3 style="font-weight: 300;" align="center">User Info</h3>


            <div class="full_width">
                <label class="field_title">First Name</label>
                <input name="first_name" type="text" value="<?php echo $json_data['msg'][0]['UserInfo']['first_name']; ?>"  required>
            </div>

            <div class="full_width">
                <label class="field_title">Last Name</label>
                <input name="last_name" type="text" value="<?php echo $json_data['msg'][0]['UserInfo']['last_name']; ?>"  required>
            </div>

            <div class="full_width">
                <label class="field_title">Phone</label>
                <input name="phone" type="text" value="<?php echo $json_data['msg'][0]['UserInfo']['phone']; ?>"  required>
            </div>

            <div class="full_width">
                <label class="field_title">Email</label>
                <input name="email" type="text" value="<?php echo $json_data['msg'][0]['User']['email']; ?>"  required>
            </div>


            <h3 style="font-weight: 300;" align="center">Currency Info</h3>
            <div style="text-align: center;font-size: 12px;">
                <!--<a href="" style=" color:#C3242E;">-->
                <!--    <span class="fa fa-edit"></span> Edit-->
                </a>
            </div>


            <div class="full_width">
                <label class="field_title">Country</label>
                <input name="country" type="text" value="<?php echo $json_data['msg'][0]['Currency']['country']; ?>"  required>
            </div>

            <div class="full_width">
                <label class="field_title">Currency</label>
                <input name="currency" type="text" value="<?php echo $json_data['msg'][0]['Currency']['currency']; ?>"  required>
            </div>

            <div class="full_width">
                <label class="field_title">Code</label>
                <input name="code" type="text" value="<?php echo $json_data['msg'][0]['Currency']['code']; ?>"  required>
            </div>

            <div class="full_width">
                <label class="field_title">Symbol</label>
                <input name="symbol" type="text" value="<?php echo $json_data['msg'][0]['Currency']['symbol']; ?>"  required>
            </div>




            <h3 style="font-weight: 300;" align="center">Tax Info</h3>
            <div style="text-align: center;font-size: 12px;">
                <!--<a href="" style=" color:#C3242E;">-->
                <!--    <span class="fa fa-edit"></span> Edit-->
                <!--</a>-->
            </div>


            <div class="full_width">
                <label class="field_title">City</label>
                <input name="city" type="text" value="<?php echo $json_data['msg'][0]['Tax']['city']; ?>"  required>
            </div>

            <div class="full_width">
                <label class="field_title">State</label>
                <input name="state" type="text" value="<?php echo $json_data['msg'][0]['Tax']['state']; ?>"  required>
            </div>

            <div class="full_width">
                <label class="field_title">Country</label>
                <input name="country" type="text" value="<?php echo $json_data['msg'][0]['Tax']['country']; ?>"  required>
            </div>

            <div class="full_width">
                <label class="field_title">Tax</label>
                <input name="tax" type="text" value="<?php echo $json_data['msg'][0]['Tax']['tax']; ?>"  required>
            </div>

            <div class="full_width">
                <label class="field_title">Delivery Fee Per km</label>
                <input name="delivery_fee_per_km" type="text" value="<?php echo $json_data['msg'][0]['Tax']['delivery_fee_per_km']; ?>"  required>
            </div>

            <div class="full_width">
                <label class="field_title">Country Code</label>
                <input name="country_code" type="text" value="<?php echo $json_data['msg'][0]['Tax']['country_code']; ?>"  required>
            </div>

            <div class="full_width">
                <label class="field_title">Delivery Time</label>
                <input name="delivery_time" type="text" value="<?php echo $json_data['msg'][0]['Tax']['delivery_time']; ?>"  required>
            </div>



            </form>
        </div>
    </div>
    <?php

}
else
if (@$_GET['action'] == "addRider")
{
    $url=$baseurl . 'showCountries';

    $data = "";

    $json_data=@curl_request($data,$url);

    ?>
    <div class="main-container dataTables_wrapper" id="table_view_wrapper" >
        <h2 style="font-weight: 300;" align="center">Add Rider</h2>

        <div style="height:400px; overflow:scroll;">
            <form class="addcategory" action="?p=rider&action=addRider" method="post" >

                <div class="full_width">
                    <label class="field_title">Email</label>
                    <input name="email" type="text" required>
                </div>

                <div class="full_width">
                    <label class="field_title">Password</label>
                    <input name="password" type="password" required>
                </div>

                <div class="full_width">
                    <label class="field_title">First Name</label>
                    <input name="first_name" type="text" required>
                </div>

                <div class="full_width">
                    <label class="field_title">Last Name</label>
                    <input name="last_name" type="text" required>
                </div>

                <div class="full_width">
                    <label class="field_title">Phone No</label>
                    <input name="phone" type="number" required>
                </div>

                <div class="full_width">
                    <label class="field_title">Country</label>
                    <select name="country" class="full_width" required>
                        <option value="">Select Country</option>

                        <?php  foreach( $json_data['msg']['countries'] as $str => $val ): ?>

                            <option value="<?php echo $val['Currency']['country']; ?>"><?php echo $val['Currency']['country']; ?></option>

                        <?php endforeach; ?>

                    </select>
                </div>

                <div class="full_width">
                    <label class="field_title">Country</label>
                    <select name="city" class="full_width" required>
                        <option value="">Select City</option>

                        <?php  foreach( $json_data['msg']['cities'] as $str => $val ): ?>

                            <option value="<?php echo $val['Tax']['city']; ?>"><?php echo $val['Tax']['city']; ?></option>

                        <?php endforeach; ?>

                    </select>
                </div>


                <div class="full_width">
                    <label class="field_title">Shift Start From (Address/Area)</label>
                    <input name="address_to_start_shift" type="text" required>
                </div>

                <div class="full_width">
                    <label class="field_title">Note</label>
                    <input name="note" type="text" required>
                </div>


                <div class="full_width">
                    <button class="com-button com-submit-button com-button--large " type="submit" style="width: 100%;" align="center">
                       Submit
                    </button>
                </div>


            </form>
        </div>
    </div>
    <?php

}
else
if (@$_GET['action'] == "addRestaurant")
{
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
    
    
    if(count($currency['msg'])=="0" || count($currency['msg'])=="")
    {
        echo"<em>Note: You have to add currency first <a href='dashboard.php?p=manageCurrency' class='redLink'>Add Currency</a></em>";
        die();
    }
    else
    if(count($tax['msg'])=="0" || count($tax['msg'])=="")
    {
        echo"<em>Note: You have to add tax information first <a href='dashboard.php?p=taxSetting' class='redLink'>Add Tax</a></em>";
        die();
    }
    
    ?>
    <div class="main-container dataTables_wrapper" id="table_view_wrapper" >
        <h2 style="font-weight: 300;" align="center">Add Restaurant</h2>

        <div style="height:450px; overflow:scroll;">

        <form action="dashboard.php?p=restaurants&action=addRestaurant" method="post" enctype="multipart/form-data">
            <div class="qr-el qr-el-3" style="min-height: auto; float:left; box-shadow: 2px 0px 30px 5px rgba(0, 0, 0, 0.03);">
                <label for="uploadFile" class="hoviringdell uploadBox" id="uploadTrigger" style="height: 110px;">
                    <img src="frontend_public/uploads/attachment/upload.png" style="width: 38px;">
                    <div class="uploadText" style="font-size: 12px;">
                        <span style="color:#F69518;">Upload Logo</span><br>
                        Size 90x90
                    </div>
                </label>
                <input name="upload_image" class="hidden" id="uploadFile" type="file" onchange="return Upload_image_desktop()" required="required">
            </div>

            <div class="qr-el qr-el-3" style="min-height: auto; float:right; box-shadow: 2px 0px 30px 5px rgba(0, 0, 0, 0.03);">
                <label for="uploadFileCover" class="hoviringdell uploadBox" id="uploadTrigger" style="height: 110px;">
                    <img src="frontend_public/uploads/attachment/upload.png" style="width: 38px;">
                    <div class="uploadText" style="font-size: 12px;">
                        <span style="color:#F69518;">Upload Cover</span><br>
                        Size 320x220
                    </div>
                </label>
                <input name="Cover_upload_image" class="hidden" id="uploadFileCover" type="file" onchange="return Upload_image_desktopCover()" required="required">
            </div>
            <div style="clear:both;"></div>


            <div class="half_width float_left">
                <label class="field_title">Restaurant Name</label>
                <input name="name" type="text" required>
            </div>

            <div class="half_width float_right">
                <label class="field_title">Slogan</label>
                <input name="slogan" type="text" required>
            </div>

            <div class="full_width clear_both">
                <label class="field_title">About</label>
                <textarea name="about" type="text" required></textarea>
            </div>

            <div class="half_width float_left">
                <label class="field_title">Speciality</label>
                <input name="speciality" type="text" required>
            </div>

            <div class="half_width float_right">
                <label class="field_title">Phone</label>
                <input name="phone" type="text" required>
            </div>

            <div class="half_width float_left">
                <label class="field_title">Timezone</label>
                <select name="timezone" class="form-control" required="">
                    <option value="">Select timezone</option>
                    <option value="-05:00">America/New York (-05:00)</option>
               </select>
            </div>

            <div class="half_width float_right">
                <label class="field_title">Country</label>
                <select name="country" class="form-control" required="">
                    <option value="">Select Country</option>

                    <?php  foreach( $country['msg']['countries'] as $str => $val ): ?>

                        <option value="<?php echo $val['Currency']['country']; ?>"><?php echo $val['Currency']['country']; ?></option>

                    <?php endforeach; ?>
               </select>
            </div>

            <div class="half_width float_left">
                <label class="field_title">State</label>
                <select name="state" class="form-control" required="">
                    <option value="">Select State</option>

                    <?php  foreach( $tax['msg'] as $str => $val ): ?>

                        <option value="<?php echo $val['Tax']['state']; ?>"><?php echo $val['Tax']['state']; ?></option>

                    <?php endforeach; ?>
               </select>
            </div>

            <div class="half_width float_right">
                <label class="field_title">City</label>
                <select name="city" class="form-control" required="">
                    <option value="">Select City</option>

                    <?php  foreach( $tax['msg'] as $str => $val ): ?>

                        <option value="<?php echo $val['Tax']['city']; ?>"><?php echo $val['Tax']['city']; ?></option>

                    <?php endforeach; ?>
               </select>
            </div>

            <div class="half_width float_left">
                <label class="field_title">Tax</label>
                <select name="tax_id" class="form-control" required="">
                    <option value="">Select Tax</option>

                    <?php  foreach( $tax['msg'] as $str => $val ): ?>

                        <option value="<?php echo $val['Tax']['id']; ?>"><?php echo $val['Tax']['tax']; ?> %</option>

                    <?php endforeach; ?>
               </select>
            </div>

            <div class="half_width float_right">
                <label class="field_title">Zip Code</label>
                <input name="zip" type="text" required>
            </div>

            <div class="half_width float_left">
                <label class="field_title">Locatino Lat</label>
                <input name="lat" type="text" required>
            </div>

            <div class="half_width float_right">
                <label class="field_title">Location Long</label>
                <input name="long" type="text" required>
            </div>
        <input type="hidden" name="currencyid" value="<?php echo $currency['msg'][0]['Currency']['id'] ?>" >
            <div class="half_width float_left">
                <label class="field_title">Minimum Order Price</label>
                <select name="min_order_price" class="form-control" required="">
                    <option value="">Select Amount</option>

                    <?php

                        for($i = 1; $i<=999; $i++) {
                            ?>
                                <option value="<?php echo $i; ?>"><?php echo $currency['msg'][0]['Currency']['symbol']; echo $i; ?></option>
                            <?php
                        }

                    ?>

               </select>

            </div>



            <div class="half_width float_right">
                <label class="field_title">Free Delivery Range</label>
                <select name="delivery_free_range" class="form-control" required="">
                    <option value="">Select KM Range</option>

                    <?php

                        for($i = 1; $i<=49; $i++) {
                            ?>
                                <option value="<?php echo $i; ?>"><?php echo $i; ?> KM</option>
                            <?php
                        }

                    ?>

               </select>
            </div>

            <div class="half_width float_left">
                <label class="field_title">AVG Food Prepation Time</label>
                <select name="preparation_time" class="form-control" required="">
                    <option value="">Select Minutes</option>
                    <option value="30">30 min</option>
                    <option value="40">40 min</option>
                    <option value="50">50 min</option>
                    <option value="60">60 min</option>
               </select>
            </div>

            <div class="half_width float_right">
                <label class="field_title">Tax implementation</label>
                <select name="tax_free" class="form-control" required="">
                    <option value="1">No Tax will implement</option>
                    <option value="0">Tax % will implement</option>
                </select>
            </div>

            <div class="clear_both"></div>





            <h3 style="font-weight: 300;" align="center">Restaurant Open/Close Timing</h3>
            <br>
            <div class="full_width">
                <input name="day[]" type="text" value="Sunday" style="background: #e1e1e11a;" readonly>
            </div>

            <div class="half_width float_left">
                <label class="field_title">Opening Time</label>
                <select name="opening_time[]" class="form-control" required="">
                    <option value="">Select Time</option>
                    <?php

                        for($i = 0; $i<=23; $i++) {
                            ?>
                                <option value="<?php echo $i; ?>:00:00" <?php if($i=="0"){echo "selected";} ?>><?php echo $i; ?>:00:00</option>
                            <?php
                        }


                    ?>
               </select>
            </div>

            <div class="half_width float_right">
                <label class="field_title">Closing Time</label>
                <select name="closing_time[]" class="form-control" required="">
                    <option value="">Select Time</option>
                    <?php

                    for($i = 0; $i<=23; $i++) {
                        ?>
                        <option value="<?php echo $i; ?>:00:00" <?php if($i=="23"){echo "selected";} ?>><?php echo $i; ?>:00:00</option>
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
                <select name="opening_time[]" class="form-control" required="">
                    <option value="">Select Time</option>
                    <?php

                    for($i = 0; $i<=23; $i++) {
                        ?>
                        <option value="<?php echo $i; ?>:00:00" <?php if($i=="0"){echo "selected";} ?>><?php echo $i; ?>:00:00</option>
                        <?php
                    }

                    ?>
               </select>
            </div>

            <div class="half_width float_right">
                <label class="field_title">Closing Time</label>
                <select name="closing_time[]" class="form-control" required="">
                    <option value="">Select Time</option>
                    <?php

                    for($i = 0; $i<=23; $i++) {
                        ?>
                        <option value="<?php echo $i; ?>:00:00" <?php if($i=="23"){echo "selected";} ?>><?php echo $i; ?>:00:00</option>
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
                <select name="opening_time[]" class="form-control" required="">
                    <option value="">Select Time</option>
                    <?php

                    for($i = 0; $i<=23; $i++) {
                        ?>
                        <option value="<?php echo $i; ?>:00:00" <?php if($i=="0"){echo "selected";} ?>><?php echo $i; ?>:00:00</option>
                        <?php
                    }

                    ?>
               </select>
            </div>

            <div class="half_width float_right">
                <label class="field_title">Closing Time</label>
                <select name="closing_time[]" class="form-control" required="">
                    <option value="">Select Time</option>
                    <?php

                    for($i = 0; $i<=23; $i++) {
                        ?>
                        <option value="<?php echo $i; ?>:00:00" <?php if($i=="23"){echo "selected";} ?>><?php echo $i; ?>:00:00</option>
                        <?php
                    }

                    ?>
               </select>
            </div>
            <div class="clear_both"></div>

            <br>
            <div class="full_width">
                <input name="day[]" type="text" value="Wednesday" style="background: #e1e1e11a;" readonly>
            </div>

            <div class="half_width float_left">
                <label class="field_title">Opening Time</label>
                <select name="opening_time[]" class="form-control" required="">
                    <option value="">Select Time</option>
                    <?php

                    for($i = 0; $i<=23; $i++) {
                        ?>
                        <option value="<?php echo $i; ?>:00:00" <?php if($i=="0"){echo "selected";} ?>><?php echo $i; ?>:00:00</option>
                        <?php
                    }
                    ?>
               </select>
            </div>

            <div class="half_width float_right">
                <label class="field_title">Closing Time</label>
                <select name="closing_time[]" class="form-control" required="">
                    <option value="">Select Time</option>
                    <?php

                    for($i = 0; $i<=23; $i++) {
                        ?>
                        <option value="<?php echo $i; ?>:00:00" <?php if($i=="23"){echo "selected";} ?>><?php echo $i; ?>:00:00</option>
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
                <select name="opening_time[]" class="form-control" required="">
                    <option value="">Select Time</option>
                    <?php

                    for($i = 0; $i<=23; $i++) {
                        ?>
                        <option value="<?php echo $i; ?>:00:00" <?php if($i=="0"){echo "selected";} ?>><?php echo $i; ?>:00:00</option>
                        <?php
                    }

                    ?>
               </select>
            </div>

            <div class="half_width float_right">
                <label class="field_title">Closing Time</label>
                <select name="closing_time[]" class="form-control" required="">
                    <option value="">Select Time</option>
                    <?php

                    for($i = 0; $i<=23; $i++) {
                        ?>
                        <option value="<?php echo $i; ?>:00:00" <?php if($i=="23"){echo "selected";} ?>><?php echo $i; ?>:00:00</option>
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
                <select name="opening_time[]" class="form-control" required="">
                    <option value="">Select Time</option>
                    <?php

                    for($i = 0; $i<=23; $i++) {
                        ?>
                        <option value="<?php echo $i; ?>:00:00" <?php if($i=="0"){echo "selected";} ?>><?php echo $i; ?>:00:00</option>
                        <?php
                    }

                    ?>
               </select>
            </div>

            <div class="half_width float_right">
                <label class="field_title">Closing Time</label>
                <select name="closing_time[]" class="form-control" required="">
                    <option value="">Select Time</option>
                    <?php

                    for($i = 0; $i<=23; $i++) {
                        ?>
                        <option value="<?php echo $i; ?>:00:00" <?php if($i=="23"){echo "selected";} ?>><?php echo $i; ?>:00:00</option>
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
                <select name="opening_time[]" class="form-control" required="">
                    <option value="">Select Time</option>
                    <?php
                    for($i = 0; $i<=23; $i++) {
                        ?>
                        <option value="<?php echo $i; ?>:00:00" <?php if($i=="0"){echo "selected";} ?>><?php echo $i; ?>:00:00</option>
                        <?php
                    }
                    ?>
               </select>
            </div>

            <div class="half_width float_right">
                <label class="field_title">Closing Time</label>
                <select name="closing_time[]" class="form-control" required="">
                    <option value="">Select Time</option>
                    <?php

                    for($i = 0; $i<=23; $i++) {
                        ?>
                        <option value="<?php echo $i; ?>:00:00" <?php if($i=="23"){echo "selected";} ?>><?php echo $i; ?>:00:00</option>
                        <?php
                    }
                    ?>
               </select>
            </div>
            <div class="clear_both"></div>

            <h3 style="font-weight: 300;" align="center">Restaurant Login information</h3>

            <div class="full_width">
                <label class="field_title">First Name</label>
                <input name="first_name" type="text" required>
            </div>

            <div class="full_width">
                <label class="field_title">Last Name</label>
                <input name="last_name" type="text" required>
            </div>

            <div class="full_width">
                <label class="field_title">Email</label>
                <input name="email" type="text" required>
            </div>

            <div class="full_width">
                <label class="field_title">Password</label>
                <input name="password" type="text" required>
            </div>

            <div class="full_width">
                <button class="com-button com-submit-button com-button--large " type="submit" style="width: 100%;" align="center">
                   Submit
                </button>
            </div>

        </form>
        </div>
    </div>
    <?php

}
else
if (@$_GET['action'] == "orderDetails")
{
    $id=$_GET['id'];
    $url=$baseurl . 'showOrderDetail';

    $data = array(
                "order_id" => $id
            );

   $json_return=@curl_request($data,$url);
   $currency=$json_return['msg'][0]['Restaurant']['Currency']['symbol'];

    ?>
    <div class="main-container dataTables_wrapper" id="table_view_wrapper" >
        <h2 style="font-weight: 300;" align="center">Order Details (#<?php echo $id; ?>)</h2>

        <div style="height:400px; overflow:scroll;">

            <h3 style="font-weight: 300;" align="left">Buyer Details</h3>

            <div style="line-height: 25px;margin-top: 10px;">
                <div class="full_width" style="font-size:13px;">
                    <span class="fa fa-user"></span>&nbsp;
                    <?php
                        echo $json_return['msg'][0]['UserInfo']['first_name']." ". $json_return['msg'][0]['UserInfo']['last_name'];
                    ?>
                </div>
                <div class="full_width" style="font-size:13px;">
                    <span class="fa fa-phone"></span>&nbsp;
                    <?php
                        echo $json_return['msg'][0]['UserInfo']['phone'];
                    ?>
                </div>

                <div class="full_width" style="font-size:13px;">
                    <span class="fa fa-street-view"></span>&nbsp;
                    <?php
                        echo $json_return['msg'][0]['Address']['city']." ".$json_return['msg'][0]['Address']['street'];
                    ?>
                </div>


            </div>

            <br>
            <h3 style="font-weight: 300;" align="left">Restaurant Details</h3>

            <div style="line-height: 25px;margin-top: 10px;">
                <div class="full_width" style="font-size:13px;">
                    <span class="fa fa-utensils"></span>&nbsp;
                    <?php
                        echo $json_return['msg'][0]['Restaurant']['name'];
                    ?>
                </div>

                <div class="full_width" style="font-size:13px;">
                    <span class="fa fa-phone"></span>&nbsp;
                    <?php
                        echo $json_return['msg'][0]['Restaurant']['phone'];
                    ?>
                </div>

                <div class="full_width" style="font-size:13px;">
                    <span class="fa fa-street-view"></span>&nbsp;
                    <?php
                        echo $json_return['msg'][0]['Restaurant']['RestaurantLocation']['city']." ".$json_return['msg'][0]['Restaurant']['RestaurantLocation']['state']." ".$json_return['msg'][0]['Restaurant']['RestaurantLocation']['country'];
                    ?>
                </div>
            </div>

            <br>
            <h3 style="font-weight: 300;" align="left">Restaurant Instructions</h3>

            <div style="line-height: 25px;margin-top: 10px;">
                <div class="full_width" style="font-size:13px;">
                    <?php
                        echo $json_return['msg'][0]['Order']['accepted_reason'];
                    ?>
                </div>
            </div>


            <br>
            <h3 style="font-weight: 300;" align="left">Customer Instructions</h3>

            <div style="line-height: 25px;margin-top: 10px;">
                <div class="full_width" style="font-size:13px;">
                    <?php
                        echo $json_return['msg'][0]['Order']['instructions'];
                    ?>
                </div>
            </div>



            <br>
            <h3 style="font-weight: 300;" align="left">Menu Item</h3>

            <div style="line-height: 25px;margin-top: 10px;">
                <div class="full_width" style="font-size:13px;">

                    <table style="font-size: 12px; width: 100%;">
                        <tr style="background: #e3e3e3;">
                            <td>Item Name</td>
                            <td>Qty.</td>
                            <td>Price</td>
                        </tr>
                        <?php  foreach( $json_return['msg'][0]['OrderMenuItem'] as $str => $val ): ?>

                        <tr style="background: #c8c1c11a;border-bottom: solid 1px #f0f0f0;">
                            <td><?php echo $val['quantity']."X ".$val['name']; ?></td>
                            <td><?php echo $val['quantity']; ?></td>
                            <td><?php echo $currency; echo $val['price']; ?></td>
                        </tr>

                        <?php endforeach; ?>
                    </table>



                    <br>
                    <div style="padding:0 20px;">
                        <div class="full_width" style="font-size:13px; width:200px; float:left;font-weight: bold;">
                            Tax
                            <?php

								if($json_return['msg'][0]['Restaurant']['tax_free']=="0")
								{
								    ?>
										(<?php echo $json_return['msg'][0]['Restaurant']['Tax']['tax']; ?>%)
									<?php
								}
							?>
                        </div>

                        <div class="full_width" style="font-size:13px; width:200px; float:right;" align="right">
                            <?php echo $currency; echo $json_return['msg'][0]['Restaurant']['Tax']['tax'];?>

                        </div>
                    </div>

                    <div style="padding:0 20px;">
                        <div class="full_width" style="font-size:13px; width:200px; float:left;font-weight: bold;">
                            Payment Method
                        </div>

                        <div class="full_width" style="font-size:13px; width:200px; float:right;" align="right">
                            <?php

                                if( $json_return['msg'][0]['Order']['payment_method_id'] != "0" )
                                {

                                    echo "Credit Card";

                                }
                                else
                                if( $json_return['msg'][0]['Order']['payment_method_id'] == "0" )
                                {

                                    echo "Cash on Delivery (COD)";

                                }
                            ?>
                        </div>
                    </div>

                    <div style="padding:0 20px;">
                        <div class="full_width" style="font-size:13px; width:200px; float:left;font-weight: bold;">
                            Delivery Fee
                        </div>

                        <div class="full_width" style="font-size:13px; width:200px; float:right;" align="right">
                            <?php echo $currency; echo $json_return['msg'][0]['Order']['delivery_fee']; ?>
                        </div>
                    </div>

                    <div style="padding:0 20px;">
                        <div class="full_width" style="font-size:13px; width:200px; float:left;font-weight: bold;">
                            SubTotal
                        </div>

                        <div class="full_width" style="font-size:13px; width:200px; float:right;" align="right">
                            <?php echo $currency; echo $json_return['msg'][0]['Order']['sub_total']; ?>
                        </div>
                    </div>

                </div>
            </div>


        </div>
    </div>
    <?php

}
else
if (@$_GET['action'] == "addAdminUser")
{
    
    ?>
    <div class="main-container dataTables_wrapper" id="table_view_wrapper" >
        <h2 style="font-weight: 300;" align="center">Add Admin User</h2>

        <div style="height:400px; overflow:scroll;">
            <form action="?p=adminUsers&action=addAdminUser" method="post" >
                <input name="user_id" type="hidden" value="<?php echo $id; ?>" required>
                <div class="full_width">
                    <label class="field_title">First Name</label>
                    <input name="first_name" type="text" required>
                </div>

                <div class="full_width">
                    <label class="field_title">Last Name</label>
                    <input name="last_name" type="text" required>
                </div>

                <div class="full_width">
                    <label class="field_title">Phone #</label>
                    <input name="phone" type="text" required>
                </div>

                <div class="full_width">
                    <label class="field_title">Email</label>
                    <input name="email" type="text" required>
                </div>

                <div class="full_width">
                    <label class="field_title">Password</label>
                    <input name="password" type="text" required>
                </div>
                <div class="full_width">
                    <button class="com-button com-submit-button com-button--large " type="submit" style="width: 100%;" align="center">
                       Submit
                    </button>
                </div>
            </form>
        </div>
    </div>
    <?php

}

?>