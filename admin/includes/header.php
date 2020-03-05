<!DOCTYPE html>
<html lang="en">
<?php include("constant.php") ?>
<meta http-equiv="content-type" content="text/html;charset=UTF-8"/>

<head>
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <meta name="description" content="WiFi Admin Dashboard"/>
    <meta name="author" content=""/>
    <meta name="csrf-token" content="GtJPRtA43dGDwYBYLXszN4GFKL5m9qJR8DzyVuW8"/>
    <title>D-Track Employee Mangement</title>
    <link rel="shortcut icon"
          href="https://lh3.googleusercontent.com/thV0Ipz-1e2Ht7GBoMhTSxa4C27w7av9y5W7miae_g7UzFDGghE71SNRsR0QnTxSsKc=s60">
    <link href='https://fonts.googleapis.com/css?family=Droid+Sans:400,700' rel='stylesheet' type='text/css'>
    <link href='https://fonts.googleapis.com/css?family=Lato:300,400,700' rel='stylesheet' type='text/css'>
    <link href="https://fonts.googleapis.com/css?family=Roboto:300,400,700" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css?family=Nunito:400,600,700" rel="stylesheet">
    <link rel="stylesheet" href="frontend_public/assets-minified/css/common.min.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Noto+Sans:400,700,400italic">
    <link rel="stylesheet" href="frontend_public/assets-minified/css/not_landing.min.css">
    <link rel="stylesheet" href="frontend_public/assets-minified/css/neon.min.css">
    <link rel="stylesheet" href="frontend_public/assets-minified/css/style.css?time=<?php echo time(); ?>">

    <style>

    </style>

    <script src="frontend_public/assets-minified/js/common.min.js"></script>
    <script src="https://code.highcharts.com/highcharts.js"></script>
    <script src="https://code.highcharts.com/modules/exporting.js"></script>
    <script src="https://code.highcharts.com/modules/export-data.js"></script>
    <script type="text/javascript" language="javascript" src="https://code.jquery.com/jquery-3.3.1.js"></script>

    <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
    <link rel="stylesheet" href="/resources/demos/style.css">

    <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>

    <script>
        $( function() {
            $( "#datepicker" ).datepicker();
        } );


        $(document).on('click', '.datepicker', function(){
            $(this).timePicker({
                changeMonth: true,
                changeYear: true,
                dateFormat: "yy-mm-dd"
            }).focus();
            $(this).removeClass('datepicker');
        });




    </script>

</head>
