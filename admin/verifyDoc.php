<?php 
if( isset($_SESSION['sessionTokon']))
{
    if(isset($_GET['action'])){
        
        if($_GET['action']=="verifyDocStatus") 
        {
            $user_id = $_GET['user_id'];
            $id = $_GET['id'];
            $status = $_GET['status'];
            
            $url=$baseurl . 'updateVerificationDocumentStatus';
            
            $data = array(
                "id" => $id, 
                "status" => $status
            );
            
            $json_data=@curl_request($data,$url);
           if($json_data['code'] !== 200)
           {
                echo "<script>window.location='dashboard.php?p=verifyDoc&user_id=".$user_id."&action=error'</script>";
           }
           else
           {
                echo "<script>window.location='dashboard.php?p=verifyDoc&user_id=".$user_id."&action=success'</script>";
           }

            
        }
        else
        if($_GET['action']=="deleteDocument") 
        {
            $user_id = $_GET['user_id'];
            $id = $_GET['id'];
            
            $url=$baseurl . 'deleteVerificationDocument';
            
            $data = array(
                "id" => $id
            );
            
            $json_data=@curl_request($data,$url);
          
            
           if($json_data['code'] !== 200)
           {
                echo "<script>window.location='dashboard.php?p=verifyDoc&user_id=".$user_id."&action=error'</script>";
           }
           else
           {
                echo "<script>window.location='dashboard.php?p=verifyDoc&user_id=".$user_id."&action=success'</script>";
           }

            
        }
        
    }
    
        $url=$baseurl . 'showAllUserVerificationDocuments';
        $user_id = htmlspecialchars($_GET['user_id'], ENT_QUOTES);
        $data = 
        array(
            "user_id" => $user_id
        );
        
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
                                    <h2>All Documents</h2>
                                    <div class="head-area">
                                    </div>
                                </div>
                                
                                <div class="qr-row1">
                                    
                                    <?php 
                                        foreach ($allusers as $single_user): 
                                        
                                    ?>
                                    
                                        <div class="qr-el qr-el-1" style="float: left;">
                                            <div style="height: 160px;">
                                                <a href="<?php echo $imagebaseurl.$single_user['VerificationDocument']['file']; ?>" target="_blank">
                                                    <img src="<?php echo $imagebaseurl.$single_user['VerificationDocument']['file']; ?>" alt="slider image" style="width: 270px; height: 155px;">
                                                </a>
                                                <div style="width:80%;float: left;font-size: 12px;padding: 5px 0;">
                                                    Status:
                                                    
                                                    <?php
                                                        if($single_user['VerificationDocument']['status']=="0")
                                                        {
                                                            ?>
                                                                <span style="color:orange;">pending</span>
                                                            <?php
                                                        }
                                                        else
                                                        if($single_user['VerificationDocument']['status']=="1")
                                                        {
                                                            ?>
                                                                <span style="color:green;">Approved</span>
                                                            <?php
                                                        }
                                                        else
                                                        if($single_user['VerificationDocument']['status']=="2")
                                                        {
                                                            ?>
                                                                <span style="color:red;">Reject</span>
                                                            <?php
                                                        }
                                                    ?>
                                                    
                                                </div>
                                                <div style="width:20%;float: left;">
                                                    <div class="more" style="margin-top: 15px;margin-left: 15px;">
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
                                                                <a href="dashboard.php?p=verifyDoc&action=verifyDocStatus&user_id=<?php echo @$_GET['user_id']; ?>&status=1&id=<?php echo $single_user['VerificationDocument']['id']; ?>">
                                                                    <li class="more-menu-item" role="presentation" >
                                                                        <button type="button" class="more-menu-btn" role="menuitem">Approved</button>
                                                                    </li>
                                                                </a>
                                                                <a href="dashboard.php?p=verifyDoc&action=verifyDocStatus&user_id=<?php echo @$_GET['user_id']; ?>&status=2&id=<?php echo $single_user['VerificationDocument']['id']; ?>">
                                                                    <li class="more-menu-item" role="presentation" >
                                                                        <button type="button" class="more-menu-btn" role="menuitem">Reject</button>
                                                                    </li>
                                                                </a>
                                                                <a href="dashboard.php?p=verifyDoc&action=deleteDocument&user_id=<?php echo @$_GET['user_id']; ?>&id=<?php echo $single_user['VerificationDocument']['id']; ?>">
                                                                    <li class="more-menu-item" role="presentation" >
                                                                        <button type="button" class="more-menu-btn" role="menuitem">Delete Document</button>
                                                                    </li>
                                                                </a>
                                                            </ul>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div style="clear:both;"></div>
                                            </div>
                                        </div>
                                    
                                    <?php endforeach; ?>
                                    <div style="clear:both;"></div>
                                    
                                </div>
                                
                                
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
        
        
        
        document.getElementById("uploadFile").onchange = function () {
            Upload_image_desktop();
        };
        
        
        function Upload_image_desktop() {

            var fileUpload = document.getElementById("uploadFile");
    
    
            var regex = new RegExp("([a-zA-Z0-9\s_\\.\-:])+(.jpg|.png)$");
            if (regex.test(fileUpload.value.toLowerCase())) {
    
    
                if (typeof (fileUpload.files) != "undefined") {
    
                    var reader = new FileReader();
    
                    reader.readAsDataURL(fileUpload.files[0]);
                    reader.onload = function (e) {
    
                        var image = new Image();
    
    
                        image.src = e.target.result;
    
    
                        image.onload = function () {
                            var height = this.height;
                            var width = this.width;
    
                            if (height == 350 && width == 610) {
    
                                document.getElementById("sliderImageform").submit();
    
                            } else {
    
                                alert("Size 350x610");
                                return false;
                            }
                        };
    
                    }
                } else {
                    alert("This browser does not support HTML5.");
                    return false;
                }
            } else {
                alert("Please select a valid Image file.");
                return false;
            }
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