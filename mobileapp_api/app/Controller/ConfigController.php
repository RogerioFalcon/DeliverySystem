<?php

App::uses('Lib', 'Utility');
App::uses('Firebase', 'Lib');
App::uses('Postmark', 'Utility');
App::uses('Message', 'Utility');
App::uses('Variables', 'Utility');
App::uses('PushNotification', 'Utility');
App::uses('CustomEmail', 'Utility');




class ConfigController extends AppController
{

    //public $components = array('Email');

    public $autoRender = false;
    public $layout = false;






    public function config()
    {

        $this->autoRender = true;
        $data['GOOGLE_MAPS_GEOCODE'] = 0;
        $data['GOOGLE_MAPS_DISTANCE_MATRIX'] = 0;
        $data['STRIPE_API_KEY'] = 0;
        $data['FIREBASE_URL'] = 0;
        $data['TWILIO'] = 0;
        $data['POSTMARK_SERVER_API_TOKEN'] = 0;
        $results = Lib::getCountryCityProvinceFromLatLong(40.678177, -73.944160);
        $duration = Lib::getDurationTimeBetweenTwoDistances(40.678177, -73.944160, 40.678177, -73.944160);


        if (strlen($results['country']) > 2) {

            $data['GOOGLE_MAPS_GEOCODE'] = 1;
        }

        if (count($duration) > 0) {
            $data['GOOGLE_MAPS_DISTANCE_MATRIX'] = 1;

        }

        $response = Lib::sendSmsVerificationCurl(TEST_MOBILE_NO, VERIFICATION_PHONENO_MESSAGE . ' ' . "1234");
        if (array_key_exists('code', $response)) {

            $data['TWILIO'] = 0;

        }

        if (array_key_exists('sid', $response)) {

            $data['TWILIO'] = 1;

        }

         $testPostMark = CustomEmail::testPostMark();
         $test_stripe = Lib::testStripe();

        if (array_key_exists('error', $test_stripe)) {

            $data['STRIPE_API_KEY'] = 0;
        }else{

            $data['STRIPE_API_KEY'] = 1;
        }


        if (array_key_exists('ErrorCode', $testPostMark)) {

            if($testPostMark['ErrorCode'] > 0) {
                $data['POSTMARK_SERVER_API_TOKEN'] = 0;

            }else{

                $data['POSTMARK_SERVER_API_TOKEN'] = 1;

            }
        }





            if (strpos(FIREBASE_URL.".json",'firebaseio.com') !== false) {

                $firebase_data = Firebase::verifyFirebaseUrl(FIREBASE_URL);

                $decode_firebase_data = json_decode($firebase_data, true);


                if(count($decode_firebase_data) > 0){

                    $data['FIREBASE_URL'] = 1;

                }

            }else{

                $data['FIREBASE_URL'] = 0;

            }


            if (@array_key_exists('error', $decode_firebase_data)) {

                $data['FIREBASE_URL'] = 0;

            }



             $push_result = $this->checkPushNotification();
             $json = Lib::isJsonError($push_result);


                    if ($json == "false") {

                        $data['FIREBASE_PUSH_NOTIFICATION_KEY'] = 1;
                    }else{

                        $data['FIREBASE_PUSH_NOTIFICATION_KEY'] = 0;

                    }


            $data['GOOGLE_MAPS_KEY'] = GOOGLE_MAPS_KEY;


        $this->set('data', $data);
    }



    public function checkPushNotification(){


        $notification['to'] = "1234456";
        $notification['notification']['title'] = "";
        $notification['notification']['body'] = "";
        $notification['notification']['badge'] = "1";
        $notification['notification']['sound'] = "default";
        $notification['notification']['icon'] = "";





        return PushNotification::sendPushNotificationToMobileDevice(json_encode($notification));


    }

}
?>