<div class="qr-sidebar">
    <div class="qr-sidebar-title-area">
        <div class="logo-area">
            <div class="qr-logo">
                <a href="#"> <img src="frontend_public/uploads/attachment/logo.png" alt=""> </a>
            </div>
        </div>
        <div class="burger-icon"> ☰</div>
    </div>

    <?php if (@$_SESSION['role'] == "0") { ?>
        <div class="not-mobile">
            <ul>
                
                <li>
                    <a href="dashboard.php?p=users" class="<?php if (strpos($_SERVER['REQUEST_URI'], "users") !== false) { echo "router-link-active ";} ?>"> 
                        <i aria-hidden="true" class="fa fa-users"></i> Users
                    </a>
                </li>
                <li>
                    <a href="dashboard.php?p=restaurants" class="<?php if (strpos($_SERVER['REQUEST_URI'], "Restaurants") !== false) { echo "router-link-active ";} ?>"> 
                        <i aria-hidden="true" class="fa fa-utensils"></i> Restaurants
                    </a>
                </li>
                <li>
                    <a href="dashboard.php?p=manageOrders" class="<?php if (strpos($_SERVER['REQUEST_URI'], "manageOrders") !== false) { echo "router-link-active "; } ?>"> 
                        <i aria-hidden="true" class="fa fa-stream"></i> Manage Orders
                    </a>
                </li>
                <li>
                    <a href="dashboard.php?p=rider" class="<?php if (strpos($_SERVER['REQUEST_URI'], "rider") !== false) { echo "router-link-active ";} ?>"> 
                        <i aria-hidden="true" class="fa fa-biking"></i> Riders
                    </a>
                </li>
                <li>
                    <a href="dashboard.php?p=appSliders" class="<?php if (strpos($_SERVER['REQUEST_URI'], "appSliders") !== false) { echo "router-link-active ";} ?>"> 
                        <i aria-hidden="true" class="fa fa-image"></i> App Sliders
                    </a>
                </li>
                
                <li>
                    <a href="dashboard.php?p=taxSetting" class="<?php if (strpos($_SERVER['REQUEST_URI'], "taxSetting") !== false) { echo "router-link-active ";} ?>"> 
                        <i aria-hidden="true" class="fa fa-hand-holding-usd"></i> Tax Setting
                    </a>
                </li>
                
                <li>
                    <a href="dashboard.php?p=manageCurrency" class="<?php if (strpos($_SERVER['REQUEST_URI'], "manageCurrency") !== false) { echo "router-link-active ";} ?>"> 
                        <i aria-hidden="true" class="fa fa-dollar-sign"></i> Manage Currency
                    </a>
                </li>
                
                <li>
                    <a href="dashboard.php?p=changePassword" class="<?php if (strpos($_SERVER['REQUEST_URI'], "changepassword") !== false) { echo "router-link-active ";} ?>"> 
                        <i aria-hidden="true" class="fa fa-unlock-alt"></i>
                        Change Password
                    </a>
                </li>
                <li>
                    <a href="dashboard.php?p=adminUsers" class="<?php if (strpos($_SERVER['REQUEST_URI'], "adminUsers") !== false) { echo "router-link-active ";} ?>"> 
                        <i aria-hidden="true" class="fa fa-users"></i>
                        Admin Users
                    </a>
                </li>
                <li>
                    <a href="dashboard.php?p=logout" class="<?php if (strpos($_SERVER['REQUEST_URI'], "logout") !== false) { echo "router-link-active ";} ?>"> 
                        <i aria-hidden="true" class="right-align fa fa-sign-out-alt"></i> Logout
                    </a>
                </li>
                
            </ul>
        </div>
        <?php } ?>
                <div class="mobile-only"></div>
</div>