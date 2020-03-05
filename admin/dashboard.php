<?php 
include("header.php"); 
if(@$_SESSION['sessionPortal']=="foodAdmin")
{
?>
<div class="mainwrapper allusers">
    <div class="qr-layout">
	
	<?php require_once("rightsidebar.php"); ?> 
	
	
		<?php 
		
		if(isset($_GET['p']) ) 
		{ 
		    if( $_GET['p'] == "users" ) 
           	{ 
    			include("users.php");
    		} 
    		
    		if( $_GET['p'] == "restaurants" ) 
    		{ 
    			include("restaurants.php");
    		} 
    		
    		if( $_GET['p'] == "manageOrders" ) 
    		{ 
    			include("manageOrders.php");
    		}
    		
    		if( $_GET['p'] == "rider" ) 
    		{ 
    			include("rider.php");
    		}
    		
    		if( $_GET['p'] == "appSliders" ) 
    		{ 
    			include("appSliders.php");
    		}
    		
    		if( $_GET['p'] == "taxSetting" ) 
    		{ 
    			include("taxSetting.php");
    		}
    		
    		if( $_GET['p'] == "manageCurrency" ) 
    		{ 
    			include("manageCurrency.php");
    		}
    		
    		if( $_GET['p'] == "changePassword" ) 
    		{ 
    			include("change_password.php");
    		}
    		
    		if( $_GET['p'] == "verifyDoc" ) 
    		{ 
    			include("verifyDoc.php");
    		}
    		
    		if( $_GET['p'] == "assignRider" ) 
    		{ 
    			include("assignRider.php");
    		}
    		
    		if( $_GET['p'] == "adminUsers" ) 
    		{ 
    			include("adminUsers.php");
    		}
    		
    		if( $_GET['p'] == "editResturent" )
    		{
    			include("editResturent.php");
    		}
		}
		
		?>
	</div>
</div>

<?php 
require_once("footer.php"); 
}
else
{
    echo "<script>window.location='index.php'</script>";
}


?>