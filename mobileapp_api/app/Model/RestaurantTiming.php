<?php



class RestaurantTiming extends AppModel
{

    public $useTable = 'restaurant_timing';



    public function isRestaurantOpen($day,$time,$restaurant_id)
    {



        return $this->find('first', array(



           // 'contain'=>array('RestaurantMenu.RestaurantMenuItem.RestaurantMenuExtraItem'),
            'conditions' => array(

                'RestaurantTiming.restaurant_id' => $restaurant_id,
                'RestaurantTiming.day' => $day,
                






            ),


            'recursive' => 0

        ));


    }


    public function getDetails($restaurant_id)
    {



        return $this->find('all', array(



            // 'contain'=>array('RestaurantMenu.RestaurantMenuItem.RestaurantMenuExtraItem'),
            'conditions' => array(

                'RestaurantTiming.restaurant_id' => $restaurant_id,







            ),


            'recursive' => 0

        ));


    }

    public function deleteRestaurantTiming($restaurant_id){


        return $this->deleteAll(
            [
                'RestaurantTiming.restaurant_id' => $restaurant_id,


            ],
            false # <- single delete statement please
        );
    }
}


?>