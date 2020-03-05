<?php
/**
 * @link          http://cakephp.org CakePHP(tm) Project
 * @package       app.View.Pages
 * @since         CakePHP(tm) v 0.10.0.1076
 */

if (!Configure::read('debug')):
    throw new NotFoundException();
endif;

App::uses('Debugger', 'Utility');
?>
<h2><?php //echo __d('cake_dev', 'Release Notes for CakePHP %s.', Configure::version()); ?></h2>
<p>
    <?php //echo $this->Html->link(__d('cake_dev', 'Read the changelog'), 'http://cakephp.org/changelogs/' . Configure::version()); ?>
</p>
<?php
if (Configure::read('debug') > 0):
    Debugger::checkSecurityKeys();
endif;
?>
<?php if (file_exists(WWW_ROOT . 'css' . DS . 'cake.generic.css')): ?>

<?php endif; ?>
<p>
    <?php
    if (version_compare(PHP_VERSION, '7.1', '<')):
        echo '<span class="notice success">';
        echo __d('cake_dev', 'Your version of PHP is '. PHP_VERSION);
        echo '</span>';
    else:?></p>

<p id="url-rewriting-warning" style="background-color:#e32; color:#fff;"><?php
    echo __d('cake_dev', 'Your version of PHP is '.PHP_VERSION.' The version should be 7.0 ');
    echo '</span>';
    endif;
    ?>
</p>

<?php
$settings = Cache::settings();
if (!empty($settings)):
    echo '<span class="notice success">';
    //echo __d('cake_dev', 'The %s is being used for core caching. To change the config edit %s', '<em>' . $settings['engine'] . 'Engine</em>', 'APP/Config/core.php');
    echo '</span>';
else:
    echo '<span class="notice">';
    echo __d('cake_dev', 'Your cache is NOT working. Please check the settings in %s', 'APP/Config/core.php');
    echo '</span>';
endif;
?>
</p>
<p>
    <?php
    $filePresent = null;
    if (file_exists(APP . 'Config' . DS . 'database.php')):
        echo '<span class="notice success">';
        echo __d('cake_dev', 'Your database configuration file is present.');
        $filePresent = true;
        echo '</span>';
    else:
        echo '<span class="notice">';
        echo __d('cake_dev', 'Your database configuration file is NOT present.');
        echo '<br/>';
        echo __d('cake_dev', 'Rename %s to %s', 'APP/Config/database.php.default', 'APP/Config/database.php');
        echo '</span>';
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
                $errorMsg .= '<br />' . $attributes['message'];
            endif;
        endif;
    }
    ?>
    <p>
        <?php
        if ($connected && $connected->isConnected()):
            echo '<span class="notice success">';
            echo __d('cake_dev', 'App is able to connect to the database.');
            echo '</span>';
        else:
            echo '<span class="notice">';
            echo __d('cake_dev', 'App is NOT able to connect to the database.');
            echo '<br /><br />';
            echo $errorMsg;
            echo '</span>';
        endif;
        ?>
    </p>
    <?php
endif;

?>


