<footer><p>© 2019 All rights reserved. <a href="#">System License Agreement</a>
    </p></footer>
<div class="PopupParent" id="PopupParent"
     style="background: rgba(0, 0, 0, 0.3); display: none;  position:  fixed; top: 0;width:  100%;height:  100%;z-index:  99999;">
    <div class="wdth" style="">
        <button type="button" onclick="ClosePopup();" class="buttonColor closebnt">×</button>
        <div class="modal-content" style="">
            <div class="modal-body" id="contentReceived"> Loading...</div>
        </div>
    </div>
</div>
<style>

</style>
<style>    table.dataTable thead th, table.dataTable thead td {
        padding: 10px 9px;
        border-bottom: 1px solid #111;
    }

    table.dataTable tfoot th, table.dataTable tfoot td {
        padding: 10px 11px;
        border-top: 1px solid #111;
    }</style>

<script src="frontend_public/assets-minified/js/not_landing.min.js"></script>
<script src="frontend_public/assets-minified/js/map.min.js"></script>

<script src="frontend_public/assets-minified/js/login.min.js"></script>
<script type="text/javascript">
    $(document).ready(function () {
        $('.alert-dialog').click(function () {
            var modalTitle = $(this).attr('data-modal-title');
            if (!modalTitle || typeof modalTitle === 'undefined') {
                modalTitle = "Delete Confirmation";
            }
            $("#global-alert-modal #globalAlertFrm .modal-title").html(modalTitle);
            var message = "<i class='fa fa-info-circle'></i> " + $(this).attr('data-message');
            $("#global-alert-modal #globalAlertFrm").attr('action', $(this).attr('data-action'));
            $("#global-alert-modal #globalAlertFrm .modal-body").html(message);
            $("input[name=hdnResource]").val($(this).attr('data-id'));
            $("#global-alert-modal").modal('show');
        });
    });





   </script>
<script src="frontend_public/assets-minified/js/custom.js"></script>