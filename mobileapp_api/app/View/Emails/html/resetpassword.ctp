<h4>Dear <?php echo $username;?>,</h4>



<p>
    You requested to reset the password for your <?php echo APP_NAME;?>  account with the e-mail address (<?php echo $toEmail;?> ). Please use this verification code in the app to reset your password.
 </p>

<p>
    Your verification code is <?php echo $code;?>
</p>



<p>

If you've received this mail in error, it's likely that another user entered
your email address by mistake while trying to reset a email. If you didn't
initiate the request, you don't need to take any further action and can safely
disregard this email.</p>
<p>
Sincerely,<br>
The <?php echo APP_NAME;?> Team
</p>
<p>
Note: This email address cannot accept replies. To fix an issue or learn more
about your account, visit our website:<a href="<?php echo BASE_URL; ?>"><?php echo BASE_URL; ?></a></p>