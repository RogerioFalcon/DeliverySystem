<div class="qr-sidebar">
    <div class="qr-sidebar-title-area">
        <div class="logo-area">
            <div class="qr-logo">
                <a href="#"> <img src="frontend_public/uploads/attachment/logo.png" alt=""> </a>
            </div>
        </div>
        <div class="burger-icon"> ☰</div>
    </div>

    <?php if ($_SESSION['role'] == "admin") { ?>
        <div class="not-mobile">
            <ul>
                <li>
                    <a href="dashboard.php" class="<?php if (strpos($_SERVER['REQUEST_URI'], " dashboard ") !== false) {
                              echo "router-link-active ";
                        } ?>"> <i aria-hidden="true" class="fa fa-tachometer"></i> Dashboard</a>
                </li>
                <li><a href="all_users.php" class="<?php if (strpos($_SERVER['REQUEST_URI'], " all_users ") !== false) {
                              echo "router-link-active ";
                        } ?>"><i aria-hidden="true" class="fa fa-users"></i> All Users</a></li>
                <li><a href="leave_applicaton.php" class="<?php if (strpos($_SERVER['REQUEST_URI'], " leave_applicaton ") !== false) {
                               echo "router-link-active ";
                         } ?>"><i aria-hidden="true" class="fa fa-bar-chart"></i> Leave <span class="count">1</span></a>
                </li>
                
                <li><a href="change_password.php"><i aria-hidden="true" class="right-align fa fa-lock"></i> Change
                          Password</a>
                </li>
                <li>
                    <form method="post" action="" class="paddadjs">
                        <input type="hidden" value="ok" name="logout"> <i aria-hidden="true" class="right-align fa fa-sign-out"></i>
                        <input type="submit" class="stylenull" value="Log Out" name="logout-btn" />
                </li>
                </form>
            </ul>
        </div>
        <?php } elseif ($_SESSION['role'] == "user") { ?>

            <div class="not-mobile">
                <ul>
                    <li><a href="ShowAttendance.php" class="<?php if (strpos($_SERVER['REQUEST_URI'], " ShowAttendance ") !== false) {
                               echo "router-link-active ";
                         } ?>"><i aria-hidden="true" class="fa fa-clock-o"></i> My Attendance</a></li>
                    <li><a href="leave_applicaton_user.php" class="<?php if (strpos($_SERVER['REQUEST_URI'], " leave_applicaton_user ") !== false) {
                               echo "router-link-active ";
                         } ?>"><i aria-hidden="true" class="fa fa-calendar"></i> Leave Application </a></li>
                    <li><a href="profile.php" class="<?php if (strpos($_SERVER['REQUEST_URI'], " profile ") !== false) {
                               echo "router-link-active ";
                         } ?>"><i aria-hidden="true" class="right-align  fa fa-user"></i> Profile</a></li>
                    <li><a href="change_password.php" class="<?php if (strpos($_SERVER['REQUEST_URI'], " change_password ") !== false) {
                              echo "router-link-active ";
                        } ?>"><i aria-hidden="true" class="right-align fa fa-lock"></i> Change
                          Password</a>
                    </li>
                    <li>
                        <form method="post" action="" class="paddadjs">
                            <input type="hidden" value="ok" name="logout"> <i aria-hidden="true" class="right-align fa fa-sign-out"></i>
                            <input type="submit" class="stylenull" value="Log Out" name="logout-btn" />
                    </li>
                    </form>
                </ul>
            </div>
            <?php } ?>
                <div class="mobile-only"></div>
</div>