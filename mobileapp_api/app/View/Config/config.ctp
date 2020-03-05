<?php App::uses('Debugger', 'Utility');


if (Configure::read('debug') > 0):
    Debugger::checkSecurityKeys();
endif;

?>

<!DOCTYPE html>
<html>
<head>
    <title>Qboxus - Server Requirements</title>
    <style>
        body {
            padding-top: 18px;
            font-family: sans-serif;
            background: #f9fafb;
            font-size: 14px;
        }

        #container {
            width: 600px;
            margin: 0 auto;
            background: #fff;
            border-radius: 10px;
            padding: 15px;
            border: 2px solid #f0f0f0;
            -webkit-box-shadow: 0px 1px 15px 1px rgba(90, 90, 90, 0.08);
            box-shadow: 0px 1px 15px 1px rgba(90, 90, 90, 0.08);
        }

        a {
            text-decoration: none;
            color: red;
        }

        h1 {
            text-align: center;
            color: #424242;
            border-bottom: 1px solid #e4e4e4;
            padding-bottom: 25px;
            font-size: 22px;
            font-weight: normal;
        }

        table {
            width: 100%;
            padding: 10px;
            border-radius: 3px;
        }

        table thead th {
            text-align: left;
            padding: 5px 0px 5px 0px;
        }

        table tbody td {
            padding: 5px 0px;
        }

        table tbody td:last-child, table thead th:last-child {
            text-align: right;
        }

        .label {
            padding: 3px 10px;
            border-radius: 4px;
            color: #fff;

        }

        .label.label-success {
            background: #4ac700;
        }

        .label.label-warning {
            background: #dc2020;
        }


        .logo {
            margin-bottom: 30px;
            margin-top: 20px;
            display: block;
        }

        .logo img {
            margin: 0 auto;
            display: block;
            border-radius: 50%;
        }

        .scene {
            width: 100%;
            height: 100%;
            perspective: 600px;
            display: -webkit-box;
            display: -moz-box;
            display: -ms-flexbox;
            display: -webkit-flex;
            display: flex;
            align-items: center;
            justify-content: center;

        svg {
            width: 240px;
            height: 240px;
        }

        }

        @keyframes arrow-spin {
            50% {
                transform: rotateY(360deg);
            }
        }
    </style>
</head>
<body>
<?php
$error = false;


    $filePresent = null;
    if (file_exists(APP . 'Config' . DS . 'database.php')):
        $database_file = "<span class='label label-success'>Success</span>";

        //echo __d('cake_dev', 'Your database configuration file is present.');
        $filePresent = true;

    else:
       // echo '<span class="notice">';
        //echo __d('cake_dev', 'Your database configuration file is NOT present.');
        //echo '<br/>';
       // echo __d('cake_dev', 'Rename %s to %s', 'APP/Config/database.php.default', 'APP/Config/database.php');
        //echo '</span>';
        $database_file = "<span class='label label-warning'>Failed</span>";
    endif;
    ?>
</p>
<?php
if (isset($filePresent)):
    App::uses('ConnectionManager', 'Model');
    try {
        $connected = ConnectionManager::getDataSource('default');
    } catch (Exception $connectionError) {
        $connected = false;
        $errorMsg = $connectionError->getMessage();
        if (method_exists($connectionError, 'getAttributes')):
            $attributes = $connectionError->getAttributes();
            if (isset($errorMsg['message'])):
              //  $errorMsg .= '<br />' . $attributes['message'];
            endif;
        endif;
    }
    ?>
    <p>
        <?php
        if ($connected && $connected->isConnected()):
            $database_connection = "<span class='label label-success'>Success</span>";
        else:
            $database_connection = "<span class='label label-warning'>Failed</span>";
        endif;
        ?>
    </p>
    <?php
endif;


if (strlen($data['GOOGLE_MAPS_KEY']) < 10) {
    $google = "<span class='label label-warning'>Failed</span>";
} else {
    $google = "<span class='label label-success'>Success</span>";
}

if ($data['GOOGLE_MAPS_GEOCODE'] == 0) {
    $geocode = "<span class='label label-warning'>Failed</span>";
} else {
    $geocode = "<span class='label label-success'>Success</span>";
}

if ($data['GOOGLE_MAPS_DISTANCE_MATRIX'] == 0) {
    $distance_matrix = "<span class='label label-warning'>Failed</span>";
} else {
    $distance_matrix = "<span class='label label-success'>Success</span>";
}



if ($data['FIREBASE_PUSH_NOTIFICATION_KEY']== 0) {
    $FIREBASE_PUSH_NOTIFICATION_KEY = "<span class='label label-warning'>Failed</span>";
} else {
    $FIREBASE_PUSH_NOTIFICATION_KEY = "<span class='label label-success'>Success</span>";
}

if ($data['FIREBASE_URL'] == 0 ) {
    $FIREBASE_URL = "<span class='label label-warning'>Failed</span>";
} else {
    $FIREBASE_URL = "<span class='label label-success'>Success</span>";
}

if ($data['TWILIO'] == 0) {
    $TWILIO = "<span class='label label-warning'>Failed</span>";
} else {
    $TWILIO = "<span class='label label-success'>Success</span>";
}

if ($data['POSTMARK_SERVER_API_TOKEN'] == 0) {
    $POSTMARK_SERVER_API_TOKEN = "<span class='label label-warning'>Failed</span>";
} else {
    $POSTMARK_SERVER_API_TOKEN = "<span class='label label-success'>Success</span>";
}

if ($data['STRIPE_API_KEY'] == 0) {
    $STRIPE_API_KEY = "<span class='label label-warning'>Failed</span>";
} else {
    $STRIPE_API_KEY = "<span class='label label-success'>Success</span>";
}
 $PHP_VERSION = PHP_VERSION;
if ($PHP_VERSION >= 7 && $PHP_VERSION < 7.1 ) {
    $requirement1 = "<span class='label label-success'>v." . PHP_VERSION . '</span>';
} else {
    $error = true;
    $requirement1 = "<span class='label label-warning'>Your PHP version is " . $PHP_VERSION . '</span>';
}



if (!extension_loaded('pdo')) {
    $error = true;
    $requirement3 = "<span class='label label-warning'>Not enabled</span>";
} else {
    $requirement3 = "<span class='label label-success'>Enabled</span>";
}

if (!extension_loaded('curl')) {
    $error = true;
    $requirement4 = "<span class='label label-warning'>Not enabled</span>";
} else {
    $requirement4 = "<span class='label label-success'>Enabled</span>";
}

if (!extension_loaded('openssl')) {
    $error = true;
    $requirement5 = "<span class='label label-warning'>Not enabled</span>";
} else {
    $requirement5 = "<span class='label label-success'>Enabled</span>";
}

if (!extension_loaded('mbstring')) {
    $error = true;
    $requirement6 = "<span class='label label-warning'>Not enabled</span>";
} else {
    $requirement6 = "<span class='label label-success'>Enabled</span>";
}

if (!extension_loaded('ctype') && !function_exists('ctype')) {
    $error = true;
    $requirement7 = "<span class='label label-warning'>Not enabled</span>";
} else {
    $requirement7 = "<span class='label label-success'>Enabled</span>";
}


if (!extension_loaded('gd')) {
    $error = true;
    $requirement9 = "<span class='label label-warning'>Not enabled</span>";
} else {
    $requirement9 = "<span class='label label-success'>Enabled</span>";
}

if (!extension_loaded('zip')) {
    $error = true;
    $requirement10 = "<span class='label label-warning'>Zip Extension is not enabled</span>";
} else {
    $requirement10 = "<span class='label label-success'>Enabled</span>";
}

$url_f_open = ini_get('allow_url_fopen');
if ($url_f_open != "1" && $url_f_open != 'On') {
    $error = true;
    $requirement11 = "<span class='label label-warning'>Allow_url_fopen is not enabled!</span>";
} else {
    $requirement11 = "<span class='label label-success'>Enabled</span>";
}

?>
<div id="container">
    <div class="logo">
        <a href="#">
            <img width="80px" src=" data:image/jpeg;base64,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"></a>
    </div>
    <h1>Server Requirements</h1>


    <table class="table table-hover" id="requirements">
        <thead>
        <tr>
            <th>Requirements</th>
            <th>Result</th>
        </tr>
        </thead>
        <tbody>
        <tr>
            <td>Database file</td>
            <td><?php echo $database_file; ?></td>
        </tr>
        <tr>
            <td>Database Connection</td>
            <td><?php echo $database_connection; ?></td>
        </tr>

        <tr>
            <td>Google Maps Key</td>
            <td><?php echo $google; ?></td>
        </tr>

        <tr>
            <td>Google Maps Geocode permission</td>
            <td><?php echo $geocode; ?></td>
        </tr>

        <tr>
            <td>Google Maps Distance Matrix permission</td>
            <td><?php echo $distance_matrix; ?></td>
        </tr>

        <tr>
            <td>Firebase Push Notification Key</td>
            <td><?php echo $FIREBASE_PUSH_NOTIFICATION_KEY; ?></td>
        </tr>

        <tr>
            <td>Firebase URL</td>
            <td><?php echo $FIREBASE_URL; ?></td>
        </tr>

        <tr>
            <td>Twilio - Phone Number Verification</td>
            <td><?php echo $TWILIO; ?></td>
        </tr>

        <tr>
            <td>PostMark - Sending Emails</td>
            <td><?php echo $POSTMARK_SERVER_API_TOKEN; ?></td>
        </tr>

        <tr>
            <td>Stripe - Payment Method</td>
            <td><?php echo $STRIPE_API_KEY; ?></td>
        </tr>
        <tr>
            <td>PHP 7.0.0+</td>
            <td><?php echo $requirement1; ?></td>
        </tr>

        <tr>
            <td>PDO PHP Extension</td>
            <td><?php echo $requirement3; ?></td>
        </tr>
        <tr>
            <td>cURL PHP Extension</td>
            <td><?php echo $requirement4; ?></td>
        </tr>
        <tr>
            <td>OpenSSL PHP Extension</td>
            <td><?php echo $requirement5; ?></td>
        </tr>
        <tr>
            <td>MBString PHP Extension</td>
            <td><?php echo $requirement6; ?></td>
        </tr>


        <tr>
            <td>GD PHP Extension</td>
            <td><?php echo $requirement9; ?></td>
        </tr>
        <tr>
            <td>Zip PHP Extension</td>
            <td><?php echo $requirement10; ?></td>
        </tr>
        <tr>
            <td>allow_url_fopen</td>
            <td><?php echo $requirement11; ?></td>
        </tr>
        </tbody>

    </table>
    <br/>

</div>

</body>
</html>
