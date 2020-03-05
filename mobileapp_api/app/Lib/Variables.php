<?php
define('APP_STATUS', 'live');
define('APP_NAME', 'foodies');
define('TEST_MOBILE_NO', '+13472720544'); //e.g +13472720544 // no should be with the country code
define('BASE_URL', 'http://domain.com/foodies/mobileapp_api/');
date_default_timezone_set('Asia/Karachi');


define('UPLOADS_FOLDER_URI', 'app/webroot/uploads');
define('VERIFICATION_DOCUMENTS', 'verification_documents');
define('PREP_REGISTRATION_SUBJECT', 'Confirm your Foodies Registration');
define('VERIFICATION_PHONENO_MESSAGE', 'Your verification code is');

//DATABASE
define('DATABASE_USER', 'DATABASE USERNAME HERE');
define('DATABASE_PASSWORD', 'DATABASE PASSWORD HERE');
define('DATABASE_NAME', 'DATABASE NAME HERE');

//PostMark
define('POSTMARK_SERVER_API_TOKEN', 'POSTMARK SERVER API TOKON KEY HERE');
define('SUPPORT_EMAIL', 'EMAIL ADDRESS FROM WHERE YOU WANT TO SEND EMAIL HERE');

//Google Maps
define('GOOGLE_MAPS_KEY', 'GOOGLE MAP API KEY HERE');

//Twilio
define('TWILIO_ACCOUNTSID', 'TWILIO ACCOUNT ID HERE');
define('TWILIO_AUTHTOKEN', 'TWILIO AUTH TOKON HERE');
define('TWILIO_NUMBER', 'TWILIO PURCHASED NUMBER HERE');

//Firebase
define('FIREBASE_PUSH_NOTIFICATION_KEY', 'FIREBASE PUSH NOTIFICATION HERE');

define('FIREBASE_URL', 'FIREBASE REAL TIME DATABASE URL HERE');//https://foodies-abcd.firebaseio.com/

//STRIPE
define('STRIPE_API_KEY','STRIPE API KEY HERE');
define('STRIPE_CURRENCY', 'usd');





//Testing


define('DEBUG_VALUE', 2); //0 means no errors will display on the screen. 2 means all the errors






?>


