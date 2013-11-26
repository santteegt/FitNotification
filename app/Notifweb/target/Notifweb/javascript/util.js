/**
 * Manejador de mensajes de error
 */
function errorHandler(item,message,container){
    var bar = container != '' ? $(container + " #status"):$('#status');
    bar.addClass('status-bar-error').html('').html(message);
    changeClass(item,'widget-ok','widget-error');    
    return false;
}

/**
 * Emision de un mensaje de error
 **/
function errorMessage(message, container){
    var bar = container ? $(container + " #status"):$('#status');
    bar.addClass('status-bar-error').html('').html(message);
    return false;
}

function clearstatusBar(container){
    var status = container ? $(container + " #status"):$('#status');
    changeClass(status,'status-bar-error','status-bar-ok');    
    status.html('').html('Bantec Inc.');
}

/**
 * Validador para widgets
 */
function validate(item, message, container){                
        if(item.val() === ''){
            return errorHandler(item, message, container);
        }
        changeClass(item,'widget-error','widget-ok');
        return true;
    }
    
function validateEqual(val1, val2, message, container){
    if(val1.val() != val2.val()){
        changeClass(val2,'widget-ok','widget-error');
        return errorHandler(val1,message != '' ? message:'LA NUEVA CONTRASEÑA INGRESADA NO COINCIDE', container);
        
    }
    changeClass(val1,'widget-error','widget-ok');
    changeClass(val2,'widget-error','widget-ok');
    return true;
}
    
/**
 * Cambio de clases CSS
 */    
function changeClass(item, lastClass, newClass){
    item.removeClass(lastClass).addClass(newClass);
    return true;
}

/**
 * Aniade la lista de dispositivos en la aplicacion
 */
function setDevice(key, value){
    $('#opciones #devices').append("<li id='"+key+"' data-model='"+value.Modelo+"'><a href=''>\n"+
        "<h3 class='ui-li-heading'>"+value.Modelo+"</h3>\n"+
        "<p class='ui-li-desc'><b>Fecha Ultima Conexión: </b></p><p>"+value.Fecha+"</p></a>\n<a class='delete' href=''></a>\n</li>"
    );
}

/**
 *Funcion que completa una lista de autorizaciones pendientes
 *en el entorno
 **/
function fillNotifCollapsible(type, notiflist){
    var max = 0;    
    var list = "<ul data-role='listview' data-inset='true' data-theme='c'>\n";
    $.each(notiflist, function(k,value) {         
        list += "<li " + (value.Estado == 0 ? "data-theme='e' data-status='0' ":"data-status='1' " ) + "data-token='" + value.Id + "'><a href=''>\n"
        + "<p class='ui-li-aside ui-li-desc'>Fecha:<strong>"+ value.Fecha + "</strong></p>\n"
        + "<h3 class='ui-li-heading'>" + value.Autorizacion + "</h3>\n"
        + "<p class='ui-li-desc'>Usuario Origen: " + value.Usuario +"</p>\n</a>\n</li>\n";
        max = k+1;        
    });    
    var head = "<div data-role='collapsible' data-theme='a' data-content-theme='a' id='" + type.substring(5,type.length) + "'>\n"
    +"<h3>\n<span class='auth-count'>"+type.substring(8,type.length)+"</span>\n"
    +"<span id='count' class='count-item ui-li-count ui-btn-up-c ui-btn-corner-all'>" + max + "</span>\n</h3>\n";
    
    $('#notifcollap').append(head + list + "</ul>\n</div>\n");    
}

/**
 * Manejador para el cambio de contrasenas e informacion de usuario
 **/
function changePassHandler(enviroment){
    $('#nupass').submit(function(event){
        clearstatusBar('#contrasena');
        var okMsg = $(this).jqmData('enviroment') == '1' ? true:false;
        var validI = true;
        if(enviroment){
            var nam = $('#textpn');
            var em1 = $('#textpc1');
            var em2 = $('#textpc2');
            var validN = validate(nam, "DEBE INGRESAR SUS NOMBRES", '#contrasena');
            var validE = validate(em1, "DEBE INGRESAR UN CORREO ELECTRONICO", '#contrasena');
            if((em2.val() != '' && !validateEqual(em1,em2, '#contrasena')) || !validN || !validE){
                validI = false;
            }
        }
        var pwd = $('#textp1');
        var npw1 = $('#textp2');
        var npw2 = $('#textp3');
        var tkn = $('#textp4');
        var validP = validate(pwd,'DEBE INGRESAR SU CONTRASEÑA', '#contrasena');
        var validU = validateEqual(npw1,npw2, '', '#contrasena');
        var validT = (enviroment) ? true: validate(tkn,'DEBE INGRESAR EL TOKEN DE SEGURIDAD', '#contrasena');
        if(!validU || !validP || !validI || !validT){
            return false;
        }
        $.mobile.showPageLoadingMsg();                        
        if(okMsg){
            $.post(ip + '/proc/chpwd', $(this).serialize(),
                function(data) {
                    if(data.devices){
                        $.mobile.hidePageLoadingMsg();                        
                        pwd.val('');
                        em2.val('');
                        npw1.val('');
                        npw2.val('');
                        OK();                    
                    }
            });
            event.preventDefault();
            return false;
        }else{
            $(this).attr('method','POST').attr('action',ip + '/proc/chpwd');        
            return true;        
        }
        
    });
}

/**
 * Handlers para establecer el borrado de errores cuando estos son lanzados
 */
function initHandlers(){
    $('input').bind('change',function(e, ui){
       cleanError($(this)); 
    });

    $('textarea').bind('change',function(e, ui){
           cleanError($(this)); 
    });
    
    $('input[type=radio]').bind('change',function(e, ui){
        $(this).parent().parent().find('label').each(function(index){
            cleanError($(this));
        });        
    });
}

/**
 * Elimina clase de marca de error en widgets
 */
function cleanError(element){
    element.removeClass('widget-error');
}

/**
 * Funcion de fin de sesion
 */
function logout(){
    $.ajax( {
        url:ip + "/proc/lout",
        type:'POST',        
       
        success:function(data){
            $(window.location).attr('href',ip + "/index.html");
        },
        error:function(data){
            getError();           
        }
    });   
}

/**
 *Ventana de dialogo qTip2
 **/
function dialogue(content, title) {
        /* 
         * Since the dialogue isn't really a tooltip as such, we'll use a dummy
         * out-of-DOM element as our target instead of an actual element like document.body
         */
        $('<div />').qtip(
        {
                content: {
                        text: content,
                        title: title
                },
                position: {
                        my: 'center', at: 'center', // Center it...
                        target: $(window) // ... in the window
                },
                show: {
                        ready: true, // Show it straight away
                        modal: {
                                on: true, // Make it modal (darken the rest of the page)...
                                blur: false // ... but don't close the tooltip when clicked
                        }
                },
                hide: false, // We'll hide it maunally so disable hide events
                style: 'ui-tooltip-undefined ui-tooltip-rounded ui-tooltip-dialogue', // Add a few styles
                events: {
                        // Hide the tooltip when any buttons in the dialogue are clicked
                        render: function(event, api) {
                                $('button', api.elements.content).click(api.hide);
                        },
                        // Destroy the tooltip once it's hidden as we no longer need it!
                        hide: function(event, api) {api.destroy();}
                }
        });
}

/**
 *Mensaje dialog
**/
function modalMsg(errorMsg, title){
    $('<div />').qtip(
    {
            id: 'modal', // Since we're only creating one modal, give it an ID so we can style it
            content: {
                    text: 
                    "<div>" +errorMsg+ "</div>",
                    title: {
                            text: title,
                            button: true
                    }
            },
            position: {
                    my: 'center', // ...at the center of the viewport
                    at: 'center',
                    target: $(window)
            },
            show: {
                    ready: true, // Show it straight away                   
                    solo: true, // ...and hide all other tooltips...
                    modal: true // ...and make it modal
            },
            hide: false,
            style: 'ui-tooltip-undefined ui-tooltip-rounded',
            events: {                        
                        // Destroy the tooltip once it's hidden as we no longer need it!
                        hide: function(event, api) {                             
                            api.destroy();
                            if(errorMsg && errorMsg.match('CAD.*').length > 0){
                                $(window.location).attr('href',ip + "/index.html");
                                //$.mobile.changePage(ip + "/index.html");
                            }
                        }
                }
    }); 
}

/**
 * Fix to current active button on navbar
 */
function resetNavHeaders(navbar, currentPage){
    navbar.find('ul a').each(function(index){        
        if($(this).attr('id') != currentPage + '-btn' ){
            $(this).removeClass('ui-btn-active');
        }else{
            $(this).addClass('ui-btn-active');
        }
    });
}

function getRoot(){
    var ip
    $.get("prop.cf", function(data) {
        ip = data;
        return ip;
    });
    
}

/**
 * Obtiene el errore del sevidor y lo muestra en un dialog
 */
function getError(){
    $.mobile.hidePageLoadingMsg();
    var json = {isModal:"true"};
    $.ajax({      
          url: ip + '/proc/error',
          type: "POST",                  
          data: json,
          dataType:'json',                  
          success: function(data){                      
              modalMsg(data.message, 'Error');                     
          }
    });
}

/**
 * Obtiene el errore del sevidor y lo muestra en el footer del entorno
 */
function getErrorFooter(container){
    $.mobile.hidePageLoadingMsg();
    var json = {isModal:"true"};
    $.ajax({      
          url: ip + '/proc/error',
          type: "POST",                  
          data: json,
          dataType:'json',                  
          success: function(data){
              errorMessage(data.message, container);              
          }
    });
}

/**
* Mensaje de proceso ejecutado correctamente
**/
function OK(){
  modalMsg("Guardado Correctamente", "Información");
}