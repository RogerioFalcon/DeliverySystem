<?php
if( isset($_SESSION['id']))
{
?>

    <div class="qr-content">
            <div class="qr-page-content">
                <div class="qr-page toppaddzero">
                    <div class="qr-content-area">
                        <div class="qr-row">
                            <div class="qr-el">
                                <div class="page-title">
                                    <h2>Change Password</h2>
                                    <div class="head-area">
                                    </div>
                                </div>
                                <div class="panel margin-top-n">

                                    <p style="color: #000;" id="error_message">
                                          <?php
                                          
                                          /*
                                          * sucess message custom
                                          *
                                          * */
                                          
                                          $error = [
                                              1 => "Invalid Password",
                                          ];
                                          
                                          $error_id = isset($_GET['error']);
                                          if ($error != 0 && in_array($error_id, $error)) {
                                                echo $error[$error_id];
                                          }
                                          ?>
                                    </p>
                                    <p style="color: #000;" id="sucess_message">
                                          <?php
                                          
                                          /*
                                          * sucess message custom
                                          *
                                          * */
                                          
                                          $sucess = [
                                              1 => "Password Updated",
                                          ];
                                          
                                          $sucess_id = isset($_GET['sucess']);
                                          if ($sucess != 0 && in_array($sucess_id, $sucess)) {
                                                echo $sucess[$sucess_id];
                                          }
                                          ?>
                                    </p>

                                    <form action="includes/process.php?p=updatepassword" method="post"
                                          class="inputs validatedForm">
                                        <div class="inputs__item">
                                            <label for="previous-password" class="inputs__label">Previous
                                                password</label>
                                            <input type="password" name="password_previous" class="inputs__input"
                                                   id="previous-password"
                                                   value="" required="required"/>
                                        </div>
                                        <div class="inputs__item inputs__item--new">
                                            <label for="new-password" class="inputs__label">New password</label>
                                            <input type="password" class="inputs__input" id="new-password"
                                                   name="new_password"
                                                   value="" required="required"/>
                                        </div>
                                        <div class="inputs__item inputs__item--new">
                                            <label for="new-password" class="inputs__label">Confirm password</label>
                                            <input type="password" class="inputs__input" id="confirme-password"
                                                   value="" required="required"/>
                                        </div>


                                        <button name="password_update"
                                                class="com-button com-submit-button com-button--large com-button--default">
                                            <div class="com-submit-button__content"><span>Update Profile</span></div>
                                        </button>
                                    </form>
                                </div>

                            </div>
                        </div>
                    </div>
                </div>

            </div>
        

    <script>

        $(document).ready(function () {
            $('#table_view').DataTable();
        });


        Highcharts.chart('pie-chart', {
            chart: {
                plotBackgroundColor: null,
                plotBorderWidth: null,
                plotShadow: false,
                type: 'pie',
                height: 200,
            },
            title: {
                text: ''
            },
            tooltip: {
                pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
            },
            plotOptions: {
                pie: {
                    allowPointSelect: true,
                    cursor: 'pointer',
                    dataLabels: {
                        enabled: false
                    },
                    showInLegend: true
                }
            },
            series: [{
                name: '',
                colorByPoint: true,
                data: [{
                    name: 'Chrome',
                    y: 25,
                    sliced: true,
                    selected: true
                }, {
                    name: 'Internet Explorer',
                    y: 75
                }]
            }]
        });

        $.getJSON(
            'https://cdn.jsdelivr.net/gh/highcharts/highcharts@v7.0.0/samples/data/usdeur.json',
            function (data) {

                Highcharts.chart('hightchartslogins', {
                    chart: {
                        height: 200,
                        zoomType: 'x'
                    },
                    title: {
                        text: ''
                    },
                    subtitle: {
                        text: document.ontouchstart === undefined ?
                            'Click and drag in the plot area to zoom in' : 'Pinch the chart to zoom in'
                    },
                    xAxis: {
                        type: 'datetime'
                    },
                    yAxis: {
                        title: {
                            text: 'Exchange rate'
                        }
                    },
                    legend: {
                        enabled: false
                    },
                    plotOptions: {
                        area: {
                            fillColor: {
                                linearGradient: {
                                    x1: 0,
                                    y1: 0,
                                    x2: 0,
                                    y2: 1
                                },
                                stops: [
                                    [0, Highcharts.getOptions().colors[0]],
                                    [1, Highcharts.Color(Highcharts.getOptions().colors[0]).setOpacity(0).get('rgba')]
                                ]
                            },
                            marker: {
                                radius: 2
                            },
                            lineWidth: 1,
                            states: {
                                hover: {
                                    lineWidth: 1
                                }
                            },
                            threshold: null
                        }
                    },

                    series: [{
                        type: 'area',
                        name: 'USD to EUR',
                        data: data
                    }]
                });
            }
        );
    </script>
    <style>
        .highcharts-credits {
            display: none;
        }

        .place-card.default-card {
            display: none;
        }
    </style>
    
<?php
}
else 
{
	
	@header("Location: index.php");
    echo "<script>window.location='index.php'</script>";
    die;
    
} 
?>