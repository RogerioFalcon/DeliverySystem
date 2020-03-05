<?php 
include("header.php"); 
if( isset($_SESSION['sessionTokon']))
{
    echo "<script>window.location='dashboard.php?p=users'</script>";
}
?>

<div>
    <div class="login-page login-form-fall">

        <div class="login-container">
            <div class="login-header login-caret">
                <div class="login-content">
                    <a href="#" class="logo">
                        <img src="frontend_public/uploads/attachment/logo.png?time=<?php echo time(); ?>" width="220" alt="site-logo"/>
                    </a>
                </div>
            </div>

            <div class="login-form">
                <div class="login-content">

                    <p style="color: #000;" id="error_message">
                          <?php
                          
                          /*
                          * sucess message custom
                          *
                          * */
                          
                          $error = [
                              1 => "Invalid Email Or Password",
                          ];
                          
                          $error_id = isset($_GET['error']);
                          if ($error != 0 && in_array($error_id, $error)) {
                                echo $error[$error_id];
                          }
                          ?>
                    </p>

                    <form method="POST" action="index.php?p=login" accept-charset="UTF-8" class="validate">
                        <div class="form-group">
                            <div class="input-group">
                                <div class="input-group-addon">
                                    <i class="entypo-user"></i>
                                </div>
                                <input class="form-control" placeholder="Email Address" autocomplete="off" name="email" type="email">
                            </div>
                            <small class='ve-error'></small>
                        </div>

                        <div class="form-group">
                            <div class="input-group">
                                <div class="input-group-addon">
                                    <i class="entypo-key"></i>
                                </div>
                                <input class="form-control" data-validate="required" placeholder="Password" autocomplete="off" name="password" type="password" value="">
                            </div>
                            <small class='ve-error'></small>
                        </div>

                        <div class="form-group">
                            <button type="submit" name="login_btn" class="btn btn-primary btn-block btn-login">
                                Log In
                                <i class="entypo-login"></i>
                            </button>
                        </div>
                    </form>

                </div>
            </div>
        </div>
    </div>
      
<?php 

include("footer.php"); 


?>

